package com.example.app.entity;

import java.time.LocalDate;

public class Client {
    private final String userLogin;
    private final String pass;
    private final String email;
    private final String phoneNumber;
    private int roleId;
    private boolean isActive;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final LocalDate birthday;

    public Client(String userLogin, String pass, String email, String phoneNumber,
                  String firstName, String lastName, String middleName,
                  LocalDate birthday) {
        this.userLogin = userLogin;
        this.pass = pass;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
    }

    public Client(String userLogin, String pass, String email, String phoneNumber, int roleId,
                  boolean isActive, String firstName, String lastName, String middleName,
                  LocalDate birthday) {
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

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getPass() {
        return pass;
    }

    public int getRoleId() {
        return roleId;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public LocalDate getBirthday() {
        return birthday;
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
