package com.mbor.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmployeeAssignmentValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeAssignment {

    String message() default "Bad params for Director and Supervisor";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
