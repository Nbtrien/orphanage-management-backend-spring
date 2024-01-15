package com.graduatebackend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyEventException extends RuntimeException {
    private String code;

    public ApplyEventException() {
        super("Apply event exception.");
    }

    public ApplyEventException(String msg) {
        super(msg);
    }

    public ApplyEventException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
