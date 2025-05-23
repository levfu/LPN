package Books;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Controller.User;
import javafx.util.Callback;


public class BookManagementController {
     @FXML
     private Label khosachLabel;
     @FXML
     private TableView<BookData> borrowBookTable;
     @FXML
     private TableColumn<BookData, String> isbnColumn;
     @FXML
     private TableColumn<BookData, String> titleColumn;
     @FXML
     private TableColumn<BookData, String> authorColumn;
     @FXML
     private TableColumn<BookData, String> dueDateColumn;
     @FXML
     private TextField titleSearchField;
     @FXML
     private TextField authorSearchField;
     @FXML
     private TextField isbnSearchField;
     @FXML
     private TextField selectedBookTextField;
     @FXML
     private Button borrowButton;
     @FXML
     private Button searchButton;
     @FXML
     private Button returnButton;
     @FXML
     private ListView<Book> bookListView;
     private final ObservableList<String> bookItems = FXCollections.observableArrayList();
     private final ObservableList<BookData> borrowedBooks = FXCollections.observableArrayList();
     private final List<Book> books = new ArrayList<>();
     private final List<String> bookList = List.of("Book1", "Book2", "Book3");
     private final int MAX_BOOKS = 20;

     private User currentUser;

     public void setUser(User user) {
          this.currentUser = user;

          if (borrowedBooks != null) {
               borrowedBooks.clear();
               borrowedBooks.addAll(DatabaseHelper.getAllBooks(currentUser.getId()));
          }
     }


     @FXML
     public void initialize() {
          DatabaseHelper.createTable();
          bookListView.setItems(FXCollections.observableList(books));
          bookListView.setVisible(false);
          titleSearchField.setPromptText("Enter book title...");
          authorSearchField.setPromptText("Enter author name...");
          isbnSearchField.setPromptText("Enter book ISBN...");
          Platform.runLater(() -> titleSearchField.getParent().requestFocus());

          isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
          titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
          authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
          dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

          borrowBookTable.setItems(borrowedBooks);

          bookListView.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {
               @Override
               public ListCell<Book> call(ListView<Book> param) {
                    return new ListCell<Book>() {
                         @Override
                         protected void updateItem(Book item, boolean empty) {
                              super.updateItem(item, empty);
                              if (empty || item == null) {
                                   setText(null);
                              } else {
                                   // Hiển thị Title - Authors
                                   setText(item.getTitle() + " - " + String.join(", ", item.getAuthor()));
                              }
                         }
                    };
               }
          });

          titleSearchField.setOnKeyPressed(keyEvent -> {
               if (keyEvent.getCode() == KeyCode.ENTER) {
                    searchBooks(new ActionEvent());
               }
          });
          authorSearchField.setOnKeyPressed(keyEvent -> {
               if (keyEvent.getCode() == KeyCode.ENTER) {
                    searchBooks(new ActionEvent());
               }
          });
          isbnSearchField.setOnKeyPressed(keyEvent -> {
               if (keyEvent.getCode() == KeyCode.ENTER) {
                    searchBooks(new ActionEvent());
               }
          });
          bookListView.setOnMouseClicked(mouseEvent -> {
               Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
               if (selectedBook != null) {
                    String bookInfo = selectedBook.getTitle() + " - " + String.join(", ", selectedBook.getAuthor());
                    selectedBookTextField.setText(bookInfo);

                    showBookDetail(selectedBook);
               }
          });


