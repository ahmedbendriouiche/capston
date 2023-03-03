package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CustomerBalanceDto;
import com.techelevator.tenmo.model.CustomerRequestDto;
import com.techelevator.tenmo.services.RestAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {
    @Autowired
    private RestAccountService accountService;
   // Get all user accounts
   @GetMapping("/{customer}")
   @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Object> getAccounts(@PathVariable String customer){
        List<Account> accounts = accountService.ListAllUserAccounts(customer);
        if (accounts == null || accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Accounts not founds for customer '"+ customer+
                            "' Please verify customer inputs");
        } else {
            return ResponseEntity.ok(accounts);
        }
    }
    /* Get user general balance : sum up all user accounts balances (over all balance)*/
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/overallbalance")
    public ResponseEntity<Object> getBalance(@RequestParam String customer){
        CustomerBalanceDto customerBalanceDto = accountService
                .getUserGeneralBalance(customer);
        if(customerBalanceDto==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Balance found for customer '"+ customer
                            +"' Please verify customer inputs");
        }
        return ResponseEntity.ok(customerBalanceDto);
    }

    // get user's balance for specific account
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/balanceByAccount")
    public ResponseEntity<Object> getBalanceByAccount(@RequestParam String customer,
                                                      @RequestParam long accountId){

        CustomerBalanceDto customerBalanceDto = accountService.
                getBalanceByAccount(customer,accountId);
        if(customerBalanceDto==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Balance Not found for customer '"+ customer
            +"' Please verify customer inputs");
        }
        return ResponseEntity.ok(customerBalanceDto);
    }

}
