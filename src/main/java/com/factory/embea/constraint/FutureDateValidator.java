package com.factory.embea.constraint;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
        try {
            var givenDate = LocalDate.parse(valueToValidate, dateTimeFormatter);
            var today = LocalDate.now();
            return !givenDate.isBefore(today);
        } catch (Exception e) {
            log.error("Error : {}", e);
            return false;
        }
    }
}
