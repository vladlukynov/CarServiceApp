package com.example.app.entity;

public class User {
    private String userLogin;
    private String pass;
    private String email;
    private String phoneNumber;
    private int roleId;
    private boolean isActive;

    public User() {
    }

    public User(String userLogin, String pass, String email, String phoneNumber, int roleId, boolean isActive) {
        this.userLogin = userLogin;
        this.pass = pass;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.isActive = isActive;
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

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "userLogin='" + userLogin + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roleId=" + roleId +
                ", isActive=" + isActive +
                '}';
    }
}
