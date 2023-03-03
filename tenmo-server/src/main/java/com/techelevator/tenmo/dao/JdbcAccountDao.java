package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
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
    private BigDecimal sumBalance(List<Account> accounts){
        return accounts.isEmpty() ? null: accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
