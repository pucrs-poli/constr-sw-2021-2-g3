package com.construcao.software.users.controller;

import com.construcao.software.users.dto.PapelDTO;
import com.construcao.software.users.model.Papel;
import com.construcao.software.users.repository.PapelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/papeis")
public class PapelController {
    private final PapelRepository papelRepository;

    public PapelController(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    @GetMapping
    public ResponseEntity<List<Papel>> recuperaPapeis() {
        var result = papelRepository.findAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Papel> recuperaPapeisPorId(@PathVariable String id) {
        var result = papelRepository.findById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criarPapel(@RequestBody PapelDTO papel, UriComponentsBuilder b) {
        var entidade = new Papel(papel.getNome());

        var salvo = papelRepository.save(entidade);

        var uriComponents = b.path("/papeis/{id}").buildAndExpand(salvo.getId());

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterarPapel(@PathVariable String id,
                                          @RequestBody PapelDTO papelDTO) {
        var papel = papelRepository.findById(id);

        if (papel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var entidade = new Papel(id, papelDTO.getNome());
        var entidadeSalva = papelRepository.save(entidade);

        return ResponseEntity.ok().body(entidadeSalva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPapel(@PathVariable String id) {
        var papel = papelRepository.findById(id);

        if (papel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        papelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
