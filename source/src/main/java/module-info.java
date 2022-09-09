module com.example.cs398project {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.sql;
    requires org.junit.jupiter.api;
    requires javafx.web;


    opens com.example.cs398project to javafx.fxml;
    exports com.example.cs398project;
}