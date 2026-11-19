/** El capitán de la nave: puede convocar una reunión de emergencia para votar. */
public class Capitan extends Tripulante {

    public Capitan(String nombre) {
        super(nombre, "capitan");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puedo convocar una reunión de emergencia para que todos voten.");
    }

    /** Anuncia la reunión. La votación en sí la gestiona la partida. */
    public void convocarVotacion() {
        System.out.println("¡Reunión de emergencia! ¡A votar!");
    }
}
