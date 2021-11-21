package com.construcao.software.users.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;

import javax.management.relation.Role;
import java.util.List;

public class Usuario {
    @Id
    private String id;
    private String keycloakId;
    private String nome;
    private String email;
    private String login;
    private List<Papel> papeis;
    private String matricula;
    private String senha;

    public Usuario(Builder builder) {
        this.nome = builder.nome;
        this.email = builder.email;
        this.login = builder.login;
        this.papeis = builder.papeis;
        this.matricula = builder.matricula;
        this.senha = builder.senha;
        this.keycloakId = builder.keycloakId;
    }

    public Usuario() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<Papel> papeis) {
        this.papeis = papeis;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder toBuilder(Usuario usuario) {
        return new Builder(usuario);
    }

    public static final class Builder {

        private String id;
        private String keycloakId;
        private String nome;
        private String email;
        private String login;
        private List<Papel> papeis;
        private String matricula;
        private String senha;

        private Builder() {
        }

        private Builder(Usuario usuario) {
            this.id = usuario.id;
            this.nome = usuario.nome;
            this.email = usuario.email;
            this.login = usuario.login;
            this.papeis = usuario.papeis;
            this.matricula = usuario.matricula;
            this.senha = usuario.senha;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder keycloakId(String keycloakId) {
            this.keycloakId = keycloakId;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder papeis(List<Papel> papeis) {
            this.papeis = papeis;
            return this;
        }

        public Builder matricula(String matricula) {
            this.matricula = matricula;
            return this;
        }

        public Builder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }
}
