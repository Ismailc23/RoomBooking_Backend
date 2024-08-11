package com.rest.contoller;

import com.rest.Entity.LoginUserDto;
import com.rest.Entity.RegisterUserDto;
import com.rest.Entity.User;
import com.rest.ExceptionHandling.UserException.InvalidCredentialsException;
import com.rest.ExceptionHandling.UserException.UserNameAlreadyExistException;
import com.rest.Response.LoginResponse;
import com.rest.services.AuthenticationService;
import com.rest.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RequestMapping("/auth")
@RestController
@CrossOrigin("*")
public class AuthenticationController {
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/registrationMethod")
    public ResponseEntity<?> registrationProcess(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.ok(registeredUser);
        }
        catch(UserNameAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
    }

    @PostMapping("/loginMethod")
    public ResponseEntity<?> loginMethod(@RequestBody LoginUserDto loginUserDto, Model model) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());
            loginResponse.setRoles(authenticatedUser.getRoles()
                    .stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toList()));
            return ResponseEntity.ok(loginResponse);
        }
        catch (InvalidCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
        }
    }
}
