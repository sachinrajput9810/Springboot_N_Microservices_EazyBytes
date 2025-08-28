package com.eazybytes.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST , reason = "Customer Already Exist")
public class CustomerAlreadyExistException extends RuntimeException {

    public CustomerAlreadyExistException(String message){
        super(message);
    }

}
