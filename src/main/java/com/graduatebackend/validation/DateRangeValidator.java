package com.graduatebackend.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;

public class DateRangeValidator implements ConstraintValidator<DateRange, Date> {
    private Date minDate;
    private Date maxDate;

    @Override
    public void initialize(DateRange constraint) {
        minDate = Date.valueOf(constraint.minDate());
        maxDate = Date.valueOf(constraint.maxDate());
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.compareTo(minDate) >= 0 && value.compareTo(maxDate) <= 0;
    }
}
