// Aqui esta lista la clase Tarea, Pedro. Farid la pedia para que los tripulantes
// (y tu impostor fingiendo) tengan algo que hacer en las salas.
public class Tarea {

    private String nombre;

    private boolean completada;

    public Tarea(String nombre) {
        this.nombre = nombre;

        this.completada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void completarTarea() {
        this.completada = true;
        System.out.println("La tarea '" + this.nombre + "' ya esta lista. ¡Un paso mas para ganar!");
    }
}