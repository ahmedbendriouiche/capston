package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The RestTransferService class implements the TransferService interface and provides
 * methods for managing transfers between user accounts.
 * Service layer implementation of the TransferService interface.
 * Acts as a middleman between the controller layer
 * and the DAO layer for transfer-related functionality.
 */
@Service
public class RestTransferService implements TransferService {

    @Autowired
    private TransferDao transferDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

    /**

     Constructor that creates a new RestTransferService object with the specified TransferDao and AccountDao objects.
     @param transferDao a TransferDao object for interacting with transfer data in the database
     @param accountDao an AccountDao object for interacting with account data in the database
     */
    public RestTransferService(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    /**
     * Returns a list of all transfers in the system.
     *
     * @return a list of Transfer objects representing all transfers
     *         in the system
     */
    @Override
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }
    /**
     * Returns the Transfer object with the specified ID.
     *
     * @param transferId the ID of the transfer to retrieve
     * @return the Transfer object with the specified ID, or null if no
     *         such transfer exists
     */
    @Override
    public Transfer getTransferById(long transferId) {
        return transferDao.getTransferById(transferId);
    }

    /**
     * Retrieves a list of transfer history for a given user, including transfers where the user is either the sender or receiver.
     *
     * @param userId the ID of the user for whom to retrieve the transfer history
     * @return a list of TransferHistoryDto objects representing the transfer history for the given user
     */
    @Override
    public List<TransferHistoryDto> getAllTransfersByUser(long userId) {
        long accountId = accountDao.getAccountIdByUserId(userId);
        List<Transfer> transfers = transferDao.getAllTransfersByUser(accountId);
        List<TransferHistoryDto> history = new ArrayList<>();

        for (Transfer transfer : transfers) {
            long id = transfer.getTransferId();
            String from = userDao.getUsernameByAccountId(transfer.getAccountFrom());
            String to = userDao.getUsernameByAccountId(transfer.getAccountTo());
            BigDecimal amount = transfer.getAmount();

            history.add(new TransferHistoryDto(id, from, to, amount));
        }
        return history;
    }
    /**
     * Creates a new transfer with the given information.
     *
     * @param type the type of transfer to create
     * @param status the status of the transfer to create
     * @param userFrom the ID of the user sending the transfer
     * @param userTo the ID of the user receiving the transfer
     * @param amount the amount to transfer
     * @return the newly created Transfer object
     */
    @Override
    public Transfer createTransfer(TransferType type, TransferStatus status, long userFrom, long userTo,
                            BigDecimal amount) {
        long accountFrom = accountDao.getAccountIdByUserId(userFrom);
        long accountTo = accountDao.getAccountIdByUserId(userTo);
        long typeId = type.getTransferTypeId();
        long statusId = status.getTransferStatusId();
        Transfer transfer = transferDao.createTransfer(typeId, statusId, accountFrom, accountTo, amount);

        return transfer;
    }
    /**

     Updates an existing transfer in the database.
     @param transfer a Transfer object representing the updated transfer data
     */
    @Override
    public void updateTransfer(Transfer transfer) {
        transferDao.updateTransfer(transfer);
    }
    /**

     Deletes a transfer from the database by its ID.
     @param transferId the ID of the transfer to delete
     */
    @Override
    public void deleteTransfer(long transferId) {
        transferDao.deleteTransfer(transferId);
    }

}