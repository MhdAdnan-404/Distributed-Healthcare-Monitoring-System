package com.mhd.accountManagement.services.authentication;

import com.mhd.accountManagement.exception.InvalidPasswordException;
import com.mhd.accountManagement.exception.UserHasBeenDeletedException;
import com.mhd.accountManagement.exception.UserHasNotBeenActivatedException;
import com.mhd.accountManagement.model.DTO.UserDTO.LoginRequest;
import com.mhd.accountManagement.model.DTO.UserDTO.LoginResponse;
import com.mhd.accountManagement.model.DTO.UserDTO.UserMapper;
import com.mhd.accountManagement.model.Users;
import com.mhd.accountManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenGenerationService {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserMapper mapper;

    public LoginResponse login(LoginRequest loginRequest) {
        Users user = userRepository.findByUsername(loginRequest.username());

        if (!Boolean.TRUE.equals(user.getIsActivated())) {
            throw new UserHasNotBeenActivatedException("The user must activate the account before logging in.");
        }
        if (Boolean.TRUE.equals(user.getIsDeleted())) {
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

            return mapper.toLoginResponse(user, token);

        } catch (BadCredentialsException e) {
            throw new InvalidPasswordException("The password is incorrect.");
        }
    }



}
