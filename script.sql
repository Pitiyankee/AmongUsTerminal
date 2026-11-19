-- Base de datos de AmongUsTerminal (MySQL 8)
CREATE DATABASE IF NOT EXISTS nave_espacial;
USE nave_espacial;

-- Una fila por cada partida jugada
CREATE TABLE IF NOT EXISTS partida (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ganador VARCHAR(20) NOT NULL
);

-- Salas de la nave en cada partida
CREATE TABLE IF NOT EXISTS sala (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_partida INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    saboteada BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_partida) REFERENCES partida(id)
);

-- Jugadores de cada partida, con su rol y si siguen vivos
CREATE TABLE IF NOT EXISTS tripulante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_partida INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    vivo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_partida) REFERENCES partida(id)
);

-- Tareas asignadas a cada tripulante en una sala
CREATE TABLE IF NOT EXISTS tarea (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_partida INT NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    completada BOOLEAN DEFAULT FALSE,
    id_tripulante INT,
    id_sala INT,
    FOREIGN KEY (id_partida) REFERENCES partida(id),
    FOREIGN KEY (id_tripulante) REFERENCES tripulante(id),
    FOREIGN KEY (id_sala) REFERENCES sala(id)
);
