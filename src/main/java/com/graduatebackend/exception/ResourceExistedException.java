package com.graduatebackend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceExistedException extends RuntimeException {
    private String code;

    public ResourceExistedException() {
        super("This email address is already in use. Please use another email address.");
    }

    public ResourceExistedException(String msg) {
        super(msg);
    }

    public ResourceExistedException(String msg, String code) {
        super(msg);
        this.code = code;
    }

}
