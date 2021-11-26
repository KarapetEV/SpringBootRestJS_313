package com.example.springbootrest.controller;

import com.example.springbootrest.exception.UserNotFoundException;
import com.example.springbootrest.model.Role;
import com.example.springbootrest.model.User;
import com.example.springbootrest.model.UserDTO;
import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
public class MainRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        if (userList == null || userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID = " + id + " not found!");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/admin/new")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        HttpHeaders headers = new HttpHeaders();
        user.setRoles(roleService.getRoleSet(userDTO));
        userService.saveUser(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<User> removeUser(@PathVariable("id") long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID = " + id + " not found!");
        }
        userService.removeUser(user);
        return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
    }

//    public void setUserRoles(User user) {
//        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
//            Set<Role> setOfRoles = new LinkedHashSet<>();
//            user.getRoles().forEach(role -> setOfRoles.add(roleService.getRoleByName(role.getRole())));
//            user.setRoles(setOfRoles);
//        } else {
//            user.setRoles(userService.getUser(user.getId()).getRoles());
//        }
//    }
}
