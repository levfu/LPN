package Controller;

import Books.Book;
import Books.BookManagementController;
import Books.DatabaseHelper;
import Books.RatingBookController;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class HomeUserController {

    @FXML
    private VBox vBox1, vBox2, vBox3, vBox4, vBox5, vBox6;

    @FXML
    private ImageView imgBook1, imgBook2, imgBook3, imgBook4, imgBook5, imgBook6;

    @FXML
    private Label label1, label2, label3, label4, label5, label6;

    @FXML
    private Button Setting;

    @FXML
    private Button buttonlogout;

    @FXML
    private Button myaccount;

    @FXML
    private Button buttonOthersuser;

    @FXML
    private Button buttonbooksuser;

    @FXML
    private Button buttoncomMUuser;

    @FXML
    private Button buttonhomeuser;


    @FXML
    private Pane PaneContent;

    @FXML
    private ListView<String> listViewuser;

    @FXML
    private VBox menuBox;

    @FXML
    private HBox randomhotuser;

    @FXML
    private TextField searchinguser;

    @FXML
    void Booksuser(ActionEvent event) {
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

    @FXML
    void Homeuser(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/HomeUser.fxml"));
            Parent root = loader.load();
            HomeUserController controller = loader.getController();
            controller.setUser(currentUser);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Trang chính - User");
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

    private void showInitialItems() {
        listViewuser.setItems(FXCollections.observableArrayList(data.subList(0, 3)));
    }

    @FXML
    public void initialize() {
        showInitialItems();
        searchinguser.setOnMouseClicked(event -> listViewuser.setVisible(!listViewuser.isVisible()));
        searchinguser.setOnKeyReleased(event -> filterList());
        updateTopRatedBooks();
        imgBook1.setOnMouseEntered(event -> scaleImage(imgBook1, 1.1));
        imgBook1.setOnMouseExited(event -> scaleImage(imgBook1, 1));

        imgBook2.setOnMouseEntered(event -> scaleImage(imgBook2, 1.1));
        imgBook2.setOnMouseExited(event -> scaleImage(imgBook2, 1));

        imgBook3.setOnMouseEntered(event -> scaleImage(imgBook3, 1.1));
        imgBook3.setOnMouseExited(event -> scaleImage(imgBook3, 1));

        imgBook4.setOnMouseEntered(event -> scaleImage(imgBook4, 1.1));
        imgBook4.setOnMouseExited(event -> scaleImage(imgBook4, 1));

        imgBook5.setOnMouseEntered(event -> scaleImage(imgBook5, 1.1));
        imgBook5.setOnMouseExited(event -> scaleImage(imgBook5, 1));

        imgBook6.setOnMouseEntered(event -> scaleImage(imgBook6, 1.1));
        imgBook6.setOnMouseExited(event -> scaleImage(imgBook6, 1));
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

    @FXML
    void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) buttonlogout.getScene().getWindow();
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
                        imgBook1.setImage(image);
                        label1.setText(book.getTitle());
                        imgBook1.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 1:
                        imgBook2.setImage(image);
                        label2.setText(book.getTitle());
                        imgBook2.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 2:
                        imgBook3.setImage(image);
                        label3.setText(book.getTitle());
                        imgBook3.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 3:
                        imgBook4.setImage(image);
                        label4.setText(book.getTitle());
                        imgBook4.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 4:
                        imgBook5.setImage(image);
                        label5.setText(book.getTitle());
                        imgBook5.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 5:
                        imgBook6.setImage(image);
                        label6.setText(book.getTitle());
                        imgBook6.setOnMouseClicked(event -> handleBookClick(event, book));
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
