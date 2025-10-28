package com.mycompany.unatienda;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import java.util.Optional;


public class AdminMainController {

    @FXML
    private TabPane mainTabPane;


    @FXML
    public void initialize() {
        System.out.println("Panel de Administrador cargado correctamente.");
    }

    @FXML
    private void handleLogout() {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmar Cierre de Sesión");
        confirmation.setHeaderText(null);
        confirmation.setContentText("¿Estás seguro de que quieres cerrar la sesión?");


        confirmation.getDialogPane().getStyleClass().add("alert-neon-dialog");

        Optional<ButtonType> result = confirmation.showAndWait();


        if (result.isPresent() && result.get() == ButtonType.OK) {
            SceneNavigator.loadScene(SceneNavigator.INICIO_VIEW);
            System.out.println("Sesión de administrador cerrada.");
        }
    }

}

