package com.example.springbootrest.dao;

import com.example.springbootrest.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getRoleById(long id);
    Role getRoleByRole(String roleName);
}
