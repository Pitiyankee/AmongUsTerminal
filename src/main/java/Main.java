import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Mensaje inicial para que se vea bien el juego
        System.out.println("--- AMONG US TERMINAL ---");

        // Registro de los jugadores (Min 4, Max 10)
        System.out.print("¿Cuantos juegan hoy? (4-10): ");
        int total = sc.nextInt();
        sc.nextLine();

        ArrayList<String> nombres = new ArrayList<>();
        for (int i = 1; i <= total; i++) {
            System.out.print("Nombre del jugador " + i + ": ");
            nombres.add(sc.nextLine());
        }

        // Reparto los roles al azar para que nadie sepa quien es quien
        ArrayList<String> roles = new ArrayList<>();
        roles.add("impostor");
        roles.add("capitan");
        for (int i = 0; i < total - 2; i++) {
            roles.add(i % 2 == 0 ? "ingeniero" : "medico");
        }
        Collections.shuffle(roles);

        // Creamos la lista de tripulantes segun el rol que les toco
        ArrayList<Tripulante> lista = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            String r = roles.get(i);
            if (r.equals("impostor")) lista.add(new Impostor(nombres.get(i)));
            else if (r.equals("capitan")) lista.add(new Capitan(nombres.get(i)));
            else if (r.equals("ingeniero")) lista.add(new Ingeniero(nombres.get(i)));
            else lista.add(new Medico(nombres.get(i)));
        }

        // Metemos las salas minimas que pide Farid
        ArrayList<Sala> salas = new ArrayList<>();
        String[] nombresSalas = {"Reactor", "Cafeteria", "Navegacion", "Electricidad", "Armamento", "Comunicaciones"};
        for (String s : nombresSalas) salas.add(new Sala(s));

        // Metemos todo en la nave para que empiece la magia
        Nave nave = new Nave(lista, salas);

        System.out.println("\n¡Listos! Roles asignados. Pulsa Enter para que empiece la accion...");
        sc.nextLine();

        // Bucle principal de turnos
        boolean fin = false;
        while (!fin) {
            for (Tripulante t : lista) {
                if (t.isVivo()) {
                    nave.limpiarPantalla();
                    System.out.println("¡Turno de " + t.getNombre() + "!");
                    System.out.println("Pasa el PC al jugador y pulsa Enter...");
                    sc.nextLine();

                    System.out.println("TU ROL ES: " + t.getRol().toUpperCase());
                    System.out.println("\n[1] Pasar turno");
                    sc.nextLine();

                    // Revisamos si alguien ya gano
                    if (nave.verificarVictoriaTripulantes() || nave.verificarVictoriaImpostor()) {
                        fin = true;
                        break;
                    }
                }
            }
        }
        System.out.println("¡Partida terminada!");
    }
}