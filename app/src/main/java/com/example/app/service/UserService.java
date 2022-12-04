package com.example.app.service;

import com.example.app.entity.User;
import com.example.app.exception.NoUserByLoginException;
import com.example.app.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public List<User> getUsers() throws SQLException {
        return userRepository.getUsers();
    }

    public User getUser(String userLogin) throws SQLException, NoUserByLoginException {
        List<User> users = userRepository.getUsers();

        Optional<User> user = users.stream().filter(user_ -> user_.getUserLogin().equals(userLogin)).findFirst();
        if (user.isPresent()) {
            return user.get();
        }

        throw new NoUserByLoginException("No user with " + userLogin + " login");
    }
}
