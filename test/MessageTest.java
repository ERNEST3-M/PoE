import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import part3_poe.Message;

public class MessageTest {

    public MessageTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("Starting Message Tests...");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("Finished Message Tests...");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Running test...");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Test completed...");
    }

    /*
        Test message length success
     */
    @Test
    public void testMessageLengthSuccess() {

        Message message = new Message(
                1,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        boolean result = message.getMessageContent().length() <= 250;

        assertTrue(result);
    }

    /*
        Test message length failure
     */
    @Test
    public void testMessageLengthFailure() {

        String longMessage =
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
              + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
              + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
              + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        Message message = new Message(
                1,
                "+27718693002",
                longMessage
        );

        boolean result = message.getMessageContent().length() > 250;

        assertTrue(result);
    }

    /*
        Test recipient number success
     */
    @Test
    public void testRecipientNumberSuccess() {

        Message message = new Message(
                1,
                "+27718693002",
                "Hi Mike"
        );

        String expected =
                "Cell phone number successfully captured.";

        boolean actual = message.checkRecipientCell();

        assertEquals(expected, actual);
    }

    /*
        Test recipient number failure
     */
    @Test
    public void testRecipientNumberFailure() {

        Message message = new Message(
                1,
                "08575975889",
                "Hi Keegan, did you receive the payment?"
        );

        String expected =
                "Cell phone number is incorrectly formatted.";

        boolean actual = message.checkRecipientCell();

        assertEquals(expected, actual);
    }

    /*
        Test message hash creation
     */
    @Test
    public void testCreateMessageHash() {

        Message message = new Message(
                0,
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        String result = message.createMessageHash();

        assertNotNull(result);
    }

    /*
        Test message ID creation
     */
    @Test
    public void testMessageIDCreated() {

        Message message = new Message(
                1,
                "+27718693002",
                "Hello"
        );

        String result = message.getMessageID();

        assertNotNull(result);

        assertEquals(10, result.length());
    }

    /*
        Test valid message ID
     */
    @Test
    public void testCheckMessageID() {

        Message message = new Message(
                1,
                "+27718693002",
                "Hello"
        );

        boolean result = message.checkMessageID();

        assertTrue(result);
    }

    /*
        Test send message
     */
    @Test
    public void testSendMessageOption() {

        Message message = new Message(
                1,
                "+27718693002",
                "Hello"
        );

        String expected = "Message successfully sent.";

        String actual = message.sentMessage(1);

        assertEquals(expected, actual);
    }

    /*
        Test discard message
     */
    @Test
    public void testDiscardMessageOption() {

        Message message = new Message(
                1,
                "+27718693002",
                "Hello"
        );

        String expected = "Message discarded.";

        String actual = message.sentMessage(0);

        assertEquals(expected, actual);
    }

    /*
        Test store message
     */
    @Test
    public void testStoreMessageOption() {

        Message message = new Message(
                1,
                "+27718693002",
                "Hello"
        );

        String expected = "Message successfully stored.";

        String actual = message.sentMessage(2);

        assertEquals(expected, actual);
    }

    /*
        Test total sent messages
     */
    @Test
    public void testReturnTotalMessages() {

        int result = Message.returnTotalMessages();

        assertTrue(result >= 0);
    }

    /*
        Test storing message
     */
    @Test
    public void testStoreMessage() {

        Message message = new Message(
                1,
                "+27718693002",
                "Stored Message"
        );

        message.storeMessage();

        assertTrue(true);
    }

    /*
        Test displaying messages
     */
    @Test
    public void testDisplayMessages() {

        Message.displayMessages();

        assertTrue(true);
    }

    /*
        Test getting recipient
     */
    @Test
    public void testGetRecipient() {

        Message message = new Message(
                1,
                "+27718693002",
                "Hello"
        );

        String expected = "+27718693002";

        String actual = message.getRecipient();

        assertEquals(expected, actual);
    }

    /*
        Test getting message content
     */
    @Test
    public void testGetMessageContent() {

        Message message = new Message(
                1,
                "+27718693002",
                "Hello World"
        );

        String expected = "Hello World";

        String actual = message.getMessageContent();

        assertEquals(expected, actual);
    }
}