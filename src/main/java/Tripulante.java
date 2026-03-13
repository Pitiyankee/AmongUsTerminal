// le pongo la palabra abstract porque esta clase es solo un molde general
// a partir de aqui sacare al ingeniero, al medico y al impostor
public abstract class Tripulante {

    // este es el numero que identifica a mi jugador en la base de datos
    private int id;

    // aqui guardo el nombre del jugador
    private String nombre;

    // aqui guardo si es ingeniero, medico, capitan o impostor
    private String rol;

    // esto me sirve para saber si el jugador sigue vivo o si el impostor lo elimino
    private boolean vivo;

    // este es mi constructor, lo uso para darle nombre y rol al jugador cuando empieza el juego
    public Tripulante(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
        // todos los jugadores empiezan vivos por defecto al iniciar la partida
        this.vivo = true;
    }

    // a partir de aqui hago mis getters y setters para poder leer y cambiar los datos del jugador
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

    // este es especial, solo me devuelve el rol que tiene el jugador
    public String getRol() {
        return rol;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    // esta es la habilidad especial de cada jugador.
    // le pongo abstract sin codigo adentro porque cada rol la hara de forma diferente
    public abstract void habilidadEspecial();

    // uso esto para imprimir los datos del jugador de forma bonita en la pantalla negra
    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Rol: " + rol + " | Vivo: " + vivo;
    }
}
