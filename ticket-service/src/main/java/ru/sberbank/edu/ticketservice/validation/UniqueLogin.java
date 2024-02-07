package ru.sberbank.edu.ticketservice.validation;

import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;

@Target( {FIELD} )
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueLoginValidator.class)
@Documented
public @interface UniqueLogin {
    String message() default "login already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
