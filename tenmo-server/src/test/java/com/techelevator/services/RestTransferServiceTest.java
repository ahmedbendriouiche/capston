package com.techelevator.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.model.TransferHistoryDto;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.junit.Before;
import org.junit.Test;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.services.RestTransferService;
/**

 The RestTransferServiceTest class tests the RestTransferService class methods.
 It includes tests for getAllTransfers, getTransferById, getAllTransfersByUser, createTransfer,
 updateTransfer and deleteTransfer methods.
 */
public class RestTransferServiceTest {

    private RestTransferService transferService;
    private TransferDao transferDao;
    private AccountDao accountDao;

    @Before
    public void setup() {
        transferDao = mock(TransferDao.class);
        accountDao = mock(AccountDao.class);
        transferService = new RestTransferService(transferDao, accountDao);
    }

    @Test
    public void testGetAllTransfers() {
        List<Transfer> expectedTransfers = new ArrayList<>();
        expectedTransfers.add(new Transfer(1, 1, 2, 2,2, new BigDecimal("100.00")));
        expectedTransfers.add(new Transfer(2, 2, 1, 2, 2, new BigDecimal("50.00")));
        when(transferDao.getAllTransfers()).thenReturn(expectedTransfers);
        List<Transfer> actualTransfers = transferService.getAllTransfers();
        assertEquals(expectedTransfers, actualTransfers);
    }

    @Test
    public void testGetTransferById() {
        Transfer expectedTransfer = new Transfer(1, 1, 2, 2, 2, new BigDecimal("100.00"));
        when(transferDao.getTransferById(1)).thenReturn(expectedTransfer);
        Transfer actualTransfer = transferService.getTransferById(1);
        assertEquals(expectedTransfer, actualTransfer);
    }

    @Test
    public void testGetAllTransfersByUser() {
        long userId = 1;
        long accountId = 1;
        List<Transfer> expectedTransfers = new ArrayList<>();
        expectedTransfers.add(new Transfer(1, 1, 2, 2, 2, new BigDecimal("100.00")));
        expectedTransfers.add(new Transfer(2, 2, 1, 2, 2, new BigDecimal("50.00")));
        when(accountDao.getAccountIdByUserId(userId)).thenReturn(accountId);
        when(transferDao.getAllTransfersByUser(accountId)).thenReturn(expectedTransfers);
        List<TransferHistoryDto> expectedHistory = new ArrayList<>();
        expectedHistory.add(new TransferHistoryDto(1, "user1", "user2", new BigDecimal("100.00")));
        expectedHistory.add(new TransferHistoryDto(2, "user2", "user1", new BigDecimal("50.00")));
        List<TransferHistoryDto> actualHistory = transferService.getAllTransfersByUser(userId);
        assertEquals(expectedHistory, actualHistory);
    }
    @Test
    public void testCreateTransfer() {
        TransferType type = new TransferType(1, "Send");
        TransferStatus status = new TransferStatus(1, "Approved");
        long userFrom = 1;
        long userTo = 2;
        BigDecimal amount = new BigDecimal("100.00");
        long accountFrom = 1;
        long accountTo = 2;
        Transfer expectedTransfer = new Transfer(1, 1, 2, 2, 2, new BigDecimal("100.00"));
        when(accountDao.getAccountIdByUserId(userFrom)).thenReturn(accountFrom);
        when(accountDao.getAccountIdByUserId(userTo)).thenReturn(accountTo);
        when(type.getTransferTypeId()).thenReturn(1L);
        when(status.getTransferStatusId()).thenReturn(1L);
        when(transferDao.createTransfer(1L, 1L, accountFrom, accountTo, amount)).thenReturn(expectedTransfer);
        Transfer actualTransfer = transferService.createTransfer(type, status, userFrom, userTo, amount);
        assertEquals(expectedTransfer, actualTransfer);
    }
    @Test
    public void testUpdateTransfer() {
        Transfer transfer = new Transfer(1, 1, 2, 2, 2, new BigDecimal("100.00"));
        transferService.updateTransfer(transfer);
        verify(transferDao).updateTransfer(transfer);
    }
    @Test
    public void testDeleteTransfer() {
        long transferId = 1;
        transferService.deleteTransfer(transferId);
        verify(transferDao).deleteTransfer(transferId);
    }

}