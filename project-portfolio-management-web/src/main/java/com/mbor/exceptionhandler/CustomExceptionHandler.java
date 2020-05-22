package com.mbor.exceptionhandler;

import com.mbor.exception.NoBRMAssignedToBusinessUnitException;
import com.mbor.exception.NoSetProjectManagerException;
import com.mbor.exception.ProjectRoleAlreadyExistException;
import com.mbor.exception.WrongProjectManagerException;
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

    @ExceptionHandler(ProjectRoleAlreadyExistException.class)
    protected ResponseEntity<Object> handleProjectRoleAlreadyExistException(RuntimeException exception, WebRequest webRequest){
        String responseBody = "message:" + exception.getMessage();
        return handleExceptionInternal(exception, responseBody,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(WrongProjectManagerException.class)
    protected ResponseEntity<Object> handleWrongProjectManagerException(RuntimeException exception, WebRequest webRequest){
        String responseBody = "message:" + exception.getMessage();
        return handleExceptionInternal(exception, responseBody,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(NoSetProjectManagerException.class)
    protected ResponseEntity<Object> handleNoSetProjectManagerException(RuntimeException exception, WebRequest webRequest){
        String responseBody = "message:" + exception.getMessage();
        return handleExceptionInternal(exception, responseBody,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(NoBRMAssignedToBusinessUnitException.class)
    protected ResponseEntity<Object> NoBRMAssignedToBusinessUnitException(RuntimeException exception, WebRequest webRequest){
        String responseBody = "message:" + exception.getMessage();
        return handleExceptionInternal(exception, responseBody,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }



}
