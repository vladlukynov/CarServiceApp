package com.example.app.entity;

import java.util.Date;

public class Client {
    private final String userLogin;
    private final String pass;
    private final String email;
    private final String phoneNumber;
    private final int roleId;
    private final boolean isActive;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final Date birthday;

    public Client(String userLogin, String pass, String email, String phoneNumber, int roleId,
                  boolean isActive, String firstName, String lastName, String middleName,
                  Date birthday) {
        this.userLogin = userLogin;
        this.pass = pass;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Client{" +
                "userLogin='" + userLogin + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roleId=" + roleId +
                ", isActive=" + isActive +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
