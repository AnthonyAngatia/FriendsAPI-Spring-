package com.wiredbrain.friends.controller;

import com.wiredbrain.friends.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.ValidationException;

@ControllerAdvice
public class ControllerExceptionHandler {

//    @RequestBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ValidationException.class)
//    ErrorMessages exceptionHandler(ValidationException e) {
//        return new ErrorMessages("400", e.getMessage());
//    }
}
