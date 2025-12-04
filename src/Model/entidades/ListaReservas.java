package Model.entidades;

import Model.bd.ConnectionBD;
import java.sql.*;
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

    // Cargar todas las reservas desde la base de datos a la lista
    public void cargarDesdeBaseDatos() {
        // Limpiar la lista actual
        cabeza = null;

        String sql = "SELECT * FROM reservas ORDER BY fecha, hora";

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

                agregar(reserva);
                contador++;
            }

            System.out.println("📋 Lista de reservas cargada: " + contador + " reservas");

        } catch (SQLException e) {
            System.out.println("❌ Error al cargar reservas a la lista: " + e.getMessage());
        }
    }

    // Cargar solo las reservas de hoy desde la base de datos
    public void cargarReservasDeHoyDesdeBD() {
        // Limpiar la lista actual
        cabeza = null;

        // Obtener fecha actual en formato yyyy-MM-dd
        java.time.LocalDate hoy = java.time.LocalDate.now();
        String fechaHoy = hoy.toString();

        String sql = "SELECT * FROM reservas WHERE fecha = ? ORDER BY hora";

        try (Connection cx = ConnectionBD.conectar();
                PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, fechaHoy);
            ResultSet rs = ps.executeQuery();

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

                agregar(reserva);
                contador++;
            }

            System.out.println("📋 Reservas de hoy cargadas a la lista: " + contador + " reservas");

        } catch (SQLException e) {
            System.out.println("❌ Error al cargar reservas de hoy: " + e.getMessage());
        }
    }

    // Buscar reserva por dia
    public List<Reserva> buscarPorDia(String fecha) {
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
    // Buscar reservas por mes ()

    public List<Reserva> buscarPorMes(int mes, int anio) {
        List<Reserva> lista = new ArrayList<>();
        NodoReserva actual = cabeza;

        String mesStr = (mes < 10) ? "0" + mes : String.valueOf(mes);

        while (actual != null) {

            String fecha = actual.reserva.getFecha();
            if (fecha.startsWith(anio + "-" + mesStr)) {
                lista.add(actual.reserva);

            }
            actual = actual.siguiente;
        }
        return lista;

    }

    public List<Reserva> buscarPorAnio(int anio) {
        List<Reserva> lista = new ArrayList<>();
        NodoReserva actual = cabeza;

        while (actual != null) {

            String fecha = actual.reserva.getFecha();
            if (actual.reserva.getFecha().startsWith(String.valueOf(anio))) {
                lista.add(actual.reserva);

            }
            actual = actual.siguiente;
        }
        return lista;

    }

    /**
     * Actualizar el estado de una reserva en la lista enlazada
     */
    public void actualizarEstado(int idReserva, String nuevoEstado) {
        NodoReserva actual = cabeza;

        while (actual != null) {
            if (actual.reserva.getId() == idReserva) {
                actual.reserva.setEstado(nuevoEstado);
                System.out.println("✅ Estado actualizado en lista enlazada para reserva ID: " + idReserva);
                return;
            }
            actual = actual.siguiente;
        }

        System.out.println("⚠️ Reserva ID " + idReserva + " no encontrada en lista enlazada");
    }

    /**
     * Alias de cargarDesdeBaseDatos() para recargar/sincronizar la lista desde BD
     */
    public void recargarDesdeBD() {
        cargarDesdeBaseDatos();
    }

}
