package Controller;

import Books.Book;
import Books.DatabaseHelper;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;
import java.util.List;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public abstract class HomeBaseController {

    protected User currentUser;
    protected Pane PaneContent;

    public void setUser(User user) {
        this.currentUser = user;
    }

    public void setPaneContent(Pane paneContent) {
        this.PaneContent = paneContent;
    }

    @FXML
    protected ImageView imgBook1, imgBook2, imgBook3, imgBook4, imgBook5, imgBook6, imgBook7, imgBook8, imgBook9, imgBook10, imgBook11, imgBook12;
    @FXML
    protected Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12;

    public void updateTopRatedBooks() {
        List<Book> topRatedBooks = DatabaseHelper.getTopRatedBooks();

        for (int i = 0; i < 6 && i < topRatedBooks.size(); i++) {
            Book book = topRatedBooks.get(i);
            String thumbnailUrl = book.getThumbnail();

            if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
                thumbnailUrl = "/View/images/avt_default.jpg";
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


    public void updateTrendingBooks() {
        List<Book> trendingBooks = DatabaseHelper.getTrendingBooks();

        for (int i = 0; i < 6 && i < trendingBooks.size(); i++) {
            Book book = trendingBooks.get(i);
            String thumbnailUrl = book.getThumbnail();

            if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
                thumbnailUrl = "/View/images/avt_default.jpg";
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


    public void handleBookClick(MouseEvent event, Book selectedBook) {

    }

    protected void scaleImage(ImageView image, double scale) {
        image.setScaleX(scale);
        image.setScaleY(scale);
    }

    protected void createSnowEffect(Pane targetPane) {
        double snowWidth = 1200;
        double snowHeight = 700;
        int maxSnowflakes = 200;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
            // Đếm số lượng tuyết đang tồn tại
            long currentSnowflakes = targetPane.getChildren().stream()
                    .filter(node -> node instanceof Circle)
                    .count();

            if (currentSnowflakes >= maxSnowflakes) return; // Không thêm mới nếu đã đủ

            Circle snowflake = new Circle(2 + Math.random() * 3, Color.WHITE);
            snowflake.setOpacity(Math.random());
            snowflake.setLayoutX(Math.random() * snowWidth);
            snowflake.setLayoutY(-100);
            targetPane.getChildren().add(snowflake);

            TranslateTransition fall = new TranslateTransition(Duration.seconds(10 + Math.random() * 10), snowflake);
            fall.setFromY(-100);
            fall.setToY(snowHeight + 100);
            fall.setCycleCount(1);
            fall.setInterpolator(Interpolator.LINEAR);
            fall.setOnFinished(event -> targetPane.getChildren().remove(snowflake)); // Xóa khi rơi xong

            TranslateTransition sway = new TranslateTransition(Duration.seconds(2 + Math.random() * 2), snowflake);
            sway.setFromX(0);
            sway.setByX(30 - Math.random() * 60);
            sway.setCycleCount(TranslateTransition.INDEFINITE);
            sway.setAutoReverse(true);

            new ParallelTransition(fall, sway).play();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }



    protected void logout(Node sourceNode) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Login.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login and Register for Library Management System");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
