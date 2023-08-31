package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class NotFindException extends Throwable implements MyExceptionInterface {

    private final String message = "По данным параметрам пользователь не найден";
    private final HttpStatus httpStatus = HttpStatus.valueOf(400);
    @Override
    public String getMessageByExc(){
        return message;
    }
    @Override
    public HttpStatus getHttpStatusByMyExc() {
        return httpStatus;
    }

}