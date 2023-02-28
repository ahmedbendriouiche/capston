package com.techelevator.controller;

import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.dao.AccountDao;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = AccountController.class)
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
public class AccountsControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountDao accountDao;


    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void Get_general_Balance_byUserName_Outcome_test() throws Exception {
        // Setup mock behavior
        when(accountDao.getGeneralBalance("user")).thenReturn(new BigDecimal("1000.00"));

        // Make GET request to "/balance"
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1000.00"));
    }
    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void Get_Balance_by_UserName_and_AccountId_Outcome_test() throws Exception {
        long accountId = 2002L;
        // Setup mock behavior
        when(accountDao.getBalanceByAccount("user", accountId))
                .thenReturn(new BigDecimal("1000.00"));

        // Make GET request to "/balance/{accountId}"
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance/"+accountId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1000.00"));
    }
    @Test
    public void Get_Balance_by_UserName_and_AccountId_Unauthorized_Access_test() throws Exception {
        long accountId = 2002L;
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance/"+accountId)
                        .header("Authorization", "Bearer"))
                .andExpect(status().isUnauthorized());;
    }
    @Test
    @WithMockUser(username = "user", password = "user", roles = "VIEWER")
    public void Get_Balance_by_UserName_and_AccountId_Forbidden_Access_test() throws Exception {
        // Make GET request to "/balance"
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


}
