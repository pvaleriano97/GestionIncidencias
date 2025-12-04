CREATE DATABASE  IF NOT EXISTS `gestion_incidencias` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gestion_incidencias`;
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
-- Table structure for table `auditoria_login`
--

DROP TABLE IF EXISTS `auditoria_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditoria_login` (
  `id_auditoria` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int DEFAULT NULL,
  `correo_usuario` varchar(100) DEFAULT NULL,
  `nombre_usuario` varchar(100) DEFAULT NULL,
  `rol_usuario` varchar(50) DEFAULT NULL,
  `fecha_login` datetime DEFAULT CURRENT_TIMESTAMP,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` varchar(500) DEFAULT NULL,
  `status_login` varchar(20) DEFAULT NULL,
  `codigo_2fa` varchar(10) DEFAULT NULL,
  `intentos_fallidos` int DEFAULT '0',
  `session_id` varchar(100) DEFAULT NULL,
  `navegador` varchar(100) DEFAULT NULL,
  `sistema_operativo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_auditoria`),
  KEY `idx_auditoria_fecha` (`fecha_login`),
  KEY `idx_auditoria_usuario` (`correo_usuario`),
  KEY `idx_auditoria_status` (`status_login`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria_login`
--

LOCK TABLES `auditoria_login` WRITE;
/*!40000 ALTER TABLE `auditoria_login` DISABLE KEYS */;
INSERT INTO `auditoria_login` VALUES (1,1,'casavilcapedro@gmail.com','Pedro Valeriano','admin','2025-12-04 20:07:30','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36','CREDENCIALES_VALIDAS',NULL,0,'4BC2C3CEE146B8BFC949E4CE686FF809','Google Chrome','Windows'),(2,1,'casavilcapedro@gmail.com','Pedro Valeriano','admin','2025-12-04 20:07:30','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36','2FA_GENERADO','197172',0,'4BC2C3CEE146B8BFC949E4CE686FF809','Google Chrome','Windows'),(3,1,'casavilcapedro@gmail.com','Pedro Valeriano','admin','2025-12-04 20:08:16','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36','2FA_EXITOSO','197172',0,'4BC2C3CEE146B8BFC949E4CE686FF809','Google Chrome','Windows'),(4,1,'casavilcapedro@gmail.com','Pedro Valeriano','admin','2025-12-04 20:08:57','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36','CREDENCIALES_VALIDAS',NULL,0,'01E490D9D98DF491C1AE78DACCCB1515','Google Chrome','Windows'),(5,1,'casavilcapedro@gmail.com','Pedro Valeriano','admin','2025-12-04 20:08:57','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36','2FA_GENERADO','036953',0,'01E490D9D98DF491C1AE78DACCCB1515','Google Chrome','Windows'),(6,1,'casavilcapedro@gmail.com','Pedro Valeriano','admin','2025-12-04 20:09:10','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36','2FA_EXITOSO','036953',0,'01E490D9D98DF491C1AE78DACCCB1515','Google Chrome','Windows'),(7,1,'casavilcapedro@gmail.com','Pedro Valeriano','admin','2025-12-04 20:09:48','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36','CREDENCIALES_VALIDAS',NULL,0,'AA09052D674BBD0A34EE7057474DCE00','Google Chrome','Windows'),(8,1,'casavilcapedro@gmail.com','Pedro Valeriano','admin','2025-12-04 20:09:48','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36','2FA_GENERADO','684165',0,'AA09052D674BBD0A34EE7057474DCE00','Google Chrome','Windows'),(9,1,'casavilcapedro@gmail.com','Pedro Valeriano','admin','2025-12-04 20:09:52','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36','2FA_FALLIDO','231232',0,'AA09052D674BBD0A34EE7057474DCE00','Google Chrome','Windows');
/*!40000 ALTER TABLE `auditoria_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipo`
--

DROP TABLE IF EXISTS `equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipo` (
  `idEquipo` int NOT NULL AUTO_INCREMENT,
  `codigoEquipo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `tipo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `estado` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Operativo',
  PRIMARY KEY (`idEquipo`),
  UNIQUE KEY `UK_Equipo_codigoEquipo` (`codigoEquipo`),
  KEY `IDX_Equipo_tipo` (`tipo`),
  KEY `IDX_Equipo_estado` (`estado`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de equipos del inventario';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
INSERT INTO `equipo` VALUES (1,'EQ001','Laptop','Operativo'),(2,'EQ002','Impresora','En reparación'),(3,'EQ003','PC de Escritorio','Operativo'),(4,'EQ004','Servidor','Mantenimiento'),(5,'EQ005','Router','Operativo'),(6,'EQ006','Switch','Operativo'),(7,'EQ007','Proyector','En reparación'),(8,'EQ008','Scanner','Operativo'),(9,'EQ009','Tablet','Operativo'),(10,'EQ010','Monitor','Dañado'),(11,'EQ011','MOUSE','Activo'),(12,'EQ12','EPSON','En Reparación'),(13,'EQ-JUNIT-1764648405513','Cámara','Operativo'),(15,'EQ-JUNIT-UPD-1764648405675','Switch','Operativo'),(16,'EQ-JUNIT-BUSCAR-1764648405728','Grabador','Operativo'),(17,'EQ-JUNIT-1764648424195','Cámara','Operativo'),(19,'EQ-JUNIT-UPD-1764648424308','Switch','Operativo'),(20,'EQ-JUNIT-BUSCAR-1764648424346','Grabador','Operativo'),(21,'EQ-JUNIT-1764648701094','Cámara','Operativo'),(23,'EQ-JUNIT-UPD-1764648701204','Switch','Operativo'),(24,'EQ-JUNIT-BUSCAR-1764648701249','Grabador','Operativo'),(25,'EQ-JUNIT-1764648714255','Cámara','Operativo'),(27,'EQ-JUNIT-UPD-1764648714356','Switch','Operativo'),(28,'EQ-JUNIT-BUSCAR-1764648714397','Grabador','Operativo'),(29,'EQ-JUNIT-1764648725868','Cámara','Operativo'),(31,'EQ-JUNIT-UPD-1764648725987','Switch','Operativo'),(32,'EQ-JUNIT-BUSCAR-1764648726032','Grabador','Operativo'),(33,'EQ-JUNIT-1764648842203','Cámara','Operativo'),(35,'EQ-JUNIT-UPD-1764648842293','Switch','Operativo'),(36,'EQ-JUNIT-BUSCAR-1764648842334','Grabador','Operativo'),(37,'EQ-JUNIT-1764648854115','Cámara','Operativo'),(39,'EQ-JUNIT-UPD-1764648854228','Switch','Operativo'),(40,'EQ-JUNIT-BUSCAR-1764648854274','Grabador','Operativo'),(41,'EQ-JUNIT-1764649109580','Cámara','Operativo'),(43,'EQ-JUNIT-UPD-1764649109673','Switch','Operativo'),(44,'EQ-JUNIT-BUSCAR-1764649109711','Grabador','Operativo'),(45,'EQ-JUNIT-1764649220336','Cámara','Operativo'),(47,'EQ-JUNIT-UPD-1764649220435','Switch','Operativo'),(48,'EQ-JUNIT-BUSCAR-1764649220474','Grabador','Operativo'),(49,'EQ-JUNIT-1764649586123','Cámara','Operativo'),(51,'EQ-JUNIT-UPD-1764649586226','Switch','Operativo'),(52,'EQ-JUNIT-BUSCAR-1764649586269','Grabador','Operativo'),(53,'EQ-JUNIT-1764649803347','Cámara','Operativo'),(55,'EQ-JUNIT-UPD-1764649803502','Switch','Operativo'),(56,'EQ-JUNIT-BUSCAR-1764649803556','Grabador','Operativo'),(57,'EQ-JUNIT-1764649838237','Cámara','Operativo'),(59,'EQ-JUNIT-UPD-1764649838349','Switch','Operativo'),(60,'EQ-JUNIT-BUSCAR-1764649838390','Grabador','Operativo'),(61,'EQ-JUNIT-1764649986206','Cámara','Operativo'),(63,'EQ-JUNIT-UPD-1764649986300','Switch','Operativo'),(64,'EQ-JUNIT-BUSCAR-1764649986341','Grabador','Operativo'),(65,'EQ-JUNIT-1764650058386','Cámara','Operativo'),(67,'EQ-JUNIT-UPD-1764650058513','Switch','Operativo'),(68,'EQ-JUNIT-BUSCAR-1764650058555','Grabador','Operativo'),(69,'EQ-JUNIT-1764650126712','Cámara','Operativo'),(71,'EQ-JUNIT-UPD-1764650126848','Switch','Operativo'),(72,'EQ-JUNIT-BUSCAR-1764650126903','Grabador','Operativo'),(73,'EQ-JUNIT-1764650295104','Cámara','Operativo'),(75,'EQ-JUNIT-UPD-1764650295212','Switch','Operativo'),(76,'EQ-JUNIT-BUSCAR-1764650295253','Grabador','Operativo'),(77,'EQ-JUNIT-1764650315819','Cámara','Operativo'),(79,'EQ-JUNIT-UPD-1764650315972','Switch','Operativo'),(80,'EQ-JUNIT-BUSCAR-1764650316038','Grabador','Operativo'),(81,'EQ-JUNIT-1764687766761','Cámara','Operativo'),(83,'EQ-JUNIT-UPD-1764687766950','Switch','Operativo'),(84,'EQ-JUNIT-BUSCAR-1764687767017','Grabador','Operativo'),(85,'EQ-JUNIT-1764815051819','Cámara','Operativo'),(87,'EQ-JUNIT-UPD-1764815051950','Switch','Operativo'),(88,'EQ-JUNIT-BUSCAR-1764815051998','Grabador','Operativo'),(89,'EQ-JUNIT-1764815068687','Cámara','Operativo'),(91,'EQ-JUNIT-UPD-1764815068838','Switch','Operativo'),(92,'EQ-JUNIT-BUSCAR-1764815068895','Grabador','Operativo'),(93,'EQ-JUNIT-1764878646762','Cámara','Operativo'),(95,'EQ-JUNIT-UPD-1764878646856','Switch','Operativo'),(96,'EQ-JUNIT-BUSCAR-1764878646894','Grabador','Operativo'),(97,'EQ-JUNIT-1764878673957','Cámara','Operativo'),(99,'EQ-JUNIT-UPD-1764878674090','Switch','Operativo'),(100,'EQ-JUNIT-BUSCAR-1764878674140','Grabador','Operativo'),(101,'EQ-JUNIT-1764878704041','Cámara','Operativo'),(103,'EQ-JUNIT-UPD-1764878704173','Switch','Operativo'),(104,'EQ-JUNIT-BUSCAR-1764878704223','Grabador','Operativo');
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
  `detalle` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idHistorial`),
  KEY `IDX_HistorialEquipo_idEquipo` (`idEquipo`),
  CONSTRAINT `FK_HistorialEquipo_Equipo` FOREIGN KEY (`idEquipo`) REFERENCES `equipo` (`idEquipo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de historial de equipos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historialequipo`
--

LOCK TABLES `historialequipo` WRITE;
/*!40000 ALTER TABLE `historialequipo` DISABLE KEYS */;
INSERT INTO `historialequipo` VALUES (1,1,'cambio la tarjeta grafica '),(2,4,'actualizo la memoria ram');
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
  `descripcion` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `estado` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Abierta',
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
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla central de incidencias técnicas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidencia`
--

LOCK TABLES `incidencia` WRITE;
/*!40000 ALTER TABLE `incidencia` DISABLE KEYS */;
INSERT INTO `incidencia` VALUES (2,'2025-10-25 19:27:54','laptop malogrado ','Abierta',1,1,2),(3,'2025-10-26 10:48:14',' impresora atasco de papel \r                     \r   ','Abierta',1,2,2),(5,'2025-10-26 11:58:52','router malogrado ','Cerrada',1,2,2),(6,'2025-10-26 21:04:21','caida de la red  pedro','Cerrada',1,4,2),(7,'2025-10-26 21:05:38',' pantalla negra \r\n            ','En Proceso',3,10,1),(8,'2025-10-26 21:06:33','malogrado el switch','En Proceso',3,6,1),(9,'2025-10-27 16:52:41','MALOGRADO','Abierta',4,11,1),(10,'2025-10-30 20:43:08','ROUTER NO PRENDE ','Cerrada',4,5,2),(11,'2025-11-23 20:14:02','SE MALO EL TECLADO ','Cerrada',3,4,1),(12,'2025-11-23 23:41:34',' MALOGRADO\r\n                  ','Abierta',1,1,2),(13,'2025-11-24 20:25:59','malogrado            \r\n               ','Abierta',1,5,2),(14,'2025-12-01 23:06:45','INCIDENCIA JUNIT OK','Abierto',1,1,1),(15,'2025-12-01 23:06:45','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(16,'2025-12-01 23:06:45','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(17,'2025-12-01 23:06:45','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(18,'2025-12-01 23:11:41','INCIDENCIA JUNIT OK','Abierto',1,1,1),(19,'2025-12-01 23:11:41','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(20,'2025-12-01 23:11:41','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(21,'2025-12-01 23:11:41','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(22,'2025-12-01 23:11:54','INCIDENCIA JUNIT OK','Abierto',1,1,1),(23,'2025-12-01 23:11:54','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(24,'2025-12-01 23:11:54','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(25,'2025-12-01 23:11:54','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(26,'2025-12-01 23:12:06','INCIDENCIA JUNIT OK','Abierto',1,1,1),(27,'2025-12-01 23:12:06','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(28,'2025-12-01 23:12:06','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(29,'2025-12-01 23:12:06','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(30,'2025-12-01 23:14:02','INCIDENCIA JUNIT OK','Abierto',1,1,1),(31,'2025-12-01 23:14:02','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(32,'2025-12-01 23:14:02','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(33,'2025-12-01 23:14:02','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(34,'2025-12-01 23:14:14','INCIDENCIA JUNIT OK','Abierto',1,1,1),(35,'2025-12-01 23:14:14','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(36,'2025-12-01 23:14:14','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(37,'2025-12-01 23:14:14','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(38,'2025-12-01 23:18:29','INCIDENCIA JUNIT OK','Abierto',1,1,1),(39,'2025-12-01 23:18:29','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(40,'2025-12-01 23:18:29','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(41,'2025-12-01 23:18:29','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(42,'2025-12-01 23:20:20','INCIDENCIA JUNIT OK','Abierto',1,1,1),(43,'2025-12-01 23:20:20','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(44,'2025-12-01 23:20:20','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(45,'2025-12-01 23:20:20','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(46,'2025-12-01 23:26:26','INCIDENCIA JUNIT OK','Abierto',1,1,1),(47,'2025-12-01 23:26:26','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(48,'2025-12-01 23:26:26','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(49,'2025-12-01 23:26:26','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(50,'2025-12-01 23:30:03','INCIDENCIA JUNIT OK','Abierto',1,1,1),(51,'2025-12-01 23:30:03','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(52,'2025-12-01 23:30:03','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(53,'2025-12-01 23:30:03','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(54,'2025-12-01 23:30:38','INCIDENCIA JUNIT OK','Abierto',1,1,1),(55,'2025-12-01 23:30:38','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(56,'2025-12-01 23:30:38','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(57,'2025-12-01 23:30:38','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(58,'2025-12-01 23:33:06','INCIDENCIA JUNIT OK','Abierto',1,1,1),(59,'2025-12-01 23:33:06','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(60,'2025-12-01 23:33:06','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(61,'2025-12-01 23:33:06','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(62,'2025-12-01 23:34:18','INCIDENCIA JUNIT OK','Abierto',1,1,1),(63,'2025-12-01 23:34:18','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(64,'2025-12-01 23:34:18','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(65,'2025-12-01 23:34:18','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(66,'2025-12-01 23:35:26','INCIDENCIA JUNIT OK','Abierto',1,1,1),(67,'2025-12-01 23:35:27','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(68,'2025-12-01 23:35:27','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(69,'2025-12-01 23:35:27','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(70,'2025-12-01 23:38:15','INCIDENCIA JUNIT OK','Abierto',1,1,1),(71,'2025-12-01 23:38:15','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(72,'2025-12-01 23:38:15','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(73,'2025-12-01 23:38:15','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(74,'2025-12-01 23:38:36','INCIDENCIA JUNIT OK','Abierto',1,1,1),(75,'2025-12-01 23:38:36','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(76,'2025-12-01 23:38:36','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(77,'2025-12-01 23:38:36','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(78,'2025-12-03 21:24:12','INCIDENCIA JUNIT OK','Abierto',1,1,1),(79,'2025-12-03 21:24:12','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(80,'2025-12-03 21:24:12','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(81,'2025-12-03 21:24:12','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(82,'2025-12-03 21:24:28','INCIDENCIA JUNIT OK','Abierto',1,1,1),(83,'2025-12-03 21:24:29','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(84,'2025-12-03 21:24:29','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(85,'2025-12-03 21:24:29','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(86,'2025-12-04 15:04:06','INCIDENCIA JUNIT OK','Abierto',1,1,1),(87,'2025-12-04 15:04:06','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(88,'2025-12-04 15:04:06','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(89,'2025-12-04 15:04:07','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1),(90,'2025-12-04 15:05:04','INCIDENCIA JUNIT OK','Abierto',1,1,1),(91,'2025-12-04 15:05:04','INC JUNIT BUSCAR POR ID','Abierto',1,1,1),(92,'2025-12-04 15:05:04','INC JUNIT ESTADO CERRADO','Cerrado',1,1,1),(93,'2025-12-04 15:05:04','INC JUNIT ESTADO EN PROCESO','En Proceso',1,1,1);
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
  `observaciones` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `idIncidencia` int NOT NULL,
  `idTecnico` int NOT NULL,
  PRIMARY KEY (`idInforme`),
  UNIQUE KEY `UK_InformeTecnico_idIncidencia` (`idIncidencia`),
  CONSTRAINT `FK_InformeTecnico_Incidencia` FOREIGN KEY (`idIncidencia`) REFERENCES `incidencia` (`idIncidencia`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de informes técnicos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informetecnico`
--

LOCK TABLES `informetecnico` WRITE;
/*!40000 ALTER TABLE `informetecnico` DISABLE KEYS */;
INSERT INTO `informetecnico` VALUES (1,'2025-03-02 00:00:00','reparaciones',3,1),(2,'2025-11-13 04:10:00','                        concluido\r\n                    ',7,11),(3,'2025-11-14 16:32:00','                                                concluido\r\n                    \r\n                    ',10,11),(4,'2025-11-12 04:46:00','                        concluido          \r\n                    ',9,1);
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
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `costo` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idRepuesto`),
  KEY `IDX_Repuesto_nombre` (`nombre`),
  CONSTRAINT `CHK_Repuesto_costo` CHECK ((`costo` > 0)),
  CONSTRAINT `CHK_Repuesto_stock` CHECK ((`stock` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de repuestos y componentes';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repuesto`
--

LOCK TABLES `repuesto` WRITE;
/*!40000 ALTER TABLE `repuesto` DISABLE KEYS */;
INSERT INTO `repuesto` VALUES (1,'Pantalla de laptop 14',5,450.00),(2,'Cable de impresora USB-B',13,12.50),(3,'Cabezal de impresión HP',5,150.00),(4,'Tarjeta de red PCIe',10,45.00),(5,'Router TP-Link AC1200',7,220.00),(6,'Fuente de poder 500W',12,95.00),(7,'Módulo RAM 8GB DDR4',20,75.00),(8,'Disco SSD 480GB',15,110.00),(9,'Antena WiFi USB',33,35.00),(10,'Pantalla LED 24\"',6,180.00),(11,'Tóner HP 85A',18,65.00),(12,'Switch de red 8 puertos',10,140.00),(13,'Cámara web 1080p',20,60.00),(14,'Cable HDMI 2.0',40,10.00),(15,'Batería de laptop universal',10,160.00);
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de solicitudes de repuestos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitudrepuesto`
--

LOCK TABLES `solicitudrepuesto` WRITE;
/*!40000 ALTER TABLE `solicitudrepuesto` DISABLE KEYS */;
INSERT INTO `solicitudrepuesto` VALUES (1,1,'2025-11-15 19:35:31',2,1),(2,2,'2025-11-15 19:36:14',3,2),(7,1,'2025-11-15 05:00:00',5,6),(8,1,'2025-11-15 05:00:00',7,1),(9,2,'2025-11-04 05:00:00',2,1),(11,7,'2025-11-16 13:36:00',10,2),(15,2,'2025-11-18 01:53:00',6,9),(16,1,'2025-11-18 02:03:00',2,1),(17,1,'2025-11-18 01:29:00',2,1),(18,3,'2025-11-18 01:50:00',2,1);
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
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `especialidad` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `disponibilidad` tinyint(1) NOT NULL DEFAULT '1',
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`idTecnico`),
  KEY `IDX_Tecnico_especialidad` (`especialidad`),
  KEY `IDX_Tecnico_disponibilidad` (`disponibilidad`),
  KEY `FK_Tecnico_Usuario` (`idUsuario`),
  CONSTRAINT `FK_Tecnico_Usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de técnicos del sistema';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tecnico`
--

LOCK TABLES `tecnico` WRITE;
/*!40000 ALTER TABLE `tecnico` DISABLE KEYS */;
INSERT INTO `tecnico` VALUES (1,'Jean Pierre','Hardware',1,3),(2,'Fernando','TI',1,4),(13,'juan','ti',1,6);
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
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `correo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `contrasena` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `rol` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `area` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `UK_Usuario_correo` (`correo`),
  KEY `IDX_Usuario_area` (`area`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla de usuarios del sistema';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Pedro','Valeriano','casavilcapedro@gmail.com','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','admin','TI'),(2,'Maria ','sandoval','maria.sv@bcp.com.pe','701a15064b9d605c98dfb74708d9e1abfcd59d083eb4332c12129bf0c97d5496','admin','contabilidad'),(3,'Jean Pierre','Rubio','jean.rubio@bcp.com.pe','701a15064b9d605c98dfb74708d9e1abfcd59d083eb4332c12129bf0c97d5496','tecnico','recursos humanos '),(4,'Fernando','Roca ','frernandoroca@bcp.com.pe','701a15064b9d605c98dfb74708d9e1abfcd59d083eb4332c12129bf0c97d5496','tecnico','contabilidad'),(6,'juan','tantalean','tantalean@bcp.com.pe','701a15064b9d605c98dfb74708d9e1abfcd59d083eb4332c12129bf0c97d5496','tecnico','ti'),(7,'Lucas','Santana','lucas.santana@bcp.com.pe','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','admin','TI');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'gestion_incidencias'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_equipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_actualizar_equipo`(
    IN p_idEquipo INT,
    IN p_codigoEquipo VARCHAR(50),
    IN p_tipo VARCHAR(50),
    IN p_estado VARCHAR(50)
)
BEGIN
    UPDATE equipo SET
        codigoEquipo = p_codigoEquipo,
        tipo = p_tipo,
        estado = p_estado
    WHERE idEquipo = p_idEquipo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_historial_equipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_actualizar_historial_equipo`(
    IN p_idHistorial INT,
    IN p_idEquipo INT,
    IN p_detalle VARCHAR(150)
)
BEGIN
    UPDATE historialequipo
    SET idEquipo = p_idEquipo,
        detalle  = p_detalle
    WHERE idHistorial = p_idHistorial;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_incidencia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_actualizar_incidencia`(
    IN p_idIncidencia INT,
    IN p_descripcion VARCHAR(100),
    IN p_estado VARCHAR(60),
    IN p_idUsuario INT,
    IN p_idEquipo INT,
    IN p_idTecnico INT
)
BEGIN
    UPDATE incidencia
    SET 
        descripcion = p_descripcion,
        estado = p_estado,
        idUsuario = p_idUsuario,
        idEquipo = p_idEquipo,
        idTecnico = IF(p_idTecnico IS NOT NULL, p_idTecnico, NULL)
    WHERE idIncidencia = p_idIncidencia;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_informe_tecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_actualizar_informe_tecnico`(
    IN p_idInforme INT,
    IN p_fechaCierre DATETIME,
    IN p_observaciones VARCHAR(150),
    IN p_idIncidencia INT,
    IN p_idTecnico INT
)
BEGIN
    UPDATE informetecnico
    SET fechaCierre = p_fechaCierre,
        observaciones = p_observaciones,
        idIncidencia = p_idIncidencia,
        idTecnico = p_idTecnico
    WHERE idInforme = p_idInforme;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_repuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_actualizar_repuesto`(
    IN p_idRepuesto INT,
    IN p_nombre VARCHAR(50),
    IN p_stock INT,
    IN p_costo DECIMAL(10,2)
)
BEGIN
    UPDATE repuesto
    SET nombre = p_nombre,
        stock = p_stock,
        costo = p_costo
    WHERE idRepuesto = p_idRepuesto;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_tecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_actualizar_tecnico`(
    IN p_idTecnico INT,
    IN p_nombre VARCHAR(50),
    IN p_especialidad VARCHAR(50),
    IN p_disponibilidad TINYINT
)
BEGIN
    UPDATE tecnico
    SET nombre = p_nombre,
        especialidad = p_especialidad,
        disponibilidad = p_disponibilidad
    WHERE idTecnico = p_idTecnico;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_actualizar_usuario`(
    IN p_idUsuario INT,
    IN p_nombre VARCHAR(50),
    IN p_apellido VARCHAR(45),
    IN p_correo VARCHAR(50),
    IN p_contrasena VARCHAR(500),
    IN p_rol VARCHAR(50),
    IN p_area VARCHAR(50)
)
BEGIN
    UPDATE usuario
    SET nombre = p_nombre,
        apellido = p_apellido,
        correo = p_correo,
        contrasena = p_contrasena,
        rol = p_rol,
        area = p_area
    WHERE idUsuario = p_idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_contar_repuestos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_contar_repuestos`(
    IN p_busqueda VARCHAR(50)
)
BEGIN
    SELECT COUNT(*) AS total
    FROM repuesto
    WHERE nombre LIKE CONCAT('%', p_busqueda, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_equipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_eliminar_equipo`(
    IN p_idEquipo INT
)
BEGIN
    DELETE FROM equipo WHERE idEquipo = p_idEquipo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_historial_equipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_eliminar_historial_equipo`(
    IN p_idHistorial INT
)
BEGIN
    DELETE FROM historialequipo WHERE idHistorial = p_idHistorial;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_incidencia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_eliminar_incidencia`(
    IN p_idIncidencia INT
)
BEGIN
    DELETE FROM incidencia
    WHERE idIncidencia = p_idIncidencia;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_informe_tecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_eliminar_informe_tecnico`(
    IN p_idInforme INT
)
BEGIN
    DELETE FROM informetecnico WHERE idInforme = p_idInforme;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_repuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_eliminar_repuesto`(
    IN p_id INT
)
BEGIN
    DELETE FROM repuesto
    WHERE idRepuesto = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_tecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_eliminar_tecnico`(
    IN p_idTecnico INT
)
BEGIN
    DELETE FROM tecnico WHERE idTecnico = p_idTecnico;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_eliminar_usuario`(
    IN p_idUsuario INT
)
BEGIN
    DELETE FROM usuario WHERE idUsuario = p_idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_equipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_insertar_equipo`(
    IN p_codigoEquipo VARCHAR(50),
    IN p_tipo VARCHAR(50),
    IN p_estado VARCHAR(50)
)
BEGIN
    INSERT INTO equipo (codigoEquipo, tipo, estado)
    VALUES (p_codigoEquipo, p_tipo, p_estado);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_historial_equipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_insertar_historial_equipo`(
    IN p_idEquipo INT,
    IN p_detalle VARCHAR(150)
)
BEGIN
    INSERT INTO historialequipo(idEquipo, detalle)
    VALUES(p_idEquipo, p_detalle);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_incidencia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_insertar_incidencia`(
    IN p_descripcion VARCHAR(100),
    IN p_estado VARCHAR(60),
    IN p_idUsuario INT,
    IN p_idEquipo INT,
    IN p_idTecnico INT
)
BEGIN
    INSERT INTO incidencia (
        descripcion,
        estado,
        fechaRegistro,
        idUsuario,
        idEquipo,
        idTecnico
    )
    VALUES (
        p_descripcion,
        p_estado,
        NOW(),
        p_idUsuario,
        p_idEquipo,
        IF(p_idTecnico IS NOT NULL, p_idTecnico, NULL)
    );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_informe_tecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_insertar_informe_tecnico`(
    IN p_fechaCierre DATETIME,
    IN p_observaciones VARCHAR(150),
    IN p_idIncidencia INT,
    IN p_idTecnico INT
)
BEGIN
    INSERT INTO informetecnico (fechaCierre, observaciones, idIncidencia, idTecnico)
    VALUES (p_fechaCierre, p_observaciones, p_idIncidencia, p_idTecnico);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_repuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_insertar_repuesto`(
    IN p_nombre VARCHAR(50),
    IN p_stock INT,
    IN p_costo DECIMAL(10,2)
)
BEGIN
    INSERT INTO repuesto(nombre, stock, costo)
    VALUES (p_nombre, p_stock, p_costo);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_tecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_insertar_tecnico`(
    IN p_nombre VARCHAR(50),
    IN p_especialidad VARCHAR(50),
    IN p_disponibilidad TINYINT
)
BEGIN
    INSERT INTO tecnico (nombre, especialidad, disponibilidad)
    VALUES (p_nombre, p_especialidad, p_disponibilidad);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insertar_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_insertar_usuario`(
    IN p_nombre VARCHAR(50),
    IN p_apellido VARCHAR(45),
    IN p_correo VARCHAR(50),
    IN p_contrasena VARCHAR(500),
    IN p_rol VARCHAR(50),
    IN p_area VARCHAR(50)
)
BEGIN
    INSERT INTO usuario (nombre, apellido, correo, contrasena, rol, area)
    VALUES (p_nombre, p_apellido, p_correo, p_contrasena, p_rol, p_area);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_historial_equipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_listar_historial_equipo`()
BEGIN
    SELECT h.idHistorial, h.idEquipo, h.detalle,
           e.codigoEquipo, e.tipo AS tipoEquipo
    FROM historialequipo h
    INNER JOIN equipo e ON h.idEquipo = e.idEquipo
    ORDER BY h.idHistorial DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_incidencias` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_listar_incidencias`()
BEGIN
    SELECT idIncidencia
    FROM incidencia
    ORDER BY idIncidencia DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_informe_tecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_listar_informe_tecnico`()
BEGIN
    SELECT it.idInforme,
           it.fechaCierre,
           it.observaciones,
           it.idIncidencia,
           it.idTecnico,
           i.descripcion AS descripcionIncidencia,
           t.nombre AS nombreTecnico
    FROM informetecnico it
    LEFT JOIN incidencia i ON i.idIncidencia = it.idIncidencia
    LEFT JOIN tecnico t ON t.idTecnico = it.idTecnico
    ORDER BY it.fechaCierre DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_repuestos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_listar_repuestos`(
    IN p_busqueda VARCHAR(50)
)
BEGIN
    SELECT *
    FROM repuesto
    WHERE nombre LIKE CONCAT('%', p_busqueda, '%')
    ORDER BY nombre ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_tecnicos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_listar_tecnicos`()
BEGIN
    SELECT idTecnico, nombre, especialidad, disponibilidad
    FROM tecnico
    ORDER BY idTecnico DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_historial_equipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_obtener_historial_equipo`(
    IN p_idHistorial INT
)
BEGIN
    SELECT h.idHistorial, h.idEquipo, h.detalle,
           e.codigoEquipo, e.tipo AS tipoEquipo
    FROM historialequipo h
    INNER JOIN equipo e ON h.idEquipo = e.idEquipo
    WHERE h.idHistorial = p_idHistorial;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_informe_tecnico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_obtener_informe_tecnico`(
    IN p_idInforme INT
)
BEGIN
    SELECT it.idInforme,
           it.fechaCierre,
           it.observaciones,
           it.idIncidencia,
           it.idTecnico,
           i.descripcion AS descripcionIncidencia,
           t.nombre AS nombreTecnico
    FROM informetecnico it
    LEFT JOIN incidencia i ON i.idIncidencia = it.idIncidencia
    LEFT JOIN tecnico t ON t.idTecnico = it.idTecnico
    WHERE it.idInforme = p_idInforme;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_repuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_obtener_repuesto`(
    IN p_id INT
)
BEGIN
    SELECT *
    FROM repuesto
    WHERE idRepuesto = p_id;
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

-- Dump completed on 2025-12-04 15:13:18
