import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import bankSimulator.model.DataHandler;
public class BankApp extends Application {
    @Override
    public void start(Stage stage) {
        Label title = new Label("Java Banking Simulator");
        Button btn1 = new Button("Create Account");
        Button btn2 = new Button("View Balance");
        btn1.setOnAction(event -> {
            System.out.println("Button clicked!");
        });
        VBox root = new VBox(20); // 20px spacing between items
        root.setAlignment(Pos.CENTER); // Centers everything
        root.getChildren().addAll(title, btn1, btn2);

        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}