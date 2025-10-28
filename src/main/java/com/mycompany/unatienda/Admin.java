
package com.mycompany.unatienda;

import java.util.ArrayList;

public class Admin extends Persona {
    
    // Atributo que indica si el usuario es administrador (por defecto, true)
    public boolean esAdmin = true;

    /**
     - Constructor de la clase Admin.
     - "nombre" es el nombre del administrador
     - "numero_Identificacion" es el número de identificación único
     - "correo" es el correo electrónico del administrador
     */
    public Admin(String nombre, int numero_Identificacion, String correo) {
        super(nombre, numero_Identificacion, correo);
    }
    
    /** - Obtiene el estado de administrador.
     - Retorna 'true' si el usuario es administrador.
     */
    public boolean getEsAdmin() {
        return esAdmin;
    }

    /**
     - Registra una acción administrativa en la consola.
     - "accion" es la descripción de la acción realizada
     */
    public void registrarAccion(String accion) {
        System.out.println("[ADMIN] " + getNombre() + ": " + accion);
    }

    /**
     - Agrega un nuevo producto al menú.
     - "menu" es el objeto del menú al cual se añade el producto
     - "p" es el producto que se va a agregar
     */
    public void agregarProducto(Menu menu, Producto p) {
        menu.agregarProducto(p);  
        registrarAccion("Agregó un producto al menú: " + p.getNombre());
    }

    /**
     - Elimina un producto del menú buscándolo por su nombre.
     - "menu" es el objeto Menú de productos actual
     - "nombre" es el nombre del producto a eliminar
     */
    public void eliminarProducto(Menu menu, String nombre) {
        Producto productoAEliminar = null;
        // Buscamos el producto por nombre en la lista observable del menú
        for (Producto prod : menu.getProductosObservable()) {
            if (prod.getNombre().equalsIgnoreCase(nombre)) {
                productoAEliminar = prod;
                break;
            }
        }
        
        if (productoAEliminar != null) {
            menu.eliminarProducto(productoAEliminar); // Pasamos el objeto Producto
            registrarAccion("Eliminó un producto del menú: " + nombre);
        } else {
             System.out.println("Intento de eliminar fallido: Producto no encontrado - " + nombre);
             registrarAccion("Intentó eliminar un producto no existente: " + nombre);
        }
    }

    /**
     - Modifica el total de un pedido, útil para aplicar descuentos o correcciones.
     - "pedido" es el objeto Pedido que se va a modificar
     - "nuevoTotal" es el nuevo valor total que tendrá el pedido
     */
    public void modificarPedido(Pedido pedido, double nuevoTotal) {
        pedido.setTotal(nuevoTotal); // Asume que Pedido tiene setTotal
        System.out.println("Pedido modificado. Nuevo total: $" + nuevoTotal);
        registrarAccion("Modificó un pedido existente #" + pedido.getId());
    }

    /**
     - Genera un reporte de ventas sumando los totales de una lista de pedidos.
     - "pedidos" es la lista de todos los pedidos a incluir en el reporte
     */
    public void generarReporte(ArrayList<Pedido> pedidos) {
        System.out.println("Reporte de ventas:");
        double total = 0;

        for (Pedido ped : pedidos) {
            System.out.println("Pedido " + ped.getId() + " - Total: $" + ped.getTotal());
            total += ped.getTotal();
        }

        System.out.println("Total vendido: $" + total);
        registrarAccion("Generó un reporte de ventas");
    }
}
