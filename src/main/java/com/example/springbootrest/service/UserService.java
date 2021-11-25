package com.example.springbootrest.service;

import com.example.springbootrest.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(long id);

    public void addUser(User user);

    void saveUser(User user);

    void removeUser(long id);

    void updateUser(long id, User updatedUser);

    User getUserByEmail(String email);
}
