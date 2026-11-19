import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza la conexión a MySQL. Los datos de conexión se leen de variables de entorno
 * para no dejar usuario ni contraseña escritos en el código:
 * DB_URL, DB_USER y DB_PASS.
 */
public class DBUtil {

    private static final String DB_URL = leer("DB_URL", "jdbc:mysql://localhost:3306/nave_espacial");
    private static final String USER = leer("DB_USER", "root");
    private static final String PASS = leer("DB_PASS", "");

    private static String leer(String variable, String porDefecto) {
        String valor = System.getenv(variable);
        return (valor == null || valor.isBlank()) ? porDefecto : valor;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
