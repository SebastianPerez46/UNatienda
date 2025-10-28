
package com.mycompany.unatienda;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 - Clase principal de la aplicación JavaFX.
 - Esta clase configura la ventana inicial y lanza la interfaz gráfica.
 */
public class App extends Application {

    /**
     - Método de inicio de la aplicación JavaFX, llamado después de init().
     - "stage" es el contenedor primario de la aplicación
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Configuramos el "Navegador" para que conozca la ventana principal (stage)
        SceneNavigator.setStage(stage);
        
        // Delegamos la carga de la primera vista al SceneNavigator
        SceneNavigator.loadScene("InicioView.fxml");
        
        
        stage.setTitle("UNatienda - Simulación");
        stage.show();
    }

    /**
     - Punto de entrada principal para la aplicación.
     - Inicializa el gestor de datos y lanza el entorno JavaFX.
     */
    public static void main(String[] args) {
        // Inicializamos el cerebro central de la aplicación (carga los datos)
        GestorTienda.getInstance(); 
        // Lanza la aplicación JavaFX
        launch();
    }
}