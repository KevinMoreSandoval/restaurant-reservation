package Controller;

import Model.bd.ConnectionBD;
import Model.dao.ReservaDAO;
import Model.entidades.Reserva;
import Model.entidades.EstadoMesa;
import Model.entidades.ListaReservas;
import Model.entidades.ArbolReservas;
import Model.services.EstadoMesasManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaController {

    private EstadoMesasManager estados;
    private ListaReservas listaReservas;
    private ArbolReservas arbolReservas;

    public ReservaController(EstadoMesasManager estados) {
        this.estados = estados;
        this.listaReservas = new ListaReservas();
        this.arbolReservas = new ArbolReservas();
        // Cargar todas las reservas existentes al árbol y a la lista
        this.arbolReservas.cargarDesdeBaseDatos();
        this.listaReservas.cargarDesdeBaseDatos();
        // Sincronizar estados con el árbol
        sincronizarEstadosConArbol();
    }

    public ReservaController(EstadoMesasManager estados, ListaReservas listaReservas) {
        this.estados = estados;
        this.listaReservas = listaReservas;
        this.arbolReservas = new ArbolReservas();
        // Cargar todas las reservas existentes al árbol
        this.arbolReservas.cargarDesdeBaseDatos();
        // Sincronizar estados con el árbol
        sincronizarEstadosConArbol();
    }

    /**
     * Sincroniza el EstadoMesasManager con las reservas del árbol
     * Debe llamarse después de cargar el árbol desde la BD
     */
    private void sincronizarEstadosConArbol() {
        List<Reserva> todasReservas = arbolReservas.obtenerTodasEnOrden();

        for (Reserva r : todasReservas) {
            if (!r.getEstado().equals("CANCELADA")) {
                estados.setEstado(r.getFecha(), r.getIdMesa(), r.getHora(), EstadoMesa.OCUPADA);
            }
        }

        System.out.println("✅ EstadoMesasManager sincronizado con " + todasReservas.size() + " reservas");
    }

    // Registrar una nueva reserva
    public boolean registrarReserva(Reserva r) {
        String sql = """
                    INSERT INTO reservas (nombre_cliente, apellido_cliente, dni_cliente, fecha, hora, id_mesa, estado)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection cx = ConnectionBD.conectar(); PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, r.getNombreCliente());
            ps.setString(2, r.getApellidoCliente());
            ps.setString(3, r.getDniCliente());
            ps.setString(4, r.getFecha());
            ps.setString(5, r.getHora());
            ps.setInt(6, r.getIdMesa());
            ps.setString(7, r.getEstado());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                estados.setEstado(r.getFecha(), r.getIdMesa(), r.getHora(), EstadoMesa.OCUPADA);

                // Agregar a la lista enlazada y al árbol
                listaReservas.agregar(r);
                arbolReservas.insertar(r);
                System.out.println("✅ Reserva guardada en BD, lista enlazada y árbol");
                System.out.println("📊 Total en lista: " + listaReservas.contar() + " | Total en árbol: "
                        + arbolReservas.contarReservas());

                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar reserva: " + e.getMessage());
        }

        return false;
    }

    // Verificar si una mesa está libre consultando el árbol y luego la memoria
    public boolean mesaDisponible(String fecha, int idMesa, String horario) {
        // Primero consultar el árbol (que tiene todas las reservas de la BD)
        if (arbolReservas.existeReserva(fecha, idMesa, horario)) {
            return false; // Ya existe una reserva activa
        }

        // Luego verificar en memoria (por si hay cambios recientes)
        EstadoMesa est = estados.getEstado(fecha, idMesa, horario);
        return est == null || est == EstadoMesa.LIBRE;
    }

    // Listar reservas por fecha
    public List<Reserva> obtenerReservasPorFecha(String fecha) {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE fecha = ?";

        try (Connection cx = ConnectionBD.conectar(); PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, fecha);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getString("nombre_cliente"),
                        rs.getString("apellido_cliente"),
                        rs.getString("dni_cliente"),
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getInt("id_mesa"),
                        rs.getString("estado"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener reservas: " + e.getMessage());
        }

        return lista;
    }

    // Obtener la instancia de ListaReservas
    public ListaReservas getListaReservas() {
        return listaReservas;
    }

    // Obtener todas las reservas de la lista enlazada
    public List<Reserva> obtenerReservasDesdeListaEnlazada() {
        return listaReservas.obtenerTodas();
    }

    // Buscar reservas por fecha en la lista enlazada
    public List<Reserva> buscarPorFechaEnLista(String fecha) {
        return listaReservas.buscarPorFecha(fecha);
    }

    // Buscar reservas por DNI en la lista enlazada
    public List<Reserva> buscarPorDniEnLista(String dni) {
        return listaReservas.buscarPorDni(dni);
    }

    // Buscar reservas por mesa en la lista enlazada
    public List<Reserva> buscarPorMesaEnLista(int idMesa) {
        return listaReservas.buscarPorMesa(idMesa);
    }

    // Contar reservas en la lista enlazada
    public int contarReservasEnLista() {
        return listaReservas.contar();
    }

    // Mostrar todas las reservas de la lista enlazada
    public void mostrarReservasEnLista() {
        listaReservas.mostrarReservas();
    }

    // ========== MÉTODOS DEL ÁRBOL (BÚSQUEDA EFICIENTE) ==========

    /**
     * Buscar reservas por fecha usando el árbol (O(log n))
     * Más eficiente que buscar en la lista enlazada
     */
    public List<Reserva> buscarPorFechaEnArbol(String fecha) {
        return arbolReservas.buscarPorFecha(fecha);
    }

    /**
     * Obtener reservas del día de hoy usando el árbol
     */
    public List<Reserva> obtenerReservasDeHoy() {
        return arbolReservas.obtenerReservasDeHoy();
    }

    /**
     * Obtener todas las reservas del árbol en orden
     */
    public List<Reserva> obtenerTodasDesdeArbol() {
        return arbolReservas.obtenerTodasEnOrden();
    }

    /**
     * Obtener la instancia del árbol de reservas
     */
    public ArbolReservas getArbolReservas() {
        return arbolReservas;
    }

    /**
     * Contar reservas en el árbol
     */
    public int contarReservasEnArbol() {
        return arbolReservas.contarReservas();
    }

    /**
     * Mostrar estructura del árbol (para debugging)
     */
    public void mostrarEstructuraArbol() {
        arbolReservas.mostrarEstructura();
    }

    /**
     * Obtener reservas del día actual desde la base de datos
     * Útil para mostrar reservas de hoy incluso después de reiniciar el programa
     */
    public List<Reserva> obtenerReservasDeHoyDesdeBD() {
        List<Reserva> lista = new ArrayList<>();
        // Obtener fecha actual en formato yyyy-MM-dd
        java.time.LocalDate hoy = java.time.LocalDate.now();
        String fechaHoy = hoy.toString();

        String sql = "SELECT * FROM reservas WHERE fecha = ? ORDER BY hora";

        try (Connection cx = ConnectionBD.conectar();
                PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, fechaHoy);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getString("nombre_cliente"),
                        rs.getString("apellido_cliente"),
                        rs.getString("dni_cliente"),
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getInt("id_mesa"),
                        rs.getString("estado"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener reservas de hoy desde BD: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Actualizar el estado de una reserva
     */
    public boolean actualizarEstadoReserva(int idReserva, String nuevoEstado) {
        ReservaDAO dao = new ReservaDAO();
        boolean actualizado = dao.actualizarEstado(idReserva, nuevoEstado);

        if (actualizado) {
            // Actualizar en la lista enlazada
            listaReservas.actualizarEstado(idReserva, nuevoEstado);

            // Actualizar en el árbol
            arbolReservas.actualizarEstado(idReserva, nuevoEstado);

            System.out.println("✅ Estado actualizado en BD, lista y árbol");
        }

        return actualizado;
    }

    /**
     * Cancelar automáticamente reservas PENDIENTE cuya hora ya pasó
     */
    public int cancelarReservasPendientesExpiradas() {
        int canceladas = 0;

        // Obtener fecha y hora actual
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        String fechaHoy = ahora.toLocalDate().toString();
        String horaActual = ahora.toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));

        // Obtener todas las reservas
        List<Reserva> todasReservas = arbolReservas.obtenerTodasEnOrden();

        for (Reserva r : todasReservas) {
            // Solo procesar reservas PENDIENTE
            if (!r.getEstado().equals("PENDIENTE")) {
                continue;
            }

            // Verificar si la fecha/hora ya pasó
            boolean expirada = false;

            if (r.getFecha().compareTo(fechaHoy) < 0) {
                // Fecha anterior a hoy
                expirada = true;
            } else if (r.getFecha().equals(fechaHoy)) {
                // Misma fecha, verificar hora
                if (r.getHora().compareTo(horaActual) <= 0) {
                    expirada = true;
                }
            }

            if (expirada) {
                // Cancelar la reserva
                if (actualizarEstadoReserva(r.getId(), "CANCELADA")) {
                    canceladas++;
                    System.out.println("⏰ Reserva ID " + r.getId() + " cancelada automáticamente (expirada)");
                }
            }
        }

        if (canceladas > 0) {
            System.out.println("✅ Total de reservas pendientes canceladas: " + canceladas);
        }

        return canceladas;
    }

    /**
     * Obtener reservas registradas hoy (por fecha_registro) desde la BD
     */
    public List<Reserva> obtenerReservasRegistradasHoy() {
        List<Reserva> lista = new ArrayList<>();
        // Obtener fecha actual en formato yyyy-MM-dd
        java.time.LocalDate hoy = java.time.LocalDate.now();
        String fechaHoy = hoy.toString();

        String sql = "SELECT * FROM reservas WHERE DATE(fecha_registro) = ? ORDER BY fecha_registro DESC";

        try (Connection cx = ConnectionBD.conectar();
                PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setString(1, fechaHoy);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getString("nombre_cliente"),
                        rs.getString("apellido_cliente"),
                        rs.getString("dni_cliente"),
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getInt("id_mesa"),
                        rs.getString("estado"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener reservas registradas hoy: " + e.getMessage());
        }

        return lista;
    }
}
