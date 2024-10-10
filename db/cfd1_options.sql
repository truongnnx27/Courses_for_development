-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cfd1
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `options` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_correct` bit(1) NOT NULL,
  `option_text` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `question_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5bmv46so2y5igt9o9n9w4fh6y` (`question_id`),
  CONSTRAINT `FK5bmv46so2y5igt9o9n9w4fh6y` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (1,_binary '\0','Ngôn ngữ lập trình thủ tục5',NULL),(2,_binary '\0','Ngôn ngữ lập trình hướng đối tượng5',NULL),(3,_binary '\0','Ngôn ngữ lập trình kịch bản5',NULL),(4,_binary '\0','Ngôn ngữ lập trình máy5',NULL),(5,_binary '\0','int5',NULL),(6,_binary '\0','String5',NULL),(7,_binary '\0','float5',NULL),(8,_binary '\0','char5',NULL),(9,_binary '\0','int x = 10;35',NULL),(10,_binary '\0','var x = 10;5',NULL),(11,_binary '\0','x int = 10;5',NULL),(12,_binary '\0','int = x 10;5',NULL),(13,_binary '\0','Ngôn ngữ lập trình thủ tục5',NULL),(14,_binary '\0','Ngôn ngữ lập trình hướng đối tượng5',NULL),(15,_binary '\0','Ngôn ngữ lập trình kịch bản5',NULL),(16,_binary '\0','Ngôn ngữ lập trình máy5',NULL),(17,_binary '\0','Ngôn ngữ lập trình thủ tục5',4),(18,_binary '\0','Ngôn ngữ lập trình hướng đối tượng5',4),(19,_binary '\0','Ngôn ngữ lập trình kịch bản5',4),(20,_binary '\0','Ngôn ngữ lập trình máy5',4),(21,_binary '\0','int5',NULL),(22,_binary '\0','String5',NULL),(23,_binary '\0','float5',NULL),(24,_binary '\0','char5',NULL),(25,_binary '\0','int5',5),(26,_binary '\0','String5',5),(27,_binary '\0','float5',5),(28,_binary '\0','char5',5),(29,_binary '\0','int x = 10;35',NULL),(30,_binary '\0','var x = 10;5',NULL),(31,_binary '\0','x int = 10;5',NULL),(32,_binary '\0','int = x 10;5',NULL),(33,_binary '\0','int x = 10;35',6),(34,_binary '\0','var x = 10;5',6),(35,_binary '\0','x int = 10;5',6),(36,_binary '\0','int = x 10;5',6),(37,_binary '\0','Ngôn ngữ lập trình thủ tục5',NULL),(38,_binary '\0','Ngôn ngữ lập trình hướng đối tượng5',NULL),(39,_binary '\0','Ngôn ngữ lập trình kịch bản5',NULL),(40,_binary '\0','Ngôn ngữ lập trình máy5',NULL),(41,_binary '\0','int5',NULL),(42,_binary '\0','String5',NULL),(43,_binary '\0','float5',NULL),(44,_binary '\0','char5',NULL),(45,_binary '\0','int x = 10;35',NULL),(46,_binary '\0','var x = 10;5',NULL),(47,_binary '\0','x int = 10;5',NULL),(48,_binary '\0','int = x 10;5',NULL),(49,_binary '\0','Ngôn ngữ lập trình thủ tục5',NULL),(50,_binary '\0','Ngôn ngữ lập trình hướng đối tượng5',NULL),(51,_binary '\0','Ngôn ngữ lập trình kịch bản5',NULL),(52,_binary '\0','Ngôn ngữ lập trình máy5',NULL),(53,_binary '\0','Ngôn ngữ lập trình thủ tục5',10),(54,_binary '\0','Ngôn ngữ lập trình hướng đối tượng5',10),(55,_binary '\0','Ngôn ngữ lập trình kịch bản5',10),(56,_binary '\0','Ngôn ngữ lập trình máy5',10),(57,_binary '\0','int5',NULL),(58,_binary '\0','String5',NULL),(59,_binary '\0','float5',NULL),(60,_binary '\0','char5',NULL),(61,_binary '\0','int5',11),(62,_binary '\0','String5',11),(63,_binary '\0','float5',11),(64,_binary '\0','char5',11),(65,_binary '\0','int x = 10;35',NULL),(66,_binary '\0','var x = 10;5',NULL),(67,_binary '\0','x int = 10;5',NULL),(68,_binary '\0','int = x 10;5',NULL),(69,_binary '\0','int x = 10;35',12),(70,_binary '\0','var x = 10;5',12),(71,_binary '\0','x int = 10;5',12),(72,_binary '\0','int = x 10;5',12),(73,_binary '\0','Ngôn ngữ lập trình thủ tục5',NULL),(74,_binary '\0','Ngôn ngữ lập trình hướng đối tượng5',NULL),(75,_binary '\0','Ngôn ngữ lập trình kịch bản5',NULL),(76,_binary '\0','Ngôn ngữ lập trình máy5',NULL),(77,_binary '\0','int5',NULL),(78,_binary '\0','String5',NULL),(79,_binary '\0','float5',NULL),(80,_binary '\0','char5',NULL),(81,_binary '\0','int x = 10;35',NULL),(82,_binary '\0','var x = 10;5',NULL),(83,_binary '\0','x int = 10;5',NULL),(84,_binary '\0','int = x 10;5',NULL),(85,_binary '\0','Ngôn ngữ lập trình thủ tục5',NULL),(86,_binary '\0','Ngôn ngữ lập trình hướng đối tượng5',NULL),(87,_binary '\0','Ngôn ngữ lập trình kịch bản5',NULL),(88,_binary '\0','Ngôn ngữ lập trình máy5',NULL),(89,_binary '\0','Ngôn ngữ lập trình thủ tục5',16),(90,_binary '\0','Ngôn ngữ lập trình hướng đối tượng5',16),(91,_binary '\0','Ngôn ngữ lập trình kịch bản5',16),(92,_binary '\0','Ngôn ngữ lập trình máy5',16),(93,_binary '\0','int5',NULL),(94,_binary '\0','String5',NULL),(95,_binary '\0','float5',NULL),(96,_binary '\0','char5',NULL),(97,_binary '\0','int5',17),(98,_binary '\0','String5',17),(99,_binary '\0','float5',17),(100,_binary '\0','char5',17),(101,_binary '\0','int x = 10;35',NULL),(102,_binary '\0','var x = 10;5',NULL),(103,_binary '\0','x int = 10;5',NULL),(104,_binary '\0','int = x 10;5',NULL),(105,_binary '\0','int x = 10;35',18),(106,_binary '\0','var x = 10;5',18),(107,_binary '\0','x int = 10;5',18),(108,_binary '\0','int = x 10;5',18);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-10 20:59:05
