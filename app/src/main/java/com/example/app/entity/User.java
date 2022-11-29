package com.example.app.entity;

public class User {
    private final String userLogin;
    private final String pass;

    public User(String userLogin, String pass) {
        this.userLogin = userLogin;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "userLogin='" + userLogin + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
