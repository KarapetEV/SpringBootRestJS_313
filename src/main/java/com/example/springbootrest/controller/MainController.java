package com.example.springbootrest.controller;

import com.example.springbootrest.model.Role;
import com.example.springbootrest.model.User;
import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Controller
public class MainController {

    private final UserService userService;
    private final RoleService roleService;


    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String mainPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "main";
    }

//    @PostMapping("/admin/new")
//    public String addUser(@ModelAttribute("user") User newUser, @RequestParam("roles") String[] rolesNames) {
//        Set<Role> roleSet = new LinkedHashSet<>();
//        newUser.getRoles().forEach(role -> roleSet.add(roleService.getRoleByName(role.getRole())));
//        newUser.setRoles(roleSet);
//        userService.saveUser(newUser);
//        return "redirect:/admin/users";
//    }

//    @PatchMapping("/admin/{id}")
//    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id,
//                             @RequestParam("roles") String[] rolesNames) {
//        setUserRoles(user);
//        userService.saveUser(user);
//        return "redirect:/admin/users";
//    }

//    @DeleteMapping("/admin/{id}")
//    public String removeUser(@PathVariable("id") long id) {
//        userService.removeUser(id);
//        return "redirect:/admin/users";
//    }

    @GetMapping("/user")
    public String getUserById(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "userInfo";
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
