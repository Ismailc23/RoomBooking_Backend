package com.rest.services;

import com.rest.Entity.*;
import com.rest.ExceptionHandling.UserException.InvalidCredentialsException;
import com.rest.ExceptionHandling.UserException.UserNameAlreadyExistException;
import com.rest.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rest.Repository.RoleRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new UserNameAlreadyExistException("Username already exists: " + input.getEmail());
        }
        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(RoleEnum.USER).ifPresent(roles::add);
        return userRepository.save(new User(input.getFullName(), input.getEmail(), passwordEncoder.encode(input.getPassword()), roles));
    }

    public User authenticate(LoginUserDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        }
        catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
    }
}