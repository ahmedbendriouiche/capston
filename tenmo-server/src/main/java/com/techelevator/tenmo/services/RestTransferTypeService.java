package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RestTransferTypeService implements TransferTypeService{
    @Autowired
    TransferTypeDao transferTypeDao;

    @Override
    public List<TransferType> listAll() {
        return transferTypeDao.listAll();
    }

    @Override
    public TransferType getTypeByName(String name) {
        return transferTypeDao.getTypeByName(name);
    }

    @Override
    public TransferType getTypeById(int id) {
        return transferTypeDao.getTypeById(id);
    }
}
