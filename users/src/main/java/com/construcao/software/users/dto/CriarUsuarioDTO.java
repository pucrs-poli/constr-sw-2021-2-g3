package com.construcao.software.users.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

public class CriarUsuarioDTO {

    @Email(message = "Favor inserir e-mail no seguinte formato: rabelo@example.com")
    private String email;

    @NotEmpty(message = "Impossível criar um login sem nome")
    private String login;

    @NotEmpty(message = "Impossível criar um usuário sem nome")
    private String nome;

    private List<PapelDTO> papeis;

    @Pattern(regexp = "\\d{9}", message = "A matricula deve ter 9 dígitos")
    private String matricula;

    @NotEmpty(message = "Impossível criar um usuário sem senha")
    private String senha;

    public CriarUsuarioDTO() {
    }

    public CriarUsuarioDTO(String email, String nome, String login, List<PapelDTO> papeis, String matricula) {
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

    public String getSenha() {
        return senha;
    }
}
