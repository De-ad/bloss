package com.brigada.bloss.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.brigada.bloss.model.User;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UserService {

     public ResponseEntity<Object> register(User user) {
        return null;
    }

    public ResponseEntity<Object> login(User user) {
        return null;
    }
}
