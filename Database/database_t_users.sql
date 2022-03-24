-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: database
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `t_users`
--

DROP TABLE IF EXISTS `t_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_users` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `family_name` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL,
  `grade` varchar(45) NOT NULL,
  `address` varchar(150) NOT NULL,
  `email` varchar(80) NOT NULL,
  `telephone` varchar(45) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `t_users_login_uindex` (`login`),
  UNIQUE KEY `t_users_telephone_uindex` (`telephone`),
  UNIQUE KEY `t_userscol7_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5654 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_users`
--

LOCK TABLES `t_users` WRITE;
/*!40000 ALTER TABLE `t_users` DISABLE KEYS */;
INSERT INTO `t_users` VALUES (1,'admin','admin2022','admin','admin','male','worker','enetcom','admin@gmail.com','99187251'),(2,'salem','aszdefrgth','youssef','choura','Female','Worker','triq taniour km 4.5  CafÃÂ© brothers','youssefc123@gmail.com','99187253'),(3,'ktyb','aszdefrgth','katybon','ktib','Female','Student','Leipziger Strasse 17','youssefch123@gmail.com','45321596'),(4,'ahmed','aszdefrgth','ahmed','choura','Female','Student','triq taniour km 4.5  Cafe ','ahmedchoura@gmail.com','99187256'),(5,'youssef','aszdefrgth','youssef','choura','Male','student','taniour km4','youssef@gmail.com','99187281'),(5653,'mariem','aszdefrgth','mariam','choura','Female','Worker','triq taniour km 4.5  CafÃ© brothers','mariemchoura8@gmail.com','99187255');
/*!40000 ALTER TABLE `t_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-24 10:47:25
