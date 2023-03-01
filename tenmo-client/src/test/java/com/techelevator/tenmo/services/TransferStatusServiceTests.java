package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferStatus;
import org.hamcrest.core.StringStartsWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class TransferStatusServiceTests {
    private static final String API_URL = "http://localhost:8080/transfers/status";
    private static final long TEST_ID3 = 3;
    private static final String TEST_NAME = "Pending";

    RestTemplate restTemplate = new RestTemplate();
    TransferStatusService service = new TransferStatusService();
    MockRestServiceServer server;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(service, "restTemplate", restTemplate);
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void test_getAllStatuses() {

        server.expect(requestTo(API_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[{\"transferStatusId\":1,\"transferStatusDesc\":\"Pending\"}," +
                                "{\"transferStatusId\":2,\"transferStatusDesc\":\"Approved\"}," +
                                "{\"transferStatusId\":3,\"transferStatusDesc\":\"Rejected\"}]",
                        MediaType.APPLICATION_JSON));

        TransferStatus[] statuses = null;
        try {
            statuses = service.getAllStatuses();
        } catch (AssertionError e) {
            fail("Didn't send the expected request to retrieve status.");
        }
        assertNotNull("Call to getStatusByName() returned null.");
        for (TransferStatus status : statuses) {
            System.out.println(status);
        }
    }

    @Test
    public void test_getStatusById() {

        server.expect(requestTo(API_URL + "?id=" + TEST_ID3))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"transferStatusId\":3,\"transferStatusDesc\":\"Rejected\"}", MediaType.APPLICATION_JSON));

        TransferStatus status = null;
        try {
            status = service.getStatusById(TEST_ID3);
        } catch (AssertionError e) {
            fail("Didn't send the expected request to retrieve status.");
        }
        assertNotNull("Call to getStatusById() returned null.");
        assertEquals("Call to getStatusById() didn't return the expected data.", TEST_ID3,
                status.getTransferStatusId());

    }

    @Test
    public void test_getStatusByName() {

        server.expect(requestTo(API_URL + "?name=" + TEST_NAME))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"transferStatusId\":1,\"transferStatusDesc\":\"Pending\"}",
                        MediaType.APPLICATION_JSON));

        TransferStatus status = null;
        try {
            status = service.getStatusByName(TEST_NAME);
        } catch (AssertionError e) {
            fail("Didn't send the expected request to retrieve status.");
        }
        assertNotNull("Call to getStatusByName() returned null.");
        assertEquals("Call to getStatusByName() didn't return the expected data.", TEST_NAME,
                status.getTransferStatusDesc());

    }
}
