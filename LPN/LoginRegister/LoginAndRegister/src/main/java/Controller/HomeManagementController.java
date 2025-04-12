package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class HomeManagementController {

    @FXML
    private Button Setting;

    @FXML
    private Button myaccount;

    @FXML
    private Button logout;

    @FXML
    private Button button2;

    @FXML
    private Button button21;

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
    void Analytics(ActionEvent event) {

    }

    @FXML
    void Books(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/BookSearch.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Book Search");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Issue(ActionEvent event) {

    }

    @FXML
    void Readers(ActionEvent event) {

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
            stage.setTitle("Login");
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
            Parent root = loader.load();

            // Truyền user
            MyAccountController controller = loader.getController();
            controller.setUser(currentUser);

            // Lấy Stage hiện tại từ nút đang nhấn
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set scene mới (chuyển trang)
            stage.setScene(new Scene(root));
            stage.setTitle("MyAccount-Manager");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}