package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.List;
import javax.sql.DataSource;

/**
 * Test class for JdbcTransferDao.
 * Uses JdbcTest annotation to create a test environment with an in-memory database.
 * Tests the methods of JdbcTransferDao.
 * The JdbcTransferDaoTest class is used to test the JdbcTransferDao class which implements the TransferDao interface.
 * This class includes test methods for the getAllTransfers, getTransfersByUserId,
 * getTransferById, createTransfer, and deleteTransfer methods of the TransferDao interface.
 */
@SpringJUnitConfig(classes = { TestingDatabaseConfig.class })
@Import(TestingDatabaseConfig.class)
public class JdbcTransferDaoTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    private JdbcTransferDao transferDao;
    @Autowired
    DataSource dataSource;

    private static final Transfer TEST_TRANSFER = new Transfer(1L, 2L, 2L, 1L, 2L, new BigDecimal("10.00"));

    /*
     * The setup method initializes the testTransfer object and sets up the jdbcTemplate and dao for each test method to use.
     * The test methods use assertions to verify that the methods of the dao return the expected results.
     */
    @BeforeEach
    public void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        transferDao = new JdbcTransferDao(jdbcTemplate);
    }
    /**
     Test case to verify that the getAllTransfers() method of JdbcTransferDao returns all transfers present in the database.
     It checks if the returned list of transfers is not null and has a size of 1, which is the size of the dataset in the test database.
     */
    @Test
    public void getAllTransfers_returns_all_transfers() {
        transferDao.createTransfer(1L, 2L, 2L, 1L, new BigDecimal("10.00"));
        List<Transfer> transfers = transferDao.getAllTransfers();
        Assertions.assertEquals(1, transfers.size());
        Transfer transfer = transfers.get(0);
        assertTransfersMatch(TEST_TRANSFER, transfer);
    }

    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assertions.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assertions.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        Assertions.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        Assertions.assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
        Assertions.assertEquals(expected.getAccountTo(), actual.getAccountTo());
        Assertions.assertEquals(expected.getAmount(), actual.getAmount());
    }
    /**
     * Test case to verify that the getTransferById() method of JdbcTransferDao returns the transfer with the specified ID.
     * It creates a transfer in the database, retrieves it by ID, and compares it to the expected transfer.
     */
    @Test
    public void getTransferById_returns_transfer_with_specified_id() {
        // Create a transfer in the database
        transferDao.createTransfer(1L, 2L, 2L, 1L, new BigDecimal("10.00"));

        // Retrieve the transfer by ID
        Transfer transfer = transferDao.getTransferById(1L);

        // Verify that the transfer retrieved is not null and matches the expected transfer
        Assertions.assertNotNull(transfer);
        assertTransfersMatch(TEST_TRANSFER, transfer);
    }
    /**
     * Tests the getAllTransfersByUser method of the TransferDAO implementation by verifying that it returns
     * a list of Transfer objects that includes all transfers involving a given account ID.
     */
    @Test
    public void getAllTransfersByUser_returns_correct_transfers() {
        long accountId = 1L;
        Transfer transfer1 = transferDao.createTransfer(accountId, 2L, 2L, 1L, new BigDecimal("10.00"));
        Transfer transfer2 = transferDao.createTransfer(3L, accountId, 2L, 1L, new BigDecimal("5.00"));
        List<Transfer> transfers = transferDao.getAllTransfersByUser(accountId);

        Assertions.assertEquals(2, transfers.size());
        assertTransfersMatch(transfer1, transfers.get(0));
        assertTransfersMatch(transfer2, transfers.get(1));
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
    public void createTransfer_creates_new_transfer_in_database() {
        Transfer transfer = transferDao.createTransfer(2L, 2L, 1L, 2L, new BigDecimal("100.00"));
        Assertions.assertNotNull(transfer.getTransferId());
        Assertions.assertTrue(transfer.getTransferId() > 0);
    }
    /**
     * Tests the updateTransfer method of the TransferDAO implementation by verifying that it updates
     * the transfer status of a given Transfer object in the transfers table.
     */
    @Test
    public void updateTransfer_updates_transfer_status() {
        Transfer transfer = transferDao.createTransfer(1L, 2L, 1L, 2L, new BigDecimal("10.00"));
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
        Transfer transfer = transferDao.createTransfer(1L, 2L, 1L, 2L, new BigDecimal("10.00"));
        transferDao.deleteTransfer(transfer.getTransferId());
        Transfer deletedTransfer = transferDao.getTransferById(transfer.getTransferId());
        Assertions.assertNull(deletedTransfer);
    }

}