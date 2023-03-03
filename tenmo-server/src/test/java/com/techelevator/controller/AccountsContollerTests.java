package com.techelevator.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.controller.AuthenticationController;
import com.techelevator.tenmo.TenmoApplication;
import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.model.CustomerRequestDto;
import com.techelevator.tenmo.model.LoginDto;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
        // perform Endpoint test
        String requestBody = createLoginBody();
        mockMvcAuthentication.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

    }
    @Test
    public  void AccountsGetBalance_Forbidden_Access_test() throws Exception {
        String token ="Bearer "+ new JSONObject(getAuthToken()).getString("token");

        // send GET request to /accounts/balance with bearer token in authorization header

        String response = mockMvcAccount.perform(get("/accounts/overallbalance?customer=user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();
    }
    private String createLoginBody() throws  JsonProcessingException {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("admin");
        loginDto.setPassword("admin");
        ObjectMapper objectMapper = new ObjectMapper();
        return  objectMapper.writeValueAsString(loginDto);
    }


    public String getAuthToken() throws Exception {
        // authenticate and retrieve token
        String requestJson = createLoginBody();
        return mockMvcAuthentication.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
