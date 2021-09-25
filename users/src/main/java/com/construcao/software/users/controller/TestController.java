package com.construcao.software.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.ok("Hello Anonymous");
    }

    @RolesAllowed("user")
    @GetMapping(value = "/user")
    public ResponseEntity<String> getUser(@RequestHeader String authorization) {
        return ResponseEntity.ok("Hello User");
    }

    @RolesAllowed("admin")
    @GetMapping(value = "/admin")
    public ResponseEntity<String> getAdmin(@RequestHeader String authorization) {
        return ResponseEntity.ok("Hello Admin");
    }

    @RolesAllowed({ "admin", "user" })
    @GetMapping(value = "/all-user")
    public ResponseEntity<String> getAllUser(@RequestHeader String authorization) {
        return ResponseEntity.ok("Hello All User");
    }
}