/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : spring

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-08-29 08:21:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for security_pr
-- ----------------------------
DROP TABLE IF EXISTS `security_pr`;
CREATE TABLE `security_pr` (
  `role_id` int(11) NOT NULL,
  `power_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`power_id`),
  KEY `FKcnn6tgbmcsd5hvf7k9p4onmnx` (`power_id`),
  CONSTRAINT `FKcnn6tgbmcsd5hvf7k9p4onmnx` FOREIGN KEY (`power_id`) REFERENCES `securitypower` (`id`),
  CONSTRAINT `FKi00ygd9aexmocef2m6hpxnhiu` FOREIGN KEY (`role_id`) REFERENCES `securityrole` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_pr
-- ----------------------------
INSERT INTO `security_pr` VALUES ('2', '7');
INSERT INTO `security_pr` VALUES ('3', '7');
INSERT INTO `security_pr` VALUES ('2', '8');
INSERT INTO `security_pr` VALUES ('3', '8');
INSERT INTO `security_pr` VALUES ('2', '10');
INSERT INTO `security_pr` VALUES ('3', '10');
INSERT INTO `security_pr` VALUES ('2', '11');
INSERT INTO `security_pr` VALUES ('3', '11');
INSERT INTO `security_pr` VALUES ('4', '11');
INSERT INTO `security_pr` VALUES ('2', '13');
INSERT INTO `security_pr` VALUES ('3', '13');
INSERT INTO `security_pr` VALUES ('2', '14');
INSERT INTO `security_pr` VALUES ('3', '14');
INSERT INTO `security_pr` VALUES ('4', '14');
INSERT INTO `security_pr` VALUES ('2', '15');
INSERT INTO `security_pr` VALUES ('3', '15');
INSERT INTO `security_pr` VALUES ('1', '16');
INSERT INTO `security_pr` VALUES ('1', '17');
INSERT INTO `security_pr` VALUES ('1', '18');

-- ----------------------------
-- Table structure for security_ur
-- ----------------------------
DROP TABLE IF EXISTS `security_ur`;
CREATE TABLE `security_ur` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK9o1yercyds6pv5lu3ym5h4h03` (`role_id`),
  CONSTRAINT `FK9o1yercyds6pv5lu3ym5h4h03` FOREIGN KEY (`role_id`) REFERENCES `securityrole` (`id`),
  CONSTRAINT `FKokxk209rsry5rujuquybq28to` FOREIGN KEY (`user_id`) REFERENCES `securityuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_ur
-- ----------------------------
INSERT INTO `security_ur` VALUES ('1', '1');
INSERT INTO `security_ur` VALUES ('2', '2');
INSERT INTO `security_ur` VALUES ('3', '3');
INSERT INTO `security_ur` VALUES ('4', '4');
INSERT INTO `security_ur` VALUES ('5', '4');
INSERT INTO `security_ur` VALUES ('24', '4');

-- ----------------------------
-- Table structure for securitypower
-- ----------------------------
DROP TABLE IF EXISTS `securitypower`;
CREATE TABLE `securitypower` (
  `id` int(11) NOT NULL,
  `httpurl` varchar(255) DEFAULT NULL,
  `powername` varchar(255) DEFAULT NULL,
  `usagemethod` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of securitypower
-- ----------------------------
INSERT INTO `securitypower` VALUES ('1', '/power/**', '分页查询权限', 'GET');
INSERT INTO `securitypower` VALUES ('2', '/power/**', '修改权限', 'PUT');
INSERT INTO `securitypower` VALUES ('3', '/power/**', '删除权限', 'DELETE');
INSERT INTO `securitypower` VALUES ('4', '/power/**', '添加权限', 'POST');
INSERT INTO `securitypower` VALUES ('5', '/role/**', '角色赋权限', 'HEAD');
INSERT INTO `securitypower` VALUES ('6', '/test/**', '测试', 'GET');
INSERT INTO `securitypower` VALUES ('7', '/role/**', '添加角色', 'POST');
INSERT INTO `securitypower` VALUES ('8', '/role/**', '修改角色', 'PUT');
INSERT INTO `securitypower` VALUES ('9', '/role/**', '删除角色', 'DELETE');
INSERT INTO `securitypower` VALUES ('10', '/role/**', '分页查询角色', 'GET');
INSERT INTO `securitypower` VALUES ('11', '/user/**', '添加用户', 'POST');
INSERT INTO `securitypower` VALUES ('12', '/user/**', '删除用户', 'DELETE');
INSERT INTO `securitypower` VALUES ('13', '/user/**', '修改用户', 'PUT');
INSERT INTO `securitypower` VALUES ('14', '/user/**', '分页查询用户', 'GET');
INSERT INTO `securitypower` VALUES ('15', '/user/**', '用户赋角色', 'HEAD');
INSERT INTO `securitypower` VALUES ('16', '/user/**', '用户所有权限', 'All');
INSERT INTO `securitypower` VALUES ('17', '/role/**', '角色所有权限', 'All');
INSERT INTO `securitypower` VALUES ('18', '/power/**', '权限所权限', 'All');

-- ----------------------------
-- Table structure for securityrole
-- ----------------------------
DROP TABLE IF EXISTS `securityrole`;
CREATE TABLE `securityrole` (
  `id` int(11) NOT NULL,
  `rolecode` varchar(255) DEFAULT NULL,
  `rolename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of securityrole
-- ----------------------------
INSERT INTO `securityrole` VALUES ('1', '001', 'Admin');
INSERT INTO `securityrole` VALUES ('2', '002', 'BackstageAdmin');
INSERT INTO `securityrole` VALUES ('3', '003', 'ReceptionAdmin');
INSERT INTO `securityrole` VALUES ('4', '004', 'USER');

-- ----------------------------
-- Table structure for securityuser
-- ----------------------------
DROP TABLE IF EXISTS `securityuser`;
CREATE TABLE `securityuser` (
  `id` int(11) NOT NULL,
  `account` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of securityuser
-- ----------------------------
INSERT INTO `securityuser` VALUES ('1', 'zxy', '$2a$10$ESWcXmZ0QnE94FwZ2zcb2.wjBFw635SxZbipojq.FD.ax3mSrnDWq', '邹想云');
INSERT INTO `securityuser` VALUES ('2', 'zyh', '$2a$10$Zbd5aWi5adrYiMFOQKfeFObZaG0l9o7Qplmry0fFyogLV98mSiDzu', '周业好');
INSERT INTO `securityuser` VALUES ('3', 'bz', '$2a$10$Zbd5aWi5adrYiMFOQKfeFObZaG0l9o7Qplmry0fFyogLV98mSiDzu', '班政');
INSERT INTO `securityuser` VALUES ('4', 'tzh', '$2a$10$Zbd5aWi5adrYiMFOQKfeFObZaG0l9o7Qplmry0fFyogLV98mSiDzu', '唐子豪');
INSERT INTO `securityuser` VALUES ('5', 'lsq', '$2a$10$Zbd5aWi5adrYiMFOQKfeFObZaG0l9o7Qplmry0fFyogLV98mSiDzu', '林水桥');
INSERT INTO `securityuser` VALUES ('24', 'TEST', '$2a$10$kfjic7/AcixIVL2yqeLZH.sGXuXE.fAfNNMzfE8Fpogsvw9vNOGei', '测试');
