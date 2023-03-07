package com.techelevator.tenmo.services;

import org.springframework.http.ResponseEntity;

public interface CustomerService {

    ResponseEntity<Object> ListAllCustomer();
}
