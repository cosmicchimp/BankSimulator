package bankSimulator.model;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class DataHandler {
    // Put code inside methods!
    public void writeData() {
        String filePath = "C:\\Users\\maxla\\Desktop\\BankSimulator\\users.txt";
        System.out.println(">>>>> Writing to: " + filePath + " <<<<<");
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("john_doe");
            writer.println("jane_smith");
            System.out.println(">>>>> FILE WRITTEN <<<<<");
        } catch (IOException e) {
            System.out.println(">>>>> ERROR: " + e.getMessage() + " <<<<<");
            e.printStackTrace();
        }
    }
}