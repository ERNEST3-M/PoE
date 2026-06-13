package part3_poe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class StoredDataManager {
    private final String[] storedMessageIDs = new String[100];
    private final String[] storedMessageHashes = new String[100];
    private final String[] storedRecipients = new String[100];
    private final String[] storedMessages = new String[100];
    private final String[] storedSenders = new String[100];
    private int recordCount = 0;
    private final String loggedInSender;

    public StoredDataManager(String senderUsername) {
        this.loggedInSender = senderUsername;
    }

    // Scans across both path frameworks automatically to read JSON datasets smoothly
    public void loadFromJson() {
        recordCount = 0; 
        File file = new File("messages.json");
        if (!file.exists()) {
            file = new File(System.getProperty("user.home"), "messages.json");
        }
        if (!file.exists()) {
            System.out.println("No JSON file data detected. Please use Main Menu Option 3 first.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String currentId = "", currentHash = "", currentRecipient = "", currentContent = "";
            
            while ((line = br.readLine()) != null) {
                if (line.contains("\"messageId\":")) currentId = extractJsonValue(line);
                if (line.contains("\"messageHash\":")) currentHash = extractJsonValue(line);
                if (line.contains("\"recipient\":")) currentRecipient = extractJsonValue(line);
                if (line.contains("\"messageContent\":")) {
                    currentContent = extractJsonValue(line);
                    
                    if (recordCount < 100) {
                        storedMessageIDs[recordCount] = currentId;
                        storedMessageHashes[recordCount] = currentHash;
                        storedRecipients[recordCount] = currentRecipient;
                        storedMessages[recordCount] = currentContent;
                        storedSenders[recordCount] = loggedInSender; 
                        recordCount++;
                    }
                }
            }
            System.out.println("Successfully pulled " + recordCount + " active log items out of stored messeages.");
        } catch (IOException e) {
            System.out.println("Error reading storage stream: " + e.getMessage());
        }
    }

    // Fixed index lookup logic ensuring colons embedded in texts won't break parsing patterns
    private String extractJsonValue(String line) {
        int firstColonIndex = line.indexOf(":");
        if (firstColonIndex != -1) {
            return line.substring(firstColonIndex + 1)
                       .replace("\"", "")
                       .replace(",", "")
                       .trim();
        }
        return "";
    }

    public void openSubMenu(Scanner scanner) {
        boolean inSubMenu = true;
        while (inSubMenu) {
            System.out.println("\n--- STORED MESSAGES SUBMENU ---");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search for messages by recipient");
            System.out.println("e. Delete a message using hash");
            System.out.println("f. Display full details report");
            System.out.println("g. Return to Main Menu");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine().toLowerCase().trim();

            switch (choice) {
                case "a":
                    displaySendersAndRecipients();
                    break;
                case "b":
                    displayLongestMessage();
                    break;
                case "c":
                    System.out.print("Enter Message ID to search: ");
                    searchByID(scanner.nextLine().trim());
                    break;
                case "d":
                    System.out.print("Enter Recipient number to search: ");
                    searchByRecipient(scanner.nextLine().trim());
                    break;
                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    deleteByHash(scanner.nextLine().trim());
                    break;
                case "f":
                    displayFullReport();
                    break;
                case "g":
                    inSubMenu = false;
                    break;
                default:
                    System.out.println("Invalid structural assignment selected.");
            }
        }
    }

    private void displaySendersAndRecipients() {
        System.out.println("\n-- SENDER & RECIPIENT LOG --");
        if (recordCount == 0) System.out.println("No entries stored.");
        for (int i = 0; i < recordCount; i++) {
            System.out.println("Sender: " + storedSenders[i] + " | Recipient: " + storedRecipients[i]);
        }
    }

    private void displayLongestMessage() {
        if (recordCount == 0) {
            System.out.println("Operation canceled: storage empty.");
            return;
        }
        int maxLength = -1;
        int longestIndex = 0;
        
        for (int i = 0; i < recordCount; i++) {
            if (storedMessages[i].length() > maxLength) {
                maxLength = storedMessages[i].length();
                longestIndex = i;
            }
        }
        System.out.println("\n-- LONGEST MESSAGE --");
        System.out.println("Length: " + maxLength + " characters");
        System.out.println("Content: " + storedMessages[longestIndex]);
    }

    public void searchByID(String searchID) {
        boolean found = false;
        for (int i = 0; i < recordCount; i++) {
            if (storedMessageIDs[i].equals(searchID)) {
                System.out.println("\nMatch Found!");
                System.out.println("Sender: " + storedSenders[i]);
                System.out.println("Recipient: " + storedRecipients[i]);
                System.out.println("Message: " + storedMessages[i]);
                found = true;
                break;
            }
        }
        if (!found) System.out.println("No message found with ID: " + searchID);
    }

    private void searchByRecipient(String recipient) {
        boolean found = false;
        System.out.println("\n-- MESSAGES FOR RECIPIENT: " + recipient + " --");
        for (int i = 0; i < recordCount; i++) {
            if (storedRecipients[i].equals(recipient)) {
                System.out.println("- [" + storedMessageIDs[i] + "]: " + storedMessages[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No matching records found for this specific target.");
    }

    void deleteByHash(String hash) {
        boolean found = false;
        for (int i = 0; i < recordCount; i++) {
            if (storedMessageHashes[i].equalsIgnoreCase(hash)) {
                found = true;
                
                // Bubble shifting array parameters back to clear the deletion index void
                for (int j = i; j < recordCount - 1; j++) {
                    storedMessageIDs[j] = storedMessageIDs[j + 1];
                    storedMessageHashes[j] = storedMessageHashes[j + 1];
                    storedRecipients[j] = storedRecipients[j + 1];
                    storedMessages[j] = storedMessages[j + 1];
                    storedSenders[j] = storedSenders[j + 1];
                }
                recordCount--; 
                System.out.println("Message tracking reference removed from active memory array index.");
                
                // Immediately overwrite file data onto disk to ensure the change persists
                persistArrayChangesToDisk();
                break;
            }
        }
        if (!found) System.out.println("Hash identity look-up error. Operation halted.");
    }

    private void persistArrayChangesToDisk() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");
        for (int i = 0; i < recordCount; i++) {
            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"messageId\": \"").append(storedMessageIDs[i]).append("\",\n");
            jsonBuilder.append("    \"messageHash\": \"").append(storedMessageHashes[i]).append("\",\n");
            jsonBuilder.append("    \"recipient\": \"").append(storedRecipients[i]).append("\",\n");
            jsonBuilder.append("    \"messageContent\": \"").append(storedMessages[i]).append("\"\n");
            jsonBuilder.append(i < recordCount - 1 ? "  },\n" : "  }\n");
        }
        jsonBuilder.append("]");

        File targetFile = JsonStorageHandler.getSafeStoragePath();

        try (FileWriter file = new FileWriter(targetFile)) {
            file.write(jsonBuilder.toString());
            System.out.println(">> System Notice: Changes completely saved down to local disk records storage.");
        } catch (IOException e) {
            System.out.println(">> System Error updating disk storage payload: " + e.getMessage());
        }
    }

    void displayFullReport() {
        System.out.println("\n=== FULL STORED MESSAGES REPORT ===");
        if (recordCount == 0) System.out.println("No records available inside active scope indices.");
        
        for (int i = 0; i < recordCount; i++) {
            System.out.println("Record #" + (i + 1));
            System.out.println("ID:        " + storedMessageIDs[i]);
            System.out.println("Hash:      " + storedMessageHashes[i]);
            System.out.println("Sender:    " + storedSenders[i]);
            System.out.println("Recipient: " + storedRecipients[i]);
            System.out.println("Message:   " + storedMessages[i]);
            System.out.println("------------------------------------");
        }
    }
}