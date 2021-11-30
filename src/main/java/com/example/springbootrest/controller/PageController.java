package com.example.springbootrest.controller;

import com.example.springbootrest.dao.UserRepository;
import com.example.springbootrest.model.User;
import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class PageController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public PageController(UserRepository userRepository, UserService userService, RoleService roleService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "main";
    }

    @GetMapping("/user")
    public String showUser(@AuthenticationPrincipal User userModel, Model model) {
        User user = userRepository.getUserByFirstName(userModel.getFirstName());
        model.addAttribute("user", user);
        return "userInfo";
    }

    @GetMapping("/user/{id}")
    public String showUserById(@PathVariable("id") long id, Model model) {
        User user = userRepository.getUserById(id);
        model.addAttribute("user", user);
        return "userInfo";
    }
}
