package Model.entidades;

import java.util.ArrayList;
import java.util.List;

public class ListaReservas {

    private NodoReserva cabeza;

    public void agregar(Reserva reserva) {
        NodoReserva nuevo = new NodoReserva(reserva);

        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }

        NodoReserva actual = cabeza;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }

        actual.siguiente = nuevo;
    }

    // Obtener todas las reservas de la lista
    public List<Reserva> obtenerTodas() {
        List<Reserva> lista = new ArrayList<>();
        NodoReserva actual = cabeza;

        while (actual != null) {
            lista.add(actual.reserva);
            actual = actual.siguiente;
        }

        return lista;
    }

    // Buscar reservas por fecha
    public List<Reserva> buscarPorFecha(String fecha) {
        List<Reserva> lista = new ArrayList<>();
        NodoReserva actual = cabeza;

        while (actual != null) {
            if (actual.reserva.getFecha().equals(fecha)) {
                lista.add(actual.reserva);
            }
            actual = actual.siguiente;
        }

        return lista;
    }

    // Buscar reservas por DNI
    public List<Reserva> buscarPorDni(String dni) {
        List<Reserva> lista = new ArrayList<>();
        NodoReserva actual = cabeza;

        while (actual != null) {
            if (actual.reserva.getDniCliente().equals(dni)) {
                lista.add(actual.reserva);
            }
            actual = actual.siguiente;
        }

        return lista;
    }

    // Buscar reservas por mesa
    public List<Reserva> buscarPorMesa(int idMesa) {
        List<Reserva> lista = new ArrayList<>();
        NodoReserva actual = cabeza;

        while (actual != null) {
            if (actual.reserva.getIdMesa() == idMesa) {
                lista.add(actual.reserva);
            }
            actual = actual.siguiente;
        }

        return lista;
    }

    // Contar el número total de reservas
    public int contar() {
        int contador = 0;
        NodoReserva actual = cabeza;

        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }

        return contador;
    }

    // Verificar si la lista está vacía
    public boolean estaVacia() {
        return cabeza == null;
    }

    // Mostrar información de todas las reservas
    public void mostrarReservas() {
        if (estaVacia()) {
            System.out.println("No hay reservas en la lista enlazada.");
            return;
        }

        NodoReserva actual = cabeza;
        int contador = 1;

        System.out.println("=== RESERVAS EN LISTA ENLAZADA ===");
        while (actual != null) {
            Reserva r = actual.reserva;
            System.out.println(contador + ". " + r.getNombreCliente() + " " + r.getApellidoCliente()
                    + " | DNI: " + r.getDniCliente()
                    + " | Fecha: " + r.getFecha()
                    + " | Hora: " + r.getHora()
                    + " | Mesa: " + r.getIdMesa()
                    + " | Estado: " + r.getEstado());
            actual = actual.siguiente;
            contador++;
        }
        System.out.println("Total de reservas: " + contar());
    }
}
