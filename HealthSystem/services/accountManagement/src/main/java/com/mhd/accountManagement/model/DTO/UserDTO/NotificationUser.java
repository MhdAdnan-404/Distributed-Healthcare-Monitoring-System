package com.mhd.accountManagement.model.DTO.UserDTO;

import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.model.Enums.PrefferedLanguage;
import lombok.Builder;

import java.util.Map;

@Builder
public record NotificationUser(
        Integer systemUserId,
        PrefferedLanguage preferredLanguage,
        Map<ContactType, String> contacts

) {
}
