package com.techelevator.services;

import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.services.RestTransferTypeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class RestTransferTypeServiceTests {
    private static final TransferType send = new TransferType(1, "Send");
    private static final TransferType request = new TransferType(2, "Request");

    @Mock
    private TransferTypeDao transferTypeDao;

    @InjectMocks
    private RestTransferTypeService restTransferTypeService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTypeListAll() {
        List<TransferType> expectedList = Arrays.asList(send, request);

        when(transferTypeDao.listAll()).thenReturn(expectedList);

        List<TransferType> actualList = restTransferTypeService.listAll();

        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetTypeByName() {
        when(transferTypeDao.getTypeByName("Send")).thenReturn(send);

        TransferType type = restTransferTypeService.getTypeByName("Send");

        Assert.assertEquals(send, type);
    }

    @Test
    public void testGetStatusById() {
        when(transferTypeDao.getTypeById(2)).thenReturn(request);

        TransferType type = restTransferTypeService.getTypeById(2);

        Assert.assertEquals(request, type);
    }
}
