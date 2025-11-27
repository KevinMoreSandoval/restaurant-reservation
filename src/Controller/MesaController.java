package Controller;

import Model.bd.ConnectionBD;
import Model.entidades.Mesa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesaController {

    // Obtener todas las mesas
    public List<Mesa> obtenerMesas() {
        List<Mesa> lista = new ArrayList<>();
        String sql = "SELECT * FROM mesas ORDER BY capacidad, id_mesa";

        try (Connection cx = ConnectionBD.conectar();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mesa m = new Mesa(
                        rs.getInt("id_mesa"),
                        rs.getInt("capacidad"));
                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener mesas: " + e.getMessage());
        }

        return lista;
    }

    // Buscar mesa por ID
    public Mesa buscarMesa(int idMesa) {
        String sql = "SELECT * FROM mesas WHERE id_mesa = ?";
        Mesa m = null;

        try (Connection cx = ConnectionBD.conectar();
                PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, idMesa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m = new Mesa(
                        rs.getInt("id_mesa"),
                        rs.getInt("capacidad"));
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar mesa: " + e.getMessage());
        }

        return m;
    }

    // Obtener mesas disponibles según capacidad mínima
    public List<Mesa> obtenerMesasPorCapacidad(int numPersonas) {
        List<Mesa> lista = new ArrayList<>();
        String sql = "SELECT * FROM mesas WHERE capacidad >= ? ORDER BY capacidad, id_mesa";

        try (Connection cx = ConnectionBD.conectar();
                PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, numPersonas);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mesa m = new Mesa(
                        rs.getInt("id_mesa"),
                        rs.getInt("capacidad"));
                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener mesas por capacidad: " + e.getMessage());
        }

        return lista;
    }
}
