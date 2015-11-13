-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.19


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema ibs
--

CREATE DATABASE IF NOT EXISTS ibs;
USE ibs;

--
-- Definition of table `ibs_app`
--

DROP TABLE IF EXISTS `ibs_app`;
CREATE TABLE `ibs_app` (
  `app_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `app_name` varchar(128) NOT NULL COMMENT '应用名称',
  `app_version` varchar(32) NOT NULL COMMENT '应用版本',
  `app_status` int(10) NOT NULL COMMENT '应用状态',
  `app_uploadtime` datetime NOT NULL COMMENT '应用上传日期',
  `app_deletetime` datetime NOT NULL COMMENT '应用删除日期',
  `app_filepath` varchar(256) NOT NULL COMMENT '应用路径',
  `app_md5` varchar(32) NOT NULL COMMENT 'MD5校验值',
  `app_iconpath` varchar(256) NOT NULL COMMENT 'app图标路径',
  `app_description` varchar(1024) NOT NULL DEFAULT '',
  PRIMARY KEY (`app_id`),
  UNIQUE KEY `app_id_UNIQUE` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='应用表';

--
-- Dumping data for table `ibs_app`
--

/*!40000 ALTER TABLE `ibs_app` DISABLE KEYS */;
INSERT INTO `ibs_app` (`app_id`,`app_name`,`app_version`,`app_status`,`app_uploadtime`,`app_deletetime`,`app_filepath`,`app_md5`,`app_iconpath`,`app_description`) VALUES 
 (66,'PinyinIme.apk','1.00.00.00',1,'2015-06-11 11:01:00','9999-12-31 00:00:00','\\app\\PinyinIme.apk','271b8982515adc4c379499452a1d4ba5','res\\images\\appIco\\PinyinIme.png','%description%'),
 (67,'AppNmgnxReportLoss.apk','1.00.00',1,'2015-06-15 17:08:47','9999-12-31 00:00:00','\\app','a420a9a103dcac944cae5d0655141859','res\\images\\appIco\\AppNmgnxReportLoss.png','AppNmgnxReportLoss'),
 (68,'PinyinIme.apk','1.00.11',1,'2015-06-17 09:37:43','9999-12-31 00:00:00','\\app','271b8982515adc4c379499452a1d4ba5','res\\images\\appIco\\PinyinIme.png','输入法'),
 (69,'QQ_244.apk','1.1',1,'2015-06-23 15:45:22','9999-12-31 00:00:00','\\app','4b8f02837be1185f434a041b1d7fae95','res\\images\\appIco\\QQ_244.png','QQ2015'),
 (70,'PinyinIme.apk','1.00.00.00',1,'2015-06-23 15:45:33','9999-12-31 00:00:00','\\app','271b8982515adc4c379499452a1d4ba5','res\\images\\appIco\\PinyinIme.png','%description%');
/*!40000 ALTER TABLE `ibs_app` ENABLE KEYS */;


--
-- Definition of table `ibs_appstatus`
--

DROP TABLE IF EXISTS `ibs_appstatus`;
CREATE TABLE `ibs_appstatus` (
  `appstatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `appstatus_code` int(10) NOT NULL COMMENT '状态码',
  `appstatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`appstatus_id`),
  UNIQUE KEY `appstatus_id_UNIQUE` (`appstatus_id`),
  UNIQUE KEY `appstatus_code_UNIQUE` (`appstatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用状态表';

--
-- Dumping data for table `ibs_appstatus`
--

/*!40000 ALTER TABLE `ibs_appstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_appstatus` ENABLE KEYS */;


--
-- Definition of table `ibs_authority`
--

DROP TABLE IF EXISTS `ibs_authority`;
CREATE TABLE `ibs_authority` (
  `authority_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `authority_userid` int(10) unsigned NOT NULL COMMENT 'user表id',
  `authority_functionid` int(10) unsigned NOT NULL COMMENT 'function表id',
  PRIMARY KEY (`authority_id`),
  UNIQUE KEY `authority_id_UNIQUE` (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=592 DEFAULT CHARSET=utf8 COMMENT='user_function映射表';

--
-- Dumping data for table `ibs_authority`
--

/*!40000 ALTER TABLE `ibs_authority` DISABLE KEYS */;
INSERT INTO `ibs_authority` (`authority_id`,`authority_userid`,`authority_functionid`) VALUES 
 (1,1,1),
 (2,1,2),
 (3,1,3),
 (4,1,4),
 (5,1,5),
 (6,1,6),
 (7,1,7),
 (8,1,8),
 (9,1,9),
 (10,1,10),
 (11,1,11),
 (13,1,13),
 (34,12,1),
 (35,12,2),
 (36,12,3),
 (37,12,4),
 (38,12,5),
 (39,12,6),
 (40,12,7),
 (41,12,8),
 (42,12,9),
 (43,12,10),
 (44,12,11),
 (56,7,1),
 (57,7,2),
 (58,7,3),
 (59,7,4),
 (60,7,5),
 (61,7,6),
 (62,7,7),
 (63,7,8),
 (64,7,9),
 (65,7,10),
 (66,7,11),
 (67,10,1),
 (68,10,2),
 (69,10,3),
 (70,10,4),
 (71,10,5),
 (72,10,6),
 (73,10,7),
 (74,10,8),
 (75,10,9),
 (76,10,10),
 (77,10,11),
 (78,86,1),
 (79,86,2),
 (80,86,3),
 (81,86,4),
 (82,86,5),
 (83,86,6),
 (84,86,7),
 (85,86,8),
 (86,86,9),
 (87,86,10),
 (88,86,11),
 (89,88,1),
 (90,88,2),
 (91,88,3),
 (92,88,4),
 (93,88,5),
 (94,88,6),
 (95,88,7),
 (96,88,8),
 (97,88,9),
 (98,88,10),
 (99,88,11),
 (100,91,1),
 (101,91,2),
 (102,91,3),
 (103,91,4),
 (104,91,5),
 (105,91,6),
 (106,91,7),
 (107,91,8),
 (108,91,9),
 (109,91,10),
 (110,91,11),
 (111,95,1),
 (112,95,2),
 (113,95,3),
 (114,95,4),
 (115,95,5),
 (116,95,6),
 (117,95,7),
 (118,95,8),
 (119,95,9),
 (120,95,10),
 (121,95,11),
 (122,102,1),
 (123,102,2),
 (124,102,3),
 (125,102,4),
 (126,102,5),
 (127,102,6),
 (128,102,7),
 (129,102,8),
 (130,102,9),
 (131,102,10),
 (132,102,11),
 (133,104,1),
 (134,104,2),
 (135,104,3),
 (136,104,4),
 (137,104,5),
 (138,104,6),
 (139,104,7),
 (140,104,8),
 (141,104,9),
 (142,104,10),
 (143,104,11),
 (144,2,1),
 (145,2,2),
 (146,2,3),
 (147,2,4),
 (148,2,5),
 (149,2,6),
 (150,2,7),
 (151,2,8),
 (152,2,9),
 (153,2,10),
 (154,2,11),
 (156,2,13),
 (157,2,14),
 (158,2,15),
 (166,106,5),
 (167,106,6),
 (183,105,1),
 (184,105,2),
 (185,105,3),
 (186,105,4),
 (187,105,5),
 (188,105,6),
 (189,105,7),
 (190,105,8),
 (191,105,9),
 (192,105,10),
 (193,105,11),
 (194,2,12),
 (199,107,1),
 (200,107,2),
 (201,107,3),
 (202,107,12),
 (203,107,4),
 (204,108,1),
 (205,108,2),
 (206,108,3),
 (207,108,12),
 (208,108,4),
 (209,110,1),
 (210,110,2),
 (211,110,3),
 (212,110,12),
 (213,110,4),
 (214,112,1),
 (215,112,2),
 (216,112,3),
 (217,112,12),
 (218,112,4),
 (219,112,7),
 (220,112,8),
 (221,112,9),
 (222,112,10),
 (223,112,11),
 (224,112,5),
 (225,112,6),
 (277,117,1),
 (278,117,2),
 (279,117,3),
 (280,117,12),
 (281,117,4),
 (297,115,1),
 (298,115,2),
 (299,115,3),
 (300,115,12),
 (301,115,4),
 (302,115,7),
 (303,115,8),
 (304,115,9),
 (305,115,10),
 (306,115,11),
 (307,115,5),
 (308,115,6),
 (309,123,1),
 (310,123,2),
 (311,123,3),
 (312,123,12),
 (313,123,4),
 (314,123,7),
 (315,123,8),
 (316,123,9),
 (317,123,10),
 (318,123,11),
 (319,123,5),
 (320,123,6),
 (321,122,1),
 (322,122,2),
 (323,122,3),
 (324,122,12),
 (325,122,4),
 (326,129,1),
 (327,129,2),
 (328,129,3),
 (329,129,12),
 (330,129,4),
 (331,114,1),
 (332,114,2),
 (333,114,3),
 (334,114,12),
 (335,114,7),
 (336,114,8),
 (337,114,9),
 (338,114,10),
 (339,114,11),
 (340,114,5),
 (341,114,6),
 (371,136,1),
 (372,136,2),
 (373,136,3),
 (374,136,12),
 (375,136,7),
 (376,136,8),
 (377,136,9),
 (378,136,10),
 (379,136,11),
 (380,136,5),
 (381,136,6),
 (382,137,1),
 (383,137,2),
 (384,137,3),
 (385,137,12),
 (386,137,7),
 (387,137,8),
 (388,137,9),
 (389,137,10),
 (390,137,11),
 (391,137,5),
 (392,137,6),
 (393,138,1),
 (394,138,2),
 (395,138,3),
 (396,138,12),
 (397,138,7),
 (398,138,8),
 (399,138,9),
 (400,138,10),
 (401,138,11),
 (402,138,5),
 (403,138,6),
 (404,132,1),
 (405,132,2),
 (406,132,3),
 (407,132,12),
 (408,132,7),
 (409,132,8),
 (410,132,9),
 (411,132,10),
 (412,132,11),
 (413,132,5),
 (414,132,6),
 (477,141,5),
 (478,141,6),
 (479,140,1),
 (480,140,2),
 (481,140,3),
 (482,140,12),
 (487,214,1),
 (488,214,2),
 (489,214,3),
 (490,214,12),
 (491,214,7),
 (492,214,8),
 (493,214,9),
 (494,214,10),
 (495,214,11),
 (496,156,1),
 (497,156,2),
 (498,156,3),
 (499,156,12),
 (500,156,7),
 (501,156,8),
 (502,156,9),
 (503,156,10),
 (504,156,11),
 (505,161,1),
 (506,161,2),
 (507,161,3),
 (508,161,12),
 (518,217,1),
 (519,217,2),
 (520,217,3),
 (521,217,12),
 (522,162,1),
 (523,162,2),
 (524,162,3),
 (525,162,12),
 (526,218,1),
 (527,218,2),
 (528,218,3),
 (529,218,12),
 (530,178,1),
 (531,178,2),
 (532,178,3),
 (533,178,12),
 (538,219,1),
 (539,219,2),
 (540,219,3),
 (541,219,12),
 (542,220,1),
 (543,220,2),
 (544,220,3),
 (545,220,12),
 (546,220,7),
 (547,220,8),
 (548,220,9),
 (549,220,10),
 (550,220,11),
 (551,221,1),
 (552,221,2),
 (553,221,3),
 (554,221,12),
 (555,207,1),
 (556,207,2),
 (557,207,3),
 (558,207,12),
 (559,208,7),
 (560,208,8),
 (561,208,9),
 (562,208,10),
 (563,208,11),
 (579,197,1),
 (580,197,2),
 (581,197,3),
 (582,197,12),
 (583,512,1),
 (584,512,2),
 (585,512,3),
 (586,512,12),
 (587,512,7),
 (588,512,8),
 (589,512,9),
 (590,512,10),
 (591,512,11);
/*!40000 ALTER TABLE `ibs_authority` ENABLE KEYS */;


--
-- Definition of table `ibs_business`
--

DROP TABLE IF EXISTS `ibs_business`;
CREATE TABLE `ibs_business` (
  `business_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `business_code` varchar(32) NOT NULL COMMENT '交易码',
  `business_name` varchar(256) NOT NULL COMMENT '交易名称',
  `business_isparent` int(1) NOT NULL COMMENT '是否父节点',
  `business_parentid` int(10) NOT NULL COMMENT '父节点id',
  `business_sortid` int(10) NOT NULL COMMENT '排序id',
  `business_feature` varchar(512) NOT NULL COMMENT '交易描述',
  `business_app` int(10) NOT NULL COMMENT '业务对应的app_id',
  PRIMARY KEY (`business_id`),
  UNIQUE KEY `business_id_UNIQUE` (`business_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='交易表';

--
-- Dumping data for table `ibs_business`
--

/*!40000 ALTER TABLE `ibs_business` DISABLE KEYS */;
INSERT INTO `ibs_business` (`business_id`,`business_code`,`business_name`,`business_isparent`,`business_parentid`,`business_sortid`,`business_feature`,`business_app`) VALUES 
 (1,'-1','个人业务',1,0,-1,'个人业务',0),
 (2,'-1','企业业务',1,0,-1,'企业业务',0),
 (3,'2015','QQ',0,1,-1,'QQ',69),
 (4,'1','个人开户',0,1,-1,'个人开户',70);
/*!40000 ALTER TABLE `ibs_business` ENABLE KEYS */;


--
-- Definition of table `ibs_businessrecord`
--

DROP TABLE IF EXISTS `ibs_businessrecord`;
CREATE TABLE `ibs_businessrecord` (
  `businessrecord_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `businessrecord_time` datetime NOT NULL COMMENT '业务时间',
  `businessrecord_outlets` int(10) unsigned NOT NULL COMMENT '业务网点_关联outlets表',
  `businessrecord_business` int(10) unsigned NOT NULL COMMENT '业务信息_关联business表',
  `businessrecord_device` int(10) unsigned NOT NULL COMMENT '办理业务的设备_关联device表',
  `businessrecord_data` varchar(512) NOT NULL COMMENT '业务数据',
  `businessrecord_code` varchar(20) NOT NULL,
  PRIMARY KEY (`businessrecord_id`,`businessrecord_time`),
  KEY `INDEX_BUSINESSRECORD_TIME` (`businessrecord_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (TO_DAYS(businessrecord_time))
(PARTITION part_201501 VALUES LESS THAN (735995) ENGINE = InnoDB,
 PARTITION part_201502 VALUES LESS THAN (736023) ENGINE = InnoDB,
 PARTITION part_201503 VALUES LESS THAN (736054) ENGINE = InnoDB,
 PARTITION part_201504 VALUES LESS THAN (736084) ENGINE = InnoDB,
 PARTITION part_201505 VALUES LESS THAN (736115) ENGINE = InnoDB,
 PARTITION part_201506 VALUES LESS THAN (736145) ENGINE = InnoDB,
 PARTITION part_201507 VALUES LESS THAN (736176) ENGINE = InnoDB) */;

--
-- Dumping data for table `ibs_businessrecord`
--

/*!40000 ALTER TABLE `ibs_businessrecord` DISABLE KEYS */;
INSERT INTO `ibs_businessrecord` (`businessrecord_id`,`businessrecord_time`,`businessrecord_outlets`,`businessrecord_business`,`businessrecord_device`,`businessrecord_data`,`businessrecord_code`) VALUES 
 (1,'2015-01-01 00:00:00',1,1,1,'123','123');
/*!40000 ALTER TABLE `ibs_businessrecord` ENABLE KEYS */;


--
-- Definition of table `ibs_businessstatistics`
--

DROP TABLE IF EXISTS `ibs_businessstatistics`;
CREATE TABLE `ibs_businessstatistics` (
  `businessstatistics_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `businessstatistics_date` date NOT NULL COMMENT '业务日期',
  `businessstatistics_outlets` int(10) unsigned NOT NULL COMMENT '业务网点_关联outlets表',
  `businessstatistics_business` int(10) unsigned NOT NULL COMMENT '业务信息_关联business表',
  `businessstatistics_device` int(10) unsigned NOT NULL COMMENT '办理业务的设备_关联device表',
  `businessstatistics_count` int(32) unsigned NOT NULL COMMENT '业务数量',
  PRIMARY KEY (`businessstatistics_id`,`businessstatistics_date`),
  KEY `INDEX_BUSINESSSTATISTICS_DATE` (`businessstatistics_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=660004 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (YEAR(businessstatistics_date))
(PARTITION part_2014 VALUES LESS THAN (2015) ENGINE = InnoDB,
 PARTITION part_2015 VALUES LESS THAN (2016) ENGINE = InnoDB) */;

--
-- Dumping data for table `ibs_businessstatistics`
--

/*!40000 ALTER TABLE `ibs_businessstatistics` DISABLE KEYS */;
INSERT INTO `ibs_businessstatistics` (`businessstatistics_id`,`businessstatistics_date`,`businessstatistics_outlets`,`businessstatistics_business`,`businessstatistics_device`,`businessstatistics_count`) VALUES 
 (1,'2014-05-16',1,3,1,40),
 (2,'2014-05-28',2,4,1,22),
 (3,'2014-05-17',3,3,1,1),
 (4,'2014-05-18',3,4,1,23),
 (5,'2014-05-19',1,3,2,12),
 (660003,'2014-05-10',1,3,1,40),
 (1,'2015-04-04',1,1,1,12),
 (2,'2015-04-05',2,1,1,13),
 (3,'2015-04-06',2,1,1,15),
 (4,'2015-04-07',1,1,1,12),
 (5,'2015-04-08',1,1,1,11),
 (6,'2015-05-10',1,1,1,13),
 (7,'2015-05-10',3,1,2,20),
 (8,'2015-05-10',2,2,2,22),
 (9,'2015-05-11',3,2,1,15),
 (10,'2015-05-11',3,2,1,12),
 (11,'2015-05-11',3,2,1,10),
 (12,'2015-05-11',3,2,1,1),
 (13,'2015-05-12',2,1,2,12),
 (14,'2015-05-13',3,1,1,11),
 (15,'2015-05-14',1,1,1,13),
 (16,'2015-05-15',3,2,1,113),
 (17,'2015-05-16',3,2,1,1),
 (18,'2015-05-17',3,2,2,1),
 (19,'2015-05-18',1,70,1,1),
 (20,'2015-04-19',1,70,1,22),
 (21,'2015-05-19',1,70,1,50),
 (22,'2015-05-20',1,70,1,88),
 (23,'2015-05-21',1,70,1,23),
 (24,'2015-05-27',1,70,1,79),
 (25,'2015-06-01',1,70,1,14),
 (26,'2015-06-10',1,70,1,77),
 (27,'2015-06-09',1,70,1,1),
 (28,'2015-06-11',1,70,1,1),
 (29,'2015-05-13',1,70,1,1);
/*!40000 ALTER TABLE `ibs_businessstatistics` ENABLE KEYS */;


--
-- Definition of table `ibs_counterapp`
--

DROP TABLE IF EXISTS `ibs_counterapp`;
CREATE TABLE `ibs_counterapp` (
  `counterapp_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `counterapp_name` varchar(128) NOT NULL COMMENT '应用名称',
  `counterapp_version` varchar(32) NOT NULL COMMENT '应用版本',
  `counterapp_lastversion` varchar(32) NOT NULL COMMENT '上一固件版本',
  `counterapp_type` int(10) NOT NULL COMMENT '应用类型_关联counterapptype表',
  `counterapp_status` int(10) NOT NULL COMMENT '应用状态',
  `counterapp_uploadtime` datetime NOT NULL COMMENT '应用上传日期',
  `counterapp_deletetime` datetime NOT NULL COMMENT '应用删除日期',
  `counterapp_filepath` varchar(256) NOT NULL COMMENT '应用路径',
  `counterapp_md5` varchar(32) NOT NULL COMMENT 'MD5校验值',
  PRIMARY KEY (`counterapp_id`),
  UNIQUE KEY `counterapp_id_UNIQUE` (`counterapp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='柜员应用表';

--
-- Dumping data for table `ibs_counterapp`
--

/*!40000 ALTER TABLE `ibs_counterapp` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_counterapp` ENABLE KEYS */;


--
-- Definition of table `ibs_counterappstatus`
--

DROP TABLE IF EXISTS `ibs_counterappstatus`;
CREATE TABLE `ibs_counterappstatus` (
  `counterappstatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `counterappstatus_code` int(10) NOT NULL COMMENT '状态码',
  `counterappstatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`counterappstatus_id`),
  UNIQUE KEY `counterappstatus_id_UNIQUE` (`counterappstatus_id`),
  UNIQUE KEY `counterappstatus_code_UNIQUE` (`counterappstatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='柜员应用状态表';

--
-- Dumping data for table `ibs_counterappstatus`
--

/*!40000 ALTER TABLE `ibs_counterappstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_counterappstatus` ENABLE KEYS */;


--
-- Definition of table `ibs_counterapptype`
--

DROP TABLE IF EXISTS `ibs_counterapptype`;
CREATE TABLE `ibs_counterapptype` (
  `counterapptype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `counterapptype_code` int(10) NOT NULL COMMENT '类型码',
  `counterapptype_name` varchar(64) NOT NULL COMMENT '类型名',
  `counterapptype_version` varchar(32) NOT NULL COMMENT '类型版本',
  `counterapptype_updatetime` datetime NOT NULL COMMENT '更新日期',
  `counterapptype_description` varchar(1024) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`counterapptype_id`),
  UNIQUE KEY `counterapptype_id_UNIQUE` (`counterapptype_id`),
  UNIQUE KEY `counterapptype_code_UNIQUE` (`counterapptype_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='柜员应用类型表';

--
-- Dumping data for table `ibs_counterapptype`
--

/*!40000 ALTER TABLE `ibs_counterapptype` DISABLE KEYS */;
INSERT INTO `ibs_counterapptype` (`counterapptype_id`,`counterapptype_code`,`counterapptype_name`,`counterapptype_version`,`counterapptype_updatetime`,`counterapptype_description`) VALUES 
 (1,1,'Telnet升级','1','2015-07-15 00:00:00','telnet升级');
/*!40000 ALTER TABLE `ibs_counterapptype` ENABLE KEYS */;


--
-- Definition of table `ibs_customer`
--

DROP TABLE IF EXISTS `ibs_customer`;
CREATE TABLE `ibs_customer` (
  `customer_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '客户id',
  `customer_name` varchar(32) NOT NULL DEFAULT '' COMMENT '客户姓名',
  `customer_idnum` varchar(32) NOT NULL COMMENT '客户证件号',
  `customer_account` varchar(256) NOT NULL COMMENT '客户账户',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ibs_customer`
--

/*!40000 ALTER TABLE `ibs_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_customer` ENABLE KEYS */;


--
-- Definition of table `ibs_device`
--

DROP TABLE IF EXISTS `ibs_device`;
CREATE TABLE `ibs_device` (
  `device_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `device_serial` varchar(64) NOT NULL COMMENT '设备序列号',
  `device_type` int(10) unsigned NOT NULL COMMENT '设备类型_关联devicetype表',
  `device_outlets` int(10) unsigned NOT NULL COMMENT '设备所在网点_关联outlets表',
  `device_status` int(10) unsigned NOT NULL COMMENT '设备状态',
  `device_ip` varchar(32) NOT NULL COMMENT '设备ip地址',
  `device_firmware` varchar(128) NOT NULL COMMENT '设备固件_关联firmware表，可有多个firmware',
  `device_app` varchar(128) NOT NULL COMMENT '设备应用_关联app表，可有多个app',
  PRIMARY KEY (`device_id`),
  UNIQUE KEY `device_id_UNIQUE` (`device_id`),
  UNIQUE KEY `device_serial_UNIQUE` (`device_serial`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='设备表';

--
-- Dumping data for table `ibs_device`
--

/*!40000 ALTER TABLE `ibs_device` DISABLE KEYS */;
INSERT INTO `ibs_device` (`device_id`,`device_serial`,`device_type`,`device_outlets`,`device_status`,`device_ip`,`device_firmware`,`device_app`) VALUES 
 (1,'001',1,1,1,'1','1,3_52,2_55','66,69,4'),
 (2,'002',1,1,1,'1','1,3_52,2_55','66,69,4'),
 (3,'003',2,2,1,'1','1,3_52,2_55','66,69,4');
/*!40000 ALTER TABLE `ibs_device` ENABLE KEYS */;


--
-- Definition of table `ibs_devicestatus`
--

DROP TABLE IF EXISTS `ibs_devicestatus`;
CREATE TABLE `ibs_devicestatus` (
  `devicestatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `devicestatus_code` int(10) NOT NULL COMMENT '状态码',
  `devicestatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`devicestatus_id`),
  UNIQUE KEY `devicestatus_id_UNIQUE` (`devicestatus_id`),
  UNIQUE KEY `devicestatus_code_UNIQUE` (`devicestatus_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='设备状态表';

--
-- Dumping data for table `ibs_devicestatus`
--

/*!40000 ALTER TABLE `ibs_devicestatus` DISABLE KEYS */;
INSERT INTO `ibs_devicestatus` (`devicestatus_id`,`devicestatus_code`,`devicestatus_description`) VALUES 
 (1,1,'在线'),
 (2,2,'下线');
/*!40000 ALTER TABLE `ibs_devicestatus` ENABLE KEYS */;


--
-- Definition of table `ibs_devicetask`
--

DROP TABLE IF EXISTS `ibs_devicetask`;
CREATE TABLE `ibs_devicetask` (
  `devicetask_id` int(10) unsigned NOT NULL,
  `devicetask_devicetype` int(10) NOT NULL COMMENT '设备类型_关联devicetype表',
  `devicetask_outlets` int(10) NOT NULL COMMENT '网点_关联outlets表',
  `devicetask_task` int(10) NOT NULL COMMENT '任务_关联task表',
  PRIMARY KEY (`devicetask_id`),
  UNIQUE KEY `appupdatemap_id_UNIQUE` (`devicetask_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备任务索引表';

--
-- Dumping data for table `ibs_devicetask`
--

/*!40000 ALTER TABLE `ibs_devicetask` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_devicetask` ENABLE KEYS */;


--
-- Definition of table `ibs_devicetype`
--

DROP TABLE IF EXISTS `ibs_devicetype`;
CREATE TABLE `ibs_devicetype` (
  `devicetype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `devicetype_code` varchar(32) NOT NULL COMMENT '类型代号',
  `devicetype_description` varchar(128) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`devicetype_id`),
  UNIQUE KEY `devicetype_id_UNIQUE` (`devicetype_id`),
  UNIQUE KEY `devicetype_code_UNIQUE` (`devicetype_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ibs_devicetype`
--

/*!40000 ALTER TABLE `ibs_devicetype` DISABLE KEYS */;
INSERT INTO `ibs_devicetype` (`devicetype_id`,`devicetype_code`,`devicetype_description`) VALUES 
 (1,'E10-1','E10-1'),
 (2,'E10-2','E10-2'),
 (3,'E10-3','E10-3');
/*!40000 ALTER TABLE `ibs_devicetype` ENABLE KEYS */;


--
-- Definition of table `ibs_firmware`
--

DROP TABLE IF EXISTS `ibs_firmware`;
CREATE TABLE `ibs_firmware` (
  `firmware_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firmware_name` varchar(128) NOT NULL COMMENT '固件名称',
  `firmware_version` varchar(32) NOT NULL COMMENT '固件版本',
  `firmware_lastversion` varchar(32) NOT NULL COMMENT '上一固件版本',
  `firmware_type` int(10) NOT NULL COMMENT '固件类型_关联firmwaretype表',
  `firmware_status` int(10) NOT NULL COMMENT '固件状态',
  `firmware_uploadtime` datetime NOT NULL COMMENT '固件上传时间',
  `firmware_deletetime` datetime NOT NULL COMMENT '固件删除时间',
  `firmware_filepath` varchar(256) NOT NULL COMMENT '固件文件路径',
  `firmware_md5` varchar(32) NOT NULL COMMENT 'MD5校验值',
  PRIMARY KEY (`firmware_id`),
  UNIQUE KEY `firmware_id_UNIQUE` (`firmware_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='固件表';

--
-- Dumping data for table `ibs_firmware`
--

/*!40000 ALTER TABLE `ibs_firmware` DISABLE KEYS */;
INSERT INTO `ibs_firmware` (`firmware_id`,`firmware_name`,`firmware_version`,`firmware_lastversion`,`firmware_type`,`firmware_status`,`firmware_uploadtime`,`firmware_deletetime`,`firmware_filepath`,`firmware_md5`) VALUES 
 (1,'打印机固件','1.00.00.00','1.00.00.00',1,0,'2015-06-01 09:55:04','2015-06-01 09:55:08',' ',' '),
 (2,'drawecard.jpg','1.2','1.1',1,0,'2015-06-04 16:48:40','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\drawecard.jpg','6fde42eb411e0e0e04aa62719635a98e'),
 (3,'FeiQ.1060559168.exe','%version%','%lastVersion%',9,0,'2015-06-04 16:49:13','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\FeiQ.1060559168.exe','bfea3e87b66b4164badd346de8172a36'),
 (4,'drawecard.jpg','1.2','1.1',9,0,'2015-06-04 16:51:07','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\drawecard.jpg','6fde42eb411e0e0e04aa62719635a98e'),
 (5,'drawecard.jpg','1.2','1.1',10,0,'2015-06-04 17:02:43','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\drawecard.jpg','6fde42eb411e0e0e04aa62719635a98e'),
 (6,'drawecard.jpg','1.2','1.1',10,0,'2015-06-04 17:02:43','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\drawecard.jpg','6fde42eb411e0e0e04aa62719635a98e'),
 (7,'1.dat','1.0','1.0',11,0,'2015-06-05 09:27:42','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (8,'2.dat','1.0','%lastVersion%',12,0,'2015-06-05 09:31:28','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\2.dat','0efd8f86d7099b39b2fae38cd704b4c5'),
 (9,'temp.dat','1.1','1.0',13,0,'2015-06-05 09:34:14','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\temp.dat','6a89cd1c17db0b36a34c917b1fa37d8b'),
 (10,'1.dat','1.0','%lastVersion%',14,0,'2015-06-05 09:43:03','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (11,'1.dat','1.1','1.0',14,0,'2015-06-05 09:44:13','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (12,'1.dat','1.0','%lastVersion%',14,0,'2015-06-05 09:46:45','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (13,'1.dat','1.1','1.0',14,0,'2015-06-05 09:46:58','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (14,'1.dat','1.0','%lastVersion%',15,0,'2015-06-05 09:51:13','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (15,'2.dat','1.0','%lastVersion%',16,0,'2015-06-05 09:51:27','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\2.dat','0efd8f86d7099b39b2fae38cd704b4c5'),
 (16,'2.dat','1.0','%lastVersion%',16,0,'2015-06-05 09:51:27','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\2.dat','0efd8f86d7099b39b2fae38cd704b4c5'),
 (17,'FeiQ.1060559168.exe','%version%','%lastVersion%',17,0,'2015-06-10 10:46:36','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\ftp\\firmware\\FeiQ.1060559168.exe','bfea3e87b66b4164badd346de8172a36'),
 (18,'1.dat','1.0','%lastVersion%',18,0,'2015-06-10 10:46:43','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\ftp\\firmware\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (19,'1.dat','1.1','1.0',18,0,'2015-06-10 10:46:51','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\ftp\\firmware\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (20,'drawecard.jpg','1.2','1.1',1,0,'2015-06-10 15:57:11','9999-12-31 00:00:00','E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\ftp\\firmware\\drawecard.jpg','6fde42eb411e0e0e04aa62719635a98e'),
 (21,'drawecard.jpg','1.2','1.1',19,0,'2015-06-11 13:52:57','9999-12-31 00:00:00','\\firmware\\drawecard.jpg','6fde42eb411e0e0e04aa62719635a98e'),
 (22,'FeiQ.1060559168.exe','%version%','%lastVersion%',20,0,'2015-06-11 13:53:56','9999-12-31 00:00:00','\\firmware\\FeiQ.1060559168.exe','bfea3e87b66b4164badd346de8172a36'),
 (23,'drawecard.jpg','1.2','1.1',21,0,'2015-06-11 13:57:22','9999-12-31 00:00:00','\\firmware\\drawecard.jpg','6fde42eb411e0e0e04aa62719635a98e'),
 (24,'FeiQ.1060559168.exe','%version%','%lastVersion%',22,0,'2015-06-11 13:58:19','9999-12-31 00:00:00','\\firmware\\FeiQ.1060559168.exe','bfea3e87b66b4164badd346de8172a36'),
 (25,'drawecard.jpg','1.2','1.1',23,0,'2015-06-11 14:03:20','9999-12-31 00:00:00','\\firmware\\drawecard.jpg','6fde42eb411e0e0e04aa62719635a98e'),
 (26,'FeiQ.1060559168.exe','%version%','%lastVersion%',24,0,'2015-06-11 14:09:11','9999-12-31 00:00:00','\\firmware\\FeiQ.1060559168.exe','bfea3e87b66b4164badd346de8172a36'),
 (27,'1.dat','1.0','%lastVersion%',25,0,'2015-06-11 14:09:19','9999-12-31 00:00:00','\\firmware\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (28,'1.dat','1.1','1.0',25,0,'2015-06-11 14:09:42','9999-12-31 00:00:00','\\firmware\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (29,'2.dat','1.0','%lastVersion%',26,0,'2015-06-11 14:09:49','9999-12-31 00:00:00','\\firmware\\2.dat','0efd8f86d7099b39b2fae38cd704b4c5'),
 (30,'1.dat','1.0','%lastVersion%',27,0,'2015-06-11 14:09:56','9999-12-31 00:00:00','\\firmware\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (31,'drawecard.jpg','1.2','1.1',28,0,'2015-06-11 14:33:41','9999-12-31 00:00:00','\\firmware\\drawecard.jpg','6fde42eb411e0e0e04aa62719635a98e'),
 (32,'2.dat','1.0','%lastVersion%',29,0,'2015-06-11 14:33:55','9999-12-31 00:00:00','\\firmware\\2.dat','0efd8f86d7099b39b2fae38cd704b4c5'),
 (33,'1.dat','1.1','1.0',30,0,'2015-06-11 17:58:38','9999-12-31 00:00:00','\\firmware\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (34,'1.dat','1.0','%lastVersion%',28,0,'2015-06-11 18:03:33','9999-12-31 00:00:00','\\firmware\\1.dat','0d4baf3e5b5ace5b3b5f35d14429e06e'),
 (35,'2.dat','1.0','%lastVersion%',31,0,'2015-06-11 18:03:43','9999-12-31 00:00:00','\\firmware\\2.dat','0efd8f86d7099b39b2fae38cd704b4c5'),
 (36,'1.dat','1.00.00','%lastVersion%',32,0,'2015-06-12 10:45:28','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (37,'1.dat','1.00.00','%lastVersion%',33,0,'2015-06-12 10:53:52','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (38,'FeiQ.1060559168.exe','%version%','%lastVersion%',34,0,'2015-06-12 14:21:49','9999-12-31 00:00:00','\\firmware','BFEA3E87B66B4164BADD346DE8172A36'),
 (39,'drawecard.jpg','1.2','1.1',35,0,'2015-06-12 14:21:55','9999-12-31 00:00:00','\\firmware','6FDE42EB411E0E0E04AA62719635A98E'),
 (40,'1.dat','1.0','%lastVersion%',36,0,'2015-06-12 14:22:04','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (41,'1.dat','1.1','1.0',36,0,'2015-06-12 14:22:11','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (42,'1.dat','1.0','%lastVersion%',35,0,'2015-06-12 14:22:18','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (43,'1.dat','1.0','%lastVersion%',36,0,'2015-06-12 14:22:53','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (44,'drawecard.jpg','1.2','1.1',37,0,'2015-06-15 14:37:10','9999-12-31 00:00:00','\\firmware','6FDE42EB411E0E0E04AA62719635A98E'),
 (45,'FeiQ.1060559168.exe','%version%','%lastVersion%',38,0,'2015-06-15 14:37:22','9999-12-31 00:00:00','\\firmware','BFEA3E87B66B4164BADD346DE8172A36'),
 (46,'1.dat','1.0','%lastVersion%',39,0,'2015-06-15 14:37:42','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (47,'1.dat','1.1','1.0',39,0,'2015-06-15 14:37:48','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (48,'1.dat','1.0','%lastVersion%',37,0,'2015-06-15 14:37:54','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (49,'drawecard.jpg','1.2','1.1',40,0,'2015-06-15 14:59:31','9999-12-31 00:00:00','\\firmware','6FDE42EB411E0E0E04AA62719635A98E'),
 (50,'1.dat','1.0','%lastVersion%',41,0,'2015-06-15 14:59:43','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (51,'1.dat','1.1','1.0',41,0,'2015-06-15 14:59:54','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (52,'1.dat','1.0','%lastVersion%',41,1,'2015-06-15 15:05:14','9999-12-31 00:00:00','\\firmware','0D4BAF3E5B5ACE5B3B5F35D14429E06E'),
 (53,'11.dat','1.2','1.1',42,0,'2015-06-15 15:06:45','9999-12-31 00:00:00','\\firmware','76767472AA08E9E07B8F1E51A5F97B61'),
 (54,'11.dat','1.2','%lastVersion%',43,0,'2015-06-15 15:07:56','9999-12-31 00:00:00','\\firmware','76767472AA08E9E07B8F1E51A5F97B61'),
 (55,'13.dat','1.3','%lastVersion%',43,1,'2015-06-15 15:11:17','9999-12-31 00:00:00','\\firmware','4E2AD3798139E2A8D9B7C1E616043746');
/*!40000 ALTER TABLE `ibs_firmware` ENABLE KEYS */;


--
-- Definition of table `ibs_firmwarestatus`
--

DROP TABLE IF EXISTS `ibs_firmwarestatus`;
CREATE TABLE `ibs_firmwarestatus` (
  `firmwarestatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firmwarestatus_code` int(10) NOT NULL COMMENT '状态码',
  `firmwarestatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`firmwarestatus_id`),
  UNIQUE KEY `firmwarestatus_id_UNIQUE` (`firmwarestatus_id`),
  UNIQUE KEY `firmwarestatus_code_UNIQUE` (`firmwarestatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件状态表';

--
-- Dumping data for table `ibs_firmwarestatus`
--

/*!40000 ALTER TABLE `ibs_firmwarestatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_firmwarestatus` ENABLE KEYS */;


--
-- Definition of table `ibs_firmwaretype`
--

DROP TABLE IF EXISTS `ibs_firmwaretype`;
CREATE TABLE `ibs_firmwaretype` (
  `firmwaretype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firmwaretype_code` int(10) NOT NULL COMMENT '类型码',
  `firmwaretype_name` varchar(64) NOT NULL COMMENT '类型名',
  `firmwaretype_version` varchar(32) NOT NULL COMMENT '类型版本',
  `firmwaretype_updatetime` datetime NOT NULL COMMENT '更新日期',
  `firmwaretype_description` varchar(1024) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_id_UNIQUE` (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_code_UNIQUE` (`firmwaretype_code`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='固件类型表';

--
-- Dumping data for table `ibs_firmwaretype`
--

/*!40000 ALTER TABLE `ibs_firmwaretype` DISABLE KEYS */;
INSERT INTO `ibs_firmwaretype` (`firmwaretype_id`,`firmwaretype_code`,`firmwaretype_name`,`firmwaretype_version`,`firmwaretype_updatetime`,`firmwaretype_description`) VALUES 
 (41,3,'PRINTER','1.0','2015-06-15 15:05:14','PRINTER%PRINTER%PRINTER'),
 (43,2,'PRINTER','1.3','2015-06-15 15:11:17','打印机更新打印机更新打印机更新%打印机更新打印机更新打印机更新');
/*!40000 ALTER TABLE `ibs_firmwaretype` ENABLE KEYS */;


--
-- Definition of table `ibs_function`
--

DROP TABLE IF EXISTS `ibs_function`;
CREATE TABLE `ibs_function` (
  `function_id` int(10) unsigned NOT NULL,
  `function_name` varchar(64) NOT NULL COMMENT '功能名称',
  `function_isparent` int(1) NOT NULL COMMENT '是否父节点',
  `function_parentid` int(10) NOT NULL COMMENT '父节点id',
  `function_sortid` int(10) NOT NULL COMMENT '排序id',
  `function_url` varchar(256) NOT NULL COMMENT '功能url',
  `function_icon` varchar(256) NOT NULL COMMENT '功能icon路径',
  `function_target` varchar(256) NOT NULL COMMENT '功能target',
  PRIMARY KEY (`function_id`),
  UNIQUE KEY `function_id_UNIQUE` (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单功能表';

--
-- Dumping data for table `ibs_function`
--

/*!40000 ALTER TABLE `ibs_function` DISABLE KEYS */;
INSERT INTO `ibs_function` (`function_id`,`function_name`,`function_isparent`,`function_parentid`,`function_sortid`,`function_url`,`function_icon`,`function_target`) VALUES 
 (1,'业务查询',1,0,1,'','res/css/zTreeStyle/img/diy/iconBusinessOC.png','ui-layout-box'),
 (2,'历史业务统计',1,1,2,'','res/css/zTreeStyle/img/diy/iconHistoryOC.png','ui-layout-box'),
 (3,'按时间查询',0,2,4,'business/statistics/querybytime/main/toUI.action','res/css/zTreeStyle/img/diy/iconTime.png','ui-layout-box'),
 (5,'后台管理',1,0,10,'','res/css/zTreeStyle/img/diy/iconConsole.png','ui-layout-box'),
 (6,'用户管理',0,5,11,'system/user/main/mainUI.action','res/css/zTreeStyle/img/diy/iconUser.png','ui-layout-box'),
 (7,'设备管理',1,0,5,'','res/css/zTreeStyle/img/diy/iconDeviceOC.png','ui-layout-box'),
 (8,'远程操作',1,7,6,'','res/css/zTreeStyle/img/diy/iconRemoteOC.png','ui-layout-box'),
 (9,'固件升级',0,8,8,'device/remotecontrol/firmwareupdate/main/toUI.action','res/css/zTreeStyle/img/diy/iconRom.png','ui-layout-box'),
 (10,'应用发布',0,8,9,'device/remotecontrol/appupdate/main/toUI.action','res/css/zTreeStyle/img/diy/iconApp.png','ui-layout-box'),
 (11,'设备查询',0,7,7,'device/devicequery/main/toUI.action','res/css/zTreeStyle/img/diy/iconQuery.png','ui-layout-box'),
 (12,'按交易类型查询',0,2,12,'business/statistics/querybydeal/main/toUI.action','res/css/zTreeStyle/img/diy/iconBusiness.png','ui-layout-box'),
 (13,'业务管理',0,5,13,' system/business/main/toUI.action',' res/css/zTreeStyle/img/diy/iconType.png','ui-layout-box'),
 (14,'网点管理',0,5,14,'system/outlets/main/toUI.action',' res/css/zTreeStyle/img/diy/iconNet.png','ui-layout-box'),
 (15,'柜员应用升级',0,7,15,'device/remotecontrol/counterappupdate/main/toUI.action',' res/css/zTreeStyle/img/diy/iconTellerApps.png','ui-layout-box');
/*!40000 ALTER TABLE `ibs_function` ENABLE KEYS */;


--
-- Definition of table `ibs_outlets`
--

DROP TABLE IF EXISTS `ibs_outlets`;
CREATE TABLE `ibs_outlets` (
  `outlets_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `outlets_code` varchar(32) NOT NULL COMMENT '网点编号',
  `outlets_name` varchar(128) NOT NULL COMMENT '网点名称',
  `outlets_isparent` int(1) NOT NULL COMMENT '是否父节点',
  `outlets_parentid` int(10) NOT NULL COMMENT '父节点id',
  `outlets_sortid` int(10) NOT NULL COMMENT '排序id',
  `outlets_description` varchar(256) NOT NULL COMMENT '网点描述',
  `outlets_app` varchar(128) NOT NULL COMMENT '网点的app',
  `outlets_recordcode` varchar(16) NOT NULL,
  PRIMARY KEY (`outlets_id`),
  UNIQUE KEY `outlets_id_UNIQUE` (`outlets_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='网点表';

--
-- Dumping data for table `ibs_outlets`
--

/*!40000 ALTER TABLE `ibs_outlets` DISABLE KEYS */;
INSERT INTO `ibs_outlets` (`outlets_id`,`outlets_code`,`outlets_name`,`outlets_isparent`,`outlets_parentid`,`outlets_sortid`,`outlets_description`,`outlets_app`,`outlets_recordcode`) VALUES 
 (1,'1001','仓山网点',0,0,1,'','66,69,4','000000'),
 (2,'1002','鼓楼网点',0,0,2,'','66,69,4','000000'),
 (3,'000002','马尾网点',0,0,3,' ','66,69,4','000000');
/*!40000 ALTER TABLE `ibs_outlets` ENABLE KEYS */;


--
-- Definition of table `ibs_serverinfo`
--

DROP TABLE IF EXISTS `ibs_serverinfo`;
CREATE TABLE `ibs_serverinfo` (
  `serverinfo_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serverinfo_firststartuptime` datetime NOT NULL COMMENT '第一次启动时间',
  `serverinfo_laststartuptime` datetime NOT NULL COMMENT '最后一次启动时间',
  `serverinfo_laststatisticstime` datetime NOT NULL COMMENT '最后一次统计数据时间',
  PRIMARY KEY (`serverinfo_id`),
  UNIQUE KEY `serverinfo_id_UNIQUE` (`serverinfo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='服务器信息表';

--
-- Dumping data for table `ibs_serverinfo`
--

/*!40000 ALTER TABLE `ibs_serverinfo` DISABLE KEYS */;
INSERT INTO `ibs_serverinfo` (`serverinfo_id`,`serverinfo_firststartuptime`,`serverinfo_laststartuptime`,`serverinfo_laststatisticstime`) VALUES 
 (1,'2015-03-20 17:44:54','2015-07-15 17:10:37','2015-06-22 23:59:59');
/*!40000 ALTER TABLE `ibs_serverinfo` ENABLE KEYS */;


--
-- Definition of table `ibs_task`
--

DROP TABLE IF EXISTS `ibs_task`;
CREATE TABLE `ibs_task` (
  `task_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `task_name` varchar(128) NOT NULL COMMENT '任务名',
  `task_type` int(10) NOT NULL COMMENT '任务类型',
  `task_status` int(10) NOT NULL COMMENT '任务状态',
  `task_releasetime` datetime NOT NULL COMMENT '任务发布时间',
  `task_executetime` datetime NOT NULL COMMENT '任务执行时间',
  `task_overtime` datetime NOT NULL COMMENT '任务结束时间',
  `task_attachment` int(10) NOT NULL COMMENT '任务附件',
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `task_id_UNIQUE` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

--
-- Dumping data for table `ibs_task`
--

/*!40000 ALTER TABLE `ibs_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_task` ENABLE KEYS */;


--
-- Definition of table `ibs_taskstatus`
--

DROP TABLE IF EXISTS `ibs_taskstatus`;
CREATE TABLE `ibs_taskstatus` (
  `taskstatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `taskstatus_code` int(10) NOT NULL COMMENT '状态码',
  `taskstatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`taskstatus_id`),
  UNIQUE KEY `taskstatus_id_UNIQUE` (`taskstatus_id`),
  UNIQUE KEY `taskstatus_code_UNIQUE` (`taskstatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务状态表';

--
-- Dumping data for table `ibs_taskstatus`
--

/*!40000 ALTER TABLE `ibs_taskstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_taskstatus` ENABLE KEYS */;


--
-- Definition of table `ibs_tasktype`
--

DROP TABLE IF EXISTS `ibs_tasktype`;
CREATE TABLE `ibs_tasktype` (
  `tasktype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tasktype_code` int(10) NOT NULL COMMENT '类型码',
  `tasktype_description` varchar(128) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`tasktype_id`),
  UNIQUE KEY `tasktype_id_UNIQUE` (`tasktype_id`),
  UNIQUE KEY `tasktype_code_UNIQUE` (`tasktype_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务类型表';

--
-- Dumping data for table `ibs_tasktype`
--

/*!40000 ALTER TABLE `ibs_tasktype` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_tasktype` ENABLE KEYS */;


--
-- Definition of table `ibs_user`
--

DROP TABLE IF EXISTS `ibs_user`;
CREATE TABLE `ibs_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_uid` varchar(64) NOT NULL COMMENT '用户账号',
  `user_name` varchar(256) NOT NULL COMMENT '用户昵称',
  `user_password` varchar(256) NOT NULL COMMENT '用户密码',
  `user_isadmin` int(1) NOT NULL COMMENT '是否管理员',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_uid_UNIQUE` (`user_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=529 DEFAULT CHARSET=utf8 COMMENT='用户表';

--
-- Dumping data for table `ibs_user`
--

/*!40000 ALTER TABLE `ibs_user` DISABLE KEYS */;
INSERT INTO `ibs_user` (`user_id`,`user_uid`,`user_name`,`user_password`,`user_isadmin`) VALUES 
 (2,'admin','陈玲','Y2VudGVybQ==',1),
 (115,'ceshi1','ceshi1','Nzg2MTg1',0),
 (120,'ggg','fff','ZmZmZg==',0),
 (145,'nany','nany','MjA0MTY4',0),
 (146,'hathway','11','ODg4ODg4',0),
 (147,'test','test123456','NzIxMDg4',0),
 (149,'11','11','MTE=',0),
 (150,'1111','1','MQ==',0),
 (151,'111','1','MQ==',0),
 (152,'1111111','1','MQ==',0),
 (153,'22','2','dnZ2Mg==',0),
 (161,'f','f','ODg4ODg4',0),
 (162,'j','j','ag==',0),
 (167,'g','g','Zw==',0),
 (168,'hf','h','aA==',0),
 (169,'hgf','d','ZA==',0),
 (170,'c','c','Yw==',0),
 (181,'s','s','cw==',0),
 (188,'qqq','q','MDg0MTUy',0),
 (207,'dddd','d','ZA==',0),
 (208,'dddddd','d','ZA==',0),
 (209,'dddddddd','dd','ZA==',0),
 (210,'www','w','dw==',0),
 (211,'dddvv','d','ZA==',0),
 (212,'vc','vc','dmM=',0),
 (213,'xzx','zz','eg==',0),
 (215,'d','d','ZA==',0),
 (216,'ssdd','ssdd123','MTIz',0),
 (511,'admin2','admin2','Y2VudGVybQ==',1),
 (512,'test3','test3','MTIzNDU2',0),
 (517,'CHENLING','CHENLING','Q0hFTkxJTkc=',0),
 (522,'夏天的太阳真是受不了','受不了','MTIzNDU2',0),
 (523,'1111111111111111','111111111111111111','MTExMTExMTExMTExMTExMTExMTE=',0),
 (525,'8888888888888888888888888888888888888888','88888888888888888888','ODg4ODg4ODg4ODg4ODg4ODg4ODg=',0),
 (526,'夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏下','夏夏夏夏夏夏夏夏夏夏','MjAyMDIwMjAyMDIwMDAwMDAwMDA=',0),
 (527,'kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk','kkkkkkkkkkkkkkkkkkkk','a2tra2tra2tra2tra2tra2tra2s=',0),
 (528,'de124g到底v','11111wwww222无法','MTExMTExMTExMTExMTExMTExMTE=',0);
/*!40000 ALTER TABLE `ibs_user` ENABLE KEYS */;


--
-- Definition of procedure `PROC_BUSINESSRECORD_MONTH_PARTITION`
--

DROP PROCEDURE IF EXISTS `PROC_BUSINESSRECORD_MONTH_PARTITION`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PROC_BUSINESSRECORD_MONTH_PARTITION`()
BEGIN
	#Routine body goes here...
	DECLARE currentTime datetime DEFAULT CURDATE();
	DECLARE partName VARCHAR(12);
	DECLARE i_part_name VARCHAR(12);
	DECLARE i_flag INT DEFAULT 0;

	DECLARE cur_partition CURSOR for select partition_name part from information_schema.PARTITIONS 
		where TABLE_SCHEMA = schema() and TABLE_NAME='ibs_businessrecord';
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET i_part_name = NULL;
  
	IF MONTH(currentTime) < 9 THEN
		SET partName=CONCAT('part_',YEAR(currentTime),'0',MONTH(currentTime));
	ELSE
		SET partName=CONCAT('part_',YEAR(currentTime),MONTH(currentTime));
	END IF;

	OPEN cur_partition;

	FETCH cur_partition into i_part_name;
	WHILE (i_part_name IS NOT NULL)
		DO
		IF i_part_name = partName THEN
			SET i_flag = 1;
		END IF;
		FETCH cur_partition into i_part_name;
	END WHILE;

	CLOSE cur_partition;
	
	IF i_flag = 0 THEN
		IF MONTH(currentTime) + 1 < 10 THEN
			SET currentTime=CONCAT(YEAR(currentTime),'-0',MONTH(currentTime) + 1,'-01');
		ELSE
			SET currentTime=CONCAT(YEAR(currentTime),'-',MONTH(currentTime) + 1,'-01');
		END IF;

		SET @v_add_s = CONCAT('ALTER TABLE ibs_businessrecord ADD PARTITION  (PARTITION ',partName,' VALUES LESS THAN (TO_DAYS(\'',currentTime,'\')))');
		prepare stmt from @v_add_s;
		execute stmt;
		deallocate prepare stmt;
	END IF;

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `PROC_BUSINESSSTATISTICS_YEAR_PARTITION`
--

DROP PROCEDURE IF EXISTS `PROC_BUSINESSSTATISTICS_YEAR_PARTITION`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PROC_BUSINESSSTATISTICS_YEAR_PARTITION`()
BEGIN
	#Routine body goes here...
	DECLARE currentTime date DEFAULT CURDATE();
	DECLARE partName VARCHAR(12);
	DECLARE i_part_name VARCHAR(12);
	DECLARE i_flag INT DEFAULT 0;
	DECLARE currentYear INT DEFAULT 0;

	DECLARE cur_partition CURSOR for select partition_name part from information_schema.PARTITIONS 
		where TABLE_SCHEMA = schema() and TABLE_NAME='ibs_businessstatistics';
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET i_part_name = NULL;

	SET partName=CONCAT('part_',YEAR(currentTime));

	OPEN cur_partition;

	FETCH cur_partition into i_part_name;
	WHILE (i_part_name IS NOT NULL)
		DO
		IF i_part_name = partName THEN
			SET i_flag = 1;
		END IF;
		FETCH cur_partition into i_part_name;
	END WHILE;

	CLOSE cur_partition;
	IF i_flag = 0 THEN
		SET currentYear=YEAR(currentTime) + 1;
		SET @v_add_s = CONCAT('ALTER TABLE ibs_businessstatistics ADD PARTITION  (PARTITION ',partName,' VALUES LESS THAN (',currentYear,'))');
		prepare stmt from @v_add_s;
		execute stmt;
		deallocate prepare stmt;
	END IF;

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
