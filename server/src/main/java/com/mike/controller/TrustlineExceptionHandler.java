package com.mike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mike.exception.UserNotFoundException;
import com.mike.exception.UsersAreSameException;

@ControllerAdvice
public class TrustlineExceptionHandler extends ResponseEntityExceptionHandler {
    
    private Logger logger = LoggerFactory.getLogger(TrustlineExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e, WebRequest webReqeust) {
        logger.debug("Failed to process {} user not found", e.getTransaction());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UsersAreSameException.class)
    public final ResponseEntity<String> handleUsersAreSameException(UsersAreSameException e, WebRequest request) {
        logger.debug("Failed to process {} to and from users are the same", e.getTransaction());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
