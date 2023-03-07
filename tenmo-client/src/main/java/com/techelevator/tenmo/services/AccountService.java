package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CustomerBalanceResponse;
import com.techelevator.tenmo.model.CustomerDto;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> ListAllUserAccounts();

    CustomerBalanceResponse getUserGeneralBalance();
    CustomerBalanceResponse getBalanceByAccount(long accountId);
    boolean accountBalanceUpdate(long to, long from,BigDecimal amount);
    List<User> listAllCustomer();
}
