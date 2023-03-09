package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest(classes = TenmoApplication.class)
public class JdbcAccountDaoTest extends BaseDaoTests{
    protected static  final String USER_1 = "new1";
    protected static  final String PASSWORD_1 = "5465453";
    protected static  final String USER_2 = "new2";
    protected static  final String PASSWORD_2 = "5465453";
    private static final BigDecimal AMOUNT_SENT = BigDecimal.valueOf(200.00);
    private static final BigDecimal UPDATED_BALANCE = BigDecimal.valueOf(1200.00);
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(1000.00);

    private JdbcAccountDao accountDao;
    private JdbcUserDao userDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        accountDao = new JdbcAccountDao(jdbcTemplate);
        userDao = new JdbcUserDao(jdbcTemplate);
    }
    @Test
    public  void AccountGetBalance_Dao_test(){
        // compare what is in database with doa outcome
        userDao.create(USER_1,PASSWORD_1);
        Assert.assertEquals(INITIAL_BALANCE.compareTo(accountDao.getGeneralBalance(USER_1)),0);
    }
    @Test
    public  void transfer_funds_test() {
     // send fund
     userDao.create(USER_1,PASSWORD_1);
     userDao.create(USER_2,PASSWORD_2);
     long from = userDao.findIdByUsername(USER_1);
     long to = userDao.findIdByUsername(USER_2);
     Assert.assertTrue(accountDao.accountsUpdate(from,to, AMOUNT_SENT));
    }
    @Test
    public void overdraft_not_allowed_test(){
        userDao.create(USER_1,PASSWORD_1);
        userDao.create(USER_2,PASSWORD_2);
        long from = userDao.findIdByUsername(USER_1);
        long to = userDao.findIdByUsername(USER_2);
        Assert.assertFalse(accountDao.accountsUpdate(from,to, AMOUNT_SENT.multiply(AMOUNT_SENT)));
    }

}
