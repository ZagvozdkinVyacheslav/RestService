package com.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Custom18YONotNullValidator implements ConstraintValidator<Over18YONotNull,String> {
    private Over18YONotNull over18YONotNull;
    @Override
    public void initialize(Over18YONotNull over18YONotNull) {
        ConstraintValidator.super.initialize(over18YONotNull);
        this.over18YONotNull = over18YONotNull;
    }
    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Date birthDateFormatDate = new Date(Integer.parseInt(contactField.substring(0, 4)),
                    Integer.parseInt(contactField.substring(5, 7)), Integer.parseInt(contactField.substring(8, 10)));

            String curentDateString = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

            Date currentDate = new Date(Integer.parseInt(curentDateString.substring(0, 4)),
                    Integer.parseInt(curentDateString.substring(4, 6)), Integer.parseInt(curentDateString.substring(6, 8)));
            long diffInMillies = Math.abs(currentDate.getTime() - birthDateFormatDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if(diff / 365 >= 18)return true;
            return false;
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }catch (NullPointerException e){
            return false;
        }

    }
}
