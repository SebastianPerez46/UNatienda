# UNatienda 🛒 - Simulación de Tienda con JavaFX

## 🌟 Descripción General

**UNatienda** es una aplicación de escritorio desarrollada en **Java** que simula las operaciones básicas de una tienda. Permite la gestión completa de productos y pedidos por parte de un administrador, y la realización de compras por parte de los clientes.

La interfaz gráfica utiliza **JavaFX**, implementando un estilo moderno con **efectos de neón** sobre fondos oscuros. El proyecto fue desarrollado como una práctica avanzada, utilizando **Maven** para la gestión de dependencias y **NetBeans** como IDE principal.

---

## ✨ Características Principales

### 💻 Interfaz y Roles
* **Estilo Moderno:** Interfaz de usuario con efectos de neón y fondos oscuros.
* **Roles Separados:** Funcionalidades para Administradores y Clientes.

### ⚙️ Flujo de Administrador
* **Gestión de Productos:** Añadir, ver y eliminar productos (con actualización de stock en tiempo real).
* **Gestión de Pedidos:** Cola de prioridad que separa pedidos **VIP** y **Normal**.
* **Historial de Ventas:** Registro de todos los pedidos procesados.

### 🛍️ Flujo de Cliente
* **Inicio de Sesión:** Acceso simple con opción de estatus **VIP** (para prioridad en la cola).
* **Carrito de Compras:** Añadir/quitar productos, resumen de pedido y total.
* **Control de Stock:** El inventario se reduce al añadir y se devuelve al cancelar.
* **Finalizar/Cancelar Pedido:** Envío a la cola de procesamiento o vaciado del carrito.

---

## 📋 Requisitos del Sistema y JavaFX

Debido a que JavaFX es un módulo externo en las versiones recientes de Java, es necesario contar con un JDK moderno y, opcionalmente, el SDK de JavaFX para el desarrollo en IDEs.

| Requisito | Versión Mínima | 
______________________________
| JDK (Java Development Kit)** | 21 | 
| Apache Maven | 3.6 | 
| JavaFX SDK | 21 |
| IDE | Apache NetBeans 12+ |

### 🚨 Instalación de JavaFX (Si no usa Maven)

Si el proyecto no fuera a ser ejecutado a través de Maven (lo cual simplifica el proceso), se requeriría descargar el **SDK de JavaFX** desde **GluonHQ** y configurar manualmente las rutas de los módulos en la máquina virtual de Java (VM Options) durante la ejecución.

**¡Buena noticia!** Dado que el proyecto usa **Maven**, el proceso se simplifica drásticamente: Maven se encarga de descargar las librerías necesarias de JavaFX (especificadas en `pom.xml`) al construir el proyecto.

---

## ⚙ Configuración e Instalación

### 1. Obtener el Código

Dezcarga el codigo fuente e importalo a netbeans como zip, o tambien se puede descomprimir desde antes e importar la carpeta descomprimida.

### 2. Abrir y Configurar Dependencias (NetBeans / Maven)

1.  Abre **NetBeans IDE (El proyecto se desarrollo en la vercion 25 asi que seria lo mas optimo)**.
2.  **Descarga Automática:** NetBeans/Maven detectará el `pom.xml` y debe comenzar a descargar las librerías necesarias de JavaFX automáticamente.
4.  Si la descarga no inicia, haz clic derecho en el proyecto (en la ventana Projects) y selecciona **`Build with Dependencies`** para forzar la descarga de los módulos de JavaFX.

---

## ▶ Cómo Ejecutar la Aplicación

### 1. Desde NetBeans IDE (Recomendado)
1.  Ve a Source Packages
2.  Selecciona el pauete "com.mycompany.unatienda".
3.  Da clic derecho en la clase App.java y ve a run Maven.
4.  Dentro de run Maven selecciona other goals y en el menu que se abre escribe "javafx:run" en goals.
5.  Dale ok y el programa se ejecutara.


Guía de Uso Rápida
Ejecuta la Aplicación y elige entre Administrador o Cliente.

Flujo de Administrador
Acceso: Ingresa el nombre admin.

Gestión de Pedidos: Observa la Cola de Prioridad y usa "Procesar" para atender las órdenes (VIP primero).

Flujo de Cliente
Acceso: Ingresa tu nombre y marca VIP si corresponde.

Menú: Usa el botón "Añadir" en la tabla para agregar productos al Carrito.

Finalizar Pedido: Envía tu orden a la cola de procesamiento.

Estructura del proyecto:

UNatienda/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mycompany/unatienda/  # Código fuente Java (.java)
│   │   │       ├── App.java              # Punto de entrada principal (lanza JavaFX)
│   │   │       ├── GestorTienda.java     # Lógica central (Singleton)
│   │   │       └── controllers/          # Controladores de las vistas
│   │   ├── resources/
│   │   │   └── com/mycompany/unatienda/  # Recursos (FXML, CSS, etc.)
│   │   │       ├── *.fxml                # Archivos de diseño de interfaz (JavaFX)
│   │   │       └── style.css             # Hoja de estilos principal
├── pom.xml                               # Archivo de configuración de Maven (¡Define JavaFX!)
└── README.md                             # Este archivo
