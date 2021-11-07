package com.construcao.software.users.controller;

import com.construcao.software.users.dto.PapelDTO;
import com.construcao.software.users.dto.UsuarioDTO;
import com.construcao.software.users.model.Usuario;
import com.construcao.software.users.repository.PapelRepository;
import com.construcao.software.users.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;

    public UsuarioController(UsuarioRepository usuarioRepository, PapelRepository papelRepository) {
        this.usuarioRepository = usuarioRepository;
        this.papelRepository = papelRepository;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> recuperarUsuarios() {

        var usuarios = converterUsuariosParaDTO(usuarioRepository.findAll());
        return ResponseEntity.ok(usuarios);
    }

    private List<UsuarioDTO> converterUsuariosParaDTO(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> {
                    var papeis = usuario.getPapeis()
                            .stream()
                            .map(papel -> new PapelDTO(papel.getNome()))
                            .collect(Collectors.toList());

                    return new UsuarioDTO(usuario.getEmail(), usuario.getLogin(), papeis, usuario.getMatricula());
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> recuperarUsuariosPorId(@PathVariable String id) {
        var result = usuarioRepository.findById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO,
                                          UriComponentsBuilder b) {

        var papeis = usuarioDTO.getPapeis()
                .stream()
                .map(it -> papelRepository.findByNome(it.getNome()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        var usuario = new Usuario(usuarioDTO.getEmail(), usuarioDTO.getLogin(), papeis, usuarioDTO.getMatricula());
        var salvo = usuarioRepository.save(usuario);
        var uriComponents = b.path("/usuarios/{id}").buildAndExpand(salvo.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable String id) {
        var usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}