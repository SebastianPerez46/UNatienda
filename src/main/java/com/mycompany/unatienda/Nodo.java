package com.mycompany.unatienda;

// Clase que representa un Nodo
public class Nodo {
    // Variable para almacenar el valor o dato que contiene el nodo
    int informacion;
    // Puntero o referencia al siguiente nodo de la estructura
    Nodo siguiente;

    // Constructor que inicializa el nodo con un valor y establece el siguiente nodo a null
    public Nodo(int valor) {
        informacion = valor; // Asigna el valor pasado como parámetro a la variable 'informacion'
        siguiente = null; // Inicialmente, el nodo no apunta a ningún otro nodo
    }
}