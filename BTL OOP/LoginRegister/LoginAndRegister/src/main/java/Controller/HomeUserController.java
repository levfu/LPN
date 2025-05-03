package Controller;

import Books.Book;
import Books.BookManagementController;
import Books.DatabaseHelper;
import Books.RatingBookController;
import javafx.animation.*;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class HomeUserController extends HomeBaseController {

    @FXML
    private VBox vBox1, vBox2, vBox3, vBox4, vBox5, vBox6, vBox7, vBox8, vBox9, vBox10, vBox11, vBox12;

    @FXML
    private ImageView imgBook1, imgBook2, imgBook3, imgBook4, imgBook5, imgBook6, imgBook7, imgBook8, imgBook9, imgBook10, imgBook11, imgBook12;

    @FXML
    private Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12;

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


    private BookManagementController bookManagementController;

    public HomeUserController() {
        this.bookManagementController = new BookManagementController();
    }

    public BookManagementController getBookManagementController() {
        return this.bookManagementController;
    }

    @FXML
    void Booksuser(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/FXML/BookSearch.fxml"));
            Parent BookView = loader.load();
            BookManagementController controller = loader.getController();
            controller.setUser(currentUser);
            PaneContent.getChildren().setAll(BookView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBookClick(MouseEvent event, Book selectedBook) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/RatingBook.fxml"));
            Parent root = loader.load();
            RatingBookController ratingController = loader.getController();
            ratingController.setBookInfo(selectedBook);
            ratingController.setUser(currentUser);
            ratingController.setHomeUserController(this);
            ratingController.setBookManagementController(bookManagementController);


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
            loader = new FXMLLoader(getClass().getResource("/View/FXML/ComMU.fxml"));
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
            loader = new FXMLLoader(getClass().getResource("/View/FXML/HomeUser.fxml"));
            Parent root = loader.load();
            HomeUserController controller = loader.getController();
            controller.setUser(currentUser);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home User");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Settings(ActionEvent event) {
        menuBox.setVisible(!menuBox.isVisible());
    }

    @FXML
    public void initialize() {
        super.createSnowEffect(PaneContent);
        updateTopRatedBooks();
        updateTrendingBooks();
        imgBook1.setOnMouseEntered(event -> super.scaleImage(imgBook1, 1.1));
        imgBook1.setOnMouseExited(event -> super.scaleImage(imgBook1, 1));

        imgBook2.setOnMouseEntered(event -> super.scaleImage(imgBook2, 1.1));
        imgBook2.setOnMouseExited(event -> super.scaleImage(imgBook2, 1));

        imgBook3.setOnMouseEntered(event -> super.scaleImage(imgBook3, 1.1));
        imgBook3.setOnMouseExited(event -> super.scaleImage(imgBook3, 1));

        imgBook4.setOnMouseEntered(event -> super.scaleImage(imgBook4, 1.1));
        imgBook4.setOnMouseExited(event -> super.scaleImage(imgBook4, 1));

        imgBook5.setOnMouseEntered(event -> super.scaleImage(imgBook5, 1.1));
        imgBook5.setOnMouseExited(event -> super.scaleImage(imgBook5, 1));

        imgBook6.setOnMouseEntered(event -> super.scaleImage(imgBook6, 1.1));
        imgBook6.setOnMouseExited(event -> super.scaleImage(imgBook6, 1));

        imgBook7.setOnMouseEntered(event -> super.scaleImage(imgBook7, 1.1));
        imgBook7.setOnMouseExited(event -> super.scaleImage(imgBook7, 1));

        imgBook8.setOnMouseEntered(event -> super.scaleImage(imgBook8, 1.1));
        imgBook8.setOnMouseExited(event -> super.scaleImage(imgBook8, 1));

        imgBook9.setOnMouseEntered(event -> super.scaleImage(imgBook9, 1.1));
        imgBook9.setOnMouseExited(event -> super.scaleImage(imgBook9, 1));

        imgBook10.setOnMouseEntered(event -> super.scaleImage(imgBook10, 1.1));
        imgBook10.setOnMouseExited(event -> super.scaleImage(imgBook10, 1));

        imgBook11.setOnMouseEntered(event -> super.scaleImage(imgBook11, 1.1));
        imgBook11.setOnMouseExited(event -> super.scaleImage(imgBook11, 1));

        imgBook12.setOnMouseEntered(event -> super.scaleImage(imgBook12, 1.1));
        imgBook12.setOnMouseExited(event -> super.scaleImage(imgBook12, 1));
    }

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }



    @FXML
    void logout(ActionEvent event) {
        super.logout((Node) event.getSource());
    }


    @FXML
    void myaccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/MyAccount.fxml"));
            Parent MyAccount = loader.load();
            MyAccountController controller = loader.getController();
            controller.setUser(currentUser);
            PaneContent.getChildren().setAll(MyAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public void updateTrendingBooks() {
        List<Book> trendingBooks = DatabaseHelper.getTrendingBooks();

        for (int i = 0; i < 6 && i < trendingBooks.size(); i++) {
            Book book = trendingBooks.get(i);
            String thumbnailUrl = book.getThumbnail();

            if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
                thumbnailUrl = "/path/to/default/image.png";
            }

            try {
                Image image = new Image(thumbnailUrl);
                switch (i) {
                    case 0:
                        imgBook7.setImage(image);
                        label7.setText(book.getTitle());
                        imgBook7.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 1:
                        imgBook8.setImage(image);
                        label8.setText(book.getTitle());
                        imgBook8.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 2:
                        imgBook9.setImage(image);
                        label9.setText(book.getTitle());
                        imgBook9.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 3:
                        imgBook10.setImage(image);
                        label10.setText(book.getTitle());
                        imgBook10.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 4:
                        imgBook11.setImage(image);
                        label11.setText(book.getTitle());
                        imgBook11.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 5:
                        imgBook12.setImage(image);
                        label12.setText(book.getTitle());
                        imgBook12.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
