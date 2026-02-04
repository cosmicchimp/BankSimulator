package bankSimulator.service;
import bankSimulator.model.Account;
import bankSimulator.model.User;
import java.sql.*;
public class DataHandler {
    //database connection helper function
    public Connection getConn() throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
        try (Statement stmt = conn.createStatement()) {
        stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }
    //Writing a database initializer function that will create tables if not in existence
    public void initDatabase() throws SQLException {
        try (
                Connection conn = getConn();
                Statement stmt = conn.createStatement();
                ) {
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
    }
    public void insertAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (accountType, balance, owner) VALUES (?, ?, ?)";
        try (
                Connection conn = getConn();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            stmt.setString(1,account.checkAccountType());
            stmt.setDouble(2, account.checkBalance());
            stmt.setString(3,account.checkOwner());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        }
    }

    public void insertUser (User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, liquid_cash) VALUES (?, ?, ?)";
        try (
                Connection conn = getConn();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {
            stmt.setString(1,user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setDouble(3, user.getLiquidCash());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        }
    }

}