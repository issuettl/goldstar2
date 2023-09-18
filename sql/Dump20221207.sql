

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
-- Dumping data for table `tb_bbs`
--


/*!40000 ALTER TABLE `tb_bbs` DISABLE KEYS */;
INSERT INTO `tb_bbs` VALUES (1,'Y','FAQ'),(2,'Y','공지사항');
/*!40000 ALTER TABLE `tb_bbs` ENABLE KEYS */;


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
-- Dumping data for table `tb_bbsctt`
--

/*!40000 ALTER TABLE `tb_bbsctt` DISABLE KEYS */;
INSERT INTO `tb_bbsctt` VALUES (1,2,'N',1,'1제목입니다','내용\n            내용\n            내용',20,'20221102000000'),(2,2,'N',2,'2제목입니다','2',15,'20221115000000'),(3,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(4,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(5,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(6,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(7,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(8,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(9,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(10,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(11,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(12,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',1,'20221102000000'),(13,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',3,'20221102000000'),(14,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',2,'20221102000000'),(15,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',2,'20221102000000'),(16,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',2,'20221102000000'),(17,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',2,'20221102000000'),(18,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',2,'20221102000000'),(19,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',3,'20221102000000'),(20,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',3,'20221102000000'),(21,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',8,'20221102000000'),(22,2,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',15,'20221102000000'),(23,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(24,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(25,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(26,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(27,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(28,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(29,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(30,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(31,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(32,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(33,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(34,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(35,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(36,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(37,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(38,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(39,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(40,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(41,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(42,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(43,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(44,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(45,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(46,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(47,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(48,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(49,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(50,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(51,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(52,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(53,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(54,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(55,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000'),(56,1,'N',2,'금성전파사:새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다. ',0,'20221102000000');
/*!40000 ALTER TABLE `tb_bbsctt` ENABLE KEYS */;

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
-- Dumping data for table `tb_indiv`
--

/*!40000 ALTER TABLE `tb_indiv` DISABLE KEYS */;
INSERT INTO `tb_indiv` VALUES (1,'자네가 노트북을 구매한 이유는 무엇인가?',1,'Y'),(2,'노꾸(노트북 꾸미기)로 자네의 개성을 표현한다면, 어떤 것을 고를 것인가?',2,'Y'),(3,'새로운 노트북으로 구매하고 싶은 유형은 어떤 것인가?',3,'Y');
/*!40000 ALTER TABLE `tb_indiv` ENABLE KEYS */;

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
-- Dumping data for table `tb_indiv_answer`
--

/*!40000 ALTER TABLE `tb_indiv_answer` DISABLE KEYS */;
INSERT INTO `tb_indiv_answer` VALUES (1,1,'N','언제 어디서나 사용하기 위해','type1'),(2,1,'N','온라인 강의 수강을 위해','type2'),(3,1,'N','취미생활을 위해','type3'),(4,1,'N','게임 및 그래픽 작업을 위해','type4'),(5,2,'N','노트북 파우치','type1'),(6,2,'N','키스킨','type2'),(7,2,'N','스티커','type3'),(8,2,'N','노트북 스킨','type4'),(9,3,'N','휴대하기 간편한 ‘그램 시리즈’ ','type1'),(10,3,'N','준수한 성능과 합리적인 가격의 ‘울트라PC’','type2'),(11,3,'N','펜 탑재 & 접을 수 있는 ‘그램360’','type3'),(12,3,'N','고사양 ‘울트라기어 게이밍 노트북’','type4');
/*!40000 ALTER TABLE `tb_indiv_answer` ENABLE KEYS */;

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
-- Dumping data for table `tb_indiv_member`
--

LOCK TABLES `tb_indiv_member` WRITE;
/*!40000 ALTER TABLE `tb_indiv_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_indiv_member` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_indiv_part`
--

LOCK TABLES `tb_indiv_part` WRITE;
/*!40000 ALTER TABLE `tb_indiv_part` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_indiv_part` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_jammy`
--

LOCK TABLES `tb_jammy` WRITE;
/*!40000 ALTER TABLE `tb_jammy` DISABLE KEYS */;
INSERT INTO `tb_jammy` VALUES (1,'123','20221206110228'),(2,'213434234','20221206110453'),(3,'asdfasdf','20221206110653'),(4,'asdfasdf','20221206110901');
/*!40000 ALTER TABLE `tb_jammy` ENABLE KEYS */;
UNLOCK TABLES;

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
  `memo` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`sn`),
  KEY `fk_tb_life_part_tb_member1_idx` (`member_sn`),
  CONSTRAINT `fk_tb_life_part_tb_member1` FOREIGN KEY (`member_sn`) REFERENCES `tb_member` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_life_part`
--

LOCK TABLES `tb_life_part` WRITE;
/*!40000 ALTER TABLE `tb_life_part` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_life_part` ENABLE KEYS */;
UNLOCK TABLES;

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
  `jammy_sn` int(11) NOT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_member`
--

LOCK TABLES `tb_member` WRITE;
/*!40000 ALTER TABLE `tb_member` DISABLE KEYS */;
INSERT INTO `tb_member` VALUES (1,'86F7DB7382BF57BA6785536D425FE105','홍길동','010-1111-9999','20221205171037',0),(2,'2A8F4897F4E370636BB3FC7910D2F16D','홍길동','010-1111-9999','20221205175433',0),(3,'E8A15453C74F682467324E835B349367','홍길동','010-1111-9999','20221205231115',0),(4,'0A90152D5FA4E091A15747A186C59901','홍길동','010-1111-9999','20221206093947',0),(5,'0C002A13314B9EC16A73D56C43EDEC22','홍길동','010-1111-9999','20221206100947',0),(6,'9EA4628F3FC3D242A910890EC45D053C','홍길동','010-1111-9999','20221206105223',4);
/*!40000 ALTER TABLE `tb_member` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_mind`
--

LOCK TABLES `tb_mind` WRITE;
/*!40000 ALTER TABLE `tb_mind` DISABLE KEYS */;
INSERT INTO `tb_mind` VALUES (1,'자네가 식물을 키우고 싶은 가장 큰 이유는 무엇인가?',1,'Y'),(2,'식물을 기를 때 가장 어렵다고 느끼는 점은 무엇인가?',2,'Y'),(3,'다음 중 친구가 화분을 선물로 준다면 받고 싶은 것은?',3,'Y');
/*!40000 ALTER TABLE `tb_mind` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_mind_answer`
--

LOCK TABLES `tb_mind_answer` WRITE;
/*!40000 ALTER TABLE `tb_mind_answer` DISABLE KEYS */;
INSERT INTO `tb_mind_answer` VALUES (1,1,'N','건강한 식재료로 활용하려고','type1'),(2,1,'N','정서 발달과 휵식을 위해','type2'),(3,1,'N','플렌테리어 아이템으로 활용하려고','type3'),(4,2,'N','적정 온도 유지','type1'),(5,2,'N','적정 습도 유지 (물)','type2'),(6,2,'N','적절한 조명 유지 (빛)','type3'),(7,3,'N','싱싱한 로메인 화분','type1'),(8,3,'N','향긋한 페퍼민트 화분','type2'),(9,3,'N','아름다운 메리골드 화분','type3');
/*!40000 ALTER TABLE `tb_mind_answer` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_mind_member`
--

LOCK TABLES `tb_mind_member` WRITE;
/*!40000 ALTER TABLE `tb_mind_member` DISABLE KEYS */;
INSERT INTO `tb_mind_member` VALUES (1,3,'20221205175510'),(1,5,'20221205175510'),(1,7,'20221205175510'),(2,1,'20221206105238'),(2,5,'20221206105238'),(2,9,'20221206105238');
/*!40000 ALTER TABLE `tb_mind_member` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_mind_part`
--

LOCK TABLES `tb_mind_part` WRITE;
/*!40000 ALTER TABLE `tb_mind_part` DISABLE KEYS */;
INSERT INTO `tb_mind_part` VALUES (1,'20221205175510','type1',2,'20221205',NULL,'notyet'),(2,'20221206105238','type3',6,'20221206',NULL,'present');
/*!40000 ALTER TABLE `tb_mind_part` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_mood`
--

LOCK TABLES `tb_mood` WRITE;
/*!40000 ALTER TABLE `tb_mood` DISABLE KEYS */;
INSERT INTO `tb_mood` VALUES (1,'자네가 TV를 구매한다면 어떤 제품을 구매하겠는가?',1,'Y'),(2,'자네는 평소에 TV를 어떻게 사용하는가?',2,'Y'),(3,'LG OLED 화질이 다른 것과 무엇이 다른가?',3,'Y');
/*!40000 ALTER TABLE `tb_mood` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_mood_answer`
--

LOCK TABLES `tb_mood_answer` WRITE;
/*!40000 ALTER TABLE `tb_mood_answer` DISABLE KEYS */;
INSERT INTO `tb_mood_answer` VALUES (1,1,'N','세계 판매 1위 제품','type1'),(2,1,'N','선명한 화질과 우수한 디자인','type2'),(3,1,'N','장시간 보아도 눈이 편안한 제품','type3'),(4,1,'N','환경을 생각한 친환경 제품','type4'),(5,2,'N','영화 감상','type1'),(6,2,'N','스포츠 관람','type2'),(7,2,'N','게임 플레이','type3'),(8,2,'N','공간 인테리어','type4'),(9,3,'N','선명도','type1'),(10,3,'N','채도','type2'),(11,3,'N','대비','type3'),(12,3,'N','명암','type4');
/*!40000 ALTER TABLE `tb_mood_answer` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_mood_member`
--

LOCK TABLES `tb_mood_member` WRITE;
/*!40000 ALTER TABLE `tb_mood_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_mood_member` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_mood_part`
--

LOCK TABLES `tb_mood_part` WRITE;
/*!40000 ALTER TABLE `tb_mood_part` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_mood_part` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_refresh_part`
--

LOCK TABLES `tb_refresh_part` WRITE;
/*!40000 ALTER TABLE `tb_refresh_part` DISABLE KEYS */;
INSERT INTO `tb_refresh_part` VALUES (1,'20221206095735',2,'20221205','20221206095735','present');
/*!40000 ALTER TABLE `tb_refresh_part` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`member_sn`,`created`),
  CONSTRAINT `fk_tb_join_tb_member1` FOREIGN KEY (`member_sn`) REFERENCES `tb_member` (`sn`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sign`
--

LOCK TABLES `tb_sign` WRITE;
/*!40000 ALTER TABLE `tb_sign` DISABLE KEYS */;
INSERT INTO `tb_sign` VALUES (2,'20221205','스무디맥스','type1',NULL,NULL),(2,'20221206','박정경','type2',NULL,NULL),(4,'20221206','테스트','type1',NULL,NULL),(6,'20221206','박정경','type1',NULL,NULL);
/*!40000 ALTER TABLE `tb_sign` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_style`
--

LOCK TABLES `tb_style` WRITE;
/*!40000 ALTER TABLE `tb_style` DISABLE KEYS */;
INSERT INTO `tb_style` VALUES (1,'자네가 옷을 관리할 때 가장 신경 쓰는 부분은 무엇인가?',1,'Y'),(2,'다음 중 자네가 가장 좋아하는 색은 무엇인가?',2,'Y'),(3,'다음 중 자네가 즐겨 입는 의류의 종류는 무엇인가?',3,'Y');
/*!40000 ALTER TABLE `tb_style` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_style_answer`
--

LOCK TABLES `tb_style_answer` WRITE;
/*!40000 ALTER TABLE `tb_style_answer` DISABLE KEYS */;
INSERT INTO `tb_style_answer` VALUES (1,1,'N','건조','type1'),(2,1,'N','살균','type2'),(3,1,'N','탈취','type3'),(4,1,'N','구김 제거','type4'),(5,2,'N','블랙 틴트 미러','type1'),(6,2,'N','크림 화이트','type2'),(7,2,'N','미스트 핑크','type3'),(8,2,'N','미스트 베이지','type4'),(9,3,'N','실크 블라우스','type1'),(10,3,'N','면 셔츠','type2'),(11,3,'N','청 자켓','type3'),(12,3,'N','캐시미어 니트','type4');
/*!40000 ALTER TABLE `tb_style_answer` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tb_style_member`
--

LOCK TABLES `tb_style_member` WRITE;
/*!40000 ALTER TABLE `tb_style_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_style_member` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_style_part`
--

LOCK TABLES `tb_style_part` WRITE;
/*!40000 ALTER TABLE `tb_style_part` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_style_part` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-07  6:10:13
