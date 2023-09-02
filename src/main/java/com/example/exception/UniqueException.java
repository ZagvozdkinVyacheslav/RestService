package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UniqueException extends Throwable implements MyExceptionInterface{
    private String message;
    private final HttpStatus httpStatus = HttpStatus.valueOf(500);
    @Override
    public String getMessageByExc(){
        return message;
    }

    @Override
    public HttpStatus getHttpStatusByMyExc() {
        return httpStatus;
    }
}
