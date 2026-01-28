
package bankSimulator.model;
import java.util.ArrayList;
import bankSimulator.model.Account; // import the Account class correctly
public class User {
    //Basic user values
    private String username;
    private String password;
    private double liquidCash;

    //Array list that holds all of the users accounts, adhering to the account type
    ArrayList<Account> accounts = new ArrayList<>();
    public User(String Username, String Password) {
        this.username = Username;
        this.password = Password;
    };
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
    //Method for user to signout
    public void signOut() {
    }
}

