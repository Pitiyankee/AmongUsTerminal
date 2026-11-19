import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Partidas completas simuladas escribiendo las respuestas por teclado. */
class PartidaTest {

    private PrintStream salidaOriginal;

    @BeforeEach
    void silenciarSalida() {
        salidaOriginal = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restaurar() {
        System.setOut(salidaOriginal);
        Consola.usarEntrada(System.in);
    }

    private static void teclear(String... lineas) {
        String texto = String.join("\n", lineas) + "\n";
        Consola.usarEntrada(new ByteArrayInputStream(texto.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void gananLosTripulantesCompletandoTodasLasTareas() {
        Fixtures f = new Fixtures();
        // Cada ronda: Ana pasa; Beto, Cris y Dani hacen su primera tarea pendiente.
        String[] ronda = {
                "", "4", "",           // Ana: Enter, pasar turno, Enter
                "", "1", "1", "",      // Beto: Enter, hacer tarea, tarea 1, Enter
                "", "1", "1", "",      // Cris
                "", "1", "1", "",      // Dani
        };
        String[] dosRondas = new String[ronda.length * 2];
        System.arraycopy(ronda, 0, dosRondas, 0, ronda.length);
        System.arraycopy(ronda, 0, dosRondas, ronda.length, ronda.length);
        teclear(dosRondas);

        String ganador = new Partida(f.nave, new Random(1)).jugar();

        assertEquals(Partida.GANAN_TRIPULANTES, ganador);
        assertEquals(6, f.nave.contarTareasCompletadas());
    }

    @Test
    void ganaElImpostorEliminandoATripulantes() {
        Fixtures f = new Fixtures();
        teclear(
                // Ronda 1
                "", "3", "1", "",        // Ana: eliminar, víctima 1 (Beto), Enter
                "", "3", "",             // Cris: pasar turno
                "", "3", "",             // Dani: pasar turno
                // Reunión por el cuerpo de Beto: todos saltan el voto
                "",                      // empezar votación
                "", "0", "",             // Ana salta
                "", "0", "",             // Cris salta
                "", "0", "",             // Dani salta
                "",                      // continuar tras el resultado
                // Ronda 2
                "", "3", "1", ""         // Ana: eliminar, víctima 1 (Cris) -> quedan 1 impostor y 1 tripulante
        );

        String ganador = new Partida(f.nave, new Random(1)).jugar();

        assertEquals(Partida.GANA_IMPOSTOR, ganador);
        assertFalse(f.beto.isVivo());
        assertFalse(f.cris.isVivo());
        assertTrue(f.dani.isVivo());
    }

    @Test
    void laVotacionPuedeExpulsarAlImpostorYGananLosTripulantes() {
        Fixtures f = new Fixtures();
        teclear(
                // Ronda 1: Ana pasa; Beto (capitán) convoca reunión con su habilidad
                "", "4", "",
                "", "2", "",             // Beto: Enter, usar habilidad -> reunión (empezar votación)
                // Votantes vivos: Ana, Beto, Cris, Dani
                "", "0", "",             // Ana salta
                "", "1", "",             // Beto vota 1 (Ana)
                "", "1", "",             // Cris vota 1 (Ana)
                "", "1", "",             // Dani vota 1 (Ana)
                "",                      // continuar tras el resultado
                ""                       // Enter de ocultar pantalla del turno de Beto
        );

        String ganador = new Partida(f.nave, new Random(1)).jugar();

        assertEquals(Partida.GANAN_TRIPULANTES, ganador);
        assertFalse(f.ana.isVivo());
    }
}
