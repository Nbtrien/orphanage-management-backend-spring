package com.graduatebackend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NonVolunteerApplyException extends RuntimeException {
    public NonVolunteerApplyException() {
        super("You are not volunteer.");
    }
}
