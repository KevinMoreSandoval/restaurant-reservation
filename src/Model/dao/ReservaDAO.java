package Model.dao;

import Model.bd.ConnectionBD;
import Model.entidades.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReservaDAO {

    public boolean registrarReserva(Reserva r) {

        String sql = "INSERT INTO reservas(nombre_cliente, apellido_cliente, dni_cliente, fecha, hora, id_mesa, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection cx = ConnectionBD.conectar(); PreparedStatement pr = cx.prepareStatement(sql)) {

            pr.setString(1, r.getNombreCliente());
            pr.setString(2, r.getApellidoCliente());
            pr.setString(3, r.getDniCliente());
            pr.setString(4, r.getFecha());
            pr.setString(5, r.getHora());
            pr.setInt(6, r.getIdMesa());
            pr.setString(7, r.getEstado());

            pr.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error al registrar reserva: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualizar el estado de una reserva
     */
    public boolean actualizarEstado(int idReserva, String nuevoEstado) {
        String sql = "UPDATE reservas SET estado = ? WHERE id_reserva = ?";

        try (Connection cx = ConnectionBD.conectar();
                PreparedStatement pr = cx.prepareStatement(sql)) {

            pr.setString(1, nuevoEstado);
            pr.setInt(2, idReserva);

            int rowsAffected = pr.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Estado actualizado a " + nuevoEstado + " para reserva ID: " + idReserva);
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println("❌ Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }
}
