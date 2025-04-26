package Controller;

import Books.*;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.List;

public class HomeManagementController {

    @FXML
    private VBox vbox1, vbox2, vbox3, vbox4, vbox5, vbox6;

    @FXML
    private ImageView img1, img2, img3, img4, img5, img6;

    @FXML
    private Label lb1, lb2, lb3, lb4, lb5, lb6;

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
    void Books(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/BookSearchManager.fxml"));
            Parent BookView = loader.load();
            BookSearchManagerController controller = loader.getController();
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
    private void handleBookClick(MouseEvent event, Book selectedBook) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RatingBook.fxml"));
            Parent root = loader.load();
            RatingBookController ratingController = loader.getController();
            ratingController.setBookInfo(selectedBook);
            ratingController.setUser(currentUser);

            Stage ratingStage = new Stage();
            ratingStage.setScene(new Scene(root));
            ratingStage.setTitle("Rating Book - " + selectedBook.getTitle());
            ratingStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        showInitialItems();
        searching.setOnMouseClicked(event -> listView.setVisible(!listView.isVisible()));
        searching.setOnKeyReleased(event -> filterList());
        updateTopRatedBooks();
        img1.setOnMouseEntered(event -> scaleImage(img1, 1.1));
        img1.setOnMouseExited(event -> scaleImage(img1, 1));

        img2.setOnMouseEntered(event -> scaleImage(img2, 1.1));
        img2.setOnMouseExited(event -> scaleImage(img2, 1));

        img3.setOnMouseEntered(event -> scaleImage(img3, 1.1));
        img3.setOnMouseExited(event -> scaleImage(img3, 1));

        img4.setOnMouseEntered(event -> scaleImage(img4, 1.1));
        img4.setOnMouseExited(event -> scaleImage(img4, 1));

        img5.setOnMouseEntered(event -> scaleImage(img5, 1.1));
        img5.setOnMouseExited(event -> scaleImage(img5, 1));

        img6.setOnMouseEntered(event -> scaleImage(img6, 1.1));
        img6.setOnMouseExited(event -> scaleImage(img6, 1));
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


    public void updateTopRatedBooks() {
        List<Book> topRatedBooks = DatabaseHelper.getTopRatedBooks();

        for (int i = 0; i < 6 && i < topRatedBooks.size(); i++) {
            Book book = topRatedBooks.get(i);
            String thumbnailUrl = book.getThumbnail();

            if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
                thumbnailUrl = "/path/to/default/image.png";
            }

            try {
                Image image = new Image(thumbnailUrl);
                switch (i) {
                    case 0:
                        img1.setImage(image);
                        lb1.setText(book.getTitle());
                        img1.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 1:
                        img2.setImage(image);
                        lb2.setText(book.getTitle());
                        img2.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 2:
                        img3.setImage(image);
                        lb3.setText(book.getTitle());
                        img3.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 3:
                        img4.setImage(image);
                        lb4.setText(book.getTitle());
                        img4.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 4:
                        img5.setImage(image);
                        lb5.setText(book.getTitle());
                        img5.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 5:
                        img6.setImage(image);
                        lb6.setText(book.getTitle());
                        img6.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void scaleImage(ImageView imageView, double scale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), imageView);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        scaleTransition.play();
    }
}