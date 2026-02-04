package bankSimulator.model;
import java.util.ArrayList;
import bankSimulator.model.User;
import bankSimulator.service.DataHandler;
import bankSimulator.service.DataConstructor;
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
        user = new User("john", "abc", 2500);
        account = new Account("Checking",1000, "john");
    }

    @Test
    @DisplayName("Check account info method returns correct string")
    public void testCheckAccountInfo() {
        String info = account.checkOwner();
        assertEquals("john", info); // Should contain account ID
    }


}