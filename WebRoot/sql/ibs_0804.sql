/*
Navicat MySQL Data Transfer

Source Server         : 3306
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-08-04 13:52:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ibs_app`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用表';

-- ----------------------------
-- Table structure for `ibs_appstatus`
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
-- Table structure for `ibs_authority`
-- ----------------------------
DROP TABLE IF EXISTS `ibs_authority`;
CREATE TABLE `ibs_authority` (
  `authority_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `authority_userid` int(10) unsigned NOT NULL COMMENT 'user表id',
  `authority_functionid` int(10) unsigned NOT NULL COMMENT 'function表id',
  PRIMARY KEY (`authority_id`),
  UNIQUE KEY `authority_id_UNIQUE` (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='user_function映射表';

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

-- ----------------------------
-- Table structure for `ibs_business`
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='交易表';

-- ----------------------------
-- Records of ibs_business
-- ----------------------------
INSERT INTO `ibs_business` VALUES ('1', '-1', '个人业务', '1', '0', '-1', '个人业务', '0');
INSERT INTO `ibs_business` VALUES ('2', '-1', '企业业务', '1', '0', '-1', '企业业务', '0');


-- ----------------------------
-- Table structure for `ibs_businessrecord`
-- ----------------------------
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
 PARTITION part_201507 VALUES LESS THAN (736176) ENGINE = InnoDB,
 PARTITION part_201508 VALUES LESS THAN (736207) ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for `ibs_businessstatistics`
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
) ENGINE=InnoDB AUTO_INCREMENT=660008 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (YEAR(businessstatistics_date))
(PARTITION part_2014 VALUES LESS THAN (2015) ENGINE = InnoDB,
 PARTITION part_2015 VALUES LESS THAN (2016) ENGINE = InnoDB) */;


-- ----------------------------
-- Table structure for `ibs_counterapp`
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='柜员应用表';


-- ----------------------------
-- Table structure for `ibs_counterappstatus`
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
-- Table structure for `ibs_counterapptype`
-- ----------------------------
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

-- ----------------------------
-- Records of ibs_counterapptype
-- ----------------------------
INSERT INTO `ibs_counterapptype` VALUES ('1', '300001', 'telnet', '2.001.01', '2015-08-04 10:07:33', '测试');

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

-- ----------------------------
-- Records of ibs_counterconfig
-- ----------------------------

