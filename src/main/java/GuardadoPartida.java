import java.sql.Connection;
import java.sql.SQLException;

/** Guarda en la base de datos el estado final de una partida, todo dentro de una transacción. */
public class GuardadoPartida {

    /** Abre la conexión configurada en DBUtil y guarda la partida. Si falla, avisa sin cerrar el juego. */
    public static boolean guardar(Nave nave, String ganador) {
        try (Connection conexion = DBUtil.getConnection()) {
            guardar(conexion, nave, ganador);
            System.out.println("Partida guardada en la base de datos.");
            return true;
        } catch (SQLException e) {
            System.out.println("No se pudo guardar la partida en la base de datos: " + e.getMessage());
            return false;
        }
    }

    /** Guarda partida, salas, tripulantes y tareas. Si algo falla, deshace todo (rollback). */
    public static void guardar(Connection conexion, Nave nave, String ganador) throws SQLException {
        boolean autoCommitOriginal = conexion.getAutoCommit();
        conexion.setAutoCommit(false);
        try {
            int idPartida = new PartidaDAO(conexion).guardarPartida(ganador);

            SalaDAO salaDAO = new SalaDAO(conexion);
            for (Sala sala : nave.getSalas()) {
                salaDAO.guardarSala(sala, idPartida);
            }

            TripulanteDAO tripulanteDAO = new TripulanteDAO(conexion);
            for (Tripulante tripulante : nave.getTripulantes()) {
                tripulanteDAO.guardarTripulante(tripulante, idPartida);
            }

            TareaDAO tareaDAO = new TareaDAO(conexion);
            for (Tarea tarea : nave.getTareas()) {
                tareaDAO.guardarTarea(tarea, idPartida);
            }

            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(autoCommitOriginal);
        }
    }
}
