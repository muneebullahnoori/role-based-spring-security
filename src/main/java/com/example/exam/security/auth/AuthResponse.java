package com.example.exam.security.auth;

import java.util.List;

public class AuthResponse {
    String accessToken;
    String refreshToken;
    String tokenType;
    List<String> roles;

    public AuthResponse(String accessToken, String refreshToken, String tokenType, List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.roles = roles;
    }
}
