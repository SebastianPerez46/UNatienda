package com.mycompany.unatienda;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.stream.Collectors;


public class AdminHistoryController {

    @FXML
    private TableView<Pedido> historyTable;
    @FXML
    private TableColumn<Pedido, Integer> colPedidoId;
    @FXML
    private TableColumn<Pedido, String> colClienteNombre;
    @FXML
    private TableColumn<Pedido, String> colClienteTipo;
    @FXML
    private TableColumn<Pedido, Double> colTotal;
    @FXML
    private TableColumn<Pedido, String> colProductos;
    @FXML
    private Label totalVendidoLabel;

    private GestorTienda gestor = GestorTienda.getInstance();

    @FXML
    public void initialize() {
        setupTableColumns();
        historyTable.setItems(gestor.getHistorialPedidos());
        updateTotalVendido();
        gestor.getHistorialPedidos().addListener((ListChangeListener<Pedido>) c -> {
            historyTable.refresh();
            updateTotalVendido();
        });
    }

    private void setupTableColumns() {
        colPedidoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colClienteNombre.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue().getCliente();
            return new javafx.beans.property.SimpleStringProperty(cliente != null ? cliente.getNombre() : "Desconocido");
        });
        colClienteTipo.setCellValueFactory(cellData -> {
            boolean esVip = cellData.getValue().isEsVIP();
            return new javafx.beans.property.SimpleStringProperty(esVip ? "VIP" : "Normal");
        });
        colClienteTipo.setStyle("-fx-alignment: CENTER;");

        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colTotal.setCellFactory(column -> new TableCell<Pedido, Double>() {
            @Override
            protected void updateItem(Double total, boolean empty) {
                super.updateItem(total, empty);
                setText(empty || total == null ? null : String.format("$%,.2f", total));
                setAlignment(Pos.CENTER_RIGHT);
            }
        });

        colProductos.setCellValueFactory(cellData -> {
            String nombres = cellData.getValue().getProductos().stream()
                                    .map(Producto::getNombre)
                                    .collect(Collectors.joining(", "));
            return new javafx.beans.property.SimpleStringProperty(nombres);
        });
         colProductos.setStyle("-fx-alignment: CENTER-LEFT;");
    }

    private void updateTotalVendido() {
        double total = gestor.getHistorialPedidos().stream()
                               .mapToDouble(Pedido::getTotal)
                               .sum();
        totalVendidoLabel.setText(String.format("Total Vendido: $%,.2f", total));
    }
}

