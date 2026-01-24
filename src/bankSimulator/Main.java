    package bankSimulator;
    import java.util.Scanner;
    import bankSimulator.model.Account; // import the Account class correctly\
    import bankSimulator.model.User;
    import java.util.ArrayList;
    import java.util.Random;
    import bankSimulator.model.DataHandler;
    public class Main {

        public static void main(String[] args) {
            // App entry point
            DataHandler handler = new DataHandler();
            handler.writeData();
            System.out.println("File location: " + new java.io.File("users.txt").getAbsolutePath());
            Scanner userInput = new Scanner(System.in);
            Random rand = new Random();
            boolean accountCreated = false;
            String username = "";
            String password = "";
            System.out.println("Choose your username to create an account:");
            username = userInput.nextLine();
            //Ask for user input for account creation
            while (!accountCreated) {
                System.out.println("Choose your password:");
                password = userInput.nextLine();

                System.out.println("Confirm your password:");
                String passwordConfirm = userInput.nextLine();
                if (password.equals(passwordConfirm)) {
                    accountCreated = true;
                    password = passwordConfirm;
                    break;
                }
                else {
                    System.out.println("Password did not match, please try again");
                }
            }
            System.out.println("Account successfully created! \n\n");
            User newUser = new User(username, password);
            System.out.println("Please choose account type to create by entering its corresponding value: \n(1) - Checking \n(2) - Savings");
            int accountType = userInput.nextInt();
            userInput.nextLine();
            System.out.println("Choose a preferred name for your account: ");
            int accountID = rand.nextInt(10000);
            Account newAccount = new Account(accountType, accountID);
            String accountInfo = newAccount.checkAccountInfo();
            newUser.addAccount(newAccount);
            ArrayList<String> userAccounts = newUser.listAccount();
            for (String account : userAccounts) {
                System.out.println(account);
            }
            System.out.println(accountInfo);
            userInput.close();
        }
    }