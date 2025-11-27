package Controller;

import Model.bd.ConnectionBD;
import Model.entidades.Reserva;
import Model.entidades.EstadoMesa;
import Model.services.EstadoMesasManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaController {

    private EstadoMesasManager estados;

    public ReservaController(EstadoMesasManager estados) {
        this.estados = estados;
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
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar reserva: " + e.getMessage());
        }

        return false;
    }

    // Verificar si una mesa está libre según la estructura en memoria
    public boolean mesaDisponible(String fecha, int idMesa, String horario) {
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
}
