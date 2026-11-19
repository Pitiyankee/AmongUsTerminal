import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Desarrollo de una partida por turnos: cada jugador vivo realiza una acción en su turno,
 * y hay reuniones de votación cuando se encuentra un cuerpo o el capitán las convoca.
 */
public class Partida {

    public static final String GANAN_TRIPULANTES = "TRIPULANTES";
    public static final String GANA_IMPOSTOR = "IMPOSTOR";

    private final Nave nave;
    private final Random random;
    private final List<Tripulante> eliminadosEnRonda = new ArrayList<>();
    private int ronda = 1;
    private String ganador = null;

    public Partida(Nave nave, Random random) {
        this.nave = nave;
        this.random = random;
    }

    /** Juega la partida hasta que haya un ganador y lo devuelve. */
    public String jugar() {
        while (ganador == null) {
            jugarRonda();
        }
        mostrarResultado();
        return ganador;
    }

    private void jugarRonda() {
        eliminadosEnRonda.clear();

        for (Tripulante jugador : new ArrayList<>(nave.getTripulantes())) {
            if (!jugador.isVivo()) {
                continue;
            }
            turno(jugador);
            if (comprobarFinal()) {
                return;
            }
        }

        if (!eliminadosEnRonda.isEmpty()) {
            String nombres = eliminadosEnRonda.get(0).getNombre();
            reunion("Se ha encontrado el cuerpo de " + nombres + ". ¡Reunión de emergencia!");
            if (comprobarFinal()) {
                return;
            }
        }
        ronda++;
    }

    private boolean comprobarFinal() {
        if (nave.verificarVictoriaTripulantes()) {
            ganador = GANAN_TRIPULANTES;
        } else if (nave.verificarVictoriaImpostor()) {
            ganador = GANA_IMPOSTOR;
        }
        return ganador != null;
    }

    // ---------------------------------------------------------------- turnos

    private void turno(Tripulante jugador) {
        nave.limpiarPantalla();
        System.out.println("=== RONDA " + ronda + " ===");
        System.out.println("¡Turno de " + jugador.getNombre() + "!");
        Consola.esperarEnter("Pasa el ordenador al jugador y pulsa Enter...");

        nave.limpiarPantalla();
        System.out.println("=== RONDA " + ronda + " · " + jugador.getNombre() + " ===");
        System.out.println("TU ROL ES: " + jugador.getRol().toUpperCase());
        jugador.habilidadEspecial();

        boolean turnoUsado = false;
        while (!turnoUsado) {
            mostrarEstado(jugador);
            if (jugador instanceof Impostor impostor) {
                turnoUsado = menuImpostor(impostor);
            } else {
                turnoUsado = menuTripulante(jugador);
            }
        }
        Consola.esperarEnter("Pulsa Enter para ocultar la pantalla...");
    }

    private void mostrarEstado(Tripulante jugador) {
        System.out.println();
        System.out.println("Tareas del equipo completadas: " + nave.contarTareasCompletadas()
                + "/" + nave.getTareas().size());
        List<Sala> saboteadas = nave.getSalasSaboteadas();
        System.out.println("Salas saboteadas: " + (saboteadas.isEmpty() ? "ninguna" : saboteadas));

        if (!(jugador instanceof Impostor)) {
            List<Tarea> pendientes = nave.getTareasPendientes(jugador);
            System.out.println("Tus tareas pendientes: " + (pendientes.isEmpty() ? "ninguna" : ""));
            for (Tarea tarea : pendientes) {
                String bloqueo = tarea.getSala().isSaboteada() ? "  [BLOQUEADA: sala saboteada]" : "";
                System.out.println("  - " + tarea.getDescripcion() + " (" + tarea.getSala().getNombre() + ")" + bloqueo);
            }
        }
        System.out.println();
    }

