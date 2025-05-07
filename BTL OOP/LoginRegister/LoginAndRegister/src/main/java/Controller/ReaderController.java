package Controller;

import Books.BookManagementController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Database.LogReDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class ReaderController {

    private Pane PaneContent;

    public void setpaneContent(Pane PaneContent) {
        this.PaneContent = PaneContent;
    }

    @FXML
    private TableColumn<User, String> Address;

    @FXML
    private TableColumn<User, String> BirthDay;

    @FXML
    private TableColumn<User, String> Email;

    @FXML
    private TableColumn<User, Integer> ID;

    @FXML
    private TableColumn<User, String> Name;

    @FXML
    private TableColumn<User, String> Phone;

    @FXML
    private TableColumn<User, String> Role;

    @FXML
    private TableColumn<User, Void> Action;


    @FXML
    private TableView<User> userTable;

    @FXML
    public void initialize() {
        userTable.setEditable(true);
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        BirthDay.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        Role.setCellValueFactory(new PropertyValueFactory<>("role"));

        Name.setCellFactory(TextFieldTableCell.forTableColumn());
        Name.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setName(event.getNewValue());
            LogReDatabase.updateUser(user);
        });

        Phone.setCellFactory(TextFieldTableCell.forTableColumn());
        Phone.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setPhone(event.getNewValue());
            LogReDatabase.updateUser(user);
        });


        Role.setCellFactory(TextFieldTableCell.forTableColumn());
        Role.setOnEditCommit(event -> {
            User user = event.getRowValue();
            user.setRole(event.getNewValue());
            LogReDatabase.updateUser(user);
        });


        Action.setCellFactory(getActionCellFactory());
        loadUsers();
    }

    private void loadUsers() {
        List<User> users = LogReDatabase.getAllUsers();
        ObservableList<User> data = FXCollections.observableArrayList(users);
        userTable.setItems(data);
    }

    private Callback<TableColumn<User, Void>, TableCell<User, Void>> getActionCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                return new TableCell<>() {

                    private final Button btnDelete = new Button("Delete");
                    private final Button btnBorrow = new Button("Borrow");
                    private final HBox pane = new HBox(5, btnDelete, btnBorrow);

                    {
                        btnDelete.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            if (LogReDatabase.deleteUserById(user.getId())) {
                                getTableView().getItems().remove(user);
                            }
                        });

                        btnBorrow.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            openBookSearch(user);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        };
    }


    private void openBookSearch(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/BookSearch.fxml"));
            Parent BookSearch = loader.load();

            BookManagementController controller = loader.getController();
            controller.setUser(user);
            PaneContent.getChildren().setAll(BookSearch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }
}