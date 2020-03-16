package com.mbor.exceptionhandler;

import com.mbor.exception.ProjectRoleAlreadyExist;
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
    protected ResponseEntity<Object> handleNoResultException(RuntimeException exception, WebRequest webRequest){
        String responseBody = "message:" + exception.getMessage();
        return handleExceptionInternal(exception, responseBody,
                new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(ProjectRoleAlreadyExist.class)
    protected ResponseEntity<Object> handleProjectRoleAlreadyExistException(RuntimeException exception, WebRequest webRequest){
        String responseBody = "message:" + exception.getMessage();
        return handleExceptionInternal(exception, responseBody,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }
}
