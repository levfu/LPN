package Controller;
import Database.LogReDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LogReController implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<String> roleComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleComboBox.getItems().addAll("User", "Manager");
        roleComboBox.setEditable(false);
    }

    @FXML
    private void handleLogin() {
        String email = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String role = roleComboBox.getValue();

        if (role == null) {
            showCustomAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn vai trò!", "/View/images/ErrorLogo.png");
            return;
        }

        if (email.isEmpty() || password.isEmpty()) {
            showCustomAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập đủ thông tin!", "/View/images/ErrorLogo.png");
            return;
        }

        if (!isValidEmail(email)) {
            showCustomAlert(Alert.AlertType.ERROR, "Lỗi", "Email không hợp lệ! Vui lòng nhập đúng định dạng.", "/View/images/ErrorLogo.png");
            return;
        }

        if (LogReDatabase.checkLogin(email, password, role)) {
            showCustomAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng nhập thành công với vai trò " + role + "!", "/View/images/TickLogo.png");
        } else {
            showCustomAlert(Alert.AlertType.ERROR, "Lỗi", "Sai Email hoặc mật khẩu!", "/View/images/ErrorLogo.png");
        }
    }

    @FXML
    private void handleRegister() {
        String email = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String role = roleComboBox.getValue();

        if (role == null) {
            showCustomAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn vai trò!", "/View/images/ErrorLogo.png");
            return;
        }

        if (email.isEmpty() || password.isEmpty()) {
            showCustomAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập đủ thông tin!", "/View/images/ErrorLogo.png");
            return;
        }

        if (!isValidEmail(email)) {
            showCustomAlert(Alert.AlertType.ERROR, "Lỗi", "Email không hợp lệ! Vui lòng nhập đúng định dạng.", "/View/images/ErrorLogo.png");
            return;
        }

        if (LogReDatabase.registerUser(email, password, role)) {
            showCustomAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng ký thành công với vai trò " + role + "!", "/View/images/TickLogo.png");
        } else {
            showCustomAlert(Alert.AlertType.ERROR, "Lỗi", "Email đã được sử dụng", "/View/images/ErrorLogo.png");
        }
    }


    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        String emailRegex = "^[A-Za-z0-9._%+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private void showCustomAlert(Alert.AlertType type, String title, String message, String imagePath) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);


        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(50);
                imageView.setFitWidth(55);
                alert.setGraphic(imageView);
            }
        } catch (Exception e) {
            System.out.println("Không thể tải ảnh nội dung: " + imagePath);
        }


        try {
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            if (imagePath != null && !imagePath.isEmpty()) {
                Image windowIcon = new Image(getClass().getResourceAsStream(imagePath));
                alertStage.getIcons().add(windowIcon);
            }
        } catch (Exception e) {
            System.out.println("Không thể đặt icon cửa sổ: " + e.getMessage());
        }

        alert.showAndWait();
    }
}