          borrowBookTable.setOnMouseClicked(mouseEvent -> {
               if (mouseEvent.getClickCount() == 2) {
                    BookData selected = borrowBookTable.getSelectionModel().getSelectedItem();
                    if (selected != null) {
                         System.out.println("Book selected !");
                         String isbn = selected.getIsbn();
                         try {
                              String jsonResponse = GoogleBookAPI.searchBooks("", "", "", isbn);
                              List<Book> books = BookParser.parseBooks(jsonResponse);
                              if (!books.isEmpty()) {
                                   showBookDetail(books.get(0));
                              } else {
                                   showAlert("Book not found !", "Can't find book on Google API !");
                              }
                         } catch (Exception e) {
                              e.printStackTrace();
                              showAlert("API Error !", "Can't get data from Google API !");
                         }
                    }
               }
          });
     }


     @FXML
     private void searchBooks(ActionEvent event) {
          String title = titleSearchField.getText().trim().toLowerCase();
          String author = authorSearchField.getText().trim().toLowerCase();
          String isbn = isbnSearchField.getText().trim();
          if (title.isEmpty() && author.isEmpty() && isbn.isEmpty()) {
               bookItems.clear();
               bookItems.add("Please enter at least one search criteria !");
               bookListView.setVisible(true);
               return;
          }
          bookItems.clear();
          try {
               String jsonResponse = GoogleBookAPI.searchBooks(title, author, "", isbn);
               List<Book> bookList = BookParser.parseBooks(jsonResponse);
               books.clear();

               if (bookList.isEmpty()) {
                    bookItems.clear();
                    bookItems.add("Book not found !");
               } else {
                    for (Book book : bookList) {
                         bookItems.add(book.getTitle() + " - " + String.join(", ", book.getAuthor()));
                         books.add(book);
                    }
               }
               bookListView.setItems(FXCollections.observableList(books));
               bookListView.setVisible(true);
          } catch (Exception e) {
               e.printStackTrace();
               bookItems.clear();
               bookItems.add("Error API data !");
               bookListView.setVisible(true);
          }
     }


     @FXML
     public void borrowBook() {
          String selectedText = selectedBookTextField.getText();

          if (selectedText != null && !selectedText.isEmpty()) {
               Book selectedBook = getBookFromText(selectedText); // từ ListView dòng text → tìm Book gốc

               if (selectedBook != null) {
                    String authorNames = String.join(", ", selectedBook.getAuthor());
                    String dueDate = LocalDate.now().plusDays(15).toString();
                    String thumbnail = selectedBook.getThumbnail();
                    String description = selectedBook.getDescription();
                    String tags = String.join(" ,", selectedBook.getCategory());

                    if (borrowedBooks.size() >= MAX_BOOKS) {
                         borrowedBooks.remove(0);
                    }

                    BookData data = new BookData(selectedBook.getTitle(), authorNames, selectedBook.getIsbn(), dueDate, thumbnail, description, tags);

                    borrowedBooks.add(data);
                    DatabaseHelper.saveToDatabase(currentUser.getId(), data);
               } else {
                    showAlert("Book not found !", "Unable to borrow because no matching book was found.");
               }
          }
     }


     private Book getBookFromText(String displayText) {
          for (Book book : books) {
               String display = book.getTitle() + " - " + String.join(", ", book.getAuthor());
               if (display.equals(displayText)) {
                    return book;
               }
          }
          return null;
     }


     private void showAlert(String title, String content) {
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle(title);
          alert.setHeaderText(null);
          alert.setContentText(content);
          alert.showAndWait();
     }

     private Book getBookByIsbn(String isbn) {
          for (Book book : books) {
               if (book.getIsbn().equals(isbn)) {
                    return book;
               }
          }
          return null;
     }


     @FXML
     private void returnBook() {
          BookData selectedRow = borrowBookTable.getSelectionModel().getSelectedItem();
          if (selectedRow != null) {
               Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
               confirmAlert.setTitle("Confirm return this book ?");
               confirmAlert.setHeaderText("Are you sure want to return this book ?");
               confirmAlert.setContentText("Book: " + selectedRow.getTitle() + " - " + selectedRow.getAuthor());
               ButtonType result = confirmAlert.showAndWait().orElse(ButtonType.CANCEL);
               if (result == ButtonType.OK) {
                    borrowedBooks.remove(selectedRow);
                    DatabaseHelper.deleteFromDatabase(selectedRow.getIsbn());
               }
          } else {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Warning");
               alert.setHeaderText(null);
               alert.setContentText("Please choose one book to return !");
               alert.showAndWait();
          }
     }


     private void showBookDetail(Book book) {
          try {
               URL fxmlurl = getClass().getResource("/View/FXML/RatingBook.fxml");
               if (fxmlurl == null) {
                    throw new IOException("File not found");
               }
               FXMLLoader loader = new FXMLLoader(fxmlurl);
               Parent root = loader.load();
               RatingBookController ratingBookController = loader.getController();
               ratingBookController.setBookInfo(book);
               ratingBookController.setUser(currentUser);
               ratingBookController.setBookManagementController(this);

               Stage detailStage = new Stage();
               detailStage.getIcons().add(new Image("/View/images/UETLogo.png"));
               detailStage.setTitle("Rating book");
               detailStage.setScene(new Scene(root));
               detailStage.show();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }


     @FXML
     public void borrowBk(Book selectedBook) {
          if (selectedBook != null) {
               String authorNames = String.join(", ", selectedBook.getAuthor());
               String dueDate = LocalDate.now().plusDays(15).toString();
               String thumbnail = selectedBook.getThumbnail();
               String tags = String.join(" ,", selectedBook.getCategory());
               String description = selectedBook.getDescription();

               if (borrowedBooks.size() >= MAX_BOOKS) {
                    borrowedBooks.remove(0);
               }

               BookData data = new BookData(selectedBook.getTitle(), authorNames, selectedBook.getIsbn(), dueDate, thumbnail, description, tags);

               borrowedBooks.add(data);
               DatabaseHelper.saveToDatabase(currentUser.getId(), data);
          } else {
               showAlert("Book Not Found !", "Unable to borrow because no matching book was found.");
          }
     }
}