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
    public ResponseEntity<Object> ListAllUserAccounts(String userName) {
        List<Account> accounts = accountDao.accountsByUserName(userName);
        if(accounts==null ){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error : Something wrong happened!");
        }
        return ResponseEntity.ok(accounts);
    }

    @Override
    public ResponseEntity<Object> getUserGeneralBalance(String userName) {
        User user = userDao.findByUsername(userName);
        BigDecimal balance = accountDao.getGeneralBalance(userName);
        if(user==null || balance==null ){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error : Something wrong happened!");
        }
        return ResponseEntity.ok(new CustomerBalanceDto(user,balance));
    }

    @Override
    public ResponseEntity<Object> getBalanceByAccount(String userName, long accountId) {

        User user = userDao.findByUsername(userName);
        BigDecimal balance = accountDao.getBalanceByAccount(userName,accountId);
        if(user==null || balance==null ){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error : Something wrong happened!");
        }
        return ResponseEntity.ok(new CustomerBalanceDto(user,balance));
    }

    @Override
    public ResponseEntity<Object> customerMoneyTransfer(long from, long to, BigDecimal amount) {
        User userFrom = userDao.getUserById(from);
        User userTo = userDao.getUserById(to);
        long currentUserAccountId = accountDao.getAccountIdByUserId(from);
        BigDecimal balance = accountDao.getBalanceByAccountId(currentUserAccountId);

        if(userTo==null || userFrom==null) {
            // make sure the user are exist
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("both or one of the users info " +
                    "not correct");
        } else if(balance.compareTo(amount) < 0) {
            //Sends error if current user has insufficient funds to send
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds");
        } else if(to == from){
            // transfer to self not allowed
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this operation can't be completed");
        } else if(amount.compareTo(BigDecimal.ZERO) < 0 || amount.equals(BigDecimal.ZERO)) {
            //Amount sent needs to be greater than 0
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount needs to be greater than 0");
        } else {
            //Tests for transfer success
            Boolean isMoneyTransferred = accountDao.accountsUpdate(from, to, amount);
            if (isMoneyTransferred) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("money was transferred");
            } else {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("money was not transferred due too internal issue error");
            }
        }
//        BigDecimal balance = accountDao.getGeneralBalance(userFrom.getUsername()).subtract(amount);
//        if(balance.compareTo(BigDecimal.ZERO)<0){
//            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("You can not overdraft your accounts");
    }

    @Override
    public ResponseEntity<Long> getAccountIdByUserId(long userId) {
        long accountId = accountDao.getAccountIdByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(accountId);
    }
}
