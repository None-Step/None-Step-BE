-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: nonestepdb
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aed`
--

DROP TABLE IF EXISTS `aed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aed` (
  `aed_no` bigint NOT NULL AUTO_INCREMENT,
  `aed_address` varchar(500) DEFAULT NULL,
  `aed_comment` varchar(500) DEFAULT NULL,
  `aed_latitude` decimal(18,13) DEFAULT NULL,
  `aed_longitude` decimal(18,13) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`aed_no`),
  KEY `FKfqmhufdvbmc1bapb4f27hev9v` (`line`,`region`,`station`),
  CONSTRAINT `FKfqmhufdvbmc1bapb4f27hev9v` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=967 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `atm`
--

DROP TABLE IF EXISTS `atm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `atm` (
  `atm_no` bigint NOT NULL AUTO_INCREMENT,
  `atm_address` varchar(500) DEFAULT NULL,
  `atm_comment` varchar(500) DEFAULT NULL,
  `atm_latitude` decimal(18,13) DEFAULT NULL,
  `atm_longitude` decimal(18,13) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`atm_no`),
  KEY `FK1uh7y4ycg8016k1lwymrjk9au` (`line`,`region`,`station`),
  CONSTRAINT `FK1uh7y4ycg8016k1lwymrjk9au` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=612 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `board_no` bigint NOT NULL AUTO_INCREMENT,
  `board_content` varchar(5000) NOT NULL,
  `board_img` varchar(3000) DEFAULT NULL,
  `board_modify_date` datetime(6) DEFAULT NULL,
  `board_title` varchar(500) NOT NULL,
  `board_write_date` datetime(6) DEFAULT NULL,
  `member_no` bigint NOT NULL,
  PRIMARY KEY (`board_no`),
  KEY `FKbuilrr84cdwl9mlya721oe7jp` (`member_no`),
  CONSTRAINT `FKbuilrr84cdwl9mlya721oe7jp` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `board_img`
--

