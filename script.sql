-- Borrar si ya existe para evitar errores a Farid
DROP DATABASE IF EXISTS nave_espacial;
CREATE DATABASE nave_espacial;
USE nave_espacial;

-- Esta es la Tabla de los personajes
CREATE TABLE tripulante (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(50) NOT NULL,
                            rol VARCHAR(20) NOT NULL,
                            vivo BOOLEAN DEFAULT TRUE
);

-- aqui la Tabla de las salas (Mínimo 6)
CREATE TABLE sala (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      nombre VARCHAR(50) NOT NULL
);

-- aqui la Tabla de las tareas
CREATE TABLE tarea (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       descripcion VARCHAR(100) NOT NULL,
                       completada BOOLEAN DEFAULT FALSE,
                       id_tripulante INT,
                       id_sala INT,
                       FOREIGN KEY (id_tripulante) REFERENCES tripulante(id),
                       FOREIGN KEY (id_sala) REFERENCES sala(id)
);