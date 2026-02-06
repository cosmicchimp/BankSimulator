package bankSimulator.service;
import bankSimulator.model.*;
import bankSimulator.service.*;
import bankSimulator.exceptions.*;
public class TransactionHandler {
    DataHandler dataHandler = new DataHandler();
    DataConstructor dataConstructor = new DataConstructor();
        public void transferFunds(Account sendingAccount, Account receivingAccount, double amount) throws BalanceException {
            if ((sendingAccount.checkBalance() < amount)) {
                    throw new BalanceException("Insufficient balance");
                }
            sendingAccount.withdraw(amount);
            receivingAccount.deposit(amount);
            System.out.println("Transaction performed successfully");
            }
        }
