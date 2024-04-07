package com.brigada.bloss.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.UserRepository;
import com.brigada.bloss.entity.User;
import com.brigada.bloss.entity.util.Roles;
import com.brigada.bloss.entity.Role;
import com.brigada.bloss.listening.MessageResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public ResponseEntity<Object> setAdmin(String username) {
        log.info("--> setting admin for '" + username + "'...");
        Optional<User> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return ResponseEntity.status(404).body(new MessageResponse("User with username=" + username + " does not exists"));
        }
        User user = optUser.get();
        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            if (role.getName().equals(Roles.ROLE_ADMIN.getTitle())) {
                return ResponseEntity.status(200).body(user);
            }
        }

        roles.add(roleService.getAdminRole());
        user.setRoles(roles);
        
        userRepository.save(user);

        return ResponseEntity.status(201).body(user);
    }

}
