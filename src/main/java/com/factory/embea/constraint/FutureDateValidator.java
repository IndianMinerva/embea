package com.factory.embea.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
        try {
            var givenDate = LocalDate.parse(valueToValidate, dateTimeFormatter);
            var today = LocalDate.now();
            System.out.println(givenDate + "    " + today + "      " + valueToValidate);
            System.out.println("HELLO   " + !givenDate.isBefore(today));
            return !givenDate.isBefore(today);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
            return false;
        }
    }
}
