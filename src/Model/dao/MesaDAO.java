package Model.dao;

import Model.bd.ConnectionBD;
import Model.entidades.Mesa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MesaDAO {

    public ArrayList<Mesa> listarMesas() {
        ArrayList<Mesa> lista = new ArrayList<>();

        String sql = "SELECT * FROM mesas";

        try (Connection cx = ConnectionBD.conectar();
                PreparedStatement pr = cx.prepareStatement(sql);
                ResultSet rs = pr.executeQuery()) {

            while (rs.next()) {
                Mesa m = new Mesa(
                        rs.getInt("id_mesa"),
                        rs.getInt("capacidad"));
                lista.add(m);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al obtener mesas: " + e.getMessage());
        }

        return lista;
    }
}
