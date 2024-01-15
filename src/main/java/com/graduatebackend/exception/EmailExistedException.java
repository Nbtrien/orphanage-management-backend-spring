package com.graduatebackend.exception;

public class EmailExistedException extends RuntimeException{
    public EmailExistedException() {
        super("This email address is already in use. Please use another email address.");
    }
}

