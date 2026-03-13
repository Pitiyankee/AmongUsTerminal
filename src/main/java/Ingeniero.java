// Carlos, bro, aqui hago al ingeniero que hereda del molde Tripulante que hice antes
public class Ingeniero extends Tripulante {

    // constructor facilito: le paso el nombre y ya le clavo el rol de ingeniero para no liarme
    public Ingeniero(String nombre) {
        super(nombre, "ingeniero");
    }

    // Farid nos pide que pongamos que hace cada uno, esto solo imprime texto en la pantalla negra
    @Override
    public void habilidadEspecial() {
        System.out.println("Puedo reparar las salas rotas!");
    }

    // Si la sala esta saboteada (true), la pongo en arreglada (false)
    public void repararSala(Sala sala) {
        if (sala.isSaboteada()) {
            sala.setSaboteada(false);
            System.out.println("Listo, ya arregle la sala " + sala.getNombre());
        } else {
            System.out.println("Esta sala ya esta bien, no toco nada para no romperla.");
        }
    }
}