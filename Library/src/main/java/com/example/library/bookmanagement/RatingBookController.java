package com.example.library.bookmanagement;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.security.PublicKey;

public class RatingBookController {
    @FXML
    private ImageView coverImage;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label tagLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private Button star1, star2, star3, star4, star5;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Button submitRatingButton;

    private int rating = 0;
    private Book currentBook;

    public void setBookInfo(Book book) {
        currentBook = book;
        titleLabel.setText(book.getTitle());
        authorLabel.setText("Authors: " + String.join(", ", book.getAuthor()));
        tagLabel.setText("Tags: " + String.join(" ,", book.getCategory()));
        descriptionLabel.setText("Description: " + book.getDescription());
        try {
            Image img = new Image(book.getThumbnail(), true);
            coverImage.setImage(img);
        } catch (Exception e) {
            System.out.println("Không thể load ảnh: " + e.getMessage());
        }
        double avg = DatabaseHelper.getAverageRating(book.getIsbn());
        ratingLabel.setText("Rating: " + String.format("%.2f", avg));
    }

    private void colorStars(int count) {
        Button[] stars = {star1, star2, star3, star4, star5};
        for (int i = 0; i < count; i++) {
            stars[i].setStyle("-fx-font-size: 30px; -fx-background-color: white; -fx-text-fill:gold;");
        }
    }

    private void resetStarColors() {
        Button[] stars = {star1, star2, star3, star4, star5};
        for (Button star : stars) {
            star.setStyle("-fx-font-size: 30px; -fx-background-color: while;-fx-text-fill: gray;");
        }
    }

    public void handleStarSelection(ActionEvent event) {
        resetStarColors();
        Button clickedStar = (Button) event.getSource();

        if(clickedStar == star1) {
            rating = 1;
            colorStars(1);
        } else if (clickedStar == star2) {
            rating = 2;
            colorStars(2);
        } else if (clickedStar == star3) {
            rating = 3;
            colorStars(3);
        } else if (clickedStar == star4) {
            rating = 4;
            colorStars(4);
        } else if (clickedStar == star5) {
            rating = 5;
            colorStars(5);
        }
    }

    @FXML
    public void handleSubmitRating(ActionEvent event) {
        if (currentBook == null || rating == 0) {
            System.out.println("Vui lòng chọn sách và số sao!");
            return;
        }

        String comment = commentTextArea.getText();
        String isbn = currentBook.getIsbn();

        DatabaseHelper.saveRating(isbn, rating, comment);

        System.out.println("Cảm ơn bạn đã phản hồi.");

        double avg = DatabaseHelper.getAverageRating(isbn);
        ratingLabel.setText("Rating: " + String.format("%.2f", avg));

        commentTextArea.clear();
        resetStarColors();
        rating = 0;
    }
}