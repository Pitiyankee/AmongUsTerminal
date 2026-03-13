import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Y aquí en esta seccion va el de los tripulantes. Funciona casi igual que el de las salas,
// pero con los datos de los jugadores. Avisame si tienes dudas.
public class TripulanteDAO {

    private Connection conexion;

    public TripulanteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Carlos, con este método guardamos al jugador (sea médico, impostor, etc.) en la tabla
    public void guardarTripulante(Tripulante tripulante) {
        // El comando SQL para insertar al jugador. Tres huecos: nombre, rol y si está vivo.
        String sql = "INSERT INTO tripulantes (nombre, rol, vivo) VALUES (?, ?, ?)";

        try {
            PreparedStatement orden = conexion.prepareStatement(sql);

            // Relleno los datos sacándolos de la información del jugador
            orden.setString(1, tripulante.getNombre());
            orden.setString(2, tripulante.getRol());
            orden.setBoolean(3, tripulante.isVivo());

            // Envío la orden
            orden.executeUpdate();
            System.out.println("¡Listo! " + tripulante.getNombre() + " ya está en la base de datos como " + tripulante.getRol());

        } catch (SQLException e) {
            System.out.println("Uy, hubo un error al guardar al tripulante.");
            e.printStackTrace();
        }
    }
}
