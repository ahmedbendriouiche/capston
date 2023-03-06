package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.services.TransferStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping(path = "/transfers/status")
public class TransferStatusController {

    @Autowired
    private TransferStatusService transferStatusService; /* Connects controller to service layer */

    // A request available only to a user named "admin". Returns all statuses in the database.
    @GetMapping(path = "/listall")
    public ResponseEntity getStatuses(Principal principal) throws Exception {
        List<TransferStatus> statusList = transferStatusService.listAll(principal);
        ResponseEntity<List<TransferStatus>> response = new ResponseEntity<>(statusList, HttpStatus.OK);

        return response;
    }


    // Returns a status found by either its name or id. Returns a bad request if given either both or no parameters.
    @GetMapping(path = "")
    public ResponseEntity<TransferStatus> getStatus(@RequestParam(defaultValue = "0") long id,
                                    @RequestParam(required = false) String name)  throws Exception {
        TransferStatus status = transferStatusService.getStatus(id, name);
        ResponseEntity<TransferStatus> response = new ResponseEntity<TransferStatus>(status, HttpStatus.OK);

        return response;
    }
}
