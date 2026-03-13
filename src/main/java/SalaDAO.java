import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Carlitos, aquí hago la clase que va a guardar las salas en la base de datos.
// Farid pidió usar el modelo DAO para mantener el orden, así que esta clase solo sirve para eso (lo que hablamos en clase).
public class SalaDAO {

    // Esta variable es el "cable" para conectar mi código con la base de datos real
    private Connection conexion;

    // Cuando creo este DAO, le paso el cable de conexión para que sepa por dónde hablar
    public SalaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Carlos, este es el método para meter una sala nueva en la tabla
    public void guardarSala(Sala sala) {
        // Aquí escribo el comando en SQL tal cual nos enseñó Farid.
        // Los signos de interrogación son los huecos vacíos que voy a rellenar abajo.
        String sql = "INSERT INTO salas (nombre, saboteada) VALUES (?, ?)";

        try {
            // Preparo el comando para enviarlo por el cable
            PreparedStatement orden = conexion.prepareStatement(sql);

            // Relleno el primer hueco con el nombre de la sala
            orden.setString(1, sala.getNombre());
            // Relleno el segundo hueco diciendo si está saboteada o no
            orden.setBoolean(2, sala.isSaboteada());

            orden.executeUpdate();
            System.out.println("¡Exito! La sala " + sala.getNombre() + " se guardó en la base de datos.");

        } catch (SQLException e) {
            // Si algo sale mal con la base de datos, imprimo este mensaje para enterarnos
            System.out.println("Error, no pude guardar la sala en la base de datos.");
            e.printStackTrace();
        }
    }
}