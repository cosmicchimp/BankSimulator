package bankSimulator.model;
import java.util.ArrayList;
import bankSimulator.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private User user;
    private Account account;

    @BeforeEach
    public void setUp() {
        // This runs before EACH test
        user = new User("john", "abc");
        account = new Account(1,1234, "john");
    };

    @Test
    @DisplayName("Check account info method returns correct string")
    public void testCheckAccountInfo() {
        String info = account.checkAccountInfo();
        assertNotNull(info);
        assertTrue(info.contains("1234")); // Should contain account ID
    }

    @Test
    @DisplayName("Account should have correct ID")
    public void testAccountId() {
        // You'll need to add getId() getter if it doesn't exist
        // For now, just test what you CAN test
        assertEquals(1234, account.checkID());
    }

    @Test
    @DisplayName("Checking the adding of accounts to a user")
    public void testAccountAdd() {
        user.addAccount(account);
        ArrayList<String> accounts = user.listAccount();
        assertEquals("The account, ID: 1234, currently has a balance of 1000.0 it is a Checking account.", accounts.get(0));


    }


}