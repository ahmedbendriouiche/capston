package com.techelevator.tenmo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CustomerBalanceResponse;
import com.techelevator.tenmo.model.CustomerDto;
import com.techelevator.util.BasicLogger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class CustomerAccountService implements AccountService {

    private final String baseUrl;
    private final String token;
    private final CustomerDto customerDto;
    private final RestTemplate restTemplate = new RestTemplate();

    public CustomerAccountService(String url, CustomerDto customerDto, String token) {
        this.baseUrl = url+"accounts/";
        this.token = token;
        this.customerDto = customerDto;
    }
    @Override
    public List<Account> ListAllUserAccounts() {
        List<Account> accounts = null;
        try {
            HttpEntity<CustomerDto> httpEntity = makeHeader();
            ResponseEntity<List<Account>> response =
                    restTemplate.exchange(baseUrl+"accounts", HttpMethod.GET,
                            httpEntity, new ParameterizedTypeReference<>() {
                            });
            accounts = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }
    @Override
    public CustomerBalanceResponse getUserGeneralBalance() {
        CustomerBalanceResponse CustomerBalance = null;
        String apiUrl = baseUrl+"overallbalance?customer="+customerDto.getName();
        try {
            HttpEntity<CustomerDto> httpEntity = makeHeader();
            ResponseEntity<CustomerBalanceResponse> response =
                    restTemplate.exchange(apiUrl, HttpMethod.GET,
                            httpEntity, CustomerBalanceResponse.class);
            return  response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return  null;
    }

    @Override
    public CustomerBalanceResponse getBalanceByAccount(long accountId) {
        CustomerBalanceResponse balance = null;
        String apiUrl = baseUrl+"balanceByAccount?customer="+customerDto.getName()+"&accountID="+accountId;
        try {
            HttpEntity<CustomerDto> httpEntity = makeHeader();
            ResponseEntity<CustomerBalanceResponse> response =
                    restTemplate.exchange(apiUrl, HttpMethod.GET,
                            httpEntity,CustomerBalanceResponse.class);
            return  response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
       return null;
    }

    @Override
    public void accountBalanceUpdate(long to, long from, BigDecimal amount) {
        String apiUrl = baseUrl+"balance/transfer?to="+to+"&"+"from="+from+"&"+"amount="+amount;
        try {
            HttpEntity<CustomerDto> httpEntity = makeHeader();
            ResponseEntity<Void> response =
                    restTemplate.exchange(apiUrl, HttpMethod.PUT,
                            httpEntity,Void.class);
            System.out.println(response.getBody());
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
    }
    private HttpEntity<CustomerDto> makeHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
}
