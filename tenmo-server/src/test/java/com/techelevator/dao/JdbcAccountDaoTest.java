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
    @Test
    public  void AccountGetBalance_Dao_test() throws Exception{
        // compare what is database with doa outcome
        Assertions.assertEquals(0, accountDao.getGeneralBalance("user").
                compareTo(BigDecimal.valueOf(1000.00)));
    }


}
