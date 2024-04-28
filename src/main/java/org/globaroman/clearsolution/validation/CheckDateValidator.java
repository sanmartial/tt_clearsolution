package org.globaroman.clearsolution.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class CheckDateValidator implements ConstraintValidator<CheckDate, Object> {
    private String fromDateField;
    private String toDateField;

    @Override
    public void initialize(CheckDate constraintAnnotation) {
        fromDateField = constraintAnnotation.from();
        toDateField = constraintAnnotation.to();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            LocalDate fromDate = (LocalDate) getFieldValueByFieldName(fromDateField, value);
            LocalDate toDate = (LocalDate) getFieldValueByFieldName(toDateField, value);
            return fromDate.isBefore(toDate);
        } catch (Exception e) {
            return false;
        }
    }

    private Object getFieldValueByFieldName(String name, Object value) {
        try {
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = value.getClass().getMethod(methodName);
            return method.invoke(value);
        } catch (Exception e) {
            throw new RuntimeException("Can't get access to field " + name, e);
        }
    }
}


