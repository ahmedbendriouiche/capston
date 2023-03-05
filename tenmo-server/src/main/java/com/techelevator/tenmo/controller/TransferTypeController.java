package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.services.RestTransferTypeService;
import com.techelevator.tenmo.services.TransferTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/transfers/type")
public class TransferTypeController {

    @Autowired
    private TransferTypeService transferTypeService ;

    @GetMapping(path = "/{id}")
    public ResponseEntity<TransferType> getTypeById(@RequestParam(defaultValue = "0") int id) {
        TransferType transferType = transferTypeService.getTypeById(id);
        return ResponseEntity.ok(transferType);
    }

    @GetMapping(path = "/{typename}")
    public ResponseEntity<TransferType> getTypeByName(@RequestParam(defaultValue = "") String name) {
        TransferType transferType = transferTypeService.getTypeByName(name);
        return ResponseEntity.ok(transferType);
    }
}
