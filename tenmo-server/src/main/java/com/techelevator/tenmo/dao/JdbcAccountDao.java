package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal Balance(String userName) {
        return sumBalance(accountsByUserName(userName));
    }
    @Override
    public List<Account> accountsByUserName(String userName) {
        String sql ="select * FROM account as a " +
                "join tenmo_user as tu on tu.user_id = a.user_id " +
                "where tu.username = ? ";
        List<Account> accounts = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userName);
            while (results.next()) {
                accounts.add(mapRowToUser(results));
            }
            return accounts;
        } catch (Exception e ){
            return null;
        }
    }
    private BigDecimal sumBalance(List<Account> accounts){
        return accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private Account mapRowToUser(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setUserId(rs.getLong("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}
