package com.techelevator.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.services.RestTransferService;

public class RestTransferServiceTest {

    private RestTransferService transferService;
    private TransferDao transferDao;
    private AccountDao accountDao;

    @Before
    public void setup() {
        transferDao = mock(TransferDao.class);
        accountDao = mock(AccountDao.class);
//        transferService = new RestTransferService(transferDao, accountDao);
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
    public void testGetTransfersByUserId() {
//        List<Transfer> expectedTransfers = new ArrayList<>();
//        expectedTransfers.add(new Transfer(1, 1, 2, 2, 2, new BigDecimal("100.00")));
//        expectedTransfers.add(new Transfer(3, 1, 3, 2, 2, new BigDecimal("25.00")));
//        when(transferDao.getTransfersByUserId(1)).thenReturn(expectedTransfers);
//        List<Transfer> actualTransfers = transferService.getTransfersByUserId(1);
//        assertEquals(expectedTransfers, actualTransfers);
    }

    @Test
    public void testGetTransferById() {
        Transfer expectedTransfer = new Transfer(1, 1, 2, 2, 2, new BigDecimal("100.00"));
        when(transferDao.getTransferById(1)).thenReturn(expectedTransfer);
        Transfer actualTransfer = transferService.getTransferById(1);
        assertEquals(expectedTransfer, actualTransfer);
    }

    // Additional tests for the remaining methods

}