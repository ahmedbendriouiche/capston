package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferType;
import com.techelevator.util.BasicLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypeService {
    public static final String API_BASE_URL = "http://localhost:8080/transfers/type";

    private RestTemplate restTemplate = new RestTemplate();

    public TransferType[] listTypes() {
       TransferType[] transferTypes = null;
       try{
           transferTypes = restTemplate.getForObject(API_BASE_URL, TransferType[].class);
       } catch (RestClientResponseException e) {
           BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
       } catch (ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       }
       return transferTypes;
    }

    public TransferType getTypeById(int id) {
        TransferType transferType = null;

        try{
            transferType = restTemplate.getForObject(API_BASE_URL + id, TransferType.class);
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
            transferType = restTemplate.getForObject(API_BASE_URL + "?name=" + name, TransferType.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferType;
    }

}
