import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Acceso a la tabla tarea. Las salas y los tripulantes deben guardarse antes (claves foráneas). */
public class TareaDAO {

    private final Connection conexion;

    public TareaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void guardarTarea(Tarea tarea, int idPartida) throws SQLException {
        String sql = "INSERT INTO tarea (id_partida, descripcion, completada, id_tripulante, id_sala) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement orden = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            orden.setInt(1, idPartida);
            orden.setString(2, tarea.getDescripcion());
            orden.setBoolean(3, tarea.isCompletada());
            orden.setInt(4, tarea.getTripulanteAsignado().getId());
            orden.setInt(5, tarea.getSala().getId());
            orden.executeUpdate();
            try (ResultSet claves = orden.getGeneratedKeys()) {
                if (claves.next()) {
                    tarea.setId(claves.getInt(1));
                }
            }
        }
    }
}
