package restauranteApp;
import java.sql.*;

public class ReservaDAO {

    public boolean verificarDisponibilidad(int idMesa, Date fecha, Time hora) {
        String sql = "SELECT * FROM reservas WHERE idMesa=? AND fecha=? AND hora=?";
        try (Connection con = ConnectionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            ps.setDate(2, fecha);
            ps.setTime(3, hora);
            return !ps.executeQuery().next(); // Si no existe → disponible
        } catch (SQLException e) {
            System.out.println("⚠ Error disponibilidad: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarReserva(Reserva r) {
        String sql = "INSERT INTO reservas(nombre, apellido, dni, fecha, hora, idMesa) VALUES(?,?,?,?,?,?)";
        try (Connection con = ConnectionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getApellido());
            ps.setString(3, r.getDni());
            ps.setDate(4, Date.valueOf(r.getFecha()));
            ps.setTime(5, Time.valueOf(r.getHora()));
            ps.setInt(6, r.getIdMesa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error registrar: " + e.getMessage());
            return false;
        }
    }
}
