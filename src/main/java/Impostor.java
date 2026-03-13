// Hola Carlos, aqui te dejo al Impostor
// Farid nos pide que implemente Saboteable y use Tareas, pero como tu eres el
// que va a crear esos archivos, te lo dejo comentado para que mi programa
// no de error en rojo. Cuando tu subas tu parte, le quitamos las barras y listo!
public class Impostor extends Tripulante /* implements Saboteable */ {

    public Impostor(String nombre) {
        // Le pasamos el nombre y le clavamos el rol de impostor directo
        super(nombre, "impostor");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puedo sabotear salas y eliminar a otros en secreto... shhh.");
    }

    // Metodo para eliminar a otro jugador (Farid dice que no me puedo eliminar a mi mismo)
    public void eliminar(Tripulante victima) {
        if (this.getId() == victima.getId()) {
            System.out.println("No me puedo eliminar a mi mismo, seria super tonto.");
        } else {
            victima.setVivo(false);
            System.out.println("He eliminado a " + victima.getNombre() + " sin que nadie se entere.");
        }
    }

    // Como Farid pide que el impostor finja hacer tareas, sobrescribo aqui.
    // OJO: Carlos, la variable Tarea la deje comentada para que no me de error hasta que tu la crees.
    public void realizarTarea(/*Tarea tarea*/) {
        System.out.println("Estoy fingiendo hacer una tarea en los paneles para que no sospechen...");
    }

    // Carlos, este es el metodo de la interfaz Saboteable que haras tu.
    // Lo dejo apagado por ahora.
    /*
    public void sabotear(Sala sala) {
        sala.setSaboteada(true);
        System.out.println("Acabo de sabotear la sala " + sala.getNombre() + " muahaha.");
    }
    */
}
