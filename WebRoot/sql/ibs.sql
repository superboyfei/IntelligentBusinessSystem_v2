# MySQL-Front 5.1  (Build 4.2)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 127.0.0.1    Database: ibs
# ------------------------------------------------------
# Server version 5.6.23-log

#
# Source for table ibs_app
#

DROP TABLE IF EXISTS `ibs_app`;
CREATE TABLE `ibs_app` (
  `app_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `app_name` varchar(128) NOT NULL COMMENT '应用名称',
  `app_version` varchar(32) NOT NULL COMMENT '应用版本',
  `app_status` int(10) NOT NULL COMMENT '应用状态',
  `app_uploadtime` datetime NOT NULL COMMENT '应用上传日期',
  `app_deletetime` datetime NOT NULL COMMENT '应用删除日期',
  `app_filepath` varchar(256) NOT NULL COMMENT '应用路径',
  PRIMARY KEY (`app_id`),
  UNIQUE KEY `app_id_UNIQUE` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用表';

#
# Dumping data for table ibs_app
#

LOCK TABLES `ibs_app` WRITE;
/*!40000 ALTER TABLE `ibs_app` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_app` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_appstatus
#

DROP TABLE IF EXISTS `ibs_appstatus`;
CREATE TABLE `ibs_appstatus` (
  `appstatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `appstatus_code` int(10) NOT NULL COMMENT '状态码',
  `appstatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`appstatus_id`),
  UNIQUE KEY `appstatus_id_UNIQUE` (`appstatus_id`),
  UNIQUE KEY `appstatus_code_UNIQUE` (`appstatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用状态表';

#
# Dumping data for table ibs_appstatus
#

LOCK TABLES `ibs_appstatus` WRITE;
/*!40000 ALTER TABLE `ibs_appstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_appstatus` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_authority
#

DROP TABLE IF EXISTS `ibs_authority`;
CREATE TABLE `ibs_authority` (
  `authority_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `authority_userid` int(10) unsigned NOT NULL COMMENT 'user表id',
  `authority_functionid` int(10) unsigned NOT NULL COMMENT 'function表id',
  PRIMARY KEY (`authority_id`),
  UNIQUE KEY `authority_id_UNIQUE` (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='user_function映射表';

#
# Dumping data for table ibs_authority
#

LOCK TABLES `ibs_authority` WRITE;
/*!40000 ALTER TABLE `ibs_authority` DISABLE KEYS */;
INSERT INTO `ibs_authority` VALUES (1,1,1);
INSERT INTO `ibs_authority` VALUES (2,1,2);
INSERT INTO `ibs_authority` VALUES (3,1,3);
INSERT INTO `ibs_authority` VALUES (4,1,4);
INSERT INTO `ibs_authority` VALUES (5,1,5);
INSERT INTO `ibs_authority` VALUES (6,1,6);
INSERT INTO `ibs_authority` VALUES (7,1,7);
INSERT INTO `ibs_authority` VALUES (8,1,8);
INSERT INTO `ibs_authority` VALUES (9,1,9);
INSERT INTO `ibs_authority` VALUES (10,1,10);
INSERT INTO `ibs_authority` VALUES (11,1,11);
INSERT INTO `ibs_authority` VALUES (12,2,1);
INSERT INTO `ibs_authority` VALUES (13,2,2);
INSERT INTO `ibs_authority` VALUES (14,2,3);
INSERT INTO `ibs_authority` VALUES (15,2,4);
INSERT INTO `ibs_authority` VALUES (16,2,5);
INSERT INTO `ibs_authority` VALUES (17,2,6);
INSERT INTO `ibs_authority` VALUES (18,2,7);
INSERT INTO `ibs_authority` VALUES (19,2,8);
INSERT INTO `ibs_authority` VALUES (20,2,9);
INSERT INTO `ibs_authority` VALUES (21,2,10);
INSERT INTO `ibs_authority` VALUES (22,2,11);
INSERT INTO `ibs_authority` VALUES (32,3,1);
INSERT INTO `ibs_authority` VALUES (33,3,2);
INSERT INTO `ibs_authority` VALUES (34,3,3);
INSERT INTO `ibs_authority` VALUES (35,3,4);
INSERT INTO `ibs_authority` VALUES (45,5,1);
INSERT INTO `ibs_authority` VALUES (46,5,2);
INSERT INTO `ibs_authority` VALUES (47,5,3);
INSERT INTO `ibs_authority` VALUES (48,5,4);
INSERT INTO `ibs_authority` VALUES (49,5,5);
INSERT INTO `ibs_authority` VALUES (50,5,6);
INSERT INTO `ibs_authority` VALUES (51,5,7);
INSERT INTO `ibs_authority` VALUES (52,5,8);
INSERT INTO `ibs_authority` VALUES (53,5,9);
INSERT INTO `ibs_authority` VALUES (54,5,10);
INSERT INTO `ibs_authority` VALUES (55,5,11);
INSERT INTO `ibs_authority` VALUES (65,7,1);
INSERT INTO `ibs_authority` VALUES (66,7,2);
INSERT INTO `ibs_authority` VALUES (67,7,3);
INSERT INTO `ibs_authority` VALUES (68,7,4);
INSERT INTO `ibs_authority` VALUES (69,7,5);
INSERT INTO `ibs_authority` VALUES (70,7,6);
INSERT INTO `ibs_authority` VALUES (71,7,7);
INSERT INTO `ibs_authority` VALUES (72,7,8);
INSERT INTO `ibs_authority` VALUES (73,7,9);
INSERT INTO `ibs_authority` VALUES (74,7,10);
INSERT INTO `ibs_authority` VALUES (75,7,11);
INSERT INTO `ibs_authority` VALUES (76,6,1);
INSERT INTO `ibs_authority` VALUES (77,6,2);
INSERT INTO `ibs_authority` VALUES (78,6,3);
INSERT INTO `ibs_authority` VALUES (79,6,4);
INSERT INTO `ibs_authority` VALUES (80,6,7);
INSERT INTO `ibs_authority` VALUES (81,6,11);
INSERT INTO `ibs_authority` VALUES (82,6,8);
INSERT INTO `ibs_authority` VALUES (83,6,9);
INSERT INTO `ibs_authority` VALUES (84,6,10);
INSERT INTO `ibs_authority` VALUES (85,6,5);
INSERT INTO `ibs_authority` VALUES (86,6,6);
/*!40000 ALTER TABLE `ibs_authority` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_business
#

DROP TABLE IF EXISTS `ibs_business`;
CREATE TABLE `ibs_business` (
  `business_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `business_code` varchar(32) NOT NULL COMMENT '交易码',
  `business_name` varchar(256) NOT NULL COMMENT '交易名称',
  `business_isparent` int(1) NOT NULL COMMENT '是否父节点',
  `business_parentid` int(10) NOT NULL COMMENT '父节点id',
  `business_sortid` int(10) NOT NULL COMMENT '排序id',
  `business_feature` varchar(512) NOT NULL COMMENT '交易描述',
  PRIMARY KEY (`business_id`),
  UNIQUE KEY `business_id_UNIQUE` (`business_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='交易表';

#
# Dumping data for table ibs_business
#

LOCK TABLES `ibs_business` WRITE;
/*!40000 ALTER TABLE `ibs_business` DISABLE KEYS */;
INSERT INTO `ibs_business` VALUES (1,'','所有业务',1,0,0,'');
INSERT INTO `ibs_business` VALUES (2,'0001','开卡',0,1,1,'');
INSERT INTO `ibs_business` VALUES (3,'0002','转账',0,1,2,'');
/*!40000 ALTER TABLE `ibs_business` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_businessrecord_201501
#

DROP TABLE IF EXISTS `ibs_businessrecord_201501`;
CREATE TABLE `ibs_businessrecord_201501` (
  `businessrecord_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `businessrecord_time` datetime NOT NULL COMMENT '业务时间',
  `businessrecord_outlets` int(10) unsigned NOT NULL COMMENT '业务网点_关联outlets表',
  `businessrecord_business` int(10) unsigned NOT NULL COMMENT '业务信息_关联business表',
  `businessrecord_device` int(10) unsigned NOT NULL COMMENT '办理业务的设备_关联device表',
  `businessrecord_data` varchar(512) NOT NULL COMMENT '业务数据',
  PRIMARY KEY (`businessrecord_id`),
  UNIQUE KEY `businessrecord_id_UNIQUE` (`businessrecord_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='2015年1月业务记录表';

#
# Dumping data for table ibs_businessrecord_201501
#

LOCK TABLES `ibs_businessrecord_201501` WRITE;
/*!40000 ALTER TABLE `ibs_businessrecord_201501` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_businessrecord_201501` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_businessstatistics_2015
#

DROP TABLE IF EXISTS `ibs_businessstatistics_2015`;
CREATE TABLE `ibs_businessstatistics_2015` (
  `businessstatistics_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `businessstatistics_date` date NOT NULL COMMENT '业务日期',
  `businessstatistics_outlets` int(11) NOT NULL COMMENT '业务网点_关联outlets表',
  `businessstatistics_business` int(11) NOT NULL COMMENT '业务信息_关联business表',
  `businessstatistics_device` int(11) NOT NULL COMMENT '办理业务的设备_关联device表',
  `businessstatistics_count` int(32) unsigned NOT NULL COMMENT '业务数量',
  PRIMARY KEY (`businessstatistics_id`),
  UNIQUE KEY `businessstatistics_id_UNIQUE` (`businessstatistics_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='2015年业务统计表';

#
# Dumping data for table ibs_businessstatistics_2015
#

LOCK TABLES `ibs_businessstatistics_2015` WRITE;
/*!40000 ALTER TABLE `ibs_businessstatistics_2015` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_businessstatistics_2015` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_device
#

DROP TABLE IF EXISTS `ibs_device`;
CREATE TABLE `ibs_device` (
  `device_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `device_serial` varchar(64) NOT NULL COMMENT '设备序列号',
  `device_type` int(10) unsigned NOT NULL COMMENT '设备类型_关联devicetype表',
  `device_outlets` int(10) unsigned NOT NULL COMMENT '设备所在网点_关联outlets表',
  `device_status` int(10) unsigned NOT NULL COMMENT '设备状态',
  `device_firmware` varchar(128) NOT NULL COMMENT '设备固件_关联firmware表，可有多个firmware',
  `device_app` varchar(128) NOT NULL COMMENT '设备应用_关联app表，可有多个app',
  PRIMARY KEY (`device_id`),
  UNIQUE KEY `device_id_UNIQUE` (`device_id`),
  UNIQUE KEY `device_serial_UNIQUE` (`device_serial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备表';

#
# Dumping data for table ibs_device
#

LOCK TABLES `ibs_device` WRITE;
/*!40000 ALTER TABLE `ibs_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_device` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_devicestatus
#

DROP TABLE IF EXISTS `ibs_devicestatus`;
CREATE TABLE `ibs_devicestatus` (
  `devicestatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `devicestatus_code` int(10) NOT NULL COMMENT '状态码',
  `devicestatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`devicestatus_id`),
  UNIQUE KEY `devicestatus_id_UNIQUE` (`devicestatus_id`),
  UNIQUE KEY `devicestatus_code_UNIQUE` (`devicestatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备状态表';

#
# Dumping data for table ibs_devicestatus
#

LOCK TABLES `ibs_devicestatus` WRITE;
/*!40000 ALTER TABLE `ibs_devicestatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_devicestatus` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_devicetask
#

DROP TABLE IF EXISTS `ibs_devicetask`;
CREATE TABLE `ibs_devicetask` (
  `devicetask_id` int(10) unsigned NOT NULL,
  `devicetask_devicetype` int(10) NOT NULL COMMENT '设备类型_关联devicetype表',
  `devicetask_outlets` int(10) NOT NULL COMMENT '网点_关联outlets表',
  `devicetask_task` int(10) NOT NULL COMMENT '任务_关联task表',
  PRIMARY KEY (`devicetask_id`),
  UNIQUE KEY `appupdatemap_id_UNIQUE` (`devicetask_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备任务索引表';

#
# Dumping data for table ibs_devicetask
#

LOCK TABLES `ibs_devicetask` WRITE;
/*!40000 ALTER TABLE `ibs_devicetask` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_devicetask` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_devicetype
#

DROP TABLE IF EXISTS `ibs_devicetype`;
CREATE TABLE `ibs_devicetype` (
  `devicetype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `devicetype_code` varchar(32) NOT NULL COMMENT '类型代号',
  `devicetype_description` varchar(128) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`devicetype_id`),
  UNIQUE KEY `devicetype_id_UNIQUE` (`devicetype_id`),
  UNIQUE KEY `devicetype_code_UNIQUE` (`devicetype_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Dumping data for table ibs_devicetype
#

LOCK TABLES `ibs_devicetype` WRITE;
/*!40000 ALTER TABLE `ibs_devicetype` DISABLE KEYS */;
INSERT INTO `ibs_devicetype` VALUES (1,'E10-1','');
INSERT INTO `ibs_devicetype` VALUES (2,'E10-2','');
/*!40000 ALTER TABLE `ibs_devicetype` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_firmware
#

DROP TABLE IF EXISTS `ibs_firmware`;
CREATE TABLE `ibs_firmware` (
  `firmware_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firmware_name` varchar(128) NOT NULL COMMENT '固件名称',
  `firmware_version` varchar(32) NOT NULL COMMENT '固件版本',
  `firmware_type` int(10) NOT NULL COMMENT '固件类型_关联firmwaretype表',
  `firmware_status` int(10) NOT NULL COMMENT '固件状态',
  `firmware_uploadtime` datetime NOT NULL COMMENT '固件上传时间',
  `firmware_deletetime` datetime NOT NULL COMMENT '固件删除时间',
  `firmware_filepath` varchar(256) NOT NULL COMMENT '固件文件路径',
  PRIMARY KEY (`firmware_id`),
  UNIQUE KEY `firmware_id_UNIQUE` (`firmware_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件表';

#
# Dumping data for table ibs_firmware
#

LOCK TABLES `ibs_firmware` WRITE;
/*!40000 ALTER TABLE `ibs_firmware` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_firmware` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_firmwarestatus
#

DROP TABLE IF EXISTS `ibs_firmwarestatus`;
CREATE TABLE `ibs_firmwarestatus` (
  `firmwarestatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firmwarestatus_code` int(10) NOT NULL COMMENT '状态码',
  `firmwarestatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`firmwarestatus_id`),
  UNIQUE KEY `firmwarestatus_id_UNIQUE` (`firmwarestatus_id`),
  UNIQUE KEY `firmwarestatus_code_UNIQUE` (`firmwarestatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件状态表';

#
# Dumping data for table ibs_firmwarestatus
#

LOCK TABLES `ibs_firmwarestatus` WRITE;
/*!40000 ALTER TABLE `ibs_firmwarestatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_firmwarestatus` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_firmwaretype
#

DROP TABLE IF EXISTS `ibs_firmwaretype`;
CREATE TABLE `ibs_firmwaretype` (
  `firmwaretype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firmwaretype_code` int(10) NOT NULL COMMENT '类型码',
  `firmwaretype_description` varchar(128) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_id_UNIQUE` (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_code_UNIQUE` (`firmwaretype_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固件类型表';

#
# Dumping data for table ibs_firmwaretype
#

LOCK TABLES `ibs_firmwaretype` WRITE;
/*!40000 ALTER TABLE `ibs_firmwaretype` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_firmwaretype` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_function
#

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

#
# Dumping data for table ibs_function
#

LOCK TABLES `ibs_function` WRITE;
/*!40000 ALTER TABLE `ibs_function` DISABLE KEYS */;
INSERT INTO `ibs_function` VALUES (1,'业务查询',1,0,1,'','res/css/zTreeStyle/img/diy/iconBusinessOC.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (2,'历史业务统计',1,1,1,'','res/css/zTreeStyle/img/diy/iconHistoryOC.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (3,'按时间查询',0,2,1,'business/statistics/querybytime/main/toUI.action','res/css/zTreeStyle/img/diy/iconTime.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (4,'实时业务明细',0,1,2,'business/record/main/toUI.action','res/css/zTreeStyle/img/diy/iconDetails.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (5,'后台管理',1,0,3,'','res/css/zTreeStyle/img/diy/iconConsole.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (6,'用户管理',0,5,1,'system/user/main/mainUI.action','res/css/zTreeStyle/img/diy/iconUser.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (7,'设备管理',1,0,2,'','res/css/zTreeStyle/img/diy/iconDeviceOC.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (8,'远程操作',1,7,2,'','res/css/zTreeStyle/img/diy/iconRemoteOC.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (9,'固件升级',0,8,1,'device/remotecontrol/firmwareupdate/main/toUI.action','res/css/zTreeStyle/img/diy/iconRom.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (10,'应用发布',0,8,2,'device/remotecontrol/appupdate/main/toUI.action','res/css/zTreeStyle/img/diy/iconApp.png','ui-layout-box');
INSERT INTO `ibs_function` VALUES (11,'设备查询',0,7,1,'device/devicequery/main/toUI.action','res/css/zTreeStyle/img/diy/iconQuery.png','ui-layout-box');
/*!40000 ALTER TABLE `ibs_function` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_outlets
#

DROP TABLE IF EXISTS `ibs_outlets`;
CREATE TABLE `ibs_outlets` (
  `outlets_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `outlets_name` varchar(128) NOT NULL COMMENT '网点名称',
  `outlets_isparent` int(1) NOT NULL COMMENT '是否父节点',
  `outlets_parentid` int(10) NOT NULL COMMENT '父节点id',
  `outlets_sortid` int(10) NOT NULL COMMENT '排序id',
  `outlets_description` varchar(256) NOT NULL COMMENT '网点描述',
  PRIMARY KEY (`outlets_id`),
  UNIQUE KEY `outlets_id_UNIQUE` (`outlets_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='网点表';

#
# Dumping data for table ibs_outlets
#

LOCK TABLES `ibs_outlets` WRITE;
/*!40000 ALTER TABLE `ibs_outlets` DISABLE KEYS */;
INSERT INTO `ibs_outlets` VALUES (1,'所有网点',1,0,0,'');
INSERT INTO `ibs_outlets` VALUES (2,'仓山网点',0,1,1,'');
INSERT INTO `ibs_outlets` VALUES (3,'鼓楼网点',0,1,2,'');
/*!40000 ALTER TABLE `ibs_outlets` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_serverinfo
#

DROP TABLE IF EXISTS `ibs_serverinfo`;
CREATE TABLE `ibs_serverinfo` (
  `serverinfo_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serverinfo_firststartuptime` datetime NOT NULL COMMENT '第一次启动时间',
  `serverinfo_laststartuptime` datetime NOT NULL COMMENT '最后一次启动时间',
  `serverinfo_laststatisticstime` datetime NOT NULL COMMENT '最后一次统计数据时间',
  PRIMARY KEY (`serverinfo_id`),
  UNIQUE KEY `serverinfo_id_UNIQUE` (`serverinfo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='服务器信息表';

#
# Dumping data for table ibs_serverinfo
#

LOCK TABLES `ibs_serverinfo` WRITE;
/*!40000 ALTER TABLE `ibs_serverinfo` DISABLE KEYS */;
INSERT INTO `ibs_serverinfo` VALUES (1,'2015-03-20 17:44:54','2015-04-29 17:04:32','1000-01-01');
/*!40000 ALTER TABLE `ibs_serverinfo` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_task
#

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

#
# Dumping data for table ibs_task
#

LOCK TABLES `ibs_task` WRITE;
/*!40000 ALTER TABLE `ibs_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_task` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_taskstatus
#

DROP TABLE IF EXISTS `ibs_taskstatus`;
CREATE TABLE `ibs_taskstatus` (
  `taskstatus_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `taskstatus_code` int(10) NOT NULL COMMENT '状态码',
  `taskstatus_description` varchar(128) NOT NULL COMMENT '状态描述',
  PRIMARY KEY (`taskstatus_id`),
  UNIQUE KEY `taskstatus_id_UNIQUE` (`taskstatus_id`),
  UNIQUE KEY `taskstatus_code_UNIQUE` (`taskstatus_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务状态表';

#
# Dumping data for table ibs_taskstatus
#

LOCK TABLES `ibs_taskstatus` WRITE;
/*!40000 ALTER TABLE `ibs_taskstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_taskstatus` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_tasktype
#

DROP TABLE IF EXISTS `ibs_tasktype`;
CREATE TABLE `ibs_tasktype` (
  `tasktype_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tasktype_code` int(10) NOT NULL COMMENT '类型码',
  `tasktype_description` varchar(128) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`tasktype_id`),
  UNIQUE KEY `tasktype_id_UNIQUE` (`tasktype_id`),
  UNIQUE KEY `tasktype_code_UNIQUE` (`tasktype_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务类型表';

#
# Dumping data for table ibs_tasktype
#

LOCK TABLES `ibs_tasktype` WRITE;
/*!40000 ALTER TABLE `ibs_tasktype` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibs_tasktype` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table ibs_user
#

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# Dumping data for table ibs_user
#

LOCK TABLES `ibs_user` WRITE;
/*!40000 ALTER TABLE `ibs_user` DISABLE KEYS */;
INSERT INTO `ibs_user` VALUES (2,'admin','管理员','Y2VudGVybQ==',1);
INSERT INTO `ibs_user` VALUES (4,'user','test','Y2VudGVybQ==',1);
INSERT INTO `ibs_user` VALUES (5,'user1','test1','MTIz',1);
INSERT INTO `ibs_user` VALUES (6,'user2','adsfsd','Y2VudGVybQ==',0);
INSERT INTO `ibs_user` VALUES (7,'user3','asdfasdf','Y2VudGVybQ==',1);
/*!40000 ALTER TABLE `ibs_user` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
