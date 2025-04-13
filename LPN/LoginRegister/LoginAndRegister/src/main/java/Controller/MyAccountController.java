package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Controller.User;
import Database.LogReDatabase;

import java.io.File;
import java.io.IOException;


public class MyAccountController {

    @FXML
    private TextField Fullname;

    @FXML
    private ImageView avatar;

    @FXML
    private Button backhome;

    @FXML
    private Button chooseimage;

    @FXML
    private TextField fulladdress;

    @FXML
    private DatePicker fullbirthday;

    @FXML
    private TextField fullsdt;

    @FXML
    private Button savecharge;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void backHome(ActionEvent event) {
        try {
            Stage stage = (Stage) backhome.getScene().getWindow();
            Parent root;

            if ("Manager".equalsIgnoreCase(currentUser.getRole())) {
                root = FXMLLoader.load(getClass().getResource("/View/HomeManager.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/View/HomeUser.fxml"));
            }

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Không thể quay lại trang chủ.");
        }
    }

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        Fullname.setText(user.getName());
        fullsdt.setText(user.getPhone());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        fulladdress.setText(user.getAddress());

        if (user.getBirthday() != null) {
            fullbirthday.setValue(user.getBirthday());
        }

        if (user.getAvatarPath() != null) {
            Image image = new Image(new File(user.getAvatarPath()).toURI().toString());
            avatar.setImage(image);
        }
    }

    private File avatarFile;

    @FXML
    void chooseavt(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh đại diện");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Ảnh", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            avatarFile = selectedFile;
            Image image = new Image(selectedFile.toURI().toString());
            avatar.setImage(image);
        }
    }

    @FXML
    void savecharge(ActionEvent event) {
        if (Fullname.getText().isEmpty() || fullsdt.getText().isEmpty() || email.getText().isEmpty()) {
            showAlert("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!email.getText().matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            showAlert("Email không hợp lệ!");
            return;
        }

        // Cập nhật lại thông tin từ form
        currentUser.setName(Fullname.getText());
        currentUser.setPhone(fullsdt.getText());
        currentUser.setEmail(email.getText());
        currentUser.setPassword(password.getText());
        currentUser.setAddress(fulladdress.getText());

        if (fullbirthday.getValue() != null) {
            currentUser.setBirthday(fullbirthday.getValue());  // kiểu LocalDate
        }

        if (avatarFile != null) {
            currentUser.setAvatarPath(avatarFile.getAbsolutePath());
        }

        boolean updated = LogReDatabase.updateUser(currentUser);
        if (updated) {
            showAlert("Cập nhật thông tin thành công!");
        } else {
            showAlert("Cập nhật thất bại.");
        }
    }

}
