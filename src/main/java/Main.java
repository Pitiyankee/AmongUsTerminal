public class Main {
    public static void main(String[] args) {
        System.out.println("--- BIENVENIDOS A AMONG US TERMINAL BY PEDRO PITIYANKEE Y CARLOS ZULETA ---");

        // Creamos una sala y un impostor para probar
        Sala salaMaquinas = new Sala("Maquinas");
        Impostor traidor = new Impostor("Pedro Pitiyankee");

        // Probamos las habilidades
        traidor.habilidadEspecial();
        traidor.sabotear(salaMaquinas);

        System.out.println("Estado de la sala: " + (salaMaquinas.isSaboteada() ? "SABOTEADA" : "OK"));
    }
}