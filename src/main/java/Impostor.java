/** El impostor: finge hacer tareas, sabotea salas y elimina tripulantes en secreto. */
public class Impostor extends Tripulante implements Saboteable {

    public Impostor(String nombre) {
        super(nombre, "impostor");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puedo sabotear salas y eliminar tripulantes sin que nadie lo sepa.");
    }

    /** Elimina a un jugador que siga vivo. No puede eliminarse a sí mismo. */
    public void eliminar(Tripulante victima) {
        if (this == victima) {
            System.out.println("No puedes eliminarte a ti mismo.");
        } else if (!victima.isVivo()) {
            System.out.println(victima.getNombre() + " ya está eliminado.");
        } else {
            victima.setVivo(false);
            System.out.println("Has eliminado a " + victima.getNombre() + " sin que nadie lo vea.");
        }
    }

    /** El impostor no completa tareas: solo aparenta hacerlas para no levantar sospechas. */
    @Override
    public void realizarTarea(Tarea tarea) {
        System.out.println("Finges hacer la tarea \"" + tarea.getDescripcion() + "\" para que nadie sospeche.");
    }

    @Override
    public void sabotear(Sala sala) {
        sala.setSaboteada(true);
        System.out.println("Has saboteado la sala " + sala.getNombre() + ".");
    }
}
