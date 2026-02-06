
package bankSimulator.model;
import java.util.ArrayList;

public class User {
    //Basic user values
    private String username;
    private String password;
    private double liquidCash;

    //Array list that holds all of the users accounts, adhering to the account type
    ArrayList<Account> accounts = new ArrayList<>();

    //Public User constructor
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

    //Get username
    public String getUsername() {
        return this.username;
    }
    //Get password
    public String getPassword() {
        return this.password;
    }
    //Get liquid cash
    public double getLiquidCash() {
        return this.liquidCash;
    }
    //Add funds to account
    public void deposit(double amount) {
        this.liquidCash += amount;
    }
    //Withdraw funds from user
    public void withdraw(double amount) {
        this.liquidCash -= amount;
    }
}

