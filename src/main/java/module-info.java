module com.example.ai2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ai2 to javafx.fxml;
    exports com.example.ai2;
}