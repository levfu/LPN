package Controller;

import Database.ChatDatabase;
import Model.Message;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class CommunityChatController {

    @FXML
    private VBox messageBox;



    @FXML
    private TextField inputField;

    @FXML
    private Button sendButton;

    private User currentUser;
    private ChatDatabase chatDatabase;
    private Timeline timeline;
    private static final String URL = "jdbc:sqlite:C:\\Users\\Admin\\Documents\\GitHub\\LPN\\BTL OOP\\LoginRegister\\LoginAndRegister\\src\\main\\resources\\library.db";

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            chatDatabase = new ChatDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputField.setOnAction(e -> sendMessage());
        sendButton.setOnAction(e -> sendMessage());
        startAutoRefresh();
    }

    public void sendMessage() {
        String content = inputField.getText().trim();
        if (!content.isEmpty() && currentUser != null) {
            try {
                chatDatabase.sendMessage(currentUser.getId(), content);
                inputField.clear();
                loadMessages();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadMessages() {
        try {
            List<Message> messages = chatDatabase.getAllMessages();
            Platform.runLater(() -> {
                messageBox.getChildren().clear();
                for (Message m : messages) {
                    boolean isCurrentUser = currentUser != null && currentUser.getId() == m.getSenderId();
                    User sender = null;
                    sender = chatDatabase.getUserById(m.getSenderId());
                    String displayName = isCurrentUser ? "You" : (sender != null && sender.getName() != null && !sender.getName().isEmpty()
                            ? sender.getName()
                            : "User " + m.getSenderId());


                    Text messageText = new Text(
                            displayName + " [" + m.getFormattedTimestamp() + "]:\n" + m.getContent()
                    );
                    messageText.setWrappingWidth(300);

                    VBox bubble = new VBox(messageText);
                    bubble.setPadding(new Insets(10));
                    bubble.setStyle(isCurrentUser
                            ? "-fx-background-color: #89abe3; -fx-background-radius: 10;"
                            : "-fx-background-color: #ea738d; -fx-background-radius: 10;");
                    bubble.setMaxWidth(350);

                    HBox messageContainer = new HBox(bubble);
                    messageContainer.setPadding(new Insets(5, 10, 5, 10));
                    messageContainer.setAlignment(isCurrentUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
                    messageContainer.setMaxWidth(Double.MAX_VALUE);

                    messageBox.getChildren().add(messageContainer);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startAutoRefresh() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> loadMessages()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stopAutoRefresh() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
