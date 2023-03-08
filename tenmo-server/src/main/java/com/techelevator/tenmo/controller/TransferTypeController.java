package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.services.RestTransferTypeService;
import com.techelevator.tenmo.services.TransferTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/transfers/type")
public class TransferTypeController {

    @Autowired
    private TransferTypeService transferTypeService ;

    @GetMapping(path = "/{id}")
    public ResponseEntity<TransferType> getTypeById(@PathVariable long id) {
        TransferType transferType = transferTypeService.getTypeById(id);
        return ResponseEntity.ok(transferType);
    }

    @GetMapping(path = "")
    public ResponseEntity<TransferType> getTypeByName(@RequestParam(defaultValue = "") String name) {
        TransferType transferType = transferTypeService.getTypeByName(name);
        return ResponseEntity.ok(transferType);
    }
}
