package bankSimulator.service;
import java.sql.SQLException;
import java.util.ArrayList;
import bankSimulator.model.User;
import bankSimulator.service.DataHandler;
import bankSimulator.service.DataConstructor;
import bankSimulator.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class DataHandlerTest {
    @BeforeEach
    public void setUp() throws SQLException {
        DataHandler dataHandler = new DataHandler();
        // Dummy user data
//        String[] usernames = {"alice", "bob", "charlie", "diana", "edward"};
//        String[] passwords = {"pass123", "qwerty", "letmein", "123456", "password"};
//        Double[] liquidCash = {1000.0, 2500.5, 500.0, 750.25, 3000.0};
//        for (int i = 0; i < usernames.length; i++) {
//            User user = new User(usernames[i], passwords[i], liquidCash[i]);
//            dataHandler.insertUser(user);
//        }
        // Dummy account data
        String[] accountTypes = {"Checking", "Savings", "Checking", "Savings", "Checking"};
        Double[] balances = {500.0, 1200.0, 300.0, 800.0, 1500.0};
        String[] owners = {"alice", "bob", "charlie", "diana", "edward"};
        for (int i = 0; i < accountTypes.length; i++) {
            Account acc = new Account(accountTypes[i], balances[i], owners[i]);
            dataHandler.insertAccount(acc);
        }
    }
    @Test
    @DisplayName("Testing user find function")
    public void findUserTest() throws SQLException {
        DataConstructor dataConstructor = new DataConstructor();
        User user = dataConstructor.pullUser("alice");
        assertEquals("pass123", user.getPassword());
    }
    @Test
    @DisplayName("Testing account pull function")
    public void findUserAccounts() throws SQLException {
        DataConstructor dataConstructor = new DataConstructor();
        ArrayList<Account> accounts = dataConstructor.pullAccounts("alice");
        Account account = accounts.get(0);
        assertEquals(500, account.checkBalance());
    }
}
