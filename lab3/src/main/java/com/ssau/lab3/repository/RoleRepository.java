package com.ssau.lab3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssau.lab3.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String name);
}
