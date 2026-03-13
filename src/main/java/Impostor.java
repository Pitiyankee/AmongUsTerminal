// ¡Listo Carlos! Como ya subiste tus archivos, ya puedo activar todo
// sin que Farid vea errores rojos en mi pantalla.
public class Impostor extends Tripulante implements Saboteable {

    public Impostor(String nombre) {
        super(nombre, "impostor");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puedo sabotear salas y eliminar a otros en secreto... shhh.");
    }

    public void eliminar(Tripulante victima) {
        if (this.getId() == victima.getId()) {
            System.out.println("No me puedo eliminar a mi mismo, seria super tonto.");
        } else {
            victima.setVivo(false);
            System.out.println("He eliminado a " + victima.getNombre() + " sin que nadie se entere.");
        }
    }

    // Ya quité las barras porque ya existe la clase Tarea que hiciste tú
    public void realizarTarea(Tarea tarea) {
        System.out.println("Estoy fingiendo hacer la tarea: " + tarea.getNombre() + " para que no sospechen, pero estoy tramando algo...");
    }

    @Override
    public void sabotear(Sala sala) {
        sala.setSaboteada(true);
        System.out.println("Acabo de sabotear la sala, no saben de lo que soy capaz" + sala.getNombre() + " JAJAJAJA.");
    }
}
