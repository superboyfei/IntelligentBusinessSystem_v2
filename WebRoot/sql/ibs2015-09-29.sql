/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2015-09-29 11:37:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ibs_app
-- ----------------------------
DROP TABLE IF EXISTS `ibs_app`;
CREATE TABLE `ibs_app` (
  `app_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `app_name` varchar(128) NOT NULL COMMENT '应用名称',
  `app_packagename` varchar(128) NOT NULL COMMENT '应用包名',
  `app_version` varchar(32) NOT NULL COMMENT '应用版本名称',
  `app_versioncode` int(10) NOT NULL COMMENT '应用版本号',
  `app_status` int(10) NOT NULL COMMENT '应用状态',
  `app_uploadtime` datetime NOT NULL COMMENT '应用上传日期',
  `app_deletetime` datetime NOT NULL COMMENT '应用删除日期',
  `app_filepath` varchar(256) NOT NULL COMMENT '应用路径',
  `app_md5` varchar(32) NOT NULL COMMENT 'MD5校验值',
  `app_iconpath` varchar(256) NOT NULL COMMENT 'app图标路径',
  `app_description` varchar(1024) NOT NULL DEFAULT '',
  PRIMARY KEY (`app_id`),
  UNIQUE KEY `app_id_UNIQUE` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='应用表';

-- ----------------------------
-- Records of ibs_app
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_appstatus
-- ----------------------------
DROP TABLE IF EXISTS `ibs_appstatus`;
CREATE TABLE `ibs_appstatus` (
  `appstatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `appstatus_code` int(10) NOT NULL COMMENT '状态码',
  `appstatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`appstatus_id`),
  UNIQUE KEY `appstatus_id_UNIQUE` (`appstatus_id`),
  UNIQUE KEY `appstatus_code_UNIQUE` (`appstatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用状态表';

-- ----------------------------
-- Records of ibs_appstatus
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_authority
-- ----------------------------
DROP TABLE IF EXISTS `ibs_authority`;
CREATE TABLE `ibs_authority` (
  `authority_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `authority_userid` int(10) unsigned NOT NULL COMMENT 'user表id',
  `authority_functionid` int(10) unsigned NOT NULL COMMENT 'function表id',
  PRIMARY KEY (`authority_id`),
  UNIQUE KEY `authority_id_UNIQUE` (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='user_function映射表';

-- ----------------------------
-- Records of ibs_authority
-- ----------------------------
INSERT INTO `ibs_authority` VALUES ('1', '1', '1');
INSERT INTO `ibs_authority` VALUES ('2', '1', '2');
INSERT INTO `ibs_authority` VALUES ('3', '1', '3');
INSERT INTO `ibs_authority` VALUES ('4', '1', '4');
INSERT INTO `ibs_authority` VALUES ('5', '1', '5');
INSERT INTO `ibs_authority` VALUES ('6', '1', '6');
INSERT INTO `ibs_authority` VALUES ('7', '1', '7');
INSERT INTO `ibs_authority` VALUES ('8', '1', '8');
INSERT INTO `ibs_authority` VALUES ('9', '1', '9');
INSERT INTO `ibs_authority` VALUES ('10', '1', '10');
INSERT INTO `ibs_authority` VALUES ('11', '1', '11');
INSERT INTO `ibs_authority` VALUES ('12', '1', '12');
INSERT INTO `ibs_authority` VALUES ('13', '1', '13');
INSERT INTO `ibs_authority` VALUES ('14', '1', '14');
INSERT INTO `ibs_authority` VALUES ('15', '1', '15');
INSERT INTO `ibs_authority` VALUES ('16', '1', '16');
INSERT INTO `ibs_authority` VALUES ('17', '1', '17');

-- ----------------------------
-- Table structure for ibs_business
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='交易表';

-- ----------------------------
-- Records of ibs_business
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (TO_DAYS(businessrecord_time))
(PARTITION part_201501 VALUES LESS THAN (735995) ENGINE = InnoDB,
 PARTITION part_201502 VALUES LESS THAN (736023) ENGINE = InnoDB,
 PARTITION part_201503 VALUES LESS THAN (736054) ENGINE = InnoDB,
 PARTITION part_201504 VALUES LESS THAN (736084) ENGINE = InnoDB,
 PARTITION part_201505 VALUES LESS THAN (736115) ENGINE = InnoDB,
 PARTITION part_201506 VALUES LESS THAN (736145) ENGINE = InnoDB,
 PARTITION part_201507 VALUES LESS THAN (736176) ENGINE = InnoDB,
 PARTITION part_201508 VALUES LESS THAN (736207) ENGINE = InnoDB,
 PARTITION part_201509 VALUES LESS THAN (736237) ENGINE = InnoDB) */;

-- ----------------------------
-- Records of ibs_businessrecord
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_businessstatistics
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=660039 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (YEAR(businessstatistics_date))
(PARTITION part_2014 VALUES LESS THAN (2015) ENGINE = InnoDB,
 PARTITION part_2015 VALUES LESS THAN (2016) ENGINE = InnoDB) */;

-- ----------------------------
-- Records of ibs_businessstatistics
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_counterapp
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='柜员应用表';

-- ----------------------------
-- Records of ibs_counterapp
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_counterappstatus
-- ----------------------------
DROP TABLE IF EXISTS `ibs_counterappstatus`;
CREATE TABLE `ibs_counterappstatus` (
  `counterappstatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `counterappstatus_code` int(10) NOT NULL COMMENT '状态码',
  `counterappstatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`counterappstatus_id`),
  UNIQUE KEY `counterappstatus_id_UNIQUE` (`counterappstatus_id`),
  UNIQUE KEY `counterappstatus_code_UNIQUE` (`counterappstatus_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='柜员应用状态表';

-- ----------------------------
-- Records of ibs_counterappstatus
-- ----------------------------
INSERT INTO `ibs_counterappstatus` VALUES ('1', '1', '可用');
INSERT INTO `ibs_counterappstatus` VALUES ('2', '0', '不可用');

-- ----------------------------
-- Table structure for ibs_counterapptype
-- ----------------------------
DROP TABLE IF EXISTS `ibs_counterapptype`;
CREATE TABLE `ibs_counterapptype` (
  `counterapptype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `counterapptype_code` varchar(64) NOT NULL COMMENT '类型码',
  `counterapptype_name` varchar(64) NOT NULL COMMENT '类型名',
  `counterapptype_version` varchar(32) NOT NULL COMMENT '类型版本',
  `counterapptype_updatetime` datetime NOT NULL COMMENT '更新日期',
  `counterapptype_description` varchar(5120) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`counterapptype_id`),
  UNIQUE KEY `counterapptype_id_UNIQUE` (`counterapptype_id`),
  UNIQUE KEY `counterapptype_code_UNIQUE` (`counterapptype_code`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='柜员应用类型表';

-- ----------------------------
-- Records of ibs_counterapptype
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_counterconfig
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='柜台配置文件信息表';

-- ----------------------------
-- Records of ibs_counterconfig
-- ----------------------------
INSERT INTO `ibs_counterconfig` VALUES ('16', 'terminal.xml', '1', '\\private\\000001\\192.168.129.168', '192.168.129.168', '2015-09-01 09:31:26', '2015-09-01 09:31:50', '0');
INSERT INTO `ibs_counterconfig` VALUES ('17', 'outlet.xml', '1', '\\public\\000001', '', '2015-09-01 09:32:11', '2015-09-01 09:34:09', '1');
INSERT INTO `ibs_counterconfig` VALUES ('18', 'terminal.xml', '2', '\\private\\000002\\192.168.129.168', '192.168.129.168', '2015-09-01 09:35:05', '2015-09-01 09:35:05', '0');
INSERT INTO `ibs_counterconfig` VALUES ('19', 'outlet.xml', '2', '\\public\\000002', '', '2015-09-01 09:35:44', '2015-09-01 09:35:44', '1');

-- ----------------------------
-- Table structure for ibs_customer
-- ----------------------------
DROP TABLE IF EXISTS `ibs_customer`;
CREATE TABLE `ibs_customer` (
  `customer_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '客户id',
  `customer_name` varchar(32) NOT NULL DEFAULT '' COMMENT '客户姓名',
  `customer_idnum` varchar(32) NOT NULL COMMENT '客户证件号',
  `customer_account` varchar(256) NOT NULL COMMENT '客户账户',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ibs_customer
-- ----------------------------
INSERT INTO `ibs_customer` VALUES ('1', '张大三', '330326199105140333', ',62220214053222,55555495845881588,');

-- ----------------------------
-- Table structure for ibs_device
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='设备表';

-- ----------------------------
-- Records of ibs_device
-- ----------------------------
INSERT INTO `ibs_device` VALUES ('1', 'dev_b1_10', '2', '1', '1', '192.168.2.64', 'financial_116', '33,34,51,30,2');
INSERT INTO `ibs_device` VALUES ('2', 'E1JX32600006B', '2', '1', '1', '192.168.2.243', 'financial_116', '33,34,51,30,2');
INSERT INTO `ibs_device` VALUES ('3', 'E1JL614000742', '2', '1', '2', '192.168.2.23', 'financial_116', '33,34,51,30,2');
INSERT INTO `ibs_device` VALUES ('4', 'dev_r1_10', '2', '2', '2', '192.168.2.64', 'financial_116', '33,34,51,30,2');

-- ----------------------------
-- Table structure for ibs_devicestatus
-- ----------------------------
DROP TABLE IF EXISTS `ibs_devicestatus`;
CREATE TABLE `ibs_devicestatus` (
  `devicestatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `devicestatus_code` int(10) NOT NULL COMMENT '状态码',
  `devicestatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`devicestatus_id`),
  UNIQUE KEY `devicestatus_id_UNIQUE` (`devicestatus_id`),
  UNIQUE KEY `devicestatus_code_UNIQUE` (`devicestatus_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='设备状态表';

-- ----------------------------
-- Records of ibs_devicestatus
-- ----------------------------
INSERT INTO `ibs_devicestatus` VALUES ('1', '1', '在线');
INSERT INTO `ibs_devicestatus` VALUES ('2', '0', '下线');

-- ----------------------------
-- Table structure for ibs_devicetask
-- ----------------------------
DROP TABLE IF EXISTS `ibs_devicetask`;
CREATE TABLE `ibs_devicetask` (
  `devicetask_id` int(10) unsigned NOT NULL,
  `devicetask_devicetype` int(10) NOT NULL COMMENT '设备类型_关联devicetype表',
  `devicetask_outlets` int(10) NOT NULL COMMENT '网点_关联outlets表',
  `devicetask_task` int(10) NOT NULL COMMENT '任务_关联task表',
  PRIMARY KEY (`devicetask_id`),
  UNIQUE KEY `appupdatemap_id_UNIQUE` (`devicetask_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备任务索引表';

-- ----------------------------
-- Records of ibs_devicetask
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_devicetype
-- ----------------------------
DROP TABLE IF EXISTS `ibs_devicetype`;
CREATE TABLE `ibs_devicetype` (
  `devicetype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `devicetype_code` varchar(32) NOT NULL COMMENT '类型代号',
  `devicetype_description` varchar(128) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`devicetype_id`),
  UNIQUE KEY `devicetype_id_UNIQUE` (`devicetype_id`),
  UNIQUE KEY `devicetype_code_UNIQUE` (`devicetype_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ibs_devicetype
-- ----------------------------
INSERT INTO `ibs_devicetype` VALUES ('1', 'E10-1', 'E10-1');
INSERT INTO `ibs_devicetype` VALUES ('2', 'E10-2', 'E10-2');

-- ----------------------------
-- Table structure for ibs_downloadfile
-- ----------------------------
DROP TABLE IF EXISTS `ibs_downloadfile`;
CREATE TABLE `ibs_downloadfile` (
  `downloadfile_id` int(10) NOT NULL AUTO_INCREMENT,
  `downloadfile_name` varchar(128) NOT NULL COMMENT '下载文件名',
  `downloadfile_size` int(10) NOT NULL COMMENT '下载文件大小',
  `downloadfile_uploadtime` datetime NOT NULL COMMENT '文件上传日期',
  `downloadfile_description` varchar(1024) NOT NULL COMMENT '文件描述',
  `downloadfile_filepath` varchar(256) NOT NULL COMMENT '文件路径',
  `downloadfile_md5` varchar(32) NOT NULL COMMENT 'MD5校验值',
  `downloadfile_iconpath` varchar(256) NOT NULL COMMENT '文件图标路径',
  PRIMARY KEY (`downloadfile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ibs_downloadfile
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_firmware
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
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8 COMMENT='固件表';

-- ----------------------------
-- Records of ibs_firmware
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_firmwarestatus
-- ----------------------------
DROP TABLE IF EXISTS `ibs_firmwarestatus`;
CREATE TABLE `ibs_firmwarestatus` (
  `firmwarestatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firmwarestatus_code` int(10) NOT NULL COMMENT '状态码',
  `firmwarestatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`firmwarestatus_id`),
  UNIQUE KEY `firmwarestatus_id_UNIQUE` (`firmwarestatus_id`),
  UNIQUE KEY `firmwarestatus_code_UNIQUE` (`firmwarestatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件状态表';

-- ----------------------------
-- Records of ibs_firmwarestatus
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_firmwaretype
-- ----------------------------
DROP TABLE IF EXISTS `ibs_firmwaretype`;
CREATE TABLE `ibs_firmwaretype` (
  `firmwaretype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firmwaretype_code` varchar(64) NOT NULL COMMENT '类型码',
  `firmwaretype_name` varchar(64) NOT NULL COMMENT '类型名',
  `firmwaretype_version` varchar(32) NOT NULL COMMENT '类型版本',
  `firmwaretype_updatetime` datetime NOT NULL COMMENT '更新日期',
  `firmwaretype_description` varchar(5120) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_id_UNIQUE` (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_code_UNIQUE` (`firmwaretype_code`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='固件类型表';

-- ----------------------------
-- Records of ibs_firmwaretype
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_function
-- ----------------------------
DROP TABLE IF EXISTS `ibs_function`;
CREATE TABLE `ibs_function` (
  `function_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `function_name` varchar(64) NOT NULL COMMENT '功能名称',
  `function_isparent` int(1) NOT NULL COMMENT '是否父节点',
  `function_parentid` int(10) NOT NULL COMMENT '父节点id',
  `function_sortid` int(10) NOT NULL COMMENT '排序id',
  `function_url` varchar(256) NOT NULL COMMENT '功能url',
  `function_icon` varchar(256) NOT NULL COMMENT '功能icon路径',
  `function_target` varchar(256) NOT NULL COMMENT '功能target',
  PRIMARY KEY (`function_id`),
  UNIQUE KEY `function_id_UNIQUE` (`function_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='菜单功能表';

-- ----------------------------
-- Records of ibs_function
-- ----------------------------
INSERT INTO `ibs_function` VALUES ('1', '业务查询', '1', '0', '1', '', 'res/css/zTreeStyle/img/diy/iconBusinessOC.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('2', '远程控制', '1', '0', '2', '', 'res/css/zTreeStyle/img/diy/iconRemoteOC.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('3', '系统管理', '1', '0', '3', '', 'res/css/zTreeStyle/img/diy/iconConsole.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('4', '历史业务统计', '1', '1', '4', '', 'res/css/zTreeStyle/img/diy/iconHistoryOC.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('5', '按交易时间查询', '0', '4', '5', 'business/statistics/querybytime/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconTime.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('6', '按业务类型查询', '0', '4', '6', 'business/statistics/querybydeal/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconBusiness.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('7', '按网点查询', '0', '4', '7', 'business/statistics/querybyoutlets/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconSearchNetstation.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('8', '设备管理', '1', '2', '8', '', 'res/css/zTreeStyle/img/diy/iconDeviceOC.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('9', '设备查询', '0', '8', '9', 'device/devicequery/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconQuery.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('10', '固件升级', '0', '8', '10', 'device/remotecontrol/firmwareupdate/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconRom.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('11', '应用发布', '0', '8', '11', 'device/remotecontrol/appupdate/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconApp.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('12', '柜面应用升级', '0', '2', '12', 'device/remotecontrol/counterappupdate/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconTellerApps.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('13', '用户管理', '0', '3', '13', 'system/user/main/mainUI.action', 'res/css/zTreeStyle/img/diy/iconUser.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('14', '业务管理', '0', '3', '14', 'system/business/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconType.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('15', '网点管理', '0', '3', '15', 'system/outlets/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconSetNetstation.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('16', '下载中心管理', '0', '3', '16', 'system/download/main/toDownloadManage.action', 'res/css/zTreeStyle/img/diy/iconNet.png', 'ui-layout-box');

-- ----------------------------
-- Table structure for ibs_outlets
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='网点表';

-- ----------------------------
-- Records of ibs_outlets
-- ----------------------------
INSERT INTO `ibs_outlets` VALUES ('1', '000001', '鼓楼', '0', '0', '-1', '', '33,34,51,30,2', '000000');
INSERT INTO `ibs_outlets` VALUES ('2', '000002', '仓山', '0', '0', '-1', '', '33,34,51,30,2', '000000');
INSERT INTO `ibs_outlets` VALUES ('15', '000003', '马尾', '0', '0', '-1', '', '33,34,51,30,2', '000000');

-- ----------------------------
-- Table structure for ibs_serverinfo
-- ----------------------------
DROP TABLE IF EXISTS `ibs_serverinfo`;
CREATE TABLE `ibs_serverinfo` (
  `serverinfo_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serverinfo_firststartuptime` datetime NOT NULL COMMENT '第一次启动时间',
  `serverinfo_laststartuptime` datetime NOT NULL COMMENT '最后一次启动时间',
  `serverinfo_laststatisticstime` datetime NOT NULL COMMENT '最后一次统计数据时间',
  PRIMARY KEY (`serverinfo_id`),
  UNIQUE KEY `serverinfo_id_UNIQUE` (`serverinfo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='服务器信息表';

-- ----------------------------
-- Records of ibs_serverinfo
-- ----------------------------
INSERT INTO `ibs_serverinfo` VALUES ('1', '2015-03-20 17:44:54', '2015-09-29 11:11:03', '2015-06-22 23:59:59');

-- ----------------------------
-- Table structure for ibs_task
-- ----------------------------
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

-- ----------------------------
-- Records of ibs_task
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_taskstatus
-- ----------------------------
DROP TABLE IF EXISTS `ibs_taskstatus`;
CREATE TABLE `ibs_taskstatus` (
  `taskstatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `taskstatus_code` int(10) NOT NULL COMMENT '状态码',
  `taskstatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`taskstatus_id`),
  UNIQUE KEY `taskstatus_id_UNIQUE` (`taskstatus_id`),
  UNIQUE KEY `taskstatus_code_UNIQUE` (`taskstatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务状态表';

-- ----------------------------
-- Records of ibs_taskstatus
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_tasktype
-- ----------------------------
DROP TABLE IF EXISTS `ibs_tasktype`;
CREATE TABLE `ibs_tasktype` (
  `tasktype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tasktype_code` int(10) NOT NULL COMMENT '类型码',
  `tasktype_description` varchar(128) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`tasktype_id`),
  UNIQUE KEY `tasktype_id_UNIQUE` (`tasktype_id`),
  UNIQUE KEY `tasktype_code_UNIQUE` (`tasktype_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务类型表';

-- ----------------------------
-- Records of ibs_tasktype
-- ----------------------------

-- ----------------------------
-- Table structure for ibs_user
-- ----------------------------
DROP TABLE IF EXISTS `ibs_user`;
CREATE TABLE `ibs_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_uid` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户账号',
  `user_name` varchar(256) NOT NULL COMMENT '用户昵称',
  `user_password` varchar(256) NOT NULL COMMENT '用户密码',
  `user_isadmin` int(1) NOT NULL COMMENT '是否管理员',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of ibs_user
-- ----------------------------
INSERT INTO `ibs_user` VALUES ('1', 'admin', '超级管理员', 'Y2VudGVybQ==', '1');

-- ----------------------------
-- Table structure for trans_record
-- ----------------------------
DROP TABLE IF EXISTS `trans_record`;
CREATE TABLE `trans_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `queryno` char(6) DEFAULT NULL COMMENT '排队号',
  `datetime` datetime DEFAULT NULL,
  `latticecode` char(50) DEFAULT NULL,
  `terminalcode` char(30) DEFAULT NULL,
  `transdata` varchar(6000) DEFAULT NULL COMMENT '记录数据',
  `cardno` char(50) DEFAULT NULL COMMENT '客户证件号',
  `date` date DEFAULT NULL,
  `account` char(40) DEFAULT NULL COMMENT '账号',
  `trancode` char(50) DEFAULT NULL COMMENT '业务码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trans_record
-- ----------------------------
INSERT INTO `trans_record` VALUES ('1', null, '000004', '2015-08-31 14:07:29', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('2', null, '000005', '2015-08-31 14:08:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('3', null, '000006', '2015-08-31 14:09:58', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('4', null, '000007', '2015-08-31 14:09:58', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('5', null, '000008', '2015-08-31 14:09:58', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('6', null, '000009', '2015-08-31 14:09:58', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('7', null, '000010', '2015-08-31 14:09:58', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('8', null, '000011', '2015-08-31 14:09:58', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('9', null, '000012', '2015-08-31 14:09:58', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('10', null, '000013', '2015-08-31 14:09:58', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('11', null, '000014', '2015-08-31 14:09:58', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('12', null, '000015', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('13', null, '000016', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('14', null, '000017', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('15', null, '000018', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('16', null, '000019', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('17', null, '000020', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('18', null, '000021', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('19', null, '000022', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('20', null, '000023', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('21', null, '000024', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('22', null, '000025', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('23', null, '000026', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('24', null, '000027', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('25', null, '000028', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('26', null, '000029', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('27', null, '000030', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('28', null, '000031', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('29', null, '000032', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('30', null, '000033', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('31', null, '000034', '2015-08-31 14:09:59', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('32', null, '000035', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('33', null, '000036', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('34', null, '000037', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('35', null, '000038', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('36', null, '000039', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('37', null, '000040', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('38', null, '000041', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('39', null, '000042', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('40', null, '000043', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('41', null, '000044', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('42', null, '000045', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('43', null, '000046', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('44', null, '000047', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('45', null, '000048', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('46', null, '000049', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('47', null, '000050', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('48', null, '000051', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('49', null, '000052', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('50', null, '000053', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('51', null, '000054', '2015-08-31 14:10:00', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('52', null, '000055', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('53', null, '000056', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('54', null, '000057', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('55', null, '000058', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('56', null, '000059', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('57', null, '000060', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('58', null, '000061', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('59', null, '000062', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('60', null, '000063', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('61', null, '000064', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('62', null, '000065', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('63', null, '000066', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('64', null, '000067', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('65', null, '000068', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('66', null, '000069', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('67', null, '000070', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('68', null, '000071', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('69', null, '000072', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('70', null, '000073', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('71', null, '000074', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('72', null, '000075', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('73', null, '000076', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('74', null, '000077', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('75', null, '000078', '2015-08-31 14:10:01', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('76', null, '000079', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('77', null, '000080', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('78', null, '000081', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('79', null, '000082', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('80', null, '000083', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('81', null, '000084', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('82', null, '000085', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('83', null, '000086', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('84', null, '000087', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('85', null, '000088', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('86', null, '000089', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('87', null, '000090', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('88', null, '000091', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('89', null, '000092', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('90', null, '000093', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('91', null, '000094', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('92', null, '000095', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('93', null, '000096', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('94', null, '000097', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('95', null, '000098', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('96', null, '000099', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('97', null, '000100', '2015-08-31 14:10:02', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('98', null, '000101', '2015-08-31 14:10:03', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('99', null, '000102', '2015-08-31 14:10:03', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('100', null, '000103', '2015-08-31 14:10:03', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('101', null, '000104', '2015-08-31 14:10:03', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('102', null, '000105', '2015-08-31 14:10:03', null, null, '[{\"prt_cus_idtype\":\"1\",\"longterm_flag\":\"2\",\"code_transtype\":\"1\",\"cus_idtype\":\"10\",\"prt_issue_addr\":\"罗源县公安局\",\"trancode\":\"020205|040402|060101\",\"cardnum\":\"5555555888855555\",\"record_id_num\":\"350123199212240315\",\"issue_code\":\"350123\",\"record_account_num\":\"5555555888855555\",\"cus_idnum\":\"350123199212240315\",\"sign_date_year\":\"2015\",\"nationality_flag\":\"CN\",\"card_change_flag\":\"0\",\"code_manage_flag\":\"1\",\"cusinfo_chg_flag\":\"0\",\"cus_sex_flag\":\"1\",\"cus_name\":\"叶焱鑫\",\"prt_idtype_info\":\"居民身份证\",\"prt_nationality\":\"中国\",\"sign_date_month\":\"7\",\"id_duedate\":\"20240124\",\"sign_date_day\":\"10\"}]', '350123199212240315', '2015-08-31', '5555555888855555', '020205|040402|060101');
INSERT INTO `trans_record` VALUES ('103', null, '000106', '2015-08-31 16:43:54', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-08-31', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('104', null, '000001', '2015-09-01 10:22:38', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-01', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('105', null, '000002', '2015-09-01 10:32:44', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-01', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('106', null, '000003', '2015-09-01 10:34:34', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-01', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('107', null, '000004', '2015-09-01 10:37:54', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-01', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('108', null, '000006', '2015-09-01 10:49:22', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-01', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('109', null, '000008', '2015-09-01 10:56:47', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-01', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('110', null, '000009', '2015-09-01 10:59:26', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-01', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('111', null, '000001', '2015-09-02 09:47:57', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('112', null, '000002', '2015-09-02 09:47:57', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('113', null, '000003', '2015-09-02 09:47:57', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('114', null, '000004', '2015-09-02 09:47:57', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('115', null, '000005', '2015-09-02 09:47:57', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('116', null, '000006', '2015-09-02 09:47:57', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('117', null, '000007', '2015-09-02 09:47:57', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('118', null, '000008', '2015-09-02 09:47:58', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('119', null, '000009', '2015-09-02 09:47:58', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('120', null, '000010', '2015-09-02 09:47:58', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('121', null, '000011', '2015-09-02 15:25:18', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('122', null, '000012', '2015-09-02 15:37:35', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');
INSERT INTO `trans_record` VALUES ('123', null, '000013', '2015-09-02 15:38:01', null, null, '[{\"longterm_flag\":\"2\",\"cus_idtype\":\"10\",\"trancode\":\"020202|010101|010201|010601\",\"prt_profession\":\"国家机关、党群组织、企业、事业单位负责人\",\"prt_issue_addr\":\"大田县公安局\",\"profession_flag\":\"1\",\"post_code\":\"\",\"record_id_num\":\"350425199201242621\",\"issue_code\":\"350425\",\"record_account_num\":\"\",\"cus_idnum\":\"350425199201242621\",\"sign_date_year\":\"2015\",\"cus_addr\":\"福建省大田县太华镇魁城村213号\",\"nationality_flag\":\"CN\",\"cus_spell\":\"lianwenjin\",\"cus_sex_flag\":\"2\",\"cus_name\":\"连文金\",\"prt_nationality\":\"1\",\"sign_date_month\":\"8\",\"sign_date_day\":\"31\",\"prt_idtype\":\"1\",\"id_duedate\":\"20221008\",\"prt_currency\":\"1\",\"mobile_num\":\"18060845472\"}]', '350425199201242621', '2015-09-02', null, '020202|010101|010201|010601');

-- ----------------------------
-- View structure for counterapp
-- ----------------------------
DROP VIEW IF EXISTS `counterapp`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `counterapp` AS select `a`.`counterapp_name` AS `counterapp_name`,`a`.`counterapp_version` AS `counterapp_version`,`a`.`counterapp_lastversion` AS `counterapp_lastversion`,`t`.`counterapptype_code` AS `counterapptype_code`,`t`.`counterapptype_name` AS `counterapptype_name`,`a`.`counterapp_status` AS `counterapp_status`,`a`.`counterapp_id` AS `counterapp_id` from (`ibs_counterapptype` `t` join `ibs_counterapp` `a`) where (`a`.`counterapp_type` = `t`.`counterapptype_id`) ; ;

-- ----------------------------
-- Procedure structure for PROC_BUSINESSRECORD_MONTH_PARTITION
-- ----------------------------
DROP PROCEDURE IF EXISTS `PROC_BUSINESSRECORD_MONTH_PARTITION`;
DELIMITER ;;
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
  
	IF MONTH(currentTime) < 10 THEN
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

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for PROC_BUSINESSSTATISTICS_YEAR_PARTITION
-- ----------------------------
DROP PROCEDURE IF EXISTS `PROC_BUSINESSSTATISTICS_YEAR_PARTITION`;
DELIMITER ;;
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

END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVENT_BUSINESSRECORD_MONTH_PARTITION
-- ----------------------------
DROP EVENT IF EXISTS `EVENT_BUSINESSRECORD_MONTH_PARTITION`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `EVENT_BUSINESSRECORD_MONTH_PARTITION` ON SCHEDULE EVERY 1 MONTH STARTS '2015-06-01 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO CALL PROC_BUSINESSRECORD_MONTH_PARTITION()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for EVENT_BUSINESSSTATISTICS_YEAR_PARTITION
-- ----------------------------
DROP EVENT IF EXISTS `EVENT_BUSINESSSTATISTICS_YEAR_PARTITION`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `EVENT_BUSINESSSTATISTICS_YEAR_PARTITION` ON SCHEDULE EVERY 1 YEAR STARTS '2015-01-01 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO call PROC_BUSINESSSTATISTICS_YEAR_PARTITION()
;;
DELIMITER ;
