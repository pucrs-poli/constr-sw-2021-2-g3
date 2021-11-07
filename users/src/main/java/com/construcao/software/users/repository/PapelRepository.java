package com.construcao.software.users.repository;

import com.construcao.software.users.model.Papel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PapelRepository extends MongoRepository<Papel, String> {
    Optional<Papel> findByNome(String nome);
}
