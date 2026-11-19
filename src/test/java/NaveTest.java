import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class NaveTest {

    @Test
    void alPrincipioNadieHaGanado() {
        Fixtures f = new Fixtures();
        assertFalse(f.nave.verificarVictoriaTripulantes());
        assertFalse(f.nave.verificarVictoriaImpostor());
    }

    @Test
    void gananLosTripulantesSiSeCompletanTodasLasTareas() {
        Fixtures f = new Fixtures();
        f.nave.getTareas().forEach(t -> t.setCompletada(true));
        assertTrue(f.nave.verificarVictoriaTripulantes());
    }

    @Test
    void gananLosTripulantesSiElImpostorEstaEliminado() {
        Fixtures f = new Fixtures();
        f.ana.setVivo(false);
        assertTrue(f.nave.verificarVictoriaTripulantes());
    }

    @Test
    void ganaElImpostorCuandoHayTantosImpostoresComoTripulantes() {
        Fixtures f = new Fixtures();
        f.beto.setVivo(false);
        f.cris.setVivo(false);
        assertTrue(f.nave.verificarVictoriaImpostor());
    }

    @Test
    void laVotacionExpulsaAlMasVotado() {
        Fixtures f = new Fixtures();
        List<Tripulante> votos = Arrays.asList(f.ana, f.ana, f.dani);
        assertSame(f.ana, f.nave.resolverVotacion(votos));
    }

    @Test
    void unEmpateNoExpulsaANadie() {
        Fixtures f = new Fixtures();
        assertNull(f.nave.resolverVotacion(Arrays.asList(f.ana, f.dani)));
    }

    @Test
    void saltarElVotoGanaSiTieneTantosVotosComoElMasVotado() {
        Fixtures f = new Fixtures();
        assertNull(f.nave.resolverVotacion(Arrays.asList(f.ana, null)));
    }

    @Test
    void sinVotosNoSeExpulsaANadie() {
        Fixtures f = new Fixtures();
        assertNull(f.nave.resolverVotacion(Arrays.asList(null, null, null)));
    }

    @Test
    void elImpostorNoRecibeTareasYLosDemasRecibenLasSuyas() {
        Fixtures f = new Fixtures();
        f.nave.getTareas().clear();
        f.nave.asignarTareas(2, new Random(1));

        assertEquals(6, f.nave.getTareas().size());
        assertTrue(f.nave.getTareasPendientes(f.ana).isEmpty());
        assertEquals(2, f.nave.getTareasPendientes(f.beto).size());
        assertEquals(2, f.nave.getTareasPendientes(f.cris).size());
        assertEquals(2, f.nave.getTareasPendientes(f.dani).size());
    }

    @Test
    void lasTareasDeUnMismoTripulanteSonDistintas() {
        Fixtures f = new Fixtures();
        f.nave.getTareas().clear();
        f.nave.asignarTareas(2, new Random(7));

        List<Tarea> tareasDeBeto = f.nave.getTareasPendientes(f.beto);
        assertFalse(tareasDeBeto.get(0).getDescripcion().equals(tareasDeBeto.get(1).getDescripcion()));
    }
}
