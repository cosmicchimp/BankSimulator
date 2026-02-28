package com.lambert.banksimulator.service;
import bankSimulator.model.*;
import bankSimulator.service.*;
import com.lambert.banksimulator.model.User;

import java.sql.*;

public class MessageSender {
    //database connection helper function
    public Connection getConn() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    public void initConvo(String sender, String receiver) throws SQLException {
        String sql = "INSERT INTO conversations (initiator, receiver) VALUES (?, ?)";
        try (
                Connection conn = getConn();
                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
                stmt.setString(1, sender);
                stmt.setString(2, receiver);
                stmt.executeUpdate();
        }
    }
    public int findConversation(String sender, String receiver) throws SQLException {
        String sql = "SELECT conversationID FROM conversations WHERE initiator = ? AND receiver = ?";
        try (
                Connection conn = getConn();
                PreparedStatement stmt = conn.prepareStatement(sql)
                )
        {
            stmt.setString(1, sender);
            stmt.setString(2, receiver);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("conversationID");
        }
    }
    public void sendMessage(User sender, String receiver, String message) throws SQLException {
        String sql = "INSERT into messages (conversationID, message, sender, receiver) VALUES (?,?,?,?)";
        try
                (
                Connection conn = getConn();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            int convoID = findConversation(sender.getUsername(), receiver);
            stmt.setInt(1, convoID);
            stmt.setString(2, message);
            stmt.setString(3, sender.getUsername());
            stmt.setString(4, receiver);
            stmt.executeUpdate();
        }
    }
}
