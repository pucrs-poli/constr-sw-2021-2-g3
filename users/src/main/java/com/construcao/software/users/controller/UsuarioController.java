package com.construcao.software.users.controller;

import com.construcao.software.users.dto.UsuarioDTO;
import com.construcao.software.users.model.Papel;
import com.construcao.software.users.model.Usuario;
import com.construcao.software.users.repository.PapelRepository;
import com.construcao.software.users.repository.UsuarioRepository;
import com.construcao.software.users.service.UsuarioService;
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

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> recuperarUsuarios(@RequestParam(required = false) String matricula,
                                                              @RequestParam(required = false) String login,
                                                              @RequestParam(required = false) String email,
                                                              @RequestParam(required = false) String inicioNome) {
        var usuarios = usuarioService.recuperarUsuarios(matricula, login, email, inicioNome);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> recuperarUsuariosPorId(@PathVariable String id) {
        return usuarioService.recuperarUsuariosPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO,
                                          UriComponentsBuilder b) {
        try {
            var salvo = usuarioService.criarUsuario(usuarioDTO);
            var uriComponents = b.path("/usuarios/{id}").buildAndExpand(salvo.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(salvo);
        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String id) {
        try {
            usuarioService.deletarUsuario(id);
        } catch(IllegalArgumentException err) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> alterarTodoUsuario(@PathVariable String id,
                                          @RequestBody UsuarioDTO usuarioDTO) {
        var usuario = usuarioService.editarTodoUsuario(id, usuarioDTO);
        return ResponseEntity.ok().body(usuario);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> alterarUsuario(@PathVariable String id,
                                            @RequestBody UsuarioDTO usuarioDTO) {
        var entidadeSalva = usuarioService.editarUsuario(id, usuarioDTO);
        return ResponseEntity.ok().body(entidadeSalva);
    }
}
