CREATE DATABASE  IF NOT EXISTS `biblioteca` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `biblioteca`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: biblioteca
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ejemplares`
--

DROP TABLE IF EXISTS `ejemplares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejemplares` (
  `id_ejemplar` int NOT NULL AUTO_INCREMENT,
  `id_libro` int NOT NULL,
  PRIMARY KEY (`id_ejemplar`),
  KEY `fk_ejemplares_libro_idx` (`id_libro`),
  CONSTRAINT `fk_ejemplares_libro` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id_libro`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejemplares`
--

LOCK TABLES `ejemplares` WRITE;
/*!40000 ALTER TABLE `ejemplares` DISABLE KEYS */;
INSERT INTO `ejemplares` VALUES (1,1),(2,1),(3,1),(4,2),(5,5),(6,5);
/*!40000 ALTER TABLE `ejemplares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generos`
--

DROP TABLE IF EXISTS `generos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `generos` (
  `id_genero` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_genero`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generos`
--

LOCK TABLES `generos` WRITE;
/*!40000 ALTER TABLE `generos` DISABLE KEYS */;
INSERT INTO `generos` VALUES (1,'Ciencia ficción'),(2,'Terror'),(3,'Drama'),(4,'Suspenso'),(5,'Fantasia'),(6,'Drama adolescente');
/*!40000 ALTER TABLE `generos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro_reserva`
--

DROP TABLE IF EXISTS `libro_reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libro_reserva` (
  `id_libro_reserva` int NOT NULL AUTO_INCREMENT,
  `id_libro` int NOT NULL,
  `id_reserva` int NOT NULL,
  PRIMARY KEY (`id_libro_reserva`),
  KEY `fk_libroreserva_libro_idx` (`id_libro`),
  KEY `fk_libroreserva_reserva_idx` (`id_reserva`),
  CONSTRAINT `fk_libroreserva_libro` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id_libro`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_libroreserva_reserva` FOREIGN KEY (`id_reserva`) REFERENCES `reservas` (`id_reserva`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro_reserva`
--

LOCK TABLES `libro_reserva` WRITE;
/*!40000 ALTER TABLE `libro_reserva` DISABLE KEYS */;
/*!40000 ALTER TABLE `libro_reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libros`
--

DROP TABLE IF EXISTS `libros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libros` (
  `id_libro` int NOT NULL AUTO_INCREMENT,
  `autor` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `titulo` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nro_edicion` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fecha_edicion` date NOT NULL,
  `id_genero` int NOT NULL,
  PRIMARY KEY (`id_libro`),
  KEY `fk_libros_generos_idx` (`id_genero`),
  CONSTRAINT `fk_libros_generos` FOREIGN KEY (`id_genero`) REFERENCES `generos` (`id_genero`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libros`
--

LOCK TABLES `libros` WRITE;
/*!40000 ALTER TABLE `libros` DISABLE KEYS */;
INSERT INTO `libros` VALUES (1,'Isaac Asimov','Yo, robot','1','2020-06-07',1),(2,'Andrzej Sapkowski','El último deseo','1','2002-02-04',5),(3,'J. R. R. Tolkien','La Comunidad del Anillo','6','2021-06-01',5),(4,'John Green','Ciudades de papel','2','2018-01-03',6),(5,'Stephen King','IT','6','2019-02-01',2),(6,'John Green','Bajo la misma estrella','4','2020-04-21',3);
/*!40000 ALTER TABLE `libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lineasdeprestamo`
--

DROP TABLE IF EXISTS `lineasdeprestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lineasdeprestamo` (
  `id_lineadeprestamo` int NOT NULL AUTO_INCREMENT,
  `id_ejemplar` int NOT NULL,
  `id_prestamo` int NOT NULL,
  `devuelto` bit(1) NOT NULL,
  PRIMARY KEY (`id_lineadeprestamo`),
  KEY `fk_lineasdeprestamo_ejemplar_idx` (`id_ejemplar`),
  KEY `fk_lineasdeprestamo_prestamos_idx` (`id_prestamo`),
  CONSTRAINT `fk_lineasdeprestamo_ejemplares` FOREIGN KEY (`id_ejemplar`) REFERENCES `ejemplares` (`id_ejemplar`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_lineasdeprestamo_prestamos` FOREIGN KEY (`id_prestamo`) REFERENCES `prestamos` (`id_prestamo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lineasdeprestamo`
--

LOCK TABLES `lineasdeprestamo` WRITE;
/*!40000 ALTER TABLE `lineasdeprestamo` DISABLE KEYS */;
/*!40000 ALTER TABLE `lineasdeprestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politicaprestamo`
--

DROP TABLE IF EXISTS `politicaprestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `politicaprestamo` (
  `idpoliticaprestamo` int NOT NULL AUTO_INCREMENT,
  `cant_max_libros_pend` int NOT NULL,
  `fecha_politica_prestamo` datetime NOT NULL,
  `cant_dias_prestamo` int NOT NULL,
  PRIMARY KEY (`idpoliticaprestamo`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politicaprestamo`
--

LOCK TABLES `politicaprestamo` WRITE;
/*!40000 ALTER TABLE `politicaprestamo` DISABLE KEYS */;
INSERT INTO `politicaprestamo` VALUES (1,3,'2010-02-17 00:00:00',4),(10,2,'2015-04-15 00:00:00',3),(11,4,'2019-09-18 00:00:00',5),(12,5,'2021-04-09 00:00:00',4);
/*!40000 ALTER TABLE `politicaprestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politicasancion`
--

DROP TABLE IF EXISTS `politicasancion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `politicasancion` (
  `id_politicasancion` int NOT NULL AUTO_INCREMENT,
  `dias_atraso_desde` int NOT NULL,
  `dias_atraso_hasta` int NOT NULL,
  `dias_sancion` int NOT NULL,
  PRIMARY KEY (`id_politicasancion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politicasancion`
--

LOCK TABLES `politicasancion` WRITE;
/*!40000 ALTER TABLE `politicasancion` DISABLE KEYS */;
INSERT INTO `politicasancion` VALUES (1,1,5,3),(2,6,10,5),(3,11,15,7),(4,16,20,10);
/*!40000 ALTER TABLE `politicasancion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamos` (
  `id_prestamo` int NOT NULL AUTO_INCREMENT,
  `fecha_prestamo` date NOT NULL,
  `dias_prestamo` int NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `id_socio` int NOT NULL,
  `fecha_devolucion` date DEFAULT NULL,
  PRIMARY KEY (`id_prestamo`),
  KEY `fk_prestamos_socios_idx` (`id_socio`),
  CONSTRAINT `fk_prestamos_socios` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamos`
--

LOCK TABLES `prestamos` WRITE;
/*!40000 ALTER TABLE `prestamos` DISABLE KEYS */;
/*!40000 ALTER TABLE `prestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas` (
  `id_reserva` int NOT NULL AUTO_INCREMENT,
  `fecha_reserva` date NOT NULL,
  `entregada` bit(1) NOT NULL,
  `id_socio` int NOT NULL,
  PRIMARY KEY (`id_reserva`),
  KEY `fk_reservas_socios_idx` (`id_socio`),
  CONSTRAINT `fk_reservas_socios` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sanciones`
--

DROP TABLE IF EXISTS `sanciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanciones` (
  `id_sancion` int NOT NULL AUTO_INCREMENT,
  `id_socio` int NOT NULL,
  `fecha_sancion` date NOT NULL,
  `dias_sancion` int NOT NULL,
  PRIMARY KEY (`id_sancion`),
  KEY `fk_sanciones_socios_idx` (`id_socio`),
  CONSTRAINT `fk_sanciones_socios` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanciones`
--

LOCK TABLES `sanciones` WRITE;
/*!40000 ALTER TABLE `sanciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `sanciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socios`
--

DROP TABLE IF EXISTS `socios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `socios` (
  `id_socio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `apellido` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `domicilio` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `telefono` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dni` int NOT NULL,
  `estado` bit(1) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_socio`),
  KEY `fk_socios_usuarios_idx` (`id_usuario`),
  CONSTRAINT `fk_socios_usuarios` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socios`
--

LOCK TABLES `socios` WRITE;
/*!40000 ALTER TABLE `socios` DISABLE KEYS */;
INSERT INTO `socios` VALUES (1,'Tomas','Ponce','poncetomaseduardo@gmail.com','Chaco','3412826001',41602902,_binary '\0',1),(2,'Lautaro','Cano','lautarojuancano@gmail.com','Cochabamba 1234','3412636021',40504604,_binary '\0',2),(3,'Juan','Perez','jperez12345679@gmail.com','Falso 123','341260312',40235505,_binary '\0',3);
/*!40000 ALTER TABLE `socios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `estado` bit(1) NOT NULL,
  `tipo` int NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'tponce','123456789',_binary '\0',0),(2,'lcano','123456789',_binary '\0',1),(3,'admin','admin',_binary '\0',2),(5,'glorenzo','123456789',_binary '',0),(6,'nfernandez','123456789',_binary '',0);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'biblioteca'
--
/*!50003 DROP PROCEDURE IF EXISTS `Is_Sancionado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `Is_Sancionado`(Id_De_Socio int)
BEGIN
select max(fecha_sancion) into @maxfecha from sanciones where id_socio=Id_De_Socio;
select DATEDIFF(CURDATE(),fecha_sancion) as diferencia,dias_sancion from sanciones where id_socio=Id_De_Socio  and  fecha_sancion=@maxfecha;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-26 21:17:06
