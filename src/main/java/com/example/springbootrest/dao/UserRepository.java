package com.example.springbootrest.dao;

import com.example.springbootrest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(long id);
    User getUserByFirstName(String name);
    User getUserByEmail(String email);
}
