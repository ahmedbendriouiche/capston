package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CustomerBalanceDto;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<Object> customerMoneyTransfer(long to, long from, BigDecimal amount) {
        User userTo = userDao.getUserById(to);
        User userFrom = userDao.getUserById(from);
        if(userTo==null || userFrom==null) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("both or one of the users info " +
                    "not correct");
        }
        if(to==from){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this operation can't be completed");
        }
        Boolean isMoneyTransferred = accountDao.accountsUpdate(to,from,amount);
        if(isMoneyTransferred){
            return  ResponseEntity.status(HttpStatus.ACCEPTED).body("money was transferred");
        }else {
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("money was not transferred due too internal issue error");
        }
    }

    @Override
    public ResponseEntity<Long> getAccountIdByUserId(long userId) {
        long accountId = accountDao.getAccountIdByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(accountId);
    }
}
