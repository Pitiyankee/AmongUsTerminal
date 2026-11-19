import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * Comprueba el guardado en base de datos usando H2 en modo compatible con MySQL,
 * cargando el mismo script.sql del proyecto (sin las líneas CREATE DATABASE y USE).
 */
class GuardadoPartidaTest {

    private static Connection nuevaBase() throws SQLException, IOException {
        Connection conexion = DriverManager.getConnection(
                "jdbc:h2:mem:" + UUID.randomUUID() + ";MODE=MySQL;DB_CLOSE_DELAY=-1");

        String script = Files.readString(Path.of("script.sql"), StandardCharsets.UTF_8);
        StringBuilder sinComentarios = new StringBuilder();
        for (String linea : script.split("\n")) {
            if (!linea.trim().startsWith("--")) {
                sinComentarios.append(linea).append("\n");
            }
        }
        try (Statement orden = conexion.createStatement()) {
            for (String sentencia : sinComentarios.toString().split(";")) {
                String sql = sentencia.trim();
                if (sql.isEmpty() || sql.toUpperCase().startsWith("CREATE DATABASE")
                        || sql.toUpperCase().startsWith("USE ")) {
                    continue;
                }
                orden.execute(sql);
            }
        }
        return conexion;
    }

    private static int contar(Connection conexion, String tabla) throws SQLException {
        try (Statement orden = conexion.createStatement();
             ResultSet r = orden.executeQuery("SELECT COUNT(*) FROM " + tabla)) {
            r.next();
            return r.getInt(1);
        }
    }

    @Test
    void guardaLaPartidaCompleta() throws Exception {
        try (Connection conexion = nuevaBase()) {
            Fixtures f = new Fixtures();
            f.nave.getTareas().get(0).setCompletada(true);
            f.dani.setVivo(false);

            GuardadoPartida.guardar(conexion, f.nave, Partida.GANA_IMPOSTOR);

            assertEquals(1, contar(conexion, "partida"));
            assertEquals(2, contar(conexion, "sala"));
            assertEquals(4, contar(conexion, "tripulante"));
            assertEquals(6, contar(conexion, "tarea"));

            try (Statement orden = conexion.createStatement();
                 ResultSet r = orden.executeQuery("SELECT ganador FROM partida")) {
                r.next();
                assertEquals("IMPOSTOR", r.getString(1));
            }
            try (Statement orden = conexion.createStatement();
                 ResultSet r = orden.executeQuery("SELECT COUNT(*) FROM tripulante WHERE vivo = FALSE")) {
                r.next();
                assertEquals(1, r.getInt(1));
            }
            assertTrue(f.dani.getId() > 0, "El tripulante debe recibir el id generado por la base de datos");
        }
    }

    @Test
    void sePuedenGuardarVariasPartidasSinConflictos() throws Exception {
        try (Connection conexion = nuevaBase()) {
            GuardadoPartida.guardar(conexion, new Fixtures().nave, Partida.GANAN_TRIPULANTES);
            GuardadoPartida.guardar(conexion, new Fixtures().nave, Partida.GANA_IMPOSTOR);

            assertEquals(2, contar(conexion, "partida"));
            assertEquals(4, contar(conexion, "sala"));
            assertEquals(8, contar(conexion, "tripulante"));
        }
    }

    @Test
    void siFallaElGuardadoSeDeshaceTodo() throws Exception {
        try (Connection conexion = nuevaBase()) {
            Fixtures f = new Fixtures();
            // Tarea de un jugador que no pertenece a la nave: su id no existe y viola la clave foránea.
            Tripulante ajeno = new Medico("Fantasma");
            f.nave.agregarTarea(new Tarea("Tarea imposible", ajeno, f.reactor));

            assertThrows(SQLException.class,
                    () -> GuardadoPartida.guardar(conexion, f.nave, Partida.GANAN_TRIPULANTES));

            assertEquals(0, contar(conexion, "partida"));
            assertEquals(0, contar(conexion, "tripulante"));
            assertEquals(0, contar(conexion, "tarea"));
        }
    }
}
