package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;

public interface TransferTypeDao {
    TransferType getTypeById(int id);
    TransferType getTypeByName(String name);
}
