package com.example.app.service;

import com.example.app.entity.Role;
import com.example.app.repository.RoleRepository;

import java.sql.SQLException;

public class RoleService {
    private final RoleRepository roleRepository = new RoleRepository();

    public Role getRoleByName(String roleName) throws SQLException {
        return roleRepository.getRoleByName(roleName);
    }

    public Role getRoleById(int roleId) throws SQLException {
        return roleRepository.getRoleById(roleId);
    }
}
