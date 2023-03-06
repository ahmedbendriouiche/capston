package com.techelevator.tenmo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such status.")
public class TransferStatusNotFoundException extends Exception {


    public TransferStatusNotFoundException() {
        super();
    }

}
