package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.services.TransferStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping(path = "/transfers/status")
public class TransferStatusController {

    @Autowired
    private TransferStatusService transferStatusService; /* Connects controller to service layer */

    // A request available only to a user named "admin". Returns all statuses in the database.
    @GetMapping(path = "/listAll")
    public ResponseEntity getStatuses(Principal principal) {
        if (principal.getName().equals("admin")) {
            return ResponseEntity.ok(transferStatusService.listAll());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
    }


    // Returns a status found by either its name or id. Returns a bad request if given either both or no parameters.
    @GetMapping(path = "")
    public ResponseEntity getStatus(@RequestParam(defaultValue = "0") long id,
                                    @RequestParam(required = false) String name) {
        ResponseEntity response = null;
        int validationCode = validateParameters(id, name);

        if (validationCode == 1 || validationCode == 2)
            response =  generateResponseWithError(validationCode);
        if (validationCode == 3 || validationCode == 4)
            response =  generateResponseWithTransferStatus(validationCode, id, name);

        return response;
    }

    private int validateParameters(long id, String name) {
        int validationCode;

        if (name != null && id > 0) validationCode = 1;         /* Multiple inputs */
        else if (name == null && id == 0) validationCode = 2;   /* No inputs */
        else if (name != null) validationCode = 3;              /* Search by name */
        else validationCode = 4;                                /* Search by id */

        return validationCode;
    }

    // Generates a response letting the user know that they need to either add a parameter or use only one
    private ResponseEntity generateResponseWithError(int code) {
        ResponseEntity response = null;

        if (code == 1) response =
                ResponseEntity.badRequest()
                        .body("Multiple inputs found. Please search for either a status id or description.");
        if (code == 2) response =
                ResponseEntity.badRequest()
                        .body("No input found. Please search for either a status id or description.");

        return response;
    }

    // Generates a response containing a TransferStatus or letting the user know that a status wasn't found
    private ResponseEntity generateResponseWithTransferStatus(int code, long id, String name) {
        TransferStatus status = null;

        if (code == 3) status = transferStatusService.getStatusByName(name);
        if (code == 4) status =  transferStatusService.getStatusById(id);

        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status not found.");
        } else {
            return ResponseEntity.ok(status);
        }
    }
}
