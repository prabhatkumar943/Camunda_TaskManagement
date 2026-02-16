package com.camunda.camundatele.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class ForBiddenException extends RuntimeException {
    public ForBiddenException(String message){
        super(message);
    }
}
