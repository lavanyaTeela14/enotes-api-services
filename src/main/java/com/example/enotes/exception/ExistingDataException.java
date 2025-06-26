package com.example.enotes.exception;

public class ExistingDataException extends RuntimeException{
    public ExistingDataException(String message) {
        super(message);
    }
}
