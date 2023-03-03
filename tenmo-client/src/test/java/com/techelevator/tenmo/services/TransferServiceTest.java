package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TransferServiceTest {
        private static final String API_URL = "http://localhost:8080/transfers";
        private static final long TEST_ID3 = 3;
        private static final String TEST_NAME = "Pending";

        RestTemplate restTemplate = new RestTemplate();
        private TransferService transferService;
        private MockRestServiceServer mockServer;

        @BeforeEach
        public void setUp() {
            restTemplate = new RestTemplate();
            mockServer = MockRestServiceServer.createServer(restTemplate);
            transferService = new TransferService(restTemplate);
        }

        @Test
        public void testGetAllTransfers() {
            Transfer[] expectedTransfers = {
                    new Transfer(1L, 2L, 2L, 1L,2L, BigDecimal.valueOf(100.00)),
                    new Transfer(2L, 2L, 2L, 2L, 2L, BigDecimal.valueOf(200.00))
            };

            mockServer.expect(requestTo("http://localhost:8080/transfers/"))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess("[{\"transferId\":1,\"transferTypeId\":2,\"transferStatusId\":3," +
                                    "\"accountFrom\":4,\"accountTo\":5,\"amount\":100.0}," +
                                    "{\"transferId\":2,\"transferTypeId\":2,\"transferStatusId\":3," +
                                    "\"accountFrom\":6,\"accountTo\":7,\"amount\":200.0}]",
                            MediaType.APPLICATION_JSON));

            Transfer[] actualTransfers = transferService.getAllTransfers();

            mockServer.verify();
            assertArrayEquals(expectedTransfers, actualTransfers);
        }

        @Test
        public void testGetTransferById() {
            Transfer transfer = new Transfer(1L, 2L, 3L, 4L,
                    5L, BigDecimal.valueOf(100.00));
            mockServer.expect(requestTo("http://localhost:8080/transfers/1"))
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
            Transfer transfer = new Transfer(null, 1L, 1L, 1L,
                    2L, BigDecimal.valueOf(100.00));
            HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
            mockServer.expect(requestTo("http://localhost:8080/transfers/"))
                    .andExpect(method(HttpMethod.POST))
                    .andExpect((RequestMatcher) content().json("{\"transferId\":null,\"transferTypeId\":1," +
                            "\"transferStatusId\":1,\"accountFrom\":1,\"accountTo\":2,\"amount\":100.0}"))
                    .andRespond(withSuccess("", MediaType.APPLICATION_JSON));
            boolean success = transferService.createTransfer(transfer);
            mockServer.verify();
            assertTrue(success);
        }

        @Test
        public void updateTransfer_returnsTrueForSuccessfulUpdate() {
            // Arrange
            Transfer transferToUpdate = transferService.getTransferById(1);
            BigDecimal updatedAmount = BigDecimal.valueOf(1000);
            transferToUpdate.setAmount(updatedAmount);

            // Act
            boolean updateSuccess = transferService.updateTransfer(transferToUpdate);

            // Assert
            assertTrue(updateSuccess, "Expected updateTransfer to return true for successful update");
            Transfer updatedTransfer = transferService.getTransferById(1);
            assertEquals(updatedAmount, updatedTransfer.getAmount(), "Expected amount to be updated in transfer");
        }

        @Test
        public void deleteTransfer_returnsTrueForSuccessfulDelete() {
            // Arrange
            Transfer newTransfer = new Transfer();
            newTransfer.setTransferTypeId(2L);
            newTransfer.setTransferStatusId(2L);
            newTransfer.setAccountFrom(1L);
            newTransfer.setAccountTo(2L);
            newTransfer.setAmount(BigDecimal.valueOf(100));
            transferService.createTransfer(newTransfer);
            Transfer createdTransfer = transferService.getAllTransfers()[transferService.getAllTransfers().length - 1];

            // Act
            boolean deleteSuccess = transferService.deleteTransfer(createdTransfer.getTransferId());

            // Assert
            assertTrue(deleteSuccess, "Expected deleteTransfer to return true for successful delete");
            Transfer[] transfersAfterDelete = transferService.getAllTransfers();
            assertFalse(containsTransfer(transfersAfterDelete, createdTransfer), "Expected deleted transfer to be removed from server");
        }
        private boolean containsTransfer(Transfer[] transfers, Transfer targetTransfer) {
            for (Transfer transfer : transfers) {
                if (Objects.equals(transfer.getTransferId(), targetTransfer.getTransferId())) {
                    return true;
                }
            }
            return false;
        }
    }
