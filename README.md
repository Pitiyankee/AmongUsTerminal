# AmongUsTerminal

Versión por terminal del juego *Among Us*, desarrollada en **Java** con **Maven** como proyecto de fin de trimestre del módulo de Programación (ACCEDIA FP, Madrid). Trabajo en equipo con control de versiones en Git y GitHub.

## Descripción

Juego de roles ocultos por turnos para **4 a 10 jugadores** que comparten un mismo ordenador. Cada jugador recibe en secreto un rol y se van pasando el ordenador de uno en uno, sin que los demás lo vean:

| Rol | Cantidad | Descripción |
|---|---|---|
| Impostor | 1 | Finge trabajar, sabotea salas y elimina tripulantes |
| Capitán | 1 | Tripulante con habilidad especial propia |
| Ingeniero y Médico | el resto, alternados | Tripulantes con habilidad especial propia |

Los roles se reparten al azar (`Collections.shuffle`) y la partida se desarrolla por turnos, con limpieza de pantalla entre jugadores para que nadie vea el rol del anterior.

## Aspectos técnicos

- **Programación orientada a objetos:** clase abstracta `Tripulante` con las subclases `Capitan`, `Ingeniero`, `Medico` e `Impostor`; cada rol implementa a su manera el método abstracto `habilidadEspecial()`.
- **Interfaces:** `Trabajable` (hacer tareas), `Votable` (votar) y `Saboteable` (sabotear salas, exclusiva del impostor).
- **Modelo de la nave:** `Nave`, `Sala` y `Tarea` representan el estado de la partida; `Nave` comprueba las condiciones de victoria de tripulantes e impostor.
- **Colecciones:** `ArrayList` para jugadores y salas; reparto aleatorio de roles.
- **Capa de acceso a datos:** `DBUtil` (conexión JDBC) y los DAO `SalaDAO` y `TripulanteDAO`, que usan `PreparedStatement`.
- **Base de datos:** `script.sql` crea el esquema MySQL `nave_espacial` con las tablas `tripulante`, `sala` y `tarea`, con claves primarias y foráneas.
- **Flujo de trabajo en Git:** una rama por funcionalidad (`feature/...`) integrada mediante pull requests.

## Estado del proyecto

**Funciona:** registro de jugadores, reparto aleatorio de roles, turnos con rol oculto y limpieza de pantalla, modelo de dominio completo y comprobación de victoria.

**Pendiente:**
- Integrar en el bucle de la partida las acciones por rol (tareas, sabotaje, eliminación y votación), que ya están definidas en las clases.
- Conectar los DAO a la partida para guardar los datos en MySQL. Ahora mismo la partida no usa la base de datos.
- Añadir el driver JDBC de MySQL como dependencia en `pom.xml`.

## Cómo ejecutarlo

**Requisitos:** JDK y Maven. El `pom.xml` está configurado para Java 25; con un JDK anterior hay que ajustar `maven.compiler.source` y `maven.compiler.target`.

```bash
git clone https://github.com/Pitiyankee/AmongUsTerminal.git
cd AmongUsTerminal
mvn compile
java -cp target/classes Main
```

También se puede abrir la carpeta en IntelliJ IDEA y ejecutar `Main`. La partida actual **no necesita** MySQL. Para trabajar con la capa de datos, ejecuta `script.sql` en tu servidor MySQL y ajusta el usuario y la contraseña en `DBUtil.java`.

## Estructura

```
src/main/java/
├── Main.java            Registro de jugadores, reparto de roles y bucle de turnos
├── Nave.java            Estado de la partida y condiciones de victoria
├── Tripulante.java      Clase abstracta base de todos los roles
├── Capitan.java · Ingeniero.java · Medico.java · Impostor.java
├── Trabajable.java · Votable.java · Saboteable.java    Interfaces
├── Sala.java · Tarea.java
├── DBUtil.java · SalaDAO.java · TripulanteDAO.java     Acceso a datos (JDBC)
script.sql               Esquema de la base de datos
```

## Equipo

Pedro Bandes ([@Pitiyankee](https://github.com/Pitiyankee)) y Carlos.
