package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.util.BasicLogger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
/**
 * The TransferService class provides methods for interacting with the Transfer resource on the Tenmo API server.
 * It communicates with the server over HTTP using a RestTemplate object.
 */
public class TransferService {
    private final String API_BASE_URL = "http://localhost:8080/transfers/";
    private final RestTemplate restTemplate;
    /**
     * Constructs a new TransferService with the specified RestTemplate.
     *
     * @param restTemplate the RestTemplate to use for HTTP requests
     */
    public TransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    /**
     * Gets all transfers from the API server.
     *
     * @return an array of Transfer objects representing all transfers
     */
    public Transfer[] getAllTransfers() {
        Transfer[] transfers = null;
        try{
            transfers = restTemplate.getForObject(API_BASE_URL, Transfer[].class);
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }
    /**
     * Gets a transfer with the specified ID from the API server.
     *
     * @param transferId the ID of the transfer to get
     * @return the Transfer object representing the transfer with the specified ID
     */
    public Transfer getTransferById(long transferId) {
        Transfer transfer = null;
        try{
            transfer = restTemplate.getForObject(API_BASE_URL + transferId, Transfer.class);
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }
    /**
     * Creates a new transfer on the API server.
     *
     * @param transfer the Transfer object representing the transfer to create
     * @return true if the transfer was created successfully, false otherwise
     */
    public boolean createTransfer(Transfer transfer) {
        boolean success = false;
        HttpEntity<Transfer> entity = makeEntity(transfer);
        try{
            restTemplate.postForObject(API_BASE_URL, entity, void.class);
            success = true;
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return success;
    }
    /**
     * Updates an existing transfer on the API server.
     *
     * @param transfer the Transfer object representing the transfer to update
     * @param status the status of the transfer
     * @return true if the transfer was updated successfully, false otherwise
     */
    public boolean updateTransfer(Transfer transfer, TransferStatus status) {
        boolean success = false;
        transfer.setTransferStatusId(status.getTransferStatusId());
        HttpEntity<Transfer> entity = makeEntity(transfer);
        try{
            restTemplate.put(API_BASE_URL + transfer.getTransferId(), entity);
            success = true;
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return success;
    }
    /**
     * Deletes a transfer with the specified ID from the API server.
     *
     * @param transferId the ID of the transfer to delete
     * @return true if the transfer was deleted successfully, false otherwise
     */
    public boolean deleteTransfer(long transferId) {
        boolean success = false;
        try{
            restTemplate.delete(API_BASE_URL + transferId);
            success = true;
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return success;
    }
    /**
     * Creates a new HttpEntity for the specified Transfer object with content type application/json.
     *
     * @param transfer the Transfer object to create an HttpEntity for
     * @return the HttpEntity for the specified Transfer object
     */
    private HttpEntity<Transfer> makeEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transfer, headers);
    }
}