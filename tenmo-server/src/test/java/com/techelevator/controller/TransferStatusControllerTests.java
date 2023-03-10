package com.techelevator.controller;

import com.techelevator.tenmo.controller.TransferStatusController;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.services.TransferStatusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.security.Principal;
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
    public void testGetStatus_returns_bad_request_without_parameters() throws Exception{
        ResponseEntity<?> response = transferStatusController.getStatus(0, null);

        assertThat(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testGetStatus_returns_bad_request_if_given_both_parameters() throws Exception{
        ResponseEntity<?> response = transferStatusController.getStatus(1, "test");

        assertThat(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testGetStatus_returns_correct_status_when_given_id() throws Exception{
        int id = 1;
        TransferStatus expectedStatus = new TransferStatus(id, "Pending");
        when(transferStatusService.getStatus(id, null)).thenReturn(expectedStatus);

        ResponseEntity<?> response = transferStatusController.getStatus(id, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedStatus);
    }

    @Test
    public void testGetStatus_returns_correct_status_when_given_name() throws Exception{
        String name = "pending";
        TransferStatus expectedStatus = new TransferStatus(1, "Pending");
        when(transferStatusService.getStatus(0, name)).thenReturn(expectedStatus);

        ResponseEntity<?> response = transferStatusController.getStatus(0, name);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedStatus);
    }

    @Test
    public void testGetStatuses_returns_ok_with_admin() throws Exception{
        Principal admin = Mockito.mock(Principal.class);

        ResponseEntity<?> adminResponse = transferStatusController.getStatuses(admin);

        assertThat(adminResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetStatuses_returns_forbidden_without_admin() throws Exception{
        Principal user = Mockito.mock(Principal.class);

        ResponseEntity<?> userResponse = transferStatusController.getStatuses(user);

        assertThat(userResponse.getStatusCode().equals(HttpStatus.FORBIDDEN));
    }

    @Test
    public void testGetStatuses_returns_correct_list() throws Exception{
        TransferStatus pending = new TransferStatus(1, "Pending");
        TransferStatus approved = new TransferStatus(2, "Approved");
        TransferStatus rejected = new TransferStatus(3, "Rejected");

        Principal admin = Mockito.mock(Principal.class);

        List<TransferStatus> expectedList = Arrays.asList(pending, approved, rejected);
        when(transferStatusService.listAll(admin)).thenReturn(expectedList);

        ResponseEntity<?> response = transferStatusController.getStatuses(admin);

        List<TransferStatus> actualList = (List<TransferStatus>) response.getBody();

        for (int i = 0; i < actualList.size(); i++) {
            assertThat(actualList.get(i).equals(expectedList.get(i)));
        }
    }
}

