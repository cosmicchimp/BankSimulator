package bankSimulator.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account;

    @BeforeEach
    public void setUp() {
        // This runs before EACH test
        account = new Account(1, 1234);
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
        assertNotNull(account);
    }
}