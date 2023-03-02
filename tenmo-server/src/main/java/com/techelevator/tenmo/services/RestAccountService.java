package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class RestAccountService implements AccountService {


    public RestAccountService() {}
    @Autowired
    private AccountDao accountDao;
    @Override
    public List<Account> ListAllUserAccounts(String userName) {
        return accountDao.accountsByUserName(userName);
    }

    @Override
    public BigDecimal getUserGeneralBalance(String userName) {
       return accountDao.getGeneralBalance(userName);
    }

    @Override
    public BigDecimal getBalanceByAccount(String userName, long accountId) {
        return accountDao.getBalanceByAccount(userName,accountId);
    }
}
