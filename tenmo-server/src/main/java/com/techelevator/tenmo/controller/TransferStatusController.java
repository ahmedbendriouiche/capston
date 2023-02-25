package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcTransferStatusDao;
import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/transfer/status")
public class TransferStatusController {

    private TransferStatusDao transferStatusDao;

    public TransferStatusController(TransferStatusDao transferStatusDao) {
        this.transferStatusDao = transferStatusDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public TransferStatus getStatusById(@RequestParam(defaultValue = "0") int id, @RequestParam(required = false) String name) {
        TransferStatus status;
        if (name == null) {
            status = transferStatusDao.getStatusById(id);
        } else {
            status = transferStatusDao.getStatusByName(name);
        }
        return status;
    }
}
