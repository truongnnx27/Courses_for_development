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
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `question_text` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `question_type_id` bigint DEFAULT NULL,
  `quiz_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdyyji0ey6u2yifjdt8ybu3tyw` (`question_type_id`),
  KEY `FKn3gvco4b0kewxc0bywf1igfms` (`quiz_id`),
  CONSTRAINT `FKdyyji0ey6u2yifjdt8ybu3tyw` FOREIGN KEY (`question_type_id`) REFERENCES `question_types` (`id`),
  CONSTRAINT `FKn3gvco4b0kewxc0bywf1igfms` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'2024-10-10 17:36:43.780465','Java là ngôn ngữ lập trình gì?5','2024-10-10 17:36:43.780465',NULL,NULL),(2,'2024-10-10 17:36:43.806544','Trong Java, kiểu dữ liệu nào dùng để lưu trữ số nguyên?5','2024-10-10 17:36:43.806544',NULL,NULL),(3,'2024-10-10 17:36:43.817185','Cú pháp nào là đúng để khai báo một biến trong Java?5','2024-10-10 17:36:43.817185',NULL,NULL),(4,'2024-10-10 17:36:43.860423','Java là ngôn ngữ lập trình gì?5','2024-10-10 17:36:43.860423',1,3),(5,'2024-10-10 17:36:43.923618','Trong Java, kiểu dữ liệu nào dùng để lưu trữ số nguyên?5','2024-10-10 17:36:43.924612',1,3),(6,'2024-10-10 17:36:43.969522','Cú pháp nào là đúng để khai báo một biến trong Java?5','2024-10-10 17:36:43.969522',1,3),(7,'2024-10-10 19:42:14.382371','Java là ngôn ngữ lập trình gì?5','2024-10-10 19:42:14.382371',NULL,NULL),(8,'2024-10-10 19:42:14.397095','Trong Java, kiểu dữ liệu nào dùng để lưu trữ số nguyên?5','2024-10-10 19:42:14.397095',NULL,NULL),(9,'2024-10-10 19:42:14.404050','Cú pháp nào là đúng để khai báo một biến trong Java?5','2024-10-10 19:42:14.404050',NULL,NULL),(10,'2024-10-10 19:42:14.416818','Java là ngôn ngữ lập trình gì?5','2024-10-10 19:42:14.416818',1,4),(11,'2024-10-10 19:42:14.434718','Trong Java, kiểu dữ liệu nào dùng để lưu trữ số nguyên?5','2024-10-10 19:42:14.434718',1,4),(12,'2024-10-10 19:42:14.454924','Cú pháp nào là đúng để khai báo một biến trong Java?5','2024-10-10 19:42:14.454924',1,4),(13,'2024-10-10 20:23:32.044678','Java là ngôn ngữ lập trình gì?5','2024-10-10 20:23:32.044678',NULL,NULL),(14,'2024-10-10 20:23:32.058183','Trong Java, kiểu dữ liệu nào dùng để lưu trữ số nguyên?5','2024-10-10 20:23:32.058183',NULL,NULL),(15,'2024-10-10 20:23:32.069359','Cú pháp nào là đúng để khai báo một biến trong Java?5','2024-10-10 20:23:32.069359',NULL,NULL),(16,'2024-10-10 20:23:32.089082','Java là ngôn ngữ lập trình gì?5','2024-10-10 20:23:32.089082',1,5),(17,'2024-10-10 20:23:32.112467','Trong Java, kiểu dữ liệu nào dùng để lưu trữ số nguyên?5','2024-10-10 20:23:32.112467',1,5),(18,'2024-10-10 20:23:32.131514','Cú pháp nào là đúng để khai báo một biến trong Java?5','2024-10-10 20:23:32.131514',1,5);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
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
