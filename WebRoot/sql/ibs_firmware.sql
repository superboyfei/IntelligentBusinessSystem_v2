/*
Navicat MySQL Data Transfer

Source Server         : 3306
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-08-07 09:56:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ibs_firmware`
-- ----------------------------
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
  `firmware_ismust` int(2) NOT NULL COMMENT '是否必须升级0：否，1：是',
  PRIMARY KEY (`firmware_id`),
  UNIQUE KEY `firmware_id_UNIQUE` (`firmware_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件表';

