package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface TransferService {

    List<Transfer> getAllTransfers();
    List<Transfer> getTransfersByUserId(long userId);
    Transfer getTransferById(long transferId);
    List<Transfer> getAllTransfersByUser(long userId);
    Transfer createTransfer(TransferType type, TransferStatus status, long accountFrom, long accountTo, BigDecimal amount);
    void deleteTransfer(long transferId);
    void updateTransfer(Transfer transfer);

}

