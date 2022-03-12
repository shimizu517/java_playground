module com.example.mvc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mvc to javafx.fxml;
    exports com.example.mvc;
}