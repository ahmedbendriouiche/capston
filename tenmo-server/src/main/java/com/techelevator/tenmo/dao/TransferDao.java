package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfers();

    Transfer getTransferById(long id);

    List<Transfer> getAllTransfersByUser(long userId);

    Transfer createTransfer(long typeId, long statusId, long accountFrom, long accountTo, BigDecimal amount);

    void deleteTransfer(long transferId);

    void updateTransfer(Transfer transfer);
}