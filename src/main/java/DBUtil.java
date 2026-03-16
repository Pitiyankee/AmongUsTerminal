import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Carlos, esta es la clase que concentra la conexion de todo
// Farid la pide para no tener que repetir el usuario y la clave en todos lados.
public class DBUtil {
    // La direccion de la base de datos que creamos en el script.sql
    private static final String DB_URL = "jdbc:mysql://localhost:3306/nave_espacial";
    private static final String USER = "root"; // Pon tu usuario de MySQL aqui
    private static final String PASS = "admin"; // Pon tu contraseña de MySQL aqui

    // Este metodo es el que usaran los DAO para pedir lagit add .  conexion
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
