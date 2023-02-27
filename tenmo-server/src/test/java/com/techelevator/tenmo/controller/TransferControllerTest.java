package com.techelevator.tenmo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class TransferControllerTest {
    private MockMvc mockMvc;
    @Mock
    private TransferDao mockTransferDao;
    private TransferController transferController;

    /**
     * Sets up the TransferController object for testing by creating a mock TransferDao object.
     */
    @BeforeEach
    public void setup() {
        mockTransferDao = mock(TransferDao.class);
        transferController = new TransferController(mockTransferDao);
    }
    /**
     * Test to verify the behavior of the "getAllTransfers" endpoint in TransferController.
     * @throws Exception if an error occurs while performing the MVC request or assertion.
     */
    @Test
    void testGetAllTransfers() throws Exception {
        List<Transfer> expectedTransfers = new ArrayList<>();
        Transfer transfer1 = new Transfer(1L, 2L, 1L, 2, 2, BigDecimal.TEN);
        Transfer transfer2 = new Transfer(2L, 1L, 2L, 2, 2, BigDecimal.valueOf(20));
        expectedTransfers.add(transfer1);
        expectedTransfers.add(transfer2);
        when(mockTransferDao.getAllTransfers()).thenReturn(expectedTransfers);

        mockMvc.perform(get("/transfers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedTransfers.size()))
                .andExpect(jsonPath("$.[0].transferId").value(transfer1.getTransferId()))
                .andExpect(jsonPath("$.[0].transferTypeId").value(transfer1.getTransferTypeId()))
                .andExpect(jsonPath("$.[0].transferStatusId").value(transfer1.getTransferStatusId()))
                .andExpect(jsonPath("$.[0].accountFrom").value(transfer1.getAccountFrom()))
                .andExpect(jsonPath("$.[0].accountTo").value(transfer1.getAccountTo()))
                .andExpect(jsonPath("$.[0].amount").value(transfer1.getAmount().doubleValue()))
                .andExpect(jsonPath("$.[1].transferId").value(transfer2.getTransferId()))
                .andExpect(jsonPath("$.[1].transferTypeId").value(transfer2.getTransferTypeId()))
                .andExpect(jsonPath("$.[1].transferStatusId").value(transfer2.getTransferStatusId()))
                .andExpect(jsonPath("$.[1].accountFrom").value(transfer2.getAccountFrom()))
                .andExpect(jsonPath("$.[1].accountTo").value(transfer2.getAccountTo()))
                .andExpect(jsonPath("$.[1].amount").value(transfer2.getAmount().doubleValue()));
    }
    /**

     Test case for the "getTransferById" method in the TransferController class
     when the transfer with the given ID exists in the database.
     <p>It creates a transfer with a specific ID, sets up the mock TransferDao to return
     that transfer when getTransferById is called with the same ID,
     calls the getTransferById method of the TransferController with that ID,
     and then asserts that the returned HTTP status code is OK and the returned Transfer object
     matches the expected transfer object.</p>
     */
    @Test
    public void getTransferById_returnsTransfer_whenTransferExists() {
        // Arrange
        int transferId = 1;
        Transfer transfer = new Transfer(1, 2, 1, 2, 2, BigDecimal.valueOf(100));
        when(mockTransferDao.getTransferById(transferId)).thenReturn(transfer);

        // Act
        ResponseEntity<Transfer> response = transferController.getTransferById(transferId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transfer, response.getBody());
    }

    /**
     Test case to verify that the 'getTransferById' method returns a 'Not Found' response
     when the requested transfer does not exist.
     */
    @Test
    public void getTransferById_returnsNotFound_whenTransferDoesNotExist() {
        // Arrange
        int transferId = 1;
        when(mockTransferDao.getTransferById(transferId)).thenReturn(null);

        // Act
        ResponseEntity<Transfer> response = transferController.getTransferById(transferId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }
    /**
     Test case to verify that creating a transfer returns the created transfer. It uses a mock transfer dao to create the
     transfer and returns it as a response. The test case first sets up the transfer object and the expected created transfer
     object. Then, it sets up the mock transfer dao to return the expected created transfer when createTransfer() is called
     with the transfer object attributes. Finally, it calls the createTransfer() method of transfer controller with the
     transfer object and verifies that it returns the expected created transfer object in the response with an HTTP status code
     of OK (200).
     */
    @Test
    public void createTransfer_returnsCreatedTransfer() {
        // Arrange
        Transfer transfer = new Transfer();
        transfer.setTransferTypeId(1);
        transfer.setTransferStatusId(2);
        transfer.setAccountFrom(3);
        transfer.setAccountTo(4);
        transfer.setAmount(BigDecimal.valueOf(100));

        Transfer createdTransfer = new Transfer(1, 1, 2, 3, 4, BigDecimal.valueOf(100));
        when(mockTransferDao.createTransfer(
                transfer.getTransferTypeId(),
                transfer.getTransferStatusId(),
                transfer.getAccountFrom(),
                transfer.getAccountTo(),
                transfer.getAmount()
        )).thenReturn(createdTransfer);

        // Act
        ResponseEntity<Transfer> response = transferController.createTransfer(transfer);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdTransfer, response.getBody());
    }
    /*
    This test case tests the behavior of the deleteTransfer method of the TransferController class.
    It verifies that the method successfully calls the deleteTransfer method of the
     TransferDao class with the correct transfer ID.

Explanation of the test case steps:
In the Arrange step, a transfer ID is set up.
In the Act step, the deleteTransfer method is called with the transfer ID set up in the Arrange step.
In the Assert step, the deleteTransfer method of the TransferDao class is verified to have been
 called exactly once with the transfer ID set up in the Arrange step.
     */
    @Test
    public void deleteTransfer_deletesExistingTransfer() {
        // Arrange
        long transferId = 1L;

        // Act
        transferController.deleteTransfer(transferId);

        // Assert
        verify(mockTransferDao, times(1)).deleteTransfer(transferId);
    }
}