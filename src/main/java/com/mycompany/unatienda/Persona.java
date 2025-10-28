package com.mycompany.unatienda;

/**
 - Clase Persona: representa una persona dentro del sistema del restaurante.
 - Es la clase base de la cual pueden heredar otras clases como Cliente o Admin.
 - Contiene información básica: nombre, número de identificación y correo electrónico.
 */
public class Persona {
    
    // Nombre de la persona
    public String nombre;
    
    // Número de identificación de la persona
    public int numero_Identificacion;
    
    // Correo electrónico de la persona
    public String correo;

    /**
     - Constructor de la clase Persona.
     - Inicializa los atributos básicos: nombre, identificación y correo.
     */
    public Persona(String nombre, int numero_Identificacion, String correo) {
        this.nombre = nombre;
        this.numero_Identificacion = numero_Identificacion;
        this.correo = correo;
    }

    // -------------------- Getters --------------------

    public String getNombre() {
        return nombre;
    }

    public int getNumero_Identificacion() {
        return numero_Identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    // -------------------- Setters --------------------

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumero_Identificacion(int numero_Identificacion) {
        this.numero_Identificacion = numero_Identificacion;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}