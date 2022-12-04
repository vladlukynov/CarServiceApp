package com.example.app.entity;

import java.time.LocalDate;

public class Employee extends User {
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String post;
    private final double salary;
    private final LocalDate birthday;

    public Employee(String userLogin, String pass, String email, String phoneNumber, int roleId,
                    boolean isActive, String firstName, String lastName, String middleName,
                    String post, double salary, LocalDate birthday) {
        super(userLogin, pass, email, phoneNumber, roleId, isActive);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.post = post;
        this.salary = salary;
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

    public String getPost() {
        return post;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userLogin='" + super.getUserLogin() + '\'' +
                ", pass='" + super.getPass() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", phoneNumber='" + super.getPhoneNumber() + '\'' +
                ", roleId=" + super.getRoleId() +
                ", isActive=" + super.isActive() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", post='" + post + '\'' +
                ", salary=" + salary +
                ", birthday=" + birthday +
                '}';
    }
}
