package com.techelevator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.TenmoApplication;
import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.controller.AuthenticationController;
import com.techelevator.tenmo.model.LoginDto;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TenmoApplication.class)

public class AccountsContollerTests {
    @Autowired
    private AccountController accountController;
    @Autowired
    private AuthenticationController authenticationController;
    private MockMvc mockMvcAccount;
    private MockMvc mockMvcAuthentication;
    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("setup()...");
        mockMvcAccount = MockMvcBuilders.standaloneSetup(accountController).build();
        mockMvcAuthentication = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }
    @Test
    public void AuthenticatedEndpoint_test() throws Exception {
        String requestBody = createLoginBody();
        mockMvcAuthentication.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }
@Test
    public  void AccountsGetBalance_Controller_test() throws Exception {
        String token =getAuthToken(mockMvcAuthentication);
    System.out.println(token);
        mockMvcAccount.perform(get("/accounts/balance")
                      .contentType(MediaType.APPLICATION_JSON)
                      .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                      .andExpect(status().isOk());
    }

    private String createLoginBody() throws JsonProcessingException {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("user");
        loginDto.setPassword("user");
        ObjectMapper objectMapper = new ObjectMapper();
        return  objectMapper.writeValueAsString(loginDto);
    }

    private HttpEntity<LoginDto> createCredentialsEntity(LoginDto credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(credentials, headers);
    }
    private String getAuthToken(MockMvc mockMvc) throws Exception {
        // authenticate and retrieve token
        String requestJson = createLoginBody();
        String responseBody = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.getString("token");
    }
}
