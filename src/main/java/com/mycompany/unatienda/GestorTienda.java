package com.mycompany.unatienda;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import java.util.stream.Collectors;

/**
 - Clase central (Singleton) que gestiona toda la lógica de la tienda, incluyendo el menú, pedidos, clientes y administración.
 */
public class GestorTienda {

    // --- Singleton Pattern ---
    private static final GestorTienda instance = new GestorTienda();
    /** - Obtiene la única instancia de GestorTienda (patrón Singleton). */
    public static GestorTienda getInstance() { return instance; }

    // --- Atributos ---
    private Menu menu;
    private ColaPrioridad colaPedidosOriginal;
    private ObservableList<Pedido> historialPedidos;
    private Admin admin;
    private Cliente clienteActual; // Información del cliente logueado
    private Pedido carritoActual;  // Carrito asociado al cliente logueado

    private ObservableList<Pedido> todosPedidosPendientes; 
    private ObservableList<Pedido> vipPedidosPendientes;
    private ObservableList<Pedido> normalPedidosPendientes;

    private int idProductoGeneral = 1;
    private int idPedidoGeneral = 100;

    /**
     - Constructor privado (para el Singleton).
     - Inicializa todas las estructuras de datos y carga los datos iniciales de la tienda.
     */
    private GestorTienda() {
        menu = new Menu();
        colaPedidosOriginal = new ColaPrioridad();
        historialPedidos = FXCollections.observableArrayList();
        admin = new Admin("Admin", 1, "admin@unatienda.com");

        todosPedidosPendientes = FXCollections.observableArrayList();
        vipPedidosPendientes = FXCollections.observableArrayList();
        normalPedidosPendientes = FXCollections.observableArrayList();

        cargarDatosIniciales();
        refrescarListasPedidosPendientes();
    }

