package com.example.library.bookmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

public class BookManagementController {
     @FXML
     private TextField searchField;
     @FXML
     private Button searchButton;
     @FXML
     private ListView<String> bookListView;
     private final ObservableList<String> bookItems = FXCollections.observableArrayList();
     private final List<String> books = List.of("Book1","Book2","Book3");
     @FXML
     public void initialize() {
          bookListView.setItems(bookItems);
          bookListView.setVisible(false);
     }
     @FXML
     private void searchBooks(ActionEvent event) {
          String title = searchField.getText().trim().toLowerCase();
          bookItems.clear();
          if(title.isEmpty()) {
               bookListView.setVisible(false);
               return;
          }

          try {
               String jsonResponse = GoogleBookAPI.searchBooks(title, "","","");
               List<Book> books = BookParser.parseBooks(jsonResponse);
               if (books.isEmpty()) {
                    bookItems.add("Khong tim thay sach");
               }else{
                    for ( Book book : books) {
                         bookItems.add(book.getTitle() + " - " + book.getAuthor());
                    }
               }

               bookListView.setVisible(true);
          } catch (Exception e) {
               e.printStackTrace();
               bookItems.add("Loi khi lay du lieu tu API");
               bookListView.setVisible(true);
          }
     }
}