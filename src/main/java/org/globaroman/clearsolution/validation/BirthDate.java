package org.globaroman.clearsolution.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BirthDateValidator.class)
public @interface BirthDate {
    String message() default "Invalid format birth date.";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
