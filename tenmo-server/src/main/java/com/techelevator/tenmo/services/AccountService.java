package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CustomerBalanceDto;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> ListAllUserAccounts(String userName);
    CustomerBalanceDto getUserGeneralBalance(String userName);
    CustomerBalanceDto getBalanceByAccount(String userName, long accountId);
    ResponseEntity<Object> customerMoneyTransfer(long to, long from, BigDecimal amount);
}
