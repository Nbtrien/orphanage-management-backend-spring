package com.graduatebackend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRange {
    String message() default "Invalid date range";

    String minDate();

    String maxDate();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
