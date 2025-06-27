package com.mhd.CallManagement.domain.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateTokenRequest(
        @NotNull(message = "Room Name can't be null")
        @NotEmpty(message = "Room Name can't be empty")
        String roomName,
        @NotNull(message = "Participant Name can't be null")
        @NotEmpty(message = "Participant Name can't be empty")
        String participantName

) {
}
