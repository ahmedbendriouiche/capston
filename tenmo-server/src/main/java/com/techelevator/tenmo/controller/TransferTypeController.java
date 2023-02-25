package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/transfer/status")
public class TransferTypeController {
    private TransferTypeDao transferTypeDao;

    public TransferTypeController(TransferTypeDao transferTypeDao) {
        this.transferTypeDao = transferTypeDao;
    }

    @GetMapping
    public TransferType getTypeById(@RequestParam(defaultValue = "0") int id) {
        TransferType transferType = transferTypeDao.getTypeById(id);

        if(transferType == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Not Found");
        } else {
            return transferType;
        }
    }
}
