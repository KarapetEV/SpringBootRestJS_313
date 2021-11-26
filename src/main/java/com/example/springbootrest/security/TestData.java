package com.example.springbootrest.security;

import com.example.springbootrest.model.Role;
import com.example.springbootrest.model.User;
import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class TestData {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public TestData(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void insertData() throws NotFoundException {

//        Role roleAdmin = new Role("ROLE_ADMIN");
//        Role roleUser = new Role("ROLE_USER");
        roleService.saveRole(new Role("ROLE_ADMIN"));
        roleService.saveRole(new Role("ROLE_USER"));

        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleService.getRoleByRole("ROLE_USER"));

        User bob = new User();
        bob.setFirstName("Bob");
        bob.setPassword("bob");
        bob.setLastName("Dealan");
        bob.setAge(51);
        bob.setEmail("bobby513@test.com");
        bob.setRoles(roles1);
        userService.saveUser(bob);

        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleService.getRoleByRole("ROLE_USER"));
        roles2.add(roleService.getRoleByRole("ROLE_ADMIN"));

        User tom = new User();
        tom.setFirstName("Tom");
        tom.setPassword("tom");
        tom.setLastName("Sawyer");
        tom.setAge(32);
        tom.setEmail("tom32@test.com");
        tom.setRoles(roles2);
        userService.saveUser(tom);
    }
}