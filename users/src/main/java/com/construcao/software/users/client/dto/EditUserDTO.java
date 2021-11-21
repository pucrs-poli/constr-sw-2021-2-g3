package com.construcao.software.users.client.dto;

public class EditUserDTO {
    private String role;
    private String email;

    public EditUserDTO(String role, String email) {
        this.role = role;
        this.email = email;
    }
}
