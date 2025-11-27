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
}
