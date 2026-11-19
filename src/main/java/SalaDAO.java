import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Acceso a la tabla sala. */
public class SalaDAO {

    private final Connection conexion;

    public SalaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /** Guarda la sala en la partida indicada y le asigna el id generado por la base de datos. */
    public void guardarSala(Sala sala, int idPartida) throws SQLException {
        String sql = "INSERT INTO sala (id_partida, nombre, saboteada) VALUES (?, ?, ?)";
        try (PreparedStatement orden = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            orden.setInt(1, idPartida);
            orden.setString(2, sala.getNombre());
            orden.setBoolean(3, sala.isSaboteada());
            orden.executeUpdate();
            try (ResultSet claves = orden.getGeneratedKeys()) {
                if (claves.next()) {
                    sala.setId(claves.getInt(1));
                }
            }
        }
    }
}
