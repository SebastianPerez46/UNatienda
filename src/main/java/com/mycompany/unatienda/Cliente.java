package com.mycompany.unatienda;

import java.util.ArrayList;

public class Cliente extends Persona {

    // Indica si el cliente tiene prioridad en los pedidos (VIP)
    public boolean esVIP;

    // Lista que almacena todos los pedidos realizados por el cliente
    public ArrayList<Pedido> historialPedidos;

    // Método de pago preferido del cliente (ejemplo: efectivo, tarjeta)
    public String metodoPago;

    /**
     - Constructor por defecto de la clase Cliente.
     - Inicializa un cliente con valores por defecto, no VIP y con historial vacío.
     */
    public Cliente() {
        super("", 0, "");
        this.esVIP = false;
        this.historialPedidos = new ArrayList<>();
        this.metodoPago = "";
    }

    /**
     - Permite al cliente realizar un pedido, agregándolo a su historial.
     - "p" es el objeto Pedido que el cliente está realizando.
     */
    public void realizarPedido(Pedido p) {
        historialPedidos.add(p);
        System.out.println("Pedido realizado por " + getNombre() + ". Total: $" + p.getTotal());
    }

    /**
     - Muestra en la consola el historial completo de pedidos del cliente.
     */
    public void verHistorial() {
        System.out.println("\nHistorial de pedidos de " + getNombre() + ":");
        if (historialPedidos.isEmpty()) {
            System.out.println("No tienes pedidos registrados.");
            return;
        }
        for (Pedido p : historialPedidos) {
            System.out.println("Pedido " + p.getId() + " - Total: $" + p.getTotal());
        }
    }

    /**
     - Actualiza el estado del cliente a VIP.
     - Este estado puede influir en la prioridad o beneficios en futuros pedidos.
     */
    public void convertirseEnVIP() {
        this.esVIP = true;
        System.out.println(getNombre() + " ahora es cliente VIP.");
    }

    // --------- Setters ---------

    /**
     - Establece el historial de pedidos del cliente.
     - "historialPedidos" es la lista de pedidos a asignar.
     */
    public void setHistorialPedidos(ArrayList<Pedido> historialPedidos) {
        this.historialPedidos = historialPedidos;
    }

    /**
     - Establece el método de pago preferido del cliente.
     - "metodoPago" es la cadena que describe el método de pago.
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    /**
     - Establece si el cliente es VIP o no.
     - "esVIP" es el valor booleano (true si es VIP).
     */
    public void setEsVIP(boolean esVIP) {
        this.esVIP = esVIP;
    }

    // --------- Getters ---------

    /**
     - Obtiene la lista completa del historial de pedidos del cliente.
     - Retorna la lista de objetos Pedido.
     */
    public ArrayList<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    /**
     - Obtiene el método de pago preferido del cliente.
     - Retorna el método de pago como String.
     */
    public String getMetodoPago() {
        return metodoPago;
    }

    /**
     - Verifica si el cliente tiene estatus VIP.
     - Retorna 'true' si es un cliente VIP.
     */
    public boolean isEsVIP() {
        return esVIP;
    }

    /**
     - Sobreescribe el método toString() para representar al cliente como texto.
     - Muestra el nombre y si tiene el estatus VIP.
     - Retorna la representación en cadena del objeto.
     */
    @Override
    public String toString() {
        return nombre + (esVIP ? " (VIP)" : "");
    }
}