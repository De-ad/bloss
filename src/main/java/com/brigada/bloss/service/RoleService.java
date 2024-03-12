package com.brigada.bloss.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.brigada.bloss.dao.RoleRepository;
import com.brigada.bloss.entity.Role;

public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow();
    }

    public Role getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").orElseThrow();
    }

}
