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
-- Dumping data for table `tb_bbsctt`
--

Jammy 포인트는 발급된 난수 번호를 Jammy 사이트에 방문하여 등록해주세요.
금성전파사 각 코-너에서 제공되는 리워드는 발급된 QR코드를 각 코-너의 스탭에게 보여주세요.


LOCK TABLES `tb_bbsctt` WRITE;
/*!40000 ALTER TABLE `tb_bbsctt` DISABLE KEYS */;
INSERT INTO `tb_bbsctt` VALUES 
(1,1,'N',1,'금성전파사 운영시간이 궁금해요','금성전파사 운영시간은 09:00~20:00(총 11시간) 입니다.
① 체험 운영시간 11:00~19:00(총 8시간)② 전시 관람 운영시간 09:00~11:00 / 19:00~20:00(총 3시간)',0,'20221102000000'),
(24,1,'N',2,'선물함의 쿠폰/리워드 사용방법에 대해 알고 싶어요. ','Jammy 포인트는 발급된 난수 번호를 Jammy 사이트에 방문하여 등록해주세요.
금성전파사 각 코-너에서 제공되는 리워드는 발급된 QR코드를 각 코-너의 스탭에게 보여주세요.',0,'20221102000000'),
(2,1,'N',3,'굿즈는 온라인에서 구매할 수 있나요?','아니요.
굿즈는 금성전파사:새로고침센터 공간을 방문하여 구매 가능합니다.',0,'20221102000000'),
(3,1,'N',4,'아이디/비밀번호를 잊어버렸어요','로그인 화면 하단의 \'아이디 찾기\', \'비밀번호 찾기\' 버튼을 눌러 진행하시면 됩니다.',0,'20221102000000'),
(4,1,'N',5,'기존 LGE.CIOM 멤버십 회원인데, 아이디를 찾을 수 없다고 합니다. ','LG전자 회원제(아이디/비밀번호 로그인)가 2020년 4월 14일부터 도입되어 회원이 통합 되었습니다.
해당 일자 이후에 LG전자 회원가입을 하지 않은 멤버십앱 고객님의 경우 LG전자 회원 가입하여 멤버십앱 로그인을 해주시기 바랍니다. 회원 가입하고 멤버십앱 로그인을 하면 기존에 보유하고 있던 멤버십 포인트 및 구매내역은 자동으로 반영됩니다.

* 멤버십앱 회원가입 : [멤버십 가입] 메뉴에서 본인인증 절차를 통하여 가입',0,'20221102000000'),
(5,1,'N',6,'로그인이 되지 않습니다','1. 아이디 찾기를 통해 통합 회원 가입 여부를 확인해 주세요. 아이디가 조회 되지 않을 경우, 회원가입 후 서비스 이용이 가능합니다.
*LG전자 통합 회원제 도입(2020년4월13일) 이후 통합 회원 전환 가입이 되지 않은 기존 멤버십 회원인 경우, 회원가입 후 멤버십앱에서 로그인하면 기존에 보유하고 있던 멤버십 포인트 및 구매 내역이 자동 반영됩니다.

2. 아이디가 이메일 아이디인지 전화번호 아이디인지 확인해 주세요. 아이디 찾기를 통해 아이디를 확인하신 후 로그인 바랍니다.

3. 아이디 또는 비밀번호 입력 시, 대소문자를 구분하여 정확히 입력해 주세요. 모바일의 경우 자동 대문자 설정으로 아이디 또는 비밀번호가 불일치하게 나타날 수도 있으니, 설정을 확인하신 후 입력하시기 바랍니다.',0,'20221102000000'),
(6,1,'N',7,'금성전파사 : 새로고침센터 가입자만 공간을 방문할 수 있나요?','아니요. 공간은 가입 여부 상관 없이 누구나 방문 가능합니다.
다만, 금성전파사에서 제공하는 체험에 참여하기 위해서는 가입이 필수입니다.',0,'20221102000000');
/*!40000 ALTER TABLE `tb_bbsctt` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-08  2:10:34
