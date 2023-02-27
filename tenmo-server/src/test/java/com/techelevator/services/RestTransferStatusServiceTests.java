package com.techelevator.services;

import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.services.RestTransferStatusService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import org.mockito.*;

import static org.mockito.Mockito.when;


public class RestTransferStatusServiceTests {
    private static final TransferStatus PENDING = new TransferStatus(1, "Pending");
    private static final TransferStatus APPROVED = new TransferStatus(2, "Approved");
    private static final TransferStatus REJECTED = new TransferStatus(3, "Rejected");

    @Mock
    private TransferStatusDao transferStatusDao;

    @InjectMocks
    private RestTransferStatusService restTransferStatusService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListAll() {
        List<TransferStatus> mockList = Arrays.asList(PENDING, APPROVED, REJECTED);

        when(transferStatusDao.listAll()).thenReturn(mockList);

        List<TransferStatus> actualList = restTransferStatusService.listAll();

        Assert.assertEquals(mockList, actualList);
    }

    @Test
    public void testGetStatusByName() {
        when(transferStatusDao.getStatusByName("Pending")).thenReturn(PENDING);

        TransferStatus status = restTransferStatusService.getStatusByName("Pending");

        Assert.assertEquals(PENDING, status);
    }

    @Test
    public void testGetStatusById() {
        when(transferStatusDao.getStatusById(1)).thenReturn(PENDING);

        TransferStatus status = restTransferStatusService.getStatusById(1);

        Assert.assertEquals(PENDING, status);
    }

}
