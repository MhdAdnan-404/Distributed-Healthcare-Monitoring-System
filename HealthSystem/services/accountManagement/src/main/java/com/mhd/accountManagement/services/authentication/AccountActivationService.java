package com.mhd.accountManagement.services.authentication;

import com.mhd.accountManagement.exception.AccountAlreadyActivatedException;
import com.mhd.accountManagement.exception.ActivationFailedException;
import com.mhd.accountManagement.exception.InvalidActivationTokenException;
import com.mhd.accountManagement.kafka.KafkaProducer;
import com.mhd.accountManagement.model.ActivationToken;
import com.mhd.accountManagement.model.DTO.UserDTO.NotificationUser;
import com.mhd.accountManagement.model.DTO.UserDTO.UserMapper;
import com.mhd.accountManagement.model.DTO.UserDTO.VerificationNotification;
import com.mhd.accountManagement.model.Enums.Role;
import com.mhd.accountManagement.model.Users;
import com.mhd.accountManagement.repository.ActivationTokenRepository;
import com.mhd.accountManagement.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountActivationService {

    @Autowired
    @Lazy
    private PatientService patientService;

    @Autowired
    private ActivationTokenRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private KafkaProducer kafkaProducer;

    public String generateAndSaveToken(Users user) {
        String token = UUID.randomUUID().toString();
        ActivationToken activationToken = ActivationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(1))
                .used(false)
                .build();

        repository.save(activationToken);
        return token;
    }

    public boolean activateAccount(String token){
        ActivationToken activationToken = repository.findByToken(token)
                .orElseThrow(() -> new InvalidActivationTokenException("Token is invalid"));

        if (activationToken.getUsed() ||  activationToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new InvalidActivationTokenException("Token is Expired or has been used");
        }
        Users user = activationToken.getUser();

        if (Boolean.TRUE.equals(user.getIsActivated())) {
            throw new AccountAlreadyActivatedException("This account is already activated.");
        }
        try {
            user.setIsActivated(true);
            activationToken.setUsed(true);
            repository.save(activationToken);
            VerificationNotification verificationNotification =mapper.toActivationNotification(user);
            kafkaProducer.sendToKafka_AccountVerification(verificationNotification);

            if (user.getRole() == Role.PATIENT) {
                Boolean res = patientService.addPatientToVitalSignManagement(user);
                if (!res) {
                    throw new RuntimeException("Patient service failed to add patient.");
                }
            }
//            send new users to be stored in the notification microservice
            kafkaProducer.sendToKafka_NotificationUserAdd(mapper.toNotificationUser(user));
            return true;

        } catch (Exception e) {
            user.setIsActivated(false);
            activationToken.setUsed(false);
            repository.save(activationToken);
            throw new ActivationFailedException("Account activation failed due to downstream error: " + e.getMessage());
        }
    }
}
