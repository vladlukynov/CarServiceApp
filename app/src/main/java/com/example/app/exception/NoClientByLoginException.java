package com.example.app.exception;

public class NoClientByLoginException extends Throwable {
    public NoClientByLoginException(String message) {
        super(message);
    }
}
