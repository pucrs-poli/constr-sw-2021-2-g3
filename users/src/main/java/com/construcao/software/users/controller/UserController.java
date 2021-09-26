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
    public List<UserRepresentation> getUser() {
        return keyCloak.realm(realm).users().list();
    }

//    @GetMapping(value = "/anonymous")
//    public ResponseEntity<String> getAnonymous() {
//        return ResponseEntity.ok("Hello Anonymous");
//    }
//
//    @RolesAllowed("user")
//    @GetMapping(value = "/user")
//    public ResponseEntity<String> getUser(@RequestHeader String authorization) {
//        return ResponseEntity.ok("Hello User");
//    }
//
//    @RolesAllowed("admin")
//    @GetMapping(value = "/admin")
//    public ResponseEntity<String> getAdmin(@RequestHeader String authorization) {
//        return ResponseEntity.ok("Hello Admin");
//    }
//
//    @RolesAllowed({ "admin", "user" })
//    @GetMapping(value = "/all-user")
//    public ResponseEntity<String> getAllUser(@RequestHeader String authorization) {
//        return ResponseEntity.ok("Hello All User");
//    }
}