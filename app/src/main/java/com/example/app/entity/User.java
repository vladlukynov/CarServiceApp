package com.example.app.entity;

public class User {
    private String userLogin;
    private String pass;
    private String email;
    private String phoneNumber;
    private int roleId;

    public User() {
    }

    public User(String userLogin, String pass, String email, String phoneNumber, int roleId) {
        this.userLogin = userLogin;
        this.pass = pass;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getRoleId() {
        return roleId;
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
