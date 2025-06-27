package com.example.notification.service;

import com.example.notification.domain.NotificationUser;
import com.example.notification.exceptions.UserAlreadyExistsException;
import com.example.notification.exceptions.UserDosentExistException;
import com.example.notification.repository.NotificationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    NotificationUserRepository notificationUserRepository;

    public void saveUser(NotificationUser user){
        boolean exists = notificationUserRepository
                .findBySystemUserId(user.getSystemUserId())
                .isPresent();

        if (exists) {
            throw new UserAlreadyExistsException("This user already exists");
        }

        notificationUserRepository.save(user);
    }

    public void updateUser(NotificationUser updatedUser) {
        Optional<NotificationUser> existingOpt = notificationUserRepository.findBySystemUserId(updatedUser.getSystemUserId());

        NotificationUser savedUser;
        if (existingOpt.isPresent()) {
            NotificationUser existing = existingOpt.get();
            existing.setPreferredLanguage(updatedUser.getPreferredLanguage());
            existing.setContacts(updatedUser.getContacts());
            savedUser = notificationUserRepository.save(existing);
        } else {
            System.out.println("User not found for update, saving as new: " + updatedUser.getSystemUserId());
            savedUser = notificationUserRepository.save(updatedUser);
        }
        refreshUserCache(savedUser);
    }

    @Cacheable(value = "notificationUsers", key = "#systemUserId")
    public NotificationUser getUser(Integer systemUserId){
        NotificationUser user = notificationUserRepository.findBySystemUserId(systemUserId)
                .orElseThrow(() -> new UserDosentExistException("This user dosen't Exists"));

        return user;
    }

    @CachePut(value = "notificationUsers", key = "#user.systemUserId")
    public NotificationUser refreshUserCache(NotificationUser user) {
        return user;
    }



}
