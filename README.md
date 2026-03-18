# 🚀 AMONG US - PROYECTO FINAL

¡Hola Farid! Este es mi proyecto de fin de trimestre para la clase de Programación. Es una versión del Among Us pero para jugar en la terminal.
La verdad espero que te guste, creo que ha sido de los proyectos mas retadores que hemos tenido. 
NOTA: Seguimos al pie de la letra el PDF, sin embargo revisa todo con calma para que puedas darnos un feedback mas honesto y real

## 🛠️ ¿Qué usamos para hacerlo?
* **Java 17**: Este es el lenguaje principal.
* **MySQL**: Este fue emplementado para guardar los datos de la partida.
* **Git/GitHub**: Usamos este para trabajar en equipo con Carlos y no perder el código.

## 🎮 Cómo lo puedes jugar?
1. Primero hay que ejecutar el archivo `script.sql` en MySQL para crear las tablas.
2. Luego se arranca el `Main.java`.
3. El juego te pedirá cuántos jugadores son (entre 4 y 10).
4. **¡NOTA Importante!**: Cuando te toque el turno, lee tu rol en secreto y pasa el PC al siguiente cuando termines para que nadie te descubra.

## 📂 Estructura general del proyecto
* **Nave.java**: Es el cerebro que controla si alguien gana o pierde.
* **Tripulante.java**: Es la base de todos los personajes (Capitán, Ingeniero, etc.).
* **DBUtil.java**: Lo que hace que el programa hable con la base de datos.
* **Interfaces**: Usé `Trabajable`, `Votable` y `Saboteable` para que el código lo puedas revisar y evaluar de forma  ordenada.

---
Hecho por **Pedro Bandes** - DAM 1º