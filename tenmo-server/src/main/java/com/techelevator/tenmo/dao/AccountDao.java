package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    BigDecimal getGeneralBalance(String userName);
    BigDecimal getBalanceByAccount(String userName, Long AccountId);
    List<Account> accountsByUserName(String userName);
    List<Account> ListAllOtherAccounts(String userName);
<<<<<<< HEAD
    Boolean accountsUpdate(long from, long to, BigDecimal amount);
=======
    Boolean accountsUpdate(long to, long from, BigDecimal amount);

    long getAccountIdByUserId(long userId);
>>>>>>> 395bb149c162c1bff7ad524d02f1182cd314b1f8
}
