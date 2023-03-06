package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class RestTransferTypeService implements TransferTypeService{
    @Autowired
    TransferTypeDao transferTypeDao;

    @Override
    public List<TransferType> listAll() {
        return transferTypeDao.listAll();
    }

    @Override
    public TransferType getTypeByName(String name) {
        TransferType transferType = transferTypeDao.getTypeByName(name);

        if(transferType == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type Not Found");
        } else {
            return transferTypeDao.getTypeByName(name);
        }

    }

    @Override
    public TransferType getTypeById(long id) {
        TransferType transferType = transferTypeDao.getTypeById(id);

        if(transferType == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found");
        } else {
            return transferTypeDao.getTypeById(id);
        }

    }
}
