package bankSimulator;
import bankSimulator.service.DataHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import bankSimulator.model.Account;
import bankSimulator.model.User;

/**
 * Main application class for the Banking Simulator.
 * Handles the JavaFX UI and navigation between different screens.
 */
public class Main extends Application {

    // ========================================
    // INSTANCE VARIABLES
    // ========================================

    /** Currently logged-in user */
    private User currentUser;

    /** Random number generator for account IDs */
    private Random rand = new Random();

    /** List of all registered users (in-memory for now) */
    private ArrayList<User> allUsers = new ArrayList<>();

    // ========================================
    // APPLICATION ENTRY POINT
    // ========================================

    /**
     * JavaFX start method - called when application launches.
     * Always shows the "Create First User" screen by default.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Banking Simulator");
        System.out.println(">>>>> APPLICATION STARTED - SHOWING CREATE USER SCREEN <<<<<");
        showCreateFirstUser(stage);
    }

    // ========================================
    // SCREEN: CREATE FIRST USER
    // ========================================

    /**
     * Shows the "Create First User" screen.
     * This is the default starting screen.
     *
     * @param stage The main application stage
     */
    private void showCreateFirstUser(Stage stage) {
        System.out.println("Rendering: Create First User screen");

        // Create UI components
        Label lblTitle = new Label("Welcome! Create your first user account");
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label userExists = new Label("");
        Label lblUser = new Label("Enter username:");
        TextField tfUser = new TextField();
        tfUser.setPromptText("Username");

        Label lblPass = new Label("Enter password:");
        PasswordField pfPass = new PasswordField();
        pfPass.setPromptText("Password");

        Label lblConfirm = new Label("Confirm password:");
        PasswordField pfConfirm = new PasswordField();
        pfConfirm.setPromptText("Confirm password");

        Label lblMsg = new Label();
        lblMsg.setStyle("-fx-text-fill: red;");

        Button btnCreate = new Button("Create First User");
        Button btnCheck = new Button("Check for username");

        // Layout all components in a vertical box
        VBox root = new VBox(10, lblTitle, userExists,lblUser, tfUser, lblPass, pfPass,
                lblConfirm, pfConfirm, btnCreate,btnCheck, lblMsg);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        // Handle "Create First User" button click
        btnCheck.setOnAction(e -> {
            userExists.setText("");
            String username = tfUser.getText().trim();
            DataHandler dataHandler = new DataHandler("C:\\Users\\maxla\\Desktop\\BankSimulator\\users.txt");
            String[] userInfo = dataHandler.findUser(username);
            if ((userInfo != null)) {
                userExists.setStyle("-fx-text-fill: red;");
                userExists.setText("Username already taken");
                System.out.println("USER INFO FOUND:");
                System.out.println(userInfo[0] + userInfo[1]);
            }
            else {
                userExists.setStyle("-fx-text-fill: green;");
                System.out.println("User not found");
                userExists.setText("Username hasn't been taken");
            }

        });
        btnCreate.setOnAction(e -> {
            String username = tfUser.getText().trim();
            String password = pfPass.getText();
            String confirm = pfConfirm.getText();

            // Validate inputs
            if (username.isEmpty()) {
                lblMsg.setText("Username cannot be empty!");
                return;
            }

            if (password.isEmpty()) {
                lblMsg.setText("Password cannot be empty!");
                return;
            }

            if (!password.equals(confirm)) {
                lblMsg.setText("Passwords do not match!");
                return;
            }

            // Create the first user
            currentUser = new User(username, password);
            allUsers.add(currentUser);
            System.out.println("First user created: " + username);

            // Navigate to account type selection
            showAccountType(stage);
        });

        // Set the scene and display it
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    // ========================================
    // SCREEN: ACCOUNT TYPE SELECTION
    // ========================================

    /**
     * Shows the account type selection screen.
     * Allows user to choose between Checking or Savings account.
     *
     * @param stage The main application stage
     */
    private void showAccountType(Stage stage) {
        System.out.println("Rendering: Account Type Selection screen");

        // Create UI components
        Label lblTitle = new Label("Select Account Type");
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label lblType = new Label("What type of account would you like to create?");

        Button btnChecking = new Button("Checking Account");
        Button btnSavings = new Button("Savings Account");

        Label lblMsg = new Label();
        lblMsg.setStyle("-fx-text-fill: green;");

        Button btnContinue = new Button("Continue to Dashboard");
        btnContinue.setVisible(false); // Hidden until account is created

        // Layout components
        VBox root = new VBox(10, lblTitle, lblType, btnChecking, btnSavings, lblMsg, btnContinue);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        // Handle "Checking Account" button click
        btnChecking.setOnAction(e -> {
            createAccount(1, lblMsg);
            btnContinue.setVisible(true);
        });

        // Handle "Savings Account" button click
        btnSavings.setOnAction(e -> {
            createAccount(2, lblMsg);
            btnContinue.setVisible(true);
        });

        // Handle "Continue to Dashboard" button click
        btnContinue.setOnAction(e -> {
            showDashboard(stage);
        });

        // Set the scene and display it
        stage.setScene(new Scene(root, 700, 500));
    }

    // ========================================
    // HELPER: CREATE ACCOUNT
    // ========================================

    /**
     * Creates a new bank account for the current user.
     *
     * @param type Account type (1 = Checking, 2 = Savings)
     * @param lblMsg Label to display success message
     */
    private void createAccount(int type, Label lblMsg) {
        // Generate random account ID with a range of the highest int computationally possible
        //to reduce chance of collisions, this could be done easier with SHA, but I'm keeping it simple
        //for right now
        int id = rand.nextInt(Integer.MAX_VALUE);

        // Create the account
        Account acc = new Account(type, id);
        currentUser.addAccount(acc);

        // Display success message
        String accountType = (type == 1) ? "Checking" : "Savings";
        System.out.println(accountType + " account created with ID: " + id);
        lblMsg.setText("Account created! ID: " + id + "\n" + acc.checkAccountInfo());
    }

    // ========================================
    // SCREEN: DASHBOARD
    // ========================================

    /**
     * Shows the main dashboard.
     * Displays user's accounts and provides navigation options.
     *
     * @param stage The main application stage
     */
    private void showDashboard(Stage stage) {
        System.out.println("Rendering: Dashboard screen");

        // Create UI components
        Label lblWelcome = new Label("Welcome to Your Dashboard!");
        lblWelcome.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Display user's accounts
        Label lblAccounts = new Label("Your Accounts:");
        TextArea taAccounts = new TextArea();
        taAccounts.setEditable(false);
        taAccounts.setPrefHeight(200);

        // Get and display all accounts
        ArrayList<String> accountList = currentUser.listAccount();
        if (accountList.isEmpty()) {
            taAccounts.setText("No accounts yet. Create one from the menu!");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String accountInfo : accountList) {
                sb.append(accountInfo).append("\n\n");
            }
            taAccounts.setText(sb.toString());
        }

        // Display liquid cash
        Label lblCash = new Label("Liquid Cash: $" +
                String.format("%.2f", currentUser.checkLiquid()));
        lblCash.setStyle("-fx-font-size: 14px;");

        Button btnAddAccount = new Button("Add New Account");

        // Layout components
        VBox root = new VBox(15, lblWelcome, lblAccounts, taAccounts, lblCash, btnAddAccount);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        // Handle "Add New Account" button click
        btnAddAccount.setOnAction(e -> {
            showAccountType(stage);
        });

        // Set the scene and display it
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    // ========================================
    // MAIN METHOD
    // ========================================

    /**
     * Application entry point.
     * Launches the JavaFX application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}