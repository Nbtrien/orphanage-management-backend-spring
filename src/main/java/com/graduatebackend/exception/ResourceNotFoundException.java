package com.graduatebackend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private String code;

    public ResourceNotFoundException() {
        this("Resource not found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }
}
