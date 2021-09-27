package com.construcao.software.users.controller;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final String realm = "Users-realm";

    private final Keycloak keyCloak;

    public UserController(Keycloak keyCloak) {
        this.keyCloak = keyCloak;
    }

    @GetMapping
    public List<UserRepresentation> getAllUsers() {
        return keyCloak.realm(realm).users().list();
    }


}