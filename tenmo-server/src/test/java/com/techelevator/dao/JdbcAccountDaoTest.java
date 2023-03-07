package com.techelevator.dao;

import com.techelevator.tenmo.TenmoApplication;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
@SpringBootTest(classes = TenmoApplication.class)
public class JdbcAccountDaoTest{
    @Autowired
    JdbcAccountDao accountDao;
    @Autowired
    JdbcUserDao userDao;
    private final String USER1="myUser1";
    private final String USER2="myUser1";
    private final String password1 ="myUserPass1";
    private final String password2 ="myUserPass2";
    private final BigDecimal amountToSend = BigDecimal.valueOf(200.00);
    private final BigDecimal updatedBalance = BigDecimal.valueOf(1200.00);
    @Test
    public  void AccountGetBalance_Dao_test() throws Exception{
        userDao.create(USER1,password2);
        // compare what is database with doa outcome
        Assertions.assertEquals(0, accountDao.getGeneralBalance(USER1).
                compareTo(BigDecimal.valueOf(1000.00)));
    }
    @Test
    public  void updateAccountBalance() throws Exception{
        userDao.create(USER1,password2);
        userDao.create(USER2,password2);

    }

}
