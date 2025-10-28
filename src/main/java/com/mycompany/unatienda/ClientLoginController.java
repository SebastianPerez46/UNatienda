package com.mycompany.unatienda;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class ClientLoginController {


    @FXML
    private TextField usernameField;

    @FXML
    private CheckBox vipCheckBox;

    @FXML
    private Label errorMessageLabel;

    private GestorTienda gestor = GestorTienda.getInstance();

    @FXML
    public void initialize() {
        errorMessageLabel.setText("");
        errorMessageLabel.setManaged(false);
        errorMessageLabel.setVisible(false);
    }


    @FXML
    private void handleLogin() {

        String username = usernameField.getText();
        boolean isVip = vipCheckBox.isSelected();


        if (username == null || username.trim().isEmpty()) {
            showError("Por favor, ingresa tu nombre.");
            return;
        }


        gestor.loginClient(username.trim(), isVip);
        System.out.println("Login de cliente exitoso: " + username.trim() + " (VIP: " + isVip + ")");


        SceneNavigator.loadScene(SceneNavigator.CLIENT_MAIN_VIEW);
    }


    @FXML
    private void handleBack() {
        SceneNavigator.loadScene(SceneNavigator.INICIO_VIEW);
    }


    private void showError(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setManaged(true);
        errorMessageLabel.setVisible(true);

    }
}

