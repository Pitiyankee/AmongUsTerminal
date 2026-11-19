/** El médico de la nave: puede examinar a un jugador para saber si es el impostor. */
public class Medico extends Tripulante {

    public Medico(String nombre) {
        super(nombre, "medico");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puedo examinar a un jugador para saber si es el impostor.");
    }

    public void examinar(Tripulante sospechoso) {
        if (sospechoso.getRol().equals("impostor")) {
            System.out.println("¡Cuidado! " + sospechoso.getNombre() + " ES EL IMPOSTOR.");
        } else {
            System.out.println(sospechoso.getNombre() + " no es el impostor.");
        }
    }
}
