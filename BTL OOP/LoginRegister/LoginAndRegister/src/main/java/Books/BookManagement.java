package Books;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class BookManagement extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BookSearch.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1000,700);
        primaryStage.setTitle("Search Books");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
