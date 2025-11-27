package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_reservas?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root"; 

    public static Connection conectar() {
        Connection con = null;
        try {
            // Registrar el driver (muy importante)
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✔ Conexión exitosa a MySQL");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ ERROR: No se encontró el Driver JDBC de MySQL");
        } catch (SQLException e) {
            System.out.println("❌ ERROR al conectar: " + e.getMessage());
        }
        return con;
    }

}
