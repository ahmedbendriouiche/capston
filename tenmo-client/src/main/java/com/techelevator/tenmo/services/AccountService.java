package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CustomerDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> ListAllUserAccounts();

    BigDecimal getUserGeneralBalance();
    BigDecimal getBalanceByAccount(long accountId);
}
