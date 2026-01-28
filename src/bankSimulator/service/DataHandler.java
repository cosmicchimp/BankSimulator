package bankSimulator.service;
import bankSimulator.model.Account;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
public class DataHandler {
    //Path that will be used by the file readers/writers
    private String path;

    //Public class constructor used to assign path
    public DataHandler(String path) {
        this.path = path;
    }

    //Public method used to write username and password to the path of choice
    public void writeUser(String user, String password) {
        String filePath = "users.txt";
        System.out.println(">>>>> Writing to: " + filePath + " <<<<<");
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(user + "," + password);
            System.out.println(">>>>> FILE WRITTEN <<<<<");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file: >>>>>>\n" + e);
        }
    }

    //Public method used to find a user and its corresponding data in a file
    // based on username

    public String[] findUser(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(",");
                String currentUser = splitLine[0];
                if (currentUser.equals(username)) {
                    String[] userInfo = {splitLine[0], splitLine[1]};
                    return userInfo;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file: >>>>>>\n" + e);
        }
        return null;
    }

    //Method to write an account to file once it has been created
    public void writeAccount(Account accountInfo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            writer.println("Hello");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing account to file: >>>>>>>> \n" + e);
        }
    }

}