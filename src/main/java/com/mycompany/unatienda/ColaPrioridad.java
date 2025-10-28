package com.mycompany.unatienda;

import java.util.PriorityQueue; // Importa la clase PriorityQueue de Java para la cola con prioridad
import java.util.ArrayList; // Importa la clase ArrayList para manejar listas dinámicas
import java.util.List; // Importa la interfaz List

/**
 - Clase que implementa una estructura de tipo Cola con Prioridad para la gestión de pedidos.
 - Los elementos se extraen según la prioridad definida internamente en la clase Pedido.
 */
public class ColaPrioridad {

    // Se declara un objeto PriorityQueue para almacenar los pedidos.
    // La prioridad se define internamente en la clase Pedido (asumiendo que implementa Comparable o se usa un Comparator).
    private PriorityQueue<Pedido> cola;

    /**
     - Constructor de la clase ColaPrioridad.
     - Inicializa una nueva cola de prioridad vacía.
     */
    public ColaPrioridad() {
        cola = new PriorityQueue<>(); // Crea una nueva instancia de PriorityQueue vacía
    }

    /**
     - Inserta un nuevo Pedido en la cola.
     - El pedido se posiciona de forma automática según su prioridad.
     - "pedido" es el objeto Pedido que se va a agregar a la cola.
     */
    public void insertar(Pedido pedido) {
        cola.offer(pedido);
    }

    /**
     - Extrae y remueve el elemento de más alta prioridad de la cola.
     - Retorna el objeto Pedido con la máxima prioridad.
     */
    public Pedido extraer() {
        return cola.poll();
    }

    /**
     - Verifica si la cola de prioridad no contiene elementos.
     - Retorna 'true' si la cola está vacía.
     */
    public boolean estaVacia() {
        return cola.isEmpty(); // Devuelve true si la cola no contiene elementos
    }

    /**
     - Obtiene una copia de los pedidos que se encuentran actualmente en la cola.
     - Esto permite visualizar los pedidos sin alterar el orden de la cola original.
     - Retorna una interfaz List de objetos Pedido.
     */
    public List<Pedido> getPedidosEnCola() {
        // Se crea un nuevo ArrayList a partir de la cola de prioridad, manteniendo el orden de prioridad actual
        return new ArrayList<>(cola);
    }
}