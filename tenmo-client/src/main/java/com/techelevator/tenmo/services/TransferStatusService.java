package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.util.BasicLogger;
import org.apiguardian.api.API;
import org.openqa.selenium.remote.Response;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


public class TransferStatusService {
    public final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;



    public TransferStatusService(String baseUrl) {
        this.baseUrl = baseUrl + "transfers/status";
    }


    public void setCurrentUser(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }

    public TransferStatus[] getAllStatuses() {
        TransferStatus[] statuses = null;
        try {
            ResponseEntity<TransferStatus[]> response =
                    restTemplate.exchange(baseUrl, HttpMethod.GET, makeAuthEntity(currentUser), TransferStatus[].class);
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
                    restTemplate.exchange((baseUrl + "?id=" + id), HttpMethod.GET, makeAuthEntity(currentUser),
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
                    restTemplate.exchange((baseUrl + "?name=" + name), HttpMethod.GET, makeAuthEntity(currentUser),
                            TransferStatus.class);
            status = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return status;
    }

    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }
}
