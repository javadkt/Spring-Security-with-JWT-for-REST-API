package com.example.springsecurity.model;

public class AuthenticationResponse {
    private final User user;
    private final String jwtToken;

    public AuthenticationResponse(User user, String jwt) {
        this.user = user;
        this.jwtToken = jwt;
    }

    public User getUser() {
        return user;
    }

    public String getJwt() {
        return jwtToken;
    }
}
