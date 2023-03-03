package com.techelevator.tenmo.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice

public class ExceptionHandlerAccountController {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e){
        String msg = "you are not authorized to access this resource";
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(msg);
    }
}
