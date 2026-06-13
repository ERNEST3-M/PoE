package part3_poe;

import java.util.Random;

public class Message {
    private String messageID;
    private String recipient;
    private String messageContent;
    private int messageNumber;
    private static int totalSentMessages = 0;
    
    private static final String[] sentMessages = new String[100];
    private static final String[] disregardedMessages = new String[100];
    private static int sentCount = 0;
    private static int disregardedCount = 0;
    
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageContent() { return messageContent; }
    public static String[] getSentMessages() { return sentMessages; }
    public static int getMessageCount() { return sentCount; }

    public Message(int messageNumber, String recipient, String messageContent) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.messageID = generateID();
    }

    private String generateID() {
        Random random = new Random();
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            build.append(random.nextInt(10));
        }
        return build.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    // Refactored to run strict compliance checks matching SA cellular standards
    public boolean checkRecipientCell() {
        String phoneRegex = "^(\\+27|27|0)[6-8][0-9]{8}$";
        String cleanRecipient = recipient.replaceAll("\\s+", "");
        return cleanRecipient.matches(phoneRegex);
    }

    public String createMessageHash() {
        String firstTwoDigits = messageID.substring(0, 2);
        String[] words = messageContent.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        String hash = firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // Perfectly matched switch block configuration patterns
    public String sentMessage(int choice) {
        String details = printMessageDetails();
        
        switch (choice) {
            case 1: // Menu Action "1 - Send"
                if (sentCount >= 100) return "Error: Storage capacity maximized.";
                totalSentMessages++;
                sentMessages[sentCount++] = details;
                return "Message successfully sent and added to Sent messages.";
                
            case 2: // Menu Action "2 - Store"
                if (sentCount >= 100) return "Error: Storage capacity maximized.";
                storeMessage();
                return "Message successfully stored in system.";
                
            case 0: // Menu Action "0 - Disregard"
                if (disregardedCount >= 100) return "Error: Storage capacity maximized.";
                disregardedMessages[disregardedCount++] = details;
                return "Message discarded and added to Disregarded messages.";
                
            default:
                return "Invalid option selected. Processing abandoned.";
        }
    }

    public String printMessageDetails() {
        return "\nMessage ID: " + messageID +
               "\nMessage Hash: " + createMessageHash() +
               "\nRecipient: " + recipient +
               "\nMessage: " + messageContent;
    }

    public static int returnTotalMessages() {
        return totalSentMessages;
    }

    public void storeMessage() {
        sentMessages[sentCount++] = printMessageDetails();
    }

    public static void displayMessages() {
        System.out.println("\n--- SENT / STORED MESSAGES ---");
        if (sentCount == 0) System.out.println("Empty.");
        for (int i = 0; i < sentCount; i++) System.out.println(sentMessages[i]);

        System.out.println("\n--- DISREGARDED MESSAGES ---");
        if (disregardedCount == 0) System.out.println("Empty.");
        for (int i = 0; i < disregardedCount; i++) System.out.println(disregardedMessages[i]);
    }
}
