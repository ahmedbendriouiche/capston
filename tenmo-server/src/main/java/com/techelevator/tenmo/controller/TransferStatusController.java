package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcTransferStatusDao;
import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.services.TransferStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
    To be merged with TransferController
 */

@RestController
@RequestMapping(path = "/transfer/status")
public class TransferStatusController {

    @Autowired
    private TransferStatusService transferStatusService; /* Connects controller to service layer */

    /*
        Currently returns a list of all statuses in the transfer_status table. This is for testing purposes
        only, and will be removed at a later date so that the method only returns a single TransferStatus. Calls the
        getStatusByName() method if parameters contain a name, and getStatusById() if no name is present.
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> getStatus(@RequestParam(defaultValue = "0") int id,
                                    @RequestParam(required = false) String name) {
        if (id == 0 && name == null) {
            // To be replaced with an error message ["Error: Please enter an ID or Name."]
            List<TransferStatus> statuses = transferStatusService.listAll();
            return ResponseEntity.ok(statuses);
        } else if (name != null) {
            TransferStatus status = transferStatusService.getStatusByName(name);
            return ResponseEntity.ok(status);
        } else {
            TransferStatus status = transferStatusService.getStatusById(id);
            return ResponseEntity.ok(status);
        }
    }
}
