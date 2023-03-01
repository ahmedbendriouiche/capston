package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.jboss.logging.BasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {
    @Autowired
    private AccountDao accountDao;
   // Get all user accounts
   @GetMapping()
   @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Account>> getAccounts(Principal principal){
        if(principal == null){
            //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "can't retrieve user credential");
        }
        List<Account> accounts = accountDao.accountsByUserName(principal.getName());
        if (accounts == null || accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(accounts);
        }
    }

    // Get user general balance : sum up all user accounts balances (over all balance)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(Principal principal){
        if(principal == null){
           //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "can't retrieve user credential");
        }
        BigDecimal balance = accountDao.getGeneralBalance(principal.getName());
        if(balance==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(balance);
    }

    // get user's balance for specific account
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/balance/{accountId}")
    public ResponseEntity<BigDecimal> getBalanceByAccount(Principal principal, @PathVariable long accountId){
        if(principal == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        BigDecimal balance = accountDao.getBalanceByAccount(principal.getName(),accountId);
        if(balance==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(balance);
    }
}
