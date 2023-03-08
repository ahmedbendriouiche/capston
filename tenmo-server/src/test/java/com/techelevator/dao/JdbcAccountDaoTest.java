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
public class JdbcAccountDaoTest extends BaseDaoTests{
    @Autowired
    JdbcAccountDao accountDao;
    @Autowired
    JdbcUserDao userDao;
    protected static final User USER_1 = new User(1001, "user1", "user1", "USER");
    protected static final User USER_2 = new User(1002, "user2", "user2", "USER");
    private static final User USER_3 = new User(1003, "user3", "user3", "USER");
    private final BigDecimal amountToSend = BigDecimal.valueOf(200.00);
    private final BigDecimal updatedBalance = BigDecimal.valueOf(1200.00);

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        accountDao = new JdbcAccountDao(jdbcTemplate);
        userDao = new JdbcUserDao(jdbcTemplate);
    }
    @Test
    public  void AccountGetBalance_Dao_test() throws Exception{
        // compare what is database with doa outcome
        userDao.create(USER_1.getUsername(),USER_1.getPassword());
        Assertions.assertEquals(1000,accountDao.getGeneralBalance(USER_1.getUsername()));
    }
    @Test
    public  void updateAccountBalance() throws Exception{


    }

}
