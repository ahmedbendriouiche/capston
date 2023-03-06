package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.CustomerDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountServiceTests {
    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY3NzkwNzIxMn0.hXsKLF9dI7QMp1kLSLIaPNVakjbGCgVpeLxYnG5omPvRT3OLrf6leT2-Y5ghS8PcgsY3NdjOK40e_tSwMxLqYg";

    private static final String API_URL = "http://localhost:8080/";
    private   CustomerAccountService accountService;
    private final RestTemplate restTemplate = new RestTemplate();
    MockRestServiceServer mockServer;

    @Before
    public void setup(){
        accountService = new CustomerAccountService(API_URL,new CustomerDto("user"),TOKEN);
        ReflectionTestUtils.setField(accountService, "restTemplate", restTemplate);
       mockServer = MockRestServiceServer.createServer(restTemplate);

    }
 @Test
    public void get_balance_bodyResponse_test(){
     mockServer.expect(requestTo(API_URL+"accounts/overallbalance?customer=user"))
             .andExpect(method(HttpMethod.GET))
             .andExpect(header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN))
             .andRespond(withSuccess(
                     "{\n" +
                             "    \"customer\": {\n" +
                             "        \"id\": 1001,\n" +
                             "        \"username\": \"user\",\n" +
                             "        \"authorities\": [\n" +
                             "            {\n" +
                             "                \"name\": \"ROLE_USER\"\n" +
                             "            }\n" +
                             "        ]\n" +
                             "    },\n" +
                             "    \"balance\": 1000.00\n" +
                             "}",
                     MediaType.APPLICATION_JSON));
     try {
         accountService.getUserGeneralBalance();
     } catch (Exception e) {
         Assert.fail("Didn't send the expected request to retrieve status.");
     }
     mockServer.verify();
 }
 @Test
    public void failed_authorization_test(){
     mockServer.expect(requestTo(API_URL+"accounts/overallbalance?customer=user"))
             .andExpect(method(HttpMethod.GET))
             .andExpect(header(HttpHeaders.AUTHORIZATION, "Bearer "+ TOKEN))
             .andRespond(withStatus(HttpStatus.FORBIDDEN));
     try {
         accountService.getUserGeneralBalance();
     } catch (HttpClientErrorException e) {
         Assert.assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
         Assert.assertTrue(e.getResponseBodyAsString().contains("Unauthorized access"));
     }
     mockServer.verify();
 }
}
