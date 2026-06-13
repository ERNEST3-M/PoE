package part3_poe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonStorageHandler {
    
    // Checks standard locations and flags user home directory context to bypass OS permissions issues
    public static File getSafeStoragePath() {
        File primaryFile = new File("messages.json");
        try {
            if (primaryFile.exists() && !primaryFile.canWrite()) {
                primaryFile.setWritable(true);
            }
            return primaryFile;
        } catch (Exception e) {
            return new File(System.getProperty("user.home"), "messages.json");
        }
    }

    public static void saveMessagesToJson() {
        int count = Message.getMessageCount();
        if (count == 0) {
            System.out.println("No messages available inside memory to synchronize.");
            return;
        }

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < count; i++) {
            String rawRecord = Message.getSentMessages()[i];
            String id = extractValue(rawRecord, "Message ID:");
            String hash = extractValue(rawRecord, "Message Hash:");
            String recipient = extractValue(rawRecord, "Recipient:");
            String content = extractValue(rawRecord, "Message:");

            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"messageId\": \"").append(id).append("\",\n");
            jsonBuilder.append("    \"messageHash\": \"").append(hash).append("\",\n");
            jsonBuilder.append("    \"recipient\": \"").append(recipient).append("\",\n");
            jsonBuilder.append("    \"messageContent\": \"").append(content).append("\"\n");
            
            if (i < count - 1) {
                jsonBuilder.append("  },\n");
            } else {
                jsonBuilder.append("  }\n");
            }
        }
        jsonBuilder.append("]");

        File targetFile = getSafeStoragePath();

        // Safe Write Sequence: Uses User Home location if root workspace denies write permissions
        try (FileWriter fileWriter = new FileWriter(targetFile)) {
            fileWriter.write(jsonBuilder.toString());
            System.out.println(">> System Notice: Messages successfully synced directly to file storage location:\n   " + targetFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println(">> Workspace Restricted. Diverting to alternate safe path extraction...");
            File fallbackFile = new File(System.getProperty("user.home"), "messages.json");
            
            try (FileWriter fallbackWriter = new FileWriter(fallbackFile)) {
                fallbackWriter.write(jsonBuilder.toString());
                System.out.println(">> Success: Saved smoothly to fallback path:\n   " + fallbackFile.getAbsolutePath());
            } catch (IOException ex) {
                System.out.println(">> Fatal Error: Storage configurations completely denied file engine writes: " + ex.getMessage());
            }
        }
    }

    private static String extractValue(String block, String label) {
        if (block == null || !block.contains(label)) return "";
        int start = block.indexOf(label) + label.length();
        int end = block.indexOf("\n", start);
        if (end == -1) {
            return block.substring(start).trim();
        }
        return block.substring(start, end).trim();
    }
}
