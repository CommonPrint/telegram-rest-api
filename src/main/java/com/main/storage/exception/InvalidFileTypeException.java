package com.main.storage.exception;

import lombok.Data;

@Data
public class InvalidFileTypeException extends RuntimeException{

    private final String message;

    public InvalidFileTypeException(String message) {
        super(message);
        this.message = message;
    }
}
