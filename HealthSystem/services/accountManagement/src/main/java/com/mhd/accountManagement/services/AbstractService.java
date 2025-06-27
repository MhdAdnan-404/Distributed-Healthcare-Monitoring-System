package com.mhd.accountManagement.services;

import com.mhd.accountManagement.exception.UserDosentExistException;
import com.mhd.accountManagement.exception.UserRegisterException;
import com.mhd.accountManagement.kafka.KafkaProducer;
import com.mhd.accountManagement.model.DTO.UserDTO.NotificationUser;
import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.model.Enums.PrefferedLanguage;
import com.mhd.accountManagement.model.Enums.Role;
import com.mhd.accountManagement.model.Users;
import com.mhd.accountManagement.repository.UserRepository;
import com.mhd.accountManagement.services.authentication.AccountActivationService;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;

import java.util.Map;

import static java.lang.String.format;
@Service
public abstract class AbstractService<
        entity,
        ID,
        Repo extends JpaRepository<entity,ID>,
        RegisterRequest,
        updateRequest,
        RegisterResponse,
        UpdateResponse
        >{

    @Autowired
    public Repo repository;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    AccountActivationService activationService;


    public UpdateResponse update(updateRequest request){
        entity user = repository.findById(getIdFromRequest(request))
                .orElseThrow(() -> new UserDosentExistException(format("Cannot update Patient: No patient with this ID: ",getIdFromRequest(request))
                ));

        entity originalCopy = deepCopyEntity(user);

        mergeEntity(user, request);
        repository.save(user);

        afterUpdateHook(originalCopy, user);
        return toUpdateResponse(user);
    }


    public void afterUpdateHook(entity original, entity updated) {
        Users oldUser = extractUserFromEntity(original);
        Users newUser = extractUserFromEntity(updated);

        boolean languageChanged = oldUser.getLanguage() != null &&
                !oldUser.getLanguage().equals(newUser.getLanguage());

        boolean contactsChanged = oldUser.getContactInfo() != null &&
                !oldUser.getContactInfo().getContacts().equals(
                        newUser.getContactInfo().getContacts()
                );

        if (languageChanged || contactsChanged) {
            NotificationUser notificationUser = NotificationUser.builder()
                    .systemUserId(newUser.getId())
                    .preferredLanguage(PrefferedLanguage.valueOf(newUser.getLanguage().toString()))
                    .contacts(newUser.getContactInfo().getContacts())
                    .build();

            kafkaProducer.sendToKafka_NotificationUserUpdate(notificationUser);
        }
    }

    public Page<entity> getAllEntities(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Map<ContactType, String> getContacts(Integer id){
        return getContacts(id);
    }

    public RegisterResponse register(RegisterRequest request) {
        try {
            entity entity = toEntity(request);

            Users user = extractUserFromEntity(entity);
            user.setRole(getUserRole());
            user.setIsDeleted(false);
            user.setIsActivated(false);
            userRepository.save(user);

            String token = activationService.generateAndSaveToken(user);

            beforeSave(entity);

            repository.save(entity);
            return toRegisterResponseWithToken(entity, token);
        } catch (Exception e) {
            throw new UserRegisterException("Failed to register user: " + e.getMessage());
        }
    }

    public boolean deleteAccount(ID userId){
        Users user = userRepository.findById((Integer) userId)
                .orElseThrow(() -> new UserDosentExistException("User not found"));
        user.setIsDeleted(true);
        userRepository.save(user);
        return true;
    }
    public abstract void mergeEntity(entity U, updateRequest request);
    public abstract UpdateResponse toUpdateResponse(entity U);
    public abstract entity toEntity(RegisterRequest request);
    public abstract ID getIdFromRequest(updateRequest request);
    public abstract Users extractUserFromEntity(entity entity);
    public abstract Role getUserRole();
    public abstract RegisterResponse toRegisterResponseWithToken(entity entity, String token);
    public abstract void beforeSave(entity entity);
    public abstract entity deepCopyEntity(entity original);
    public abstract Map<ContactType, String> getContacts(ID id);

}
