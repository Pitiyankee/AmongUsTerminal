import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RolesTest {

    @Test
    void elImpostorPuedeEliminarAOtroJugador() {
        Fixtures f = new Fixtures();
        f.ana.eliminar(f.beto);
        assertFalse(f.beto.isVivo());
    }

    @Test
    void elImpostorNoPuedeEliminarseASiMismo() {
        Fixtures f = new Fixtures();
        f.ana.eliminar(f.ana);
        assertTrue(f.ana.isVivo());
    }

    @Test
    void elImpostorSaboteaUnaSala() {
        Fixtures f = new Fixtures();
        f.ana.sabotear(f.reactor);
        assertTrue(f.reactor.isSaboteada());
    }

    @Test
    void elIngenieroRepararUnaSalaSaboteada() {
        Fixtures f = new Fixtures();
        f.reactor.setSaboteada(true);
        f.cris.repararSala(f.reactor);
        assertFalse(f.reactor.isSaboteada());
    }

    @Test
    void unTripulanteCompletaSuTarea() {
        Fixtures f = new Fixtures();
        Tarea tarea = f.nave.getTareasPendientes(f.beto).get(0);
        f.beto.realizarTarea(tarea);
        assertTrue(tarea.isCompletada());
    }

    @Test
    void noSePuedeHacerUnaTareaDeOtroJugador() {
        Fixtures f = new Fixtures();
        Tarea tareaDeBeto = f.nave.getTareasPendientes(f.beto).get(0);
        f.cris.realizarTarea(tareaDeBeto);
        assertFalse(tareaDeBeto.isCompletada());
    }

    @Test
    void unaSalaSaboteadaBloqueaLasTareasDeEsaSala() {
        Fixtures f = new Fixtures();
        Tarea tarea = f.nave.getTareasPendientes(f.beto).get(0);
        tarea.getSala().setSaboteada(true);

        f.beto.realizarTarea(tarea);
        assertFalse(tarea.isCompletada());

        tarea.getSala().setSaboteada(false);
        f.beto.realizarTarea(tarea);
        assertTrue(tarea.isCompletada());
    }

    @Test
    void unJugadorEliminadoNoPuedeHacerTareas() {
        Fixtures f = new Fixtures();
        Tarea tarea = f.nave.getTareasPendientes(f.dani).get(0);
        f.dani.setVivo(false);
        f.dani.realizarTarea(tarea);
        assertFalse(tarea.isCompletada());
    }

    @Test
    void elImpostorNoCompletaTareasAunqueLasFinja() {
        Fixtures f = new Fixtures();
        Tarea tarea = f.nave.getTareasPendientes(f.beto).get(0);
        f.ana.realizarTarea(tarea);
        assertFalse(tarea.isCompletada());
    }
}
