package Books;

import Controller.HomeUserController;
import Controller.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.List;

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

    @FXML
    private VBox commentListVBox;

    @FXML
    private AnchorPane acP;

    @FXML
    private Button borrowBk;

    private BookManagementController bookManagementController;
    private int rating = 0;
    private Book currentBook;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setBookManagementController(BookManagementController controller) {
        this.bookManagementController = controller;
        this.bookManagementController.setUser(user);
    }

    public void setHomeUserController(HomeUserController controller) {
        this.bookManagementController = controller.getBookManagementController(); // Lấy BookManagementController từ HomeUserController
    }

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
        loadComments(currentBook.getIsbn());
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
            star.setStyle("-fx-font-size: 30px; -fx-background-color: white;-fx-text-fill: gray;");
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
        int userId = user.getId();
        DatabaseHelper.saveRating(userId, isbn, rating, comment);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cảm ơn bạn!");
        alert.setHeaderText(null);
        alert.setContentText("Cảm ơn bạn đã phản hồi. Đánh giá của bạn đã được ghi nhận");
        alert.showAndWait();

        double avg = DatabaseHelper.getAverageRating(isbn);
        ratingLabel.setText("Rating: " + String.format("%.2f", avg));
        loadComments(currentBook.getIsbn());

        commentTextArea.clear();
        resetStarColors();
        rating = 0;
    }

    private void loadComments(String isbn) {
        commentListVBox.getChildren().clear();
        List<UserComment> comments = DatabaseHelper.getCommentsForBook(isbn);
        for (UserComment c : comments) {
            VBox box = new VBox();
            box.setSpacing(2);

            Label rating = new Label("⭐".repeat(c.getRating()));
            rating.setStyle("-fx-text-fill: gold; -fx-font-size: 14px;");

            Label content = new Label("\"" + c.getComment() + "\"");
            content.setWrapText(true);

            Label user = new Label("- " + c.getUserName());
            user.setStyle("-fx-font-style: italic; -fx-font-size: 12px;");

            box.getChildren().addAll(rating, content, user);
            box.setStyle("-fx-padding: 10; -fx-background-color: #f4f4f4; -fx-background-radius: 10;");
            commentListVBox.getChildren().add(box);
        }
    }

    @FXML
    public void handleBorrow() {
        bookManagementController.borrowBk(currentBook);
    }
}