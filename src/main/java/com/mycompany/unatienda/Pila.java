package com.mycompany.unatienda;

/**
 - Clase Pila (estructura de datos Last-In, First-Out o LIFO).
 - Implementada usando nodos enlazados, donde el último valor ingresado es el primero en salir.
 */
public class Pila {

    private Nodo UltimoValorIngresado;
    int tamano = 0;
    String Lista = ""; 

    /**
     - Constructor de la clase Pila.
     - Inicializa la pila vacía (el último valor es null).
     */
    public Pila() {
        UltimoValorIngresado = null;
        tamano = 0;
    }

    /**
     - Verifica si la pila no contiene elementos.
     - Retorna 'true' si el último valor ingresado es null.
     */
    public boolean PilaVacia(){
        return UltimoValorIngresado == null;
    }

    /**
     - Inserta un nuevo elemento en la parte superior de la pila (Push).
     - "nodo" es el valor entero que se desea agregar.
     */
    public void IngresarNodo(int nodo){
        Nodo nuevo_nodo = new Nodo(nodo);
        nuevo_nodo.siguiente = UltimoValorIngresado;
        UltimoValorIngresado = nuevo_nodo;
        tamano++;
    }

    /**
     - Elimina el elemento que se encuentra en la parte superior de la pila (Pop).
     - Retorna el valor entero del elemento que fue eliminado. Retorna -1 si la pila está vacía.
     */
    public int EliminarNodo() {
        if (PilaVacia()) {
            System.out.println("La pila está vacía. No se puede eliminar.");
            return -1; // Devolver un valor centinela
        }
        int auxiliar = UltimoValorIngresado.informacion;
        UltimoValorIngresado = UltimoValorIngresado.siguiente;
        tamano--;
        return auxiliar;
    }

    /**
     - Obtiene el valor del elemento en la parte superior de la pila sin eliminarlo (Peek).
     - Retorna el valor entero, o -1 si la pila está vacía.
     */
    public int MostrarUltimoValor() {
        if(PilaVacia()) return -1;
        return UltimoValorIngresado.informacion;
    }

    /**
     - Obtiene el número de elementos que contiene la pila.
     - Retorna el tamaño actual de la pila.
     */
    public int MostrarTamano() {
        return tamano;
    }

    /**
     - Elimina todos los elementos de la pila dejándola vacía.
     */
    public void VaciarPila() {
        while(!PilaVacia()) {
            EliminarNodo();
        }
    }

    /**
     - Imprime en la consola el contenido de la pila, desde el último valor ingresado hasta el primero.
     */
    public void MostrarContenido() {
        Nodo recorrido = UltimoValorIngresado;
        String pilaStr = "";
        
        while (recorrido != null) {
            pilaStr += recorrido.informacion + "\n";
            recorrido = recorrido.siguiente;
        }
        
        if (pilaStr.isEmpty()) {
            System.out.println("Pila vacía.");
        } else {
            System.out.println("Contenido de la pila:\n" + pilaStr);
        }
    }
}