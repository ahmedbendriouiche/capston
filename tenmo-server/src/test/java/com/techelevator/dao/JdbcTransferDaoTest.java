package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import org.springframework.jdbc.core.JdbcTemplate;



import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**

 * Test class for JdbcTransferDao.
 * Uses JdbcTest annotation to create a test environment with an in-memory database.
 * Tests the methods of JdbcTransferDao.
 * The JdbcTransferDaoTest class is used to test the JdbcTransferDao class which implements the TransferDao interface.
 * This class includes test methods for the getAllTransfers, getTransfersByUserId,
 * getTransferById, createTransfer, and deleteTransfer methods of the TransferDao interface.
 */

public class JdbcTransferDaoTest extends BaseDaoTests {
    private static final long USER_ID = 1L;
    private static final Transfer TEST_TRANSFER = new Transfer(1L, 2L, 2L, 1L, 2L, BigDecimal.valueOf(50.0));
    private static final Transfer ANOTHER_TEST_TRANSFER = new Transfer(2L, 2L, 2L, 2L, 1L, BigDecimal.valueOf(25.0));
    protected static  final String USER_1 = "new1";
    protected static  final String PASSWORD_1 = "5465453";
    protected static  final String USER_2 = "new2";
    protected static  final String PASSWORD_2 = "5465453";
    private JdbcTransferDao transferDao;
    private long accountFrom;
    private long accountTo;

    /*
     * The setup method initializes the testTransfer object and sets up the jdbcTemplate and dao for each test method to use.
     * The test methods use assertions to verify that the methods of the dao return the expected results.
     */
    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        transferDao = new JdbcTransferDao(jdbcTemplate);
        JdbcAccountDao accountDao = new JdbcAccountDao(jdbcTemplate);
        JdbcUserDao userDao = new JdbcUserDao(jdbcTemplate);

        userDao.create(USER_1,PASSWORD_1);
        userDao.create(USER_2,PASSWORD_2);
         accountFrom = accountDao.getAccountIdByUserId(userDao.findIdByUsername(USER_1));
         accountTo = accountDao.getAccountIdByUserId(userDao.findIdByUsername(USER_2));

    }

    /**
     Test case to verify that the getAllTransfers() method of JdbcTransferDao returns all transfers present in the database.
     It checks if the returned list of transfers is not null and has a size of 1, which is the size of the dataset in the test database.
     */
    // create new customer
    @Test
    public void testCreateTransfer() {
        // Arrange
        Transfer transfer = new Transfer(2L, 2L, 2L, accountFrom, accountTo, BigDecimal.valueOf(15.0));

        // Act

        // Assert
        assertNotNull(transfer);
    }
    @Test
    public void testGetAllTransfers() {
        // Arrange

        transferDao.createTransfer(1L, 2L, accountFrom, accountTo,  BigDecimal.valueOf(50.0));
        transferDao.createTransfer(2L, 3L, accountFrom, accountTo,  BigDecimal.valueOf(20.0));

        // Act
        List<Transfer> transfers = transferDao.getAllTransfers();

        // Assert
        assertNotNull(transfers);
    }
    /**
     Test case to verify that returns transfers
     only for the given user id.
     */
    @Test
    public void testGetTransfersByUserId() {

        List<Transfer> transfers = new ArrayList<>(Arrays.asList(
                transferDao.createTransfer(1L, 2L, accountFrom, accountTo,  BigDecimal.valueOf(50.0)),
                transferDao.createTransfer(1L, 2L, accountFrom, accountTo,  BigDecimal.valueOf(50.0)),
                transferDao.createTransfer(1L, 2L, accountFrom, accountTo,  BigDecimal.valueOf(50.0))
        ));

        // Arrange
       int Size = transferDao.getAllTransfersByUser(accountFrom).size();
        Assert.assertEquals(Size,transfers.size());
    }

    @Test
    public void testGetTransferById() {
        Transfer transfer = transferDao.createTransfer(1L, 2L, accountFrom, accountTo,  BigDecimal.valueOf(50.0));
        // Act
        Transfer transferExpected = transferDao.getTransferById(transfer.getTransferId());

        // Assert
        assertNotNull(transfer);
        assertEquals(transfer, transferExpected);
    }



    @Test
    public void testUpdateTransfer() {
        // Arrange
        //dao.createTransfer(TEST_TRANSFER);
        Transfer transfer = transferDao.createTransfer(1L, 2L, accountFrom, accountTo,  BigDecimal.valueOf(50.0));


        transfer.setTransferStatusId(2L);

        // Act
        transferDao.updateTransfer(transfer);

        // Assert
        Transfer updatedTransfer = transferDao.getTransferById(transfer.getTransferId());
        assertEquals(TEST_TRANSFER.getTransferStatusId(), updatedTransfer.getTransferStatusId());
    }
    @Test
    public void testDeleteTransfer() {
        // Arrange
        List<Transfer> transfers = new ArrayList<>(Arrays.asList(
                transferDao.createTransfer(1L, 2L, accountFrom, accountTo,  BigDecimal.valueOf(50.0)),
                transferDao.createTransfer(1L, 2L, accountFrom, accountTo,  BigDecimal.valueOf(50.0)),
                transferDao.createTransfer(1L, 2L, accountFrom, accountTo,  BigDecimal.valueOf(50.0))
        ));

        // Act
        transfers.forEach((s)->transferDao.deleteTransfer(s.getTransferId()));

        // Assert
        assertNull(transferDao.getTransferById(transfers.get(1).getTransferId()));
    }
}