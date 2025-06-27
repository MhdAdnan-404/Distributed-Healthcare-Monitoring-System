package com.mhd.accountManagement.model.DTO.UserDTO;

import com.mhd.accountManagement.model.Enums.ContactType;
import com.mhd.accountManagement.validation.ValidContactInfoMap;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record ContactInfoDTO(
        Integer id,
        @ValidContactInfoMap
        Map<ContactType, String> contacts

) {
}
