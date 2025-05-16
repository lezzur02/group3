/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 8.0.41 : Database - information_systemdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`information_systemdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `information_systemdb`;

/*Table structure for table `accdetails` */

DROP TABLE IF EXISTS `accdetails`;

CREATE TABLE `accdetails` (
  `accId` int NOT NULL AUTO_INCREMENT,
  `accUsername` varchar(255) DEFAULT NULL,
  `accPassword` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`accId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `accdetails` */

insert  into `accdetails`(`accId`,`accUsername`,`accPassword`) values 
(1,'Ruzzel','17'),
(2,'Gerome','Lagutan');

/*Table structure for table `eventdetails` */

DROP TABLE IF EXISTS `eventdetails`;

CREATE TABLE `eventdetails` (
  `eventid` int NOT NULL AUTO_INCREMENT,
  `eventtitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `eventdate` date DEFAULT NULL,
  PRIMARY KEY (`eventid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `eventdetails` */

insert  into `eventdetails`(`eventid`,`eventtitle`,`description`,`eventdate`) values 
(1,'Family Day','A day dedicated to faith, fun, and\n fellowship with the family.','2025-04-09'),
(2,'Youth Gathering','Fellowship with the young people \ngrow bond and escpecially their faith','2025-04-27');

/*Table structure for table `inactivememberdetails` */

DROP TABLE IF EXISTS `inactivememberdetails`;

CREATE TABLE `inactivememberdetails` (
  `inactiveid` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `lifeverse` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'inactive',
  PRIMARY KEY (`inactiveid`),
  CONSTRAINT `inactive` FOREIGN KEY (`inactiveid`) REFERENCES `memberdetails` (`memberid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `inactivememberdetails` */

insert  into `inactivememberdetails`(`inactiveid`,`fullname`,`age`,`birthday`,`gender`,`contact`,`address`,`lifeverse`,`status`) values 
(2,'Clark E. Ayalin',18,'2006-12-15','Male','09478429667','Bagong Silang','John 3:16','inactive'),
(7,'John Denver Genson',18,'2007-03-05','Male','09628351783','tawid sapa 2','Luke 15;11 ','inactive');

/*Table structure for table `memberdetails` */

DROP TABLE IF EXISTS `memberdetails`;

CREATE TABLE `memberdetails` (
  `memberid` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `lifeverse` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'active',
  PRIMARY KEY (`memberid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `memberdetails` */

insert  into `memberdetails`(`memberid`,`fullname`,`age`,`birthday`,`gender`,`contact`,`address`,`lifeverse`,`status`) values 
(1,'Ruzzel V. Dumalag',17,'2006-09-02','Male','09763466970','#76 Virginia Street Gulod Novaliches Quezon City','Philippians 4:13','active'),
(2,'Clark E. Ayalin',18,'2006-12-15','Male','09478429667','Bagong Silang','John 3:16','inactive'),
(3,'John S. Doe',19,'2008-04-16','Male','09784697901','Sauyo','Jeremiah 29:11','active'),
(4,'Kim Mingyu',27,'1997-04-06','Male','09867562756','Anyang South Korea','Isaiah 41:10','active'),
(5,'Ruth V. Dumalag',30,'1994-03-15','Female','09398811725','Nitang','Romans 8:28','active'),
(6,'Jedrick E. Gacelo',17,'2006-03-08','Male','09398811723','Caloocan','Romans 16:19','active'),
(7,'John Denver Genson',18,'2007-03-05','Male','09628351783','tawid sapa 2','Luke 15;11 ','inactive'),
(8,'Crispino E. Jonathan ',18,'2006-04-06','Male','09408811726','Bagumbong','Romans 14:17','active');

/*Table structure for table `newcomerdetails` */

DROP TABLE IF EXISTS `newcomerdetails`;

CREATE TABLE `newcomerdetails` (
  `newcomerid` int NOT NULL AUTO_INCREMENT,
  `newcomerfullname` varchar(255) DEFAULT NULL,
  `newcomerage` int DEFAULT NULL,
  `newcomerbirthday` date DEFAULT NULL,
  `newcomerfbacc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`newcomerid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `newcomerdetails` */

insert  into `newcomerdetails`(`newcomerid`,`newcomerfullname`,`newcomerage`,`newcomerbirthday`,`newcomerfbacc`) values 
(1,'Crispino',18,'2006-04-08','Jonathan Crispino');

/*Table structure for table `reportdetails` */

DROP TABLE IF EXISTS `reportdetails`;

CREATE TABLE `reportdetails` (
  `reportid` int NOT NULL AUTO_INCREMENT,
  `reportname` varchar(255) DEFAULT NULL,
  `reportdescription` varchar(255) DEFAULT NULL,
  `reporttype` varchar(255) DEFAULT NULL,
  `reportdate` date DEFAULT NULL,
  `reportamount` int DEFAULT NULL,
  PRIMARY KEY (`reportid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `reportdetails` */

insert  into `reportdetails`(`reportid`,`reportname`,`reportdescription`,`reporttype`,`reportdate`,`reportamount`) values 
(1,'Ruzzel Dumalag','Food Expenses','Refunds','2025-04-05',500);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
