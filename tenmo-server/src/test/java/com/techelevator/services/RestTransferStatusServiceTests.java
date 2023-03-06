package com.techelevator.services;

import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.services.RestTransferStatusService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.Principal;
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
    public void testListAll() throws Exception{
        List<TransferStatus> mockList = Arrays.asList(PENDING, APPROVED, REJECTED);

        when(transferStatusDao.listAll()).thenReturn(mockList);

        Principal admin = Mockito.mock(Principal.class);
        when(admin.getName()).thenReturn("admin");

        List<TransferStatus> actualList = restTransferStatusService.listAll(admin);

        Assert.assertEquals(mockList, actualList);
    }

    @Test
    public void testGetStatusByName() throws Exception{
        when(transferStatusDao.getStatusByName("Pending")).thenReturn(PENDING);

        TransferStatus status = restTransferStatusService.getStatus(0, "Pending");

        Assert.assertEquals(PENDING, status);
    }

    @Test
    public void testGetStatusById() throws Exception{
        when(transferStatusDao.getStatusById(1)).thenReturn(PENDING);

        TransferStatus status = restTransferStatusService.getStatus(1, null);

        Assert.assertEquals(PENDING, status);
    }

}
