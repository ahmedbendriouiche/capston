package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.services.RestAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;


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
        return accountService.ListAllUserAccounts(customer);
    }
    /* Get user general balance : sum up all user accounts balances (over all balance)*/
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/overallbalance")
    public ResponseEntity<Object> getBalance(@RequestParam String customer){
        return accountService
                .getUserGeneralBalance(customer);
    }

    // get user's balance for specific account
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/balanceByAccount")
    public ResponseEntity<Object> getBalanceByAccount(@RequestParam String customer,
                                                      @RequestParam long accountId){
        return accountService
                .getBalanceByAccount(customer,accountId);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/balance/transfer")
    public ResponseEntity<Object> accountBalanceUpdate(@RequestParam long from, @RequestParam long to,
                                                @RequestParam BigDecimal amount){
        return accountService.customerMoneyTransfer(from,to,amount);
    }
}
