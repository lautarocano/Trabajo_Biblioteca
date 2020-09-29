CREATE DATABASE  IF NOT EXISTS `biblioteca` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `biblioteca`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: biblioteca
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
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
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ejemplares` (
  `id_ejemplar` int(11) NOT NULL AUTO_INCREMENT,
  `id_libro` int(11) NOT NULL,
  PRIMARY KEY (`id_ejemplar`),
  KEY `fk_ejemplares_libro_idx` (`id_libro`),
  CONSTRAINT `fk_ejemplares_libro` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id_libro`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejemplares`
--

LOCK TABLES `ejemplares` WRITE;
/*!40000 ALTER TABLE `ejemplares` DISABLE KEYS */;
INSERT INTO `ejemplares` VALUES (1,1),(2,1),(3,1),(4,2);
/*!40000 ALTER TABLE `ejemplares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generos`
--

DROP TABLE IF EXISTS `generos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `generos` (
  `id_genero` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(125) NOT NULL,
  PRIMARY KEY (`id_genero`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generos`
--

LOCK TABLES `generos` WRITE;
/*!40000 ALTER TABLE `generos` DISABLE KEYS */;
INSERT INTO `generos` VALUES (1,'Comedia'),(2,'Terror'),(3,'Drama'),(4,'Suspenso'),(5,'Fantasia');
/*!40000 ALTER TABLE `generos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro_reserva`
--

DROP TABLE IF EXISTS `libro_reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `libro_reserva` (
  `id_libro_reserva` int(11) NOT NULL AUTO_INCREMENT,
  `id_libro` int(11) NOT NULL,
  `id_reserva` int(11) NOT NULL,
  PRIMARY KEY (`id_libro_reserva`),
  KEY `fk_libroreserva_libro_idx` (`id_libro`),
  KEY `fk_libroreserva_reserva_idx` (`id_reserva`),
  CONSTRAINT `fk_libroreserva_libro` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id_libro`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_libroreserva_reserva` FOREIGN KEY (`id_reserva`) REFERENCES `reservas` (`id_reserva`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
 SET character_set_client = utf8mb4 ;
CREATE TABLE `libros` (
  `id_libro` int(11) NOT NULL AUTO_INCREMENT,
  `autor` varchar(125) NOT NULL,
  `titulo` varchar(125) NOT NULL,
  `nro_edicion` varchar(11) NOT NULL,
  `fecha_edicion` date NOT NULL,
  `cant_dias_max` int(11) NOT NULL,
  `id_genero` int(11) NOT NULL,
  PRIMARY KEY (`id_libro`),
  KEY `fk_libros_generos_idx` (`id_genero`),
  CONSTRAINT `fk_libros_generos` FOREIGN KEY (`id_genero`) REFERENCES `generos` (`id_genero`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libros`
--

LOCK TABLES `libros` WRITE;
/*!40000 ALTER TABLE `libros` DISABLE KEYS */;
INSERT INTO `libros` VALUES (1,'Tomas','La laguna','1','2020-06-07',10,2),(2,'Lautaro','La horca','1','2020-02-04',15,2),(3,'Julio Chavez','Los miserables','1','2020-01-01',20,3),(4,'Marcos Penia','Bajo la misma estrella','2','2018-01-03',10,1);
/*!40000 ALTER TABLE `libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lineasdeprestamo`
--

DROP TABLE IF EXISTS `lineasdeprestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `lineasdeprestamo` (
  `id_lineadeprestamo` int(11) NOT NULL AUTO_INCREMENT,
  `id_ejemplar` int(11) NOT NULL,
  `id_prestamo` int(11) NOT NULL,
  `devuelto` bit(1) NOT NULL,
  PRIMARY KEY (`id_lineadeprestamo`),
  KEY `fk_lineasdeprestamo_ejemplar_idx` (`id_ejemplar`),
  KEY `fk_lineasdeprestamo_prestamos_idx` (`id_prestamo`),
  CONSTRAINT `fk_lineasdeprestamo_ejemplares` FOREIGN KEY (`id_ejemplar`) REFERENCES `ejemplares` (`id_ejemplar`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_lineasdeprestamo_prestamos` FOREIGN KEY (`id_prestamo`) REFERENCES `prestamos` (`id_prestamo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lineasdeprestamo`
--

LOCK TABLES `lineasdeprestamo` WRITE;
/*!40000 ALTER TABLE `lineasdeprestamo` DISABLE KEYS */;
INSERT INTO `lineasdeprestamo` VALUES (1,1,1,_binary ''),(2,1,1,_binary '');
/*!40000 ALTER TABLE `lineasdeprestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politicaprestamo`
--

DROP TABLE IF EXISTS `politicaprestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `politicaprestamo` (
  `idpoliticaprestamo` int(11) NOT NULL AUTO_INCREMENT,
  `cant_max_libros_pend` int(11) NOT NULL,
  PRIMARY KEY (`idpoliticaprestamo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politicaprestamo`
--

LOCK TABLES `politicaprestamo` WRITE;
/*!40000 ALTER TABLE `politicaprestamo` DISABLE KEYS */;
INSERT INTO `politicaprestamo` VALUES (1,5),(2,10),(3,40),(4,50),(5,3);
/*!40000 ALTER TABLE `politicaprestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politicasancion`
--

DROP TABLE IF EXISTS `politicasancion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `politicasancion` (
  `id_politicasancion` int(11) NOT NULL AUTO_INCREMENT,
  `dias_atraso_desde` int(11) NOT NULL,
  `dias_atraso_hasta` int(11) NOT NULL,
  `dias_sancion` int(11) NOT NULL,
  PRIMARY KEY (`id_politicasancion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politicasancion`
--

LOCK TABLES `politicasancion` WRITE;
/*!40000 ALTER TABLE `politicasancion` DISABLE KEYS */;
INSERT INTO `politicasancion` VALUES (1,1,5,3),(2,6,10,5),(3,11,15,7),(4,16,2000,100);
/*!40000 ALTER TABLE `politicasancion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `prestamos` (
  `id_prestamo` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_prestamo` date NOT NULL,
  `dias_prestamo` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `id_socio` int(11) NOT NULL,
  `fecha_devolucion` date DEFAULT NULL,
  PRIMARY KEY (`id_prestamo`),
  KEY `fk_prestamos_socios_idx` (`id_socio`),
  CONSTRAINT `fk_prestamos_socios` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamos`
--

LOCK TABLES `prestamos` WRITE;
/*!40000 ALTER TABLE `prestamos` DISABLE KEYS */;
INSERT INTO `prestamos` VALUES (1,'2019-01-01',5,2,1,NULL),(2,'2020-01-01',6,2,1,NULL),(3,'2019-05-03',7,0,1,NULL),(4,'2019-04-03',9,1,1,NULL);
/*!40000 ALTER TABLE `prestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reservas` (
  `id_reserva` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_reserva` date NOT NULL,
  `entregada` bit(1) NOT NULL,
  `id_socio` int(11) NOT NULL,
  PRIMARY KEY (`id_reserva`),
  KEY `fk_reservas_socios_idx` (`id_socio`),
  CONSTRAINT `fk_reservas_socios` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sanciones` (
  `id_sancion` int(11) NOT NULL AUTO_INCREMENT,
  `id_socio` int(11) NOT NULL,
  `fecha_sancion` date NOT NULL,
  `dias_sancion` int(11) NOT NULL,
  PRIMARY KEY (`id_sancion`),
  KEY `fk_sanciones_socios_idx` (`id_socio`),
  CONSTRAINT `fk_sanciones_socios` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanciones`
--

LOCK TABLES `sanciones` WRITE;
/*!40000 ALTER TABLE `sanciones` DISABLE KEYS */;
INSERT INTO `sanciones` VALUES (1,1,'2019-07-01',5),(2,1,'2019-09-05',10);
/*!40000 ALTER TABLE `sanciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socios`
--

DROP TABLE IF EXISTS `socios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `socios` (
  `id_socio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(125) NOT NULL,
  `apellido` varchar(125) NOT NULL,
  `email` varchar(125) NOT NULL,
  `domicilio` varchar(125) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `dni` int(11) NOT NULL,
  `estado` bit(1) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id_socio`),
  KEY `fk_socios_usuarios_idx` (`id_usuario`),
  CONSTRAINT `fk_socios_usuarios` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socios`
--

LOCK TABLES `socios` WRITE;
/*!40000 ALTER TABLE `socios` DISABLE KEYS */;
INSERT INTO `socios` VALUES (1,'tomas','ponce','tpopo@gmail.com','nose1234','1230913',41029330,_binary '\0',1);
/*!40000 ALTER TABLE `socios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(125) NOT NULL,
  `password` varchar(125) NOT NULL,
  `estado` bit(1) NOT NULL,
  `tipo` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'backtomas','1234567',_binary '\0',0),(2,'lautarogod','32659877',_binary '',1),(3,'germanneitormaster','487951',_binary '\0',1),(4,'alicebrancketivocih','1111111',_binary '',0);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-29 19:04:25
