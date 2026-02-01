package bankSimulator.service;
import bankSimulator.model.Account;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.sql.*;
public class DataHandler {
    //Writing a database initializer function that will create tables if not in existence
    public void initDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db")) {
            Statement stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            //Initializing the create user table

            String createUserTable = """
                    CREATE TABLE IF NOT EXISTS users (
                    username TEXT PRIMARY KEY,
                    password TEXT NOT NULL,
                    liquid_cash REAL DEFAULT 0.0
                    )
                    """;
            stmt.execute(createUserTable);


            //Now we will initialize the account table
            String createAccountTable = """
                    CREATE TABLE IF NOT EXISTS accounts (
                    accountID INTEGER PRIMARY KEY AUTOINCREMENT,
                    accountType TEXT NOT NULL,
                    balance REAL DEFAULT 1000,
                    owner TEXT NOT NULL,
                    FOREIGN KEY (owner) REFERENCES users(username)
                    )
                    """;
            stmt.execute(createAccountTable);

            //Initializing the transaction history table
            String createTransactionTable = """
                    CREATE TABLE IF NOT EXISTS transaction_history (
                    transactionID INTEGER PRIMARY KEY AUTOINCREMENT,
                    amountTransferred REAL NOT NULL,
                    sendingAccount INTEGER NOT NULL,
                    receivingAccount INTEGER NOT NULL,
                    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (sendingAccount) REFERENCES accounts(accountID),
                    FOREIGN KEY (receivingAccount) REFERENCES accounts(accountID)
                    )
                    """;
            stmt.execute(createTransactionTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertAccount(Account account) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db")) {
            String sql = "INSERT INTO accounts (accountID, accountType, balance, owner) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,String.valueOf(account.checkID()));
            stmt.setString(2,String.valueOf(account.checkAccountType()));
            stmt.setString(3,String.valueOf(account.checkBalance()));
            stmt.setString(4,account.checkOwner());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser (String username, String password) {

    }
    public ArrayList<Account> pullAccounts(String username) {
        ArrayList<String> accounts
        return accounts;
    }

}