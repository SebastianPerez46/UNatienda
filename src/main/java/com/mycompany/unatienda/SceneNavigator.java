package com.mycompany.unatienda;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 - Clase SceneNavigator.
 - Gestor estático de la navegación en la aplicación JavaFX.
 - Se encarga de cargar archivos FXML y cambiar la raíz de la escena principal (Scene).
 */
public class SceneNavigator {

    private static Stage mainStage;
    private static Scene mainScene;
    private static final String FXML_BASE_PATH = "/com/mycompany/unatienda/";
    
    // Cache para almacenar vistas que deben reutilizarse 
    private static Map<String, Parent> viewCache = new HashMap<>();

    // Constantes de ruta para todos los archivos FXML
    public static final String INICIO_VIEW = "InicioView.fxml";
    public static final String ADMIN_LOGIN_VIEW = "AdminLoginView.fxml";
    public static final String ADMIN_MAIN_VIEW = "AdminMainView.fxml"; 
    public static final String CLIENT_LOGIN_VIEW = "ClientLoginView.fxml";
    public static final String CLIENT_MAIN_VIEW = "ClientMainView.fxml"; 

    /**
     - Establece la ventana principal de la aplicación.
     - Es obligatorio llamar a este método al inicio de la aplicación.
     - "stage" es la instancia principal de Stage proporcionada por JavaFX.
     */
    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    /**
     - Carga un nuevo archivo FXML y lo establece como la raíz de la escena principal.
     - Implementa una lógica de cacheo: las vistas de login/cliente se recargan siempre, las vistas administrativas se cachean.
     - "fxml" es el nombre del archivo FXML a cargar (e.g., "InicioView.fxml").
     */
    public static void loadScene(String fxml) {
        try {
            String fullPath = FXML_BASE_PATH + fxml;
            Parent root;

            // Vistas que deben recargarse siempre para asegurar el estado inicial limpio
            if (fxml.equals(INICIO_VIEW) || fxml.equals(ADMIN_LOGIN_VIEW) || fxml.equals(CLIENT_LOGIN_VIEW) ||
                fxml.equals(CLIENT_MAIN_VIEW))
            {
                System.out.println("Recargando vista (sin cache): " + fxml);
                viewCache.remove(fxml); // Limpiamos por si acaso
                root = loadFXML(fullPath);
                
                // Se cachea CLIENT_MAIN_VIEW temporalmente, pero se fuerza la recarga en la siguiente llamada a este método.
                if (fxml.equals(CLIENT_MAIN_VIEW)) {
                    viewCache.put(fxml, root); 
                }
            }
            // Vistas principales (AdminMainView) se cargan desde cache si existen
            else if (viewCache.containsKey(fxml)) {
                System.out.println("Cargando vista desde cache: " + fxml);
                root = viewCache.get(fxml);
            }
            // Si no está en cache y no es de recarga forzada, la cargamos y cacheamos
            else {
                System.out.println("Cargando vista por primera vez y cacheando: " + fxml);
                root = loadFXML(fullPath);
                viewCache.put(fxml, root);
            }

            // Inicialización de la escena la primera vez
            if (mainScene == null) {
                mainScene = new Scene(root);
                String cssPath = App.class.getResource(FXML_BASE_PATH + "style.css").toExternalForm();
                if (cssPath != null) {
                    mainScene.getStylesheets().add(cssPath);
                } else {
                    System.err.println("Error: No se encontró style.css en " + FXML_BASE_PATH);
                }
                mainStage.setScene(mainScene);
            } 
            // Cambio de la raíz de la escena si ya existe
            else {
                mainScene.setRoot(root);
                // Re-aplicar CSS
                String cssPath = App.class.getResource(FXML_BASE_PATH + "style.css").toExternalForm();
                mainScene.getStylesheets().clear(); 
                if (cssPath != null) {
                    mainScene.getStylesheets().add(cssPath);
                }
            }

            mainStage.sizeToScene(); // Ajusta tamaño
            mainStage.centerOnScreen(); // Centra la ventana

        } catch (IOException e) {
            System.err.println("Error al cargar la escena FXML: " + fxml);
            e.printStackTrace();
        } catch (NullPointerException e) {
             System.err.println("Error CRÍTICO: No se encontró el recurso FXML o CSS en la ruta: " + FXML_BASE_PATH + fxml);
             System.err.println("Verifica que el archivo exista en 'src/main/resources" + FXML_BASE_PATH + "' y que el nombre sea exacto.");
             e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar la escena: " + fxml);
            e.printStackTrace();
        }
    }

    /**
     - Método auxiliar que utiliza FXMLLoader para cargar un archivo FXML dado su ruta completa.
     - "fullPath" es la ruta completa al archivo FXML.
     - Lanza IOException si el archivo FXML no se encuentra o no se puede cargar.
     - Retorna el objeto Parent (raíz) del grafo de escena cargado.
     */
    private static Parent loadFXML(String fullPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fullPath));
        if (App.class.getResource(fullPath) == null) {
            throw new IOException("No se pudo encontrar el archivo FXML en la ruta: " + fullPath);
        }
        return loader.load();
    }

    /**
     - Cierra la ventana principal de la aplicación.
     */
    public static void exit() {
        if (mainStage != null) {
            mainStage.close();
        }
    }
}