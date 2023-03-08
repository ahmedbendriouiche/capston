package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferHistoryDto;
import com.techelevator.tenmo.model.UserInfoDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserInfoService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;


    public UserInfoService(String baseUrl) {
        this.baseUrl = baseUrl + "users";
    }


    public UserInfoDto[] getUserInfos(AuthenticatedUser currentUser) {
        UserInfoDto[] history = null;

        try{
            long userId = currentUser.getUser().getId();

            ResponseEntity<UserInfoDto[]> response = restTemplate.exchange(baseUrl + "/listall?userId=" + userId,
                    HttpMethod.GET,
                    makeAuthEntity(currentUser), UserInfoDto[].class);
            history = response.getBody();
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return history;
    }

    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }
}
