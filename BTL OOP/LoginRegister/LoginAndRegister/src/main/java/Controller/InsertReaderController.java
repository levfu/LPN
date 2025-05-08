package Controller;

import Database.LogReDatabase;
import Utils.EmailSender;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class InsertReaderController {

    @FXML
    private TextField addressField;

    @FXML
    private TextField birthdayField;

    @FXML
    private TextField emailField;

    @FXML
    private Button handleInsert;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField roleField;

    private ReaderController readerController;

    public void setReaderController(ReaderController readerController) {
        this.readerController = readerController;
    }
    @FXML
    void Insert(ActionEvent event) {
        try {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleField.getText().trim();
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String birthdayText = birthdayField.getText().trim();
            LocalDate birthday = null;
            try {
                birthday = LocalDate.parse(birthdayText);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Birthday must be in the format yyyy-MM-dd (e.g., 2000-01-01)");
            }
            String address = addressField.getText().trim();
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || birthday == null || address.isEmpty() || role.isEmpty()) {
                throw new IllegalArgumentException("All fields are required!");
            }
            String verificationCode = String.valueOf((int)(Math.random() * 900000) + 100000);
            EmailSender.sendVerificationEmail(email, verificationCode);
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Email Verification");
            dialog.setHeaderText("A verification code has been sent to your email.");
            dialog.setContentText("Enter the code:");

            Optional<String> result = dialog.showAndWait();
            if (!result.isPresent() || !result.get().trim().equals(verificationCode)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Incorrect Verification Code");
                alert.setHeaderText(null);
                alert.setContentText("The verification code is incorrect or has been canceled.");
                alert.showAndWait();
                return;
            }
            User newUser = new User(0, email, password, role, name, phone, birthday, address);
            boolean success = LogReDatabase.insertUser(newUser);
            Alert alert = new Alert(success ? AlertType.INFORMATION : AlertType.ERROR);
            alert.setTitle("Add User");
            alert.setHeaderText(null);
            alert.setContentText(success ? "User has been added successfully!" : "Failed to add user!");
            alert.showAndWait();

            if (success) {
                if (readerController != null) {
                    readerController.loadUsers();
                }
                ((Stage) handleInsert.getScene().getWindow()).close();
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Data");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
