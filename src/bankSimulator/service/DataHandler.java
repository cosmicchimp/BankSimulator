package bankSimulator.service;
import bankSimulator.model.Account;
import bankSimulator.model.User;
import java.sql.*;
import java.util.*;

public class DataHandler {

    //database connection helper function
    public Connection getConn() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    //Database initializer function that will create tables if not in existence
    public void initDatabase() throws SQLException {
        try (
                Connection conn = getConn(); Statement stmt = conn.createStatement();
        ) {
            //Initializing the create user table
            String createUserTable = """
            CREATE TABLE IF NOT EXISTS users(
                    username TEXT PRIMARY KEY,
                    password TEXT NOT NULL,
                    liquid_cash REAL DEFAULT 0.0
      )
            """;
            stmt.execute(createUserTable);

            //Now we will initialize the account table
            String createAccountTable = """
            CREATE TABLE IF NOT EXISTS accounts(
                    accountID INTEGER PRIMARY KEY AUTOINCREMENT,
                    accountType TEXT NOT NULL,
                    balance REAL DEFAULT 1000,
                    accountName TEXT,
                    owner TEXT NOT NULL,
                    FOREIGN KEY(owner) REFERENCES users(username)
      )
            """;
            stmt.execute(createAccountTable);

            //Initializing the transaction history table
            String createTransactionTable = """
            CREATE TABLE IF NOT EXISTS transaction_history(
                    transactionID INTEGER PRIMARY KEY AUTOINCREMENT,
                    amountTransferred REAL NOT NULL,
                    sendingAccount INTEGER NOT NULL,
                    receivingAccount INTEGER NOT NULL,
                    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY(sendingAccount) REFERENCES accounts(accountID),
                    FOREIGN KEY(receivingAccount) REFERENCES accounts(accountID)
      )
            """;
            stmt.execute(createTransactionTable);
            String createConversationTable = """
            CREATE TABLE IF NOT EXISTS conversations(
                    conversationID INTEGER PRIMARY KEY AUTOINCREMENT,
                    initiator TEXT NOT NULL,
                    receiver TEXT NOT NULL,
                    FOREIGN KEY(initiator) REFERENCES users(username),
                    FOREIGN KEY(receiver) REFERENCES users(username)
              )
            """;
            stmt.execute(createConversationTable);

            String createMessagesTable = """
            CREATE TABLE IF NOT EXISTS messages(
                    messageID INTEGER PRIMARY KEY AUTOINCREMENT,
                    conversationID INTEGER,
                    message TEXT NOT NULL,
                    sender TEXT NOT NULL,
                    receiver TEXT NOT NULL,
                    sent_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY(sender) REFERENCES users(username),
                    FOREIGN KEY(conversationID) REFERENCES conversations(conversationID)
            )
            """;
            stmt.execute(createMessagesTable);
        }
    }

    //Function for inserting accounts into the database
    public void insertAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (accountType, balance, owner, accountName) VALUES (?, ?, ?, ?)";
        try (
                Connection conn = getConn(); PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, account.checkAccountType());
            stmt.setDouble(2, account.checkBalance());
            stmt.setString(3, account.checkOwner());
            stmt.setString(4, account.checkName());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        }
    }
    //Function for checking if a username has already been taken
    public boolean checkUsername(String username) throws SQLException {
        String sql = "SELECT 1 FROM users where LOWER(username) = LOWER(?)";
        try (
                Connection conn = getConn(); PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next();
            }
        }
    }
    //Function for inserting users in the database
    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, liquid_cash) VALUES (?, ?, ?)";
        try (
                Connection conn = getConn(); PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setDouble(3, user.getLiquidCash());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        }
    }

    //Method for locating, constructing, and adding all accounts to an arraylist which is used
    //by the user model
    public ArrayList < Account > populateAccounts(String owner) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE owner = ?";
        ArrayList < Account > accounts = new ArrayList < > ();
        DataConstructor dc = new DataConstructor();
        try (
                Connection conn = getConn(); PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, owner);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("accountID"), rs.getString("accountType"), rs.getDouble("balance"), rs.getString("owner"));
                accounts.add(account);
            }
            return accounts;
        }
    }

    //Method for updating the values of account balances in the database
    public void updateAccountBalance(int accountID, double balance) throws SQLException {
        try (
                Connection conn = getConn(); PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE accountID = ?");
        ) {
            stmt.setDouble(1, balance);
            stmt.setInt(2, accountID);
            stmt.executeUpdate();
        }
    }

    //Function that deletes and closes an account if the balance sits at 0, otherwise return false
    public boolean closeAccount(int accountID) throws SQLException {
        String sql = "DELETE FROM accounts WHERE accountID = ?";
        DataConstructor dc = new DataConstructor();
        try (
                Connection conn = getConn(); PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            Account account = dc.pullAccountByID(accountID);
            stmt.setInt(1, accountID);
            if (account.checkBalance() == 0) {
                stmt.executeUpdate();
                return true;
            } else {
                return false;
            }
        }
    }
    public boolean checkLogin(String username, String password) throws SQLException{
        String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";
        try (
                Connection conn = getConn();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return (rs.next());
            }
        }
    }
    public ArrayList<User> pullUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();
        try (
                 Connection conn = getConn();
                 PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"), rs.getDouble("liquid_cash"));
                users.add(user);
            }
            return users;
        }
    }

}