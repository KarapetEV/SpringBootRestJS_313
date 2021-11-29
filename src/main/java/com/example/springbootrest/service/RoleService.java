package com.example.springbootrest.service;

import com.example.springbootrest.model.Role;
import com.example.springbootrest.model.UserDTO;
import javassist.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleByRole(String roleName) throws NotFoundException;

    void saveRole(Role role);

    void updateRole(Role role);

    Set<Role> getRoleSet(String[] roleSet) throws NotFoundException;

    //Set<Role> setRoleByName(String name, String[] rolesName);
}
