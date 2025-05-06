package Controller;
import Database.LogReDatabase;
import Utils.EmailSender;
import Utils.VerificationCodeGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordController {
    @FXML
    private TextField txtEmail;
    @FXML private Button reset;
    @FXML private Button verifyEmail;
    @FXML private TextField txtPassword;
    @FXML private TextField txtconfirm;



    @FXML
    private void initialize() {
        verifyEmail.setOnAction(event -> handleVerifyEmail());
        reset.setOnAction(event -> handleResetPassword());
        reset.setDisable(true);
    }


    private void handleVerifyEmail() {
        String email = txtEmail.getText().trim();

        if (email.isEmpty()) {
            showCustomAlert(Alert.AlertType.WARNING, "Warning", "Please enter your email !", "/View/images/ErrorLogo.png");
            return;
        }

        if (!isValidEmail(email)) {
            showCustomAlert(Alert.AlertType.ERROR, "Error", "Email not found !", "/View/images/ErrorLogo.png");
            return;
        }

        if (!LogReDatabase.checkEmailExists(email)) {
            showCustomAlert(Alert.AlertType.ERROR, "Error", "Email not found in database !", "/View/images/ErrorLogo.png");
            return;
        }


        String codeToSend = VerificationCodeGenerator.generateCode();

        new Thread(() -> {
            EmailSender.sendVerificationEmail(email,codeToSend);

            javafx.application.Platform.runLater(() -> {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Email verification");
                dialog.setHeaderText("Enter verification code sent to your email:");
                dialog.setContentText("Verification code:");

                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/View/images/EmailLogo.png")));

                dialog.showAndWait().ifPresent(userCode -> {
                    if (userCode.equals(codeToSend)) {
                        showCustomAlert(Alert.AlertType.INFORMATION, "Verified successfully !", "Your email has been verified !", "/View/images/TickLogo.png");
                        reset.setDisable(false);
                    } else {
                        showCustomAlert(Alert.AlertType.ERROR, "Error", "Wrong verification code !", "/View/images/ErrorLogo.png");
                    }
                });
            });
        }).start();
    }


    public void handleResetPassword() {
        String email = txtEmail.getText().trim();
        String newPassword = txtPassword.getText().trim();
        String confirmPassword = txtconfirm.getText().trim();


        if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showCustomAlert(Alert.AlertType.ERROR, "Error", "Please enter email and password !", "/View/images/ErrorLogo.png");
            return;
        }


        if (!newPassword.equals(confirmPassword)) {
            showCustomAlert(Alert.AlertType.ERROR, "Error", "Password and confirm password not match !", "/View/images/ErrorLogo.png");
            return;
        }


        LogReDatabase.updatePassword(email, newPassword);


        EmailSender.sendNewPasswordEmail(email,newPassword);

        showCustomAlert(Alert.AlertType.INFORMATION, "Change password successfully !", "Your new password has been sent your email !", "/View/images/TickLogo.png");
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Matcher m = Pattern.compile(emailRegex).matcher(email);
        return m.matches();
    }


    private void showCustomAlert(Alert.AlertType type, String title, String message, String imagePath) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                Image img = new Image(getClass().getResourceAsStream(imagePath));
                ImageView iv = new ImageView(img);
                iv.setFitHeight(50);
                iv.setFitWidth(55);
                alert.setGraphic(iv);
                Stage st = (Stage) alert.getDialogPane().getScene().getWindow();
                st.getIcons().add(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        alert.showAndWait();
    }


}
