package com.construcao.software.users.repository;

import com.construcao.software.users.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    List<Usuario> findAllByMatriculaOrLoginOrEmailOrNomeStartsWith(String matricula, String login, String email, String nome);
}