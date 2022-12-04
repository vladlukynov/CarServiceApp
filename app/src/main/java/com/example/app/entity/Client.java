package com.example.app.entity;

import java.time.LocalDate;

public class Client extends User {
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final LocalDate birthday;

    public Client(String userLogin, String pass, String email, String phoneNumber,
                  String firstName, String lastName, String middleName,
                  LocalDate birthday) {
        super(userLogin, pass, email, phoneNumber, 3, true);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
    }

    public Client(String userLogin, String pass, String email, String phoneNumber, int roleId,
                  boolean isActive, String firstName, String lastName, String middleName,
                  LocalDate birthday) {
        super(userLogin, pass, email, phoneNumber, roleId, isActive);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
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
                "userLogin='" + super.getUserLogin() + '\'' +
                ", pass='" + super.getPass() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", phoneNumber='" + super.getPhoneNumber() + '\'' +
                ", roleId=" + super.getRoleId() +
                ", isActive=" + super.isActive() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
