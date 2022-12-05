package com.example.app.exception;

public class NoCarByIdException extends Throwable {
    public NoCarByIdException(String message) {
        super(message);
    }
}
