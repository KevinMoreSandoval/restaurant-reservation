# 📚 Documentación Técnica del Sistema de Reservas

## 1. 📋 Resumen del Proyecto

Este proyecto es un **Sistema de Gestión de Reservas** para un restaurante, desarrollado en **Java** bajo el patrón de arquitectura **MVC (Modelo-Vista-Controlador)**.

### 🎯 Objetivos Principales

- **Administrar reservas** de forma eficiente.
- **Verificar disponibilidad** de mesas en tiempo real.
- **Evitar conflictos** de horarios.
- **Persistir información** en base de datos MySQL.

---

## 2. 🏗️ Arquitectura del Sistema

El sistema sigue una arquitectura **MVC**, garantizando una clara separación de responsabilidades:

### 🧠 Modelo (Model)

Contiene la lógica de negocio y acceso a datos.

- **`entidades`**: Objetos del negocio (`Reserva`, `Mesa`) y estructuras de datos.
- **`dao`**: Acceso a datos (Data Access Object).
- **`bd`**: Conexión a base de datos.
- **`services`**: Lógica adicional (ej. `EstadoMesasManager`).

### 🖥️ Vista (View)

Interfaz de usuario (GUI) construida con **Swing**.

- Muestra información al usuario.
- Captura eventos y entradas.

### 🎮 Controlador (Controller)

Intermediario entre Vista y Modelo.

- Recibe peticiones de la vista.
- Invoca lógica del modelo.
- Actualiza la vista.

---

## 3. 🧩 Estructuras de Datos

Se utilizan estructuras optimizadas para el rendimiento:

### 🌳 3.1. Árbol Binario de Búsqueda (`ArbolReservas`)

**Propósito:** Búsqueda eficiente de reservas por fecha.

- **Complejidad:** O(log n) para búsquedas.
- **Funcionamiento:** Organiza reservas cronológicamente.

### 🔗 3.2. Lista Enlazada Simple (`ListaReservas`)

**Propósito:** Manejo secuencial y recorrido lineal.

- **Complejidad:** O(n) para recorridos.
- **Uso:** Búsquedas por DNI, listados completos.

### 🗺️ 3.3. HashMap Anidado (`EstadoMesasManager`)

**Propósito:** Verificación instantánea de disponibilidad.

- **Complejidad:** O(1) (tiempo constante).
- **Estructura:** `Fecha -> Mesa -> Horario -> Estado`.

---

## 4. 📦 Clases Principales

| Capa            | Clase               | Descripción                                         |
| --------------- | ------------------- | --------------------------------------------------- |
| **Entidad**     | `Reserva`           | Datos de la reserva (Cliente, Fecha, Mesa, Estado). |
| **Entidad**     | `Mesa`              | Información de mesas y capacidad.                   |
| **Controlador** | `ReservaController` | Orquesta lógica, sincroniza estructuras y BD.       |
| **DAO**         | `ReservaDAO`        | Consultas SQL para reservas.                        |

---

## 5. 🔄 Flujo de Datos

1.  **Inicio**: Carga de datos desde BD.
2.  **Memoria**: Poblado de `ListaReservas` y `ArbolReservas`.
3.  **Sincronización**: Actualización de `EstadoMesasManager`.
4.  **Operación**:
    - _Buscar por Fecha_ ➔ Usa **Árbol**.
    - _Buscar por DNI_ ➔ Usa **Lista**.
    - _Verificar Mesa_ ➔ Usa **HashMap**.
5.  **Persistencia**: Guardado en BD y actualización en memoria.

---

## 6. ✅ Conclusión

El sistema combina estructuras de datos avanzadas para ofrecer un rendimiento óptimo:

- **Árboles** para búsquedas temporales.
- **Listas** para almacenamiento secuencial.
- **HashMaps** para validaciones instantáneas.
