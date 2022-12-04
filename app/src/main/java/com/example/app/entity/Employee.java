package com.example.app.entity;

import java.time.LocalDate;

public class Employee {
    private final String userLogin;
    private final String pass;
    private final String email;
    private final String phoneNumber;
    private final int roleId;
    private final boolean isActive;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String post;
    private final double salary;
    private final LocalDate birthday;

    public Employee(String userLogin, String pass, String email, String phoneNumber, int roleId,
                    boolean isActive, String firstName, String lastName, String middleName,
                    String post, double salary, LocalDate birthday) {
        this.userLogin = userLogin;
        this.pass = pass;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.post = post;
        this.salary = salary;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userLogin='" + userLogin + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roleId=" + roleId +
                ", isActive=" + isActive +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", post='" + post + '\'' +
                ", salary=" + salary +
                ", birthday=" + birthday +
                '}';
    }
}
