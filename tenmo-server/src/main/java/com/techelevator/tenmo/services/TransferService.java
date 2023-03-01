package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface TransferService {

    List<Transfer> getAllTransfers();
    List<Transfer> getTransfersByUserId(long userId);
    Transfer getTransferById(long transferId);
    List<Transfer> getAllTransfersByUser(long userId);
    void createTransfer(Transfer transfer);
    void deleteTransfer(long transferId);
    void updateTransfer(Transfer transfer);

}

