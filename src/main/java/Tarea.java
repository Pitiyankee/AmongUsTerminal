/**
 * Carlos, aqui te dejo las clases que representa las misiones de la nave.
 * El diagrama UML dice que cada tarea necesita saber en que sala se hace
 * y quien la tiene asignada.
 */
public class Tarea {
    // Atributos obligatorios segun el PDF
    private int id;
    private String descripcion;
    private boolean completada;
    private Tripulante tripulanteAsignado;
    private Sala sala;

    // Constructor completo para que los DAOs funcionen bien (igual revisa esto porque aun me enrredo)
    public Tarea(String descripcion, Tripulante tripulanteAsignado, Sala sala) {
        this.descripcion = descripcion;
        this.tripulanteAsignado = tripulanteAsignado;
        this.sala = sala;
        this.completada = false;
    }

    // Getters y Setters necesarios para que Tripulante no de error
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
        return "Tarea: " + descripcion + " | Sala: " + (sala != null ? sala.getNombre() : "Sin sala") + " | Estado: " + (completada ? "Hecha" : "Pendiente");
    }
}