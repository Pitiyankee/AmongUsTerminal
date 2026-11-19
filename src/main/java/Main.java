import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** Punto de entrada: registra a los jugadores, reparte los roles y lanza la partida. */
public class Main {

    private static final int MIN_JUGADORES = 4;
    private static final int MAX_JUGADORES = 10;
    private static final int TAREAS_POR_TRIPULANTE = 2;
    private static final String[] NOMBRES_SALAS =
            {"Reactor", "Cafetería", "Navegación", "Electricidad", "Armamento", "Comunicaciones"};

    public static void main(String[] args) {
        // Con "--semilla N" el reparto de roles y tareas es siempre el mismo (útil para pruebas).
        Random random = new Random();
        if (args.length == 2 && args[0].equals("--semilla")) {
            random = new Random(Long.parseLong(args[1]));
        }

        System.out.println("--- AMONG US TERMINAL ---");
        int total = Consola.leerEntero("¿Cuántos juegan hoy? (" + MIN_JUGADORES + "-" + MAX_JUGADORES + "): ",
                MIN_JUGADORES, MAX_JUGADORES);
        List<String> nombres = pedirNombres(total);

        Nave nave = crearNave(nombres, random);
        Consola.esperarEnter("\n¡Listos! Roles asignados en secreto. Pulsa Enter para que empiece la acción...");

        Partida partida = new Partida(nave, random);
        String ganador = partida.jugar();

        if (Consola.confirmar("\n¿Guardar la partida en la base de datos MySQL?")) {
            GuardadoPartida.guardar(nave, ganador);
        }
    }

    private static List<String> pedirNombres(int total) {
        List<String> nombres = new ArrayList<>();
        while (nombres.size() < total) {
            String nombre = Consola.leerLinea("Nombre del jugador " + (nombres.size() + 1) + ": ");
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
            } else if (nombres.contains(nombre)) {
                System.out.println("Ese nombre ya está en uso.");
            } else {
                nombres.add(nombre);
            }
        }
        return nombres;
    }

    /** Un impostor, un capitán y el resto repartidos entre ingenieros y médicos, al azar. */
    static Nave crearNave(List<String> nombres, Random random) {
        int total = nombres.size();
        List<String> roles = new ArrayList<>();
        roles.add("impostor");
        roles.add("capitan");
        for (int i = 0; i < total - 2; i++) {
            roles.add(i % 2 == 0 ? "ingeniero" : "medico");
        }
        Collections.shuffle(roles, random);

        ArrayList<Tripulante> tripulantes = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            String nombre = nombres.get(i);
            switch (roles.get(i)) {
                case "impostor" -> tripulantes.add(new Impostor(nombre));
                case "capitan" -> tripulantes.add(new Capitan(nombre));
                case "ingeniero" -> tripulantes.add(new Ingeniero(nombre));
                default -> tripulantes.add(new Medico(nombre));
            }
        }

        ArrayList<Sala> salas = new ArrayList<>();
        for (String nombreSala : NOMBRES_SALAS) {
            salas.add(new Sala(nombreSala));
        }

        Nave nave = new Nave(tripulantes, salas);
        nave.asignarTareas(TAREAS_POR_TRIPULANTE, random);
        return nave;
    }
}
