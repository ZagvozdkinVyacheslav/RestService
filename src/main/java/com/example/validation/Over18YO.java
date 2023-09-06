package com.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = Custom18YOValidator.class)
public @interface Over18YO {
    String message() default "Citizen must be over 18 years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
