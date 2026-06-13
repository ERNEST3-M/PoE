import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import part3_poe.Login;

public class LoginTest {
   
    public LoginTest() {
    }
    @BeforeAll
    public static void setUpClass() {
    }
   
    @AfterAll
    public static void tearDownClass() {
    }
   
    @BeforeEach
    public void setUp() {
    }
   
    @AfterEach
    public void tearDown() {
    }

    /**
        Test of checkUserName method, of class Login.
     */
    @Test
    public void testCheckUserNameTrue() {
        System.out.println("checkUserName");
        String username = "kyl_1";
        Login instance = new Login();
        boolean expResult = true;
        boolean result = instance.checkUserName(username);
        assertEquals(expResult, result);
    }
    @Test
    public void testCheckUserNameFalse() {
        System.out.println("checkUserName");
        String username = "kyle";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkUserName(username);
        assertEquals(expResult, result);
    }

    /**
        Test of checkPasswordComplexity method, of class Login.
     */
    @Test
    public void testCheckPasswordComplexityTrue() {
        System.out.println("checkPasswordComplexity");
        String password = "Ch&&sec@ke99";
        Login instance = new Login();
        boolean expResult = true;
        boolean result = instance.checkPasswordComplexity(password);
        assertEquals(expResult, result);
    }
    @Test
    public void testCheckPasswordComplexityFalse() {
        System.out.println("checkPasswordComplexity");
        String password = "password";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity(password);
        assertEquals(expResult, result);

    }
     /**
        Test of checkCellPhoneNumber method, of class Registration.
     */
    @Test
    public void testCheckCellPhoneNumberTrue() {
        System.out.println("CheckCellPhoneNumber");
        String password = "+27838968976";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity(password);
        assertEquals(expResult, result);
        }
    @Test
    public void testCheckCellPhoneNumberFalse() {
        System.out.println("CheckCellPhoneNumber");
        String password = "08966553";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity(password);
        assertEquals(expResult, result);
        }

    /**
        Test of registerUser method, of class Login.
     */
    @Test
    public void testRegisterUserTrue() {
        System.out.println("registerUser");
        String username = "kyl_1";
        String password = "Ch&&sec@ke99";
        String phone = "+27838968976";
        Login instance = new Login();
        String expResult =  "The user has been registered successfully.";
        String result = instance.registerUser(username, password, phone);
        assertEquals(expResult, result);
    }
   
   @Test
    public void testRegisterUserFalse() {
        System.out.println("registerUser");
        String username = "kyle";
        String password = "password";
        String phone = "08966553";
        Login instance = new Login();
        String expResult =  "The username is incorrectly formatted.";
        String result = instance.registerUser(username, password, phone);
        assertEquals(expResult, result);
    }

    /**
        Test of loginUser method, of class Login.
    */
    @Test
    public void testLoginUserTrue() {
        System.out.println("loginUser");
        String username = "kyl_1";
        String password = "Ch&&sec@ke99";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.loginUser(username, password);
        assertEquals(expResult, result);
    }
   
    @Test
    public void testLoginUserFalse() {
        System.out.println("loginUser");
        String username = "kyle";
        String password = "password";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.loginUser(username, password);
        assertEquals(expResult, result);
    }


    /**
        Test of returnLoginStatus method, of class Login.
     */
   
    @Test
    public void testReturnLoginStatusTrue() {
        System.out.println("returnLoginStatus");
        boolean isLoggedIn = true;
        Login instance = new Login();
        String expResult = "A successful login! Welcome, kyl_1.";
        String result = instance.returnLoginStatus(isLoggedIn);
        assertEquals(expResult, result);
    }
   
    @Test
    public void testReturnLoginStatusFalse() {
        System.out.println("returnLoginStatus");
        boolean isLoggedIn = false;
        Login instance = new Login();
        String expResult = "A failed login. Invalid username or password.";
        String result = instance.returnLoginStatus(isLoggedIn);
        assertEquals(expResult, result);
    }
}