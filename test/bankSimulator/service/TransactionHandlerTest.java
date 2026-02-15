package bankSimulator.service;

import bankSimulator.model.Account;
import bankSimulator.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class TransactionHandlerTest {
    @Test
    @DisplayName("Testing movement of funds between accounts")
    public void transferTest() throws SQLException {
        TransactionHandler th = new TransactionHandler();
        DataConstructor dc = new DataConstructor();
        Account emptyAccount = dc.pullAccountByID(2);
        User user = new User("bob", "qwerty", 1000);
        user.populateAccounts();
        user.printAccountInfo();
        th.transferFunds(2, 7, 1200);
        user.populateAccounts();
        user.printAccountInfo();
        assertEquals(0, emptyAccount.checkBalance());
    }
}


