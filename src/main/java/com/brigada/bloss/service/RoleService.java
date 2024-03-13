package com.brigada.bloss.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.brigada.bloss.dao.RoleRepository;
import com.brigada.bloss.entity.Role;
import com.brigada.bloss.entity.util.Roles;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName(Roles.ROLE_USER.getTitle()).orElseThrow();
    }

    public Role getAdminRole() {
        return roleRepository.findByName(Roles.ROLE_ADMIN.getTitle()).orElseThrow();
    }

    public Role getSuperAdminRole() {
        return roleRepository.findByName(Roles.ROLE_SUPER_ADMIN.getTitle()).orElseThrow();
    }

}
