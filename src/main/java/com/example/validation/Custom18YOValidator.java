package com.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.time.LocalDate;


public class Custom18YOValidator implements ConstraintValidator<Over18YO,String> {
    private Over18YO over18YO;

    @Override
    public void initialize(Over18YO over18YO) {
        ConstraintValidator.super.initialize(over18YO);
        this.over18YO = over18YO;
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext constraintValidatorContext) {
        if(contactField == null)return true;
        LocalDate currentDate =  LocalDate.now();
        currentDate = currentDate.minusDays(Integer.parseInt(contactField.substring(0,2)));
        currentDate = currentDate.minusMonths(Integer.parseInt(contactField.substring(3,5)));
        currentDate = currentDate.minusYears(Integer.parseInt(contactField.substring(6,10)));
        if(currentDate.getYear() >= 18)return true;
        return false;

    }
}
