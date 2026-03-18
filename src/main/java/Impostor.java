/**
 * Clase que representa al Impostor en el juego.
 * Hereda de Tripulante e implementa la interfaz Saboteable.
 */
public class Impostor extends Tripulante implements Saboteable {

    public Impostor(String nombre) {
        super(nombre, "Impostor");
    }

    @Override
    public void realizarTarea(Tarea tarea) {
        // El impostor no hace tareas, solo finge que las hace
        System.out.println(getNombre() + " dice: Estoy fingiendo hacer la tarea: " + tarea.getDescripcion() + " para que no sospechen...");
    }

    @Override
    public void sabotear(Sala sala) {
        System.out.println("¡SABOTAJE! El impostor " + getNombre() + " ha saboteado la sala: " + sala.getNombre());
    }

    @Override
    public void habilidadEspecial() {
        System.out.println(getNombre() + " usa su habilidad especial: ¡Se mete por las alcantarillas!");
    }

    @Override
    public String toString() {
        return "Impostor: " + getNombre() + " (Estado: " + (isVivo() ? "Vivo" : "Eliminado") + ")";
    }
}