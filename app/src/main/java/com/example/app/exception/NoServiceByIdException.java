package com.example.app.exception;

public class NoServiceByIdException extends Throwable {
    public NoServiceByIdException(String message) {
        super(message);
    }
}
