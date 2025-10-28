package com.mycompany.unatienda;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 - Clase Producto que representa un artículo en venta en el menú.
 - Todos los atributos son implementados como JavaFX Properties para permitir la actualización automática en la interfaz gráfica.
 */
public class Producto {

    // Atributos convertidos a Propiedades JavaFX
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final DoubleProperty precio;
    private final StringProperty categoria;
    private final IntegerProperty stock;

    /**
     - Constructor de la clase Producto.
     - Inicializa todas las propiedades del producto con los valores recibidos.
     - "id" es el identificador único del producto.
     - "nombre" es el nombre descriptivo del producto.
     - "precio" es el costo unitario del producto.
     - "categoria" es la clasificación del producto (e.g., Bebida, Snack).
     - "stock" es la cantidad inicial disponible del producto.
     */
    public Producto(int id, String nombre, double precio, String categoria, int stock) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleDoubleProperty(precio);
        this.categoria = new SimpleStringProperty(categoria);
        this.stock = new SimpleIntegerProperty(stock);
    }

    // --- Getters de Propiedades (para JavaFX) ---
    
    /** - Obtiene la propiedad observable del ID. */
    public IntegerProperty idProperty() {
        return id;
    }

    /** - Obtiene la propiedad observable del nombre. */
    public StringProperty nombreProperty() {
        return nombre;
    }

    /** - Obtiene la propiedad observable del precio. */
    public DoubleProperty precioProperty() {
        return precio;
    }
    
    /** - Obtiene la propiedad observable de la categoría. */
    public StringProperty categoriaProperty() {
        return categoria;
    }

    /** - Obtiene la propiedad observable del stock. */
    public IntegerProperty stockProperty() {
        return stock;
    }
    
    // --- Getters y Setters Estándar ---

    /** - Obtiene el valor entero del ID. */
    public int getId() {
        return id.get();
    }

    /** - Establece el valor entero del ID. - "id" es el nuevo identificador. */
    public void setId(int id) {
        this.id.set(id);
    }

    /** - Obtiene el valor de cadena del nombre. */
    public String getNombre() {
        return nombre.get();
    }

    /** - Establece el valor de cadena del nombre. - "nombre" es el nuevo nombre. */
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    /** - Obtiene el valor doble del precio. */
    public double getPrecio() {
        return precio.get();
    }

    /** - Establece el valor doble del precio. - "precio" es el nuevo precio. */
    public void setPrecio(double precio) {
        this.precio.set(precio);
    }
    
    /** - Obtiene el valor de cadena de la categoría. */
    public String getCategoria() {
        return categoria.get();
    }

    /** - Establece el valor de cadena de la categoría. - "categoria" es la nueva categoría. */
    public void setCategoria(String categoria) {
        this.categoria.set(categoria);
    }

    /** - Obtiene el valor entero del stock actual. */
    public int getStock() {
        return stock.get();
    }

    /** - Establece el valor entero del stock. - "stock" es la nueva cantidad de stock. */
    public void setStock(int stock) {
        this.stock.set(stock);
    }
    
    /**
     - Reduce la cantidad de stock del producto.
     - "cantidad" es la cantidad a descontar.
     - Retorna 'true' si el stock pudo ser reducido; 'false' si el stock es insuficiente.
     */
    public boolean reducirStock(int cantidad) {
        if (this.stock.get() >= cantidad) {
            this.stock.set(this.stock.get() - cantidad);
            return true;
        } else {
            System.out.println("⚠ Stock insuficiente para el producto: " + getNombre());
            return false;
        }
    }
    
    /**
     - Aumenta la cantidad de stock del producto.
     - Se utiliza típicamente al cancelar un pedido o eliminar un artículo del carrito.
     - "cantidad" es la cantidad a aumentar.
     */
    public void aumentarStock(int cantidad) {
        this.stock.set(this.stock.get() + cantidad);
    }
    
    /**
     - Genera una representación en formato de texto del producto.
     - Retorna la representación en cadena del objeto.
     */
    @Override
    public String toString() {
        return getNombre() + " (" + getCategoria() + ") - $" + getPrecio() + " | Stock: " + getStock();
    }
}