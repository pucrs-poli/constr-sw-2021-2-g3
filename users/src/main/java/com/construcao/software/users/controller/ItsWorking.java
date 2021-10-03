package com.construcao.software.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/is-it-working")
public class ItsWorking {

    @GetMapping
    public String itsWorking() {
        return "Yes, it's working";
    }
}
