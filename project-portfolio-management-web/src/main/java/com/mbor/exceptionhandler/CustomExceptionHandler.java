package com.mbor.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoResultException.class)
    protected ResponseEntity<Object> handleNeResultException(RuntimeException exception, WebRequest webRequest){
        String bodyOfResponse = "message:" + exception.getMessage();
        return handleExceptionInternal(exception, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

}
