import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** La nave: guarda a los tripulantes, las salas y las tareas, y decide quién gana. */
public class Nave {

    private static final String[] DESCRIPCIONES_TAREAS = {
            "Calibrar los motores", "Descargar los datos", "Limpiar el filtro de oxígeno",
            "Alinear el telescopio", "Reiniciar el escudo", "Revisar el cableado",
            "Recargar el combustible", "Ajustar el reactor", "Vaciar la basura", "Escanear la tarjeta"
    };

    private final ArrayList<Tripulante> tripulantes;
    private final ArrayList<Sala> salas;
    private final ArrayList<Tarea> tareas;

    public Nave(ArrayList<Tripulante> tripulantes, ArrayList<Sala> salas) {
        this.tripulantes = tripulantes;
        this.salas = salas;
        this.tareas = new ArrayList<>();
    }

    /** Borra la terminal usando secuencias ANSI. */
    public void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void agregarTarea(Tarea t) {
        this.tareas.add(t);
    }

    /** Reparte a cada tripulante (menos al impostor) tareas distintas en salas al azar. */
    public void asignarTareas(int tareasPorTripulante, Random random) {
        for (Tripulante t : tripulantes) {
            if (t.getRol().equals("impostor")) {
                continue;
            }
            List<String> descripciones = new ArrayList<>(List.of(DESCRIPCIONES_TAREAS));
            Collections.shuffle(descripciones, random);
            for (int i = 0; i < tareasPorTripulante; i++) {
                Sala sala = salas.get(random.nextInt(salas.size()));
                agregarTarea(new Tarea(descripciones.get(i), t, sala));
            }
        }
    }

    public List<Tripulante> getVivos() {
        List<Tripulante> vivos = new ArrayList<>();
        for (Tripulante t : tripulantes) {
            if (t.isVivo()) {
                vivos.add(t);
            }
        }
        return vivos;
    }

    public List<Tarea> getTareasPendientes(Tripulante tripulante) {
        List<Tarea> pendientes = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.getTripulanteAsignado() == tripulante && !tarea.isCompletada()) {
                pendientes.add(tarea);
            }
        }
        return pendientes;
    }

    public List<Sala> getSalasSaboteadas() {
        List<Sala> saboteadas = new ArrayList<>();
        for (Sala sala : salas) {
            if (sala.isSaboteada()) {
                saboteadas.add(sala);
            }
        }
        return saboteadas;
    }

    public long contarTareasCompletadas() {
        return tareas.stream().filter(Tarea::isCompletada).count();
    }

    /** Ganan los tripulantes si todas las tareas están hechas o si ya no queda ningún impostor vivo. */
    public boolean verificarVictoriaTripulantes() {
        boolean tareasListas = !tareas.isEmpty() && tareas.stream().allMatch(Tarea::isCompletada);

        boolean impostorMuerto = tripulantes.stream()
                .filter(t -> t.getRol().equalsIgnoreCase("impostor"))
                .noneMatch(Tripulante::isVivo);

        return tareasListas || impostorMuerto;
    }

    /** Gana el impostor cuando hay tantos impostores vivos como tripulantes normales (o más). */
    public boolean verificarVictoriaImpostor() {
        long normalesVivos = tripulantes.stream()
                .filter(t -> !t.getRol().equalsIgnoreCase("impostor") && t.isVivo())
                .count();
        long malosVivos = tripulantes.stream()
                .filter(t -> t.getRol().equalsIgnoreCase("impostor") && t.isVivo())
                .count();

        return malosVivos >= normalesVivos;
    }

    /**
     * Cuenta los votos de una reunión. Un voto nulo significa "saltar".
     * Devuelve al jugador expulsado, o null si hay empate o si saltar tiene tantos votos
     * como el más votado.
     */
    public Tripulante resolverVotacion(List<Tripulante> votos) {
        Map<Tripulante, Integer> recuento = new HashMap<>();
        int saltos = 0;
        for (Tripulante voto : votos) {
            if (voto == null) {
                saltos++;
            } else {
                recuento.merge(voto, 1, Integer::sum);
            }
        }

        int maximo = 0;
        Tripulante masVotado = null;
        boolean empate = false;
        for (Map.Entry<Tripulante, Integer> entrada : recuento.entrySet()) {
            if (entrada.getValue() > maximo) {
                maximo = entrada.getValue();
                masVotado = entrada.getKey();
                empate = false;
            } else if (entrada.getValue() == maximo) {
                empate = true;
            }
        }

        if (masVotado == null || empate || saltos >= maximo) {
            return null;
        }
        return masVotado;
    }

    public ArrayList<Tripulante> getTripulantes() { return tripulantes; }
    public ArrayList<Sala> getSalas() { return salas; }
    public ArrayList<Tarea> getTareas() { return tareas; }
}
