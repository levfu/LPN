package Controller;
import Database.LogReDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
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
            showAlert(Alert.AlertType.WARNING, "Vui lòng chọn vai trò!");
            return;
        }


        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập đủ thông tin!");
            return;
        }


        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Email không hợp lệ! Vui lòng nhập đúng định dạng.");
            return;
        }

        System.out.println("Role selected: " + role);

        if (LogReDatabase.checkLogin(email, password, role)) {
            showAlert(Alert.AlertType.INFORMATION, "Đăng nhập thành công với vai trò: " + role + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Sai Email hoặc mật khẩu!");
        }
    }

    @FXML
    private void handleRegister() {
        String email = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String role = roleComboBox.getValue();


        if (role == null) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng chọn vai trò!");
            return;
        }


        System.out.println("Email: '" + email + "', Password: '" + password + "', Role: '" + role + "'");

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập đủ thông tin!");
            return;
        }


        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Email không hợp lệ! Vui lòng nhập đúng định dạng.");
            return;
        }

        if (LogReDatabase.registerUser(email, password, role)) {
            showAlert(Alert.AlertType.INFORMATION, "Đăng ký thành công với vai trò: " + role + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Email đã được sử dụng");
        }
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }





}
