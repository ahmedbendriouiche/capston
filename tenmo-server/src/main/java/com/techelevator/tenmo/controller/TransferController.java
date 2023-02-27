package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }
    /**
     * Retrieves a list of all transfers.
     *
     * @return A ResponseEntity object containing a list of Transfer objects
     */
    @GetMapping
    public ResponseEntity<List<Transfer>> getAllTransfers() {
        List<Transfer> transfers = transferDao.getAllTransfers();
        return ResponseEntity.ok(transfers);
    }
    /**
     * Retrieves a specific transfer by ID.
     *
     * @param transferId the ID of the transfer to retrieve
     * @return a ResponseEntity containing the Transfer object with the specified ID and a status code of 200 (OK), or a status code of 404 (Not Found) if the transfer is not found
     */
    @GetMapping("/{transferId}")
    public ResponseEntity<Transfer> getTransferById(@PathVariable int transferId) {
        Transfer transfer = transferDao.getTransferById(transferId);
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
     * @return a ResponseEntity containing a list of Transfer objects if the user exists, or a 404 Not Found status otherwise
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Transfer>> getTransfersByUserId(@PathVariable int userId) {
        List<Transfer> transfers = transferDao.getTransfersByUserId(userId);
        return ResponseEntity.ok(transfers);
    }
    /**
     * Creates a new transfer based on the provided Transfer object.
     *
     * @param transfer The Transfer object to create.
     * @return A ResponseEntity containing the created Transfer object.
     */
    @PostMapping
    public ResponseEntity<Transfer> createTransfer(@RequestBody Transfer transfer) {
        Transfer createdTransfer = transferDao.createTransfer(transfer.getTransferTypeId(),
                transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(),
                transfer.getAmount());
        return ResponseEntity.ok(createdTransfer);
    }
    /**
     * Deletes the transfer with the specified ID.
     *
     * @param transferId the ID of the transfer to delete
     */
    @DeleteMapping("/{transferId}")
    public void deleteTransfer(@PathVariable long transferId) {
        transferDao.deleteTransfer(transferId);
    }
}