package com.construcao.software.users.controller;

import com.construcao.software.users.dto.PapelDTO;
import com.construcao.software.users.model.Papel;
import com.construcao.software.users.repository.PapelRepository;
import com.construcao.software.users.service.PapelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Deprecated
@RestController
@RequestMapping("/papeis")
public class PapelController {

    private final PapelService papelService;

    public PapelController(PapelService papelService) {
        this.papelService = papelService;
    }

    @GetMapping
    public ResponseEntity<List<Papel>> recuperaPapeis() {
        return ResponseEntity.ok(papelService.recuperarPapeis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Papel> recuperaPapeisPorId(@PathVariable String id) {
        return papelService.recuperarPapelPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Papel> criarPapel(@Valid @RequestBody PapelDTO papel, UriComponentsBuilder b) {

        var salvo = papelService.salvarPapel(papel);

        var uri = b.path("/papeis/{id}").buildAndExpand(salvo.getId()).toUri();

        return ResponseEntity.created(uri).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Papel> alterarPapel(@PathVariable String id,
                                          @Valid @RequestBody PapelDTO papelDTO) {
        try {
            var entidadeSalva = papelService.alterarPapel(id, papelDTO);
            return ResponseEntity.ok().body(entidadeSalva);
        } catch (IllegalArgumentException err) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Papel> deletarPapel(@PathVariable String id) {
        try{
            papelService.deletarPapel(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException err) {
            return ResponseEntity.notFound().build();
        }
    }

}
