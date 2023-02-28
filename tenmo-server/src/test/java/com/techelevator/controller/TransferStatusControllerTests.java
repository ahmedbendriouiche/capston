package com.techelevator.controller;

import com.techelevator.tenmo.controller.TransferStatusController;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.services.TransferStatusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferStatusControllerTests {

    @Mock
    private TransferStatusService transferStatusService;

    @InjectMocks
    private TransferStatusController transferStatusController;

    @Test
    public void testGetStatusWithId() {
        int id = 1;
        TransferStatus expectedStatus = new TransferStatus(id, "Pending");
        when(transferStatusService.getStatusById(id)).thenReturn(expectedStatus);

        ResponseEntity<?> response = transferStatusController.getStatus(id, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedStatus);
    }

    @Test
    public void testGetStatusWithName() {
        String name = "pending";
        TransferStatus expectedStatus = new TransferStatus(1, "Pending");
        when(transferStatusService.getStatusByName(name)).thenReturn(expectedStatus);

        ResponseEntity<?> response = transferStatusController.getStatus(0, name);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedStatus);
    }

    @Test
    public void testGetStatusWithInvalidParams() {
        TransferStatus pending = new TransferStatus(1, "Pending");
        TransferStatus approved = new TransferStatus(2, "Approved");
        TransferStatus rejected = new TransferStatus(3, "Rejected");
        List<TransferStatus> expectedList = Arrays.asList(pending, approved, rejected);
        when(transferStatusService.listAll()).thenReturn(expectedList);

        ResponseEntity<?> response = transferStatusController.getStatus(0, null);

        List<TransferStatus> actualList = (List<TransferStatus>) response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        for (int i = 0; i < actualList.size(); i++) {
            assertThat(actualList.get(i).equals(expectedList.get(i)));
        }

    }
}

