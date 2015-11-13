/*
Navicat MySQL Data Transfer

Source Server         : 3306
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-07-17 16:01:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ibs_counterconfig`
-- ----------------------------
DROP TABLE IF EXISTS `ibs_counterconfig`;
CREATE TABLE `ibs_counterconfig` (
  `config_id` int(10) NOT NULL AUTO_INCREMENT,
  `config_name` varchar(32) NOT NULL COMMENT '配置名称',
  `config_outlets` int(10) NOT NULL COMMENT '所属的网点',
  `config_filepath` varchar(56) NOT NULL COMMENT '对私的配置文件',
  `config_ip` varchar(32) NOT NULL COMMENT '柜台ip',
  `config_uploadtime` datetime NOT NULL COMMENT '配置文件上传时间',
  `config_updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  `config_ispublic` tinyint(1) NOT NULL COMMENT '是否对公：0：对私，1：对公',
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `privateconfig_id_UNIQUE` (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='柜台配置文件信息表';
