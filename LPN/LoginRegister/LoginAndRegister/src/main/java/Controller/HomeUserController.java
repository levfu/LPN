package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class HomeUserController {

    @FXML
    private Button Setting;

    @FXML
    private Button buttonAnalyticsuser;

    @FXML
    private Button buttonOthersuser;

    @FXML
    private Button buttonbooksuser;

    @FXML
    private Button buttoncomMUuser;

    @FXML
    private Button buttonhomeuser;

    @FXML
    private ListView<String> listViewuser;

    @FXML
    private VBox menuBox;

    @FXML
    private HBox ramdomhotuser;

    @FXML
    private TextField searchinguser;

    @FXML
    void Analytics(ActionEvent event) {

    }

    @FXML
    void Booksuser(ActionEvent event) {
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
    void ComMU(ActionEvent event) {

    }

    @FXML
    void Homeuser(ActionEvent event) {

    }

    @FXML
    void Settings(ActionEvent event) {
        menuBox.setVisible(!menuBox.isVisible());
    }

    private final ObservableList<String> data = FXCollections.observableArrayList(
            "Java", "JavaFX", "Python", "C++", "Kotlin", "JavaScript"
    );

    private void showInitialItems() {
        listViewuser.setItems(FXCollections.observableArrayList(data.subList(0, 3)));
    }

    @FXML
    public void initialize() {
        showInitialItems();
        searchinguser.setOnMouseClicked(event -> listViewuser.setVisible(!listViewuser.isVisible()));
        searchinguser.setOnKeyReleased(event -> filterList());
    }


    private void filterList() {
        String keyword = searchinguser.getText().toLowerCase();
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        for (String item : data) {
            if (item.toLowerCase().contains(keyword)) {
                filteredList.add(item);
            }
        }

        if (keyword.isEmpty()) {
            showInitialItems();
        } else if (filteredList.isEmpty()) {
            listViewuser.setVisible(false);
        } else {
            listViewuser.setItems(filteredList);
            listViewuser.setVisible(true);
        }
    }

}
