package bankSimulator;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Pos;

import java.util.Random;
import java.util.ArrayList;

import bankSimulator.model.Account;
import bankSimulator.model.User;

public class Main extends Application {

    private User currentUser;
    private Random rand = new Random();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Banking Simulator");

        // Username & password input
        Label lblUser = new Label("Enter username:");
        TextField tfUser = new TextField();
        Label lblPass = new Label("Enter password:");
        PasswordField pfPass = new PasswordField();
        Label lblConfirm = new Label("Confirm password:");
        PasswordField pfConfirm = new PasswordField();
        Label lblMsg = new Label();
        Button btnCreate = new Button("Create Account");
        VBox root = new VBox(10, lblUser, tfUser, lblPass, pfPass, lblConfirm, pfConfirm, btnCreate, lblMsg);
        root.setAlignment(Pos.CENTER);

        btnCreate.setOnAction(e -> {
            System.out.println("Button press");
            String username = tfUser.getText();
            String password = pfPass.getText();
            String confirm = pfConfirm.getText();

            if (!password.equals(confirm)) {
                lblMsg.setText("Passwords do not match!");
                return;
            }

            currentUser = new User(username, password);
            lblMsg.setText("Account created!");
            showAccountType(stage);
        });

        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showAccountType(Stage stage) {
        Label lblType = new Label("Select account type:");
        Button btnChecking = new Button("Checking");
        Button btnSavings = new Button("Savings");
        Label lblMsg = new Label();

        VBox root = new VBox(10, lblType, btnChecking, btnSavings, lblMsg);
        root.setAlignment(Pos.CENTER);

        btnChecking.setOnAction(e -> createAccount(1, lblMsg));
        btnSavings.setOnAction(e -> createAccount(2, lblMsg));

        stage.setScene(new Scene(root, 700, 500));
    }

    private void createAccount(int type, Label lblMsg) {
        int id = rand.nextInt(10000);
        Account acc = new Account(type, id);
        currentUser.addAccount(acc);
        lblMsg.setText("Account created! ID: " + id + "\n" + acc.checkAccountInfo());
    }

    public static void main(String[] args) {
        launch(args); // clean JavaFX entry
    }
}
