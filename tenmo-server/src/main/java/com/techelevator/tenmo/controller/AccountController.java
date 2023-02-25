package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountDao accountDao;


    @GetMapping()
    public ResponseEntity<List<Account>> getAccounts(Principal principal){
        List<Account> accounts = accountDao.accountsByUserName(principal.getName());
        if (accounts == null || accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(accounts);
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(Principal principal){
        BigDecimal balance = accountDao.Balance(principal.getName());
        if(balance==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(balance);
    }
}
