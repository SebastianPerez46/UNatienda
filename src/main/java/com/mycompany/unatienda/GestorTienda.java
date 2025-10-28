package com.mycompany.unatienda;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class GestorTienda {

    private static final GestorTienda instance = new GestorTienda();
    public static GestorTienda getInstance() { return instance; }

    private Menu menu;
    private ColaPrioridad colaPedidosOriginal;
    private ObservableList<Pedido> historialPedidos;
    private Admin admin;
    private Cliente clienteActual;
    private Pedido carritoActual;

    private ObservableList<Pedido> todosPedidosPendientes;
    private FilteredList<Pedido> vipPedidosPendientes;
    private FilteredList<Pedido> normalPedidosPendientes;

    private int idProductoGeneral = 1;
    private int idPedidoGeneral = 100;

    private GestorTienda() {
        menu = new Menu();
        colaPedidosOriginal = new ColaPrioridad();
        historialPedidos = FXCollections.observableArrayList();
        admin = new Admin("Admin", 1, "admin@unatienda.com");

        todosPedidosPendientes = FXCollections.observableArrayList();
        vipPedidosPendientes = new FilteredList<>(todosPedidosPendientes, p -> p.isEsVIP());
        normalPedidosPendientes = new FilteredList<>(todosPedidosPendientes, p -> !p.isEsVIP());

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Colombiana", 2500, "Bebida", 32));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Galletas", 1000, "Snack", 44));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Vive100", 3000, "Snack", 23));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Sprite", 2500, "Bebida", 52));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Chocorramo", 2500, "Snack", 62));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Choclitos", 2000, "Snack", 16));
    }

    public boolean loginAdmin(String nombre) {
        return admin.getNombre().equalsIgnoreCase(nombre);
    }

    public void loginClient(String nombre, boolean esVIP) {
        this.clienteActual = new Cliente();
        this.clienteActual.setNombre(nombre);
        this.clienteActual.setEsVIP(esVIP);
        this.clienteActual.setNumero_Identificacion(0);
        this.clienteActual.setCorreo("");

        crearNuevoCarrito();
    }

    public void logoutClient() {
        if (carritoActual != null && !carritoActual.getProductos().isEmpty()) {
            System.out.println("Cancelando carrito pendiente al salir...");
            cancelarPedidoActual();
        }
        this.clienteActual = null;
        this.carritoActual = null;
        System.out.println("Sesión de cliente limpiada.");
    }

    public void crearNuevoCarrito() {
        if (this.clienteActual == null) {
            System.err.println("Error: No se puede crear carrito sin cliente logueado.");
            return;
        }
        int pedidoId = getNextPedidoId();
        this.carritoActual = new Pedido(pedidoId, this.clienteActual, this.clienteActual.isEsVIP());
        System.out.println("Nuevo carrito creado con ID: " + pedidoId + " para " + clienteActual.getNombre());
    }

    public boolean agregarProductoAlCarrito(Producto productoDelMenu) {
         if (carritoActual == null) {
             System.err.println("Error: No hay carrito para agregar producto.");
             return false;
         }
        if (productoDelMenu.reducirStock(1)) {
            carritoActual.agregarProducto(productoDelMenu);
            System.out.println("Añadido al carrito: " + productoDelMenu.getNombre());
            return true;
        } else {
            System.out.println("No se pudo añadir (sin stock): " + productoDelMenu.getNombre());
            return false;
        }
    }

    public void quitarProductoDelCarrito(Producto productoEnCarrito) {
        if (carritoActual == null) return;
        carritoActual.eliminarProducto(productoEnCarrito);
        productoEnCarrito.aumentarStock(1);
        System.out.println("Quitado del carrito: " + productoEnCarrito.getNombre());
    }

    public Pedido finalizarPedido() {
        if (carritoActual == null || carritoActual.getProductos().isEmpty()) {
            return null;
        }

        colaPedidosOriginal.insertar(carritoActual);
        todosPedidosPendientes.add(carritoActual);
        System.out.println("Pedido finalizado y enviado a la cola: #" + carritoActual.getId());

        Pedido pedidoFinalizado = carritoActual;
        crearNuevoCarrito();
        return pedidoFinalizado;
    }

    public void cancelarPedidoActual() {
        if (carritoActual == null) return;
        for(Producto p : carritoActual.getProductos()) {
            p.aumentarStock(1);
        }
        System.out.println("Pedido cancelado. Stock devuelto.");
        crearNuevoCarrito();
    }

    public void agregarProductoAlMenu(Producto p) {
        if (p.getId() == 0) {
             p.setId(getNextProductoId());
        }
        menu.agregarProducto(p);
    }

    public void eliminarProductoDelMenu(Producto p) {
        menu.eliminarProducto(p);
    }

    public int getNextProductoId() {
        return idProductoGeneral++;
    }

    public int getNextPedidoId() {
        return idPedidoGeneral++;
    }

    public Pedido procesarSiguientePedido() {
        Pedido pedidoAProcesar = colaPedidosOriginal.extraer();

        if (pedidoAProcesar != null) {
            todosPedidosPendientes.remove(pedidoAProcesar);
            historialPedidos.add(0, pedidoAProcesar);
            System.out.println("Pedido procesado: #" + pedidoAProcesar.getId());
            return pedidoAProcesar;
        } else {
            System.out.println("No hay pedidos en la cola para procesar.");
            return null;
        }
    }

    public Menu getMenu() { return menu; }
    public ObservableList<Pedido> getHistorialPedidos() { return historialPedidos; }
    public Cliente getClienteActual() { return clienteActual; }
    public Pedido getCarritoActual() { return carritoActual; }

    public ObservableList<Pedido> getVipPedidosPendientes() { return vipPedidosPendientes; }
    public ObservableList<Pedido> getNormalPedidosPendientes() { return normalPedidosPendientes; }

}
