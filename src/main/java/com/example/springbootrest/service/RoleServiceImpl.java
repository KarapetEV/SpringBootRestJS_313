package com.example.springbootrest.service;

import com.example.springbootrest.dao.RoleRepository;
import com.example.springbootrest.model.Role;
import com.example.springbootrest.model.UserDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByRole(String roleName) throws NotFoundException {
        Role role = roleRepository.getRoleByRole(roleName);
        if (role == null) {
            throw new NotFoundException("Role '" + roleName + "' not found");
        }
        return role;
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Set<Role> getRoleSet(UserDTO userDTO) {
        Set<Role> setOfRoles = new HashSet<>();
        for (String role : userDTO.getRolesNames()) {
            setOfRoles.add(roleRepository.getRoleByRole(role));
        }
        return setOfRoles;
    }

//    @Override
//    public Set<Role> setRoleByName(String name, String[] rolesName) {
//        return roleRepository.setRoleByName(name, rolesName);
//    }
}
