package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.exceptions.InvalidParameterException;
import com.techelevator.tenmo.exceptions.TransferStatusNotFoundException;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class RestTransferStatusService implements TransferStatusService {

    @Autowired
    TransferStatusDao transferStatusDao; /* Connects service layer to the dao */

    @Override
    public List<TransferStatus> listAll(Principal principal) throws Exception{
        List<TransferStatus> statusList;

        if (principal.getName().equals("admin")) {
            statusList = transferStatusDao.listAll();
        } else {
            throw new AccessDeniedException("Access denied.");
        }

        return statusList;
    }

    @Override
    public TransferStatus getStatus(long id, String name) throws Exception{
        TransferStatus status = null;

        int validationCode = validateParameters(id, name);

        if (validationCode == 1 || validationCode == 2)
            throw new InvalidParameterException();
        if (validationCode == 3 || validationCode == 4)
            status = generateTransferStatus(validationCode, id, name);
        if (status == null) throw new TransferStatusNotFoundException();

        return status;
    }

    // Generates a code based on parameter input that is then used to decide which response to send
    private int validateParameters(long id, String name) {
        int validationCode;

        if (name != null && id > 0) validationCode = 1;         /* Multiple inputs */
        else if (name == null && id == 0) validationCode = 2;   /* No inputs */
        else if (name != null) validationCode = 3;              /* Search by name */
        else validationCode = 4;                                /* Search by id */

        return validationCode;
    }

    // Generates a response letting the user know that they need to either add a parameter or use only one
    private String generateMessage(int code) {
        String message = null;

        if (code == 1)
            message = "Multiple inputs found. Please search for either a status id or description.";
        if (code == 2)
            message =  "No input found. Please search for either a status id or description.";

        return message;
    }

    // Generates a response containing a TransferStatus or letting the user know that a status wasn't found
    private TransferStatus generateTransferStatus(int code, long id, String name) {
        TransferStatus status = null;

        if (code == 3) status = transferStatusDao.getStatusByName(name);
        if (code == 4) status =  transferStatusDao.getStatusById(id);

        return status;
    }
}
