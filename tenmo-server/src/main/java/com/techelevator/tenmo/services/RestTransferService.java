package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class RestTransferService implements TransferService {

    @Autowired
    private TransferDao transferDao;
//    @Autowired
//    private AccountDao accountDao;

//    @Autowired
//    public RestTransferService(TransferDao transferDao, AccountDao accountDao) {
//        this.transferDao = transferDao;
//        this.accountDao = accountDao;
//    }
    @Override
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }

    @Override
    public Transfer getTransferById(long transferId) {
        return transferDao.getTransferById(transferId);
    }
    @Override
    public List<Transfer> getAllTransfersByUser(long userId) {
        return transferDao.getAllTransfersByUser(userId);
    }
    @Override
    public Transfer createTransfer(TransferType type, TransferStatus status, long accountFrom, long accountTo,
                            BigDecimal amount) {
        long typeId = type.getTransferTypeId();
        long statusId = status.getTransferStatusId();
        Transfer transfer = transferDao.createTransfer(typeId, statusId, accountFrom, accountTo, amount);

        return transfer;
    }
    @Override
    public void updateTransfer(Transfer transfer) {
        transferDao.updateTransfer(transfer);
    }
    @Override
    public void deleteTransfer(long transferId) {
        transferDao.deleteTransfer(transferId);
    }

}