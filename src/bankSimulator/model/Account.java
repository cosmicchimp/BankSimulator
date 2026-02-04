package bankSimulator.model;

public class Account {

    //basic private values for accounts
    private String owner = "";
    private double balance = 1000;
    private String accountType;
    private int accountID;


    //public constructor class
    public Account(String accountType, double balance, String owner) {
        this.accountType = accountType;
        this.owner = owner;
        this.balance = balance;
    }

    //public method to withdraw amounts from an account and move it to the user liquid balance
    public double withdraw(int withdrawAmount) {
        balance -= withdrawAmount;
        return balance;
    }

    //basic method for account type print
    public String checkAccountType() {
        return this.accountType;
    }

    //public method to check specific account info, such as balance, and account type
    public String checkAccountInfo() {
        return "The account, ID: " + this.accountID + ", currently has a balance of " + balance + " it is a " + accountType + " account.";
    }
    public int checkID() {
        return this.accountID;
    }
    //public method to just check the balance of an account
    public double checkBalance() {
        return balance;
    }

    public String checkOwner() {
        return this.owner;
    }
    //public method to deposit money into an account from liquid cash
    public double deposit(double depositAmount) {
        this.balance += depositAmount;
        return this.balance;
    }

    //public method that allows you to transfer amounts in between accounts
    public double transfer(double transferAmount, int sendingAccount, int receivingAccount) {
        return 9.9;
    }
}
