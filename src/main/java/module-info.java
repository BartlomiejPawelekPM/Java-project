module com.example.logowanie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires jbcrypt;


    opens com.example.logowanie to javafx.fxml;
    exports com.example.logowanie;
}