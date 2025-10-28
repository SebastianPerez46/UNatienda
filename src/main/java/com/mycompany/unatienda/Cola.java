
package com.mycompany.unatienda;

/**
 - Clase Cola (estructura de datos First-In, First-Out o FIFO).
 - Implementada usando nodos enlazados.
 */
public class Cola {
    private Nodo inicioCola, finalCola;  
    String Cola = ""; 
    
    /**
     - Constructor de la clase Cola.
     - Inicializa el inicio y el final de la cola a 'null' (vacía).
     */
    public Cola(){
        inicioCola = null;
        finalCola = null;
    }
    
    /**
     - Verifica si la cola está vacía.
     - Retorna 'true' si el inicio de la cola es null.
     */
    public boolean ColaVacia(){
        return inicioCola == null;
    }
    
    /**
     - Inserta un nuevo elemento al final de la cola (Enqueue).
     - "informacion" es el valor entero que se desea agregar a la cola.
     */
    public void Insertar(int informacion){
        Nodo nuevo_nodo = new Nodo(informacion);
        nuevo_nodo.informacion = informacion;
        nuevo_nodo.siguiente = null;
        
        if (ColaVacia()) {
            inicioCola = nuevo_nodo;
            finalCola = nuevo_nodo;
        } else {
            finalCola.siguiente = nuevo_nodo;
            finalCola = nuevo_nodo;
        }
    }
    
    /**
     - Extrae el elemento del inicio de la cola (Dequeue).
     - Retorna el valor entero del elemento que fue extraído. Retorna -1 si la cola está vacía.
     */
    public int Extraer(){
        if (!ColaVacia()){
            int informacion = inicioCola.informacion;
            
            if (inicioCola == finalCola) {
                inicioCola = null;
                finalCola = null;
            } else {
                inicioCola = inicioCola.siguiente;
            }
            return informacion;
        } else {
            return -1; 
        }
    }
    
    /**
     - Genera una cadena de texto con el contenido de la cola en orden (desde el inicio hasta el final).
     - Retorna una String con los elementos separados por espacios.
     */
    public String obtenerContenido() {
        Nodo recorrido = inicioCola;
        String colaStr = "";
        
        while (recorrido != null) {
            colaStr += recorrido.informacion + " ";
            recorrido = recorrido.siguiente;
        }
        return colaStr.trim();
    } 

    /**
     - Muestra el contenido actual de la cola en la consola.
     */
    public void MostrarContenido(){
        String contenido = obtenerContenido();
        if(contenido.isEmpty()) {
            System.out.println("Cola vacía.");
        } else {
            System.out.println("Contenido de la cola: " + contenido);
        }
    }
}