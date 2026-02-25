
package bankSimulator.model;
import bankSimulator.service.DataConstructor;

import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    //Basic user values
    private String username;
    private String password;
    private double liquidCash;
    //Array list that holds all of the users accounts, adhering to the account type
    ArrayList<Account> accounts = new ArrayList<>();
    public User(String Username, String Password, double liquidCash) {
        this.username = Username;
        this.password = Password;
        this.liquidCash = liquidCash;
    }
    //Method to add an account to the array list of accounts
    public String addAccount(Account accountToAdd) {
        accounts.add(accountToAdd);
        return "Account successfully added!";

    }

    //Method to return a list of all user accounts and their balances
    public ArrayList<String> listAccount() {
        ArrayList<String> allAccounts = new ArrayList<>();
        for (Account account : accounts) {
            String accountInfo = account.checkAccountInfo();
            allAccounts.add(accountInfo);
        }
        return allAccounts;
    }
    //New and improved method for listing all user accounts and their information
    public void printAccountInfo() {
        for (Account account : accounts) {
            System.out.println("Account ID: " + account.checkID());
            System.out.println("Account Type: " + account.checkAccountType());
            System.out.println("Account Balance: " + account.checkBalance());
            System.out.println("-------------------------------");
        }
    }
    //Method to populate accounts stored in the database
    public void populateAccounts() {
        try {
            DataConstructor dc = new DataConstructor();
            ArrayList<Account> accountList = dc.pullAccounts(this.username);
            for (Account account : accountList) {
                addAccount(account);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Method to check liquid cash
    public double checkLiquid() {
        return this.liquidCash;
    }

    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public double getLiquidCash() {
        return this.liquidCash;
    }

}

