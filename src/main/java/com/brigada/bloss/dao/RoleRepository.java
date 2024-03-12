package com.brigada.bloss.dao;

import org.springframework.stereotype.Repository;
import com.brigada.bloss.entity.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String string);

}
