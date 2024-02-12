package com.brigada.bloss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.brigada.bloss.model.User;
import com.brigada.bloss.services.UserService;

public class EnterController {

    @Autowired
    private UserService userService;

    public EnterController() { }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        return userService.login(user);
    }

}