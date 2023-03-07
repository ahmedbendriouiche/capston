package com.techelevator.tenmo;


import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.CustomerDto;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.CustomerAccountService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;


public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final CustomerAccountService customerAccountService =  new CustomerAccountService() ;

    private final TransferService transferService = new TransferService(API_BASE_URL);

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
                viewTransferHistory(currentUser);
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
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

	private void viewTransferHistory(AuthenticatedUser currentUser) {
		// TODO Auto-generated method stub
        Transfer[] transfers = transferService.getAllTransfers(currentUser);
        System.out.println("-------------------------------------------");
        System.out.println("TRANSFER HISTORY");
        System.out.println("ID     From     To     Amount");
        for (Transfer transfer : transfers) {
            System.out.println(transfer.getTransferId() + " " + transfer.getAccountFrom() + " " + transfer.getAccountTo()
            + " " + transfer.getAmount());
        }
        System.out.println("-------------------------------------------");
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        long id = consoleService.promptForId("Enter ID of user you are sending to (0 to cancel): ");
        BigDecimal amount = consoleService.promptForAmount("Enter amount: ");
        customerAccountService.setBaseUrl(API_BASE_URL+"accounts/");
        customerAccountService.setToken(currentUser.getToken());
        customerAccountService.accountBalanceUpdate(id,currentUser.getUser().getId(),amount);
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
