package com.construcao.software.users.dto;

import javax.validation.constraints.NotEmpty;

public class PapelDTO {

    @NotEmpty
    private String nome;

    public PapelDTO() {
    }

    public PapelDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
