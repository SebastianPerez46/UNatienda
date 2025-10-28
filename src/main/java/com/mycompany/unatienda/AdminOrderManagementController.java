package com.mycompany.unatienda;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class AdminOrderManagementController {

    @FXML
    private ListView<Pedido> vipListView;

    @FXML
    private ListView<Pedido> normalListView;

    @FXML
    private Button processOrderButton;

    @FXML
    private Label statusLabel;

    private GestorTienda gestor = GestorTienda.getInstance();

    @FXML
    public void initialize() {

        vipListView.setItems(gestor.getVipPedidosPendientes());
        normalListView.setItems(gestor.getNormalPedidosPendientes());


        vipListView.setPlaceholder(new Label("No hay pedidos VIP en espera."));
        normalListView.setPlaceholder(new Label("No hay pedidos normales en espera."));


        updateProcessButtonState();


        ListChangeListener<Pedido> listener = c -> updateProcessButtonState();
        gestor.getVipPedidosPendientes().addListener(listener);
        gestor.getNormalPedidosPendientes().addListener(listener);

        statusLabel.setText("");
    }


    @FXML
    private void handleProcessOrder() {
        Pedido procesado = gestor.procesarSiguientePedido();
        if (procesado != null) {
            statusLabel.setText("Pedido #" + procesado.getId() + " (" + (procesado.isEsVIP() ? "VIP" : "Normal") + ") procesado.");

        } else {
             statusLabel.setText("No hay pedidos pendientes para procesar.");
        }
        updateProcessButtonState();
    }


    private void updateProcessButtonState() {
        boolean hayPedidos = !gestor.getVipPedidosPendientes().isEmpty() || !gestor.getNormalPedidosPendientes().isEmpty();
        processOrderButton.setDisable(!hayPedidos);
    }
}

