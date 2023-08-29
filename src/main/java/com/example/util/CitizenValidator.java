package com.example.util;

import com.example.database.entity.Citizen;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CitizenValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Citizen.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Citizen citizen = (Citizen)target;
    }
}
