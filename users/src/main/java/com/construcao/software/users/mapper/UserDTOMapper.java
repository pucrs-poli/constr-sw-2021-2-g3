package com.construcao.software.users.mapper;

import com.construcao.software.users.client.dto.CreateUserRequest;
import com.construcao.software.users.dto.UsuarioDTO;

public class UserDTOMapper {
    public static CreateUserRequest toCreateUserDTO(UsuarioDTO userDTO) {
        var userRole = userDTO.getPapeis().get(0).toString();
        return new CreateUserRequest(userDTO.getLogin(), userRole, userDTO.getEmail(), userDTO.getSenha());
    }
}
