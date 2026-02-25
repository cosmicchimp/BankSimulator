package bankSimulator;
import java.sql.SQLException;
import bankSimulator.model.Account;
import bankSimulator.model.User;
import bankSimulator.service.DataHandler;
import bankSimulator.service.MessageSender;
import org.w3c.dom.ls.LSOutput;

import javax.xml.crypto.Data;
import java.util.*;
/**
 * Main application class for the Banking Simulator.
 * Handles the JavaFX UI and navigation between different screens.
 */

public class Main {
    public User currentUser = null;
    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.run();
    }
    private void run() throws Exception {
        DataHandler dh = new DataHandler();
        dh.initDatabase();
        System.out.println("Database initialized...");
        try (
                Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("Banking Application CLI has begun: \n");
            System.out.println("""
            Welcome to my Java Bank Application
            -----------------------------------
            Type 'help' for a list of commands 
            -----------------------------------
            You can create accounts, transfer
            funds, and communicate with other
            users!
            -----------------------------------
            """);
            while (true) {
                String command = scanner.nextLine();
                if (command.equals("end")) {
                    break;
                }
                readCmd(command, scanner);
            }

        }
    }
    private void readCmd(String cmd, Scanner scanner) throws Exception {
        if (cmd.equals("help")) {
            help();
        }
        if (cmd.equals("create-u")) {
            createUser(scanner);
        }
        if (cmd.equals("login")) {
            login(scanner);
        }
        if (cmd.equals("create-a")) {
            createAccount(scanner);
        }
        if (cmd.equals("list-u")) {
            listUsers();
        }
        if (cmd.equals("msg")) {
            sendMessage(scanner);
        }
    }
    private void help() {
        System.out.println("""
            1. Type 'end' to terminate session 
            2. Type 'create-u' to create a user and its log in info 
            3. Type 'login' to prompt a username and password login
            4. Once logged in you can use 'create-a' to create an account 
            5. Type 'list-u' to see a list of users 
            6. Type 'msg' to send a message to a users inbox
            7. Type 'inbox' to check your inbox for messages
            8. Type 'balances' to view your accounts and their balances
            9. Type 'transfer' to initiate a transfer of funds, either between your accounts or another users account
            10. Type 'close' to close an account, however it must be sitting at a 0 balance
            """);
    }
    private void createUser(Scanner scanner) throws SQLException {
        DataHandler dh = new DataHandler();
        System.out.println("Choose a username");
        String username = "";
        while (true) {
            username = scanner.nextLine();
            if ((dh.checkUsername(username))) {
                System.out.println("Username chosen: " + username);
                break;
            } else {
                System.out.println("Username is already taken please choose a different name");
            }
        }
        System.out.println("Please choose your password:");
        while (true) {
            String password = scanner.nextLine();
            if (password.length() >= 8) {
                User newUser = new User(username, password, 0);
                dh.insertUser(newUser);
                break;
            }
            System.out.println("""
                    Password does not meet length requirements Please choose a new password:
            """);
        }

    }
    public void login(Scanner scanner) throws SQLException {
        System.out.println("Please enter your username: ");
        DataHandler dh = new DataHandler();
        while (true) {
            String username = scanner.nextLine();
            if (dh.checkUsername(username)) {
                System.out.printf("User does not exist");
                continue;
            }
            System.out.printf("Please enter your password: ");
            String password = scanner.nextLine();
            if (dh.checkLogin(username, password)) {
                System.out.println("You are now logged in as " + username);
                currentUser = new User(username, password, 0);
                break;
            }
            else {
                System.out.println("Incorrect password");
            }
        }
    }
    public void createAccount(Scanner scanner) throws SQLException {
        if (currentUser == null){
            System.out.println("You must log in before creating an account");
            return;
        }
        DataHandler dh = new DataHandler();
        String accountType;
        String accountName;
        double balance;
        System.out.println("Please choose a name for your account");
        accountName = scanner.nextLine();
        System.out.println("""
                Please choose your account type [1,2,3]: 
                (1) - Checking
                (2) - Savings
                (3) - HYSA (High Yield Savings Accounts)""");
        while (true) {
            accountType = scanner.nextLine();
            if (List.of("1", "2", "3").contains(accountType)) {
                break;
            }
            System.out.println("Invalid account type chosen, please choose again");
        }
        System.out.println("Choose a starting balance for your account [$1 - $9999]");
        while (true) {
            balance = scanner.nextDouble();
            if (balance >= 1 && balance <= 9999) {
                System.out.println("Account created successfuly");
                Account newAccount = new Account(typeReader(accountType), balance, currentUser.getUsername(), accountName);
                dh.insertAccount(newAccount);
                currentUser.addAccount(newAccount);
                break;
            }
            System.out.println("Invalid value chosen, please choose again");
        }

    }
    public void listUsers() throws SQLException {
        System.out.println("List of users:");
        DataHandler dh = new DataHandler();
        ArrayList<User> users = dh.pullUsers();
        for (User user : users) {
            System.out.println(user.getUsername());
        }
    }
    public void sendMessage(Scanner scanner) throws SQLException {
        System.out.println("Please enter the username of the person you would like to message: ");
        String receiver = scanner.nextLine();
        System.out.println("Enter your message: ");
        String message = scanner.nextLine();
        MessageSender ms = new MessageSender();
        User sender = currentUser;
        ms.initConvo(sender.getUsername(), receiver);
        ms.sendMessage(sender, receiver, message);
        System.out.println("Message sent!");
    }
    public String typeReader(String value) {
        String type = "";
        if (value.equals("1")) {
            type = "Checking";
        }
        if (value.equals("2")) {
            type = "Savings";
        }
        if (value.equals("3")) {
            type = "HYSA";
        }
        return type;
    }
}