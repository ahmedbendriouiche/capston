package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
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
@RunWith(SpringRunner.class)
@JdbcTest
public class JdbcTransferDaoTest {
    @Autowired
    private TransferDao transferDao;
    private Transfer testTransfer;
    private static final long TEST_TRANSFER_ID = 9999L;
    private static final long TEST_TRANSFER_TYPE_ID = 2L;
    private static final long TEST_TRANSFER_STATUS_ID = 2L;
    private static final long TEST_ACCOUNT_FROM = 1L;
    private static final long TEST_ACCOUNT_TO = 2L;
    private static final BigDecimal TEST_AMOUNT = new BigDecimal("100.00");

    private JdbcTransferDao dao;
    private JdbcTemplate jdbcTemplate;

    /*
     * The setup method initializes the testTransfer object and sets up the jdbcTemplate and dao for each test method to use.
     * The test methods use assertions to verify that the methods of the dao return the expected results.
     */
    @BeforeEach
    public void setup() {
        DataSource dataSource = new SingleConnectionDataSource("jdbc:postgresql://localhost:5432/tenmo_test",
                "postgres", "postgres", true);
        jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTransferDao(jdbcTemplate);
        testTransfer = new Transfer();
        testTransfer.setTransferId(TEST_TRANSFER_ID);
        testTransfer.setTransferTypeId(TEST_TRANSFER_TYPE_ID);
        testTransfer.setTransferStatusId(TEST_TRANSFER_STATUS_ID);
        testTransfer.setAccountFrom(TEST_ACCOUNT_FROM);
        testTransfer.setAccountTo(TEST_ACCOUNT_TO);
        testTransfer.setAmount(TEST_AMOUNT);
    }
    /**
     Test case to verify that the getAllTransfers() method of JdbcTransferDao returns all transfers present in the database.
     It checks if the returned list of transfers is not null and has a size of 1, which is the size of the dataset in the test database.
     */
    @Test
    public void getAllTransfers_returns_all_transfers() {
        List<Transfer> transfers = transferDao.getAllTransfers();
        assertNotNull(transfers);
        assertEquals(1, transfers.size());
    }
    /**
     Test case to verify that returns transfers
     only for the given user id.
     */
    @Test
    public void getTransfersByUserId_returns_transfers_for_given_user_id() {
        List<Transfer> transfers = transferDao.getTransfersByUserId(1);
        assertNotNull(transfers);
        assertEquals(1, transfers.size());
    }

    @Test
    public void getTransferById_returns_correct_transfer() {
        Transfer transfer = transferDao.getTransferById(testTransfer.getTransferId());
        assertNotNull(transfer);
        assertEquals(testTransfer.getTransferTypeId(), transfer.getTransferTypeId());
        assertEquals(testTransfer.getTransferStatusId(), transfer.getTransferStatusId());
        assertEquals(testTransfer.getAccountFrom(), transfer.getAccountFrom());
        assertEquals(testTransfer.getAccountTo(), transfer.getAccountTo());
        assertEquals(testTransfer.getAmount(), transfer.getAmount());
    }

    @Test
    public void createTransfer_adds_new_transfer() {
        // Arrange
        Transfer newTransfer = new Transfer();
        newTransfer.setTransferTypeId(2);
        newTransfer.setTransferStatusId(2);
        newTransfer.setAccountFrom(2);
        newTransfer.setAccountTo(1);
        newTransfer.setAmount(new BigDecimal("25.00"));

        // Act
        transferDao.createTransfer(newTransfer);
        List<Transfer> transfers = transferDao.getAllTransfers();
        // Assert
        assertNotNull(transfers);
        assertEquals(2, transfers.size());
        Transfer createdTransfer = transfers.get(1);
        assertNotNull(createdTransfer);
        assertEquals(newTransfer.getTransferTypeId(), createdTransfer.getTransferTypeId());
        assertEquals(newTransfer.getTransferStatusId(), createdTransfer.getTransferStatusId());
        assertEquals(newTransfer.getAccountFrom(), createdTransfer.getAccountFrom());
        assertEquals(newTransfer.getAccountTo(), createdTransfer.getAccountTo());
        assertEquals(newTransfer.getAmount(), createdTransfer.getAmount());
    }

    @Test
    public void createTransfer_throws_duplicate_key_exception_on_same_transfer() {
        // Attempt to create the same transfer twice
        transferDao.createTransfer(testTransfer);
        assertThrows(DuplicateKeyException.class, () -> transferDao.createTransfer(testTransfer));
    }

    @Test
    public void createTransfer_throws_data_access_exception_on_invalid_transfer_type_id() {
        Transfer newTransfer = new Transfer();
        newTransfer.setTransferTypeId(0);
        newTransfer.setTransferStatusId(2);
        newTransfer.setAccountFrom(2);
        newTransfer.setAccountTo(1);
        newTransfer.setAmount(new BigDecimal("25.00"));

        transferDao.createTransfer(newTransfer);
    }

    @Test
    public void deleteTransfer_removesTransferFromDatabase() throws SQLException {
        // Arrange
        dao.createTransfer(TEST_TRANSFER_TYPE_ID, TEST_TRANSFER_STATUS_ID, TEST_ACCOUNT_FROM, TEST_ACCOUNT_TO, TEST_AMOUNT);
        List<Transfer> transfersBefore = dao.getAllTransfers();
        int numTransfersBefore = transfersBefore.size();

        // Act
        dao.deleteTransfer(TEST_TRANSFER_ID);
        List<Transfer> transfersAfter = dao.getAllTransfers();
        int numTransfersAfter = transfersAfter.size();

        // Assert
        assertEquals(numTransfersBefore - 1, numTransfersAfter);
    }
}