package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferStatus;

import java.util.List;


public interface TransferStatusService {

    List<TransferStatus> listAll();
    TransferStatus getStatusByName(String name);
    TransferStatus getStatusById(int id);

}
