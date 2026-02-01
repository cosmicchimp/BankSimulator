
package bankSimulator.model;
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

    //Method to check liquid cash
    public double checkLiquid() {
        return this.liquidCash;
    }

    //Method to send money to a user
//    public void sendMoney(int userID, double amount, int sendingAccountID, int receivingAccountID) throws AccountException {
//        Account sendingAccount = bankManager.findAccount(sendingAccountID);
//        Account receivingAccount = bankManager.findAccount(receivingAccountID);
//        if ((sendingAccount == null) || (receivingAccount == null)) {
//            System.out.println("One or more account does not exist");
//            throw new AccountException("One or more accounts does not exist");
//        }
//        if ((amount > sendingAccount.getBalance())) {
//            System.out.println("Insufficient balance");
//            throw new AccountException("Insufficient balance");
//        }
//
//
//    }

    //Method for user to signout
    public void signOut() {

    }
}

