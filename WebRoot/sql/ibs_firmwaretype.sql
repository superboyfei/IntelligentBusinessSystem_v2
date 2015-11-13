/*
Navicat MySQL Data Transfer

Source Server         : 3306
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-08-07 11:17:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ibs_firmwaretype`
-- ----------------------------
DROP TABLE IF EXISTS `ibs_firmwaretype`;
CREATE TABLE `ibs_firmwaretype` (
  `firmwaretype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firmwaretype_code` varchar(64) NOT NULL COMMENT '类型码',
  `firmwaretype_name` varchar(64) NOT NULL COMMENT '类型名',
  `firmwaretype_version` varchar(32) NOT NULL COMMENT '类型版本',
  `firmwaretype_updatetime` datetime NOT NULL COMMENT '更新日期',
  `firmwaretype_description` varchar(1024) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_id_UNIQUE` (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_code_UNIQUE` (`firmwaretype_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件类型表';


