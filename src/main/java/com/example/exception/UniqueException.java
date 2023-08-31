package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UniqueException extends Throwable{
    private final String message = "Пользователь с введенными вами данными уже существует";
    private final HttpStatus httpStatus = HttpStatus.valueOf(500);
    private final ZonedDateTime dateTime;

}
