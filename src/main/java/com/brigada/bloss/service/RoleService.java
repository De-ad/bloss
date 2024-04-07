package com.brigada.bloss.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.brigada.bloss.dao.RoleRepository;
import com.brigada.bloss.entity.Role;
import com.brigada.bloss.entity.util.Roles;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role getUserRole() {
        log.info("--> getting user role from role repo...");
        return roleRepository.findByName(Roles.ROLE_USER.getTitle()).orElseThrow();
    }

    public Role getAdminRole() {
        log.info("--> getting admin role from role repo...");
        return roleRepository.findByName(Roles.ROLE_ADMIN.getTitle()).orElseThrow();
    }

    public Role getSuperAdminRole() {
        log.info("--> getting superadmin role from role repo...");
        return roleRepository.findByName(Roles.ROLE_SUPER_ADMIN.getTitle()).orElseThrow();
    }

}
