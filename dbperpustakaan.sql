-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
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
  `NamaAnggota` varchar(100) DEFAULT NULL,
  `Alamat` text DEFAULT NULL,
  `NoTelepon` varchar(15) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `IdRole` int(11) DEFAULT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`IdAnggota`),
  KEY `IdRole` (`IdRole`),
  CONSTRAINT `tanggota_ibfk_1` FOREIGN KEY (`IdRole`) REFERENCES `trole` (`IdRole`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tanggota`
--

LOCK TABLES `tanggota` WRITE;
/*!40000 ALTER TABLE `tanggota` DISABLE KEYS */;
INSERT INTO `tanggota` VALUES ('AG001','John Doe','Jl. Contoh No. 123','081234567890','john.doe@example.com','johndoe','password123',1,'2023-10-26 10:00:00','2023-10-26 10:00:00'),('AG002','Doe John','Jl. Contoh No. 123','081234567890','john.doe@example.com','doejohn','password123',2,'2023-10-26 10:00:00','2023-10-26 10:00:00');
/*!40000 ALTER TABLE `tanggota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tdetailpeminjaman`
--

DROP TABLE IF EXISTS `tdetailpeminjaman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tdetailpeminjaman` (
  `IdDetailPeminjaman` varchar(10) NOT NULL,
  `IdPeminjaman` varchar(10) DEFAULT NULL,
  `IdEksemplar` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdDetailPeminjaman`),
  KEY `IdPeminjaman` (`IdPeminjaman`),
  KEY `IdEksemplar` (`IdEksemplar`),
  CONSTRAINT `tdetailpeminjaman_ibfk_1` FOREIGN KEY (`IdPeminjaman`) REFERENCES `tpeminjaman` (`IdPeminjaman`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tdetailpeminjaman`
--

LOCK TABLES `tdetailpeminjaman` WRITE;
/*!40000 ALTER TABLE `tdetailpeminjaman` DISABLE KEYS */;
/*!40000 ALTER TABLE `tdetailpeminjaman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teksemplar`
--

DROP TABLE IF EXISTS `teksemplar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teksemplar` (
  `IdEksemplar` int(11) NOT NULL,
  `IdBuku` varchar(10) DEFAULT NULL,
  `KodeEksemplar` varchar(20) NOT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`KodeEksemplar`),
  KEY `IdBuku` (`IdBuku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teksemplar`
--

LOCK TABLES `teksemplar` WRITE;
/*!40000 ALTER TABLE `teksemplar` DISABLE KEYS */;
INSERT INTO `teksemplar` VALUES (1,'BUK001','BUK001.1','2025-05-16 18:38:43','2025-05-16 18:38:43'),(10,'BUK001','BUK001.10','2025-05-16 18:38:43','2025-05-16 18:38:43'),(11,'BUK001','BUK001.11','2025-05-16 18:45:13','2025-05-16 18:45:13'),(12,'BUK001','BUK001.12','2025-05-16 18:45:13','2025-05-16 18:45:13'),(13,'BUK001','BUK001.13','2025-05-16 19:17:00','2025-05-16 19:17:00'),(2,'BUK001','BUK001.2','2025-05-16 18:38:43','2025-05-16 18:38:43'),(3,'BUK001','BUK001.3','2025-05-16 18:38:43','2025-05-16 18:38:43'),(4,'BUK001','BUK001.4','2025-05-16 18:38:43','2025-05-16 18:38:43'),(5,'BUK001','BUK001.5','2025-05-16 18:38:43','2025-05-16 18:38:43'),(6,'BUK001','BUK001.6','2025-05-16 18:38:43','2025-05-16 18:38:43'),(8,'BUK001','BUK001.8','2025-05-16 18:38:43','2025-05-16 18:38:43'),(9,'BUK001','BUK001.9','2025-05-16 18:38:43','2025-05-16 18:38:43'),(1,'BUK002','BUK002.1','2025-05-16 18:38:55','2025-05-16 18:38:55'),(2,'BUK002','BUK002.2','2025-05-16 18:38:55','2025-05-16 18:38:55'),(3,'BUK002','BUK002.3','2025-05-16 18:38:55','2025-05-16 18:38:55'),(4,'BUK002','BUK002.4','2025-05-16 18:38:55','2025-05-16 18:38:55'),(5,'BUK002','BUK002.5','2025-05-16 18:38:55','2025-05-16 18:38:55');
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
  `NamaKategori` varchar(100) NOT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`IdKategori`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tkategori`
--

LOCK TABLES `tkategori` WRITE;
/*!40000 ALTER TABLE `tkategori` DISABLE KEYS */;
INSERT INTO `tkategori` VALUES ('KTG002','Sains','2025-05-09 23:57:57','2025-05-09 23:57:57'),('KTG003','Sejarah','2025-05-09 23:57:57','2025-05-09 23:57:57'),('KTG004','Fiksi','2025-05-09 23:57:57','2025-05-09 23:57:57'),('KTG005','Bisnis','2025-05-09 23:57:57','2025-05-09 23:57:57'),('KTG006','Kesehatan','2025-05-09 23:57:57','2025-05-09 23:57:57'),('KTG007','Seni & Desain','2025-05-09 23:57:57','2025-05-09 23:57:57'),('KTG008','Pendidikan','2025-05-09 23:57:57','2025-05-09 23:57:57'),('KTG009','Agama','2025-05-09 23:57:57','2025-05-09 23:57:57'),('KTG010','Hukum','2025-05-09 23:57:57','2025-05-09 23:57:57');
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
  KEY `IdKategori` (`IdKategori`),
  KEY `IdRak` (`IdRak`),
  CONSTRAINT `tmasterbuku_ibfk_1` FOREIGN KEY (`IdKategori`) REFERENCES `tkategori` (`IdKategori`),
  CONSTRAINT `tmasterbuku_ibfk_2` FOREIGN KEY (`IdRak`) REFERENCES `trak` (`IdRak`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tmasterbuku`
--

LOCK TABLES `tmasterbuku` WRITE;
/*!40000 ALTER TABLE `tmasterbuku` DISABLE KEYS */;
INSERT INTO `tmasterbuku` VALUES ('BUK001','DSADA','DAS','DASD',12,'2025-05-16 18:38:43','2025-05-16 19:16:59','KTG002','RAK002'),('BUK002','DASDA','DASD','DA',5,'2025-05-16 18:38:55','2025-05-16 18:38:55','KTG009','RAK009');
/*!40000 ALTER TABLE `tmasterbuku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpeminjaman`
--

DROP TABLE IF EXISTS `tpeminjaman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tpeminjaman` (
  `IdPeminjaman` varchar(10) NOT NULL,
  `IdAnggota` varchar(10) DEFAULT NULL,
  `TanggalPinjam` date DEFAULT NULL,
  `TanggalJatuhTempo` date DEFAULT NULL,
  PRIMARY KEY (`IdPeminjaman`),
  KEY `IdAnggota` (`IdAnggota`),
  CONSTRAINT `tpeminjaman_ibfk_1` FOREIGN KEY (`IdAnggota`) REFERENCES `tanggota` (`IdAnggota`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpeminjaman`
--

LOCK TABLES `tpeminjaman` WRITE;
/*!40000 ALTER TABLE `tpeminjaman` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpeminjaman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpengembalian`
--

DROP TABLE IF EXISTS `tpengembalian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tpengembalian` (
  `IdPengembalian` varchar(10) NOT NULL,
  `IdPeminjaman` varchar(10) DEFAULT NULL,
  `TanggalKembali` date DEFAULT NULL,
  `Denda` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`IdPengembalian`),
  KEY `IdPeminjaman` (`IdPeminjaman`),
  CONSTRAINT `tpengembalian_ibfk_1` FOREIGN KEY (`IdPeminjaman`) REFERENCES `tpeminjaman` (`IdPeminjaman`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpengembalian`
--

LOCK TABLES `tpengembalian` WRITE;
/*!40000 ALTER TABLE `tpengembalian` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpengembalian` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trak`
--

DROP TABLE IF EXISTS `trak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trak` (
  `IdRak` varchar(10) NOT NULL,
  `NamaRak` varchar(100) NOT NULL,
  `Lokasi` varchar(100) NOT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`IdRak`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trak`
--

LOCK TABLES `trak` WRITE;
/*!40000 ALTER TABLE `trak` DISABLE KEYS */;
INSERT INTO `trak` VALUES ('RAK002','Rak Sains','Lantai 1 - A2','2025-05-09 23:58:02','2025-05-09 23:58:02'),('RAK003','Rak Sejarah','Lantai 1 - B1','2025-05-09 23:58:02','2025-05-09 23:58:02'),('RAK004','Rak Fiksi','Lantai 2 - C1','2025-05-09 23:58:02','2025-05-09 23:58:02'),('RAK005','Rak Bisnis','Lantai 2 - C2','2025-05-09 23:58:02','2025-05-09 23:58:02'),('RAK006','Rak Kesehatan','Lantai 2 - C3','2025-05-09 23:58:02','2025-05-09 23:58:02'),('RAK007','Rak Seni','Lantai 3 - D1','2025-05-09 23:58:02','2025-05-09 23:58:02'),('RAK008','Rak Pendidikan','Lantai 3 - D2','2025-05-09 23:58:02','2025-05-09 23:58:02'),('RAK009','Rak Agama','Lantai 3 - E1','2025-05-09 23:58:02','2025-05-09 23:58:02'),('RAK010','Rak Hukum','Lantai 3 - E2','2025-05-09 23:58:02','2025-05-09 23:58:02');
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
  `NamaRole` varchar(50) DEFAULT NULL,
  `DateCreate` datetime DEFAULT NULL,
  `DateModify` datetime DEFAULT NULL,
  PRIMARY KEY (`IdRole`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trole`
--

LOCK TABLES `trole` WRITE;
/*!40000 ALTER TABLE `trole` DISABLE KEYS */;
INSERT INTO `trole` VALUES (1,'admin','2025-05-02 19:15:10','2025-05-02 19:15:10'),(2,'mahasiswa','2025-05-02 20:39:42','2025-05-02 20:39:42');
/*!40000 ALTER TABLE `trole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-16 19:55:05
