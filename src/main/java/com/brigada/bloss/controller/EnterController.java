package com.brigada.bloss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brigada.bloss.entity.User;
import com.brigada.bloss.listening.CredentialsRequest;
import com.brigada.bloss.service.UserService;

@RestController
@RequestMapping("/enter")
public class EnterController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody CredentialsRequest credentialsRequest) {
        return userService.login(credentialsRequest);
    }

}
