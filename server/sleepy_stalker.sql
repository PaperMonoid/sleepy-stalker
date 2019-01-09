-- MySQL dump 10.17  Distrib 10.3.11-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: sleepy_stalker
-- ------------------------------------------------------
-- Server version	10.3.11-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Sleep`
--

DROP TABLE IF EXISTS `Sleep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sleep` (
  `SleepId` int(4) NOT NULL AUTO_INCREMENT,
  `SleepTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `Food` enum('low','medium','high') NOT NULL DEFAULT 'low',
  `Exercise` enum('low','medium','high') NOT NULL DEFAULT 'low',
  `Stress` enum('low','medium','high') NOT NULL DEFAULT 'low',
  `Mood` enum('happy','sad','angry') NOT NULL DEFAULT 'happy',
  PRIMARY KEY (`SleepId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sleep`
--

LOCK TABLES `Sleep` WRITE;
/*!40000 ALTER TABLE `Sleep` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sleep` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WakeUp`
--

DROP TABLE IF EXISTS `WakeUp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WakeUp` (
  `WakeUpId` int(4) NOT NULL AUTO_INCREMENT,
  `SleepId` int(4) NOT NULL,
  `WakeUpTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `WakeUpType` enum('natural','alarm','other') NOT NULL DEFAULT 'natural',
  PRIMARY KEY (`WakeUpId`),
  UNIQUE KEY `SleepId` (`SleepId`),
  CONSTRAINT `WakeUp_ibfk_1` FOREIGN KEY (`SleepId`) REFERENCES `Sleep` (`SleepId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WakeUp`
--

LOCK TABLES `WakeUp` WRITE;
/*!40000 ALTER TABLE `WakeUp` DISABLE KEYS */;
/*!40000 ALTER TABLE `WakeUp` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-08 23:27:26
