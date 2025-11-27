package Model.bd;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_reservas?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("❌ ERROR BD: " + e.getMessage());
            return null;
        }
    }
}
