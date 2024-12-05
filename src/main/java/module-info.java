module lms.lms {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires transitive javafx.graphics;
    requires java.sql;
    
    exports lms;
    opens lms to javafx.fxml;
    exports lms.controllers;
    opens lms.controllers to javafx.fxml;
    exports lms.models;
    opens lms.models to javafx.fxml;
}