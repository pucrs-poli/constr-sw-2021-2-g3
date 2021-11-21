package com.construcao.software.users.client.dto;

public class ChangePasswordDTO {
    private String password;

    public ChangePasswordDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
