package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;

public interface TransferStatusDao {

    TransferStatus getStatusById(int id);
    TransferStatus getStatusByName(String name);
}
