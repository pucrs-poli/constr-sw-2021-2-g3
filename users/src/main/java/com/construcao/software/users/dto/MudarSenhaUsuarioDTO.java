package com.construcao.software.users.dto;

public class MudarSenhaUsuarioDTO {

    private String senhaNova;

    public MudarSenhaUsuarioDTO(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

}
