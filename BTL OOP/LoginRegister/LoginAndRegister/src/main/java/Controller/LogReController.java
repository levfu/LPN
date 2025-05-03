package Controller;

import Database.LogReDatabase;
import Utils.EmailSender;
import Utils.VerificationCodeGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogReController implements Initializable {
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private TextField txtPasswordVisible;
    @FXML private CheckBox checkboxPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleComboBox.getItems().addAll("User", "Manager");
        roleComboBox.setEditable(false);
        checkboxPassword.setOnAction(e -> {
            if (checkboxPassword.isSelected()) {
                txtPasswordVisible.setText(txtPassword.getText());
                txtPasswordVisible.setVisible(true);
                txtPasswordVisible.setManaged(true);
                txtPassword.setVisible(false);
                txtPassword.setManaged(false);
            } else {
                txtPassword.setText(txtPasswordVisible.getText());
                txtPassword.setVisible(true);
                txtPassword.setManaged(true);
                txtPasswordVisible.setVisible(false);
                txtPasswordVisible.setManaged(false);
            }
        });

    }

    @FXML
    private void handleLogin() {
        String email = txtUsername.getText().trim();
        String password = checkboxPassword.isSelected() ? txtPasswordVisible.getText().trim() : txtPassword.getText().trim();
        String role  = roleComboBox.getValue();
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
            showCustomAlert(Alert.AlertType.INFORMATION, "Thành công",
                    "Đăng nhập thành công với vai trò " + role + "!", "/View/images/TickLogo.png");
            switchToHome(role);
        } else {
            showCustomAlert(Alert.AlertType.ERROR, "Lỗi", "Sai Email hoặc mật khẩu!", "/View/images/ErrorLogo.png");
        }
    }

    @FXML
    private void handleRegister() {
        String email = txtUsername.getText().trim();
        String password = checkboxPassword.isSelected() ? txtPasswordVisible.getText().trim() : txtPassword.getText().trim();
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
        if (LogReDatabase.emailExists(email, role)) {
            showCustomAlert(Alert.AlertType.ERROR, "Lỗi", "Email đã được sử dụng!", "/View/images/ErrorLogo.png");
            return;
        }

        String verificationCode = VerificationCodeGenerator.generateCode();

        new Thread(() -> {
            EmailSender.sendVerificationEmail(email, verificationCode);

            javafx.application.Platform.runLater(() -> {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Xác thực Email");
                dialog.setHeaderText("Nhập mã xác thực đã gửi tới email của bạn:");
                dialog.setContentText("Mã xác thực:");

                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/View/images/EmailLogo.png")));

                dialog.showAndWait().ifPresent(userInputCode -> {
                    if (userInputCode.equals(verificationCode)) {
                        if (LogReDatabase.registerUser(email, password, role)) {
                            showCustomAlert(Alert.AlertType.INFORMATION, "Thành công",
                                    "Đăng ký thành công với vai trò " + role + "!", "/View/images/TickLogo.png");
                        } else {
                            showCustomAlert(Alert.AlertType.ERROR, "Lỗi", "Đăng ký thất bại!", "/View/images/ErrorLogo.png");
                        }
                    } else {
                        showCustomAlert(Alert.AlertType.ERROR, "Lỗi", "Mã xác thực không đúng!", "/View/images/ErrorLogo.png");
                    }
                });
            });
        }).start();
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
        }
        alert.showAndWait();
    }

    private void switchToHome(String role) {
        try {
            String email    = txtUsername.getText().trim();
            String password = checkboxPassword.isSelected() ? txtPasswordVisible.getText().trim() : txtPassword.getText().trim();
            Controller.User user = LogReDatabase.getUser(email, password, role);
            if (user == null) {
                showCustomAlert(Alert.AlertType.ERROR,
                        "Lỗi",
                        "Không tìm thấy thông tin người dùng!",
                        "/View/images/ErrorLogo.png");
                return;
            }
            String fxmlPath = role.equals("Manager")
                    ? "/View/FXML/HomeManager.fxml"
                    : "/View/FXML/HomeUser.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            if (role.equals("Manager")) {
                HomeManagementController ctrl = loader.getController();
                ctrl.setUser(user);
            } else {
                HomeUserController ctrl = loader.getController();
                ctrl.setUser(user);
            }
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            Scene scene = new Scene(root, 1300, 750);
            stage.setScene(scene);
            stage.setTitle(role.equals("Manager") ? "Home Manager" : "Home User");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showCustomAlert(Alert.AlertType.ERROR,
                    "Lỗi",
                    "Không thể mở giao diện cho " + role + "!",
                    "/View/images/ErrorLogo.png");
        }
    }


}