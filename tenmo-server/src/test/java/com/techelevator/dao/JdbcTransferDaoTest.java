package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;

import com.techelevator.tenmo.dao.JdbcUserDao;


import com.techelevator.tenmo.model.Transfer;

import org.junit.Before;

import org.junit.Test;


import org.springframework.jdbc.core.JdbcTemplate;



import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;


import java.util.List;


import static org.junit.Assert.*;

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

        jdbcTemplate = new JdbcTemplate(dataSource);
        transferDao = new JdbcTransferDao(jdbcTemplate);
    }

    /**
     Test case to verify that the getAllTransfers() method of JdbcTransferDao returns all transfers present in the database.
     It checks if the returned list of transfers is not null and has a size of 1, which is the size of the dataset in the test database.
     */
    // create new customer


    @Test
    public void testGetAllTransfers() {
        // Arrange

        transferDao.createTransfer(1L, 2L, accountFrom, accountTo, BigDecimal.valueOf(50.0));
        transferDao.createTransfer(2L, 3L, accountFrom, accountTo, BigDecimal.valueOf(20.0));

        // Act
        List<Transfer> transfers = transferDao.getAllTransfers();

        // Assert
        assertNotNull(transfers);
    }


    /**
     * Test case to verify that the getTransferById() method of JdbcTransferDao returns the transfer with the specified ID.
     * It creates a transfer in the database, retrieves it by ID, and compares it to the expected transfer.
     */
    @Test
    public void testGetTransfersByUserId() {
            List<Transfer> transfers = new ArrayList<>(Arrays.asList(
                    transferDao.createTransfer(1L, 2L, accountFrom, accountTo, BigDecimal.valueOf(50.0)),
                    transferDao.createTransfer(1L, 2L, accountFrom, accountTo, BigDecimal.valueOf(50.0)),
                    transferDao.createTransfer(1L, 2L, accountFrom, accountTo, BigDecimal.valueOf(50.0))
            ));

            // Arrange
            int Size = transferDao.getAllTransfersByUser(accountFrom).size();
            assertEquals(Size, transfers.size());
        }

    /**
     * Tests the getAllTransfersByUser method of the TransferDAO implementation by verifying that it returns
     * a list of Transfer objects that includes all transfers involving a given account ID.
     */
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
        Transfer transfer = transferDao.createTransfer(1L, 2L, accountFrom, accountTo, BigDecimal.valueOf(50.0));


        transfer.setTransferStatusId(2L);

        // Act
        transferDao.updateTransfer(transfer);

        // Assert
        Transfer updatedTransfer = transferDao.getTransferById(transfer.getTransferId());
        assertEquals(TEST_TRANSFER.getTransferStatusId(), updatedTransfer.getTransferStatusId());
    }

    @Test
    public void getAllTransfersByUser_returns_empty_list() {
        long accountId = 1L;
        List<Transfer> transfers = transferDao.getAllTransfersByUser(accountId);

        Assertions.assertEquals(0, transfers.size());
    }
    /**
     * Test case to verify that createTransfer() method creates a new transfer in the database.
     * It checks if the returned transfer id is not null and has a value greater than 0.
     */
    @Test
    public void testDeleteTransfer() {
        // Arrange
        List<Transfer> transfers = new ArrayList<>(Arrays.asList(
                transferDao.createTransfer(1L, 2L, accountFrom, accountTo, BigDecimal.valueOf(50.0)),
                transferDao.createTransfer(1L, 2L, accountFrom, accountTo, BigDecimal.valueOf(50.0)),
                transferDao.createTransfer(1L, 2L, accountFrom, accountTo, BigDecimal.valueOf(50.0))
        ));

        // Act
        transfers.forEach((s) -> transferDao.deleteTransfer(s.getTransferId()));

        // Assert
        assertNull(transferDao.getTransferById(transfers.get(1).getTransferId()));

    }
    /**
     * Tests the updateTransfer method of the TransferDAO implementation by verifying that it updates
     * the transfer status of a given Transfer object in the transfers table.
     */
    @Test
    public void updateTransfer_updates_transfer_status() {
        Transfer transfer = transferDao.createTransfer(1L, 2L, accountFrom, accountTo, new BigDecimal("10.00"));
        transfer.setTransferStatusId(2L);
        transferDao.updateTransfer(transfer);
        Transfer updatedTransfer = transferDao.getTransferById(transfer.getTransferId());
        Assertions.assertEquals(2L, updatedTransfer.getTransferStatusId());
    }
    /**
     * Tests the deleteTransfer method of the TransferDAO implementation by verifying that it removes
     * a given transfer from the transfers table.
     */
    @Test
    public void deleteTransfer_deletes_transfer() {
        Transfer transfer = transferDao.createTransfer(1L, 2L, accountFrom, accountTo, new BigDecimal("10.00"));
        transferDao.deleteTransfer(transfer.getTransferId());
        Transfer deletedTransfer = transferDao.getTransferById(transfer.getTransferId());
        Assertions.assertNull(deletedTransfer);
    }

}