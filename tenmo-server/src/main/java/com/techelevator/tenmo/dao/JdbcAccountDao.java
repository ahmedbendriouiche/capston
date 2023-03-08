package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Account> ListAllOtherAccounts(String userName) {
        String sql ="select u.* FROM tenmo_user as u USING(user_id) where u.username != ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class), userName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Boolean accountsUpdate(long to, long from, BigDecimal amount) {
        return transferFunds(from,to,amount);
    }

    private boolean transferFunds(long from, long to, BigDecimal amount)  {
        String sql="BEGIN TRANSACTION;\n" +
                " UPDATE account  SET balance = (balance - ?) WHERE user_id = ?;\n" +
                " UPDATE account SET balance = (balance + ?) WHERE user_id = ?;\n" +
                "COMMIT;";
        try  {
            Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().setAutoCommit(false);
            jdbcTemplate.execute((ConnectionCallback<Integer>) con -> {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setBigDecimal(1, amount);
                pstmt.setLong(2,from);
                pstmt.setBigDecimal(3, amount);
                pstmt.setLong(4, to);
                return pstmt.executeUpdate();
            });
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            try {
                Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().rollback();
            }catch (SQLException ex){
                System.err.println(ex.getMessage());
            }
            return false;
        }finally {
            try {
                Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().setAutoCommit(true);
            }catch (SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
}

    private BigDecimal sumBalance(List<Account> accounts){
        return accounts.isEmpty() ? null: accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public long getAccountIdByUserId(long userId) {
        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if (result.next()) {
            return result.getLong("account_id");
        } else {
            throw new RuntimeException("No account found for user with ID " + userId);
        }
    }
}
