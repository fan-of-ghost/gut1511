module com.example.gut1511 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.gut1511 to javafx.fxml;
    exports com.example.gut1511;
}