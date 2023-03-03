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
    public BigDecimal getUserGeneralBalance() {
        CustomerBalanceResponse CustomerBalance = null;
        String url = baseUrl+"overallbalance?customer="+customerDto.getName();
        try {
            HttpEntity<CustomerDto> httpEntity = makeHeader();
            ResponseEntity<CustomerBalanceResponse> response =
                    restTemplate.exchange(url, HttpMethod.GET,
                            httpEntity, CustomerBalanceResponse.class);
            CustomerBalance = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return CustomerBalance.getBalance();
    }

    @Override
    public BigDecimal getBalanceByAccount(long accountId) {
        CustomerBalanceResponse balance = null;
        String url = baseUrl+"balanceByAccount?customer"+customerDto.getName()+"&accountID="+accountId;
        try {
            HttpEntity<CustomerDto> httpEntity = makeHeader();
            ResponseEntity<CustomerBalanceResponse> response =
                    restTemplate.exchange(url, HttpMethod.GET,
                            httpEntity,CustomerBalanceResponse.class);
            balance = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        assert balance != null;
        return balance.getBalance();
    }
    private HttpEntity<CustomerDto> makeHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
}
