package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CustomerBalanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class RestAccountService implements AccountService {


    public RestAccountService() {}
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;
    @Override
    public List<Account> ListAllUserAccounts(String userName) {
        return accountDao.accountsByUserName(userName);
    }

    @Override
    public CustomerBalanceDto getUserGeneralBalance(String userName) {
       return new CustomerBalanceDto(userDao.findByUsername(userName),
               accountDao.getGeneralBalance(userName));
    }

    @Override
    public CustomerBalanceDto getBalanceByAccount(String userName, long accountId) {
        return  new CustomerBalanceDto(userDao.findByUsername(userName),
                accountDao.getBalanceByAccount(userName,accountId));
    }
}
