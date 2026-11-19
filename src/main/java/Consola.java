import java.io.InputStream;
import java.util.Scanner;

/** Entrada por teclado compartida por todo el juego, con validación de lo que escribe el usuario. */
public final class Consola {

    private static Scanner entrada = new Scanner(System.in);

    private Consola() {
    }

    /** Cambia el origen de la entrada (se usa en las pruebas automáticas). */
    public static void usarEntrada(InputStream flujo) {
        entrada = new Scanner(flujo);
    }

    public static String leerLinea(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }

    /** Pide un número entero entre min y max (ambos incluidos) hasta que sea válido. */
    public static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            String texto = leerLinea(mensaje);
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= min && valor <= max) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // se vuelve a pedir
            }
            System.out.println("Opción no válida. Escribe un número entre " + min + " y " + max + ".");
        }
    }

    public static void esperarEnter(String mensaje) {
        System.out.print(mensaje);
        entrada.nextLine();
    }

    public static boolean confirmar(String mensaje) {
        while (true) {
            String texto = leerLinea(mensaje + " (s/n): ").toLowerCase();
            if (texto.equals("s")) {
                return true;
            }
            if (texto.equals("n")) {
                return false;
            }
        }
    }
}
