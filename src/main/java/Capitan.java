// El capitan de la nave, el que manda a votar cuando hay emergencia
public class Capitan extends Tripulante {

    public Capitan(String nombre) {
        super(nombre, "capitan");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puedo llamar a todos para votar super rapido.");
    }

    // Nota para Carlos: Farid dice que esto llama a la votacion de la nave.
    // OJO: Le quite la variable Nave por ahora para que no me de error rojo porque tu no has creado esa clase todavia.
    // Cuando la crees, le quitamos las dos barritas (//)
    public void convocarVotacion(/*Nave nave*/) {
        System.out.println("¡Reunion de emergencia! ¡A votar!");
        // nave.iniciarVotacion();
    }
}
