-- creo la base de datos y le digo que la use
CREATE DATABASE nave_espacial;
USE nave_espacial;

-- aqui guardo los datos de cada jugador
CREATE TABLE tripulante (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(50) NOT NULL,
                            rol VARCHAR(20) NOT NULL,
                            vivo BOOLEAN DEFAULT TRUE
);

-- aqui guardo las habitaciones de la nave
CREATE TABLE sala (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      nombre VARCHAR(50) NOT NULL UNIQUE,
                      saboteada BOOLEAN DEFAULT FALSE
);

-- aqui guardo las misiones que tienen que hacer
CREATE TABLE tarea (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       descripcion VARCHAR(100) NOT NULL,
                       completada BOOLEAN DEFAULT FALSE,
                       id_tripulante INT,
                       id_sala INT,
                       FOREIGN KEY (id_tripulante) REFERENCES tripulante(id),
                       FOREIGN KEY (id_sala) REFERENCES sala(id)
);