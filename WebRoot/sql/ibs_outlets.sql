/*
Navicat MySQL Data Transfer

Source Server         : 3301
Source Server Version : 50617
Source Host           : 127.0.0.1:3301
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-06-17 16:37:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ibs_outlets`
-- ----------------------------
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

-- ----------------------------
-- Records of ibs_outlets
-- ----------------------------
INSERT INTO `ibs_outlets` VALUES ('1', '1001', '仓山网点', '0', '0', '1', '', '', '000015');
INSERT INTO `ibs_outlets` VALUES ('2', '1002', '鼓楼网点', '0', '0', '2', '', '', '000015');
INSERT INTO `ibs_outlets` VALUES ('3', '000002', '马尾网点', '0', '0', '3', ' ', '', '000018');
