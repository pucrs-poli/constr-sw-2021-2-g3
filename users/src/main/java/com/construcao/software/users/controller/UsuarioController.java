package com.construcao.software.users.controller;

import com.construcao.software.users.dto.PapelDTO;
import com.construcao.software.users.dto.UsuarioDTO;
import com.construcao.software.users.model.Papel;
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
    public ResponseEntity<List<UsuarioDTO>> recuperarUsuarios(@RequestParam(required = false) String matricula,
                                                              @RequestParam(required = false) String login,
                                                              @RequestParam(required = false) String email,
                                                              @RequestParam(required = false) String inicioNome) {
        List<UsuarioDTO> usuarios;
        if (matricula == null && login == null && email == null && inicioNome == null) {
            usuarios = converterUsuariosParaDTO(usuarioRepository.findAll());
        } else {
            usuarios = converterUsuariosParaDTO(usuarioRepository.findAllByMatriculaOrLoginOrEmailOrNomeStartsWith(matricula, login, email, inicioNome));
        }
        return ResponseEntity.ok(usuarios);
    }

    private List<UsuarioDTO> converterUsuariosParaDTO(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> {
                    var papeis = usuario.getPapeis()
                            .stream()
                            .map(papel -> new PapelDTO(papel.getNome()))
                            .collect(Collectors.toList());

                    return new UsuarioDTO(usuario.getEmail(), usuario.getNome(), usuario.getLogin(), papeis, usuario.getMatricula());
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

        var papeis = buscaPapeis(usuarioDTO);

        var usuario = new Usuario(usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getLogin(), papeis, usuarioDTO.getMatricula());
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

    @PutMapping("/{id}")
    public ResponseEntity<?> alterarTodoUsuario(@PathVariable String id,
                                          @RequestBody UsuarioDTO usuarioDTO) {
        var usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var papeis = buscaPapeis(usuarioDTO);

        var entidade = new Usuario(id, usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getLogin(), papeis, usuarioDTO.getMatricula());
        var entidadeSalva = usuarioRepository.save(entidade);

        return ResponseEntity.ok().body(entidadeSalva);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> alterarUsuario(@PathVariable String id,
                                            @RequestBody UsuarioDTO usuarioDTO) {
        var usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var usuarioAlterado = usuario.get();

        List<Papel> papeis;
        if (usuarioDTO.getPapeis() != null) {
            papeis = buscaPapeis(usuarioDTO);
            usuarioAlterado.setPapeis(papeis);
        }

        if (usuarioDTO.getEmail() != null) {
            usuarioAlterado.setEmail(usuarioDTO.getEmail());
        }

        if (usuarioDTO.getNome() != null) {
            usuarioAlterado.setNome(usuarioDTO.getNome());
        }

        if (usuarioDTO.getMatricula() != null) {
            usuarioAlterado.setMatricula(usuarioDTO.getMatricula());
        }

        if (usuarioDTO.getLogin() != null) {
            usuarioAlterado.setLogin(usuarioDTO.getLogin());
        }

        var entidadeSalva = usuarioRepository.save(usuarioAlterado);
        return ResponseEntity.ok().body(entidadeSalva);
    }

    private List<Papel> buscaPapeis(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioDTO.getPapeis()
                .stream()
                .map(it -> papelRepository.findByNome(it.getNome()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
