package com.techelevator.tenmo;


import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final CustomerAccountService customerAccountService =  new CustomerAccountService() ;
    private final UserInfoService userInfoService = new UserInfoService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);
    private final TransferStatusService statusService = new TransferStatusService(API_BASE_URL);
    private final TransferTypeService typeService = new TransferTypeService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);

        // Setting AuthenticatedUser for services
        statusService.setCurrentUser(currentUser);
        typeService.setCurrentUser(currentUser);
        transferService.setCurrentUser(currentUser);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
//                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
//                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }


	private void viewCurrentBalance() {
        // create customer targeted account
        CustomerDto customerReq = new CustomerDto();
        customerReq.setId(currentUser.getUser().getId());
        customerReq.setName(currentUser.getUser().getUsername());

        // initiate the service request
        customerAccountService.setBaseUrl(API_BASE_URL+"accounts/");
        customerAccountService.setToken(currentUser.getToken());
        customerAccountService.setCustomerDto(customerReq);

		// instantiate customer request service
        System.out.println(customerAccountService.getUserGeneralBalance().getBalance());
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        TransferHistoryDto[] history = transferService.getAllTransfersByUser();
        String hyphenSeparator = "--------------------------------------------------" +
                "--------------------------------------";

        System.out.println(hyphenSeparator);
        System.out.printf("%-34s%s%34s\n", "|", "<<TRANSFER HISTORY>>", "|");
        System.out.println(hyphenSeparator);
        System.out.printf("| %-5s | %-56s | %-17s |\n","ID","FROM/TO","AMOUNT");
        System.out.println(hyphenSeparator);
        for (TransferHistoryDto transfer : history) {
            System.out.println(transfer.toString(currentUser));
        }
        System.out.println(hyphenSeparator);
        transfersOptions(history);
	}

    private void transfersOptions(TransferHistoryDto[] transfers) {
        List<Long> tranferIds = createListOfTransferIds(transfers);
        long optionSelection = -1;
        while (optionSelection != 0) {
            consoleService.printTransfersOptions();
            optionSelection = consoleService.promptForLong("\nPlease enter your selection: ");
            if (tranferIds.contains(optionSelection)) {
                System.out.println("Transfer shows up here.");
                viewTransfer(optionSelection, transfers);
            } else if (optionSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewTransfer(long transferId, TransferHistoryDto[] histories) {
        Transfer transfer = transferService.getTransferById(transferId);
        TransferType type = typeService.getTypeById(transfer.getTransferTypeId());
        TransferStatus status = statusService.getStatusById(transfer.getTransferStatusId());
        TransferDetailsDto details = new TransferDetailsDto();
        for (TransferHistoryDto history : histories) {
            if (history.getTransferId() == transferId) {
                details.setTransferId(history.getTransferId());
                details.setAccountFrom(history.getUserFrom());
                details.setAccountTo(history.getUserTo());
                details.setTransferType(type.getTransferTypeDesc());
                details.setTransferStatus(status.getTransferStatusDesc());
                details.setAmount(history.getAmount());
                break;
            }
        }
        System.out.println(details);
    }

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        customerAccountService.setBaseUrl(API_BASE_URL+"accounts/");
        customerAccountService.setToken(currentUser.getToken());
        UserInfoDto[] infoArray = userInfoService.getUserInfos(currentUser);

        System.out.println("-------------------------------------------");
        System.out.printf("| %-39s |\n", "USERS");
        System.out.println("-------------------------------------------");
        System.out.printf("| %-11s | %-25s |\n","ID","NAME");
        System.out.println("-------------------------------------------");
        for (UserInfoDto info : infoArray) {
            System.out.println(info);
        }
        System.out.println("-------------------------------------------\n");


        //Prompt for id and transfer amount
        long id = consoleService.promptForId("Enter ID of user you are sending to (0 to cancel): ");
        BigDecimal amount = consoleService.promptForAmount("Enter amount: ");

        //Updates current user and target user for transfer
        boolean success = customerAccountService.accountBalanceUpdate(id,currentUser.getUser().getId(),amount);
        if (success) {
            transferService.createTransfer(id, amount);
        } else {
            consoleService.printErrorMessage();
        }
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

    private List<Long> createListOfTransferIds(TransferHistoryDto[] historyDtoList) {
        List<Long> transferIds = new ArrayList<>();
        for (TransferHistoryDto historyDto : historyDtoList) {
            transferIds.add(historyDto.getTransferId());
        }

        return transferIds;
    }

}
