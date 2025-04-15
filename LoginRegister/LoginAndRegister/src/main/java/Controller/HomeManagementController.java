package Controller;

import Books.BookManagementController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeManagementController {

    @FXML
    private Pane PaneContent;

    @FXML
    private Button Setting;

    @FXML
    private Button myaccount;

    @FXML
    private Button logout;

    @FXML
    private Button button2;

    @FXML
    private Button buttonhome;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button buttonbooks;

    @FXML
    private ListView<String> listView;

    @FXML
    private VBox menuBox;

    @FXML
    private HBox ramdomhot;

    @FXML
    private TextField searching;

    @FXML
    void Homemanger(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/HomeManager.fxml"));
            Parent root = loader.load();
            HomeManagementController controller = loader.getController();
            controller.setUser(currentUser);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Trang chính - Manager");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Analytics(ActionEvent event) {

    }

    @FXML
    void Books(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/BookSearch.fxml"));
            Parent BookView = loader.load();
            BookManagementController controller = loader.getController();
            controller.setUser(currentUser);
            PaneContent.getChildren().setAll(BookView);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Issue(ActionEvent event) {

    }

    @FXML
    void Readers(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/Reader.fxml"));
            Parent readerView = loader.load();
            ReaderController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setpaneContent(PaneContent);
            PaneContent.getChildren().setAll(readerView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Settings(ActionEvent event) {
        menuBox.setVisible(!menuBox.isVisible());
    }

    private final ObservableList<String> data = FXCollections.observableArrayList(
            "Java", "JavaFX", "Python", "C++", "Kotlin", "JavaScript"
    );


    @FXML
    public void initialize() {
        showInitialItems();
        searching.setOnMouseClicked(event -> listView.setVisible(!listView.isVisible()));
        searching.setOnKeyReleased(event -> filterList());

    }


    private void showInitialItems() {
        listView.setItems(FXCollections.observableArrayList(data.subList(0, 3)));
    }

    private void filterList() {
        String keyword = searching.getText().toLowerCase();
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        for (String item : data) {
            if (item.toLowerCase().contains(keyword)) {
                filteredList.add(item);
            }
        }

        if (keyword.isEmpty()) {
            showInitialItems();
        } else if (filteredList.isEmpty()) {
            listView.setVisible(false);
        } else {
            listView.setItems(filteredList);
            listView.setVisible(true);
        }
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) logout.getScene().getWindow();
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.setTitle("Login & Register for Library Management System");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    void myaccount(ActionEvent event) {
        if (currentUser == null) {
            System.out.println("User is not set");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MyAccount.fxml"));
            Parent MyAccount = loader.load();
            MyAccountController controller = loader.getController();
            controller.setUser(currentUser);
            PaneContent.getChildren().setAll(MyAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ComMU(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/ComMu.fxml"));
            Parent readerView = loader.load();
            CommunityChatController controller = loader.getController();
            controller.setUser(currentUser);
            PaneContent.getChildren().setAll(readerView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}