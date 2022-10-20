module com.example.projectjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectjavafx to javafx.fxml;
    exports com.example.projectjavafx;
}