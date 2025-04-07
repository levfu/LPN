package com.example.library.bookmanagement;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManagementController {
     @FXML
     private TableView<BookData> borrowBookTable;
     @FXML
     private TableColumn<BookData, String> isbnColumn;
     @FXML
     private TableColumn<BookData, String> titleColumn;
     @FXML
     private TableColumn<BookData, String> authorColumn;
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
     private ListView<String> bookListView;
     private final ObservableList<String> bookItems = FXCollections.observableArrayList();
     private final ObservableList<BookData> borrowedBooks = FXCollections.observableArrayList();
     private final List<Book> books = new ArrayList<>();
     private final List<String> bookList = List.of("Book1","Book2","Book3");
     private final int MAX_BOOKS = 20;
     @FXML
     public void initialize() {
          bookListView.setItems(bookItems);
          bookListView.setVisible(false);
          titleSearchField.setPromptText("Nhập tên sách...");
          authorSearchField.setPromptText("Nhập tên tác giả...");
          isbnSearchField.setPromptText("Nhập mã sách...");
          Platform.runLater(() -> titleSearchField.getParent().requestFocus());

          isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
          titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
          authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

          borrowBookTable.setItems(borrowedBooks);

          titleSearchField.setOnKeyPressed(keyEvent -> {
               if(keyEvent.getCode() == KeyCode.ENTER) {
                    searchBooks(new ActionEvent());
               }
          });
          authorSearchField.setOnKeyPressed(keyEvent -> {
               if(keyEvent.getCode() == KeyCode.ENTER) {
                    searchBooks(new ActionEvent());
               }
          });
          isbnSearchField.setOnKeyPressed(keyEvent -> {
               if(keyEvent.getCode() == KeyCode.ENTER) {
                    searchBooks(new ActionEvent());
               }
          });
          bookListView.setOnMouseClicked(mouseEvent -> {
               String selectedBook = bookListView.getSelectionModel().getSelectedItem();
               if (selectedBook != null) {
                    selectedBookTextField.setText(selectedBook);
               }
          });
     }
     @FXML
     private void searchBooks(ActionEvent event) {
          String title = titleSearchField.getText().trim().toLowerCase();
          String author = authorSearchField.getText().trim().toLowerCase();
          String isbn = isbnSearchField.getText().trim();
          if(title.isEmpty() && author.isEmpty() && isbn.isEmpty()) {
               bookItems.clear();
               bookItems.add("Vui lòng nhập ít nhất 1 thông tin để tìm kiếm!");
               bookListView.setVisible(true);
               return;
          }
          bookItems.clear();
          try {
               String jsonResponse = GoogleBookAPI.searchBooks(title, author,"",isbn);
               List<Book> bookList = BookParser.parseBooks(jsonResponse);
               if (bookList.isEmpty()) {
                    bookItems.add("Khong tim thay sach");
               }else{
                    for ( Book book : bookList) {
                         bookItems.add(book.getTitle() + " - " + String.join(", ",book.getAuthor()));
                         books.add(book);
                    }
               }

               bookListView.setVisible(true);
          } catch (Exception e) {
               e.printStackTrace();
               bookItems.add("Lỗi khi lấy dữ liệu từ API");
               bookListView.setVisible(true);
          }
     }
     @FXML
     private void borrowBook() {
          String selectedText = selectedBookTextField.getText();

          if(selectedText != null && selectedText.contains(" - ")) {
               String [] parts = selectedText.split(" - ");
               if (parts.length == 2) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    Book selectedBook = getBookFromtList(title,author);
                    if (selectedBook != null) {
                         String authorNames = String.join(", ", selectedBook.getAuthor());
                         if (borrowedBooks.size() >= MAX_BOOKS){
                              borrowedBooks.remove(0);
                         }
                         borrowedBooks.add(new BookData(selectedBook.getTitle(), authorNames, selectedBook.getIsbn()));
                    }
               }
          }
     }
      private Book getBookFromtList(String title, String author) {
          for (Book book : books) {
              if (book.getTitle().equals(title)) {
                   for (String bookAuthor : book.getAuthor()) {
                        if (bookAuthor.contains(author)) {
                             return book;
                        }
                   }
              }
          }
          return null;
      }
      @FXML
     private void returnBook() {
          BookData selectedRow = borrowBookTable.getSelectionModel().getSelectedItem();
          if (selectedRow != null) {
               Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
               confirmAlert.setTitle("Xác nhận trả sách");
               confirmAlert.setHeaderText("Bạn có chắc chắn muốn trả sách này");
               confirmAlert.setContentText("Sách: " + selectedRow.getTitle() + " - " + selectedRow.getAuthor());
               ButtonType result = confirmAlert.showAndWait().orElse(ButtonType.CANCEL);
               if (result == ButtonType.OK) {
                    borrowedBooks.remove(selectedRow);
               }
          } else {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Cảnh báo");
               alert.setHeaderText(null);
               alert.setContentText("Vui lòng chọn một sách để trả!");
               alert.showAndWait();
          }
      }

}