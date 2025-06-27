package com.example.notification.clients;

import com.example.notification.domain.Enums.ContactType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(
        name="vital-SignManagement-service",
        url="${application.config.accountManagement-url}"
)
public interface AccountManagementClient {

    @GetMapping("/getContactsForEntitiy/{id}")
    Map<ContactType,String> getContactsForEntity (@PathVariable Integer id);
}
