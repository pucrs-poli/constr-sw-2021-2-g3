package com.construcao.software.users.controller;

import com.construcao.software.users.model.Papel;
import com.construcao.software.users.repository.PapelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/papeis")
public class PapelController {
    private final PapelRepository papelRepository;

    public PapelController(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Papel>> getPapeis() {
        var result = papelRepository.findAll();

        return ResponseEntity.ok(result);
    }
}
