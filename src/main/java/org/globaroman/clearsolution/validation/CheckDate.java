package org.globaroman.clearsolution.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckDateValidator.class)
public @interface CheckDate {
    String message() default "fromDate must be less than toDate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String from();
    String to();
}
