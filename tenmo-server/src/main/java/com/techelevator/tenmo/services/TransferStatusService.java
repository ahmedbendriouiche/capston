package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferStatus;

import java.security.Principal;
import java.util.List;


public interface TransferStatusService {

    List<TransferStatus> listAll(Principal principal) throws Exception;
    TransferStatus getStatus(long id, String name) throws Exception;

}
