    package com.mycompany.unatienda;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.util.Optional;
import javafx.collections.FXCollections;


public class ClientMainController {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Producto> menuTable;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String> colCategoria;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;
    @FXML private TableColumn<Producto, Void> colAnadir;

    @FXML private ListView<Producto> cartListView;
    @FXML private Label totalLabel;
    @FXML private Label pedidoInfoLabel;

    private GestorTienda gestor = GestorTienda.getInstance();
    private Pedido carrito;

    @FXML
    public void initialize() {
        carrito = gestor.getCarritoActual();
        if (carrito == null || gestor.getClienteActual() == null) {
            System.err.println("Error: No se pudo inicializar ClientMainController sin carrito o cliente.");

            return;
        }


        welcomeLabel.setText("¡Bienvenido, " + gestor.getClienteActual().getNombre() + "!");

        setupMenuTable();
        setupCartListView();
        setupBindings();


        menuTable.setItems(gestor.getMenu().getProductosObservable());
        if (carrito.getProductos() != null) {
            cartListView.setItems(carrito.getProductos());
        } else {
             System.err.println("Advertencia: La lista de productos del carrito es null.");
             cartListView.setItems(FXCollections.observableArrayList());
        }

    }


    private void setupMenuTable() {
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colCategoria.setCellValueFactory(cellData -> cellData.getValue().categoriaProperty());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        colStock.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());


        colPrecio.setCellFactory(formatPriceCell());
        colStock.setStyle("-fx-alignment: CENTER;");
        colNombre.setStyle("-fx-alignment: CENTER-LEFT;");
        colCategoria.setStyle("-fx-alignment: CENTER;");


        colAnadir.setCellFactory(createAddButtonCellFactory());
    }


    private Callback<TableColumn<Producto, Void>, TableCell<Producto, Void>> createAddButtonCellFactory() {
        return param -> new TableCell<>() {
            private final Button btn = new Button("Añadir");
            {
                btn.getStyleClass().add("button-add-in-table");
                btn.setOnAction(event -> {
                    Producto producto = getTableView().getItems().get(getIndex());
                    handleAddToCart(producto);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Producto producto = getTableView().getItems().get(getIndex());

                    btn.setDisable(producto.getStock() <= 0);
                    setGraphic(btn);
                    setAlignment(Pos.CENTER);
                }
            }
        };
    }



    private void setupCartListView() {

          cartListView.setCellFactory(lv -> new ListCell<Producto>() {
               @Override
               protected void updateItem(Producto item, boolean empty) {
                   super.updateItem(item, empty);
                   if (empty || item == null) {
                       setText(null);
                   } else {

                       setText(String.format("%s - $%,.2f", item.getNombre(), item.getPrecio()));
                   }
               }
          });
    }


    private void setupBindings() {

        totalLabel.textProperty().bind(Bindings.createStringBinding(() ->
                String.format("$%,.2f", carrito.getTotal()),
                carrito.totalProperty()
        ));


          pedidoInfoLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            Cliente c = gestor.getClienteActual();
             return String.format("Pedido #%d - Cliente: %s (%s)",
                   carrito.getId(),
                   c != null ? c.getNombre() : "N/A",
                   c != null && c.isEsVIP() ? "VIP" : "Normal");
             },

             carrito.totalProperty()

          ));
    }



    private void handleAddToCart(Producto producto) {
        if (producto != null) {
            boolean added = gestor.agregarProductoAlCarrito(producto);
            if (added) {

                menuTable.refresh();


            } else {

                 Alert alert = new Alert(Alert.AlertType.WARNING);
                 alert.setTitle("Stock Insuficiente");
                 alert.setHeaderText(null);
                 alert.setContentText("No hay suficiente stock para añadir '" + producto.getNombre() + "'.");
                 alert.getDialogPane().getStyleClass().add("alert-neon-dialog");
                 alert.showAndWait();
            }
        }
    }


     @FXML
     private void handleRemoveFromCart() {
         Producto selectedItem = cartListView.getSelectionModel().getSelectedItem();
         if (selectedItem != null) {
             gestor.quitarProductoDelCarrito(selectedItem);

             menuTable.refresh();

         } else {


         }
     }


    @FXML
    private void handleFinalizarPedido() {
        Pedido pedidoFinalizado = gestor.finalizarPedido();

        if (pedidoFinalizado != null) {

            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Pedido Realizado");
            confirmation.setHeaderText("¡Tu pedido ha sido enviado!");
            confirmation.setContentText(String.format("Pedido #%d recibido. Total: $%,.2f",
                                         pedidoFinalizado.getId(), pedidoFinalizado.getTotal()));
            confirmation.getDialogPane().getStyleClass().add("alert-neon-dialog");
            confirmation.showAndWait();



            refreshCartUI();
            menuTable.refresh();

        } else {

             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText(null);
             alert.setContentText("No puedes finalizar un pedido vacío.");
             alert.getDialogPane().getStyleClass().add("alert-neon-dialog");
             alert.showAndWait();
        }
    }


    @FXML
    private void handleCancelarPedido() {
         if (carrito != null && !carrito.getProductos().isEmpty()) {
             Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
             confirmation.setTitle("Cancelar Pedido");
             confirmation.setHeaderText("¿Estás seguro de que quieres cancelar tu pedido actual?");
             confirmation.setContentText("Los productos volverán al menú.");
             confirmation.getDialogPane().getStyleClass().add("alert-neon-dialog");

             Optional<ButtonType> result = confirmation.showAndWait();
             if (result.isPresent() && result.get() == ButtonType.OK) {
                 gestor.cancelarPedidoActual();

                 refreshCartUI();
                 menuTable.refresh();
             }
         } else {


         }
    }


    @FXML
    private void handleExitStore() {

        gestor.logoutClient();

        SceneNavigator.loadScene(SceneNavigator.INICIO_VIEW);
    }


    private void refreshCartUI() {

        carrito = gestor.getCarritoActual();
        if (carrito != null && carrito.getProductos() != null) {
            cartListView.setItems(carrito.getProductos());
        } else {
             cartListView.setItems(FXCollections.observableArrayList());
        }



    }


    private Callback<TableColumn<Producto, Double>, TableCell<Producto, Double>> formatPriceCell() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                setText(empty || price == null ? null : String.format("$%,.2f", price));
                setAlignment(Pos.CENTER_RIGHT);
            }
        };
    }
}

