import java.util.ArrayList;

// El cerebro que maneja la nave, los turnos y la victoria
public class Nave {
    private ArrayList<Tripulante> tripulantes;
    private ArrayList<Sala> salas;
    private ArrayList<Tarea> tareas;

    public Nave(ArrayList<Tripulante> tripulantes, ArrayList<Sala> salas) {
        this.tripulantes = tripulantes;
        this.salas = salas;
        this.tareas = new ArrayList<>();
    }

    // Metodo para borrar la terminal (Requisito de la Pág 5)
    public void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void agregarTarea(Tarea t) {
        this.tareas.add(t);
    }

    // Reviso si ganaron los buenos (Pág 5 y 19)
    public boolean verificarVictoriaTripulantes() {
        // Victoria si todas las tareas estan al 100%
        boolean tareasListas = !tareas.isEmpty() && tareas.stream().allMatch(Tarea::isCompletada);

        // O si ya no queda ningun impostor vivo
        boolean impostorMuerto = tripulantes.stream()
                .filter(t -> t.getRol().equalsIgnoreCase("impostor"))
                .noneMatch(Tripulante::isVivo);

        return tareasListas || impostorMuerto;
    }

    public boolean verificarVictoriaImpostor() {
        long normalesVivos = tripulantes.stream()
                .filter(t -> !t.getRol().equalsIgnoreCase("impostor") && t.isVivo())
                .count();
        long malosVivos = tripulantes.stream()
                .filter(t -> t.getRol().equalsIgnoreCase("impostor") && t.isVivo())
                .count();

        return malosVivos >= normalesVivos;
    }

    // Getters para que el Main pueda cotillear las listas
    public ArrayList<Tripulante> getTripulantes() { return tripulantes; }
    public ArrayList<Sala> getSalas() { return salas; }
}