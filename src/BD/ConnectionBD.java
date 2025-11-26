
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_reservas";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection conectar() {

        try {
            return DriverManager.getConnection(URL, USER, PASS);

        } catch (SQLException e) {

            System.out.println("Conexión fallida" + e.getMessage());
            return null;
        }

    }
}
