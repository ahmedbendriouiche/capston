package com.techelevator.tenmo.exceptionHandler;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAccountDao {
        @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An error occurred while accessing data");
        }
}
