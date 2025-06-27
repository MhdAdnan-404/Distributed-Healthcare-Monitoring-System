package com.mhd.accountManagement.model.DTO.UserDTO;

import lombok.Builder;

import java.time.Instant;

@Builder
public record VerificationNotification(
        boolean success,
        String username,
        String email,
        Instant timestamp
) {
}
