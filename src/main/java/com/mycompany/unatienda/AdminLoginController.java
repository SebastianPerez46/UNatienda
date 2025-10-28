package com.mycompany.unatienda;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    public void initialize() {
        errorMessageLabel.setText("");
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();

        if (GestorTienda.getInstance().loginAdmin(username)) {
            System.out.println("Login de admin exitoso: " + username);
            SceneNavigator.loadScene(SceneNavigator.ADMIN_MAIN_VIEW);
        } else {
            System.out.println("Login de admin fallido: " + username);
            errorMessageLabel.setText("Nombre de administrador incorrecto.");
            errorMessageLabel.setStyle("-fx-text-fill: #FFA07A;");
        }
    }

    @FXML
    private void handleBack() {
        SceneNavigator.loadScene(SceneNavigator.INICIO_VIEW);
    }
}

