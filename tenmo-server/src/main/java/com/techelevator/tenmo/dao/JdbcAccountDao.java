package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getGeneralBalance(String userName) {
        return sumBalance(accountsByUserName(userName));
    }
    @Override
    public BigDecimal getBalanceByAccount(String userName,Long accountId){
        String sql ="select a.balance  FROM account as a  join tenmo_user as u USING(user_id) where u.username = ? and " +
                "a.account_id = ? ";
        BigDecimal balance = null;
        try {
            balance= jdbcTemplate.queryForObject(sql, new Object[]{userName, accountId}, BigDecimal.class);
        }catch (Exception e){
            return null;
        }
        return balance;
    }
    @Override
    public List<Account> accountsByUserName(String userName) {
        String sql ="select a.* FROM account AS a join tenmo_user as u USING(user_id) where u.username = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class), userName);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Account> ListAllOtherAccounts(String userName) {
        String sql ="select u.* FROM tenmo_user as u USING(user_id) where u.username != ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class), userName);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Boolean accountsUpdate(long to, long from, BigDecimal amount) {
        return moneyTransfer(to,from,amount);
    }

    private boolean moneyTransfer(long to, long from, BigDecimal amount){
        String sql="BEGIN TRANSACTION;\n" +
                " UPDATE account  SET balance = (balance + ?) WHERE account_id = ?;\n" +
                " UPDATE account SET balance = (balance - ?) WHERE account_id = ?;\n" +
                "COMMIT;";
        try {
            jdbcTemplate.update(sql,amount,to,amount,from);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private BigDecimal sumBalance(List<Account> accounts){
        return accounts.isEmpty() ? null: accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
