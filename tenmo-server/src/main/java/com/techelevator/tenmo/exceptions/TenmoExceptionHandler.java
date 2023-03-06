package com.techelevator.tenmo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TenmoExceptionHandler {

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access denied.")
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedExceptionHandler(AccessDeniedException e) {}
}
