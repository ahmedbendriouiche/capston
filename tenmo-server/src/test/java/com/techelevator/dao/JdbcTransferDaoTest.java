package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

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

public class JdbcTransferDaoTest {
    private static final long USER_ID = 1L;
    private static final Transfer TEST_TRANSFER = new Transfer(1L, 2L, 2L, 1L, 2L, BigDecimal.valueOf(50.0));
    private static final Transfer ANOTHER_TEST_TRANSFER = new Transfer(2L, 2L, 2L, 2L, 1L, BigDecimal.valueOf(25.0));

    private JdbcTransferDao dao;
    DataSource dataSource;

    /*
     * The setup method initializes the testTransfer object and sets up the jdbcTemplate and dao for each test method to use.
     * The test methods use assertions to verify that the methods of the dao return the expected results.
     */
    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTransferDao(jdbcTemplate);
    }
    /**
     Test case to verify that the getAllTransfers() method of JdbcTransferDao returns all transfers present in the database.
     It checks if the returned list of transfers is not null and has a size of 1, which is the size of the dataset in the test database.
     */
    @Test
    void testGetAllTransfers() {
        // Arrange
        int initialSize = dao.getAllTransfers().size();
        dao.createTransfer(TEST_TRANSFER);
        dao.createTransfer(ANOTHER_TEST_TRANSFER);

        // Act
        List<Transfer> transfers = dao.getAllTransfers();

        // Assert
        assertNotNull(transfers);
        assertEquals(initialSize + 2, transfers.size());
        assertTrue(transfers.contains(TEST_TRANSFER));
        assertTrue(transfers.contains(ANOTHER_TEST_TRANSFER));
    }
    /**
     Test case to verify that returns transfers
     only for the given user id.
     */
    @Test
    void testGetTransfersByUserId() {
        // Arrange
        int initialSize = dao.getTransfersByUserId(USER_ID).size();
        dao.createTransfer(TEST_TRANSFER);
        dao.createTransfer(ANOTHER_TEST_TRANSFER);

        // Act
        List<Transfer> transfers = dao.getTransfersByUserId(USER_ID);

        // Assert
        assertNotNull(transfers);
        assertEquals(initialSize + 2, transfers.size());
        assertTrue(transfers.contains(TEST_TRANSFER));
        assertTrue(transfers.contains(ANOTHER_TEST_TRANSFER));
    }

    @Test
    void testGetTransferById() {
        // Arrange
        dao.createTransfer(TEST_TRANSFER);
        dao.createTransfer(ANOTHER_TEST_TRANSFER);

        // Act
        Transfer transfer = dao.getTransferById(TEST_TRANSFER.getTransferId());

        // Assert
        assertNotNull(transfer);
        assertEquals(TEST_TRANSFER, transfer);
    }

    @Test
    void testGetAllTransfersByUser() {
        // Arrange
        int initialSize = dao.getAllTransfersByUser(USER_ID).size();
        dao.createTransfer(TEST_TRANSFER);
        dao.createTransfer(ANOTHER_TEST_TRANSFER);

        // Act
        List<Transfer> transfers = dao.getAllTransfersByUser(USER_ID);

        // Assert
        assertNotNull(transfers);
        assertEquals(initialSize + 2, transfers.size());
        assertTrue(transfers.contains(TEST_TRANSFER));
        assertTrue(transfers.contains(ANOTHER_TEST_TRANSFER));
    }

    @Test
    void testCreateTransfer() {
        // Arrange
        Transfer transfer = new Transfer(2L, 2L, 2L, 1L, 2L, BigDecimal.valueOf(15.0));

        // Act
        dao.createTransfer(transfer);
        // Assert
        assertNotNull(transfer.getTransferId());
    }
    @Test
    void testUpdateTransfer() {
        // Arrange
        dao.createTransfer(TEST_TRANSFER);
        TEST_TRANSFER.setTransferStatusId(2L);

        // Act
        dao.updateTransfer(TEST_TRANSFER);

        // Assert
        Transfer updatedTransfer = dao.getTransferById(TEST_TRANSFER.getTransferId());
        assertEquals(TEST_TRANSFER.getTransferStatusId(), updatedTransfer.getTransferStatusId());
    }
    @Test
    void testDeleteTransfer() {
        // Arrange
        dao.createTransfer(TEST_TRANSFER);

        // Act
        dao.deleteTransfer(TEST_TRANSFER.getTransferId());

        // Assert
        assertNull(dao.getTransferById(TEST_TRANSFER.getTransferId()));
    }
}