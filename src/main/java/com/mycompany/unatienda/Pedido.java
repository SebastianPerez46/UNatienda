package com.mycompany.unatienda;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 - Clase Pedido, representa la orden de compra realizada por un cliente.
 - Implementa Comparable para permitir la ordenación por prioridad en la ColaPrioridad.
 */
public class Pedido implements Comparable<Pedido> {

    public int id;
    public Cliente cliente;
    public boolean prioridad;
    
    // Lista observable para permitir la actualización automática en la interfaz gráfica.
    private ObservableList<Producto> productos;
    
    // Propiedad observable para actualizar automáticamente el total en la interfaz gráfica.
    private final DoubleProperty total;

    /**
     - Constructor de la clase Pedido.
     - Inicializa las propiedades de prioridad y la lista observable de productos.
     - "id" es el identificador único del pedido.
     - "cliente" es el objeto Cliente que realiza el pedido.
     - "esVIP" es el estado de prioridad del cliente (true si es VIP).
     */
    public Pedido(int id, Cliente cliente, boolean esVIP) {
        this.id = id;
        this.cliente = cliente;
        this.prioridad = esVIP;
        
        this.productos = FXCollections.observableArrayList();
        this.total = new SimpleDoubleProperty(0.0);
    }

    /**
     - Agrega un producto al pedido y dispara el recálculo del total.
     - "p" es el producto que se va a añadir a la orden.
     */
    public void agregarProducto(Producto p) {
        productos.add(p);
        recalcularTotal();
    }

    /**
     - Elimina un producto del pedido y dispara el recálculo del total.
     - "p" es el producto que se va a eliminar de la orden.
     */
    public void eliminarProducto(Producto p) {
        productos.remove(p);
        recalcularTotal();
    }
    
    /**
     - Recalcula la suma total de los precios de todos los productos en el pedido.
     - El valor se asigna a la propiedad "total".
     */
    public void recalcularTotal() {
        double nuevoTotal = 0;
        for (Producto p : productos) {
            nuevoTotal += p.getPrecio();
        }
        this.total.set(nuevoTotal);
    }

    // --- Getters y Setters ---
    
    /** - Obtiene el identificador único del pedido. */
    public int getId() { return id; }
    /** - Establece el identificador único del pedido. - "id" es el nuevo valor de identificador. */
    public void setId(int id) { this.id = id; }
    /** - Obtiene el objeto Cliente asociado al pedido. */
    public Cliente getCliente() { return cliente; }
    /** - Establece el objeto Cliente asociado al pedido. - "cliente" es el nuevo objeto Cliente. */
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    /** - Verifica si el pedido tiene prioridad (si el cliente es VIP). */
    public boolean isEsVIP() { return prioridad; }
    /** - Establece si el pedido tiene prioridad. - "prioridad" es el valor booleano. */
    public void setPrioridad(boolean prioridad) { this.prioridad = prioridad; }

    // Getters para JavaFX
    
    /** - Obtiene la lista observable de productos en el pedido. */
    public ObservableList<Producto> getProductos() {
        return productos;
    }
    
    /** - Obtiene la propiedad observable del total para el data-binding de JavaFX. */
    public DoubleProperty totalProperty() {
        return total;
    }
    
    /** - Obtiene el valor actual del total del pedido. */
    public double getTotal() {
        return total.get();
    }

    /** - Establece manualmente un nuevo valor total para el pedido. 
     * - "total" es el nuevo valor total. */
    public void setTotal(double total) {
        this.total.set(total);
    }
    
    // Lógica de ColaPrioridad
    /**
     - Implementa la lógica de comparación para la cola de prioridad.
     - Prioriza los pedidos VIP (-1) sobre los pedidos Normales (1).
     - Si la prioridad es igual, ordena por ID de forma ascendente.
     - "otro" es el objeto Pedido con el que se va a comparar.
     */
    @Override
    public int compareTo(Pedido otro) {
        if (this.prioridad && !otro.prioridad) {
            return -1; // Este pedido (VIP) va primero
        } else if (!this.prioridad && otro.prioridad) {
            return 1;  // El otro pedido (VIP) va primero
        } else {
            return Integer.compare(this.id, otro.id); // Orden por ID
        }
    }

    /**
     - Genera una representación en formato de texto del pedido.
     - Incluye el ID, el tipo (VIP/Normal), el cliente y el total.
     - Retorna la representación en cadena del objeto.
     */
    @Override
    public String toString() {
        String tipo = this.prioridad ? "VIP" : "Normal";
        String nombreCliente = (this.cliente != null) ? this.cliente.getNombre() : "Desconocido"; 
        return "Pedido #" + id + " [" + tipo + "] - Cliente: " + nombreCliente + " - Total: $" + String.format("%.2f", getTotal());
    }
}