/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 10.4.27-MariaDB : Database - veterinaria
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`veterinaria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `veterinaria`;

/*Table structure for table `animales` */

DROP TABLE IF EXISTS `animales`;

CREATE TABLE `animales` (
  `idanimal` int(11) NOT NULL AUTO_INCREMENT,
  `nombreanimal` varchar(50) NOT NULL,
  PRIMARY KEY (`idanimal`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `animales` */

insert  into `animales`(`idanimal`,`nombreanimal`) values 
(1,'Gato'),
(2,'Perro'),
(3,'Conejo'),
(4,'Cerdo'),
(5,'Pajaro');

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(50) NOT NULL,
  `nombres` varchar(50) NOT NULL,
  `dni` char(8) NOT NULL,
  `claveacceso` varchar(200) NOT NULL,
  PRIMARY KEY (`idcliente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `clientes` */

insert  into `clientes`(`idcliente`,`apellidos`,`nombres`,`dni`,`claveacceso`) values 
(1,'Montoya Cardenas','Estefany','78638012','$2y$10$FgVDwl0OQN5fNoYatdsDqu7EPaybkgynXclTotQZTfA5GSTxQjj1W'),
(2,'Montoya Cardenas','Luis','78520043','Arie28'),
(3,'Cartagena Salazar','Jimena','59545454','121212'),
(4,'Duran Buenamarca','Adriana','78456210','112233'),
(5,'Monayco González ','Luis','76494319','1234');

/*Table structure for table `mascotas` */

DROP TABLE IF EXISTS `mascotas`;

CREATE TABLE `mascotas` (
  `idmascota` int(11) NOT NULL AUTO_INCREMENT,
  `idcliente` int(11) NOT NULL,
  `idraza` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `fotografia` varchar(50) DEFAULT NULL,
  `color` varchar(20) NOT NULL,
  `genero` char(1) NOT NULL,
  `tamaño` smallint(6) NOT NULL,
  `peso` smallint(6) NOT NULL,
  PRIMARY KEY (`idmascota`),
  KEY `fk_idcliente` (`idcliente`),
  CONSTRAINT `fk_idcliente` FOREIGN KEY (`idcliente`) REFERENCES `clientes` (`idcliente`),
  CONSTRAINT `ck_genero_masco` CHECK (`genero` in ('M','H'))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `mascotas` */

insert  into `mascotas`(`idmascota`,`idcliente`,`idraza`,`nombre`,`fotografia`,`color`,`genero`,`tamaño`,`peso`) values 
(1,1,3,'Toby','perro.jpg','Blanco y negro','M',55,22);

/*Table structure for table `razas` */

DROP TABLE IF EXISTS `razas`;

CREATE TABLE `razas` (
  `idraza` int(11) NOT NULL AUTO_INCREMENT,
  `idanimal` int(11) NOT NULL,
  `nombreraza` varchar(40) NOT NULL,
  PRIMARY KEY (`idraza`),
  KEY `fk_idanimal_raza` (`idanimal`),
  CONSTRAINT `fk_idanimal_raza` FOREIGN KEY (`idanimal`) REFERENCES `animales` (`idanimal`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `razas` */

insert  into `razas`(`idraza`,`idanimal`,`nombreraza`) values 
(1,1,'Abisino'),
(2,1,'Asiatico'),
(3,2,'Pastor'),
(4,2,'Chihuahua'),
(5,3,'Palmera'),
(6,3,'Beveren'),
(7,4,'Tan'),
(8,4,'Chihuahua'),
(9,5,'Loro'),
(10,5,'Huacamayo');

/* Procedure structure for procedure `spu_buscar_mascota` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_buscar_mascota` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_buscar_mascota`(
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
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_listar_mascota` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_listar_mascota` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_listar_mascota`()
BEGIN
	SELECT idmascota, clientes.nombres, clientes.apellidos, razas.nombreraza, nombre, color, genero, tamaño, peso
	FROM mascotas
	INNER JOIN clientes ON clientes.idcliente = mascotas.idcliente
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	INNER JOIN animales ON animales.idanimal = razas.idanimal;                               
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_login` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_login` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_login`( 
	IN _dni char(8)
)
BEGIN
	SELECT idcliente, nombres, dni,apellidos, claveacceso
	FROM clientes
	WHERE  dni = _dni;
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_obtener_data` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_obtener_data` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_obtener_data`( 
	in _idcliente int
)
begin
	select * from clientes
	where idcliente = _idcliente;
end */$$
DELIMITER ;

/* Procedure structure for procedure `spu_register_cliente` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_register_cliente` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_register_cliente`(
	IN _apellidos VARCHAR(50),
	IN _nombres VARCHAR(50),
	IN _dni		CHAR(8),
	IN _claveacceso VARCHAR(200)
)
BEGIN 
	INSERT INTO clientes (apellidos, nombres, dni, claveacceso) VALUES
						(_apellidos,_nombres,_dni,_claveacceso);
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_register_mascota` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_register_mascota` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_register_mascota`(
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
END */$$
DELIMITER ;

/* Procedure structure for procedure `spu_update_cliente` */

/*!50003 DROP PROCEDURE IF EXISTS  `spu_update_cliente` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `spu_update_cliente`(
	IN _idcliente  INT,
	IN _apellidos VARCHAR(40),
	in _nombres varchar(40),
	in _dni char(8),
	in _clave varchar(200)
)
BEGIN 
	UPDATE clientes SET
	apellidos = _apellidos,
	nombres = _nombres,
	dni = _dni,
	claveacceso = _clave
	WHERE idcliente = _idcliente;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
