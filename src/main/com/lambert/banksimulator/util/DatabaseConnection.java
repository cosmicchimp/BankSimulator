package com.lambert.banksimulator.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    //database connection helper function
    public static Connection getConn() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }
}
