package com.lambert.banksimulator.service;
import bankSimulator.service.*;
import bankSimulator.model.*;
import com.lambert.banksimulator.model.Account;

import java.sql.SQLException;

public class TransactionHandler {
    public void transferFunds(int senderID, int receiverID, double amount) throws SQLException {
        DataHandler dh = new DataHandler();
        DataConstructor dc = new DataConstructor();
        //Reconstructing the account class objects based on the id provided
        Account sender = dc.pullAccountByID(senderID);
        Account receiver = dc.pullAccountByID(receiverID);
        if (sender.checkBalance() >= amount) {
            //Making the DB update to the senders account balance using class methods
            dh.updateAccountBalance(senderID, sender.withdraw(amount));
            //Making the DB update to the receivers account balance using class methods
            dh.updateAccountBalance(receiverID, receiver.deposit(amount));
            System.out.println("Transaction completed successfully");
        }
        else {
            System.out.println("Insufficient funds");
        }
    }
}
