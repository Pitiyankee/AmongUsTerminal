import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Acceso a la tabla tripulante. */
public class TripulanteDAO {

    private final Connection conexion;

    public TripulanteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /** Guarda al jugador (con su rol y si sigue vivo) y le asigna el id generado. */
    public void guardarTripulante(Tripulante tripulante, int idPartida) throws SQLException {
        String sql = "INSERT INTO tripulante (id_partida, nombre, rol, vivo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement orden = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            orden.setInt(1, idPartida);
            orden.setString(2, tripulante.getNombre());
            orden.setString(3, tripulante.getRol());
            orden.setBoolean(4, tripulante.isVivo());
            orden.executeUpdate();
            try (ResultSet claves = orden.getGeneratedKeys()) {
                if (claves.next()) {
                    tripulante.setId(claves.getInt(1));
                }
            }
        }
    }
}
