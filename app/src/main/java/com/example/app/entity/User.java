package com.example.app.entity;

public class User {
    private final String userLogin;
    private final String pass;
    private final String email;
    private final String phoneNumber;

    public User(String userLogin, String pass, String email, String phoneNumber) {
        this.userLogin = userLogin;
        this.pass = pass;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userLogin='" + userLogin + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
