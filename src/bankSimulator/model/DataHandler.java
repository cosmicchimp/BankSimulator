package bankSimulator.model;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class DataHandler {

    public void writeData(String user) {
        String filePath = "users.txt";
        System.out.println(">>>>> Writing to: " + filePath + " <<<<<");
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(user);
            System.out.println(">>>>> FILE WRITTEN <<<<<");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public ArrayList readData() {
//
//    }
}