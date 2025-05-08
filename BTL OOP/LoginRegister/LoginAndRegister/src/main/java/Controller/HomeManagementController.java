package Controller;

import Books.*;
import Utils.MusicApp;
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

import java.io.IOException;
import java.util.List;

public class HomeManagementController extends HomeBaseController {
    @FXML
    private VBox vbox1, vbox2, vbox3, vbox4, vbox5, vbox6, vbox7, vbox8, vbox9, vbox10, vbox11, vbox12;
    @FXML
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;
    @FXML
    private Label lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9, lb10, lb11, lb12;
    @FXML
    private Pane PaneContent;
    @FXML
    private Button Setting;
    @FXML
    private Button myaccount;
    @FXML
    private Button music;
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

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    void Homemanger(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/FXML/HomeManager.fxml"));
            Parent root = loader.load();
            HomeManagementController controller = loader.getController();
            controller.setUser(currentUser);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home Manager");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Books(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/FXML/BookSearchManager.fxml"));
            Parent BookView = loader.load();
            BookSearchManagerController controller = loader.getController();
            controller.setUser(currentUser);
            PaneContent.getChildren().setAll(BookView);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void Readers(ActionEvent event) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/View/FXML/Reader.fxml"));
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
    @FXML
    public void handleBookClick(MouseEvent event, Book selectedBook) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/RatingBook.fxml"));
            Parent root = loader.load();
            RatingBookController ratingController = loader.getController();
            ratingController.setBookInfo(selectedBook);
            ratingController.setUser(currentUser);

            Stage ratingStage = new Stage();
            ratingStage.getIcons().add(new Image("/View/images/UETLogo.png"));
            ratingStage.setScene(new Scene(root));
            ratingStage.setTitle("Rating Book - " + selectedBook.getTitle());
            ratingStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        updateTopRatedBooks();
        updateTrendingBooks();
        img1.setOnMouseEntered(event -> super.scaleImage(img1, 1.1));
        img1.setOnMouseExited(event -> super.scaleImage(img1, 1));

        img2.setOnMouseEntered(event -> super.scaleImage(img2, 1.1));
        img2.setOnMouseExited(event -> super.scaleImage(img2, 1));

        img3.setOnMouseEntered(event -> super.scaleImage(img3, 1.1));
        img3.setOnMouseExited(event -> super.scaleImage(img3, 1));

        img4.setOnMouseEntered(event -> super.scaleImage(img4, 1.1));
        img4.setOnMouseExited(event -> super.scaleImage(img4, 1));

        img5.setOnMouseEntered(event -> super.scaleImage(img5, 1.1));
        img5.setOnMouseExited(event -> super.scaleImage(img5, 1));

        img6.setOnMouseEntered(event -> super.scaleImage(img6, 1.1));
        img6.setOnMouseExited(event -> super.scaleImage(img6, 1));

        img7.setOnMouseEntered(event -> super.scaleImage(img7, 1.1));
        img7.setOnMouseExited(event -> super.scaleImage(img7, 1));

        img8.setOnMouseEntered(event -> super.scaleImage(img8, 1.1));
        img8.setOnMouseExited(event -> super.scaleImage(img8, 1));

        img9.setOnMouseEntered(event -> super.scaleImage(img9, 1.1));
        img9.setOnMouseExited(event -> super.scaleImage(img9, 1));

        img10.setOnMouseEntered(event -> super.scaleImage(img10, 1.1));
        img10.setOnMouseExited(event -> super.scaleImage(img10, 1));

        img11.setOnMouseEntered(event -> super.scaleImage(img11, 1.1));
        img11.setOnMouseExited(event -> super.scaleImage(img11, 1));

        img12.setOnMouseEntered(event -> super.scaleImage(img12, 1.1));
        img12.setOnMouseExited(event -> super.scaleImage(img12, 1));

        super.createSnowEffect(PaneContent);
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
                        img7.setImage(image);
                        lb7.setText(book.getTitle());
                        img7.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 1:
                        img8.setImage(image);
                        lb8.setText(book.getTitle());
                        img8.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 2:
                        img9.setImage(image);
                        lb9.setText(book.getTitle());
                        img9.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 3:
                        img10.setImage(image);
                        lb10.setText(book.getTitle());
                        img10.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 4:
                        img11.setImage(image);
                        lb11.setText(book.getTitle());
                        img11.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                    case 5:
                        img12.setImage(image);
                        lb12.setText(book.getTitle());
                        img12.setOnMouseClicked(event -> handleBookClick(event, book));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isMusicPlaying = true;

    @FXML
    private void toggleMusic() {
        if (isMusicPlaying) {
            MusicApp.stopMusic();
            music.setText("Play Music");
        } else {
            MusicApp.playBackgroundMusic("managermusic.mp3");
            music.setText("Pause Music");
        }
        isMusicPlaying = !isMusicPlaying;
    }

}