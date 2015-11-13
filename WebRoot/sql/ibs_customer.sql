/*
Navicat MySQL Data Transfer

Source Server         : 3301
Source Server Version : 50617
Source Host           : localhost:3301
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-06-29 17:11:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ibs_customer`
-- ----------------------------
DROP TABLE IF EXISTS `ibs_customer`;
CREATE TABLE `ibs_customer` (
  `customer_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '客户id',
  `customer_name` varchar(32) NOT NULL DEFAULT '' COMMENT '客户姓名',
  `customer_idnum` varchar(32) NOT NULL COMMENT '客户证件号',
  `customer_account` varchar(256) NOT NULL COMMENT '客户账户',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_id_UNIQUE` (`customer_id`) USING BTREE,
  UNIQUE KEY `customer_idnum_UNIQUE` (`customer_idnum`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

