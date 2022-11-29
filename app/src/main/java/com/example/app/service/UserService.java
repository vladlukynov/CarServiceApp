package com.example.app.service;

import com.example.app.entity.User;
import com.example.app.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public void getUsers() {
        try {
            List<User> users = userRepository.getUsers();

            for (User user : users) {
                System.out.println(user);
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
