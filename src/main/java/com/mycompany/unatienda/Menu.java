package com.mycompany.unatienda;

import java.util.ArrayList; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 - Clase Menu que gestiona la lista de productos disponibles en la tienda.
 - Utiliza ObservableList para permitir la vinculación directa con componentes de interfaz gráfica.
 */
public class Menu {

    // Lista observable que almacena todos los objetos Producto del menú.
    private ObservableList<Producto> productosObservable;

    /**
     - Constructor de la clase Menu.
     - Inicializa la lista observable de productos.
     */
    public Menu() {
        // Inicializamos la lista observable
        this.productosObservable = FXCollections.observableArrayList();
    }

    /**
     - Agrega un nuevo producto a la lista del menú.
     - "p" es el producto que se va a agregar.
     */
    public void agregarProducto(Producto p) {
        productosObservable.add(p); // Añadimos a la lista observable
        System.out.println("Producto agregado al menú: " + p.getNombre() + " (ID: " + p.getId() + ")");
    }

    /**
     - Elimina un producto de la lista del menú.
     - "p" es el producto que se va a eliminar.
     */
    public void eliminarProducto(Producto p) {
        boolean removed = productosObservable.remove(p); // Eliminamos de la lista observable
        if (removed) {
            System.out.println("Producto eliminado del menú: " + p.getNombre());
        } else {
             System.out.println("Producto no encontrado para eliminar: " + p.getNombre());
        }
    }
    
    /**
     - Devuelve la lista observable de productos.
     - Es el enlace principal para la visualización de datos en la interfaz gráfica.
     - Retorna la lista observable de productos.
     */
    public ObservableList<Producto> getProductosObservable() {
        return productosObservable;
    }


    // --- Métodos de Búsqueda y Visualización ---
    
    /**
     - Busca un producto dentro del menú utilizando su identificador único.
     - "id" es el identificador del producto a buscar.
     - Retorna el objeto Producto encontrado, o 'null' si no existe.
     */
    public Producto getProducto(int id) {
        for (Producto p : productosObservable) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null; 
    }


    /**
     - Genera una representación en formato de texto tabular del menú.
     - Este método es útil para impresión en consola o depuración.
     - Retorna una cadena String con el contenido formateado.
     */
    public String mostrarMenuString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID\tPRODUCTO\t\tPRECIO\t\tSTOCK\n");
        sb.append("---------------------------------------------------\n");

        for (Producto p : productosObservable) {
            sb.append(p.getId()).append(". \t");
            sb.append(p.getNombre());

            // Alinea columnas (simplificado)
            if (p.getNombre().length() < 8) sb.append("\t\t"); else sb.append("\t");

            sb.append("$").append(String.format("%,.2f", p.getPrecio()))
              .append("\t\t").append(p.getStock()).append("\n");
        }
        return sb.toString();
    }
}