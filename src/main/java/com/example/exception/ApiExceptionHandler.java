package com.example.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleValidationConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})//Ошибка чтения тела запроса - 422 и сообщение об ошибке;
    public ResponseEntity<Object> handleValidationExceptions(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>("Произошла ошибка чтения тела запроса.", HttpStatus.valueOf(422));
    }
    //custom exceptions
    @ExceptionHandler({UniqueException.class, NotFindException.class})//this classes implements MyExceptionInterface
    public ResponseEntity<Object> handleValidationExceptions(MyExceptionInterface ex) {
        return new ResponseEntity<>(ex.getMessageByExc(),ex.getHttpStatusByMyExc());
    }
    @ExceptionHandler({Exception.class})//Ошибка сервера - 500 и сообщение об ошибке;
    public ResponseEntity<Object> handleValidationAllException(Exception ex) {
        return new ResponseEntity<>("Произошла ошибка работы сервера", HttpStatus.valueOf(500));
    }
}
