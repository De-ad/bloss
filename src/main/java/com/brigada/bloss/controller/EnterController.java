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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/enter")
@Slf4j
public class EnterController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        log.info("-> got POST at /enter/register from " + user.getUsername());
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody CredentialsRequest credentialsRequest) {
        log.info("-> got POST at /enter/login from " + credentialsRequest.getUsername());
        return userService.login(credentialsRequest);
    }

}
