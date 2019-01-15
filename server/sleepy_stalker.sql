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
  `SleepyUserId` int(4) NOT NULL,
  `SleepTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `Food` enum('low','medium','high') NOT NULL DEFAULT 'low',
  `Exercise` enum('low','medium','high') NOT NULL DEFAULT 'low',
  `Stress` enum('low','medium','high') NOT NULL DEFAULT 'low',
  `Mood` enum('happy','sad','angry') NOT NULL DEFAULT 'happy',
  PRIMARY KEY (`SleepId`),
  KEY `SleepyUserId` (`SleepyUserId`),
  CONSTRAINT `Sleep_ibfk_1` FOREIGN KEY (`SleepyUserId`) REFERENCES `SleepyUser` (`SleepyUserId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sleep`
--

LOCK TABLES `Sleep` WRITE;
/*!40000 ALTER TABLE `Sleep` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sleep` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`blondie`@`localhost`*/ /*!50003 TRIGGER after_Sleep_insert AFTER INSERT
       ON Sleep
       FOR EACH ROW
BEGIN
	SET @wakeUpId = fn_wakeUpFrom(NEW.SleepTime, NEW.SleepyUserId);
	IF @wakeUpId IS NOT NULL THEN
	   SET @sleepIntervalId = (SELECT SleepIntervalId FROM SleepInterval WHERE WakeUpId = @wakeUpId LIMIT 1);
	   IF @sleepIntervalId IS NOT NULL THEN
	      SET @sleepTime = (SELECT SleepTime FROM SleepInterval JOIN Sleep ON SleepInterval.SleepId = Sleep.SleepId WHERE SleepIntervalId = @sleepIntervalId LIMIT 1);
	      IF NEW.SleepTime > @sleepTime THEN
	      	 UPDATE SleepInterval SET SleepId = NEW.SleepId WHERE SleepIntervalId = @sleepIntervalId;
	      END IF;
	   ELSE
		INSERT INTO SleepInterval(SleepyUserId, SleepId, WakeUpId) VALUES(NEW.SleepyUserId, NEW.SleepId, @wakeUpId);
           END IF;
        END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`blondie`@`localhost`*/ /*!50003 TRIGGER before_Sleep_delete BEFORE DELETE
       ON Sleep
       FOR EACH ROW
BEGIN
	SET @sleepIntervalId = (SELECT SleepIntervalId FROM SleepInterval WHERE SleepId = OLD.SleepId LIMIT 1);
	IF @sleepIntervalId IS NOT NULL THEN
	   SET @sleepId = (SElECT fn_SleepFromNth(WakeUpTime, SleepInterval.SleepyUserId, 1) FROM SleepInterval JOIN WakeUp ON SleepInterval.WakeUpId = WakeUp.WakeUpId WHERE SleepIntervalId = @sleepIntervalId LIMIT 1);
	   IF @sleepId IS NOT NULL THEN
	      SET @newSleepIntervalId = (SELECT SleepIntervalId FROM SleepInterval WHERE SleepId = @sleepId LIMIT 1);
	      IF @newSleepIntervalId IS NULL THEN
	      	 UPDATE SleepInterval SET SleepId = @sleepId WHERE SleepIntervalId = @sleepIntervalId;
              ELSE
		DELETE FROM SleepInterval WHERE SleepIntervalId = @sleepIntervalId;
              END IF;
           ELSE
		DELETE FROM SleepInterval WHERE SleepIntervalId = @sleepIntervalId;
           END IF;
        END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `SleepInterval`
--

DROP TABLE IF EXISTS `SleepInterval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SleepInterval` (
  `SleepIntervalId` int(4) NOT NULL AUTO_INCREMENT,
  `SleepyUserId` int(4) NOT NULL,
  `SleepId` int(4) NOT NULL,
  `WakeUpId` int(4) NOT NULL,
  PRIMARY KEY (`SleepIntervalId`),
  UNIQUE KEY `SleepId` (`SleepId`),
  UNIQUE KEY `WakeUpId` (`WakeUpId`),
  KEY `SleepyUserId` (`SleepyUserId`),
  CONSTRAINT `SleepInterval_ibfk_1` FOREIGN KEY (`SleepId`) REFERENCES `Sleep` (`SleepId`),
  CONSTRAINT `SleepInterval_ibfk_2` FOREIGN KEY (`WakeUpId`) REFERENCES `WakeUp` (`WakeUpId`),
  CONSTRAINT `SleepInterval_ibfk_3` FOREIGN KEY (`SleepyUserId`) REFERENCES `SleepyUser` (`SleepyUserId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SleepInterval`
--

LOCK TABLES `SleepInterval` WRITE;
/*!40000 ALTER TABLE `SleepInterval` DISABLE KEYS */;
/*!40000 ALTER TABLE `SleepInterval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SleepyUser`
--

DROP TABLE IF EXISTS `SleepyUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SleepyUser` (
  `SleepyUserId` int(4) NOT NULL AUTO_INCREMENT,
  `SleepyUserName` varchar(32) NOT NULL,
  PRIMARY KEY (`SleepyUserId`),
  UNIQUE KEY `Username` (`SleepyUserName`),
  UNIQUE KEY `Username_2` (`SleepyUserName`),
  UNIQUE KEY `SleepyUserName` (`SleepyUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SleepyUser`
--

LOCK TABLES `SleepyUser` WRITE;
/*!40000 ALTER TABLE `SleepyUser` DISABLE KEYS */;
/*!40000 ALTER TABLE `SleepyUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WakeUp`
--

DROP TABLE IF EXISTS `WakeUp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WakeUp` (
  `WakeUpId` int(4) NOT NULL AUTO_INCREMENT,
  `SleepyUserId` int(4) NOT NULL,
  `WakeUpTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `WakeUpReason` enum('natural','alarm','other') NOT NULL DEFAULT 'natural',
  PRIMARY KEY (`WakeUpId`),
  KEY `SleepyUserId` (`SleepyUserId`),
  CONSTRAINT `WakeUp_ibfk_1` FOREIGN KEY (`SleepyUserId`) REFERENCES `SleepyUser` (`SleepyUserId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WakeUp`
--

LOCK TABLES `WakeUp` WRITE;
/*!40000 ALTER TABLE `WakeUp` DISABLE KEYS */;
/*!40000 ALTER TABLE `WakeUp` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`blondie`@`localhost`*/ /*!50003 TRIGGER after_WakeUp_insert AFTER INSERT
       ON WakeUp
       FOR EACH ROW
BEGIN
	SET @sleepId = fn_SleepFrom(NEW.WakeUpTime, NEW.SleepyUserId);
	IF @sleepId IS NOT NULL THEN
	   SET @sleepIntervalId = (SELECT SleepIntervalId FROM SleepInterval WHERE SleepId = @sleepId LIMIT 1);
	   IF @sleepIntervalId IS NOT NULL THEN
	      SET @wakeUpTime = (SELECT WakeUpTime FROM SleepInterval JOIN WakeUp ON SleepInterval.WakeUpId = WakeUp.WakeUpId WHERE SleepIntervalId = @sleepIntervalId LIMIT 1);
	      IF NEW.WakeUpTime < @wakeUpTime THEN
	      	 UPDATE SleepInterval SET  WakeUpId = NEW.WakeUpId WHERE SleepIntervalId = @sleepIntervalId;
	      END IF;
	   ELSE
		INSERT INTO SleepInterval(SleepyUserId, SleepId, WakeUpId) VALUES(NEW.SleepyUserId, @sleepId, NEW.WakeUpId);
           END IF;
        END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`blondie`@`localhost`*/ /*!50003 TRIGGER before_WakeUp_delete BEFORE DELETE
       ON WakeUp
       FOR EACH ROW
BEGIN
	SET @sleepIntervalId = (SELECT SleepIntervalId FROM SleepInterval WHERE WakeUpId = OLD.WakeUpId LIMIT 1);
	IF @sleepIntervalId IS NOT NULL THEN
	   SET @wakeUpId = (SElECT fn_WakeUpFromNth(SleepTime, SleepInterval.SleepyUserId, 1) FROM SleepInterval JOIN Sleep ON SleepInterval.SleepId = Sleep.SleepId WHERE SleepIntervalId = @sleepIntervalId LIMIT 1);
	   IF @wakeUpId IS NOT NULL THEN
	      SET @newSleepIntervalId = (SELECT SleepIntervalId FROM SleepInterval WHERE WakeUpId = @wakeUpId LIMIT 1);
	      IF @newSleepIntervalId IS NULL THEN
	      	 UPDATE SleepInterval SET WakeUpId = @wakeUpId WHERE SleepIntervalId = @sleepIntervalId;
              ELSE
		DELETE FROM SleepInterval WHERE SleepIntervalId = @sleepIntervalId;
              END IF;
           ELSE
		DELETE FROM SleepInterval WHERE SleepIntervalId = @sleepIntervalId;
           END IF;
        END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping routines for database 'sleepy_stalker'
--
/*!50003 DROP FUNCTION IF EXISTS `fn_SleepFrom` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`blondie`@`localhost` FUNCTION `fn_SleepFrom`(wakeUpTime timestamp, userId INT(4)) RETURNS int(11)
RETURN (SELECT SleepId
       FROM Sleep
       WHERE SleepTime <= wakeUpTime AND SleepyUserId = userId
       ORDER BY SleepTime DESC
       LIMIT 1) ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `fn_SleepFromNth` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`blondie`@`localhost` FUNCTION `fn_SleepFromNth`(wakeUpTime timestamp, userId INT(4), nth INT(4)) RETURNS int(11)
RETURN (SELECT SleepId
       FROM Sleep
       WHERE SleepTime <= wakeUpTime AND SleepyUserId = userId
       ORDER BY SleepTime DESC
       LIMIT nth, 1) ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `fn_WakeUpFrom` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`blondie`@`localhost` FUNCTION `fn_WakeUpFrom`(sleepTime timestamp, userId INT(4)) RETURNS int(11)
RETURN (SELECT WakeUpId
       FROM WakeUp
       WHERE WakeUpTime >= sleepTime AND SleepyUserId = userId
       ORDER BY WakeUpTime ASC
       LIMIT 1) ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `fn_WakeUpFromNth` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`blondie`@`localhost` FUNCTION `fn_WakeUpFromNth`(sleepTime timestamp, userId INT(4), nth INT(4)) RETURNS int(11)
RETURN (SELECT WakeUpId
       FROM WakeUp
       WHERE WakeUpTime >= sleepTime AND SleepyUserId = userId
       ORDER BY WakeUpTime ASC
       LIMIT nth, 1) ;;
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

-- Dump completed on 2019-01-14 22:39:59
