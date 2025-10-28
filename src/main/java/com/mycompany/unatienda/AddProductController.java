package com.mycompany.unatienda;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class AddProductController {

    
    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private Label statusMessageLabel; 

    private GestorTienda gestor = GestorTienda.getInstance();

 
    @FXML
    public void initialize() {
        categoryComboBox.setItems(FXCollections.observableArrayList(
                "Bebida",
                "Snack",
                "Comida",
                "Postre",
                "Otro"
        ));
        
        statusMessageLabel.setVisible(false);
        statusMessageLabel.setManaged(false); 
    }

  
    @FXML
    private void handleAddProduct() {
        // 1. Obtener y validar datos
        String nombre = nameField.getText();
        String categoria = categoryComboBox.getValue();
        double precio;
        int stock;

        if (nombre == null || nombre.trim().isEmpty() || categoria == null) {
            mostrarMensaje("Error: Nombre y Categoría son obligatorios.", true);
            return;
        }

        try {
            precio = Double.parseDouble(priceField.getText());
            stock = Integer.parseInt(stockField.getText());
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: Precio y Stock deben ser números válidos.", true);
            return;
        }
        
        if (precio <= 0 || stock < 0) {
             mostrarMensaje("Error: Precio y Stock deben ser valores positivos.", true);
            return;
        }


        Producto nuevoProducto = new Producto(
                gestor.getNextProductoId(), 
                nombre,
                precio,
                categoria,
                stock
        );
        
        gestor.agregarProductoAlMenu(nuevoProducto); 
        System.out.println("Producto agregado: " + nuevoProducto.getNombre());

        handleClearFields();
        mostrarMensaje("¡Producto agregado exitosamente!", false);
    }


    @FXML
    private void handleClearFields() {
        nameField.clear();
        priceField.clear();
        stockField.clear();
        categoryComboBox.setValue(null);
    }
    

    private void mostrarMensaje(String mensaje, boolean esError) {
        statusMessageLabel.setText(mensaje);
        statusMessageLabel.setVisible(true);
        statusMessageLabel.setManaged(true);
        
        if (esError) {
            statusMessageLabel.setStyle("-fx-text-fill: #FFA07A;");
        } else {
            statusMessageLabel.setStyle("-fx-text-fill: #00E676;");
        }

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> {
            statusMessageLabel.setVisible(false);
            statusMessageLabel.setManaged(false);
        });
        delay.play();
    }
}

