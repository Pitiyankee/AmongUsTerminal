import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Acceso a la tabla partida: una fila por cada partida jugada. */
public class PartidaDAO {

    private final Connection conexion;

    public PartidaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /** Crea la partida con su ganador y devuelve el id generado. */
    public int guardarPartida(String ganador) throws SQLException {
        String sql = "INSERT INTO partida (ganador) VALUES (?)";
        try (PreparedStatement orden = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            orden.setString(1, ganador);
            orden.executeUpdate();
            try (ResultSet claves = orden.getGeneratedKeys()) {
                claves.next();
                return claves.getInt(1);
            }
        }
    }
}
