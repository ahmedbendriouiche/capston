package com.techelevator.controller;

import com.techelevator.tenmo.controller.TransferTypeController;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.services.TransferTypeService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferTypeControllerTests {
    @Mock
    private TransferTypeService transferTypeService;

    @InjectMocks
    private TransferTypeController transferTypeController;


    @Test
    public void testGetTypeById() {
        int id = 1;
        TransferType expectedType = new TransferType(id, "Send");
        when(transferTypeService.getTypeById(id)).thenReturn(expectedType);

        ResponseEntity<TransferType> response = transferTypeController.getTypeById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedType);
    }

    @Test
    public void testGetTypeByName() {
        String name = "Request";
        TransferType expectedType = new TransferType(1, "Request");
        when(transferTypeService.getTypeByName("Request")).thenReturn(expectedType);

        ResponseEntity<TransferType> response = transferTypeController.getTypeByName(name);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedType);
    }
}
