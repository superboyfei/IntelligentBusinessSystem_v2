/*
Navicat MySQL Data Transfer

Source Server         : mysql_ibs
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-08-31 17:42:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ibs_downloadfile
-- ----------------------------
DROP TABLE IF EXISTS `ibs_downloadfile`;
CREATE TABLE `ibs_downloadfile` (
  `downloadfile_id` int(10) NOT NULL AUTO_INCREMENT,
  `downloadfile_name` varchar(128) NOT NULL COMMENT '下载文件名',
  `downloadfile_size` int(10) NOT NULL COMMENT '下载文件大小',
  `downloadfile_uploadtime` datetime NOT NULL COMMENT '文件上传日期',
  `downloadfile_description` varchar(512) NOT NULL COMMENT '文件描述',
  `downloadfile_filepath` varchar(256) NOT NULL COMMENT '文件路径',
  `downloadfile_md5` varchar(32) NOT NULL COMMENT 'MD5校验值',
  `downloadfile_iconpath` varchar(256) NOT NULL COMMENT '文件图标路径',
  PRIMARY KEY (`downloadfile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ibs_downloadfile
-- ----------------------------
