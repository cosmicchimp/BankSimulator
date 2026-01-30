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
                    accountID INTEGER PRIMARY KEY,
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
                    transactionID INTEGER PRIMARY KEY,
                    amountTransferred REAL NOT NULL,
                    sendingAccount INTEGER NOT NULL,
                    receivingAccount INTEGER NOT NULL,
                    FOREIGN KEY (sendingAccount) REFERENCES accounts(accountID),
                    FOREIGN KEY (receivingAccount) REFERENCES accounts(accountID),
                    date TEXT NOT NULL
                    )
                    """;
            stmt.execute(createTransactionTable);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Public method used to write username and password to the path of choice
    public void writeUser(String user, String password) {
        String filePath = "users.txt";
        System.out.println(">>>>> Writing to: " + filePath + " <<<<<");
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(user + "," + password);
            System.out.println(">>>>> FILE WRITTEN <<<<<");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file: >>>>>>\n" + e);
        }
    }

    //Public method used to find a user and its corresponding data in a file
    // based on username

    public String[] findUser(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(",");
                String currentUser = splitLine[0];
                if (currentUser.equals(username)) {
                    String[] userInfo = {splitLine[0], splitLine[1]};
                    return userInfo;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file: >>>>>>\n" + e);
        }
        return null;
    }

    //Method to write an account to file once it has been created
    public void writeAccount(Account accountInfo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            writer.println("Hello");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing account to file: >>>>>>>> \n" + e);
        }
    }

}