DROP TABLE IF EXISTS `board_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_img` (
  `img_no` bigint NOT NULL AUTO_INCREMENT,
  `img_link` varchar(3000) DEFAULT NULL,
  `board_no` bigint DEFAULT NULL,
  PRIMARY KEY (`img_no`),
  KEY `FKk7k531pxi4tipuk0qy8twoeig` (`board_no`),
  CONSTRAINT `FKk7k531pxi4tipuk0qy8twoeig` FOREIGN KEY (`board_no`) REFERENCES `board` (`board_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `car_congestion`
--

DROP TABLE IF EXISTS `car_congestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_congestion` (
  `car_congestion_no` bigint NOT NULL AUTO_INCREMENT,
  `car_congestion` varchar(255) DEFAULT NULL,
  `car_day` enum('HOLIDAY','SAT','WEEKDAY') DEFAULT NULL,
  `car_direction` int DEFAULT NULL,
  `car_index` int DEFAULT NULL,
  `car_next_station` varchar(255) DEFAULT NULL,
  `car_time` time(6) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`car_congestion_no`),
  KEY `FKanefbdj7vc9txc83o3gdx1wq6` (`line`,`region`,`station`),
  CONSTRAINT `FKanefbdj7vc9txc83o3gdx1wq6` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=6291361 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `center`
--

DROP TABLE IF EXISTS `center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `center` (
  `center_no` bigint NOT NULL AUTO_INCREMENT,
  `center_address` varchar(500) DEFAULT NULL,
  `center_comment` varchar(500) DEFAULT NULL,
  `center_hours` varchar(500) DEFAULT NULL,
  `center_latitude` decimal(18,13) DEFAULT NULL,
  `center_longitude` decimal(18,13) DEFAULT NULL,
  `center_tel` varchar(100) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`center_no`),
  KEY `FK7e2x6k9wwdylt1wbxauwellr4` (`line`,`region`,`station`),
  CONSTRAINT `FK7e2x6k9wwdylt1wbxauwellr4` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=928 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `charger`
--

DROP TABLE IF EXISTS `charger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charger` (
  `charger_no` bigint NOT NULL AUTO_INCREMENT,
  `charger_address` varchar(500) DEFAULT NULL,
  `charger_comment` varchar(500) DEFAULT NULL,
  `charger_latitude` decimal(18,13) DEFAULT NULL,
  `charger_longitude` decimal(18,13) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`charger_no`),
  KEY `FKce4m6yi2xo2knt6wjnqni6o31` (`line`,`region`,`station`),
  CONSTRAINT `FKce4m6yi2xo2knt6wjnqni6o31` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=554 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `chat_no` bigint NOT NULL AUTO_INCREMENT,
  `chat_date` datetime(6) DEFAULT NULL,
  `chat_line` varchar(100) NOT NULL,
  `chat_msg` varchar(1500) NOT NULL,
  `chat_region` varchar(100) NOT NULL,
  `chat_isdelete` bit(1) DEFAULT NULL,
  `chat_isreport` bit(1) DEFAULT NULL,
  `chat_reply` bigint DEFAULT NULL,
  PRIMARY KEY (`chat_no`),
  KEY `FKtrjvx7ydnjxji7232eqjctrty` (`chat_reply`),
  CONSTRAINT `FKtrjvx7ydnjxji7232eqjctrty` FOREIGN KEY (`chat_reply`) REFERENCES `chat` (`chat_no`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chat_member`
--

DROP TABLE IF EXISTS `chat_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_member` (
  `chat_room_no` bigint NOT NULL,
  `member_no` bigint NOT NULL,
  PRIMARY KEY (`chat_room_no`,`member_no`),
  KEY `FKm2844t722tdkydwx1act283md` (`member_no`),
  CONSTRAINT `FKcv3wmaxqr4kkcwb9bk5ucqto8` FOREIGN KEY (`chat_room_no`) REFERENCES `chat` (`chat_no`),
  CONSTRAINT `FKm2844t722tdkydwx1act283md` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dif_toilet`
--

DROP TABLE IF EXISTS `dif_toilet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dif_toilet` (
  `dif_toilet_no` bigint NOT NULL AUTO_INCREMENT,
  `dif_toilet_address` varchar(500) DEFAULT NULL,
  `dif_toilet_comment` varchar(500) DEFAULT NULL,
  `dif_toilet_exit` varchar(50) DEFAULT NULL,
  `dif_toilet_latitude` decimal(18,13) DEFAULT NULL,
  `dif_toilet_longitude` decimal(18,13) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dif_toilet_no`),
  KEY `FKl03tobdwe01kjvqh3hhxmgj9k` (`line`,`region`,`station`),
  CONSTRAINT `FKl03tobdwe01kjvqh3hhxmgj9k` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=1118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `down_congestion`
--

DROP TABLE IF EXISTS `down_congestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `down_congestion` (
  `down_congestion_no` bigint NOT NULL AUTO_INCREMENT,
  `down_congestion_0000` decimal(9,5) DEFAULT NULL,
  `down_congestion_0030` decimal(9,5) DEFAULT NULL,
  `down_congestion_0530` decimal(9,5) DEFAULT NULL,
  `down_congestion_0600` decimal(9,5) DEFAULT NULL,
  `down_congestion_0630` decimal(9,5) DEFAULT NULL,
  `down_congestion_0700` decimal(9,5) DEFAULT NULL,
  `down_congestion_0730` decimal(9,5) DEFAULT NULL,
  `down_congestion_0800` decimal(9,5) DEFAULT NULL,
  `down_congestion_0830` decimal(9,5) DEFAULT NULL,
  `down_congestion_0900` decimal(9,5) DEFAULT NULL,
  `down_congestion_0930` decimal(9,5) DEFAULT NULL,
  `down_congestion_1000` decimal(9,5) DEFAULT NULL,
  `down_congestion_1030` decimal(9,5) DEFAULT NULL,
  `down_congestion_1100` decimal(9,5) DEFAULT NULL,
  `down_congestion_1130` decimal(9,5) DEFAULT NULL,
  `down_congestion_1200` decimal(9,5) DEFAULT NULL,
  `down_congestion_1230` decimal(9,5) DEFAULT NULL,
  `down_congestion_1300` decimal(9,5) DEFAULT NULL,
  `down_congestion_1330` decimal(9,5) DEFAULT NULL,
  `down_congestion_1400` decimal(9,5) DEFAULT NULL,
  `down_congestion_1430` decimal(9,5) DEFAULT NULL,
  `down_congestion_1500` decimal(9,5) DEFAULT NULL,
  `down_congestion_1530` decimal(9,5) DEFAULT NULL,
  `down_congestion_1600` decimal(9,5) DEFAULT NULL,
  `down_congestion_1630` decimal(9,5) DEFAULT NULL,
  `down_congestion_1700` decimal(9,5) DEFAULT NULL,
  `down_congestion_1730` decimal(9,5) DEFAULT NULL,
  `down_congestion_1800` decimal(9,5) DEFAULT NULL,
  `down_congestion_1830` decimal(9,5) DEFAULT NULL,
  `down_congestion_1900` decimal(9,5) DEFAULT NULL,
  `down_congestion_1930` decimal(9,5) DEFAULT NULL,
  `down_congestion_2000` decimal(9,5) DEFAULT NULL,
  `down_congestion_2030` decimal(9,5) DEFAULT NULL,
  `down_congestion_2100` decimal(9,5) DEFAULT NULL,
  `down_congestion_2130` decimal(9,5) DEFAULT NULL,
  `down_congestion_2200` decimal(9,5) DEFAULT NULL,
  `down_congestion_2230` decimal(9,5) DEFAULT NULL,
  `down_congestion_2300` decimal(9,5) DEFAULT NULL,
  `down_congestion_2330` decimal(9,5) DEFAULT NULL,
  `down_congestion_type` enum('HOLIDAY','SAT','WEEKDAY') DEFAULT NULL,
  `down_next_station` varchar(100) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`down_congestion_no`),
  KEY `FKcjsgwa8tjp76170oc4s10nfrl` (`line`,`region`,`station`),
  CONSTRAINT `FKcjsgwa8tjp76170oc4s10nfrl` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=3250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `down_etc`
--

DROP TABLE IF EXISTS `down_etc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `down_etc` (
  `down_etc_no` bigint NOT NULL AUTO_INCREMENT,
  `down_etc_boarding_best` varchar(200) DEFAULT NULL,
  `down_etc_condition` varchar(50) NOT NULL,
  `down_etc_elevator` varchar(200) DEFAULT NULL,
  `down_etc_escal` varchar(200) DEFAULT NULL,
  `down_etc_index` varchar(50) NOT NULL,
  `down_etc_stair` varchar(200) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`down_etc_no`),
  KEY `FK523c4m8jrcbo34np13j5v001f` (`line`,`region`,`station`),
  CONSTRAINT `FK523c4m8jrcbo34np13j5v001f` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=7367 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `down_time`
--

DROP TABLE IF EXISTS `down_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `down_time` (
  `down_no` bigint NOT NULL AUTO_INCREMENT,
  `down_direction` varchar(100) DEFAULT NULL,
  `down_holiday_end` varchar(50) DEFAULT NULL,
  `down_holiday_start` varchar(50) DEFAULT NULL,
  `down_sat_end` varchar(50) DEFAULT NULL,
  `down_sat_start` varchar(50) DEFAULT NULL,
  `down_weekday_end` varchar(50) DEFAULT NULL,
  `down_weekday_start` varchar(50) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`down_no`),
  KEY `FKov6oqpfd8l2if1jjoull7o03x` (`line`,`region`,`station`),
  CONSTRAINT `FKov6oqpfd8l2if1jjoull7o03x` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=1089 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `elevator`
--

DROP TABLE IF EXISTS `elevator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `elevator` (
  `elevator_no` bigint NOT NULL AUTO_INCREMENT,
  `elevator_address` varchar(500) DEFAULT NULL,
  `elevator_comment` varchar(500) DEFAULT NULL,
  `elevator_latitude` decimal(18,13) DEFAULT NULL,
  `elevator_longitude` decimal(18,13) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`elevator_no`),
  KEY `FK4v5h48ve08c0msbts7gqlgdrb` (`line`,`region`,`station`),
  CONSTRAINT `FK4v5h48ve08c0msbts7gqlgdrb` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=1529 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `escal`
--

DROP TABLE IF EXISTS `escal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `escal` (
  `escal_no` bigint NOT NULL AUTO_INCREMENT,
  `escal_address` varchar(500) DEFAULT NULL,
  `escal_comment` varchar(500) DEFAULT NULL,
  `escal_latitude` decimal(18,13) DEFAULT NULL,
  `escal_longitude` decimal(18,13) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`escal_no`),
  KEY `FKlgq885yo372q1b30eo4bpr44w` (`line`,`region`,`station`),
  CONSTRAINT `FKlgq885yo372q1b30eo4bpr44w` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=1613 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info`
--

DROP TABLE IF EXISTS `info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `info` (
  `line` varchar(255) NOT NULL,
  `region` varchar(255) NOT NULL,
  `station` varchar(255) NOT NULL,
  `info_address` varchar(500) NOT NULL,
  `info_cid` int NOT NULL,
  `info_climate_get_off` varchar(10) DEFAULT NULL,
  `info_climate_get_on` varchar(10) DEFAULT NULL,
  `info_flooding` varchar(10) DEFAULT NULL,
  `info_holiday_end` varchar(50) DEFAULT NULL,
  `info_holiday_start` varchar(50) DEFAULT NULL,
  `info_latitude` decimal(18,13) NOT NULL,
  `info_longitude` decimal(18,13) NOT NULL,
  `info_sid` int NOT NULL,
  `info_sat_end` varchar(50) DEFAULT NULL,
  `info_sat_start` varchar(50) DEFAULT NULL,
  `info_transfer` varchar(200) DEFAULT NULL,
  `info_weekday_end` varchar(50) DEFAULT NULL,
  `info_weekday_start` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`line`,`region`,`station`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lift`
--

DROP TABLE IF EXISTS `lift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lift` (
  `lift_no` bigint NOT NULL AUTO_INCREMENT,
  `lift_address` varchar(500) DEFAULT NULL,
  `lift_end_comment` varchar(50) DEFAULT NULL,
  `lift_end_floor` varchar(50) DEFAULT NULL,
  `lift_exit` varchar(50) DEFAULT NULL,
  `lift_height` varchar(50) DEFAULT NULL,
  `lift_kg` varchar(50) DEFAULT NULL,
  `lift_latitdue` decimal(18,13) DEFAULT NULL,
  `lift_longitude` decimal(18,13) DEFAULT NULL,
  `lift_start_comment` varchar(500) DEFAULT NULL,
  `lift_start_floor` varchar(50) DEFAULT NULL,
  `lift_width` varchar(50) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`lift_no`),
  KEY `FKmtjf8rsmnlrbtjgc5vsrvy510` (`line`,`region`,`station`),
  CONSTRAINT `FKmtjf8rsmnlrbtjgc5vsrvy510` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_no` bigint NOT NULL AUTO_INCREMENT,
  `member_delete_time` datetime(6) DEFAULT NULL,
  `member_file` varchar(3000) DEFAULT NULL,
  `member_id` varchar(100) DEFAULT NULL,
  `member_isdelete` bit(1) DEFAULT NULL,
  `member_jointime` datetime(6) DEFAULT NULL,
  `member_mail` varchar(100) NOT NULL,
  `member_name` varchar(100) NOT NULL,
  `member_nick_name` varchar(100) NOT NULL,
  `member_password` varchar(2000) NOT NULL,
  `member_phone` varchar(100) NOT NULL,
  `member_random` varchar(100) NOT NULL,
  `member_refresh_token` varchar(2000) DEFAULT NULL,
  `member_report` int DEFAULT NULL,
  `member_social_id` varchar(500) DEFAULT NULL,
  `member_social_type` enum('KAKAO','NAVER','NOMAL') DEFAULT NULL,
  PRIMARY KEY (`member_no`),
  UNIQUE KEY `UK4rw879c4q7wrgi3v64cy73b17` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nursing_room`
--

DROP TABLE IF EXISTS `nursing_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nursing_room` (
  `nursing_no` bigint NOT NULL AUTO_INCREMENT,
  `nursing_address` varchar(500) DEFAULT NULL,
  `nursing_comment` varchar(500) DEFAULT NULL,
  `nursing_latitude` decimal(18,13) DEFAULT NULL,
  `nursing_longitude` decimal(18,13) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`nursing_no`),
  KEY `FKivbkfikrum3248e16bhtc2xqa` (`line`,`region`,`station`),
  CONSTRAINT `FKivbkfikrum3248e16bhtc2xqa` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=537 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `path_mark`
--

DROP TABLE IF EXISTS `path_mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `path_mark` (
  `path_no` bigint NOT NULL AUTO_INCREMENT,
  `path_color` varchar(10) NOT NULL,
  `path_end_latitude` decimal(18,13) NOT NULL,
  `path_end_longitude` decimal(18,13) NOT NULL,
  `path_end_nickname` varchar(50) NOT NULL,
  `path_start_latitude` decimal(18,13) NOT NULL,
  `path_start_longitude` decimal(18,13) NOT NULL,
  `path_start_nickname` varchar(50) NOT NULL,
  `member_no` bigint NOT NULL,
  PRIMARY KEY (`path_no`),
  UNIQUE KEY `uniquePathAndMemberNo` (`path_start_latitude`,`path_start_longitude`,`path_end_latitude`,`path_end_longitude`,`member_no`),
  KEY `FKkh957m4rwnms7s0djxnduc8hp` (`member_no`),
  CONSTRAINT `FKkh957m4rwnms7s0djxnduc8hp` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `place_mark`
--

DROP TABLE IF EXISTS `place_mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `place_mark` (
  `place_no` bigint NOT NULL AUTO_INCREMENT,
  `place_address` varchar(500) NOT NULL,
  `place_color` varchar(10) NOT NULL,
  `place_latitude` decimal(18,13) NOT NULL,
  `place_longitude` decimal(18,13) NOT NULL,
  `place_nickname` varchar(50) NOT NULL,
  `member_no` bigint NOT NULL,
  PRIMARY KEY (`place_no`),
  UNIQUE KEY `uniqueStartPointAndEndPointAndMemberNo` (`place_latitude`,`place_longitude`,`member_no`),
  KEY `FKcek9i78vwafto1ggulaxm1sqk` (`member_no`),
  CONSTRAINT `FKcek9i78vwafto1ggulaxm1sqk` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subway_mark`
--

DROP TABLE IF EXISTS `subway_mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subway_mark` (
  `line` varchar(255) NOT NULL,
  `member_no` bigint NOT NULL,
  `region` varchar(255) NOT NULL,
  `station` varchar(255) NOT NULL,
  PRIMARY KEY (`line`,`member_no`,`region`,`station`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `toilet`
--

DROP TABLE IF EXISTS `toilet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `toilet` (
  `toilet_no` bigint NOT NULL AUTO_INCREMENT,
  `toilet_address` varchar(500) DEFAULT NULL,
  `toilet_comment` varchar(500) DEFAULT NULL,
  `toilet_exit` varchar(50) DEFAULT NULL,
  `toilet_latitude` decimal(18,13) DEFAULT NULL,
  `toilet_longitude` decimal(18,13) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`toilet_no`),
  KEY `FK202q4s8o0a25o4tc09yj68kdo` (`line`,`region`,`station`),
  CONSTRAINT `FK202q4s8o0a25o4tc09yj68kdo` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=1173 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `up_congestion`
--

DROP TABLE IF EXISTS `up_congestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `up_congestion` (
  `up_congestion_no` bigint NOT NULL AUTO_INCREMENT,
  `up_congestion_0000` decimal(7,3) DEFAULT NULL,
  `up_congestion_0030` decimal(7,3) DEFAULT NULL,
  `up_congestion_0530` decimal(7,3) DEFAULT NULL,
  `up_congestion_0600` decimal(7,3) DEFAULT NULL,
  `up_congestion_0630` decimal(7,3) DEFAULT NULL,
  `up_congestion_0700` decimal(7,3) DEFAULT NULL,
  `up_congestion_0730` decimal(7,3) DEFAULT NULL,
  `up_congestion_0800` decimal(7,3) DEFAULT NULL,
  `up_congestion_0830` decimal(7,3) DEFAULT NULL,
  `up_congestion_0900` decimal(7,3) DEFAULT NULL,
  `up_congestion_0930` decimal(7,3) DEFAULT NULL,
  `up_congestion_1000` decimal(7,3) DEFAULT NULL,
  `up_congestion_1030` decimal(7,3) DEFAULT NULL,
  `up_congestion_1100` decimal(7,3) DEFAULT NULL,
  `up_congestion_1130` decimal(7,3) DEFAULT NULL,
  `up_congestion_1200` decimal(7,3) DEFAULT NULL,
  `up_congestion_1230` decimal(7,3) DEFAULT NULL,
  `up_congestion_1300` decimal(7,3) DEFAULT NULL,
  `up_congestion_1330` decimal(7,3) DEFAULT NULL,
  `up_congestion_1400` decimal(7,3) DEFAULT NULL,
  `up_congestion_1430` decimal(7,3) DEFAULT NULL,
  `up_congestion_1500` decimal(7,3) DEFAULT NULL,
  `up_congestion_1530` decimal(7,3) DEFAULT NULL,
  `up_congestion_1600` decimal(7,3) DEFAULT NULL,
  `up_congestion_1630` decimal(7,3) DEFAULT NULL,
  `up_congestion_1700` decimal(7,3) DEFAULT NULL,
  `up_congestion_1730` decimal(7,3) DEFAULT NULL,
  `up_congestion_1800` decimal(7,3) DEFAULT NULL,
  `up_congestion_1830` decimal(7,3) DEFAULT NULL,
  `up_congestion_1900` decimal(7,3) DEFAULT NULL,
  `up_congestion_1930` decimal(7,3) DEFAULT NULL,
  `up_congestion_2000` decimal(7,3) DEFAULT NULL,
  `up_congestion_2030` decimal(7,3) DEFAULT NULL,
  `up_congestion_2100` decimal(7,3) DEFAULT NULL,
  `up_congestion_2130` decimal(7,3) DEFAULT NULL,
  `up_congestion_2200` decimal(7,3) DEFAULT NULL,
  `up_congestion_2230` decimal(7,3) DEFAULT NULL,
  `up_congestion_2300` decimal(7,3) DEFAULT NULL,
  `up_congestion_2330` decimal(7,3) DEFAULT NULL,
  `up_congestion_type` enum('HOLIDAY','SAT','WEEKDAY') DEFAULT NULL,
  `up_next_station` varchar(255) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`up_congestion_no`),
  KEY `FKb6gj2y01qiccu6iko95tx248i` (`line`,`region`,`station`),
  CONSTRAINT `FKb6gj2y01qiccu6iko95tx248i` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=3250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `up_etc`
--

DROP TABLE IF EXISTS `up_etc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `up_etc` (
  `up_etc_no` bigint NOT NULL AUTO_INCREMENT,
  `up_etc_boarding_best` varchar(200) DEFAULT NULL,
  `up_etc_condition` varchar(50) NOT NULL,
  `up_etc_elevator` varchar(200) DEFAULT NULL,
  `up_etc_escal` varchar(200) DEFAULT NULL,
  `up_etc_index` varchar(50) NOT NULL,
  `up_etc_stair` varchar(200) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`up_etc_no`),
  KEY `FKqec712dwogyyv9n7lnn5ntnrj` (`line`,`region`,`station`),
  CONSTRAINT `FKqec712dwogyyv9n7lnn5ntnrj` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=7373 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `up_time`
--

DROP TABLE IF EXISTS `up_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `up_time` (
  `up_no` bigint NOT NULL AUTO_INCREMENT,
  `up_direction` varchar(100) DEFAULT NULL,
  `up_holiday_end` varchar(50) DEFAULT NULL,
  `up_holiday_start` varchar(50) DEFAULT NULL,
  `up_sat_end` varchar(50) DEFAULT NULL,
  `up_sat_start` varchar(50) DEFAULT NULL,
  `up_weekday_end` varchar(50) DEFAULT NULL,
  `up_weekday_start` varchar(50) DEFAULT NULL,
  `line` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`up_no`),
  KEY `FKmt0i7gbel3sjtajuq3rkkvyd4` (`line`,`region`,`station`),
  CONSTRAINT `FKmt0i7gbel3sjtajuq3rkkvyd4` FOREIGN KEY (`line`, `region`, `station`) REFERENCES `info` (`line`, `region`, `station`)
) ENGINE=InnoDB AUTO_INCREMENT=1085 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-05 13:40:30
