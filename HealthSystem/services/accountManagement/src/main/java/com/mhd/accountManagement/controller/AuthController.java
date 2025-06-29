package com.mhd.accountManagement.controller;

import com.mhd.accountManagement.exception.InvalidPasswordException;
import com.mhd.accountManagement.exception.UserExistsException;
import com.mhd.accountManagement.exception.UserHasBeenDeletedException;
import com.mhd.accountManagement.exception.UserHasNotBeenActivatedException;
import com.mhd.accountManagement.model.DTO.UserDTO.*;
import com.mhd.accountManagement.model.Users;
import com.mhd.accountManagement.repository.UserRepository;
import com.mhd.accountManagement.services.authentication.AccountActivationService;
import com.mhd.accountManagement.services.authentication.JwtUtil;
import com.mhd.accountManagement.services.authentication.TokenGenerationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    AccountActivationService activationService;

    @Autowired
    TokenGenerationService tokenGenerationService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse response = tokenGenerationService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> VerifyAccount(@RequestParam("token") String token) {
        activationService.activateAccount(token);
        return ResponseEntity.ok("Account activated successfully!");
    }


}
