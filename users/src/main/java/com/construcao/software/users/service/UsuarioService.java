package com.construcao.software.users.service;

import com.construcao.software.users.client.KeySeguroClient;
import com.construcao.software.users.client.dto.ChangePasswordDTO;
import com.construcao.software.users.client.dto.EditUserDTO;
import com.construcao.software.users.client.dto.EvaluatePermissionRequest;
import com.construcao.software.users.dto.CriarUsuarioDTO;
import com.construcao.software.users.dto.MudarSenhaUsuarioDTO;
import com.construcao.software.users.dto.PapelDTO;
import com.construcao.software.users.dto.UsuarioDTO;
import com.construcao.software.users.mapper.UserDTOMapper;
import com.construcao.software.users.model.Papel;
import com.construcao.software.users.model.Usuario;
import com.construcao.software.users.repository.PapelRepository;
import com.construcao.software.users.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;
    private final KeySeguroClient keySeguroClient;

    public UsuarioService(UsuarioRepository usuarioRepository, PapelRepository papelRepository, KeySeguroClient keySeguroClient) {
        this.usuarioRepository = usuarioRepository;
        this.papelRepository = papelRepository;
        this.keySeguroClient = keySeguroClient;
    }

    public List<UsuarioDTO> recuperarUsuarios(String matricula, String login, String email, String inicioNome) {
        List<UsuarioDTO> usuarios;
        if (matricula == null && login == null && email == null && inicioNome == null) {
            usuarios = this.converterUsuariosParaDTO(usuarioRepository.findAll());
        } else {
            usuarios = this.converterUsuariosParaDTO(usuarioRepository.findAllByMatriculaOrLoginOrEmailOrNomeStartsWith(matricula, login, email, inicioNome));
        }

        return usuarios;
    }

    private List<UsuarioDTO> converterUsuariosParaDTO(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> {
                    var papeis = usuario.getPapeis()
                            .stream()
                            .map(papel -> new PapelDTO(papel.getNome()))
                            .collect(Collectors.toList());

                    return new UsuarioDTO(usuario.getId(), usuario.getEmail(), usuario.getNome(), usuario.getLogin(), papeis, usuario.getMatricula());
                })
                .collect(Collectors.toList());
    }

    private UsuarioDTO converterUsuarioParaDTO(Usuario usuario) {

        var papeis = usuario.getPapeis()
                .stream()
                .map(papel -> new PapelDTO(papel.getNome()))
                .collect(Collectors.toList());

        return new UsuarioDTO(usuario.getId(), usuario.getEmail(), usuario.getNome(), usuario.getLogin(), papeis, usuario.getMatricula());
    }

    public Optional<UsuarioDTO> recuperarUsuariosPorId(String id) {
        return usuarioRepository.findById(id).map(this::converterUsuarioParaDTO);
    }

    public UsuarioDTO criarUsuario(CriarUsuarioDTO usuarioDTO) {
        var usuarioKeycloak = keySeguroClient.createUser(UserDTOMapper.toCreateUserDTO(usuarioDTO));

        var papeis = buscaPapeis(usuarioDTO);
        var usuario = Usuario.builder()
                .keycloakId(usuarioKeycloak.getId())
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .login(usuarioDTO.getLogin())
                .papeis(papeis)
                .matricula(usuarioDTO.getMatricula())
                .senha(usuarioDTO.getSenha())
                .build();
        return converterUsuarioParaDTO(usuarioRepository.save(usuario));
    }

    private List<Papel> buscaPapeis(UsuarioDTO usuarioDTO) {
        return usuarioDTO.getPapeis()
                .stream()
                .map(it -> papelRepository.findByNome(it.getNome()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<Papel> buscaPapeis(CriarUsuarioDTO usuarioDTO) {
        return usuarioDTO.getPapeis()
                .stream()
                .map(it -> papelRepository.findByNome(it.getNome()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public void deletarUsuario(String id) {
        var usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        // Pode lançar exceção desconhecida.
        keySeguroClient.deleteUser(id);
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO editarTodoUsuario(String id, CriarUsuarioDTO usuarioDTO) {
        var usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        var usuario = usuarioOptional.get();

        var papeis = buscaPapeis(usuarioDTO);
        var entidade = Usuario.toBuilder(usuario)
                .senha(usuarioDTO.getSenha())
                .email(usuarioDTO.getEmail())
                .login(usuarioDTO.getLogin())
                .matricula(usuarioDTO.getMatricula())
                .nome(usuarioDTO.getNome())
                .papeis(papeis)
                .build();

        var papel = usuario.getPapeis().get(0).toString();
        var editUserDTO = new EditUserDTO(papel, usuario.getEmail());
        keySeguroClient.editUser(id, editUserDTO);
        return converterUsuarioParaDTO(usuarioRepository.save(entidade));
    }

    public UsuarioDTO editarUsuario(String id, MudarSenhaUsuarioDTO usuarioDTO) {

        var usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        var usuarioAlterado = usuario.get();
        usuarioAlterado.setEmail(usuarioDTO.getSenhaNova());

        return converterUsuarioParaDTO(usuarioRepository.save(usuarioAlterado));
    }
}
