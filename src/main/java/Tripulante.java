/**
 * Carlos, esta es la clase madre. 
 * He añadido 'implements Trabajable, Votable' para cumplir con lo que 
 * Farid puso en el diagrama de clases del PDF.
 */
public abstract class Tripulante implements Trabajable, Votable {

    // Atributos obligatorios segun el PDF
    private int id;
    private String nombre;
    private String rol;
    private boolean vivo;

    // Constructor que inicializa el nombre y el rol, y activa el true
    public Tripulante(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.vivo = true;
    }

    // Getters y Setters que pide farid
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    // Metodo de la interfaz
    @Override
    public void realizarTarea(Tarea tarea) {
        if (this.vivo && tarea.getTripulanteAsignado().equals(this)) {
            tarea.setCompletada(true);
            System.out.println(nombre + " ha completado la tarea: " + tarea.getDescripcion());
        } else {
            System.out.println(nombre + " no puede realizar esta tarea.");
        }
    }

    // Metodo de la interfaz Votable
    @Override
    public void votar(Tripulante sospechoso) {
        if (this.vivo) {
            System.out.println(nombre + " vota a " + sospechoso.getNombre() + " para que lo echen de la nave.");
        }
    }

    // Metodo abstracto que cada rol (Ingeniero, Medico, etc, etc etc.) hara a su manera
    public abstract void habilidadEspecial();

    // Representacion de los string que tambien farid pide (revisalos en el PDF)
    @Override
    public String toString() {
        String estado = vivo ? "VIVO" : "ELIMINADO";
        return "ID: " + id + " | " + nombre + " (" + rol + ") - Estado: " + estado;
    }
}