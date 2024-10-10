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
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `number_of_ratings` int NOT NULL,
  `number_of_students` int NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `rating` decimal(38,2) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `course_level_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmx5gb6xsbkdmycpbi3mkj9l89` (`course_level_id`),
  KEY `FK4u40nf46n1nqa5h38sn5g17ac` (`created_by`),
  CONSTRAINT `FK4u40nf46n1nqa5h38sn5g17ac` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKmx5gb6xsbkdmycpbi3mkj9l89` FOREIGN KEY (`course_level_id`) REFERENCES `course_levels` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (11,'Javascript','2024-10-02 14:35:18.000000','Javascript được ra đời vào năm 1995.','English',120,60,120.00,4.00,'Javascript là ngôn ngữ mạnh mẽ trong lĩnh cực công nghệ thông tin','2024-10-02 14:35:18.000000',2,17),(12,'MySQL','2024-10-02 14:35:18.000000','MySQL là cơ sở dữ liệu khá phổ biến trong lĩnh vực CNTT.','English',120,60,120.00,4.00,'Dễ tiếp cận với người mới','2024-10-02 14:35:18.000000',2,17),(13,'HTML/CSS','2024-10-02 14:35:18.000000','Ngôn ngữ dùng để thiết kế giao diện','English',120,60,120.00,4.00,'Cơ bản của các bạn mới','2024-10-02 14:35:18.000000',2,17),(14,'Bootstrap','2024-10-02 14:35:18.000000','Framework giúp tối ưu','English',120,60,120.00,4.00,'Nhanh và gọn hơn','2024-10-02 14:35:18.000000',3,17),(15,'Java swing','2024-10-02 14:35:18.000000','Java được ra đời vào năm 1995.','English',120,60,120.00,4.00,'Java là ngôn ngữ mạnh mẽ trong lĩnh cực công nghệ thông tin','2024-10-02 14:35:18.000000',1,17);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-10 20:59:04
