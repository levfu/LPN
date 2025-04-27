package Books;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Controller.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class BookSearchManagerController {

    @FXML
    private AnchorPane Apanecontent;

    @FXML
    private TableColumn<Book, String> authorsBook;

    @FXML
    private TableColumn<Book, String> isbnBook;

    @FXML
    private TableColumn<Book, String> titleBook;

    @FXML
    private TableView<Book> BookTable;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    private ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    public void initialize() {
        isbnBook.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleBook.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorsBook.setCellValueFactory(new PropertyValueFactory<>("author"));
        loadBooks("book");
        searchButton.setOnAction(event -> handleSearch());
        searchField.setOnAction(event -> handleSearch());
        BookTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Book selectedBook = BookTable.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    openRatingView(selectedBook);
                }
            }
        });
    }

    private void handleSearch() {
        String title = searchField.getText().trim();
        if (title.isEmpty()) {
            System.out.println("Vui lòng nhập tên sách.");
            return;
        }
        loadBooks(title);
    }

    private void loadBooks(String title) {
        try {
            String json = GoogleBookAPI.searchBooks(title, "", "", "");
            if (json != null) {
                List<Book> books = BookParser.parseBooks(json);
                bookObservableList.clear();
                bookObservableList.addAll(books);
                BookTable.setItems(bookObservableList);
            } else {
                System.out.println("Không lấy được dữ liệu từ Google Books API.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openRatingView(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RatingBook.fxml"));
            Parent ratingView = loader.load();
            RatingBookController ratingCtrl = loader.getController();
            ratingCtrl.setBookInfo(book);
            if (currentUser != null) {
                ratingCtrl.setUser(currentUser);
            }
            Apanecontent.getChildren().setAll(ratingView);
            AnchorPane.setTopAnchor(ratingView, 0.0);
            AnchorPane.setBottomAnchor(ratingView, 0.0);
            AnchorPane.setLeftAnchor(ratingView, 0.0);
            AnchorPane.setRightAnchor(ratingView, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
