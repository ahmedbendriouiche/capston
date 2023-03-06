package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferServiceTest {
        private static final String API_URL = "http://localhost:8080/transfers";
        private static final long TEST_ID3 = 3;
        private static final String TEST_NAME = "Pending";

        @Autowired
        RestTemplate restTemplate = new RestTemplate();
        @Autowired
        private TransferService transferService = new TransferService(restTemplate);

        @Autowired
        private MockRestServiceServer mockServer;

        @Before
        public void setup() {
            ReflectionTestUtils.setField(transferService, "restTemplate", restTemplate);
            mockServer = MockRestServiceServer.createServer(restTemplate);
        }
        @Test
        public void testGetAllTransfers() {
            mockServer.expect(requestTo(API_URL))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess("[{\"transferId\":1,\"transferTypeId\":2,\"transferStatusId\":3," +
                                    "\"accountFrom\":4,\"accountTo\":5,\"amount\":100.0}," +
                                    "{\"transferId\":2,\"transferTypeId\":2,\"transferStatusId\":3," +
                                    "\"accountFrom\":6,\"accountTo\":7,\"amount\":200.0}]",
                            MediaType.APPLICATION_JSON));

            Transfer[] transfers = null;
            try {
                transfers = transferService.getAllTransfers();
            } catch (AssertionError e) {
                fail("Didn't send the expected request to retrieve status.");
            }
            assertNotNull("Call to getAllTransfers() returned null.");
            for (Transfer transfer : transfers) {
                System.out.println(transfer);
            }
        }
        @Test
        public void testGetTransferById() {
            Transfer transfer = new Transfer(1L, 2L, 3L, 4L,
                    5L, BigDecimal.valueOf(100.00));
            mockServer.expect(requestTo(API_URL+ "/1"))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess("{\"transferId\":1,\"transferTypeId\":2,\"transferStatusId\":3," +
                            "\"accountFrom\":4,\"accountTo\":5,\"amount\":100.0}", MediaType.APPLICATION_JSON));
            Transfer actualTransfer = transferService.getTransferById(1L);
            mockServer.verify();
            assertEquals(transfer, actualTransfer);
        }
        @Test
        public void testCreateTransfer() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Transfer transfer = new Transfer(1L, 1L, 1L,
                    1L, 2L, BigDecimal.valueOf(100.00));
            HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
            // Set up a mock response
            mockServer.expect(requestTo("/transfer"))
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withSuccess("{\"transferId\":1,\"transferTypeId\":2," +
                            "\"transferStatusId\":4,\"accountFrom\":4,\"accountTo\":5,\"amount\":100.0}", MediaType.APPLICATION_JSON));

            HttpEntity<String> request = new HttpEntity<>("{\"accountFrom\":4,\"accountTo\":5,\"amount\":100.0}", headers);
            String response = restTemplate.postForObject("/transfer", request, String.class);
            assertEquals("{\"transferId\":1,\"transferTypeId\":2,\"transferStatusId\":4,\"accountFrom\":4,\"accountTo\":5,\"amount\":100.0}", response);

        // Verify that the mock server received the request
            mockServer.verify();
        }
        @Test
        public void testUpdateTransfer() throws Exception {
            // create a Transfer object
            Transfer transfer = new Transfer();
            transfer.setTransferId(1L);
            transfer.setTransferTypeId(2L);
            transfer.setTransferStatusId(4L);
            transfer.setAccountFrom(4L);
            transfer.setAccountTo(5L);
            transfer.setAmount(new BigDecimal(100));

            // create a TransferStatus object
            TransferStatus status = new TransferStatus();
            status.setTransferStatusId(5);

            // mock the RestTemplate class
            RestTemplate restTemplateSpy = Mockito.spy(restTemplate);

            // set up the spy to return null when put() is called
            Mockito.doNothing().when(restTemplateSpy).put(Mockito.anyString(), Mockito.any(HttpEntity.class));

            // set up the service class with the spy RestTemplate
            TransferService transferService = new TransferService(restTemplateSpy);
            ReflectionTestUtils.setField(transferService, "restTemplate", restTemplateSpy);

            // call the updateTransfer() method
            boolean success = transferService.updateTransfer(transfer, status);

            // verify that put() was called with the correct arguments
            Mockito.verify(restTemplateSpy).put(API_URL + transfer.getTransferId(), makeEntityHelper(transfer));

            // assert that the method returned true
            assertTrue(success);

        }

        @Test
        public void deleteTransfer_returnsTrueForSuccessfulDelete() {
            // Arrange
            mockServer.expect(requestTo(API_URL + "/1"))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withSuccess("[{\"transferId\":1,\"transferTypeId\":2,\"transferStatusId\":3," +
                                    "\"accountFrom\":4,\"accountTo\":5,\"amount\":100.0}," +
                                    "{\"transferId\":2,\"transferTypeId\":2,\"transferStatusId\":3," +
                                    "\"accountFrom\":6,\"accountTo\":7,\"amount\":200.0}]",
                            MediaType.APPLICATION_JSON));

            // Act
            boolean deleteSuccess = transferService.deleteTransfer(1);

            // Assert
            assertTrue(deleteSuccess, "Expected deleteTransfer to return true for successful delete");
        }
        private HttpEntity<Transfer> makeEntityHelper(Transfer transfer){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new HttpEntity<>(transfer, headers);
        }
    }