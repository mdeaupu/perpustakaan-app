-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dbperpustakaan
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `tanggota`
--

DROP TABLE IF EXISTS `tanggota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tanggota` (
  `IdAnggota` varchar(10) NOT NULL,
  `Nim` varchar(45) DEFAULT NULL,
  `Nama` varchar(45) DEFAULT NULL,
  `Prodi` varchar(45) DEFAULT NULL,
  `Semester` int(11) DEFAULT NULL,
  `NoTelepon` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Alamat` text DEFAULT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`IdAnggota`),
  UNIQUE KEY `Nim_UNIQUE` (`Nim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tanggota`
--

LOCK TABLES `tanggota` WRITE;
/*!40000 ALTER TABLE `tanggota` DISABLE KEYS */;
INSERT INTO `tanggota` VALUES ('AGT001','202301001','Budi Santoso','Teknik Informatika',3,'08123456789','budi@email.com','Jl. Merdeka No. 10','2025-05-30 15:36:44','2025-05-30 15:36:44'),('AGT002','202302045','Ani Rahmawati','Manajemen',5,'08129876543','ani@email.com','Jl. Sudirman No. 5','2025-05-30 15:36:44','2025-05-30 15:36:44'),('AGT003','202303123','Citra Dewi','Akuntansi',7,'08561234567','citra@email.com','Jl. Gatot Subroto No. 15','2025-05-30 15:36:44','2025-05-30 15:36:44');
/*!40000 ALTER TABLE `tanggota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teksemplar`
--

DROP TABLE IF EXISTS `teksemplar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teksemplar` (
  `IdEksemplar` int(11) DEFAULT NULL,
  `IdBuku` varchar(10) DEFAULT NULL,
  `KodeEksemplar` varchar(20) NOT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  `Status` enum('true','false') DEFAULT NULL,
  PRIMARY KEY (`KodeEksemplar`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teksemplar`
--

LOCK TABLES `teksemplar` WRITE;
/*!40000 ALTER TABLE `teksemplar` DISABLE KEYS */;
INSERT INTO `teksemplar` VALUES (1,'BUK001','BUK001.1','2025-05-30 17:49:39','2025-05-30 17:49:39','true'),(10,'BUK001','BUK001.10','2025-05-30 17:49:39','2025-05-30 17:49:39','true'),(11,'BUK001','BUK001.11','2025-05-30 17:49:39','2025-05-30 17:49:39','false'),(12,'BUK001','BUK001.12','2025-05-30 17:49:39','2025-05-30 17:49:39','true'),(2,'BUK001','BUK001.2','2025-05-30 17:49:39','2025-05-30 17:49:39','true'),(3,'BUK001','BUK001.3','2025-05-30 17:49:39','2025-05-30 17:49:39','true'),(4,'BUK001','BUK001.4','2025-05-30 17:49:39','2025-05-30 17:49:39','true'),(5,'BUK001','BUK001.5','2025-05-30 17:49:39','2025-05-30 17:49:39','false'),(6,'BUK001','BUK001.6','2025-05-30 17:49:39','2025-05-30 17:49:39','true'),(7,'BUK001','BUK001.7','2025-05-30 17:49:39','2025-05-30 17:49:39','true'),(8,'BUK001','BUK001.8','2025-05-30 17:49:39','2025-05-30 17:49:39','false'),(9,'BUK001','BUK001.9','2025-05-30 17:49:39','2025-05-30 17:49:39','false'),(1,'BUK002','BUK002.1','2025-05-30 17:50:01','2025-05-30 17:50:01','false'),(2,'BUK002','BUK002.2','2025-05-30 17:50:01','2025-05-30 17:50:01','true'),(3,'BUK002','BUK002.3','2025-05-30 17:50:01','2025-05-30 17:50:01','false'),(4,'BUK002','BUK002.4','2025-05-30 17:50:01','2025-05-30 17:50:01','true'),(5,'BUK002','BUK002.5','2025-05-30 17:50:01','2025-05-30 17:50:01','true');
/*!40000 ALTER TABLE `teksemplar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tkategori`
--

DROP TABLE IF EXISTS `tkategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tkategori` (
  `IdKategori` varchar(10) NOT NULL,
  `NamaKategori` varchar(100) DEFAULT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`IdKategori`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tkategori`
--

LOCK TABLES `tkategori` WRITE;
/*!40000 ALTER TABLE `tkategori` DISABLE KEYS */;
INSERT INTO `tkategori` VALUES ('KTG001','Teknologi Informasi','2025-05-30 15:39:18','2025-05-30 15:39:18'),('KTG002','Manajemen Bisnis','2025-05-30 15:39:18','2025-05-30 15:39:18'),('KTG003','Sastra','2025-05-30 15:39:18','2025-05-30 15:39:18'),('KTG004','Ilmu Pengetahuan Alam','2025-05-30 15:39:18','2025-05-30 15:39:18');
/*!40000 ALTER TABLE `tkategori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tmasterbuku`
--

DROP TABLE IF EXISTS `tmasterbuku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmasterbuku` (
  `IdBuku` varchar(10) NOT NULL,
  `NamaBuku` varchar(100) DEFAULT NULL,
  `Pengarang` varchar(100) DEFAULT NULL,
  `Penerbit` varchar(100) DEFAULT NULL,
  `Jumlah` int(11) DEFAULT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  `IdKategori` varchar(10) DEFAULT NULL,
  `IdRak` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`IdBuku`),
  UNIQUE KEY `IdKategori_UNIQUE` (`IdKategori`),
  UNIQUE KEY `IdRak_UNIQUE` (`IdRak`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tmasterbuku`
--

LOCK TABLES `tmasterbuku` WRITE;
/*!40000 ALTER TABLE `tmasterbuku` DISABLE KEYS */;
INSERT INTO `tmasterbuku` VALUES ('BUK001','INFORMATIKA','PUTRA','PT PUTRA',12,'2025-05-30 17:49:39','2025-05-30 17:49:39','KTG001','RAK001'),('BUK002','MATEMATIKA','PUTRA','PT PUTRA',5,'2025-05-30 17:50:01','2025-05-30 17:50:01','KTG002','RAK002');
/*!40000 ALTER TABLE `tmasterbuku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpeminjaman`
--

DROP TABLE IF EXISTS `tpeminjaman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tpeminjaman` (
  `IdPeminjaman` varchar(45) NOT NULL,
  `IdAnggota` varchar(10) DEFAULT NULL,
  `NamaAnggota` varchar(45) DEFAULT NULL,
  `KodeEksemplar` varchar(45) DEFAULT NULL,
  `NamaBuku` varchar(45) DEFAULT NULL,
  `TanggalPinjam` datetime DEFAULT NULL,
  `TanggalJatuhTempo` datetime DEFAULT NULL,
  PRIMARY KEY (`IdPeminjaman`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpeminjaman`
--

LOCK TABLES `tpeminjaman` WRITE;
/*!40000 ALTER TABLE `tpeminjaman` DISABLE KEYS */;
INSERT INTO `tpeminjaman` VALUES ('AGT001-BUK00111-20250530','AGT001','Budi Santoso','BUK001.11','INFORMATIKA','2025-05-30 18:24:41','2025-06-06 18:24:41'),('AGT001-BUK0018-20250530','AGT001','Budi Santoso','BUK001.8','INFORMATIKA','2025-05-30 18:17:08','2025-06-06 18:17:08'),('AGT002-BUK0019-20250530','AGT002','Ani Rahmawati','BUK001.9','INFORMATIKA','2025-05-30 18:42:37','2025-06-06 18:42:37'),('AGT002-BUK0021-20250530','AGT002','Ani Rahmawati','BUK002.1','MATEMATIKA','2025-05-30 18:52:33','2025-06-06 18:52:33'),('AGT003-BUK0015-20250530','AGT003','Citra Dewi','BUK001.5','INFORMATIKA','2025-05-30 18:11:28','2025-06-06 18:11:28'),('AGT003-BUK0023-20250530','AGT003','Citra Dewi','BUK002.3','MATEMATIKA','2025-05-30 18:17:55','2025-06-06 18:17:55');
/*!40000 ALTER TABLE `tpeminjaman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trak`
--

DROP TABLE IF EXISTS `trak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trak` (
  `IdRak` varchar(10) NOT NULL,
  `NamaRak` varchar(45) DEFAULT NULL,
  `Lokasi` varchar(100) DEFAULT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`IdRak`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trak`
--

LOCK TABLES `trak` WRITE;
/*!40000 ALTER TABLE `trak` DISABLE KEYS */;
INSERT INTO `trak` VALUES ('RAK001','Rak Teknologi','Lantai 1, Area A','2025-05-30 15:40:02','2025-05-30 15:40:02'),('RAK002','Rak Manajemen','Lantai 1, Area B','2025-05-30 15:40:02','2025-05-30 15:40:02'),('RAK003','Rak Sastra','Lantai 2, Area A','2025-05-30 15:40:02','2025-05-30 15:40:02'),('RAK004','Rak IPA','Lantai 2, Area B','2025-05-30 15:40:02','2025-05-30 15:40:02');
/*!40000 ALTER TABLE `trak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trole`
--

DROP TABLE IF EXISTS `trole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trole` (
  `IdRole` int(11) NOT NULL AUTO_INCREMENT,
  `NamaRole` varchar(45) DEFAULT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`IdRole`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trole`
--

LOCK TABLES `trole` WRITE;
/*!40000 ALTER TABLE `trole` DISABLE KEYS */;
INSERT INTO `trole` VALUES (1,'Admin','2025-05-30 15:36:20','2025-05-30 15:36:20'),(2,'Staff','2025-05-30 15:36:20','2025-05-30 15:36:20');
/*!40000 ALTER TABLE `trole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tuser`
--

DROP TABLE IF EXISTS `tuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tuser` (
  `IdUser` varchar(10) NOT NULL,
  `Username` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `IdRole` int(11) unsigned DEFAULT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`IdUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tuser`
--

LOCK TABLES `tuser` WRITE;
/*!40000 ALTER TABLE `tuser` DISABLE KEYS */;
INSERT INTO `tuser` VALUES ('ADM001','admin','admin123',1,'2025-05-30 15:36:28','2025-05-30 15:36:28'),('STF001','staff','staff123',2,'2025-05-30 15:36:28','2025-05-30 15:36:28');
/*!40000 ALTER TABLE `tuser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-08 21:39:38