    /** - Carga un conjunto inicial de productos al menú. */
    private void cargarDatosIniciales() {
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Colombiana", 2500, "Bebida", 32));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Galletas", 1000, "Snack", 44));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Vive100", 3000, "Snack", 23));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Sprite", 2500, "Bebida", 52));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Chocorramo", 2500, "Snack", 62));
        agregarProductoAlMenu(new Producto(getNextProductoId(), "Choclitos", 2000, "Snack", 16));
    }

    // --- Lógica de Login/Logout ---
    /**
     - Intenta realizar el login del administrador.
     - "nombre" es el nombre del usuario a verificar contra el nombre del administrador.
     - Retorna 'true' si el nombre coincide.
     */
    public boolean loginAdmin(String nombre) {
        return admin.getNombre().equalsIgnoreCase(nombre);
    }

    /**
     - Inicia la sesión de un nuevo cliente.
     - Crea un nuevo objeto Cliente y un nuevo carrito de compras asociado.
     - "nombre" es el nombre del cliente que inicia sesión.
     - "esVIP" es un booleano que indica si el cliente tiene estatus VIP.
     */
    public void loginClient(String nombre, boolean esVIP) {
        // Crea un NUEVO cliente cada vez que alguien inicia sesión
        this.clienteActual = new Cliente();
        this.clienteActual.setNombre(nombre);
        this.clienteActual.setEsVIP(esVIP);
        // Resetea otros datos si es necesario (ej. ID, correo)
        this.clienteActual.setNumero_Identificacion(0); // O generar uno único
        this.clienteActual.setCorreo("");

        // Crea un NUEVO carrito vacío asociado a este nuevo cliente
        crearNuevoCarritoInternal(); // Usamos un método interno para claridad
        System.out.println("Login cliente: " + nombre + " (VIP: " + esVIP + ")");
    }

    /**
     - Cierra la sesión del cliente actual.
     - Si existe un carrito activo, lo cancela y restaura el inventario.
     */
    public void logoutClient() {
        // Si hay un carrito en progreso, se cancela para devolver el stock
        if (this.carritoActual != null && !this.carritoActual.getProductos().isEmpty()) {
            System.out.println("Cancelando carrito pendiente antes de logout...");
            cancelarPedidoInternal(); // Usamos método interno
        }
        // Limpia las referencias al cliente y su carrito
        this.clienteActual = null;
        this.carritoActual = null;
        System.out.println("Sesión de cliente cerrada.");
    }


    /** - Crea una nueva instancia de Pedido (carrito) para el cliente actual. */
    private void crearNuevoCarritoInternal() {
        if (this.clienteActual == null) {
             System.err.println("Error: No se puede crear carrito sin cliente logueado.");
             return;
        }
        int pedidoId = getNextPedidoId(); // Obtiene ID público
        this.carritoActual = new Pedido(pedidoId, this.clienteActual, this.clienteActual.isEsVIP());
        System.out.println("Nuevo carrito creado con ID: " + pedidoId + " para " + clienteActual.getNombre());
    }

    /**
     - Agrega un producto del menú al carrito actual.
     - Reduce el stock del producto en el menú.
     - "productoDelMenu" es el objeto Producto que se desea agregar.
     - Retorna 'true' si la operación fue exitosa (hay stock).
     */
    public boolean agregarProductoAlCarrito(Producto productoDelMenu) {
        if (carritoActual == null) {
            System.err.println("Error: No hay carrito activo para agregar producto.");
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

    /**
     - Quita un producto del carrito actual y restaura su stock.
     - "productoEnCarrito" es el objeto Producto a eliminar del carrito.
     */
    public void quitarProductoDelCarrito(Producto productoEnCarrito) {
        if (carritoActual == null || productoEnCarrito == null) return;
        carritoActual.eliminarProducto(productoEnCarrito);
        productoEnCarrito.aumentarStock(1);
        System.out.println("Quitado del carrito: " + productoEnCarrito.getNombre());
    }

    /**
     - Finaliza el carrito actual, lo añade a la cola de pedidos y lo marca como completado.
     - Retorna el objeto Pedido finalizado, o 'null' si el carrito estaba vacío.
     */
    public Pedido finalizarPedido() {
        if (carritoActual == null || carritoActual.getProductos().isEmpty()) {
            System.out.println("No se puede finalizar un pedido vacío.");
            return null;
        }

        colaPedidosOriginal.insertar(carritoActual);
        refrescarListasPedidosPendientes();
        System.out.println("Pedido finalizado y enviado a la cola: #" + carritoActual.getId());

        Pedido pedidoFinalizado = carritoActual;
        // Importante: No creamos un carrito nuevo aquí, se hará en el próximo login
        this.carritoActual = null; // Marcamos el carrito como finalizado
        return pedidoFinalizado;
    }

    /** - Cancela el pedido (carrito) actual y devuelve el stock de todos sus productos al inventario. */
    private void cancelarPedidoInternal() {
        if (carritoActual == null) return;
        for(Producto p : carritoActual.getProductos()) {
            p.aumentarStock(1); // Devuelve stock
        }
        System.out.println("Carrito cancelado (ID: " + carritoActual.getId() +"). Stock devuelto.");
        // No creamos carrito nuevo aquí, se hará en el próximo login
        this.carritoActual = null; // Marcamos el carrito como cancelado
    }

    /** - Método público que permite al controlador cancelar el pedido (carrito) en curso. */
    public void cancelarPedidoActual() {
        cancelarPedidoInternal();
    }


    // --- Lógica de Productos (Admin) ---
    /**
     - Agrega un nuevo producto a la lista del menú.
     - "p" es el objeto Producto que se va a añadir.
     */
    public void agregarProductoAlMenu(Producto p) {
        menu.agregarProducto(p);
    }

    /**
     - Elimina un producto de la lista del menú.
     - "p" es el objeto Producto que se va a eliminar.
     */
    public void eliminarProductoDelMenu(Producto p) {
        menu.eliminarProducto(p);
    }

    /**
     - Obtiene el siguiente ID único para un nuevo producto y lo incrementa.
     - Retorna el ID disponible.
     */
    public int getNextProductoId() { // Público
        return idProductoGeneral++;
    }


    // --- Lógica de Pedidos (Admin) ---
    /**
     - Procesa el siguiente pedido disponible en la cola (el de mayor prioridad).
     - Mueve el pedido procesado de la cola de pendientes al historial.
     - Retorna el Pedido procesado, o 'null' si la cola está vacía.
     */
    public Pedido procesarSiguientePedido() {
        if (!colaPedidosOriginal.estaVacia()) {
            Pedido pedidoProcesado = colaPedidosOriginal.extraer();
            historialPedidos.add(pedidoProcesado); // Añade al historial
            refrescarListasPedidosPendientes();
            System.out.println("Pedido procesado: #" + pedidoProcesado.getId());
            return pedidoProcesado;
        } else {
            System.out.println("No hay pedidos en la cola para procesar.");
            refrescarListasPedidosPendientes();
            return null;
        }
    }

    /** - Actualiza las listas observables de pedidos pendientes (VIP y Normal) con los pedidos de la cola principal. */
    private void refrescarListasPedidosPendientes() {
        ObservableList<Pedido> copiaOrdenada = FXCollections.observableArrayList(colaPedidosOriginal.getPedidosEnCola());
        vipPedidosPendientes.setAll(copiaOrdenada.stream()
                .filter(Pedido::isEsVIP)
                .collect(Collectors.toList()));
        normalPedidosPendientes.setAll(copiaOrdenada.stream()
                .filter(p -> !p.isEsVIP())
                .collect(Collectors.toList()));
    }

    // --- Getters ---
    /** - Obtiene el objeto Menu de la tienda. */
    public Menu getMenu() { return menu; }
    /** - Obtiene la lista observable del historial de pedidos completados. */
    public ObservableList<Pedido> getHistorialPedidos() { return historialPedidos; }
    /** - Obtiene el objeto Cliente de la sesión actual. */
    public Cliente getClienteActual() { return clienteActual; }
    /** - Obtiene el objeto Pedido que actúa como carrito de compras actual. */
    public Pedido getCarritoActual() { return carritoActual; }
    /** - Obtiene la lista observable de pedidos pendientes con estatus VIP. */
    public ObservableList<Pedido> getVipPedidosPendientes() { return vipPedidosPendientes; }
    /** - Obtiene la lista observable de pedidos pendientes con estatus Normal. */
    public ObservableList<Pedido> getNormalPedidosPendientes() { return normalPedidosPendientes; }

    // --- Otros Métodos ---
    /**
     - Obtiene el siguiente ID único para un nuevo pedido y lo incrementa.
     - Retorna el ID disponible.
     */
    public int getNextPedidoId() { // Público
        return idPedidoGeneral++;
    }
}