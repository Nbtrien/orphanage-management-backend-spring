package com.graduatebackend.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException() {
        super("This user name is already in use. Please use another user name.");
    }
}
