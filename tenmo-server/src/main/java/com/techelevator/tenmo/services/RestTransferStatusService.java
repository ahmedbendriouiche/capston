package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestTransferStatusService implements TransferStatusService {

    @Autowired
    TransferStatusDao transferStatusDao; /* Connects service layer to the dao */

    @Override
    public List<TransferStatus> listAll() {
        return transferStatusDao.listAll();
    }

    @Override
    public TransferStatus getStatusByName(String name) {
        return transferStatusDao.getStatusByName(name);
    }

    @Override
    public TransferStatus getStatusById(int id) {
        return transferStatusDao.getStatusById(id);
    }
}
