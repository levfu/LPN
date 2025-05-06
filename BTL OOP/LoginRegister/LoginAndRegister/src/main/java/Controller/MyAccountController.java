package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import Database.LogReDatabase;

import java.io.File;

public class MyAccountController {

    @FXML
    private TextField fullname;

    @FXML
    private ImageView avatar;

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
        alert.setTitle("Anouncement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        fullname.setText(user.getName());
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
        fileChooser.setTitle("Choose your avatar !");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Ảnh", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            if (selectedFile.length() > 5 * 1024 * 1024) {
                showAlert("Please choose avatar with 5MB size smaller.");
                return;
            }
            if (!selectedFile.getName().matches(".*\\.(jpg|jpeg|png)$")) {
                showAlert("Error image regex !");
                return;
            }
            avatarFile = selectedFile;
            Image image = new Image(selectedFile.toURI().toString());
            avatar.setImage(image);

        }
    }

    @FXML
    void savecharge(ActionEvent event) {
        if (fullname.getText().isEmpty()|| fullsdt.getText().isEmpty() || fullsdt.getText().isEmpty() || email.getText().isEmpty()) {
            showAlert("Please enter full detail !");
            return;
        }

        if (!email.getText().matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            showAlert("Wrong email regex !");
            return;
        }
        currentUser.setName(fullname.getText());
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
            showAlert("Update detail successfully !");
        } else {
            showAlert("Update detail failed !");
        }
    }

}