    /** Menú de tripulante. Devuelve true si el jugador ha gastado su turno. */
    private boolean menuTripulante(Tripulante jugador) {
        System.out.println("1. Hacer una tarea");
        System.out.println("2. Usar mi habilidad (" + descripcionHabilidad(jugador) + ")");
        System.out.println("3. Pasar turno");
        int opcion = Consola.leerEntero("Elige una opción: ", 1, 3);

        switch (opcion) {
            case 1:
                return hacerTarea(jugador);
            case 2:
                return usarHabilidad(jugador);
            default:
                return true;
        }
    }

    private String descripcionHabilidad(Tripulante jugador) {
        if (jugador instanceof Capitan) {
            return "convocar reunión";
        }
        if (jugador instanceof Ingeniero) {
            return "reparar una sala";
        }
        return "examinar a un jugador";
    }

    private boolean hacerTarea(Tripulante jugador) {
        List<Tarea> pendientes = nave.getTareasPendientes(jugador);
        if (pendientes.isEmpty()) {
            System.out.println("No te quedan tareas pendientes.");
            return false;
        }

        for (int i = 0; i < pendientes.size(); i++) {
            System.out.println((i + 1) + ". " + pendientes.get(i));
        }
        int opcion = Consola.leerEntero("Elige la tarea (0 para volver): ", 0, pendientes.size());
        if (opcion == 0) {
            return false;
        }

        Tarea tarea = pendientes.get(opcion - 1);
        jugador.realizarTarea(tarea);
        return tarea.isCompletada();
    }

    private boolean usarHabilidad(Tripulante jugador) {
        if (jugador instanceof Capitan capitan) {
            capitan.convocarVotacion();
            reunion(capitan.getNombre() + " ha convocado una reunión de emergencia.");
            return true;
        }
        if (jugador instanceof Ingeniero ingeniero) {
            return repararSala(ingeniero);
        }
        return examinar((Medico) jugador);
    }

    private boolean repararSala(Ingeniero ingeniero) {
        List<Sala> saboteadas = nave.getSalasSaboteadas();
        if (saboteadas.isEmpty()) {
            System.out.println("No hay ninguna sala saboteada.");
            return false;
        }

        for (int i = 0; i < saboteadas.size(); i++) {
            System.out.println((i + 1) + ". " + saboteadas.get(i).getNombre());
        }
        int opcion = Consola.leerEntero("Elige la sala a reparar (0 para volver): ", 0, saboteadas.size());
        if (opcion == 0) {
            return false;
        }
        ingeniero.repararSala(saboteadas.get(opcion - 1));
        return true;
    }

    private boolean examinar(Medico medico) {
        List<Tripulante> otros = otrosVivos(medico);
        for (int i = 0; i < otros.size(); i++) {
            System.out.println((i + 1) + ". " + otros.get(i).getNombre());
        }
        int opcion = Consola.leerEntero("¿A quién examinas? (0 para volver): ", 0, otros.size());
        if (opcion == 0) {
            return false;
        }
        medico.examinar(otros.get(opcion - 1));
        return true;
    }

    /** Menú del impostor. Devuelve true si ha gastado su turno. */
    private boolean menuImpostor(Impostor impostor) {
        System.out.println("1. Fingir una tarea");
        System.out.println("2. Sabotear una sala");
        System.out.println("3. Eliminar a un tripulante");
        System.out.println("4. Pasar turno");
        int opcion = Consola.leerEntero("Elige una opción: ", 1, 4);

        switch (opcion) {
            case 1:
                return fingirTarea(impostor);
            case 2:
                return sabotear(impostor);
            case 3:
                return eliminar(impostor);
            default:
                return true;
        }
    }

    private boolean fingirTarea(Impostor impostor) {
        List<Tarea> pendientes = new ArrayList<>();
        for (Tarea tarea : nave.getTareas()) {
            if (!tarea.isCompletada()) {
                pendientes.add(tarea);
            }
        }
        if (pendientes.isEmpty()) {
            System.out.println("No quedan tareas que fingir.");
            return false;
        }
        impostor.realizarTarea(pendientes.get(random.nextInt(pendientes.size())));
        return true;
    }

