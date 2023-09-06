package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class UniqueException extends Exception{
    private String message;
    private final HttpStatus httpStatus = HttpStatus.valueOf(500);
}
