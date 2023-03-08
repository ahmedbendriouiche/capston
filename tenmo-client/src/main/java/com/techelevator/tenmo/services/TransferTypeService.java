package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypeService {
    public final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public TransferTypeService(String baseUrl) {
        this.baseUrl = baseUrl + "transfers/type";
    }

    public void setCurrentUser(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }

    public TransferType[] listTypes() {
       TransferType[] transferTypes = null;
       try{
           transferTypes = restTemplate.getForObject(baseUrl, TransferType[].class);
       } catch (RestClientResponseException e) {
           BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
       } catch (ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       }
       return transferTypes;
    }

    public TransferType getTypeById(long id) {
        TransferType transferType = null;

        try{
            ResponseEntity<TransferType> response = restTemplate.exchange(baseUrl + "/" + id, HttpMethod.GET,
                    makeAuthEntity(currentUser), TransferType.class);
            transferType = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferType;
    }

    public TransferType getTypeByName(String name) {
        TransferType transferType = null;

        try{
            transferType = restTemplate.getForObject(baseUrl + "?name=" + name, TransferType.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferType;
    }

    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }

}
