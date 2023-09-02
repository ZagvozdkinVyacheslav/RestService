package com.example.exception;

import org.springframework.http.HttpStatus;

public interface MyExceptionInterface {
    public String getMessageByExc();
    public HttpStatus getHttpStatusByMyExc();
}
