/** El ingeniero de la nave: es el único que puede reparar las salas saboteadas. */
public class Ingeniero extends Tripulante {

    public Ingeniero(String nombre) {
        super(nombre, "ingeniero");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puedo reparar las salas saboteadas.");
    }

    /** Si la sala está saboteada la deja reparada; si no, no hace nada. */
    public void repararSala(Sala sala) {
        if (sala.isSaboteada()) {
            sala.setSaboteada(false);
            System.out.println("Listo, he reparado la sala " + sala.getNombre() + ".");
        } else {
            System.out.println("La sala " + sala.getNombre() + " ya está en buen estado.");
        }
    }
}
