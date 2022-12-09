package com.example.app.service;

import com.example.app.entity.Client;
import com.example.app.entity.Employee;
import com.example.app.entity.User;
import com.example.app.exception.NoUserByLoginException;
import com.example.app.repository.ClientRepository;
import com.example.app.repository.EmployeeRepository;
import com.example.app.repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final ClientRepository clientRepository = new ClientRepository();

    public List<User> getUsers() throws SQLException {
        return userRepository.getUsers();
    }

    public User getUser(String userLogin) throws SQLException, NoUserByLoginException {
        List<Client> clients = clientRepository.getClientsInfo();
        List<Employee> employees = employeeRepository.getEmployeesInfo();
        List<User> users = new ArrayList<>();
        users.addAll(clients);
        users.addAll(employees);

        Optional<User> user = users.stream().filter(user_ -> user_.getUserLogin().equals(userLogin)).findFirst();
        if (user.isPresent()) {
            return user.get();
        }

        throw new NoUserByLoginException("No user with " + userLogin + " login");
    }

    public void activateUser(String userLogin) throws SQLException {
        userRepository.changeUserStatus(userLogin, 1);
    }

    public void deactivateUser(String userLogin) throws SQLException {
        userRepository.changeUserStatus(userLogin, 0);
    }
}
