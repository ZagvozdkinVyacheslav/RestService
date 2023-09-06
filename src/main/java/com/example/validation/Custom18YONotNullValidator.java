package com.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class Custom18YONotNullValidator implements ConstraintValidator<Over18YONotNull,String> {
    private Over18YONotNull over18YONotNull;
    @Override
    public void initialize(Over18YONotNull over18YONotNull) {
        ConstraintValidator.super.initialize(over18YONotNull);
        this.over18YONotNull = over18YONotNull;
    }
    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate currentDate =  LocalDate.now();
        currentDate = currentDate.minusDays(Integer.parseInt(contactField.substring(0,2)));
        currentDate = currentDate.minusMonths(Integer.parseInt(contactField.substring(3,5)));
        currentDate = currentDate.minusYears(Integer.parseInt(contactField.substring(6,10)));
        if(currentDate.getYear() >= 18)return true;
        return false;
    }
}
