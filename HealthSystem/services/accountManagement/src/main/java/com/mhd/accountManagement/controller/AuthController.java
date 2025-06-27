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
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserMapper mapper;

    @Autowired
    AccountActivationService activationService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        Users user = userRepository.findByUsername(loginRequest.username());

        if (!Boolean.TRUE.equals(user.getIsActivated())) {
            throw new UserHasNotBeenActivatedException("The user must activate the account before logging in.");
        }
        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new UserHasBeenDeletedException("The user was deleted");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(mapper.toLoginResponse(user, token));

        } catch (BadCredentialsException e) {
            throw new InvalidPasswordException("The password is incorrect.");
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<String> VerifyAccount(@RequestParam("token") String token) {
        activationService.activateAccount(token);
        return ResponseEntity.ok("Account activated successfully!");
    }


}
