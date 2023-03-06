package com.techelevator.tenmo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unable to initiate transaction. Insufficient funds " +
        "available in your account balance.")
public class InsufficientFundsException extends Exception {


    public InsufficientFundsException() {
        super();
    }

}
