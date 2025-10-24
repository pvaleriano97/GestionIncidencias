-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: gestion_incidencias
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `equipo`
--

DROP TABLE IF EXISTS `equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipo` (
  `idEquipo` int NOT NULL AUTO_INCREMENT,
  `codigoEquipo` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tipo` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `estado` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Operativo',
  PRIMARY KEY (`idEquipo`),
  UNIQUE KEY `UK_Equipo_codigoEquipo` (`codigoEquipo`),
  KEY `IDX_Equipo_tipo` (`tipo`),
  KEY `IDX_Equipo_estado` (`estado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de equipos del inventario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historialequipo`
--

DROP TABLE IF EXISTS `historialequipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historialequipo` (
  `idHistorial` int NOT NULL AUTO_INCREMENT,
  `idEquipo` int NOT NULL,
  `detalle` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idHistorial`),
  KEY `IDX_HistorialEquipo_idEquipo` (`idEquipo`),
  CONSTRAINT `FK_HistorialEquipo_Equipo` FOREIGN KEY (`idEquipo`) REFERENCES `equipo` (`idEquipo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de historial de equipos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historialequipo`
--

LOCK TABLES `historialequipo` WRITE;
/*!40000 ALTER TABLE `historialequipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `historialequipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidencia`
--

DROP TABLE IF EXISTS `incidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidencia` (
  `idIncidencia` int NOT NULL AUTO_INCREMENT,
  `fechaRegistro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `descripcion` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `estado` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Abierta',
  `idUsuario` int NOT NULL,
  `idEquipo` int NOT NULL,
  `idTecnico` int DEFAULT NULL,
  PRIMARY KEY (`idIncidencia`),
  KEY `IDX_Incidencia_estado` (`estado`),
  KEY `IDX_Incidencia_fechaRegistro` (`fechaRegistro`),
  KEY `IDX_Incidencia_idUsuario` (`idUsuario`),
  KEY `IDX_Incidencia_idEquipo` (`idEquipo`),
  KEY `IDX_Incidencia_idTecnico` (`idTecnico`),
  CONSTRAINT `FK_Incidencia_Equipo` FOREIGN KEY (`idEquipo`) REFERENCES `equipo` (`idEquipo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK_Incidencia_Tecnico` FOREIGN KEY (`idTecnico`) REFERENCES `tecnico` (`idTecnico`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_Incidencia_Usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla central de incidencias técnicas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidencia`
--

LOCK TABLES `incidencia` WRITE;
/*!40000 ALTER TABLE `incidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informetecnico`
--

DROP TABLE IF EXISTS `informetecnico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `informetecnico` (
  `idInforme` int NOT NULL AUTO_INCREMENT,
  `fechaCierre` datetime NOT NULL,
  `observaciones` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `idIncidencia` int NOT NULL,
  PRIMARY KEY (`idInforme`),
  UNIQUE KEY `UK_InformeTecnico_idIncidencia` (`idIncidencia`),
  CONSTRAINT `FK_InformeTecnico_Incidencia` FOREIGN KEY (`idIncidencia`) REFERENCES `incidencia` (`idIncidencia`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de informes técnicos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informetecnico`
--

LOCK TABLES `informetecnico` WRITE;
/*!40000 ALTER TABLE `informetecnico` DISABLE KEYS */;
/*!40000 ALTER TABLE `informetecnico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repuesto`
--

DROP TABLE IF EXISTS `repuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repuesto` (
  `idRepuesto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `costo` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idRepuesto`),
  KEY `IDX_Repuesto_nombre` (`nombre`),
  CONSTRAINT `CHK_Repuesto_costo` CHECK ((`costo` > 0)),
  CONSTRAINT `CHK_Repuesto_stock` CHECK ((`stock` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de repuestos y componentes';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repuesto`
--

LOCK TABLES `repuesto` WRITE;
/*!40000 ALTER TABLE `repuesto` DISABLE KEYS */;
/*!40000 ALTER TABLE `repuesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitudrepuesto`
--

DROP TABLE IF EXISTS `solicitudrepuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitudrepuesto` (
  `idSolicitud` int NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `fechaSolicitud` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idIncidencia` int NOT NULL,
  `idRepuesto` int NOT NULL,
  PRIMARY KEY (`idSolicitud`),
  KEY `IDX_SolicitudRepuesto_incidencia_repuesto` (`idIncidencia`,`idRepuesto`),
  KEY `IDX_SolicitudRepuesto_fechaSolicitud` (`fechaSolicitud`),
  KEY `FK_SolicitudRepuesto_Repuesto` (`idRepuesto`),
  CONSTRAINT `FK_SolicitudRepuesto_Incidencia` FOREIGN KEY (`idIncidencia`) REFERENCES `incidencia` (`idIncidencia`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_SolicitudRepuesto_Repuesto` FOREIGN KEY (`idRepuesto`) REFERENCES `repuesto` (`idRepuesto`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `CHK_SolicitudRepuesto_cantidad` CHECK ((`cantidad` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de solicitudes de repuestos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitudrepuesto`
--

LOCK TABLES `solicitudrepuesto` WRITE;
/*!40000 ALTER TABLE `solicitudrepuesto` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitudrepuesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tecnico`
--

DROP TABLE IF EXISTS `tecnico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tecnico` (
  `idTecnico` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `especialidad` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `disponibilidad` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idTecnico`),
  KEY `IDX_Tecnico_especialidad` (`especialidad`),
  KEY `IDX_Tecnico_disponibilidad` (`disponibilidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de técnicos del sistema';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tecnico`
--

LOCK TABLES `tecnico` WRITE;
/*!40000 ALTER TABLE `tecnico` DISABLE KEYS */;
/*!40000 ALTER TABLE `tecnico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `correo` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `contrasena` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rol` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `area` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `UK_Usuario_correo` (`correo`),
  KEY `IDX_Usuario_area` (`area`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de usuarios del sistema';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Pedro','','frocab@bcp.com.pe','@B123','','TI');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-23 19:58:00
