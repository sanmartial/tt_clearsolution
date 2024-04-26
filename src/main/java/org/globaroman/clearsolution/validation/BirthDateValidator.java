package org.globaroman.clearsolution.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class BirthDateValidator implements ConstraintValidator<BirthDate, String> {

    private static final String PATTERN_OF_BIRTH_DATE =
            "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)\\d\\d$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null
                && Pattern.compile(PATTERN_OF_BIRTH_DATE).matcher(s).matches()
                && isLaterCurrentDate(s);
    }

    private boolean isLaterCurrentDate(String s) {

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(s, formatDate);

        return date.compareTo(LocalDate.now()) < 0;
    }
}
