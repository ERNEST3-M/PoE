package part3_poe;

import java.util.Scanner;

public class QuickChatApp {
    private final String loggedInUser;
    private final StoredDataManager dataManager;

    public QuickChatApp(String username) {
        this.loggedInUser = username;
        this.dataManager = new StoredDataManager(username);
    }

    public void start(Scanner scanner) {
        System.out.println("\n--- STARTING QUICKCHAT APPLICATION ---");
        boolean appRunning = true;

        while (appRunning) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Send a message");
            System.out.println("2. Display Sent/Disregarded Messages");
            System.out.println("3. Save Messages to JSON File");
            System.out.println("4. Stored Messages (Submenu)");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid entry, enter a number");
                scanner.nextLine();
                continue;
            }
            
            int mainChoice = scanner.nextInt();
            scanner.nextLine(); 

            switch (mainChoice) {
                case 1:
                    processNewMessage(scanner);
                    break;
                case 2:
                    Message.displayMessages();
                    break;
                case 3:
                    JsonStorageHandler.saveMessagesToJson();
                    break;
                case 4:
                    dataManager.loadFromJson();
                    dataManager.openSubMenu(scanner);
                    break;
                case 5:
                    appRunning = false;
                    System.out.println("Exiting QuickChat. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice selection.");
            }
        }
    }

    private void processNewMessage(Scanner scanner) {
        String recipient = "";
        boolean validRecipient = false;
        
        // Forces continuous loop until a valid SA phone number given
        while (!validRecipient) {
            System.out.print("Enter recipient's phone number (e.g., 0823456789 or +27823456789): ");
            recipient = scanner.nextLine().replaceAll("\\s+", "");
            
            // Temporary object instance solely used to run the regex check method rules
            Message validationCheck = new Message(0, recipient, "");
            if (validationCheck.checkRecipientCell()) {
                validRecipient = true;
            } else {
                System.out.println("Invalid format. Please use a valid South African mobile format.\n");
            }
        }
        
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();
        
        Message newMessage = new Message(Message.returnTotalMessages() + 1, recipient, message);
        System.out.println("\nGenerated ID: " + newMessage.getMessageID());
        System.out.println("Generated Hash: " + newMessage.createMessageHash());
        
        System.out.println("\nWhat would you like to do with this message?");
        System.out.println("1 - Send | 2 - Store | 0 - Disregard");
        System.out.print("Select an action: ");
        
        if (!scanner.hasNextInt()) {
            System.out.println("An error has occured");
            scanner.nextLine();
            return;
        }
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        // Passes standard choice cleanly down to internal array systems
        System.out.println(newMessage.sentMessage(choice));
    }
}