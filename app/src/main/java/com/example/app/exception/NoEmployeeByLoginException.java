package com.example.app.exception;

public class NoEmployeeByLoginException extends Throwable {
    public NoEmployeeByLoginException(String message) {
        super(message);
    }
}
