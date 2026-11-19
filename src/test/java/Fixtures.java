import java.util.ArrayList;

/** Partida de prueba con roles y tareas fijos: Ana (impostor), Beto (capitán), Cris (ingeniero), Dani (médico). */
final class Fixtures {

    final Impostor ana = new Impostor("Ana");
    final Capitan beto = new Capitan("Beto");
    final Ingeniero cris = new Ingeniero("Cris");
    final Medico dani = new Medico("Dani");
    final Sala reactor = new Sala("Reactor");
    final Sala cafeteria = new Sala("Cafetería");
    final Nave nave;

    Fixtures() {
        ArrayList<Tripulante> jugadores = new ArrayList<>();
        jugadores.add(ana);
        jugadores.add(beto);
        jugadores.add(cris);
        jugadores.add(dani);

        ArrayList<Sala> salas = new ArrayList<>();
        salas.add(reactor);
        salas.add(cafeteria);

        nave = new Nave(jugadores, salas);
        for (Tripulante t : new Tripulante[]{beto, cris, dani}) {
            nave.agregarTarea(new Tarea("Tarea A de " + t.getNombre(), t, reactor));
            nave.agregarTarea(new Tarea("Tarea B de " + t.getNombre(), t, cafeteria));
        }
    }
}
