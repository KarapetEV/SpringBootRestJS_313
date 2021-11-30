package com.example.springbootrest.service;

import com.example.springbootrest.dao.UserRepository;
import com.example.springbootrest.model.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private BCryptPasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCrypt().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(bCrypt().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("User with email: '" + email + "' not found");
        }
        return user;
    }

    @Override
    public User getUserByName(String name) throws NotFoundException {
        User user = userRepository.getUserByFirstName(name);
        if (user == null) {
            throw new NotFoundException("User '" + name + "' not found");
        }
        return user;
    }
}
