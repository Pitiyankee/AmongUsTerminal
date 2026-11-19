# AmongUsTerminal

Versión por terminal del juego *Among Us*, desarrollada en **Java 17** con **Maven**. Juego de roles ocultos por turnos para 4 a 10 jugadores que comparten un mismo ordenador, con votaciones, sabotajes y guardado opcional de las partidas en **MySQL**. Proyecto en equipo con control de versiones en Git y GitHub.

## Cómo se juega

Cada jugador recibe en secreto un rol y se van pasando el ordenador de uno en uno. La pantalla se borra entre turnos para que nadie vea el rol ni las acciones de los demás.

| Rol | Cantidad | Qué puede hacer |
|---|---|---|
| **Impostor** | 1 | Fingir tareas, sabotear salas y eliminar a un jugador en secreto |
| **Capitán** | 1 | Hacer tareas y convocar una reunión de emergencia |
| **Ingeniero** | el resto, alternados | Hacer tareas y reparar salas saboteadas |
| **Médico** | el resto, alternados | Hacer tareas y examinar a un jugador para saber si es el impostor |

**Turnos.** En su turno cada jugador vivo realiza una acción. Cada tripulante tiene 2 tareas en distintas salas de la nave (Reactor, Cafetería, Navegación, Electricidad, Armamento y Comunicaciones).

**Sabotaje.** Una sala saboteada bloquea las tareas de esa sala hasta que un ingeniero la repare.

**Reuniones.** Cuando se encuentra un cuerpo, o cuando el capitán lo decide, todos los jugadores vivos votan en secreto a un sospechoso o saltan el voto. Se expulsa al más votado; si hay empate, o si saltar tiene tantos votos como el más votado, no se expulsa a nadie. Al expulsar a alguien se revela si era el impostor.

**Victoria.**
- Ganan los **tripulantes** si se completan todas las tareas o si el impostor es eliminado.
- Gana el **impostor** cuando quedan tantos impostores vivos como tripulantes.

Al terminar, el juego ofrece guardar la partida en MySQL.

## Cómo ejecutarlo

**Requisitos:** JDK 17 o superior y Maven.

```bash
git clone https://github.com/Pitiyankee/AmongUsTerminal.git
cd AmongUsTerminal
mvn compile exec:java
```

También se puede abrir la carpeta en IntelliJ IDEA y ejecutar `Main`. Con `--semilla N` el reparto de roles y tareas es siempre el mismo, útil para probar o depurar:

```bash
mvn compile exec:java -Dexec.args="--semilla 42"
```

### Guardar partidas en MySQL (opcional)

El juego funciona sin base de datos. Para guardar las partidas:

1. Ejecuta `script.sql` en tu servidor MySQL 8 para crear la base de datos `nave_espacial`.
2. Configura la conexión con variables de entorno (no hay usuario ni contraseña en el código):

| Variable | Valor por defecto |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/nave_espacial` |
| `DB_USER` | `root` |
| `DB_PASS` | *(vacío)* |

Si la base de datos no está disponible, el juego avisa y sigue sin cerrarse.

## Pruebas

```bash
mvn test
```

25 pruebas automáticas con JUnit 5:
- **Reglas de cada rol:** eliminar, sabotear, reparar, tareas bloqueadas y jugadores eliminados.
- **Condiciones de victoria y votación:** mayoría, empates y votos saltados.
- **Partidas completas simuladas por teclado:** victoria de los tripulantes por tareas, del impostor por eliminaciones y por expulsión del impostor.
- **Guardado en base de datos:** inserción de la partida completa, varias partidas seguidas y rollback si algo falla. Estas pruebas se ejecutan sobre H2 en modo compatible con MySQL, cargando el mismo `script.sql`.

## Aspectos técnicos

- **Programación orientada a objetos:** clase abstracta `Tripulante` con las subclases `Capitan`, `Ingeniero`, `Medico` e `Impostor`; cada rol implementa su `habilidadEspecial()`.
- **Interfaces:** `Trabajable` (hacer tareas), `Votable` (votar) y `Saboteable` (solo el impostor).
- **Separación de responsabilidades:** `Nave` contiene las reglas (victoria, votación, reparto de tareas), `Partida` controla el desarrollo por turnos, `Consola` encapsula la entrada por teclado y `Main` solo prepara la partida.
- **JDBC con patrón DAO:** `PartidaDAO`, `SalaDAO`, `TripulanteDAO` y `TareaDAO` usan `PreparedStatement` y claves generadas; `GuardadoPartida` guarda todo en una **transacción** con `commit` y `rollback`.
- **Base de datos:** esquema MySQL con claves primarias y foráneas (`script.sql`).
- **Configuración fuera del código:** credenciales por variables de entorno.
- **Git:** una rama por funcionalidad integrada mediante pull requests.

## Estructura

```
src/main/java/
├── Main.java              Registro de jugadores, reparto de roles y arranque
├── Partida.java           Turnos, acciones, reuniones y resultado
├── Nave.java              Reglas: victoria, votación y reparto de tareas
├── Consola.java           Entrada por teclado validada
├── Tripulante.java        Clase abstracta base
├── Capitan.java · Ingeniero.java · Medico.java · Impostor.java
├── Trabajable.java · Votable.java · Saboteable.java        Interfaces
├── Sala.java · Tarea.java
└── DBUtil.java · PartidaDAO.java · SalaDAO.java · TripulanteDAO.java · TareaDAO.java · GuardadoPartida.java
src/test/java/             Pruebas con JUnit 5
script.sql                 Esquema de la base de datos
```

## Equipo

Pedro Bandes ([@Pitiyankee](https://github.com/Pitiyankee)) y Carlos.
