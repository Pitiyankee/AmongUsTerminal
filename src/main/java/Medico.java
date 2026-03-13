// Este es el medico, casi igual que el ingeniero pero con la habilidad de revisar a la gente
public class Medico extends Tripulante {

    // le pongo su rol directo de medico al crearlo
    public Medico(String nombre) {
        super(nombre, "medico");
    }

    // el texto de su habilidad que sale en la terminal
    @Override
    public void habilidadEspecial() {
        System.out.println("Puedo revisar a alguien para ver si es el impostor.");
    }

    // aqui reviso a otro jugador. Farid pidio que diga si es impostor o no
    public void examinar(Tripulante sospechoso) {
        if (sospechoso.getRol().equals("impostor")) {
            System.out.println("¡Cuidado! " + sospechoso.getNombre() + " ES EL IMPOSTOR!");
        } else {
            System.out.println(sospechoso.getNombre() + " es de los buenos, todo cool.");
        }
    }
}
