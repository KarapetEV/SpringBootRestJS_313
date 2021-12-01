package com.example.springbootrest.controller;

import com.example.springbootrest.exception.UserNotFoundException;
import com.example.springbootrest.model.Role;
import com.example.springbootrest.model.User;
import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class MainRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        if (userList != null && !userList.isEmpty()) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/showUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID = " + id + " not found!");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<User> showUserInfo(@AuthenticationPrincipal User user) throws NotFoundException {
        User userById = userService.getUserByEmail(user.getEmail());
        return ResponseEntity.ok(userById);
    }

    @PostMapping("/newUser")
    public ResponseEntity<User> addUser(@RequestBody User user) throws NotFoundException {
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> removeUser(@PathVariable("id") long id) throws UserNotFoundException {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    public void setUserRoles(User user) {
//        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
//            Set<Role> setOfRoles = new LinkedHashSet<>();
//            user.getRoles().forEach(role -> {
//                try {
//                    setOfRoles.add(roleService.getRoleByRole(role.getRole()));
//                } catch (NotFoundException e) {
//                    e.printStackTrace();
//                }
//            });
//            user.setRoles(setOfRoles);
//        } else {
//            user.setRoles(userService.getUserById(user.getId()).getRoles());
//        }
//    }
}
