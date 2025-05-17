package Application;
import Utils.MusicApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class  LogReMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Login.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.getIcons().add(new Image("/View/images/UETLogo.png"));

            primaryStage.setScene(scene);
            primaryStage.setTitle("Login and Register for Library Management System");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
