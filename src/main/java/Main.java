public class Main {
    public static void main(String[] args) {
        System.out.println("--- 🚀 BIENVENIDOS A AMONG US TERMINAL BY PITIYANKEE, CARLOS & NASA 🚀 ---");

        // 1. Creamos el mapa (las salas)
        Sala motores = new Sala("Motores");
        Sala oxigeno = new Sala("Oxigeno");

        // 2. Creamos a los jugadores (tus clases heredadas)
        Ingeniero pedroIng = new Ingeniero("Pedro");
        Medico carlosMed = new Medico("Carlos");
        Capitan faridCap = new Capitan("Farid");
        Impostor traidor = new Impostor("El Enmascarado");

        System.out.println("\n--- 📋 ESTADO INICIAL DE NUESTRA TRIPULACIÓN ---");
        System.out.println(pedroIng);
        System.out.println(carlosMed);
        System.out.println(traidor);

        System.out.println("\n--- 😱 QUE COMIENCEN LOS JUEGOS DEL HAMBRE (LA ACCIÓN) ---");

        // El impostor sabotea y elimina
        traidor.sabotear(motores);
        traidor.eliminar(faridCap); // El Capitán ya no podrá llamar a votación

        // El médico revisa si alguien es sospechoso
        carlosMed.examinar(traidor);

        // El ingeniero arregla el desastre
        pedroIng.repararSala(motores);

        // Intentamos usar una habilidad especial de cada uno
        System.out.println("\n--- ✨ HABILIDADES ESPECIALES ---");
        pedroIng.habilidadEspecial();
        carlosMed.habilidadEspecial();
        traidor.habilidadEspecial();

        System.out.println("\n--- 📊 ESTADO FINAL ---");
        System.out.println("¿Hey ALguien confirma si el capitán esta vivo? " + (faridCap.isVivo() ? "Sí" : "No (F en el chat)"));
        System.out.println("¿La sala de motores esta arreglada? " + (motores.isSaboteada() ? "Sigue rota" : "Arreglada por Pedro el super Ingeniero"));

        System.out.println("\n--- ✅ PRUEBA DE TODAS LAS OPCIONES COMPLETADA ---");
    }
}