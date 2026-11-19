/**
 * Clase base de todos los jugadores de la nave.
 * Cada rol (Capitán, Ingeniero, Médico, Impostor) hereda de ella y define su habilidad especial.
 */
public abstract class Tripulante implements Trabajable, Votable {

    private int id;
    private String nombre;
    private String rol;
    private boolean vivo;

    public Tripulante(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.vivo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    /**
     * Completa la tarea si el tripulante está vivo, la tarea es suya, no está hecha
     * y su sala no está saboteada.
     */
    @Override
    public void realizarTarea(Tarea tarea) {
        if (!vivo || tarea.getTripulanteAsignado() != this) {
            System.out.println(nombre + " no puede realizar esta tarea.");
        } else if (tarea.isCompletada()) {
            System.out.println("La tarea \"" + tarea.getDescripcion() + "\" ya está completada.");
        } else if (tarea.getSala() != null && tarea.getSala().isSaboteada()) {
            System.out.println("La sala " + tarea.getSala().getNombre()
                    + " está saboteada. Hace falta un ingeniero que la repare antes de seguir.");
        } else {
            tarea.setCompletada(true);
            System.out.println(nombre + " ha completado la tarea: " + tarea.getDescripcion());
        }
    }

    /** El voto es secreto: solo se informa de que el jugador ha votado, no de a quién. */
    @Override
    public void votar(Tripulante sospechoso) {
        if (vivo) {
            System.out.println("Voto registrado.");
        }
    }

    /** Cada rol describe y ejecuta su habilidad a su manera. */
    public abstract void habilidadEspecial();

    @Override
    public String toString() {
        String estado = vivo ? "VIVO" : "ELIMINADO";
        return nombre + " (" + rol + ") - " + estado;
    }
}
