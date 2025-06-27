package com.mhd.accountManagement.model.DTO.UserDTO;

public record UserRegisterResponseDTO(
        Integer id,
        String username,
        String token,
        String message
    ) {
}
