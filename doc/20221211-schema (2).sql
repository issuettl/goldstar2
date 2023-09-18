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
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `tb_bbs`
--

LOCK TABLES `tb_bbs` WRITE;
/*!40000 ALTER TABLE `tb_bbs` DISABLE KEYS */;
INSERT INTO `tb_bbs` VALUES (1,'Y','FAQ'),(2,'Y','공지사항');
/*!40000 ALTER TABLE `tb_bbs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_bbsctt`
--

LOCK TABLES `tb_bbsctt` WRITE;
/*!40000 ALTER TABLE `tb_bbsctt` DISABLE KEYS */;
INSERT INTO `tb_bbsctt` VALUES (1,1,'N',1,'금성전파사 운영시간이 궁금해요','금성전파사 운영시간은 09:00~20:00(총 11시간) 입니다.\n① 체험 운영시간 11:00~19:00(총 8시간)② 전시 관람 운영시간 09:00~11:00 / 19:00~20:00(총 3시간)',0,'20221102000000'),(2,1,'N',3,'굿즈는 온라인에서 구매할 수 있나요?','아니요.\n굿즈는 금성전파사:새로고침센터 공간을 방문하여 구매 가능합니다.',0,'20221102000000'),(3,1,'N',4,'아이디/비밀번호를 잊어버렸어요','로그인 화면 하단의 \'아이디 찾기\', \'비밀번호 찾기\' 버튼을 눌러 진행하시면 됩니다.',0,'20221102000000'),(4,1,'N',5,'기존 LGE.CIOM 멤버십 회원인데, 아이디를 찾을 수 없다고 합니다. ','LG전자 회원제(아이디/비밀번호 로그인)가 2020년 4월 14일부터 도입되어 회원이 통합 되었습니다.\n해당 일자 이후에 LG전자 회원가입을 하지 않은 멤버십앱 고객님의 경우 LG전자 회원 가입하여 멤버십앱 로그인을 해주시기 바랍니다. 회원 가입하고 멤버십앱 로그인을 하면 기존에 보유하고 있던 멤버십 포인트 및 구매내역은 자동으로 반영됩니다.\n\n* 멤버십앱 회원가입 : [멤버십 가입] 메뉴에서 본인인증 절차를 통하여 가입',0,'20221102000000'),(5,1,'N',6,'로그인이 되지 않습니다','1. 아이디 찾기를 통해 통합 회원 가입 여부를 확인해 주세요. 아이디가 조회 되지 않을 경우, 회원가입 후 서비스 이용이 가능합니다.\n*LG전자 통합 회원제 도입(2020년4월13일) 이후 통합 회원 전환 가입이 되지 않은 기존 멤버십 회원인 경우, 회원가입 후 멤버십앱에서 로그인하면 기존에 보유하고 있던 멤버십 포인트 및 구매 내역이 자동 반영됩니다.\n\n2. 아이디가 이메일 아이디인지 전화번호 아이디인지 확인해 주세요. 아이디 찾기를 통해 아이디를 확인하신 후 로그인 바랍니다.\n\n3. 아이디 또는 비밀번호 입력 시, 대소문자를 구분하여 정확히 입력해 주세요. 모바일의 경우 자동 대문자 설정으로 아이디 또는 비밀번호가 불일치하게 나타날 수도 있으니, 설정을 확인하신 후 입력하시기 바랍니다.',0,'20221102000000'),(6,1,'N',7,'금성전파사 : 새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.\n다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다.',0,'20221102000000'),(24,1,'N',2,'선물함의 쿠폰/리워드 사용방법에 대해 알고 싶어요. ','Jammy 포인트는 발급된 난수 번호를 Jammy 사이트에 방문하여 등록해주세요.\n금성전파사 각 코-너에서 제공되는 리워드는 발급된 QR코드를 각 코-너의 스탭에게 보여주세요.',0,'20221102000000');
/*!40000 ALTER TABLE `tb_bbsctt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_indiv`
--

LOCK TABLES `tb_indiv` WRITE;
/*!40000 ALTER TABLE `tb_indiv` DISABLE KEYS */;
INSERT INTO `tb_indiv` VALUES (1,'자네가 노트북을 구매한 이유는 무엇인가?',1,'Y'),(2,'노꾸(노트북 꾸미기)로 자네의 개성을 표현한다면, 어떤 것을 고를 것인가?',2,'Y'),(3,'새로운 노트북으로 구매하고 싶은 유형은 어떤 것인가?',3,'Y');
/*!40000 ALTER TABLE `tb_indiv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_indiv_answer`
--

LOCK TABLES `tb_indiv_answer` WRITE;
/*!40000 ALTER TABLE `tb_indiv_answer` DISABLE KEYS */;
INSERT INTO `tb_indiv_answer` VALUES (1,1,'N','언제 어디서나 사용하기 위해','type1'),(2,1,'N','온라인 강의 수강을 위해','type2'),(3,1,'N','취미생활을 위해','type3'),(4,1,'N','게임 및 그래픽 작업을 위해','type4'),(5,2,'N','노트북 파우치','type1'),(6,2,'N','키스킨','type2'),(7,2,'N','스티커','type3'),(8,2,'N','노트북 스킨','type4'),(9,3,'N','휴대하기 간편한 ‘그램 시리즈’ ','type1'),(10,3,'N','준수한 성능과 합리적인 가격의 ‘울트라PC’','type2'),(11,3,'N','펜 탑재 & 접을 수 있는 ‘그램360’','type3'),(12,3,'N','고사양 ‘울트라기어 게이밍 노트북’','type4');
/*!40000 ALTER TABLE `tb_indiv_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_indiv_member`
--

LOCK TABLES `tb_indiv_member` WRITE;
/*!40000 ALTER TABLE `tb_indiv_member` DISABLE KEYS */;
INSERT INTO `tb_indiv_member` VALUES (1,2,'20221208091556'),(1,6,'20221208091556'),(1,10,'20221208091556'),(2,3,'20221208112129'),(2,7,'20221208112129'),(2,11,'20221208112129'),(3,1,'20221208112654'),(3,5,'20221208112654'),(3,9,'20221208112654'),(5,3,'20221208180849'),(5,7,'20221208180849'),(5,10,'20221208180849'),(6,3,'20221208181536'),(6,7,'20221208181536'),(6,11,'20221208181536');
/*!40000 ALTER TABLE `tb_indiv_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_indiv_part`
--

LOCK TABLES `tb_indiv_part` WRITE;
/*!40000 ALTER TABLE `tb_indiv_part` DISABLE KEYS */;
INSERT INTO `tb_indiv_part` VALUES (1,'20221208091554','type2',19,'20221208',NULL,'notyet'),(2,'20221208112129','type3',22,'20221208',NULL,'notyet'),(3,'20221208112654','type1',25,'20221208',NULL,'notyet'),(4,'20221208113027',NULL,29,'20221208',NULL,'notyet'),(5,'20221208180849','type3',46,'20221208',NULL,'notyet'),(6,'20221208181536','type3',47,'20221208',NULL,'notyet');
/*!40000 ALTER TABLE `tb_indiv_part` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_jammy`
--

LOCK TABLES `tb_jammy` WRITE;
/*!40000 ALTER TABLE `tb_jammy` DISABLE KEYS */;
INSERT INTO `tb_jammy` VALUES (1,'123','20221206110228'),(2,'213434234','20221206110453'),(3,'asdfasdf','20221206110653'),(4,'asdfasdf','20221206110901');
/*!40000 ALTER TABLE `tb_jammy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_life_date`
--

LOCK TABLES `tb_life_date` WRITE;
/*!40000 ALTER TABLE `tb_life_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_life_date` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_life_part`
--

LOCK TABLES `tb_life_part` WRITE;
/*!40000 ALTER TABLE `tb_life_part` DISABLE KEYS */;
INSERT INTO `tb_life_part` VALUES (1,41,'weekday',2,'20221210','PM16','status4','20221208153427',NULL,NULL,'20221210224818',NULL,'20221210233516','present'),(2,44,'weekend',4,'20221208','PM13','status3','20221208175910',NULL,'20221211005537',NULL,NULL,NULL,NULL),(3,44,'weekend',4,'20221208','PM13','status2','20221208180818',NULL,'20221211005537',NULL,NULL,NULL,NULL),(4,47,'weekend',3,'20221208','PM13','status3','20221208181736',NULL,'20221211005537',NULL,NULL,NULL,NULL),(5,47,'weekday',5,'20221224','PM16','status6','20221208181848',NULL,NULL,'20221210203621','asdfa',NULL,NULL),(6,44,'weekend',5,'20221208','PM15','status2','20221208182644',NULL,'20221211010311',NULL,NULL,NULL,NULL),(7,48,'weekend',5,'20221215','PM14','status3','20221208185118',NULL,'20221211005340',NULL,NULL,NULL,NULL),(8,50,'weekend',5,'20221208','PM15','status2','20221208230833',NULL,'20221211005205',NULL,NULL,NULL,NULL),(13,51,'weekday',2,'20221219','AM11','status5','20221211043356','20221211053712',NULL,NULL,NULL,NULL,NULL),(14,51,'weekend',5,'20221225','AM11','status5','20221211043648','20221211053653',NULL,NULL,NULL,NULL,NULL),(15,51,'weekend',4,'20221224','PM16','status1','20221211044901',NULL,NULL,NULL,NULL,NULL,NULL),(16,51,'weekend',5,'20221225','PM14','status5','20221211070337','20221211071319',NULL,NULL,NULL,NULL,NULL),(17,51,'weekend',5,'20221225','PM14','status5','20221211071008','20221211071309',NULL,NULL,NULL,NULL,NULL),(18,51,'weekend',5,'20221225','PM13','status1','20221211071121',NULL,NULL,NULL,NULL,NULL,NULL),(19,51,'weekend',5,'20221225','PM16','status1','20221211071413',NULL,NULL,NULL,NULL,NULL,NULL),(20,51,'weekday',5,'20221219','PM14','status2','20221211072928',NULL,NULL,NULL,NULL,NULL,NULL),(21,51,'weekday',5,'20221219','PM15','status2','20221211072945',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_life_part` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_manager`
--

LOCK TABLES `tb_manager` WRITE;
/*!40000 ALTER TABLE `tb_manager` DISABLE KEYS */;
INSERT INTO `tb_manager` VALUES (1,'admin-temp1','temp1','temp','b6ac5f6e346d879272fd01fd27629a06c8ca6d139c7e1c32c6040d692403b3a83e5649338b1faa3f77a68fb2f37ff2033f4bb0e6b746a1543604448b264347eb','20221202111111','20221202111111',0,'20221202111111'),(2,'admin-temp2','temp2','temp','e4d9c2eadcbd6987c2afae60a4b4f86dae81347dcd07099b05e854f32f09934469ffc94bdf58bc767646345e441ccf34798c28fbfc9089f2398bbef71c1b1375','20221202111111','20221202111111',0,'20221202111111'),(3,'brightbell1','brightbell1','brightbell','caa5a7e785d02337f38471fce79dd47831f4fdec9d5644b116a3df448cf8ee9acb883035cb47d3162ae28bb8d71ffbf7bd66068e493acc0b2e88f814e6c50e4c','20221202111111','20221202111111',0,'20221202111111'),(4,'brightbell2','brightbell2','brightbell','8619f7e4c544d40253877d866c4f88d171a0b8c4bb9ee80e1480acc9b8e08b2cca234fdbf971501381969940d4b886a14c57ae3bb05e7b1acd5272d4f25c2548','20221202111111','20221202111111',0,'20221202111111');
/*!40000 ALTER TABLE `tb_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_member`
--

LOCK TABLES `tb_member` WRITE;
/*!40000 ALTER TABLE `tb_member` DISABLE KEYS */;
INSERT INTO `tb_member` VALUES (1,'86F7DB7382BF57BA6785536D425FE105','홍길동','010-1111-9999','20221205171037','',0),(2,'2A8F4897F4E370636BB3FC7910D2F16D','홍길동','010-1111-9999','20221205175433','',0),(3,'E8A15453C74F682467324E835B349367','홍길동','010-1111-9999','20221205231115','',0),(4,'0A90152D5FA4E091A15747A186C59901','홍길동','010-1111-9999','20221206093947','',0),(5,'0C002A13314B9EC16A73D56C43EDEC22','홍길동','010-1111-9999','20221206100947','',0),(6,'9EA4628F3FC3D242A910890EC45D053C','홍길동','010-1111-9999','20221206105223','',4),(7,'F3779D4D3AFF4A681915B8FF895BD2FB','홍길동','010-1111-9999','20221207144326','',0),(8,'F3779D4D3AFF4A681915B8FF895BD2FB','홍길동','010-1111-9999','20221207144329','',0),(9,'19D1E742A1528E1903C2B6F20DA923A6','홍길동','010-1111-9999','20221207150421','',0),(10,'A85A718BBDF7D030B40BA403A59CAFD4','홍길동','010-1111-9999','20221207151255','',0),(11,'8D7F9CBF3E37BDC9D6E251FCDC70CBAF','홍길동','010-1111-9999','20221207151350','',0),(12,'3ADB500A07348521D8A6B3032F416A15','홍길동','010-1111-9999','20221208002937','',0),(13,'E5E845227E007A6CEC2AEED756335B6B','홍길동','010-1111-9999','20221208015022','',0),(14,'9D49053AD743A95BEA3974682B32F6BB','홍길동','010-1111-9999','20221208023913','',0),(15,'D87851983843BE268D9B6E85AA4438FF','홍길동','010-1111-9999','20221208082535','',0),(16,'738F3F5C1D68EFD5B927AB4131B74279','홍길동','010-1111-9999','20221208082717','',0),(17,'CD9815004A3995F2DF25AEA560CA458D','홍길동','010-1111-9999','20221208082719','',0),(18,'AB343C1BC3A617073753653A4CE9ADC7','홍길동','010-1111-9999','20221208083017','',0),(19,'F71DE5AE0570EB30AF53CA2CEC634DDB','홍길동','010-1111-9999','20221208091456','',0),(20,'34A8D54BD2F77586200F0F50B2C6A599','홍길동','010-1111-9999','20221208112015','',0),(21,'A587AC3CF4C9498A485F1B0BFF29D0AA','홍길동','010-1111-9999','20221208112018','',0),(22,'38E839C2C3E846FA97AF96D7CD816A2A','홍길동','010-1111-9999','20221208112046','',0),(23,'1853C589E27578493A2D974F1816A05B','홍길동','010-1111-9999','20221208112121','',0),(24,'2F536678335E137B300E5D5024980957','홍길동','010-1111-9999','20221208112405','',0),(25,'AEE99F71EA111C2EFACE7E6881B53B55','홍길동','010-1111-9999','20221208112418','',0),(26,'AD0599487C78EB8B0CB2E07F9E569B81','홍길동','010-1111-9999','20221208112418','',0),(27,'E388A7F2AA519C5B4F3E2A6B224D7F9E','홍길동','010-1111-9999','20221208112508','',0),(28,'8CF5DFE7FAA646C76CEC8379C8C921AC','홍길동','010-1111-9999','20221208112541','',0),(29,'0D1146105692B12877F876F7EB425E8E','홍길동','010-1111-9999','20221208112611','',0),(30,'06E29114099058B6B3E920960B830EA7','홍길동','010-1111-9999','20221208112745','',0),(31,'1AD7CA4137474C787776D29E5CF68517','홍길동','010-1111-9999','20221208112923','',0),(32,'FEF96C5BB41B4D3F1F4FE0931B90145C','홍길동','010-1111-9999','20221208112933','',0),(33,'F025CC2BF7778D8B4E8083A9363D4E1D','홍길동','010-1111-9999','20221208113150','',0),(34,'F025CC2BF7778D8B4E8083A9363D4E1D','홍길동','010-1111-9999','20221208113223','',0),(35,'D9B4549A53953E380B5906FBFF5DB30D','홍길동','010-1111-9999','20221208113603','',0),(36,'092FE51BBE9C1EF5EBBC42E835BFD9F2','홍길동','010-1111-9999','20221208114321','',0),(37,'7F7646A3B660914010D9875AF50BEBC6','홍길동','010-1111-9999','20221208134316','',0),(38,'67272D3AA450D3A74235FB71600F3706','홍길동','010-1111-9999','20221208150529','',0),(39,'34DF3A978EE677D6B48B909258A72BF7','홍길동','010-1111-9999','20221208150744','',0),(40,'123CD6A2289A73BD20F305457AF4D268','홍길동','010-1111-9999','20221208150825','',0),(41,'3E29BB6E25F737BDA7345E0A2230C881','홍길동','010-1111-9999','20221208151210','',0),(42,'C46D935A8416BC563AA830F5ED39E7D4','홍길동','010-1111-9999','20221208153347','',0),(43,'2CACCD75A5BE590D3478A4E6F93D6415','홍길동','010-1111-9999','20221208165831','',0),(44,'71C78A8FCA7354F51776DC750E61F6BB','홍길동','010-1111-9999','20221208175910','',0),(45,'7EAA1FBC3872E2225CA6C6DD8622D2B5','홍길동','010-1111-9999','20221208175953','',0),(46,'EEB86955AEF04BA8AA477C4D797C6BBF','홍길동','010-1111-9999','20221208180802','',0),(47,'4137DC0C318DF990C897B65FA55D69CA','홍길동','010-1111-9999','20221208181500','',0),(48,'D6A1E29D910C744130AC7EC1F1B737D5','홍길동','010-1111-9999','20221208185118','',0),(49,'B4E6EC9E656CA955BBCC69FDDD831DA0','홍길동','010-1111-9999','20221208224353','',0),(50,'local-test','3Dpgh3/KIbDwXJsvjbrBCA==','8N8rO0WG7P+OA96hLBMe+g==','20221208230410','',0),(51,'Xto0tOYbcojkixXjpNWSoA==','3Dpgh3/KIbDwXJsvjbrBCA==','8N8rO0WG7P+OA96hLBMe+g==','20221209094728','20221211070315',0);
/*!40000 ALTER TABLE `tb_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_mind`
--

LOCK TABLES `tb_mind` WRITE;
/*!40000 ALTER TABLE `tb_mind` DISABLE KEYS */;
INSERT INTO `tb_mind` VALUES (1,'자네가 식물을 키우고 싶은 가장 큰 이유는 무엇인가?',1,'Y'),(2,'식물을 기를 때 가장 어렵다고 느끼는 점은 무엇인가?',2,'Y'),(3,'다음 중 친구가 화분을 선물로 준다면 받고 싶은 것은?',3,'Y');
/*!40000 ALTER TABLE `tb_mind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_mind_answer`
--

LOCK TABLES `tb_mind_answer` WRITE;
/*!40000 ALTER TABLE `tb_mind_answer` DISABLE KEYS */;
INSERT INTO `tb_mind_answer` VALUES (1,1,'N','건강한 식재료로 활용하려고','type1'),(2,1,'N','정서 발달과 휵식을 위해','type2'),(3,1,'N','플렌테리어 아이템으로 활용하려고','type3'),(4,2,'N','적정 온도 유지','type1'),(5,2,'N','적정 습도 유지 (물)','type2'),(6,2,'N','적절한 조명 유지 (빛)','type3'),(7,3,'N','싱싱한 로메인 화분','type1'),(8,3,'N','향긋한 페퍼민트 화분','type2'),(9,3,'N','아름다운 메리골드 화분','type3');
/*!40000 ALTER TABLE `tb_mind_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_mind_member`
--

LOCK TABLES `tb_mind_member` WRITE;
/*!40000 ALTER TABLE `tb_mind_member` DISABLE KEYS */;
INSERT INTO `tb_mind_member` VALUES (1,3,'20221205175510'),(1,5,'20221205175510'),(1,7,'20221205175510'),(2,1,'20221206105238'),(2,5,'20221206105238'),(2,9,'20221206105238'),(3,2,'20221207150450'),(3,5,'20221207150450'),(3,8,'20221207150450'),(4,2,'20221208083052'),(4,5,'20221208083052'),(4,8,'20221208083052'),(5,1,'20221208112633'),(5,6,'20221208112633'),(5,8,'20221208112633'),(6,1,'20221208112726'),(6,4,'20221208112726'),(6,8,'20221208112726'),(7,2,'20221208112935'),(7,4,'20221208112935'),(7,7,'20221208112935'),(8,3,'20221208113007'),(8,4,'20221208113007'),(8,9,'20221208113007'),(10,1,'20221208170110'),(10,4,'20221208170110'),(10,7,'20221208170110'),(11,2,'20221208180842'),(11,5,'20221208180842'),(11,9,'20221208180842');
/*!40000 ALTER TABLE `tb_mind_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_mind_part`
--

LOCK TABLES `tb_mind_part` WRITE;
/*!40000 ALTER TABLE `tb_mind_part` DISABLE KEYS */;
INSERT INTO `tb_mind_part` VALUES (1,'20221205175510','type1',2,'20221205',NULL,'notyet'),(2,'20221206105238','type3',6,'20221206',NULL,'present'),(3,'20221207150448','type2',9,'20221207',NULL,'notyet'),(4,'20221208083048','type2',18,'20221208',NULL,'notyet'),(5,'20221208112633','type2',25,'20221208',NULL,'notyet'),(6,'20221208112726','type1',28,'20221208',NULL,'notyet'),(7,'20221208112935','type1',24,'20221208',NULL,'notyet'),(8,'20221208113006','type3',29,'20221208',NULL,'notyet'),(9,'20221208113128',NULL,32,'20221208',NULL,'notyet'),(10,'20221208170109','type1',43,'20221208',NULL,'notyet'),(11,'20221208180842','type2',46,'20221208',NULL,'notyet');
/*!40000 ALTER TABLE `tb_mind_part` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_mood`
--

LOCK TABLES `tb_mood` WRITE;
/*!40000 ALTER TABLE `tb_mood` DISABLE KEYS */;
INSERT INTO `tb_mood` VALUES (1,'자네가 TV를 구매한다면 어떤 제품을 구매하겠는가?',1,'Y'),(2,'자네는 평소에 TV를 어떻게 사용하는가?',2,'Y'),(3,'LG OLED 화질이 다른 것과 무엇이 다른가?',3,'Y');
/*!40000 ALTER TABLE `tb_mood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_mood_answer`
--

LOCK TABLES `tb_mood_answer` WRITE;
/*!40000 ALTER TABLE `tb_mood_answer` DISABLE KEYS */;
INSERT INTO `tb_mood_answer` VALUES (1,1,'N','세계 판매 1위 제품','type1'),(2,1,'N','선명한 화질과 우수한 디자인','type2'),(3,1,'N','장시간 보아도 눈이 편안한 제품','type3'),(4,1,'N','환경을 생각한 친환경 제품','type4'),(5,2,'N','영화 감상','type1'),(6,2,'N','스포츠 관람','type2'),(7,2,'N','게임 플레이','type3'),(8,2,'N','공간 인테리어','type4'),(9,3,'N','선명도','type1'),(10,3,'N','채도','type2'),(11,3,'N','대비','type3'),(12,3,'N','명암','type4');
/*!40000 ALTER TABLE `tb_mood_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_mood_member`
--

LOCK TABLES `tb_mood_member` WRITE;
/*!40000 ALTER TABLE `tb_mood_member` DISABLE KEYS */;
INSERT INTO `tb_mood_member` VALUES (1,2,'20221208082750'),(1,7,'20221208082750'),(1,9,'20221208082750'),(2,2,'20221208113310'),(2,8,'20221208113310'),(2,9,'20221208113310'),(3,2,'20221208180829'),(3,6,'20221208180829'),(3,10,'20221208180829');
/*!40000 ALTER TABLE `tb_mood_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_mood_part`
--

LOCK TABLES `tb_mood_part` WRITE;
/*!40000 ALTER TABLE `tb_mood_part` DISABLE KEYS */;
INSERT INTO `tb_mood_part` VALUES (1,'20221208082750','type1',16,'20221208',NULL,'notyet'),(2,'20221208113309','type1',28,'20221208',NULL,'notyet'),(3,'20221208180829','type2',46,'20221208',NULL,'notyet');
/*!40000 ALTER TABLE `tb_mood_part` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_refresh_part`
--

LOCK TABLES `tb_refresh_part` WRITE;
/*!40000 ALTER TABLE `tb_refresh_part` DISABLE KEYS */;
INSERT INTO `tb_refresh_part` VALUES (1,'20221206095735',2,'20221205','20221206095735','present');
/*!40000 ALTER TABLE `tb_refresh_part` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_sign`
--

LOCK TABLES `tb_sign` WRITE;
/*!40000 ALTER TABLE `tb_sign` DISABLE KEYS */;
INSERT INTO `tb_sign` VALUES (2,'20221205','스무디맥스','type1',NULL,NULL,''),(2,'20221206','박정경','type2',NULL,NULL,''),(4,'20221206','테스트','type1',NULL,NULL,''),(6,'20221206','박정경','type1',NULL,NULL,''),(8,'20221207','아','type2',NULL,NULL,''),(9,'20221207','개발','type1',NULL,NULL,''),(10,'20221207','박정경','type5',NULL,NULL,''),(11,'20221207','개발','type2',NULL,NULL,''),(13,'20221208','개발','type5',NULL,NULL,'20221208015030'),(14,'20221208','박정경','type1',NULL,NULL,'20221208023920'),(15,'20221208','아아아아','type3',NULL,NULL,'20221208082548'),(16,'20221208','박지성','type1',NULL,NULL,'20221208082733'),(18,'20221208','김가오나씨','type1',NULL,NULL,'20221208083033'),(19,'20221208','아르미다','type6',NULL,NULL,'20221208091508'),(20,'20221208','김짱','type1',NULL,NULL,'20221208112022'),(21,'20221208','동그라미네','type2',NULL,NULL,'20221208112037'),(22,'20221208','아르미야','type3',NULL,NULL,'20221208112051'),(23,'20221208','김승훈짱','type4',NULL,NULL,'20221208112129'),(24,'20221208','룰루우우우','type4',NULL,NULL,'20221208112413'),(25,'20221208','금영','type3',NULL,NULL,'20221208112428'),(26,'20221208','금옥','type6',NULL,NULL,'20221208112428'),(27,'20221208','테스트안내','type2',NULL,NULL,'20221208112518'),(28,'20221208','꽁주비주','type4',NULL,NULL,'20221208112547'),(29,'20221208','아토아람','type4',NULL,NULL,'20221208112632'),(31,'20221208','브라이트벨','type2',NULL,NULL,'20221208112932'),(32,'20221208','샛별이','type4',NULL,NULL,'20221208112948'),(34,'20221208','멍청이','type2',NULL,NULL,'20221208113410'),(35,'20221208','이병현','type1',NULL,NULL,'20221208113611'),(36,'20221208','샷','type4',NULL,NULL,'20221208114329'),(37,'20221208','뭄뭄','type2',NULL,NULL,'20221208134323'),(38,'20221208','금히','type5',NULL,NULL,'20221208150533'),(39,'20221208','금히',NULL,NULL,NULL,'20221208150748'),(40,'20221208','김동현','type2',NULL,NULL,'20221208150831'),(41,'20221208','김동현','type3',NULL,NULL,'20221208152953'),(42,'20221208','금성전파사','type1',NULL,NULL,'20221208153356'),(43,'20221208','뭄무','type3',NULL,NULL,'20221208165836'),(45,'20221208','미미','type2',NULL,NULL,'20221208180002'),(46,'20221208','김','type2',NULL,NULL,'20221208180807'),(47,'20221208','김동현이','type3',NULL,NULL,'20221208181508'),(49,'20221208','인싸잇','type5',NULL,NULL,'20221208224431'),(50,'20221208','표준한글','type2',NULL,NULL,'20221208230813'),(51,'20221209','오호','type2',NULL,NULL,'20221209095004');
/*!40000 ALTER TABLE `tb_sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_style`
--

LOCK TABLES `tb_style` WRITE;
/*!40000 ALTER TABLE `tb_style` DISABLE KEYS */;
INSERT INTO `tb_style` VALUES (1,'자네가 옷을 관리할 때 가장 신경 쓰는 부분은 무엇인가?',1,'Y'),(2,'다음 중 자네가 가장 좋아하는 색은 무엇인가?',2,'Y'),(3,'다음 중 자네가 즐겨 입는 의류의 종류는 무엇인가?',3,'Y');
/*!40000 ALTER TABLE `tb_style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_style_answer`
--

LOCK TABLES `tb_style_answer` WRITE;
/*!40000 ALTER TABLE `tb_style_answer` DISABLE KEYS */;
INSERT INTO `tb_style_answer` VALUES (1,1,'N','건조','type1'),(2,1,'N','살균','type2'),(3,1,'N','탈취','type3'),(4,1,'N','구김 제거','type4'),(5,2,'N','블랙 틴트 미러','type1'),(6,2,'N','크림 화이트','type2'),(7,2,'N','미스트 핑크','type3'),(8,2,'N','미스트 베이지','type4'),(9,3,'N','실크 블라우스','type1'),(10,3,'N','면 셔츠','type2'),(11,3,'N','청 자켓','type3'),(12,3,'N','캐시미어 니트','type4');
/*!40000 ALTER TABLE `tb_style_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_style_member`
--

LOCK TABLES `tb_style_member` WRITE;
/*!40000 ALTER TABLE `tb_style_member` DISABLE KEYS */;
INSERT INTO `tb_style_member` VALUES (1,2,'20221208083113'),(1,7,'20221208083113'),(1,11,'20221208083113'),(2,3,'20221208092208'),(2,7,'20221208092208'),(2,10,'20221208092208'),(3,2,'20221208112922'),(3,6,'20221208112922'),(3,10,'20221208112922'),(4,1,'20221208114342'),(4,5,'20221208114342'),(4,9,'20221208114342'),(5,1,'20221208180019'),(5,5,'20221208180019'),(5,12,'20221208180019'),(6,3,'20221208180819'),(6,7,'20221208180819'),(6,11,'20221208180819');
/*!40000 ALTER TABLE `tb_style_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tb_style_part`
--

LOCK TABLES `tb_style_part` WRITE;
/*!40000 ALTER TABLE `tb_style_part` DISABLE KEYS */;
INSERT INTO `tb_style_part` VALUES (1,'20221208083108','type3',18,'20221208',NULL,'notyet'),(2,'20221208092208','type3',19,'20221208',NULL,'notyet'),(3,'20221208112922','type2',25,'20221208',NULL,'notyet'),(4,'20221208114342','type1',36,'20221208',NULL,'notyet'),(5,'20221208180019','type1',45,'20221208',NULL,'notyet'),(6,'20221208180819','type3',46,'20221208',NULL,'notyet');
/*!40000 ALTER TABLE `tb_style_part` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-11  7:47:25
