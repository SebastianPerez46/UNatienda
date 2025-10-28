module com.mycompany.unatienda {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.unatienda to javafx.fxml;
    exports com.mycompany.unatienda;
}
