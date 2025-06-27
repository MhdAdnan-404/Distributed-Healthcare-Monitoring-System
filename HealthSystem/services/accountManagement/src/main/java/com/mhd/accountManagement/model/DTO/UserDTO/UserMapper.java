package com.mhd.accountManagement.model.DTO.UserDTO;

import com.mhd.accountManagement.model.Address;
import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

//shouldn't this class be static ?
@Service
public class UserMapper {

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse toUserResponse(Users user){
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getIsActivated(),
                user.getIsDeleted()
        );
    }

    public UserUpdateResponse toUserUpdateResponse(Users user){
        return new UserUpdateResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public ContactInfoDTO toContactInfoDTO(ContactInfo entity) {
        return new ContactInfoDTO(
                entity.getId(),
                entity.getContacts()
        );
    }

    public Address toAddress(AddressDTO addressDTO) {
        Address address = Address.builder()
                .country(addressDTO.country())
                .city(addressDTO.city())
                .streetName(addressDTO.streetName())
                .streetNumber(addressDTO.streetNumber())
                .label(addressDTO.label())
                .build();

        return address;
    }

    public NotificationUser toNotificationUser(Users user){
        return NotificationUser
                .builder()
                .systemUserId(user.getId())
                .preferredLanguage(user.getLanguage())
                .contacts(user.getContactInfo().getContacts())
                .build();
    }

    public LoginResponse toLoginResponse(Users user, String token){
        return new LoginResponse(token, user.getRole());
    }

    public UserRegisterResponseDTO toRegisterResponse(Users user, String token,String message){
        return new UserRegisterResponseDTO(user.getId(), user.getUsername(), token ,message);
    }

    public ContactInfo toContactInfo(ContactInfoDTO contactInfoDTO) {
        return new ContactInfo(contactInfoDTO.contacts());
    }

    public Users toUserEntity(UserRegisterRequestDTO request) {
        Users user = Users.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();


        List<Address> addresses = request.addresses().stream()
                .map(this::toAddress)
                .peek(address -> address.setUser(user))
                .toList();
        user.setAddresses(addresses);

        ContactInfo contactInfo = toContactInfo(request.contactInfoDTO());
        contactInfo.setUser(user);
        user.setContactInfo(contactInfo);

        return user;

    }

    public VerificationNotification toActivationNotification(Users user) {
        return VerificationNotification
                .builder()
                .success(true)
                .username(user.getUsername())
//                should be the registration email
                .email(user.getContactInfo().getContacts().get(ContactType.EMAIL))
                .timestamp(Instant.now())
                .build();
    }
}
