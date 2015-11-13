/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : ibs

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2015-07-10 18:17:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ibs_app
-- ----------------------------
DROP TABLE IF EXISTS `ibs_app`;
CREATE TABLE `ibs_app` (
  `app_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `app_name` varchar(128) NOT NULL COMMENT '应用名称',
  `app_version` varchar(32) NOT NULL COMMENT '应用版本',
  `app_status` int(10) NOT NULL COMMENT '应用状态',
  `app_uploadtime` datetime NOT NULL COMMENT '应用上传日期',
  `app_deletetime` datetime NOT NULL COMMENT '应用删除日期',
  `app_filepath` varchar(256) NOT NULL COMMENT '应用路径',
  `app_md5` varchar(32) NOT NULL COMMENT 'MD5校验值',
  `app_iconpath` varchar(256) NOT NULL COMMENT 'app图标路径',
  `app_description` varchar(1024) NOT NULL DEFAULT '',
  PRIMARY KEY (`app_id`),
  UNIQUE KEY `app_id_UNIQUE` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='应用表';

-- ----------------------------
-- Records of ibs_app
-- ----------------------------
INSERT INTO `ibs_app` VALUES ('66', 'PinyinIme.apk', '1.00.00.00', '1', '2015-06-11 11:01:00', '9999-12-31 00:00:00', '\\app\\PinyinIme.apk', '271b8982515adc4c379499452a1d4ba5', 'res\\images\\appIco\\PinyinIme.png', '%description%');
INSERT INTO `ibs_app` VALUES ('67', 'AppNmgnxReportLoss.apk', '1.00.00', '1', '2015-06-15 17:08:47', '9999-12-31 00:00:00', '\\app', 'a420a9a103dcac944cae5d0655141859', 'res\\images\\appIco\\AppNmgnxReportLoss.png', 'AppNmgnxReportLoss');
INSERT INTO `ibs_app` VALUES ('68', 'PinyinIme.apk', '1.00.11', '1', '2015-06-17 09:37:43', '9999-12-31 00:00:00', '\\app', '271b8982515adc4c379499452a1d4ba5', 'res\\images\\appIco\\PinyinIme.png', '输入法');
INSERT INTO `ibs_app` VALUES ('69', 'QQ_244.apk', '1.1', '1', '2015-06-23 15:45:22', '9999-12-31 00:00:00', '\\app', '4b8f02837be1185f434a041b1d7fae95', 'res\\images\\appIco\\QQ_244.png', 'QQ2015');
INSERT INTO `ibs_app` VALUES ('70', 'PinyinIme.apk', '1.00.00.00', '1', '2015-06-23 15:45:33', '9999-12-31 00:00:00', '\\app', '271b8982515adc4c379499452a1d4ba5', 'res\\images\\appIco\\PinyinIme.png', '%description%');

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
) ENGINE=InnoDB AUTO_INCREMENT=592 DEFAULT CHARSET=utf8 COMMENT='user_function映射表';

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
INSERT INTO `ibs_authority` VALUES ('13', '1', '13');
INSERT INTO `ibs_authority` VALUES ('34', '12', '1');
INSERT INTO `ibs_authority` VALUES ('35', '12', '2');
INSERT INTO `ibs_authority` VALUES ('36', '12', '3');
INSERT INTO `ibs_authority` VALUES ('37', '12', '4');
INSERT INTO `ibs_authority` VALUES ('38', '12', '5');
INSERT INTO `ibs_authority` VALUES ('39', '12', '6');
INSERT INTO `ibs_authority` VALUES ('40', '12', '7');
INSERT INTO `ibs_authority` VALUES ('41', '12', '8');
INSERT INTO `ibs_authority` VALUES ('42', '12', '9');
INSERT INTO `ibs_authority` VALUES ('43', '12', '10');
INSERT INTO `ibs_authority` VALUES ('44', '12', '11');
INSERT INTO `ibs_authority` VALUES ('56', '7', '1');
INSERT INTO `ibs_authority` VALUES ('57', '7', '2');
INSERT INTO `ibs_authority` VALUES ('58', '7', '3');
INSERT INTO `ibs_authority` VALUES ('59', '7', '4');
INSERT INTO `ibs_authority` VALUES ('60', '7', '5');
INSERT INTO `ibs_authority` VALUES ('61', '7', '6');
INSERT INTO `ibs_authority` VALUES ('62', '7', '7');
INSERT INTO `ibs_authority` VALUES ('63', '7', '8');
INSERT INTO `ibs_authority` VALUES ('64', '7', '9');
INSERT INTO `ibs_authority` VALUES ('65', '7', '10');
INSERT INTO `ibs_authority` VALUES ('66', '7', '11');
INSERT INTO `ibs_authority` VALUES ('67', '10', '1');
INSERT INTO `ibs_authority` VALUES ('68', '10', '2');
INSERT INTO `ibs_authority` VALUES ('69', '10', '3');
INSERT INTO `ibs_authority` VALUES ('70', '10', '4');
INSERT INTO `ibs_authority` VALUES ('71', '10', '5');
INSERT INTO `ibs_authority` VALUES ('72', '10', '6');
INSERT INTO `ibs_authority` VALUES ('73', '10', '7');
INSERT INTO `ibs_authority` VALUES ('74', '10', '8');
INSERT INTO `ibs_authority` VALUES ('75', '10', '9');
INSERT INTO `ibs_authority` VALUES ('76', '10', '10');
INSERT INTO `ibs_authority` VALUES ('77', '10', '11');
INSERT INTO `ibs_authority` VALUES ('78', '86', '1');
INSERT INTO `ibs_authority` VALUES ('79', '86', '2');
INSERT INTO `ibs_authority` VALUES ('80', '86', '3');
INSERT INTO `ibs_authority` VALUES ('81', '86', '4');
INSERT INTO `ibs_authority` VALUES ('82', '86', '5');
INSERT INTO `ibs_authority` VALUES ('83', '86', '6');
INSERT INTO `ibs_authority` VALUES ('84', '86', '7');
INSERT INTO `ibs_authority` VALUES ('85', '86', '8');
INSERT INTO `ibs_authority` VALUES ('86', '86', '9');
INSERT INTO `ibs_authority` VALUES ('87', '86', '10');
INSERT INTO `ibs_authority` VALUES ('88', '86', '11');
INSERT INTO `ibs_authority` VALUES ('89', '88', '1');
INSERT INTO `ibs_authority` VALUES ('90', '88', '2');
INSERT INTO `ibs_authority` VALUES ('91', '88', '3');
INSERT INTO `ibs_authority` VALUES ('92', '88', '4');
INSERT INTO `ibs_authority` VALUES ('93', '88', '5');
INSERT INTO `ibs_authority` VALUES ('94', '88', '6');
INSERT INTO `ibs_authority` VALUES ('95', '88', '7');
INSERT INTO `ibs_authority` VALUES ('96', '88', '8');
INSERT INTO `ibs_authority` VALUES ('97', '88', '9');
INSERT INTO `ibs_authority` VALUES ('98', '88', '10');
INSERT INTO `ibs_authority` VALUES ('99', '88', '11');
INSERT INTO `ibs_authority` VALUES ('100', '91', '1');
INSERT INTO `ibs_authority` VALUES ('101', '91', '2');
INSERT INTO `ibs_authority` VALUES ('102', '91', '3');
INSERT INTO `ibs_authority` VALUES ('103', '91', '4');
INSERT INTO `ibs_authority` VALUES ('104', '91', '5');
INSERT INTO `ibs_authority` VALUES ('105', '91', '6');
INSERT INTO `ibs_authority` VALUES ('106', '91', '7');
INSERT INTO `ibs_authority` VALUES ('107', '91', '8');
INSERT INTO `ibs_authority` VALUES ('108', '91', '9');
INSERT INTO `ibs_authority` VALUES ('109', '91', '10');
INSERT INTO `ibs_authority` VALUES ('110', '91', '11');
INSERT INTO `ibs_authority` VALUES ('111', '95', '1');
INSERT INTO `ibs_authority` VALUES ('112', '95', '2');
INSERT INTO `ibs_authority` VALUES ('113', '95', '3');
INSERT INTO `ibs_authority` VALUES ('114', '95', '4');
INSERT INTO `ibs_authority` VALUES ('115', '95', '5');
INSERT INTO `ibs_authority` VALUES ('116', '95', '6');
INSERT INTO `ibs_authority` VALUES ('117', '95', '7');
INSERT INTO `ibs_authority` VALUES ('118', '95', '8');
INSERT INTO `ibs_authority` VALUES ('119', '95', '9');
INSERT INTO `ibs_authority` VALUES ('120', '95', '10');
INSERT INTO `ibs_authority` VALUES ('121', '95', '11');
INSERT INTO `ibs_authority` VALUES ('122', '102', '1');
INSERT INTO `ibs_authority` VALUES ('123', '102', '2');
INSERT INTO `ibs_authority` VALUES ('124', '102', '3');
INSERT INTO `ibs_authority` VALUES ('125', '102', '4');
INSERT INTO `ibs_authority` VALUES ('126', '102', '5');
INSERT INTO `ibs_authority` VALUES ('127', '102', '6');
INSERT INTO `ibs_authority` VALUES ('128', '102', '7');
INSERT INTO `ibs_authority` VALUES ('129', '102', '8');
INSERT INTO `ibs_authority` VALUES ('130', '102', '9');
INSERT INTO `ibs_authority` VALUES ('131', '102', '10');
INSERT INTO `ibs_authority` VALUES ('132', '102', '11');
INSERT INTO `ibs_authority` VALUES ('133', '104', '1');
INSERT INTO `ibs_authority` VALUES ('134', '104', '2');
INSERT INTO `ibs_authority` VALUES ('135', '104', '3');
INSERT INTO `ibs_authority` VALUES ('136', '104', '4');
INSERT INTO `ibs_authority` VALUES ('137', '104', '5');
INSERT INTO `ibs_authority` VALUES ('138', '104', '6');
INSERT INTO `ibs_authority` VALUES ('139', '104', '7');
INSERT INTO `ibs_authority` VALUES ('140', '104', '8');
INSERT INTO `ibs_authority` VALUES ('141', '104', '9');
INSERT INTO `ibs_authority` VALUES ('142', '104', '10');
INSERT INTO `ibs_authority` VALUES ('143', '104', '11');
INSERT INTO `ibs_authority` VALUES ('144', '2', '1');
INSERT INTO `ibs_authority` VALUES ('145', '2', '2');
INSERT INTO `ibs_authority` VALUES ('146', '2', '3');
INSERT INTO `ibs_authority` VALUES ('147', '2', '4');
INSERT INTO `ibs_authority` VALUES ('148', '2', '5');
INSERT INTO `ibs_authority` VALUES ('149', '2', '6');
INSERT INTO `ibs_authority` VALUES ('150', '2', '7');
INSERT INTO `ibs_authority` VALUES ('151', '2', '8');
INSERT INTO `ibs_authority` VALUES ('152', '2', '9');
INSERT INTO `ibs_authority` VALUES ('153', '2', '10');
INSERT INTO `ibs_authority` VALUES ('154', '2', '11');
INSERT INTO `ibs_authority` VALUES ('156', '2', '13');
INSERT INTO `ibs_authority` VALUES ('157', '2', '14');
INSERT INTO `ibs_authority` VALUES ('166', '106', '5');
INSERT INTO `ibs_authority` VALUES ('167', '106', '6');
INSERT INTO `ibs_authority` VALUES ('183', '105', '1');
INSERT INTO `ibs_authority` VALUES ('184', '105', '2');
INSERT INTO `ibs_authority` VALUES ('185', '105', '3');
INSERT INTO `ibs_authority` VALUES ('186', '105', '4');
INSERT INTO `ibs_authority` VALUES ('187', '105', '5');
INSERT INTO `ibs_authority` VALUES ('188', '105', '6');
INSERT INTO `ibs_authority` VALUES ('189', '105', '7');
INSERT INTO `ibs_authority` VALUES ('190', '105', '8');
INSERT INTO `ibs_authority` VALUES ('191', '105', '9');
INSERT INTO `ibs_authority` VALUES ('192', '105', '10');
INSERT INTO `ibs_authority` VALUES ('193', '105', '11');
INSERT INTO `ibs_authority` VALUES ('194', '2', '12');
INSERT INTO `ibs_authority` VALUES ('199', '107', '1');
INSERT INTO `ibs_authority` VALUES ('200', '107', '2');
INSERT INTO `ibs_authority` VALUES ('201', '107', '3');
INSERT INTO `ibs_authority` VALUES ('202', '107', '12');
INSERT INTO `ibs_authority` VALUES ('203', '107', '4');
INSERT INTO `ibs_authority` VALUES ('204', '108', '1');
INSERT INTO `ibs_authority` VALUES ('205', '108', '2');
INSERT INTO `ibs_authority` VALUES ('206', '108', '3');
INSERT INTO `ibs_authority` VALUES ('207', '108', '12');
INSERT INTO `ibs_authority` VALUES ('208', '108', '4');
INSERT INTO `ibs_authority` VALUES ('209', '110', '1');
INSERT INTO `ibs_authority` VALUES ('210', '110', '2');
INSERT INTO `ibs_authority` VALUES ('211', '110', '3');
INSERT INTO `ibs_authority` VALUES ('212', '110', '12');
INSERT INTO `ibs_authority` VALUES ('213', '110', '4');
INSERT INTO `ibs_authority` VALUES ('214', '112', '1');
INSERT INTO `ibs_authority` VALUES ('215', '112', '2');
INSERT INTO `ibs_authority` VALUES ('216', '112', '3');
INSERT INTO `ibs_authority` VALUES ('217', '112', '12');
INSERT INTO `ibs_authority` VALUES ('218', '112', '4');
INSERT INTO `ibs_authority` VALUES ('219', '112', '7');
INSERT INTO `ibs_authority` VALUES ('220', '112', '8');
INSERT INTO `ibs_authority` VALUES ('221', '112', '9');
INSERT INTO `ibs_authority` VALUES ('222', '112', '10');
INSERT INTO `ibs_authority` VALUES ('223', '112', '11');
INSERT INTO `ibs_authority` VALUES ('224', '112', '5');
INSERT INTO `ibs_authority` VALUES ('225', '112', '6');
INSERT INTO `ibs_authority` VALUES ('277', '117', '1');
INSERT INTO `ibs_authority` VALUES ('278', '117', '2');
INSERT INTO `ibs_authority` VALUES ('279', '117', '3');
INSERT INTO `ibs_authority` VALUES ('280', '117', '12');
INSERT INTO `ibs_authority` VALUES ('281', '117', '4');
INSERT INTO `ibs_authority` VALUES ('297', '115', '1');
INSERT INTO `ibs_authority` VALUES ('298', '115', '2');
INSERT INTO `ibs_authority` VALUES ('299', '115', '3');
INSERT INTO `ibs_authority` VALUES ('300', '115', '12');
INSERT INTO `ibs_authority` VALUES ('301', '115', '4');
INSERT INTO `ibs_authority` VALUES ('302', '115', '7');
INSERT INTO `ibs_authority` VALUES ('303', '115', '8');
INSERT INTO `ibs_authority` VALUES ('304', '115', '9');
INSERT INTO `ibs_authority` VALUES ('305', '115', '10');
INSERT INTO `ibs_authority` VALUES ('306', '115', '11');
INSERT INTO `ibs_authority` VALUES ('307', '115', '5');
INSERT INTO `ibs_authority` VALUES ('308', '115', '6');
INSERT INTO `ibs_authority` VALUES ('309', '123', '1');
INSERT INTO `ibs_authority` VALUES ('310', '123', '2');
INSERT INTO `ibs_authority` VALUES ('311', '123', '3');
INSERT INTO `ibs_authority` VALUES ('312', '123', '12');
INSERT INTO `ibs_authority` VALUES ('313', '123', '4');
INSERT INTO `ibs_authority` VALUES ('314', '123', '7');
INSERT INTO `ibs_authority` VALUES ('315', '123', '8');
INSERT INTO `ibs_authority` VALUES ('316', '123', '9');
INSERT INTO `ibs_authority` VALUES ('317', '123', '10');
INSERT INTO `ibs_authority` VALUES ('318', '123', '11');
INSERT INTO `ibs_authority` VALUES ('319', '123', '5');
INSERT INTO `ibs_authority` VALUES ('320', '123', '6');
INSERT INTO `ibs_authority` VALUES ('321', '122', '1');
INSERT INTO `ibs_authority` VALUES ('322', '122', '2');
INSERT INTO `ibs_authority` VALUES ('323', '122', '3');
INSERT INTO `ibs_authority` VALUES ('324', '122', '12');
INSERT INTO `ibs_authority` VALUES ('325', '122', '4');
INSERT INTO `ibs_authority` VALUES ('326', '129', '1');
INSERT INTO `ibs_authority` VALUES ('327', '129', '2');
INSERT INTO `ibs_authority` VALUES ('328', '129', '3');
INSERT INTO `ibs_authority` VALUES ('329', '129', '12');
INSERT INTO `ibs_authority` VALUES ('330', '129', '4');
INSERT INTO `ibs_authority` VALUES ('331', '114', '1');
INSERT INTO `ibs_authority` VALUES ('332', '114', '2');
INSERT INTO `ibs_authority` VALUES ('333', '114', '3');
INSERT INTO `ibs_authority` VALUES ('334', '114', '12');
INSERT INTO `ibs_authority` VALUES ('335', '114', '7');
INSERT INTO `ibs_authority` VALUES ('336', '114', '8');
INSERT INTO `ibs_authority` VALUES ('337', '114', '9');
INSERT INTO `ibs_authority` VALUES ('338', '114', '10');
INSERT INTO `ibs_authority` VALUES ('339', '114', '11');
INSERT INTO `ibs_authority` VALUES ('340', '114', '5');
INSERT INTO `ibs_authority` VALUES ('341', '114', '6');
INSERT INTO `ibs_authority` VALUES ('371', '136', '1');
INSERT INTO `ibs_authority` VALUES ('372', '136', '2');
INSERT INTO `ibs_authority` VALUES ('373', '136', '3');
INSERT INTO `ibs_authority` VALUES ('374', '136', '12');
INSERT INTO `ibs_authority` VALUES ('375', '136', '7');
INSERT INTO `ibs_authority` VALUES ('376', '136', '8');
INSERT INTO `ibs_authority` VALUES ('377', '136', '9');
INSERT INTO `ibs_authority` VALUES ('378', '136', '10');
INSERT INTO `ibs_authority` VALUES ('379', '136', '11');
INSERT INTO `ibs_authority` VALUES ('380', '136', '5');
INSERT INTO `ibs_authority` VALUES ('381', '136', '6');
INSERT INTO `ibs_authority` VALUES ('382', '137', '1');
INSERT INTO `ibs_authority` VALUES ('383', '137', '2');
INSERT INTO `ibs_authority` VALUES ('384', '137', '3');
INSERT INTO `ibs_authority` VALUES ('385', '137', '12');
INSERT INTO `ibs_authority` VALUES ('386', '137', '7');
INSERT INTO `ibs_authority` VALUES ('387', '137', '8');
INSERT INTO `ibs_authority` VALUES ('388', '137', '9');
INSERT INTO `ibs_authority` VALUES ('389', '137', '10');
INSERT INTO `ibs_authority` VALUES ('390', '137', '11');
INSERT INTO `ibs_authority` VALUES ('391', '137', '5');
INSERT INTO `ibs_authority` VALUES ('392', '137', '6');
INSERT INTO `ibs_authority` VALUES ('393', '138', '1');
INSERT INTO `ibs_authority` VALUES ('394', '138', '2');
INSERT INTO `ibs_authority` VALUES ('395', '138', '3');
INSERT INTO `ibs_authority` VALUES ('396', '138', '12');
INSERT INTO `ibs_authority` VALUES ('397', '138', '7');
INSERT INTO `ibs_authority` VALUES ('398', '138', '8');
INSERT INTO `ibs_authority` VALUES ('399', '138', '9');
INSERT INTO `ibs_authority` VALUES ('400', '138', '10');
INSERT INTO `ibs_authority` VALUES ('401', '138', '11');
INSERT INTO `ibs_authority` VALUES ('402', '138', '5');
INSERT INTO `ibs_authority` VALUES ('403', '138', '6');
INSERT INTO `ibs_authority` VALUES ('404', '132', '1');
INSERT INTO `ibs_authority` VALUES ('405', '132', '2');
INSERT INTO `ibs_authority` VALUES ('406', '132', '3');
INSERT INTO `ibs_authority` VALUES ('407', '132', '12');
INSERT INTO `ibs_authority` VALUES ('408', '132', '7');
INSERT INTO `ibs_authority` VALUES ('409', '132', '8');
INSERT INTO `ibs_authority` VALUES ('410', '132', '9');
INSERT INTO `ibs_authority` VALUES ('411', '132', '10');
INSERT INTO `ibs_authority` VALUES ('412', '132', '11');
INSERT INTO `ibs_authority` VALUES ('413', '132', '5');
INSERT INTO `ibs_authority` VALUES ('414', '132', '6');
INSERT INTO `ibs_authority` VALUES ('477', '141', '5');
INSERT INTO `ibs_authority` VALUES ('478', '141', '6');
INSERT INTO `ibs_authority` VALUES ('479', '140', '1');
INSERT INTO `ibs_authority` VALUES ('480', '140', '2');
INSERT INTO `ibs_authority` VALUES ('481', '140', '3');
INSERT INTO `ibs_authority` VALUES ('482', '140', '12');
INSERT INTO `ibs_authority` VALUES ('487', '214', '1');
INSERT INTO `ibs_authority` VALUES ('488', '214', '2');
INSERT INTO `ibs_authority` VALUES ('489', '214', '3');
INSERT INTO `ibs_authority` VALUES ('490', '214', '12');
INSERT INTO `ibs_authority` VALUES ('491', '214', '7');
INSERT INTO `ibs_authority` VALUES ('492', '214', '8');
INSERT INTO `ibs_authority` VALUES ('493', '214', '9');
INSERT INTO `ibs_authority` VALUES ('494', '214', '10');
INSERT INTO `ibs_authority` VALUES ('495', '214', '11');
INSERT INTO `ibs_authority` VALUES ('496', '156', '1');
INSERT INTO `ibs_authority` VALUES ('497', '156', '2');
INSERT INTO `ibs_authority` VALUES ('498', '156', '3');
INSERT INTO `ibs_authority` VALUES ('499', '156', '12');
INSERT INTO `ibs_authority` VALUES ('500', '156', '7');
INSERT INTO `ibs_authority` VALUES ('501', '156', '8');
INSERT INTO `ibs_authority` VALUES ('502', '156', '9');
INSERT INTO `ibs_authority` VALUES ('503', '156', '10');
INSERT INTO `ibs_authority` VALUES ('504', '156', '11');
INSERT INTO `ibs_authority` VALUES ('505', '161', '1');
INSERT INTO `ibs_authority` VALUES ('506', '161', '2');
INSERT INTO `ibs_authority` VALUES ('507', '161', '3');
INSERT INTO `ibs_authority` VALUES ('508', '161', '12');
INSERT INTO `ibs_authority` VALUES ('518', '217', '1');
INSERT INTO `ibs_authority` VALUES ('519', '217', '2');
INSERT INTO `ibs_authority` VALUES ('520', '217', '3');
INSERT INTO `ibs_authority` VALUES ('521', '217', '12');
INSERT INTO `ibs_authority` VALUES ('522', '162', '1');
INSERT INTO `ibs_authority` VALUES ('523', '162', '2');
INSERT INTO `ibs_authority` VALUES ('524', '162', '3');
INSERT INTO `ibs_authority` VALUES ('525', '162', '12');
INSERT INTO `ibs_authority` VALUES ('526', '218', '1');
INSERT INTO `ibs_authority` VALUES ('527', '218', '2');
INSERT INTO `ibs_authority` VALUES ('528', '218', '3');
INSERT INTO `ibs_authority` VALUES ('529', '218', '12');
INSERT INTO `ibs_authority` VALUES ('530', '178', '1');
INSERT INTO `ibs_authority` VALUES ('531', '178', '2');
INSERT INTO `ibs_authority` VALUES ('532', '178', '3');
INSERT INTO `ibs_authority` VALUES ('533', '178', '12');
INSERT INTO `ibs_authority` VALUES ('538', '219', '1');
INSERT INTO `ibs_authority` VALUES ('539', '219', '2');
INSERT INTO `ibs_authority` VALUES ('540', '219', '3');
INSERT INTO `ibs_authority` VALUES ('541', '219', '12');
INSERT INTO `ibs_authority` VALUES ('542', '220', '1');
INSERT INTO `ibs_authority` VALUES ('543', '220', '2');
INSERT INTO `ibs_authority` VALUES ('544', '220', '3');
INSERT INTO `ibs_authority` VALUES ('545', '220', '12');
INSERT INTO `ibs_authority` VALUES ('546', '220', '7');
INSERT INTO `ibs_authority` VALUES ('547', '220', '8');
INSERT INTO `ibs_authority` VALUES ('548', '220', '9');
INSERT INTO `ibs_authority` VALUES ('549', '220', '10');
INSERT INTO `ibs_authority` VALUES ('550', '220', '11');
INSERT INTO `ibs_authority` VALUES ('551', '221', '1');
INSERT INTO `ibs_authority` VALUES ('552', '221', '2');
INSERT INTO `ibs_authority` VALUES ('553', '221', '3');
INSERT INTO `ibs_authority` VALUES ('554', '221', '12');
INSERT INTO `ibs_authority` VALUES ('555', '207', '1');
INSERT INTO `ibs_authority` VALUES ('556', '207', '2');
INSERT INTO `ibs_authority` VALUES ('557', '207', '3');
INSERT INTO `ibs_authority` VALUES ('558', '207', '12');
INSERT INTO `ibs_authority` VALUES ('559', '208', '7');
INSERT INTO `ibs_authority` VALUES ('560', '208', '8');
INSERT INTO `ibs_authority` VALUES ('561', '208', '9');
INSERT INTO `ibs_authority` VALUES ('562', '208', '10');
INSERT INTO `ibs_authority` VALUES ('563', '208', '11');
INSERT INTO `ibs_authority` VALUES ('579', '197', '1');
INSERT INTO `ibs_authority` VALUES ('580', '197', '2');
INSERT INTO `ibs_authority` VALUES ('581', '197', '3');
INSERT INTO `ibs_authority` VALUES ('582', '197', '12');
INSERT INTO `ibs_authority` VALUES ('583', '512', '1');
INSERT INTO `ibs_authority` VALUES ('584', '512', '2');
INSERT INTO `ibs_authority` VALUES ('585', '512', '3');
INSERT INTO `ibs_authority` VALUES ('586', '512', '12');
INSERT INTO `ibs_authority` VALUES ('587', '512', '7');
INSERT INTO `ibs_authority` VALUES ('588', '512', '8');
INSERT INTO `ibs_authority` VALUES ('589', '512', '9');
INSERT INTO `ibs_authority` VALUES ('590', '512', '10');
INSERT INTO `ibs_authority` VALUES ('591', '512', '11');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='交易表';

-- ----------------------------
-- Records of ibs_business
-- ----------------------------
INSERT INTO `ibs_business` VALUES ('1', '-1', '个人业务', '1', '0', '-1', '个人业务', '0');
INSERT INTO `ibs_business` VALUES ('2', '-1', '企业业务', '1', '0', '-1', '企业业务', '0');
INSERT INTO `ibs_business` VALUES ('3', '2015', 'QQ', '0', '1', '-1', 'QQ', '69');
INSERT INTO `ibs_business` VALUES ('4', '1', '个人开户', '0', '1', '-1', '个人开户', '70');

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
 PARTITION part_201507 VALUES LESS THAN (736176) ENGINE = InnoDB) */;

-- ----------------------------
-- Records of ibs_businessrecord
-- ----------------------------
INSERT INTO `ibs_businessrecord` VALUES ('1', '2015-01-01 00:00:00', '1', '1', '1', '123', '123');

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
) ENGINE=InnoDB AUTO_INCREMENT=660004 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (YEAR(businessstatistics_date))
(PARTITION part_2014 VALUES LESS THAN (2015) ENGINE = InnoDB,
 PARTITION part_2015 VALUES LESS THAN (2016) ENGINE = InnoDB) */;

-- ----------------------------
-- Records of ibs_businessstatistics
-- ----------------------------
INSERT INTO `ibs_businessstatistics` VALUES ('1', '2014-05-16', '1', '3', '1', '40');
INSERT INTO `ibs_businessstatistics` VALUES ('2', '2014-05-28', '2', '4', '1', '22');
INSERT INTO `ibs_businessstatistics` VALUES ('3', '2014-05-17', '3', '3', '1', '1');
INSERT INTO `ibs_businessstatistics` VALUES ('4', '2014-05-18', '3', '4', '1', '23');
INSERT INTO `ibs_businessstatistics` VALUES ('5', '2014-05-19', '1', '3', '2', '12');
INSERT INTO `ibs_businessstatistics` VALUES ('660003', '2014-05-10', '1', '3', '1', '40');
INSERT INTO `ibs_businessstatistics` VALUES ('1', '2015-04-04', '1', '1', '1', '12');
INSERT INTO `ibs_businessstatistics` VALUES ('2', '2015-04-05', '2', '1', '1', '13');
INSERT INTO `ibs_businessstatistics` VALUES ('3', '2015-04-06', '2', '1', '1', '15');
INSERT INTO `ibs_businessstatistics` VALUES ('4', '2015-04-07', '1', '1', '1', '12');
INSERT INTO `ibs_businessstatistics` VALUES ('5', '2015-04-08', '1', '1', '1', '11');
INSERT INTO `ibs_businessstatistics` VALUES ('6', '2015-05-10', '1', '1', '1', '13');
INSERT INTO `ibs_businessstatistics` VALUES ('7', '2015-05-10', '3', '1', '2', '20');
INSERT INTO `ibs_businessstatistics` VALUES ('8', '2015-05-10', '2', '2', '2', '22');
INSERT INTO `ibs_businessstatistics` VALUES ('9', '2015-05-11', '3', '2', '1', '15');
INSERT INTO `ibs_businessstatistics` VALUES ('10', '2015-05-11', '3', '2', '1', '12');
INSERT INTO `ibs_businessstatistics` VALUES ('11', '2015-05-11', '3', '2', '1', '10');
INSERT INTO `ibs_businessstatistics` VALUES ('12', '2015-05-11', '3', '2', '1', '1');
INSERT INTO `ibs_businessstatistics` VALUES ('13', '2015-05-12', '2', '1', '2', '12');
INSERT INTO `ibs_businessstatistics` VALUES ('14', '2015-05-13', '3', '1', '1', '11');
INSERT INTO `ibs_businessstatistics` VALUES ('15', '2015-05-14', '1', '1', '1', '13');
INSERT INTO `ibs_businessstatistics` VALUES ('16', '2015-05-15', '3', '2', '1', '113');
INSERT INTO `ibs_businessstatistics` VALUES ('17', '2015-05-16', '3', '2', '1', '1');
INSERT INTO `ibs_businessstatistics` VALUES ('18', '2015-05-17', '3', '2', '2', '1');
INSERT INTO `ibs_businessstatistics` VALUES ('19', '2015-05-18', '1', '70', '1', '1');
INSERT INTO `ibs_businessstatistics` VALUES ('20', '2015-04-19', '1', '70', '1', '22');
INSERT INTO `ibs_businessstatistics` VALUES ('21', '2015-05-19', '1', '70', '1', '50');
INSERT INTO `ibs_businessstatistics` VALUES ('22', '2015-05-20', '1', '70', '1', '88');
INSERT INTO `ibs_businessstatistics` VALUES ('23', '2015-05-21', '1', '70', '1', '23');
INSERT INTO `ibs_businessstatistics` VALUES ('24', '2015-05-27', '1', '70', '1', '79');
INSERT INTO `ibs_businessstatistics` VALUES ('25', '2015-06-01', '1', '70', '1', '14');
INSERT INTO `ibs_businessstatistics` VALUES ('26', '2015-06-10', '1', '70', '1', '77');
INSERT INTO `ibs_businessstatistics` VALUES ('27', '2015-06-09', '1', '70', '1', '1');
INSERT INTO `ibs_businessstatistics` VALUES ('28', '2015-06-11', '1', '70', '1', '1');
INSERT INTO `ibs_businessstatistics` VALUES ('29', '2015-05-13', '1', '70', '1', '1');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ibs_customer
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='设备表';

-- ----------------------------
-- Records of ibs_device
-- ----------------------------
INSERT INTO `ibs_device` VALUES ('1', '001', '1', '1', '1', '1', '1,3_52,2_55', '66,69,4');
INSERT INTO `ibs_device` VALUES ('2', '002', '1', '1', '1', '1', '1,3_52,2_55', '66,69,4');
INSERT INTO `ibs_device` VALUES ('3', '003', '2', '2', '1', '1', '1,3_52,2_55', '66,69,4');

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
INSERT INTO `ibs_devicestatus` VALUES ('2', '2', '下线');

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
INSERT INTO `ibs_devicetype` VALUES ('3', 'E10-3', 'E10-3');

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
  PRIMARY KEY (`firmware_id`),
  UNIQUE KEY `firmware_id_UNIQUE` (`firmware_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='固件表';

-- ----------------------------
-- Records of ibs_firmware
-- ----------------------------
INSERT INTO `ibs_firmware` VALUES ('1', '打印机固件', '1.00.00.00', '1.00.00.00', '1', '0', '2015-06-01 09:55:04', '2015-06-01 09:55:08', ' ', ' ');
INSERT INTO `ibs_firmware` VALUES ('2', 'drawecard.jpg', '1.2', '1.1', '1', '0', '2015-06-04 16:48:40', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\drawecard.jpg', '6fde42eb411e0e0e04aa62719635a98e');
INSERT INTO `ibs_firmware` VALUES ('3', 'FeiQ.1060559168.exe', '%version%', '%lastVersion%', '9', '0', '2015-06-04 16:49:13', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\FeiQ.1060559168.exe', 'bfea3e87b66b4164badd346de8172a36');
INSERT INTO `ibs_firmware` VALUES ('4', 'drawecard.jpg', '1.2', '1.1', '9', '0', '2015-06-04 16:51:07', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\drawecard.jpg', '6fde42eb411e0e0e04aa62719635a98e');
INSERT INTO `ibs_firmware` VALUES ('5', 'drawecard.jpg', '1.2', '1.1', '10', '0', '2015-06-04 17:02:43', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\drawecard.jpg', '6fde42eb411e0e0e04aa62719635a98e');
INSERT INTO `ibs_firmware` VALUES ('6', 'drawecard.jpg', '1.2', '1.1', '10', '0', '2015-06-04 17:02:43', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-04\\drawecard.jpg', '6fde42eb411e0e0e04aa62719635a98e');
INSERT INTO `ibs_firmware` VALUES ('7', '1.dat', '1.0', '1.0', '11', '0', '2015-06-05 09:27:42', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('8', '2.dat', '1.0', '%lastVersion%', '12', '0', '2015-06-05 09:31:28', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\2.dat', '0efd8f86d7099b39b2fae38cd704b4c5');
INSERT INTO `ibs_firmware` VALUES ('9', 'temp.dat', '1.1', '1.0', '13', '0', '2015-06-05 09:34:14', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\temp.dat', '6a89cd1c17db0b36a34c917b1fa37d8b');
INSERT INTO `ibs_firmware` VALUES ('10', '1.dat', '1.0', '%lastVersion%', '14', '0', '2015-06-05 09:43:03', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('11', '1.dat', '1.1', '1.0', '14', '0', '2015-06-05 09:44:13', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('12', '1.dat', '1.0', '%lastVersion%', '14', '0', '2015-06-05 09:46:45', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('13', '1.dat', '1.1', '1.0', '14', '0', '2015-06-05 09:46:58', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('14', '1.dat', '1.0', '%lastVersion%', '15', '0', '2015-06-05 09:51:13', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('15', '2.dat', '1.0', '%lastVersion%', '16', '0', '2015-06-05 09:51:27', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\2.dat', '0efd8f86d7099b39b2fae38cd704b4c5');
INSERT INTO `ibs_firmware` VALUES ('16', '2.dat', '1.0', '%lastVersion%', '16', '0', '2015-06-05 09:51:27', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\upload\\firmware\\2015-06-05\\2.dat', '0efd8f86d7099b39b2fae38cd704b4c5');
INSERT INTO `ibs_firmware` VALUES ('17', 'FeiQ.1060559168.exe', '%version%', '%lastVersion%', '17', '0', '2015-06-10 10:46:36', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\ftp\\firmware\\FeiQ.1060559168.exe', 'bfea3e87b66b4164badd346de8172a36');
INSERT INTO `ibs_firmware` VALUES ('18', '1.dat', '1.0', '%lastVersion%', '18', '0', '2015-06-10 10:46:43', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\ftp\\firmware\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('19', '1.dat', '1.1', '1.0', '18', '0', '2015-06-10 10:46:51', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\ftp\\firmware\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('20', 'drawecard.jpg', '1.2', '1.1', '1', '0', '2015-06-10 15:57:11', '9999-12-31 00:00:00', 'E:\\CodeTools\\apache-tomcat-7.0.54\\webapps\\IntelligentBusinessSystem\\ftp\\firmware\\drawecard.jpg', '6fde42eb411e0e0e04aa62719635a98e');
INSERT INTO `ibs_firmware` VALUES ('21', 'drawecard.jpg', '1.2', '1.1', '19', '0', '2015-06-11 13:52:57', '9999-12-31 00:00:00', '\\firmware\\drawecard.jpg', '6fde42eb411e0e0e04aa62719635a98e');
INSERT INTO `ibs_firmware` VALUES ('22', 'FeiQ.1060559168.exe', '%version%', '%lastVersion%', '20', '0', '2015-06-11 13:53:56', '9999-12-31 00:00:00', '\\firmware\\FeiQ.1060559168.exe', 'bfea3e87b66b4164badd346de8172a36');
INSERT INTO `ibs_firmware` VALUES ('23', 'drawecard.jpg', '1.2', '1.1', '21', '0', '2015-06-11 13:57:22', '9999-12-31 00:00:00', '\\firmware\\drawecard.jpg', '6fde42eb411e0e0e04aa62719635a98e');
INSERT INTO `ibs_firmware` VALUES ('24', 'FeiQ.1060559168.exe', '%version%', '%lastVersion%', '22', '0', '2015-06-11 13:58:19', '9999-12-31 00:00:00', '\\firmware\\FeiQ.1060559168.exe', 'bfea3e87b66b4164badd346de8172a36');
INSERT INTO `ibs_firmware` VALUES ('25', 'drawecard.jpg', '1.2', '1.1', '23', '0', '2015-06-11 14:03:20', '9999-12-31 00:00:00', '\\firmware\\drawecard.jpg', '6fde42eb411e0e0e04aa62719635a98e');
INSERT INTO `ibs_firmware` VALUES ('26', 'FeiQ.1060559168.exe', '%version%', '%lastVersion%', '24', '0', '2015-06-11 14:09:11', '9999-12-31 00:00:00', '\\firmware\\FeiQ.1060559168.exe', 'bfea3e87b66b4164badd346de8172a36');
INSERT INTO `ibs_firmware` VALUES ('27', '1.dat', '1.0', '%lastVersion%', '25', '0', '2015-06-11 14:09:19', '9999-12-31 00:00:00', '\\firmware\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('28', '1.dat', '1.1', '1.0', '25', '0', '2015-06-11 14:09:42', '9999-12-31 00:00:00', '\\firmware\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('29', '2.dat', '1.0', '%lastVersion%', '26', '0', '2015-06-11 14:09:49', '9999-12-31 00:00:00', '\\firmware\\2.dat', '0efd8f86d7099b39b2fae38cd704b4c5');
INSERT INTO `ibs_firmware` VALUES ('30', '1.dat', '1.0', '%lastVersion%', '27', '0', '2015-06-11 14:09:56', '9999-12-31 00:00:00', '\\firmware\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('31', 'drawecard.jpg', '1.2', '1.1', '28', '0', '2015-06-11 14:33:41', '9999-12-31 00:00:00', '\\firmware\\drawecard.jpg', '6fde42eb411e0e0e04aa62719635a98e');
INSERT INTO `ibs_firmware` VALUES ('32', '2.dat', '1.0', '%lastVersion%', '29', '0', '2015-06-11 14:33:55', '9999-12-31 00:00:00', '\\firmware\\2.dat', '0efd8f86d7099b39b2fae38cd704b4c5');
INSERT INTO `ibs_firmware` VALUES ('33', '1.dat', '1.1', '1.0', '30', '0', '2015-06-11 17:58:38', '9999-12-31 00:00:00', '\\firmware\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('34', '1.dat', '1.0', '%lastVersion%', '28', '0', '2015-06-11 18:03:33', '9999-12-31 00:00:00', '\\firmware\\1.dat', '0d4baf3e5b5ace5b3b5f35d14429e06e');
INSERT INTO `ibs_firmware` VALUES ('35', '2.dat', '1.0', '%lastVersion%', '31', '0', '2015-06-11 18:03:43', '9999-12-31 00:00:00', '\\firmware\\2.dat', '0efd8f86d7099b39b2fae38cd704b4c5');
INSERT INTO `ibs_firmware` VALUES ('36', '1.dat', '1.00.00', '%lastVersion%', '32', '0', '2015-06-12 10:45:28', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('37', '1.dat', '1.00.00', '%lastVersion%', '33', '0', '2015-06-12 10:53:52', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('38', 'FeiQ.1060559168.exe', '%version%', '%lastVersion%', '34', '0', '2015-06-12 14:21:49', '9999-12-31 00:00:00', '\\firmware', 'BFEA3E87B66B4164BADD346DE8172A36');
INSERT INTO `ibs_firmware` VALUES ('39', 'drawecard.jpg', '1.2', '1.1', '35', '0', '2015-06-12 14:21:55', '9999-12-31 00:00:00', '\\firmware', '6FDE42EB411E0E0E04AA62719635A98E');
INSERT INTO `ibs_firmware` VALUES ('40', '1.dat', '1.0', '%lastVersion%', '36', '0', '2015-06-12 14:22:04', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('41', '1.dat', '1.1', '1.0', '36', '0', '2015-06-12 14:22:11', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('42', '1.dat', '1.0', '%lastVersion%', '35', '0', '2015-06-12 14:22:18', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('43', '1.dat', '1.0', '%lastVersion%', '36', '0', '2015-06-12 14:22:53', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('44', 'drawecard.jpg', '1.2', '1.1', '37', '0', '2015-06-15 14:37:10', '9999-12-31 00:00:00', '\\firmware', '6FDE42EB411E0E0E04AA62719635A98E');
INSERT INTO `ibs_firmware` VALUES ('45', 'FeiQ.1060559168.exe', '%version%', '%lastVersion%', '38', '0', '2015-06-15 14:37:22', '9999-12-31 00:00:00', '\\firmware', 'BFEA3E87B66B4164BADD346DE8172A36');
INSERT INTO `ibs_firmware` VALUES ('46', '1.dat', '1.0', '%lastVersion%', '39', '0', '2015-06-15 14:37:42', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('47', '1.dat', '1.1', '1.0', '39', '0', '2015-06-15 14:37:48', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('48', '1.dat', '1.0', '%lastVersion%', '37', '0', '2015-06-15 14:37:54', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('49', 'drawecard.jpg', '1.2', '1.1', '40', '0', '2015-06-15 14:59:31', '9999-12-31 00:00:00', '\\firmware', '6FDE42EB411E0E0E04AA62719635A98E');
INSERT INTO `ibs_firmware` VALUES ('50', '1.dat', '1.0', '%lastVersion%', '41', '0', '2015-06-15 14:59:43', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('51', '1.dat', '1.1', '1.0', '41', '0', '2015-06-15 14:59:54', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('52', '1.dat', '1.0', '%lastVersion%', '41', '1', '2015-06-15 15:05:14', '9999-12-31 00:00:00', '\\firmware', '0D4BAF3E5B5ACE5B3B5F35D14429E06E');
INSERT INTO `ibs_firmware` VALUES ('53', '11.dat', '1.2', '1.1', '42', '0', '2015-06-15 15:06:45', '9999-12-31 00:00:00', '\\firmware', '76767472AA08E9E07B8F1E51A5F97B61');
INSERT INTO `ibs_firmware` VALUES ('54', '11.dat', '1.2', '%lastVersion%', '43', '0', '2015-06-15 15:07:56', '9999-12-31 00:00:00', '\\firmware', '76767472AA08E9E07B8F1E51A5F97B61');
INSERT INTO `ibs_firmware` VALUES ('55', '13.dat', '1.3', '%lastVersion%', '43', '1', '2015-06-15 15:11:17', '9999-12-31 00:00:00', '\\firmware', '4E2AD3798139E2A8D9B7C1E616043746');

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
  `firmwaretype_code` int(10) NOT NULL COMMENT '类型码',
  `firmwaretype_name` varchar(64) NOT NULL COMMENT '类型名',
  `firmwaretype_version` varchar(32) NOT NULL COMMENT '类型版本',
  `firmwaretype_updatetime` datetime NOT NULL COMMENT '更新日期',
  `firmwaretype_description` varchar(1024) NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_id_UNIQUE` (`firmwaretype_id`),
  UNIQUE KEY `firmwaretype_code_UNIQUE` (`firmwaretype_code`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='固件类型表';

-- ----------------------------
-- Records of ibs_firmwaretype
-- ----------------------------
INSERT INTO `ibs_firmwaretype` VALUES ('41', '3', 'PRINTER', '1.0', '2015-06-15 15:05:14', 'PRINTER%PRINTER%PRINTER');
INSERT INTO `ibs_firmwaretype` VALUES ('43', '2', 'PRINTER', '1.3', '2015-06-15 15:11:17', '打印机更新打印机更新打印机更新%打印机更新打印机更新打印机更新');

-- ----------------------------
-- Table structure for ibs_function
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
INSERT INTO `ibs_function` VALUES ('14', '网点管理', '0', '5', '14', 'system/outlets/main/toUI.action', ' res/css/zTreeStyle/img/diy/iconUser.png', 'ui-layout-box');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='网点表';

-- ----------------------------
-- Records of ibs_outlets
-- ----------------------------
INSERT INTO `ibs_outlets` VALUES ('1', '1001', '仓山网点', '0', '0', '1', '', '66,69,4', '000000');
INSERT INTO `ibs_outlets` VALUES ('2', '1002', '鼓楼网点', '0', '0', '2', '', '66,69,4', '000000');
INSERT INTO `ibs_outlets` VALUES ('3', '000002', '马尾网点', '0', '0', '3', ' ', '66,69,4', '000000');

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
INSERT INTO `ibs_serverinfo` VALUES ('1', '2015-03-20 17:44:54', '2015-07-10 17:40:14', '2015-06-22 23:59:59');

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
  `user_uid` varchar(64) NOT NULL COMMENT '用户账号',
  `user_name` varchar(256) NOT NULL COMMENT '用户昵称',
  `user_password` varchar(256) NOT NULL COMMENT '用户密码',
  `user_isadmin` int(1) NOT NULL COMMENT '是否管理员',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_uid_UNIQUE` (`user_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=529 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of ibs_user
-- ----------------------------
INSERT INTO `ibs_user` VALUES ('2', 'admin', '陈玲', 'Y2VudGVybQ==', '1');
INSERT INTO `ibs_user` VALUES ('115', 'ceshi1', 'ceshi1', 'Nzg2MTg1', '0');
INSERT INTO `ibs_user` VALUES ('120', 'ggg', 'fff', 'ZmZmZg==', '0');
INSERT INTO `ibs_user` VALUES ('145', 'nany', 'nany', 'MjA0MTY4', '0');
INSERT INTO `ibs_user` VALUES ('146', 'hathway', '11', 'ODg4ODg4', '0');
INSERT INTO `ibs_user` VALUES ('147', 'test', 'test123456', 'NzIxMDg4', '0');
INSERT INTO `ibs_user` VALUES ('149', '11', '11', 'MTE=', '0');
INSERT INTO `ibs_user` VALUES ('150', '1111', '1', 'MQ==', '0');
INSERT INTO `ibs_user` VALUES ('151', '111', '1', 'MQ==', '0');
INSERT INTO `ibs_user` VALUES ('152', '1111111', '1', 'MQ==', '0');
INSERT INTO `ibs_user` VALUES ('153', '22', '2', 'dnZ2Mg==', '0');
INSERT INTO `ibs_user` VALUES ('161', 'f', 'f', 'ODg4ODg4', '0');
INSERT INTO `ibs_user` VALUES ('162', 'j', 'j', 'ag==', '0');
INSERT INTO `ibs_user` VALUES ('167', 'g', 'g', 'Zw==', '0');
INSERT INTO `ibs_user` VALUES ('168', 'hf', 'h', 'aA==', '0');
INSERT INTO `ibs_user` VALUES ('169', 'hgf', 'd', 'ZA==', '0');
INSERT INTO `ibs_user` VALUES ('170', 'c', 'c', 'Yw==', '0');
INSERT INTO `ibs_user` VALUES ('181', 's', 's', 'cw==', '0');
INSERT INTO `ibs_user` VALUES ('188', 'qqq', 'q', 'MDg0MTUy', '0');
INSERT INTO `ibs_user` VALUES ('207', 'dddd', 'd', 'ZA==', '0');
INSERT INTO `ibs_user` VALUES ('208', 'dddddd', 'd', 'ZA==', '0');
INSERT INTO `ibs_user` VALUES ('209', 'dddddddd', 'dd', 'ZA==', '0');
INSERT INTO `ibs_user` VALUES ('210', 'www', 'w', 'dw==', '0');
INSERT INTO `ibs_user` VALUES ('211', 'dddvv', 'd', 'ZA==', '0');
INSERT INTO `ibs_user` VALUES ('212', 'vc', 'vc', 'dmM=', '0');
INSERT INTO `ibs_user` VALUES ('213', 'xzx', 'zz', 'eg==', '0');
INSERT INTO `ibs_user` VALUES ('215', 'd', 'd', 'ZA==', '0');
INSERT INTO `ibs_user` VALUES ('216', 'ssdd', 'ssdd123', 'MTIz', '0');
INSERT INTO `ibs_user` VALUES ('511', 'admin2', 'admin2', 'Y2VudGVybQ==', '1');
INSERT INTO `ibs_user` VALUES ('512', 'test3', 'test3', 'MTIzNDU2', '0');
INSERT INTO `ibs_user` VALUES ('517', 'CHENLING', 'CHENLING', 'Q0hFTkxJTkc=', '0');
INSERT INTO `ibs_user` VALUES ('522', '夏天的太阳真是受不了', '受不了', 'MTIzNDU2', '0');
INSERT INTO `ibs_user` VALUES ('523', '1111111111111111', '111111111111111111', 'MTExMTExMTExMTExMTExMTExMTE=', '0');
INSERT INTO `ibs_user` VALUES ('525', '8888888888888888888888888888888888888888', '88888888888888888888', 'ODg4ODg4ODg4ODg4ODg4ODg4ODg=', '0');
INSERT INTO `ibs_user` VALUES ('526', '夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏夏下', '夏夏夏夏夏夏夏夏夏夏', 'MjAyMDIwMjAyMDIwMDAwMDAwMDA=', '0');
INSERT INTO `ibs_user` VALUES ('527', 'kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk', 'kkkkkkkkkkkkkkkkkkkk', 'a2tra2tra2tra2tra2tra2tra2s=', '0');
INSERT INTO `ibs_user` VALUES ('528', 'de124g到底v', '11111wwww222无法', 'MTExMTExMTExMTExMTExMTExMTE=', '0');

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
