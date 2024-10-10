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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bio` varchar(300) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified` bit(1) DEFAULT NULL,
  `full_name` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profile_picture` varchar(250) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `website` varchar(255) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (17,'Nhóm trưởng','2024-10-02 14:35:18.000000','truongnnx27@gmail.com',_binary '','Xuân Trường','$2b$12$n4qmbJG8vETbs5Wl0PoOE.xw.2r.8Suwz69v7kFBl3D63b7eHO9UK\n','xuantruong.png','2024-10-03 14:35:18.000000','Xuân Trường','http://xuantruong',1),(18,'Nhóm phó','2024-10-02 14:35:18.000000','tronglong@gmail.com',_binary '','Trọng Long','$2b$12$n4qmbJG8vETbs5Wl0PoOE.xw.2r.8Suwz69v7kFBl3D63b7eHO9UK\n','tronglong.png','2024-10-03 14:35:18.000000','Trọng Long','http://tronglong',1),(19,'Thành viên','2024-10-02 14:35:18.000000','dongkhanh@gmail.com',_binary '','Đồng Khánh','$2b$12$n4qmbJG8vETbs5Wl0PoOE.xw.2r.8Suwz69v7kFBl3D63b7eHO9UK\n','dongkhanh.png','2024-10-03 14:35:18.000000','Đồng Khánh','http://dongkhanh',1),(20,'Thành viên','2024-10-02 14:35:18.000000','quockhanh@gmail.com',_binary '','Quốc Khánh','$2b$12$n4qmbJG8vETbs5Wl0PoOE.xw.2r.8Suwz69v7kFBl3D63b7eHO9UK\n','quockhanh.png','2024-10-03 14:35:18.000000','Quốc Khánh','http://quockhanh',1),(21,'Thành viên','2024-10-02 14:35:18.000000','thanhsang@gmail.com',_binary '','Thành Sang','$2b$12$n4qmbJG8vETbs5Wl0PoOE.xw.2r.8Suwz69v7kFBl3D63b7eHO9UK\n','thanhsang.png','2024-10-03 14:35:18.000000','Thành Sang','http://dongkhanh',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
