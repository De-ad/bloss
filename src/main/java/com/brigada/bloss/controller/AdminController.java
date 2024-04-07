package com.brigada.bloss.controller;

import com.brigada.bloss.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brigada.bloss.service.AdminService;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    UserService userService;

    @PostMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> setAdmin(@PathVariable String username) {
        log.info("-> got POST at /admin/" + username);
        return adminService.setAdmin(username);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        log.info("-> got GET at /admin/users");
        return userService.getAll();
    }

}
