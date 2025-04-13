package com.example.library.bookmanagement;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RatingBookController {
    @FXML
    private ImageView coverImage;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    public void setBookInfo(Book book) {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(String.join(", ", book.getAuthor()));
        try {
            Image img = new Image(book.getThumbnail(), true);
            coverImage.setImage(img);
        } catch (Exception e) {
            System.out.println("Không thể load ảnh: " + e.getMessage());
        }
    }
}