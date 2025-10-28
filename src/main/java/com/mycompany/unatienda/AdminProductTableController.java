package com.mycompany.unatienda;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.Optional;


public class AdminProductTableController {

    @FXML
    private TableView<Producto> adminProductTable;
    @FXML
    private TableColumn<Producto, Integer> colId;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, String> colCategoria;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, Integer> colStock;
    @FXML
    private TableColumn<Producto, Void> colEliminar;

    private GestorTienda gestor = GestorTienda.getInstance();

    @FXML
    public void initialize() {
        adminProductTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setupTableColumns();
        setupEliminarButtonColumn();

        adminProductTable.setItems(gestor.getMenu().getProductosObservable());

        gestor.getMenu().getProductosObservable().addListener((javafx.collections.ListChangeListener<Producto>) c -> {
            adminProductTable.refresh();
        });
    }


    private void setupTableColumns() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colCategoria.setCellValueFactory(cellData -> cellData.getValue().categoriaProperty());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        colStock.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());


        colPrecio.setCellFactory(column -> new TableCell<Producto, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                setText(empty || price == null ? null : String.format("$%,.2f", price));
                setAlignment(Pos.CENTER_RIGHT);
            }
        });


        colId.setStyle("-fx-alignment: CENTER;");
        colStock.setStyle("-fx-alignment: CENTER;");
    }


    private void setupEliminarButtonColumn() {
        Callback<TableColumn<Producto, Void>, TableCell<Producto, Void>> cellFactory = param -> {
            final TableCell<Producto, Void> cell = new TableCell<>() {
                private final Button btn = new Button("Eliminar");
                {
                    btn.getStyleClass().add("button-delete-in-table");
                    btn.setOnAction(event -> {
                        Producto producto = getTableView().getItems().get(getIndex());
                        handleDeleteProduct(producto);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : btn);
                    setAlignment(Pos.CENTER);
                }
            };
            return cell;
        };
        colEliminar.setCellFactory(cellFactory);
    }


    private void handleDeleteProduct(Producto producto) {
        if (producto == null) return;


        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmar Eliminación");
        confirmation.setHeaderText("¿Seguro que deseas eliminar el producto?");
        confirmation.setContentText(producto.getNombre() + " (ID: " + producto.getId() + ")");
        confirmation.getDialogPane().getStyleClass().add("alert-neon-dialog");

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            gestor.eliminarProductoDelMenu(producto);
            System.out.println("Producto eliminado: " + producto.getNombre());

        }
    }
}

