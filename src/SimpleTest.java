import java.io.PrintWriter;

public class SimpleTest {
    public static void main(String[] args) {
        System.out.println("STARTING");

        try {
            PrintWriter w = new PrintWriter("C:\\Users\\maxla\\Desktop\\test.txt");
            w.println("IT WORKED");
            w.close();
            System.out.println("FILE CREATED");
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }

        System.out.println("DONE");
    }
}
