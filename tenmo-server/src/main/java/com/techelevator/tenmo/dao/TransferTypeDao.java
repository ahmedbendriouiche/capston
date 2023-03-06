package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;

import java.util.List;

public interface TransferTypeDao {

    List<TransferType> listAll();
    TransferType getTypeById(long id);
    TransferType getTypeByName(String name);
}
