package com.rest.Response;

import lombok.Data;
import java.util.List;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;

    private List<String> roles;
}