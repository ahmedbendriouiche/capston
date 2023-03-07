package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferHistoryDto;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.TransferStatusService;
import com.techelevator.tenmo.services.TransferTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferStatusService statusService;
    @Autowired
    private TransferTypeService typeService;


//    public TransferController(TransferService transferService) {
//        this.transferService = transferService;
//    }

    /**
     * Retrieves a list of all transfers.
     *
     * @return A ResponseEntity object containing a list of Transfer objects
     */
    @GetMapping
    public ResponseEntity<List<Transfer>> getAllTransfers() {
        List<Transfer> transfers = transferService.getAllTransfers();
        return ResponseEntity.ok(transfers);
    }

    /**
     * Retrieves a specific transfer by ID.
     *
     * @param transferId the ID of the transfer to retrieve
     * @return a ResponseEntity containing the Transfer object with the specified ID and a status code of 200 (OK),
     * or a status code of 404 (Not Found) if the transfer is not found
     */
    @GetMapping("/{transferId}")
    public ResponseEntity<Transfer> getTransferById(@PathVariable int transferId) {
        Transfer transfer = transferService.getTransferById(transferId);
        if (transfer == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(transfer);
        }
    }

    /**
     * Retrieves a list of transfers associated with the specified user.
     *
     * @param userId the ID of the user whose transfers should be retrieved
     * @return a ResponseEntity containing a list of Transfer objects if the user exists, or a 404 Not Found status
     * otherwise
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<TransferHistoryDto>> getAllTransfersByUser(@PathVariable long userId) {
        List<TransferHistoryDto> history = transferService.getAllTransfersByUser(userId);
        return ResponseEntity.ok(history);
    }

    /**
     * Creates a new transfer based on the provided Transfer object.
     *
     *
     * @return A ResponseEntity containing the created Transfer object.
     */
    @PostMapping("/new")
    public ResponseEntity<Transfer> createTransfer(@RequestParam long userFrom, @RequestParam long userTo,
    @RequestParam BigDecimal amount) throws Exception{
        TransferStatus status = statusService.getStatus(0, "Approved");
        TransferType type = typeService.getTypeByName("Send");
        Transfer transfer = transferService.createTransfer(type, status, userFrom, userTo, amount);

        return new ResponseEntity<>(transfer, HttpStatus.CREATED);
    }

    /**
     * Updates an existing transfer based on the provided Transfer object.
     *
     * @param transfer The Transfer object to update.
     * @return A ResponseEntity containing the updated Transfer object.
     */
    @PutMapping("/{transferId}")
    public ResponseEntity<Transfer> updateTransfer(@Valid @RequestBody Transfer transfer,
                                                   @PathVariable long transferId) {
        // Make sure the transfer ID from the path variable matches the transfer ID in the request body
        if (transfer.getTransferId() != transferId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer ID in path variable does not match " +
                    "Transfer ID in request body");
        }

        // Update the transfer in the database using the TransferService
        transferService.updateTransfer(transfer);

        // Retrieve the updated transfer from the database and return it in the response body
        Transfer updatedTransfer = transferService.getTransferById(transferId);
        return ResponseEntity.ok(updatedTransfer);
    }

    /**
     * Deletes the transfer with the specified ID.
     *
     * @param transferId the ID of the transfer to delete
     */
    @DeleteMapping("/{transferId}")
    public void deleteTransfer(@PathVariable long transferId) {
        transferService.deleteTransfer(transferId);
    }
}