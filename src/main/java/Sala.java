public class Sala {

    // este es el numero que identifica a mi sala en la base de datos
    private int id;

    // aqui guardo el nombre de la sala, por ejemplo Reactor o Cafeteria
    private String nombre;

    // esto me dice si el impostor ha roto la sala. si es true, esta saboteada
    private boolean saboteada;

    // este es mi constructor para crear la sala solo con el nombre
    // por defecto le digo que no esta saboteada
    public Sala(String nombre) {
        this.nombre = nombre;
        this.saboteada = false;
    }

    // este es otro constructor que necesito para cuando traiga los datos completos de la base de datos
    public Sala(int id, String nombre, boolean saboteada) {
        this.id = id;
        this.nombre = nombre;
        this.saboteada = saboteada;
    }

    // a partir de aqui hago los getters y setters para poder leer y cambiar mis variables
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

    public boolean isSaboteada() {
        return saboteada;
    }

    public void setSaboteada(boolean saboteada) {
        this.saboteada = saboteada;
    }

    // uso esto para imprimir los datos de la sala de forma bonita en la pantalla
    @Override
    public String toString() {
        return "Sala: " + nombre + " | Saboteada: " + saboteada;
    }
}