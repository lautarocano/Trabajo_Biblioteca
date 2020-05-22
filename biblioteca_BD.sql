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
  `id_ejemplar` int(11) NOT NULL,
  `id_libro` int(11) NOT NULL,
  PRIMARY KEY (`id_ejemplar`,`id_libro`),
  KEY `idLibro_idx` (`id_libro`),
  CONSTRAINT `fk_libro_ejemplar` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id_libro`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejemplares`
--

LOCK TABLES `ejemplares` WRITE;
/*!40000 ALTER TABLE `ejemplares` DISABLE KEYS */;
/*!40000 ALTER TABLE `ejemplares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generos`
--

DROP TABLE IF EXISTS `generos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `generos` (
  `id_genero` int(11) NOT NULL,
  `descripcion` varchar(125) NOT NULL,
  PRIMARY KEY (`id_genero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generos`
--

LOCK TABLES `generos` WRITE;
/*!40000 ALTER TABLE `generos` DISABLE KEYS */;
/*!40000 ALTER TABLE `generos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro_reserva`
--

DROP TABLE IF EXISTS `libro_reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `libro_reserva` (
  `id_libro_reserva` int(11) NOT NULL,
  `id_libro` int(11) NOT NULL,
  `id_reserva` int(11) NOT NULL,
  PRIMARY KEY (`id_libro_reserva`),
  KEY `fk_reserva_libroreserva_idx` (`id_reserva`),
  KEY `fk_libro_libroreserva_idx` (`id_libro`),
  CONSTRAINT `fk_libro_libroreserva` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id_libro`) ON UPDATE CASCADE,
  CONSTRAINT `fk_reserva_libroreserva` FOREIGN KEY (`id_reserva`) REFERENCES `reservas` (`id_reserva`) ON UPDATE CASCADE
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
  `id_libro` int(11) NOT NULL,
  `autor` varchar(125) NOT NULL,
  `titulo` varchar(125) NOT NULL,
  `nro_edicion` varchar(11) NOT NULL,
  `fecha_edicion` date NOT NULL,
  `cant_dias_max` int(11) NOT NULL,
  `id_genero` int(11) NOT NULL,
  PRIMARY KEY (`id_libro`),
  KEY `idGenero_idx` (`id_genero`),
  CONSTRAINT `fk_genero_libro` FOREIGN KEY (`id_genero`) REFERENCES `generos` (`id_genero`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libros`
--

LOCK TABLES `libros` WRITE;
/*!40000 ALTER TABLE `libros` DISABLE KEYS */;
/*!40000 ALTER TABLE `libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lineasdeprestamo`
--

DROP TABLE IF EXISTS `lineasdeprestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `lineasdeprestamo` (
  `id_lineadeprestamo` int(11) NOT NULL,
  `id_ejemplar` int(11) NOT NULL,
  `id_libro` int(11) NOT NULL,
  `id_prestamo` int(11) NOT NULL,
  `fecha_devolucion` date NOT NULL,
  `devuelto` bit(1) NOT NULL,
  PRIMARY KEY (`id_lineadeprestamo`,`id_prestamo`,`id_libro`,`id_ejemplar`),
  KEY `fk_lineadeprestamo_ejemplar_idx` (`id_ejemplar`,`id_libro`),
  KEY `fk_lineadeprestamo_prestamo_idx` (`id_prestamo`),
  CONSTRAINT `fk_ejemplar_lineadeprestamo` FOREIGN KEY (`id_ejemplar`) REFERENCES `ejemplares` (`id_ejemplar`) ON UPDATE CASCADE,
  CONSTRAINT `fk_prestamo_lineadeprestamo` FOREIGN KEY (`id_prestamo`) REFERENCES `prestamos` (`id_prestamo`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
 SET character_set_client = utf8mb4 ;
CREATE TABLE `politicaprestamo` (
  `idpoliticaprestamo` int(11) NOT NULL,
  `cant_max_libros_pend` int(11) NOT NULL,
  PRIMARY KEY (`idpoliticaprestamo`,`cant_max_libros_pend`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politicaprestamo`
--

LOCK TABLES `politicaprestamo` WRITE;
/*!40000 ALTER TABLE `politicaprestamo` DISABLE KEYS */;
/*!40000 ALTER TABLE `politicaprestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `politicasancion`
--

DROP TABLE IF EXISTS `politicasancion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `politicasancion` (
  `id_politicasancion` int(11) NOT NULL,
  `dias_atraso_desde` int(11) NOT NULL,
  `dias_atraso_hasta` int(11) NOT NULL,
  `dias_sancion` int(11) NOT NULL,
  PRIMARY KEY (`id_politicasancion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `politicasancion`
--

LOCK TABLES `politicasancion` WRITE;
/*!40000 ALTER TABLE `politicasancion` DISABLE KEYS */;
/*!40000 ALTER TABLE `politicasancion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `prestamos` (
  `id_prestamo` int(11) NOT NULL,
  `fecha_prestamo` date NOT NULL,
  `dias_prestamo` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `id_socio` int(11) NOT NULL,
  PRIMARY KEY (`id_prestamo`,`id_socio`),
  KEY `fk_prestamo_socio_idx` (`id_socio`),
  CONSTRAINT `fk_socio_prestamo` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reservas` (
  `id_reserva` int(11) NOT NULL,
  `fecha_reserva` date NOT NULL,
  `entregada` bit(1) NOT NULL,
  `id_socio` int(11) NOT NULL,
  PRIMARY KEY (`id_reserva`),
  KEY `fk_socio_reserva_idx` (`id_socio`),
  CONSTRAINT `fk_socio_reserva` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`) ON UPDATE CASCADE
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
  `id_sancion` int(11) NOT NULL,
  `id_socio` int(11) NOT NULL,
  `fecha_sancion` date NOT NULL,
  `dias_sancion` int(11) NOT NULL,
  PRIMARY KEY (`id_sancion`,`id_socio`),
  KEY `fk_sancion_socio_idx` (`id_socio`),
  CONSTRAINT `fk_socio_sancion` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
 SET character_set_client = utf8mb4 ;
CREATE TABLE `socios` (
  `id_socio` int(11) NOT NULL,
  `nombre` varchar(125) NOT NULL,
  `apellido` varchar(125) NOT NULL,
  `email` varchar(125) NOT NULL,
  `domicilio` varchar(125) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `dni` int(11) NOT NULL,
  `estado` bit(1) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_socio`),
  KEY `fk_usuario_socio_idx` (`id_usuario`),
  CONSTRAINT `fk_usuario_socio` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socios`
--

LOCK TABLES `socios` WRITE;
/*!40000 ALTER TABLE `socios` DISABLE KEYS */;
/*!40000 ALTER TABLE `socios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(125) NOT NULL,
  `password` varchar(125) NOT NULL,
  `estado` bit(1) NOT NULL,
  `tipo` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
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

-- Dump completed on 2020-05-22 14:16:16
