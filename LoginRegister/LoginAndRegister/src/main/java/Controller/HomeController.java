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
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML
    private AnchorPane archopaneharry;

    @FXML
    private AnchorPane archopanelordking;

    @FXML
    private AnchorPane archopanelordking1;

    @FXML
    private AnchorPane archopanelordking2;

    @FXML
    private AnchorPane archopanelordking3;

    @FXML
    private AnchorPane archopanelordking4;

    @FXML
    private HBox ramdomhot;

    @FXML
    private Button buttonhary;

    @FXML
    private Button Setting;

    @FXML
    private Button button2;

    @FXML
    private Button button21;

    @FXML
    private Button buttonbooks;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private ListView<String> listView;

    @FXML
    private VBox menuBox;

    @FXML
    private TextField searching;

    @FXML
    void Analytics(ActionEvent event) {

    }

    @FXML
    void Books(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/book-search.fxml"));
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

    private List<AnchorPane> allPanes;

    @FXML
    public void initialize() {
        showInitialItems();
        searching.setOnMouseClicked(event -> listView.setVisible(!listView.isVisible()));
        searching.setOnKeyReleased(event -> filterList());

        allPanes = Arrays.asList(archopaneharry , archopanelordking , archopanelordking1 , archopanelordking2
        , archopanelordking3 , archopanelordking4 );


        showRandomThree();
    }

    private void showRandomThree() {
        List<AnchorPane> shuffled = new ArrayList<>(allPanes);
        Collections.shuffle(shuffled);
        List<AnchorPane> chosen = shuffled.subList(0, 3);

        for (AnchorPane pane : allPanes) {
            pane.setVisible(false);
            pane.setManaged(false);
        }

        for (AnchorPane pane : chosen) {
            pane.setVisible(true);
            pane.setManaged(true);
        }

        ramdomhot.getChildren().setAll(chosen);
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

}
