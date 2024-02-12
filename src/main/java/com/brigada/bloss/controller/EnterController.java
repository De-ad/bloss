package com.brigada.bloss.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brigada.bloss.entity.User;

@RestController
@RequestMapping("/enter")
public class EnterController {


    public EnterController() { }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        return null;
    }

}
