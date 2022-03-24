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
-- Table structure for table `t_books`
--

DROP TABLE IF EXISTS `t_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_books` (
  `isbn` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `authors` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lang` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `abstract` varchar(900) DEFAULT NULL,
  `publishYear` int NOT NULL,
  PRIMARY KEY (`isbn`),
  UNIQUE KEY `t_bookscol4_UNIQUE` (`abstract`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_books`
--

LOCK TABLES `t_books` WRITE;
/*!40000 ALTER TABLE `t_books` DISABLE KEYS */;
INSERT INTO `t_books` VALUES ('0000185884','Chess and Checkers The Way to Mastership','Edward Lasker','English','THE RULES OF THE GAME\r\n          BOARD AND MEN\r\nThe game of Chess is played by two armies who oppose each other on a square board or battlefield of sixty-four alternate white\r\nand black squares. Each army has sixteen men; one King, one Queen, two Rooks (or Castles), two Bishops, two Knights and eight Pawns.\r\nThe Generals of the two armies are the two players themselves. The men of one side are of light color and are called White,\r\nthose of the other side are of dark color and are called Black.\r\n',1922),('0142437182','Siddhartha','Herman Hesse','Arabic','He ate only once a day, and never something cooked. He fasted for fifteen days. He fasted for twenty-eight days.\r\nThe flesh waned from his thighs and cheeks. Feverish dreams flickered from his enlarged eyes, long nails grew slowly on his parched\r\nfingers and a dry, shaggy beard grew on his chin. His glance turned to icy when he encountered women; his mouth twitched with contempt,\r\nwhen he walked through a city of nicely dressed people.\r\n',1995),('0199535566','Pride and Prejudice','Austen,Jane','English','Pride and Prejudice has delighted generations of readers with its unforgettable cast of characters',2002),('0234567891','Versailles The View From Sweden','Sidna rabi','Arabic','salemo aalaykom\r\n',2006),('0262680920','The New Hacker\'s Dictionary','Eric S. Raymond','English','Originally, this was due to a desire to freeze the file temporarily to facilitate the production of Steele-1983, but external\r\nconditions caused the `temporary\' freeze to become permanent.\r\nThe AI Lab culture had been hit hard in the late 1970s by funding cuts and the resulting administrative decision to use vendor-supported\r\nhardware and software instead of homebrew whenever possible. At MIT, most AI work had turned to dedicated LISP Machines.\r\n',1997),('0345324390','The Gods of Mars','Edgar Rice Burroughs','English','In addition to the features which I have already described, the beast was equipped with a massive tail about six feet in length, quite round where it joined the body, but tapering to a flat, thin blade toward the end, which trailed at right angles to the ground.\r\nBy far the most remarkable feature of this most remarkable creature, however, were the two tiny replicas of it, each about six inches\r\nin length, which dangled, one on either side, from its armpits. They were suspended by a small stem which seemed to grow from the exact\r\ntops of their heads to where it connected them with the body of the adult.\r\n',1918),('0486205282','Chess Strategy','Edward Lasker','English','This settles all typical end-games of King and pawn against King. There is, however, one exception to the rules set out,\r\nnamely, when a ROOK\'S PAWN is concerned. Here the isolated King always succeeds in drawing if he can reach the corner where the\r\npawn has to queen, for he cannot be driven out again. The Rook\'s pawn affords another opportunity for the weaker side to draw.\r\nDiagram 55 will illustrate this, and similar positions are of frequent occurrence in practice. Here Black draws with 1. ... K-B5.\r\nAs he threatens to capture the pawn, White must play 2. P-R4. Then after the reply K-B4, White is still unable to cut the opponent\r\noff from the corner with K-Kt7, as the loss of the pawn is still threatened through K-Kt5. And after 3.\r\n',1915),('0486266885','The Strange Case of Dr. Jekyll and Mr. Hyde','Robert Louis Stevenson','English','only differed on some point of science,\" he thought; and being a man of no scientific passions\r\n(except in the matter of conveyancing), he even added: \"It is nothing worse than that!\" He gave his friend a few seconds to recover\r\nhis composure, and then approached the question he had come to put. \"Did you ever come across a protege of his--one Hyde?\" he asked.\r\n\"Hyde?\" repeated Lanyon. \"No. Never heard of him. Since my time.\"That was the amount of information that the lawyer carried back with\r\nhim to the great, dark bed on which he tossed to and fro, until the small hours of the morning began to grow large. It was a night of\r\nlittle ease to his toiling mind, toiling in mere darkness and beseiged by questions.\r\n',1886),('0549849555','The Woman in White','Wilkie Collins','Arabic','So the Papa says, I have got a letter from my friend, the Mister; and he wants a recommend from me, of a drawing-master,\r\nto go down to his house in the country.My-soul-bless-my-soul! when I heard the golden Papa say those words, if I had been big enough\r\nto reach up to him, I should have put my arms round his neck, and pressed him to my bosom in a long and grateful hug! As it was,\r\nI only bounced upon my chair. My seat was on thorns, and my soul was on fire to speak but I held my tongue, and let Papa go...\r\n',2000),('0586565658','Hacker Crackdown','Bruce Sterling','English','But this book is not public domain. You can\'t copyright it in your own name. I own the copyright. Attempts to pirate this\r\nbook and make money from selling it may involve you in a serious litigative snarl. Believe me, for the pittance you might wring out of\r\nsuch an action, it\'s really not worth it. This book don\'t \"belong\" to you. In an odd but very genuine way, I feel it doesn\'t \"belong\"\r\nto me, either. It\'s a book about the people of cyberspace, and distributing it in this way is the best way I know to actually make this\r\ninformation available, freely and easily, to all the people of cyberspace--including people far outside the borders of the United States,\r\nwho otherwise may never have a chance to see any edition of the book.\r\n',1994),('0812972120','The Mysterious Island','Jules Verne','English','same day another important personage fell into the hands of the Southerners. This was no other than Gideon Spilen,\r\na reporter for the New York Herald, who had been ordered to follow the changes of the war in the midst of the Northern armies.\r\nGideon Spilett was one of that race of indomitable English or American chroniclers, like Stanley and others, who stop at nothing to\r\nobtain exact information, and transmit it to their journal in the shortest possible time\r\n',1874),('0856786786','How to Avoid Huge Ships','sami','Deutsch','aaaaaaa',1999),('0951753123','Favorite Flies And Their Histories','souad','Arabic','Kteb\r\n',2008);
/*!40000 ALTER TABLE `t_books` ENABLE KEYS */;
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
