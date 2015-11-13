/*
Navicat MySQL Data Transfer

Source Server         : centerm
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2015-07-17 16:57:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ibs_businessrecord
-- ----------------------------
DROP TABLE IF EXISTS `ibs_businessrecord`;
CREATE TABLE `ibs_businessrecord` (
  `businessrecord_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `businessrecord_time` datetime NOT NULL COMMENT '业务时间',
  `businessrecord_outlets` int(10) unsigned NOT NULL COMMENT '业务网点_关联outlets表',
  `businessrecord_business` int(10) unsigned NOT NULL COMMENT '业务信息_关联business表',
  `businessrecord_device` int(10) unsigned NOT NULL COMMENT '办理业务的设备_关联device表',
  `businessrecord_data` varchar(8096) NOT NULL COMMENT '业务数据',
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

-- ----------------------------
-- Records of ibs_businessrecord
-- ----------------------------
INSERT INTO `ibs_businessrecord` VALUES ('1', '2015-01-01 00:00:00', '1', '1', '1', '123', '123');
