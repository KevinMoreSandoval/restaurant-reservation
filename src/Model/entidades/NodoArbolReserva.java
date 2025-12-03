package Model.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Nodo del árbol binario de búsqueda para organizar reservas por fecha
 */
public class NodoArbolReserva {

    private String fecha; // Clave del nodo (formato: YYYY-MM-DD)
    private List<Reserva> reservas; // Lista de reservas para esta fecha
    public NodoArbolReserva izquierdo;
    public NodoArbolReserva derecho;

    public NodoArbolReserva(String fecha) {
        this.fecha = fecha;
        this.reservas = new ArrayList<>();
        this.izquierdo = null;
        this.derecho = null;
    }

    // Agregar una reserva a este nodo
    public void agregarReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }

    // Getters
    public String getFecha() {
        return fecha;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public int getCantidadReservas() {
        return reservas.size();
    }
}
