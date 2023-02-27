package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;

import java.util.List;

public interface TransferTypeService {
    List<TransferType> listAll();
    TransferType getTypeByName(String name);
    TransferType getTypeById(int id);
}
