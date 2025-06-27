package com.mhd.accountManagement.services.authentication;

import com.mhd.accountManagement.model.Users;
import com.mhd.accountManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User.withUsername(users.getUsername())
                .password(users.getPassword())
                .roles(users.getRole().name())
                .build();

    }
}
