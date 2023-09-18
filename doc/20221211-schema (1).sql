-- MySQL dump 10.19  Distrib 10.3.37-MariaDB, for Win64 (AMD64)
--
-- Host: mariadb.neoflow.co.kr    Database: goldstar_schema
-- ------------------------------------------------------
-- Server version	10.8.3-MariaDB-1:10.8.3+maria~jammy

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_bbs`
--

DROP TABLE IF EXISTS `tb_bbs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_bbs` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `use_at` varchar(1) NOT NULL,
  `nm` varchar(100) NOT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_bbsctt`
--

DROP TABLE IF EXISTS `tb_bbsctt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_bbsctt` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `bbs_sn` int(11) NOT NULL,
  `delete_at` varchar(1) NOT NULL,
  `ordinal` int(11) NOT NULL,
  `sj` varchar(500) NOT NULL,
  `cn` mediumtext DEFAULT NULL,
  `rd` int(11) NOT NULL,
  `regist_dt` varchar(14) NOT NULL,
  PRIMARY KEY (`sn`,`bbs_sn`),
  KEY `fk_tb_bbsctt_tb_bbs_idx` (`bbs_sn`),
  CONSTRAINT `fk_tb_bbsctt_tb_bbs` FOREIGN KEY (`bbs_sn`) REFERENCES `tb_bbs` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_indiv`
--

DROP TABLE IF EXISTS `tb_indiv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_indiv` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(500) NOT NULL,
  `ordinal` int(11) NOT NULL,
  `status` varchar(1) NOT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_indiv_answer`
--

DROP TABLE IF EXISTS `tb_indiv_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_indiv_answer` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `indiv_sn` int(11) NOT NULL,
  `delete_at` varchar(1) NOT NULL,
  `nm` varchar(500) NOT NULL,
  `ty` varchar(500) NOT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_answer_tb_survey100` (`indiv_sn`),
  CONSTRAINT `fk_tb_answer_tb_survey100` FOREIGN KEY (`indiv_sn`) REFERENCES `tb_indiv` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_indiv_member`
--

