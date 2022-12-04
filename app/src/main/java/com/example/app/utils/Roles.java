package com.example.app.utils;

import com.example.app.exception.NoRoleByIdException;

public class Roles {
    public static String getRoleName(int roleId) throws NoRoleByIdException {
        switch (roleId) {
            case 1 -> {
                return "Администратор";
            }
            case 2 -> {
                return "Сотрудник";
            }
            case 3 -> {
                return "Клиент";
            }
        }
        throw new NoRoleByIdException("Not found role by id " + roleId);
    }
}
