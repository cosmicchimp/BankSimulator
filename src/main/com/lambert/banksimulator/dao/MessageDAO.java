package com.lambert.banksimulator.dao;
import com.lambert.banksimulator.model.User;
import com.lambert.banksimulator.util.DatabaseConnection;
import java.sql.*;

public class MessageDAO {
    //Function to initiate conversation and log the interaction within the conversation table
    public void initConvo(String sender, String receiver) throws SQLException {
        String sql = "INSERT INTO conversations (initiator, receiver) VALUES (?, ?)";
        try (
                Connection conn = DatabaseConnection.getConn();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, sender);
            stmt.setString(2, receiver);
            stmt.executeUpdate();
        }
    }
    //Function for sending messages to users inbox, persisting via database
    public void sendMessage(User sender, String receiver, String message) throws SQLException {
        String sql = "INSERT into messages (conversationID, message, sender, receiver) VALUES (?,?,?,?)";
        try
                (
                        Connection conn = DatabaseConnection.getConn();
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
    //Function for locating conversations
    public int findConversation(String sender, String receiver) throws SQLException {
        String sql = "SELECT conversationID FROM conversations WHERE initiator = ? AND receiver = ?";
        try (
                Connection conn = DatabaseConnection.getConn();
                PreparedStatement stmt = conn.prepareStatement(sql)
        )
        {
            stmt.setString(1, sender);
            stmt.setString(2, receiver);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("conversationID");
        }
        //Function for locating all messages related to a conversation ID
    }

}
