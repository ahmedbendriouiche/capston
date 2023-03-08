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

@Service
public class RestTransferService implements TransferService {

    @Autowired
    private TransferDao transferDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }

    @Override
    public Transfer getTransferById(long transferId) {
        return transferDao.getTransferById(transferId);
    }
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
    @Override
    public void updateTransfer(Transfer transfer) {
        transferDao.updateTransfer(transfer);
    }
    @Override
    public void deleteTransfer(long transferId) {
        transferDao.deleteTransfer(transferId);
    }

}