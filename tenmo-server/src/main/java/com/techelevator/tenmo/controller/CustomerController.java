package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@PreAuthorize("isAuthenticated()")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @GetMapping("/customers/listall")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public  ResponseEntity<Object> getAllCustomers(){
        return customerService.ListAllCustomer();
    }
}
