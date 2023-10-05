CREATE DATABASE veterinaria
USE veterinaria

CREATE TABLE animales
(
	idanimal	INT AUTO_INCREMENT PRIMARY KEY,
	nombreanimal 	VARCHAR(50) 	NOT NULL
)ENGINE=INNODB;

INSERT INTO animales(nombreanimal)VALUES
('Gato'),('Perro'),('Conejo'),('Cerdo'),('Pajaro');

SELECT * FROM animales
-- ---------------------------------------------------------------
CREATE TABLE clientes
(
	idcliente 	INT AUTO_INCREMENT PRIMARY KEY,
	apellidos 	VARCHAR(50) 	NOT NULL,
	nombres 	VARCHAR(50)	NOT NULL,
	dni 		CHAR(8) 	NOT NULL,
	claveacceso	VARCHAR(200)	NOT NULL
)ENGINE=INNODB;

INSERT INTO clientes(apellidos,nombres,dni,claveacceso)VALUES
	('Montoya Cardenas','Estefany','78638012','1234567'),
	('Fajardo Ananpa','Sara','78340182','Arie28'),
	('Cartagena Salazar','Jimena','59545454','121212'),
	('Duran Buenamarca','Adriana','78456210','112233');
	
SELECT * FROM clientes
-- 1234
UPDATE clientes SET claveacceso = '$2y$10$FgVDwl0OQN5fNoYatdsDqu7EPaybkgynXclTotQZTfA5GSTxQjj1W'
WHERE idcliente = 1;

CREATE TABLE razas
(
	idraza 		INT AUTO_INCREMENT PRIMARY KEY,
	idanimal	INT 		NOT NULL,
	nombreraza 	VARCHAR(40) 	NOT NULL,    
	CONSTRAINT fk_idanimal_raza FOREIGN KEY (idanimal) REFERENCES animales (idanimal)
)ENGINE=INNODB;

INSERT INTO razas (idanimal,nombreraza) VALUES
	(1,'Abisino'),(1,'Asiatico'),
	(2,'Pastor'),(2,'Chihuahua'),
	(3,'Palmera'),(3,'Beveren'),
	(4,'Tan'),(4,'Chihuahua'),
	(5,'Loro'),(5,'Huacamayo');

SELECT * FROM razas
CREATE TABLE mascotas -- falta eje
(
	idmascota 	INT AUTO_INCREMENT PRIMARY KEY,
	idcliente 	INT 		NOT NULL,
	idraza 		INT 		NOT NULL,
	nombre 		VARCHAR(40) 	NOT NULL,
	fotografia 	VARCHAR(50) 	NULL,
	color 		VARCHAR(20) 	NOT NULL,
	genero 		CHAR(1) 	NOT NULL, -- M,H
	tamaño 		SMALLINT	NOT NULL,
	peso		SMALLINT 	NOT NULL,
	CONSTRAINT fk_idcliente FOREIGN KEY (idcliente) REFERENCES clientes (idcliente),
	CONSTRAINT ck_genero_masco CHECK (genero IN ('M','H'))
)ENGINE=INNODB;

INSERT INTO mascotas (idcliente,idraza,nombre,fotografia,color,genero,tamaño,peso) VALUES
			(1,3,'Toby','perro.jpg','Blanco y negro','M',55,22);
			
SELECT * FROM razas

-- PROCEDIMIENTOS
DELIMITER $$
CREATE PROCEDURE spu_listar_mascota()
BEGIN
	SELECT idmascota, clientes.nombres, clientes.apellidos, razas.nombreraza, nombre, color, genero, tamaño, peso
	FROM mascotas
	INNER JOIN clientes ON clientes.idcliente = mascotas.idcliente
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	INNER JOIN animales ON animales.idanimal = razas.idanimal;                               
END$$


DELIMITER$$
CREATE PROCEDURE spu_login 
( 
	IN _dni CHAR(8)
)
BEGIN
	SELECT idcliente, nombres, dni,apellidos, claveacceso
	FROM clientes
	WHERE  dni = _dni;
END$$

SELECT * FROM clientes

CALL spu_login('78340182');
-- registrar clientes y mascotas . con el cliente sacar todos los datos de la mascota o alrevez

DELIMITER $$
CREATE PROCEDURE spu_register_cliente
(
	IN _apellidos VARCHAR(50),
	IN _nombres VARCHAR(50),
	IN _dni		CHAR(8),
	IN _claveacceso VARCHAR(200)
)
BEGIN 
	INSERT INTO clientes (apellidos, nombres, dni, claveacceso) VALUES
				(_apellidos,_nombres,_dni,_claveacceso);
END $$

DELIMITER $$
CREATE PROCEDURE spu_register_mascota
(
	_idcliente INT,
	_idraza    INT,
	_nombre  VARCHAR(50),
	_fotografia VARCHAR(200),
	_color 	VARCHAR(40),
	_genero CHAR(1),
	_tamaño SMALLINT,
	_peso  SMALLINT
)
BEGIN
	INSERT INTO mascotas (idcliente,idraza,nombre,fotografia,color,genero,tamaño,peso)VALUES
		(_idcliente,_idraza,_nombre,_fotografia,_color,_genero,_tamaño,_peso);
END $$

CALL spu_register_mascota(2,2,'Sara','','Blanco','M','23','24');

DELIMITER $$
CREATE PROCEDURE spu_buscar_mascota
(
	IN _dni CHAR(8)
)
BEGIN
	SELECT idmascota,clientes.nombres, clientes.apellidos, clientes.dni, animales.nombreanimal, 
	razas.nombreraza, mascotas.nombre, mascotas.color, mascotas.fotografia,
	mascotas.genero, mascotas.peso
	FROM mascotas
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	INNER JOIN clientes ON clientes.idcliente = mascotas.idcliente
	INNER JOIN animales ON animales.idanimal = razas.idanimal
	WHERE clientes.dni = _dni;
END $$

CALL spu_buscar_mascota('78638012');
SELECT * FROM clientes

DELIMITER $$
CREATE PROCEDURE spu_obtener_data
( 
	IN _idcliente INT
)
BEGIN
	SELECT * FROM clientes
	WHERE idcliente = _idcliente;
END $$

CALL spu_obtener_data(4);

DELIMITER $$
CREATE PROCEDURE spu_update_cliente
(
	IN _idcliente  INT,
	IN _apellidos VARCHAR(40),
	IN _nombres VARCHAR(40),
	IN _dni CHAR(8),
	IN _clave VARCHAR(200)
)
BEGIN 
	UPDATE clientes SET
	apellidos = _apellidos,
	nombres = _nombres,
	dni = _dni,
	claveacceso = _clave
	WHERE idcliente = _idcliente;
END $$

CALL spu_update_cliente(2,'Montoya Cardenas','Luis','78520043','Arie28');

