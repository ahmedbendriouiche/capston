package com.techelevator.tenmo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input. Please search using either the " +
        "status description or the status id.")
public class InvalidParameterException extends Exception{


    public InvalidParameterException() {
        super();
    }

}
