package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.util.BasicLogger;
import org.apiguardian.api.API;
import org.openqa.selenium.remote.Response;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


public class TransferStatusService {
    public static final String API_BASE_URL = "http://localhost:8080/transfers/status";
    private RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public TransferStatus[] getAllStatuses() {
        TransferStatus[] statuses = null;
        try {
            ResponseEntity<TransferStatus[]> response =
                    restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), TransferStatus[].class);
            statuses = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return statuses;
    }

    public TransferStatus getStatusById(long id) {
        TransferStatus status = null;
        try {
            ResponseEntity<TransferStatus> response =
                    restTemplate.exchange((API_BASE_URL + "?id=" + id), HttpMethod.GET, makeAuthEntity(),
                            TransferStatus.class);
            status = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return status;
    }

    public TransferStatus getStatusByName(String name) {
        TransferStatus status = null;
        try {
            ResponseEntity<TransferStatus> response =
                    restTemplate.exchange((API_BASE_URL + "?name=" + name), HttpMethod.GET, makeAuthEntity(),
                            TransferStatus.class);
            status = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return status;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
