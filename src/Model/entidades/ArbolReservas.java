package Model.entidades;

import Model.bd.ConnectionBD;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Árbol binario de búsqueda para organizar reservas por fecha
 * Permite búsquedas eficientes O(log n) en lugar de O(n)
 */
public class ArbolReservas {

    private NodoArbolReserva raiz;

    public ArbolReservas() {
        this.raiz = null;
    }

    /**
     * Inserta una reserva en el árbol organizándola por fecha
     */
    public void insertar(Reserva reserva) {
        raiz = insertarRecursivo(raiz, reserva);
    }

    private NodoArbolReserva insertarRecursivo(NodoArbolReserva nodo, Reserva reserva) {
        // Si el nodo es null, crear un nuevo nodo con esta fecha
        if (nodo == null) {
            NodoArbolReserva nuevoNodo = new NodoArbolReserva(reserva.getFecha());
            nuevoNodo.agregarReserva(reserva);
            return nuevoNodo;
        }

        // Comparar fechas
        int comparacion = reserva.getFecha().compareTo(nodo.getFecha());

        if (comparacion < 0) {
            // La fecha es menor, ir a la izquierda
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, reserva);
        } else if (comparacion > 0) {
            // La fecha es mayor, ir a la derecha
            nodo.derecho = insertarRecursivo(nodo.derecho, reserva);
        } else {
            // La fecha es igual, agregar a la lista de este nodo
            nodo.agregarReserva(reserva);
        }

        return nodo;
    }

    /**
     * Busca todas las reservas de una fecha específica
     * Complejidad: O(log n)
     */
    public List<Reserva> buscarPorFecha(String fecha) {
        NodoArbolReserva nodo = buscarNodoRecursivo(raiz, fecha);

        if (nodo != null) {
            return nodo.getReservas();
        }

        return new ArrayList<>(); // Lista vacía si no se encuentra
    }

    private NodoArbolReserva buscarNodoRecursivo(NodoArbolReserva nodo, String fecha) {
        // Caso base: nodo vacío o fecha encontrada
        if (nodo == null || nodo.getFecha().equals(fecha)) {
            return nodo;
        }

        // Comparar fechas
        int comparacion = fecha.compareTo(nodo.getFecha());

        if (comparacion < 0) {
            // Buscar en el subárbol izquierdo
            return buscarNodoRecursivo(nodo.izquierdo, fecha);
        } else {
            // Buscar en el subárbol derecho
            return buscarNodoRecursivo(nodo.derecho, fecha);
        }
    }

    /**
     * Obtiene todas las reservas del día de hoy
     */
    public List<Reserva> obtenerReservasDeHoy() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaHoy = hoy.format(formatter);

        return buscarPorFecha(fechaHoy);
    }

    /**
     * Verifica si existe una reserva activa para una mesa específica en una fecha y
     * hora
     * 
     * @param fecha   Fecha de la reserva (formato yyyy-MM-dd)
     * @param idMesa  ID de la mesa
     * @param horario Hora de la reserva
     * @return true si existe una reserva activa, false en caso contrario
     */
    public boolean existeReserva(String fecha, int idMesa, String horario) {
        // Buscar todas las reservas de esa fecha usando el árbol
        List<Reserva> reservasDeFecha = buscarPorFecha(fecha);

        // Filtrar por mesa y horario específicos
        for (Reserva r : reservasDeFecha) {
            if (r.getIdMesa() == idMesa &&
                    r.getHora().equals(horario) &&
                    !r.getEstado().equals("CANCELADA")) {
                return true; // Ya existe una reserva activa
            }
        }

        return false; // No hay reserva, la mesa está disponible
    }

    /**
     * Obtiene todas las reservas del árbol en orden (in-order traversal)
     */
    public List<Reserva> obtenerTodasEnOrden() {
        List<Reserva> lista = new ArrayList<>();
        inOrderRecursivo(raiz, lista);
        return lista;
    }

    private void inOrderRecursivo(NodoArbolReserva nodo, List<Reserva> lista) {
        if (nodo != null) {
            inOrderRecursivo(nodo.izquierdo, lista);
            lista.addAll(nodo.getReservas());
            inOrderRecursivo(nodo.derecho, lista);
        }
    }

    /**
     * Carga todas las reservas desde la base de datos al árbol
     */
    public void cargarDesdeBaseDatos() {
        String sql = "SELECT * FROM reservas ORDER BY fecha";

        try (Connection cx = ConnectionBD.conectar();
                Statement stmt = cx.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            int contador = 0;
            while (rs.next()) {
                Reserva reserva = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getString("nombre_cliente"),
                        rs.getString("apellido_cliente"),
                        rs.getString("dni_cliente"),
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getInt("id_mesa"),
                        rs.getString("estado"));

                insertar(reserva);
                contador++;
            }

            System.out.println("🌳 Árbol de reservas cargado: " + contador + " reservas");

        } catch (SQLException e) {
            System.out.println("❌ Error al cargar reservas al árbol: " + e.getMessage());
        }
    }

    /**
     * Cuenta el total de reservas en el árbol
     */
    public int contarReservas() {
        return contarRecursivo(raiz);
    }

    private int contarRecursivo(NodoArbolReserva nodo) {
        if (nodo == null) {
            return 0;
        }

        return nodo.getCantidadReservas()
                + contarRecursivo(nodo.izquierdo)
                + contarRecursivo(nodo.derecho);
    }

    /**
     * Verifica si el árbol está vacío
     */
    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Muestra información del árbol (para debugging)
     */
    public void mostrarEstructura() {
        System.out.println("=== ESTRUCTURA DEL ÁRBOL DE RESERVAS ===");
        if (estaVacio()) {
            System.out.println("El árbol está vacío");
        } else {
            mostrarRecursivo(raiz, "", true);
            System.out.println("Total de reservas: " + contarReservas());
        }
    }

    private void mostrarRecursivo(NodoArbolReserva nodo, String prefijo, boolean esUltimo) {
        if (nodo != null) {
            System.out.println(prefijo + (esUltimo ? "└── " : "├── ")
                    + nodo.getFecha() + " (" + nodo.getCantidadReservas() + " reservas)");

            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");

            if (nodo.derecho != null) {
                mostrarRecursivo(nodo.derecho, nuevoPrefijo, nodo.izquierdo == null);
            }
            if (nodo.izquierdo != null) {
                mostrarRecursivo(nodo.izquierdo, nuevoPrefijo, true);
            }
        }
    }

    /**
     * Actualizar el estado de una reserva en el árbol
     */
    public void actualizarEstado(int idReserva, String nuevoEstado) {
        actualizarEstadoRecursivo(raiz, idReserva, nuevoEstado);
    }

    private boolean actualizarEstadoRecursivo(NodoArbolReserva nodo, int idReserva, String nuevoEstado) {
        if (nodo == null) {
            return false;
        }

        // Buscar en las reservas de este nodo
        for (Reserva r : nodo.getReservas()) {
            if (r.getId() == idReserva) {
                r.setEstado(nuevoEstado);
                System.out.println("✅ Estado actualizado en árbol para reserva ID: " + idReserva);
                return true;
            }
        }

        // Buscar en subárboles
        if (actualizarEstadoRecursivo(nodo.izquierdo, idReserva, nuevoEstado)) {
            return true;
        }
        return actualizarEstadoRecursivo(nodo.derecho, idReserva, nuevoEstado);
    }
}
