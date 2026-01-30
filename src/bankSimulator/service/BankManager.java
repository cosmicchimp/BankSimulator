package bankSimulator.service;
import bankSimulator.model.Account;
import java.sql.*;
public class BankManager {
    public Account findAccount(int ID) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db")) {
            String sql = "SELECT * FROM accounts WHERE accountID = ?";
        }
        catch (SQLException e) {
            e.printStackTrack();

        }
        Account accountToReturn =
        return accountToReturn;
    }
}
