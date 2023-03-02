package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.services.TransferStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        if (name != null && id > 0) {
            return ResponseEntity.badRequest().body("Multiple inputs found. Please search for either a status id or " +
                    "description.");
        }
        if ((name == null && id == 0) || (name != null && id > 0)) {
            return ResponseEntity.badRequest().body("No input found. Please search for either a status id or " +
                    "description.");
        }
        if (name != null) {
            return ResponseEntity.ok(transferStatusService.getStatusByName(name));
        }
        return ResponseEntity.ok(transferStatusService.getStatusById(id));
    }
}
