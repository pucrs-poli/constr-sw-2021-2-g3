package com.construcao.software.users.dto;

import com.construcao.software.users.model.Papel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UsuarioDTO {
    @Email
    private String email;

    @NotEmpty
    private String login;

    @NotEmpty
    private String nome;

    private List<PapelDTO> papeis;

    @Pattern(regexp = "\\d{9}")
    private String matricula;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String email, String nome, String login, List<PapelDTO> papeis, String matricula) {
        this.email = email;
        this.nome = nome;
        this.login = login;
        this.papeis = papeis;
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public List<PapelDTO> getPapeis() {
        return papeis;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }
}
