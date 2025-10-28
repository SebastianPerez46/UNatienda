package com.mycompany.unatienda;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;


public class InicioController {


    @FXML
    private Button adminButton;

    @FXML
    private Button clientButton;

    @FXML
    private Button exitButton;


    @FXML
    public void initialize() {

        animateButtonIn(adminButton, 0);
        animateButtonIn(clientButton, 150);
        animateButtonIn(exitButton, 300);
    }


    private void animateButtonIn(Button button, long delayMillis) {

        FadeTransition ft = new FadeTransition(Duration.millis(600), button);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);


        TranslateTransition tt = new TranslateTransition(Duration.millis(600), button);
        tt.setFromY(50);
        tt.setToY(0);


        ParallelTransition pt = new ParallelTransition(ft, tt);
        pt.setDelay(Duration.millis(delayMillis));
        pt.play();
    }


    @FXML
    private void handleAdminClick() {
        System.out.println("Botón Administrador presionado");

        SceneNavigator.loadScene(SceneNavigator.ADMIN_LOGIN_VIEW);
    }


    @FXML
    private void handleClientClick() {
        System.out.println("Botón Cliente presionado");
        SceneNavigator.loadScene(SceneNavigator.CLIENT_LOGIN_VIEW);
    }


    @FXML
    private void handleExitClick() {
        System.out.println("Saliendo de la aplicación...");
        SceneNavigator.exit();
    }
}