    private boolean sabotear(Impostor impostor) {
        List<Sala> libres = new ArrayList<>();
        for (Sala sala : nave.getSalas()) {
            if (!sala.isSaboteada()) {
                libres.add(sala);
            }
        }
        if (libres.isEmpty()) {
            System.out.println("Todas las salas ya están saboteadas.");
            return false;
        }

        for (int i = 0; i < libres.size(); i++) {
            System.out.println((i + 1) + ". " + libres.get(i).getNombre());
        }
        int opcion = Consola.leerEntero("Elige la sala a sabotear (0 para volver): ", 0, libres.size());
        if (opcion == 0) {
            return false;
        }
        impostor.sabotear(libres.get(opcion - 1));
        return true;
    }

    private boolean eliminar(Impostor impostor) {
        List<Tripulante> victimas = otrosVivos(impostor);
        for (int i = 0; i < victimas.size(); i++) {
            System.out.println((i + 1) + ". " + victimas.get(i).getNombre());
        }
        int opcion = Consola.leerEntero("¿A quién eliminas? (0 para volver): ", 0, victimas.size());
        if (opcion == 0) {
            return false;
        }

        Tripulante victima = victimas.get(opcion - 1);
        impostor.eliminar(victima);
        eliminadosEnRonda.add(victima);
        return true;
    }

    private List<Tripulante> otrosVivos(Tripulante jugador) {
        List<Tripulante> otros = new ArrayList<>(nave.getVivos());
        otros.remove(jugador);
        return otros;
    }

    // ------------------------------------------------------------- votación

    /** Reunión: cada jugador vivo vota en secreto a un sospechoso o salta el voto. */
    private void reunion(String motivo) {
        nave.limpiarPantalla();
        System.out.println("=== REUNIÓN ===");
        System.out.println(motivo);
        Consola.esperarEnter("Pulsa Enter para empezar la votación...");

        List<Tripulante> votos = new ArrayList<>();
        for (Tripulante votante : nave.getVivos()) {
            nave.limpiarPantalla();
            System.out.println("Votación: turno de " + votante.getNombre());
            Consola.esperarEnter("Pasa el ordenador y pulsa Enter...");

            List<Tripulante> candidatos = otrosVivos(votante);
            for (int i = 0; i < candidatos.size(); i++) {
                System.out.println((i + 1) + ". " + candidatos.get(i).getNombre());
            }
            int opcion = Consola.leerEntero("¿A quién votas? (0 para saltar el voto): ", 0, candidatos.size());

            if (opcion == 0) {
                System.out.println("Has saltado tu voto.");
                votos.add(null);
            } else {
                Tripulante elegido = candidatos.get(opcion - 1);
                votante.votar(elegido);
                votos.add(elegido);
            }
            Consola.esperarEnter("Pulsa Enter para ocultar tu voto...");
        }

        nave.limpiarPantalla();
        System.out.println("=== RESULTADO DE LA VOTACIÓN ===");
        Tripulante expulsado = nave.resolverVotacion(votos);
        if (expulsado == null) {
            System.out.println("Nadie ha sido expulsado.");
        } else {
            expulsado.setVivo(false);
            String esImpostor = expulsado.getRol().equals("impostor")
                    ? " ERA el impostor." : " NO era el impostor.";
            System.out.println(expulsado.getNombre() + " ha sido expulsado de la nave." + esImpostor);
        }
        Consola.esperarEnter("Pulsa Enter para continuar...");
    }

    // ------------------------------------------------------------ resultado

    private void mostrarResultado() {
        nave.limpiarPantalla();
        System.out.println("=== FIN DE LA PARTIDA ===");
        if (ganador.equals(GANAN_TRIPULANTES)) {
            System.out.println("¡Ganan los TRIPULANTES!");
        } else {
            System.out.println("¡Gana el IMPOSTOR!");
        }
        System.out.println();
        System.out.println("Jugadores:");
        for (Tripulante t : nave.getTripulantes()) {
            System.out.println("  " + t);
        }
        System.out.println();
        System.out.println("Tareas completadas: " + nave.contarTareasCompletadas() + "/" + nave.getTareas().size());
        System.out.println("Rondas jugadas: " + ronda);
    }

    public String getGanador() {
        return ganador;
    }
}
