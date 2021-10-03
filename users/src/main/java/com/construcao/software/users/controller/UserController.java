package com.construcao.software.users.controller;

import com.construcao.software.users.dto.UserDTO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final String realm;
    private final Keycloak keyCloak;

    public UserController(Keycloak keyCloak, @Value("${keycloak.realm}") String realm) {
        this.keyCloak = keyCloak;
        this.realm = realm;
    }

    @GetMapping
    public List<UserRepresentation> getAllUsers() {
        return keyCloak.realm(realm).users().list();
    }

    @GetMapping("/{userId}")
    public UserRepresentation getAllUsersByUserId(@PathVariable String userId) {
        return keyCloak.realm(realm).users().get(userId).toRepresentation();
    }

    @PostMapping
    public void createUser(@RequestBody UserDTO user) {
        var cR = new CredentialRepresentation();
        cR.setTemporary(false);
        cR.setType(CredentialRepresentation.PASSWORD);
        cR.setValue(user.getPassword());

        var newUser = new UserRepresentation();
        newUser.setUsername(user.getUsername());
        newUser.setCredentials(List.of(cR));
        newUser.setEnabled(true);

        keyCloak.realm(realm).users().create(newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        var foundUser = keyCloak.realm(realm).users().get(userId).toRepresentation();
        foundUser.setEnabled(false);
        keyCloak.realm(realm).users().get(userId).update(foundUser);
    }

    @PatchMapping("/{userId}")
    public void changePassword(@PathVariable String userId, @RequestBody String newPassword) {

        var foundUser = keyCloak.realm(realm).users().get(userId).toRepresentation();

        var cR = new CredentialRepresentation();
        cR.setTemporary(false);
        cR.setType(CredentialRepresentation.PASSWORD);
        cR.setValue(newPassword);

        foundUser.setCredentials(List.of(cR));

        foundUser.getCredentials().forEach(e -> System.out.println(e.getValue()));

        keyCloak.realm(realm).users().get(userId).update(foundUser);
    }

    @PutMapping("/{userId}")
    public void changeSomething(@PathVariable String userId, @RequestBody UserDTO userDTO) {

        var cR = new CredentialRepresentation();
        cR.setTemporary(false);
        cR.setType(CredentialRepresentation.PASSWORD);
        cR.setValue(userDTO.getPassword());

        var toUpdateUser = new UserRepresentation();
        toUpdateUser.setUsername(userDTO.getUsername());
        toUpdateUser.setCredentials(List.of(cR));
        toUpdateUser.setEnabled(true);
        toUpdateUser.setFirstName(userDTO.getFirstName());
        toUpdateUser.setLastName(userDTO.getLastName());
        toUpdateUser.setEmail(userDTO.getEmail());

        keyCloak.realm(realm).users().get(userId).update(toUpdateUser);
    }
}