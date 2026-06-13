import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import part3_poe.Message;

public class StoredDataManagerTest {
    

    @Test
    public void testMessageIDLength() {
        Message msg = new Message(
                1,
                "+27834557896",
                "Did you get the cake?"
        );

        assertEquals(10, msg.getMessageID().length());
    }

    @Test
    public void testRecipientValidation() {
        Message msg = new Message(
                1,
                "+27834557896",
                "Did you get the cake?"
        );

        assertTrue(msg.checkRecipientCell());
    }

    @Test
    public void testRecipientValidationFail() {
        Message msg = new Message(
                1,
                "12345",
                "Did you get the cake?"
        );

        assertFalse(msg.checkRecipientCell());
    }

    @Test
    public void testMessageHashCreated() {
        Message msg = new Message(
                1,
                "+27834557896",
                "Did you get the cake?"
        );

        String hash = msg.createMessageHash();

        assertNotNull(hash);
        assertTrue(hash.contains(":1:"));
    }

    @Test
    public void testSentMessageAddedToArray() {

        Message msg = new Message(
                1,
                "+27834557896",
                "Did you get the cake?"
        );

        msg.sentMessage(1);

        String[] sentMessages = Message.getSentMessages();

        assertNotNull(sentMessages[0]);
        assertTrue(sentMessages[0].contains("Did you get the cake?"));
    }

    @Test
    public void testStoredMessageAddedToArray() {

        Message msg = new Message(
                2,
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time."
        );

        msg.sentMessage(2);

        String[] sentMessages = Message.getSentMessages();

        boolean found = false;

        for (String message : sentMessages) {
            if (message != null &&
                message.contains("Where are you? You are late!")) {

                found = true;
                break;
            }
        }

        assertTrue(found);
    }

    @Test
    public void testDisregardedMessage() {

        Message msg = new Message(
                3,
                "+27834484567",
                "Yohoooo, I am at your gate."
        );

        String result = msg.sentMessage(0);

        assertEquals(
                "Message discarded and added to Disregarded messages.",
                result
        );
    }

    @Test
    public void testTotalMessagesSent() {

        int before = Message.returnTotalMessages();

        Message msg = new Message(
                4,
                "+27838884567",
                "It is dinner time !"
        );

        msg.sentMessage(1);

        int after = Message.returnTotalMessages();

        assertEquals(before + 1, after);
    }

}