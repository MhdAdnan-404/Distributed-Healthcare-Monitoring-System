package com.mhd.accountManagement.model.DTO.DoctorDTO;

import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.UserMapper;
import com.mhd.accountManagement.model.Doctor;
import com.mhd.accountManagement.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorMapper {

    @Autowired
    UserMapper userMapper;
    public DoctorUpdateResponse toDoctorUpdateResponse(Doctor doctor) {
        Users user = doctor.getUser();
        ContactInfo contactInfo = user.getContactInfo();


        ContactInfoDTO contactInfoDTO = userMapper.toContactInfoDTO(contactInfo);


        List<AddressDTO> addressDTOs = user.getAddresses().stream()
                .map(address -> new AddressDTO(
                        address.getId(),
                        address.getCountry(),
                        address.getCity(),
                        address.getStreetName(),
                        address.getStreetNumber(),
                        address.getLabel()
                ))
                .toList();

        return new DoctorUpdateResponse(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpeciality(),
                contactInfoDTO,
                addressDTOs
        );
    }



    public Doctor toDoctor(DoctorRegisterRequest request){
        Users user  = userMapper.toUserEntity(request.user());
        Doctor doctor = Doctor.builder()
                .name(request.name())
                .specialty(request.specialty())
                .user(user)
                .build();
        return doctor;
    }

    public DoctorRegisterResponse toDoctorRegisterResponse(Doctor doctor,String token) {
        DoctorRegisterResponse doctorRegisterResponse = new DoctorRegisterResponse(
                doctor.getId(),
                token
        );
        return doctorRegisterResponse;
    }
}
