package bankSimulator.service;
import bankSimulator.model.Account;
import bankSimulator.model.User;

import java.util.ArrayList;
import java.sql.*;
public class DataConstructor {
    public Connection getConn() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }
    public ArrayList<Account> pullAccounts(String username) throws SQLException {
        ArrayList<Account> accounts = new ArrayList<>();
        Connection conn = getConn();
        String sql = "SELECT * FROM accounts WHERE owner = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet accountData = stmt.executeQuery();
        //loops through the data set constructing and returning an account for each row
        while (accountData.next()) {
             Account currentAccount = new Account(accountData.getInt("accountID"), accountData.getString("accountType"),accountData.getDouble("balance"), accountData.getString("owner"));
            accounts.add(currentAccount);
        }
        //return the array list of accounts
        return accounts;
    }
    public User pullUser(String username) throws SQLException {
        Connection conn = getConn();
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet userData = stmt.executeQuery();
        userData.next();
        User user = new User(userData.getString("username"), userData.getString("password"), userData.getDouble("liquid_cash"));
        return user;
    }
}
