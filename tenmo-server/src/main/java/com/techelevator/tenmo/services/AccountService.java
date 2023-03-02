package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> ListAllUserAccounts(String userName);
    BigDecimal getUserGeneralBalance(String userName);
    BigDecimal getBalanceByAccount(String userName, long accountId);
}
