module com.example.sillystamper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sillystamper to javafx.fxml;
    exports com.example.sillystamper;
}