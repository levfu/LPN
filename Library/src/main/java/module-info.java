module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;

    opens com.example.library to javafx.fxml;
    opens com.example.library.bookmanagement to javafx.fxml;
    opens com.example.library.alert to javafx.fxml;

    exports com.example.library;
    exports com.example.library.bookmanagement;
    exports com.example.library.alert;
}