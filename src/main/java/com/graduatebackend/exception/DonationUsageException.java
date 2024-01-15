package com.graduatebackend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationUsageException extends RuntimeException {
    private String code;

    public DonationUsageException() {
        super("Donation usage exception.");
    }

    public DonationUsageException(String msg) {
        super(msg);
    }

    public DonationUsageException(String msg, String code) {
        super(msg);
        this.code = code;
    }

}
