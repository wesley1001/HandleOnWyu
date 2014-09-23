/*
SQLyog 企业版 - MySQL GUI v7.14 
MySQL - 5.5.28 : Database - wuyuan
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`wuyuan` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `wuyuan`;

/*Table structure for table `concurrent_job` */

DROP TABLE IF EXISTS `concurrent_job`;

CREATE TABLE `concurrent_job` (
  `current_job_id` int(8) NOT NULL,
  `title` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `limit_time` date DEFAULT '2013-09-19',
  `salary` varchar(16) COLLATE utf8_bin DEFAULT '1000',
  `person_limit` varchar(8) COLLATE utf8_bin DEFAULT '8',
  `sex` varchar(8) COLLATE utf8_bin DEFAULT '不限',
  `work_time` varchar(16) COLLATE utf8_bin DEFAULT '8小时',
  `place` varchar(32) COLLATE utf8_bin DEFAULT '市区',
  `phone` varchar(16) COLLATE utf8_bin DEFAULT '15695462587',
  `content` varchar(256) COLLATE utf8_bin DEFAULT '有一定的表达能力',
  `contract_person` varchar(8) COLLATE utf8_bin DEFAULT '王永亮',
  PRIMARY KEY (`current_job_id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `concurrent_job` */

insert  into `concurrent_job`(`current_job_id`,`title`,`limit_time`,`salary`,`person_limit`,`sex`,`work_time`,`place`,`phone`,`content`,`contract_person`) values (1,'服务员','2013-09-19','200','8','男','8小时','市区','15695462587','有一定的表达能力','陈鹏'),(2,'家教','2013-09-19','1000元/天','8','女','8小时','市区','15695462587','有一定的表达能力','王永亮'),(3,'收银员','2013-09-19','600','8','不限','8小时','市区','15695462587','有一定的表达能力','李金龙'),(4,'还好','2013-09-19','1000','8','不限','8小时','市区','15695462587','有一定的表达能力','王永亮'),(5,'5','2013-09-19','1000','8','不限','8小时','市区','15695462587','有一定的表达能力','王永亮'),(6,'6','2013-09-19','1000','8','不限','8小时','市区','15695462587','有一定的表达能力','王永亮'),(7,'7','2013-09-19','1000','8','不限','8小时','市区','15695462587','有一定的表达能力','王永亮'),(8,'8','2013-09-19','1000','8','不限','8小时','市区','15695462587','有一定的表达能力','王永亮'),(9,'9','2013-09-19','1000','8','不限','8小时','市区','15695462587','有一定的表达能力','王永亮'),(10,'好','2013-10-24','','','sex','','','','',''),(11,'好','2013-10-24','','','sex','','','','',''),(12,'','2013-10-24','','','sex','','','','',''),(13,'李金光','2013-10-26','1000','100','sex','10','是地方','','',''),(14,'发生的发','2013-10-26','是地方','发生地方','sex','的发是地方爱上地方生的','','','',''),(15,'缺之品','2013-10-30','100','2','sex','武夷山','不知道','4654132','阿斯顿发生的发空间','缺德'),(16,'机会规划局','2013-11-03','','','sex','','','','',''),(17,'健康规划师','2013-11-03','','','sex','','','','',''),(18,'请问而','2013-11-03','','','sex','','','','',''),(19,'虽然同样让他一句话','2013-11-03','','','sex','','','','',''),(20,'虽然同样让他一句话','2013-11-03','','','sex','','','','',''),(21,'氨基酸离开大家发生','2014-02-18','','','sex','','','564564564热风对策研究','','');

/*Table structure for table `forum_comment` */

DROP TABLE IF EXISTS `forum_comment`;

CREATE TABLE `forum_comment` (
  `nid` int(8) NOT NULL,
  `cid` int(8) NOT NULL AUTO_INCREMENT,
  `comment` varchar(32) COLLATE utf8_bin DEFAULT '吃不起啊',
  PRIMARY KEY (`cid`,`nid`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `forum_comment` */

insert  into `forum_comment`(`nid`,`cid`,`comment`) values (2,1,'吃不起啊'),(1,4,'吃不起啊'),(1,3,'吃不起啊'),(1,2,'吃不起啊'),(1,1,'吃不起啊'),(2,2,'吃不起啊'),(2,3,'吃不起啊'),(2,4,'吃不起啊');

/*Table structure for table `forum_content` */

DROP TABLE IF EXISTS `forum_content`;

CREATE TABLE `forum_content` (
  `cid` int(8) NOT NULL,
  `nid` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(16) COLLATE utf8_bin DEFAULT '食堂饭菜贵',
  `time` date DEFAULT '2013-09-22',
  `content` varchar(64) COLLATE utf8_bin DEFAULT '涨价了',
  PRIMARY KEY (`cid`,`nid`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `forum_content` */

insert  into `forum_content`(`cid`,`nid`,`title`,`time`,`content`) values (1,1,'我是数计系的','2013-09-22','涨价了'),(2,1,'我是茶与食品学院','2013-09-22','食堂饭菜贵'),(3,1,'食堂饭菜贵ghte','2013-09-22','涨价了'),(4,1,'食堂饭菜贵4gdffgh','2013-09-22','涨价了'),(5,1,'食堂饭菜贵','2013-09-22','涨价了'),(6,1,'食堂饭菜贵','2013-09-22','涨价了'),(7,1,'食堂饭菜贵','2013-09-22','涨价了'),(8,1,'食堂饭菜贵1','2013-09-22','涨价了1'),(9,1,'食堂饭菜贵','2013-09-22','涨价了'),(10,1,'食堂饭菜贵','2013-09-22','涨价了'),(11,1,'食堂饭菜贵','2013-09-22','涨价了'),(12,1,'食堂饭菜贵','2013-09-22','涨价了'),(13,1,'食堂饭菜贵','2013-09-22','涨价了'),(14,1,'食堂饭菜贵','2013-09-22','涨价了'),(1,2,'我是数计系的食堂饭菜贵','2013-09-22','涨价了'),(2,2,'食堂饭菜贵','2013-09-22','食堂饭菜贵'),(3,2,'食堂饭菜贵','2013-09-22','涨价了'),(4,2,'食堂饭菜贵','2013-09-22','涨价了'),(5,2,'食堂饭菜贵','2013-09-22','涨价了'),(6,2,'食堂饭菜贵','2013-09-22','涨价了'),(1,3,'食堂饭菜贵','2013-09-22','涨价了'),(1,4,'食堂饭菜贵','2013-09-22','涨价了'),(1,5,'食堂饭菜贵','2013-09-22','涨价了'),(1,6,'食堂饭菜贵','2013-09-22','涨价了'),(1,7,'食堂饭菜贵','2013-09-22','涨价了'),(1,8,'食堂饭菜贵','2013-09-22','涨价了'),(1,9,'食堂饭菜贵','2013-09-22','涨价了'),(9,2,'食堂饭菜贵','2013-09-22','涨价了'),(10,2,'食堂饭菜贵','2013-09-22','涨价了'),(11,2,'食堂饭菜贵','2013-09-22','涨价了'),(12,2,'食堂饭菜贵','2013-09-22','涨价了'),(8,2,'食堂饭菜贵2','2013-09-22','涨价了2');

/*Table structure for table `forum_id` */

DROP TABLE IF EXISTS `forum_id`;

CREATE TABLE `forum_id` (
  `forum_id` int(8) NOT NULL,
  `forum_name` varchar(16) COLLATE utf8_bin DEFAULT '数学与计算机学院',
  PRIMARY KEY (`forum_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `forum_id` */

insert  into `forum_id`(`forum_id`,`forum_name`) values (1,'数学与计算机学院'),(2,'茶与食品学院'),(3,'人文与教师教育学院'),(4,'旅游学院'),(5,'艺术学院'),(6,'商学院'),(7,' 机电工程学院'),(8,'土木工程与建筑学院'),(9,'生态与资源工程学院'),(10,'传媒与动漫学院'),(11,'思想政治理论教学部'),(12,'继续教育学院'),(13,'体育教学部');

/*Table structure for table `home_table` */

DROP TABLE IF EXISTS `home_table`;

CREATE TABLE `home_table` (
  `first_id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `time` date DEFAULT '2013-09-18' COMMENT 'time',
  `content` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT 'content',
  `title` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'title',
  PRIMARY KEY (`first_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `home_table` */

insert  into `home_table`(`first_id`,`time`,`content`,`title`) values (1,'2013-09-18','武夷学院第一条','小夜的新闻'),(2,'2013-09-18','武夷学院第一条2','考研词汇'),(3,'2013-09-18','武夷学院第一条3','万事大吉'),(10,'2013-09-26','有人说：“李金光是狗日的”，李金亮连连点头','李金亮'),(4,'2013-09-18',NULL,NULL),(5,'2013-09-18',NULL,NULL),(9,'2013-09-18',NULL,NULL),(8,'2013-09-18',NULL,NULL),(7,'2013-09-18',NULL,NULL),(6,'2013-09-18',NULL,NULL);

/*Table structure for table `image` */

DROP TABLE IF EXISTS `image`;

CREATE TABLE `image` (
  `image` text COLLATE utf8_bin
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `image` */

insert  into `image`(`image`) values (''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),(''),('/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAA0JCgsKCA0LCgsODg0PEyAVExISEyccHhcgLikxMC4p\nLSwzOko+MzZGNywtQFdBRkxOUlNSMj5aYVpQYEpRUk//2wBDAQ4ODhMREyYVFSZPNS01T09PT09P\nT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT0//wAARCAASABIDASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDR+03E\nhddiCEgcgnpjrnt+HpWbczX2osiW7TGMAD7vP14/+uau6ZdWtraJcOjyQZHmjdnaeen6n3HpVU3q\npNI+meckRlzsYYDJtJIweDgHP0x75IQSdyHJtWZD5sg4MRyOuX/+tRTo2meNXLuCwBIY5P4k0VXK\nhGpGiprc8KKFjMBkKAYG/aPmx68nmpICV1G2ZThmiYsR1J8snn8aKKpiRx92qtdzMygkyMSSOvNF\nFFaoxP/Z\n');

/*Table structure for table `lost_property` */

DROP TABLE IF EXISTS `lost_property`;

CREATE TABLE `lost_property` (
  `flag` int(2) NOT NULL,
  `lost_id` int(16) NOT NULL,
  `title` varchar(64) COLLATE utf8_bin DEFAULT '银行卡',
  `time` varchar(16) COLLATE utf8_bin DEFAULT '2013-09-18',
  `content` varchar(256) COLLATE utf8_bin DEFAULT '失主尽快来找',
  `place` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `contract_person` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `QQ` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`flag`,`lost_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `lost_property` */

insert  into `lost_property`(`flag`,`lost_id`,`title`,`time`,`content`,`place`,`contract_person`,`phone`,`QQ`) values (0,1,'银行卡','2013-09-18','失主尽快来找','综合大楼','陈鹏','15659350289','395884397'),(0,2,'钱包','2013-09-18','失主尽快来找','双好超市','王永亮','18965381289','546239544'),(0,3,'银行卡2','2013-09-18','失主尽快来找',NULL,NULL,NULL,NULL),(1,5,'胎儿特','台湾儿童','我儿童我儿童玩儿','儿童玩儿','认为儿童','他儿童','我儿童'),(1,1,'银行卡5','2013-09-18','谁来帮我找找啊，着急啊。',NULL,NULL,NULL,NULL),(1,2,'银行卡2','2013-09-18','谁来帮我找找啊，着急啊。',NULL,NULL,NULL,NULL),(1,3,'银行卡3','2013-09-18','谁来帮我找找啊，着急啊。',NULL,NULL,NULL,NULL),(0,5,'有问题','','','','','',''),(0,6,'银行卡6','2013-09-18','失主尽快来找',NULL,NULL,NULL,NULL),(0,7,'银行卡7','2013-09-18','失主尽快来找',NULL,NULL,NULL,NULL),(1,4,'就客户健康','','','','','',''),(0,8,'银行卡8','2013-09-18','失主尽快来找',NULL,NULL,NULL,NULL),(0,4,'我丢失了陈鹏','10-12','','教室门口','项羽','',''),(0,9,'银行卡9','2013-09-18','失主尽快来找',NULL,NULL,NULL,NULL),(0,10,'银行卡10','2013-09-18','失主尽快来找',NULL,NULL,NULL,NULL),(0,11,'银行卡11','2013-09-18','失主尽快来找',NULL,NULL,NULL,NULL),(0,12,'一元钱','','','','','',''),(0,13,'两块钱','','','','','',''),(0,14,'公司地方','','','','','',''),(1,6,'好友','','','','','',''),(1,7,'你想怎么样','','','','','',''),(0,15,'爱上地方','','','','','',''),(0,16,'是的发生的','是的发生地方','是的发生地方','是的发生的','阿斯顿发生','阿斯顿发生的','是的发生'),(0,17,'公司地方广东省','是的发生地方','阿斯顿发生地方阿斯顿发生地方爱上地方爱上地方','的发生的','的发生的','阿斯顿发生','是的发生的'),(0,18,'的符合公司的','','','','','',''),(0,19,'android','爱上地方','','是地方','张学友','爱上地方',''),(1,8,'123','','','','','',''),(0,20,'456','','','','','',''),(1,9,'789','','','','','',''),(0,21,'hello','','','','','',''),(0,22,'789123','','','','','12654684',''),(0,23,'你好啊','','','','','123456789','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
