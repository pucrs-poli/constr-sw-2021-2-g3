package com.construcao.software.users.client.dto;

public class CreateUserRequest {

    private String username;
    private String role;
    private String email;
    private String password;

    public CreateUserRequest(String username, String role, String email, String password) {
        this.username = username;
        this.role = role;
        this.email = email;
        this.password = password;
    }
}
