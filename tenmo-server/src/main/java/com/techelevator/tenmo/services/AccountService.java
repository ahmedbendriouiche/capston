package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CustomerBalanceDto;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    ResponseEntity<Object> ListAllUserAccounts(String userName);
   ResponseEntity<Object> getUserGeneralBalance(String userName);
    ResponseEntity<Object>getBalanceByAccount(String userName, long accountId);
    ResponseEntity<Object> customerMoneyTransfer(long to, long from, BigDecimal amount);
}
