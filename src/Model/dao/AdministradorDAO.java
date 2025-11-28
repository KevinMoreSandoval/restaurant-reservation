
package Model.dao;

import Model.bd.ConnectionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdministradorDAO {

    public boolean validarLogin(String usuario, String password) {

        String sql = "SELECT * FROM admin WHERE usuario = ? AND password = ?";

        try (Connection cx = ConnectionBD.conectar(); PreparedStatement pr = cx.prepareStatement(sql)) {

            pr.setString(1, usuario);
            pr.setString(2, password);

            ResultSet rs = pr.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Error login: " + e.getMessage());
            return false;
        }
    }
}
