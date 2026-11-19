/** Una misión que un tripulante debe completar en una sala de la nave. */
public class Tarea {

    private int id;
    private String descripcion;
    private boolean completada;
    private Tripulante tripulanteAsignado;
    private Sala sala;

    public Tarea(String descripcion, Tripulante tripulanteAsignado, Sala sala) {
        this.descripcion = descripcion;
        this.tripulanteAsignado = tripulanteAsignado;
        this.sala = sala;
        this.completada = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }

    public Tripulante getTripulanteAsignado() { return tripulanteAsignado; }
    public void setTripulanteAsignado(Tripulante tripulanteAsignado) { this.tripulanteAsignado = tripulanteAsignado; }

    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }

    @Override
    public String toString() {
        return descripcion + " (" + (sala != null ? sala.getNombre() : "sin sala") + ")"
                + " - " + (completada ? "Hecha" : "Pendiente");
    }
}