DROP TABLE IF EXISTS `tb_indiv_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_indiv_member` (
  `part_sn` int(11) NOT NULL,
  `answer_sn` int(11) NOT NULL,
  `created` varchar(14) NOT NULL,
  PRIMARY KEY (`part_sn`,`answer_sn`),
  KEY `fk_tb_mind_member_tb_mind_part1_idx` (`part_sn`),
  KEY `fk_tb_mind_member_tb_mind_answer1_idx` (`answer_sn`),
  CONSTRAINT `fk_tb_mind_member_tb_mind_answer100` FOREIGN KEY (`answer_sn`) REFERENCES `tb_indiv_answer` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_mind_member_tb_mind_part100` FOREIGN KEY (`part_sn`) REFERENCES `tb_indiv_part` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_indiv_part`
--

DROP TABLE IF EXISTS `tb_indiv_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_indiv_part` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `created` varchar(14) NOT NULL,
  `ty` varchar(500) DEFAULT NULL,
  `sign_member_sn` int(11) NOT NULL,
  `sign_created` varchar(8) NOT NULL,
  `staff_created` varchar(14) DEFAULT NULL,
  `staff_check` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_indiv_part_tb_sign1_idx` (`sign_member_sn`,`sign_created`),
  CONSTRAINT `fk_tb_indiv_part_tb_sign1` FOREIGN KEY (`sign_member_sn`, `sign_created`) REFERENCES `tb_sign` (`member_sn`, `created`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_jammy`
--

DROP TABLE IF EXISTS `tb_jammy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_jammy` (
  `sn` int(11) NOT NULL,
  `code` varchar(100) DEFAULT NULL,
  `issued` varchar(14) DEFAULT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_life_date`
--

DROP TABLE IF EXISTS `tb_life_date`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_life_date` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(8) NOT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_life_part`
--

DROP TABLE IF EXISTS `tb_life_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_life_part` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `member_sn` int(11) NOT NULL,
  `ty` varchar(500) NOT NULL,
  `member_cnt` int(11) NOT NULL,
  `date` varchar(8) NOT NULL,
  `time` varchar(6) NOT NULL,
  `status` varchar(14) NOT NULL,
  `reservated` varchar(14) DEFAULT NULL,
  `canceled` varchar(14) DEFAULT NULL,
  `reservated_admin` varchar(14) DEFAULT NULL,
  `canceled_admin` varchar(14) DEFAULT NULL,
  `memo` mediumtext DEFAULT NULL,
  `staff_created` varchar(14) DEFAULT NULL,
  `staff_check` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_life_part_tb_member1_idx` (`member_sn`),
  CONSTRAINT `fk_tb_life_part_tb_member1` FOREIGN KEY (`member_sn`) REFERENCES `tb_member` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_manager`
--

DROP TABLE IF EXISTS `tb_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_manager` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(500) NOT NULL,
  `name` varchar(2000) NOT NULL,
  `company` varchar(500) NOT NULL,
  `pw` varchar(2000) NOT NULL,
  `created` varchar(14) NOT NULL,
  `updated` varchar(14) NOT NULL,
  `invalid_pass` int(11) NOT NULL,
  `updated_pass` varchar(14) NOT NULL,
  PRIMARY KEY (`sn`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_member`
--

DROP TABLE IF EXISTS `tb_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_member` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(500) NOT NULL,
  `name` varchar(500) NOT NULL,
  `hp` varchar(500) NOT NULL,
  `created` varchar(14) NOT NULL,
  `updated` varchar(14) NOT NULL,
  `jammy_sn` int(11) NOT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_mind`
--

DROP TABLE IF EXISTS `tb_mind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mind` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(500) NOT NULL,
  `ordinal` int(11) NOT NULL,
  `status` varchar(1) NOT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_mind_answer`
--

DROP TABLE IF EXISTS `tb_mind_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mind_answer` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `mind_sn` int(11) NOT NULL,
  `delete_at` varchar(1) NOT NULL,
  `nm` varchar(500) NOT NULL,
  `ty` varchar(500) NOT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_answer_tb_survey1` (`mind_sn`),
  CONSTRAINT `fk_tb_answer_tb_survey1` FOREIGN KEY (`mind_sn`) REFERENCES `tb_mind` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_mind_member`
--

DROP TABLE IF EXISTS `tb_mind_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mind_member` (
  `part_sn` int(11) NOT NULL,
  `answer_sn` int(11) NOT NULL,
  `created` varchar(14) NOT NULL,
  PRIMARY KEY (`part_sn`,`answer_sn`),
  KEY `fk_tb_mind_member_tb_mind_part1_idx` (`part_sn`),
  KEY `fk_tb_mind_member_tb_mind_answer1_idx` (`answer_sn`),
  CONSTRAINT `fk_tb_mind_member_tb_mind_answer1` FOREIGN KEY (`answer_sn`) REFERENCES `tb_mind_answer` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_mind_member_tb_mind_part1` FOREIGN KEY (`part_sn`) REFERENCES `tb_mind_part` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_mind_part`
--

DROP TABLE IF EXISTS `tb_mind_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mind_part` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `created` varchar(14) NOT NULL,
  `ty` varchar(500) DEFAULT NULL,
  `sign_member_sn` int(11) NOT NULL,
  `sign_created` varchar(8) NOT NULL,
  `staff_created` varchar(14) DEFAULT NULL,
  `staff_check` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_mind_part_tb_sign1_idx` (`sign_member_sn`,`sign_created`),
  CONSTRAINT `fk_tb_mind_part_tb_sign1` FOREIGN KEY (`sign_member_sn`, `sign_created`) REFERENCES `tb_sign` (`member_sn`, `created`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_mood`
--

DROP TABLE IF EXISTS `tb_mood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mood` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(500) NOT NULL,
  `ordinal` int(11) NOT NULL,
  `status` varchar(1) NOT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_mood_answer`
--

DROP TABLE IF EXISTS `tb_mood_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mood_answer` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `mood_sn` int(11) NOT NULL,
  `delete_at` varchar(1) NOT NULL,
  `nm` varchar(500) NOT NULL,
  `ty` varchar(500) NOT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_answer_tb_survey1000` (`mood_sn`),
  CONSTRAINT `fk_tb_answer_tb_survey1000` FOREIGN KEY (`mood_sn`) REFERENCES `tb_mood` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_mood_member`
--

DROP TABLE IF EXISTS `tb_mood_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mood_member` (
  `part_sn` int(11) NOT NULL,
  `answer_sn` int(11) NOT NULL,
  `created` varchar(14) NOT NULL,
  PRIMARY KEY (`part_sn`,`answer_sn`),
  KEY `fk_tb_mind_member_tb_mind_part1_idx` (`part_sn`),
  KEY `fk_tb_mind_member_tb_mind_answer1_idx` (`answer_sn`),
  CONSTRAINT `fk_tb_mind_member_tb_mind_answer1000` FOREIGN KEY (`answer_sn`) REFERENCES `tb_mood_answer` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_mind_member_tb_mind_part1000` FOREIGN KEY (`part_sn`) REFERENCES `tb_mood_part` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_mood_part`
--

DROP TABLE IF EXISTS `tb_mood_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mood_part` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `created` varchar(14) NOT NULL,
  `ty` varchar(500) DEFAULT NULL,
  `sign_member_sn` int(11) NOT NULL,
  `sign_created` varchar(8) NOT NULL,
  `staff_created` varchar(14) DEFAULT NULL,
  `staff_check` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_indiv_part_tb_sign1_idx` (`sign_member_sn`,`sign_created`),
  CONSTRAINT `fk_tb_indiv_part_tb_sign10` FOREIGN KEY (`sign_member_sn`, `sign_created`) REFERENCES `tb_sign` (`member_sn`, `created`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_refresh_part`
--

DROP TABLE IF EXISTS `tb_refresh_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_refresh_part` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `created` varchar(14) NOT NULL,
  `sign_member_sn` int(11) NOT NULL,
  `sign_created` varchar(8) NOT NULL,
  `staff_created` varchar(14) DEFAULT NULL,
  `staff_check` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_refresh_part_tb_sign1_idx` (`sign_member_sn`,`sign_created`),
  CONSTRAINT `fk_tb_refresh_part_tb_sign1` FOREIGN KEY (`sign_member_sn`, `sign_created`) REFERENCES `tb_sign` (`member_sn`, `created`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_sign`
--

DROP TABLE IF EXISTS `tb_sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sign` (
  `member_sn` int(11) NOT NULL,
  `created` varchar(8) NOT NULL,
  `nick` varchar(100) NOT NULL,
  `worry_ty` varchar(100) DEFAULT NULL,
  `qr_string` varchar(500) DEFAULT NULL,
  `qr_image` varchar(500) DEFAULT NULL,
  `added` varchar(14) NOT NULL,
  PRIMARY KEY (`member_sn`,`created`),
  CONSTRAINT `fk_tb_join_tb_member1` FOREIGN KEY (`member_sn`) REFERENCES `tb_member` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_style`
--

DROP TABLE IF EXISTS `tb_style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_style` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(500) NOT NULL,
  `ordinal` int(11) NOT NULL,
  `status` varchar(1) NOT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_style_answer`
--

DROP TABLE IF EXISTS `tb_style_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_style_answer` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `style_sn` int(11) NOT NULL,
  `delete_at` varchar(1) NOT NULL,
  `nm` varchar(500) NOT NULL,
  `ty` varchar(500) NOT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_answer_tb_survey10` (`style_sn`),
  CONSTRAINT `fk_tb_answer_tb_survey10` FOREIGN KEY (`style_sn`) REFERENCES `tb_style` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_style_member`
--

DROP TABLE IF EXISTS `tb_style_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_style_member` (
  `part_sn` int(11) NOT NULL,
  `answer_sn` int(11) NOT NULL,
  `created` varchar(14) NOT NULL,
  PRIMARY KEY (`part_sn`,`answer_sn`),
  KEY `fk_tb_mind_member_tb_mind_part1_idx` (`part_sn`),
  KEY `fk_tb_mind_member_tb_mind_answer1_idx` (`answer_sn`),
  CONSTRAINT `fk_tb_mind_member_tb_mind_answer10` FOREIGN KEY (`answer_sn`) REFERENCES `tb_style_answer` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_mind_member_tb_mind_part10` FOREIGN KEY (`part_sn`) REFERENCES `tb_style_part` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_style_part`
--

DROP TABLE IF EXISTS `tb_style_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_style_part` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `created` varchar(14) NOT NULL,
  `ty` varchar(500) DEFAULT NULL,
  `sign_member_sn` int(11) NOT NULL,
  `sign_created` varchar(8) NOT NULL,
  `staff_created` varchar(14) DEFAULT NULL,
  `staff_check` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_style_part_tb_sign1_idx` (`sign_member_sn`,`sign_created`),
  CONSTRAINT `fk_tb_style_part_tb_sign1` FOREIGN KEY (`sign_member_sn`, `sign_created`) REFERENCES `tb_sign` (`member_sn`, `created`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-11  7:47:16