-- ----------------------------
-- Table structure for `ibs_customer`
-- ----------------------------
DROP TABLE IF EXISTS `ibs_customer`;
CREATE TABLE `ibs_customer` (
  `customer_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '客户id',
  `customer_name` varchar(32) NOT NULL DEFAULT '' COMMENT '客户姓名',
  `customer_idnum` varchar(32) NOT NULL COMMENT '客户证件号',
  `customer_account` varchar(256) NOT NULL COMMENT '客户账户',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `ibs_device`
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备表';


-- ----------------------------
-- Table structure for `ibs_devicestatus`
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
INSERT INTO `ibs_devicestatus` VALUES ('2', '2', '下线');

-- ----------------------------
-- Table structure for `ibs_devicetask`
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
-- Table structure for `ibs_devicetype`
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
INSERT INTO `ibs_devicetype` VALUES ('3', 'E10-3', 'E10-3');

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

-- ----------------------------
-- Table structure for `ibs_firmwarestatus`
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
-- Table structure for `ibs_firmwaretype`
-- ----------------------------
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件类型表';

-- ----------------------------
-- Table structure for `ibs_function`
-- ----------------------------
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

-- ----------------------------
-- Records of ibs_function
-- ----------------------------
INSERT INTO `ibs_function` VALUES ('1', '业务查询', '1', '0', '1', '', 'res/css/zTreeStyle/img/diy/iconBusinessOC.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('2', '历史业务统计', '1', '1', '2', '', 'res/css/zTreeStyle/img/diy/iconHistoryOC.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('3', '按时间查询', '0', '2', '4', 'business/statistics/querybytime/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconTime.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('5', '后台管理', '1', '0', '10', '', 'res/css/zTreeStyle/img/diy/iconConsole.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('6', '用户管理', '0', '5', '11', 'system/user/main/mainUI.action', 'res/css/zTreeStyle/img/diy/iconUser.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('7', '设备管理', '1', '0', '5', '', 'res/css/zTreeStyle/img/diy/iconDeviceOC.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('8', '远程操作', '1', '7', '6', '', 'res/css/zTreeStyle/img/diy/iconRemoteOC.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('9', '固件升级', '0', '8', '8', 'device/remotecontrol/firmwareupdate/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconRom.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('10', '应用发布', '0', '8', '9', 'device/remotecontrol/appupdate/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconApp.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('11', '设备查询', '0', '7', '7', 'device/devicequery/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconQuery.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('12', '按交易类型查询', '0', '2', '12', 'business/statistics/querybydeal/main/toUI.action', 'res/css/zTreeStyle/img/diy/iconBusiness.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('13', '业务管理', '0', '5', '13', ' system/business/main/toUI.action', ' res/css/zTreeStyle/img/diy/iconType.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('14', '网点管理', '0', '5', '14', 'system/outlets/main/toUI.action', ' res/css/zTreeStyle/img/diy/iconNet.png', 'ui-layout-box');
INSERT INTO `ibs_function` VALUES ('15', '柜员应用升级', '0', '7', '15', 'device/remotecontrol/counterappupdate/main/toUI.action', ' res/css/zTreeStyle/img/diy/iconTellerApps.png', 'ui-layout-box');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网点表';

-- ----------------------------
-- Table structure for `ibs_serverinfo`
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
INSERT INTO `ibs_serverinfo` VALUES ('1', '2015-03-20 17:44:54', '2015-08-04 13:33:05', '2015-06-22 23:59:59');

-- ----------------------------
-- Table structure for `ibs_task`
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
-- Table structure for `ibs_taskstatus`
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
-- Table structure for `ibs_tasktype`
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
-- Table structure for `ibs_user`
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of ibs_user
-- ----------------------------
INSERT INTO `ibs_user` VALUES ('1', 'admin', '超级管理员', 'Y2VudGVybQ==', '1');

-- ----------------------------
-- View structure for `counterapp`
-- ----------------------------
DROP VIEW IF EXISTS `counterapp`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `counterapp` AS select `a`.`counterapp_name` AS `counterapp_name`,`a`.`counterapp_version` AS `counterapp_version`,`a`.`counterapp_lastversion` AS `counterapp_lastversion`,`t`.`counterapptype_code` AS `counterapptype_code`,`t`.`counterapptype_name` AS `counterapptype_name`,`a`.`counterapp_status` AS `counterapp_status`,`a`.`counterapp_id` AS `counterapp_id` from (`ibs_counterapptype` `t` join `ibs_counterapp` `a`) where (`a`.`counterapp_type` = `t`.`counterapptype_id`) ;

-- ----------------------------
-- Procedure structure for `PROC_BUSINESSRECORD_MONTH_PARTITION`
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

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `PROC_BUSINESSSTATISTICS_YEAR_PARTITION`
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
-- Event structure for `EVENT_BUSINESSRECORD_MONTH_PARTITION`
-- ----------------------------
DROP EVENT IF EXISTS `EVENT_BUSINESSRECORD_MONTH_PARTITION`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `EVENT_BUSINESSRECORD_MONTH_PARTITION` ON SCHEDULE EVERY 1 MONTH STARTS '2015-06-01 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO CALL PROC_BUSINESSRECORD_MONTH_PARTITION()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `EVENT_BUSINESSSTATISTICS_YEAR_PARTITION`
-- ----------------------------
DROP EVENT IF EXISTS `EVENT_BUSINESSSTATISTICS_YEAR_PARTITION`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `EVENT_BUSINESSSTATISTICS_YEAR_PARTITION` ON SCHEDULE EVERY 1 YEAR STARTS '2015-01-01 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO call PROC_BUSINESSSTATISTICS_YEAR_PARTITION()
;;
DELIMITER ;
