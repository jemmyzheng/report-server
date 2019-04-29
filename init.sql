/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : kg_daily

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 29/04/2019 10:14:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_airport
-- ----------------------------
DROP TABLE IF EXISTS `t_airport`;
CREATE TABLE `t_airport` (
  `Fid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `Fname` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`Fid`),
  UNIQUE KEY `name` (`Fname`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COMMENT='机场';

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `Fdept_name` varchar(50) NOT NULL COMMENT '部门名称',
  `Fadd_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`Fdept_name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Table structure for t_field
-- ----------------------------
DROP TABLE IF EXISTS `t_field`;
CREATE TABLE `t_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `Ffield_name` varchar(50) NOT NULL COMMENT '字段名称',
  `Ffield_type` smallint(6) NOT NULL COMMENT '字段类型（0:整数；1:小数；2:文本）',
  `Frequired` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否必填（0:可为空；1:必填）',
  `Fadd_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8 COMMENT='报表字段';

-- ----------------------------
-- Table structure for t_project_build
-- ----------------------------
DROP TABLE IF EXISTS `t_project_build`;
CREATE TABLE `t_project_build` (
  `Fid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `Fprojetc_id` int(11) NOT NULL COMMENT 'project ID',
  `Fbranch` varchar(100) NOT NULL COMMENT '所在分支',
  `Fcommit_hash` varchar(100) NOT NULL COMMENT 'commit HASH',
  `Fcommit_message` varchar(100) NOT NULL COMMENT 'commit 信息',
  `Fcommitter` varchar(100) NOT NULL COMMENT '提交者名称',
  `Fcommit_date` datetime NOT NULL COMMENT 'commit 日期',
  `Faction_type` smallint(6) NOT NULL COMMENT '本次commit所触发的行为类型 （1-Pull Request，2-Merge&Push，3-Test Build, 4-Release Build）',
  `Fversion` varchar(50) NOT NULL COMMENT '当前版本',
  `Flast_version` varchar(50) NOT NULL COMMENT '上一次版本',
  `Fbuild_number` int(11) DEFAULT NULL COMMENT 'Jenkins的Build Number（只在行为类型>=3的时候才有值）',
  `Fstate` smallint(6) NOT NULL DEFAULT '1' COMMENT '状态（1-pending,2-running,3-success,4-failed,5-canceled）',
  `Fadd_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`Fid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目构建记录表';

-- ----------------------------
-- Table structure for t_report
-- ----------------------------
DROP TABLE IF EXISTS `t_report`;
CREATE TABLE `t_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `Freport_name` varchar(50) NOT NULL COMMENT '报表名称',
  `Fdept_id` int(11) NOT NULL COMMENT '所属部门',
  `Freport_type` smallint(6) NOT NULL COMMENT '报表类型（0:日报；1:周报；2:月报；3：年报）',
  `Freport_deadline` varchar(15) NOT NULL COMMENT '数据录入的截止时间（MM-WW-DD-HH-mm）',
  `Fadd_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Fmodify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `Fmodify_user_id` int(11) NOT NULL COMMENT '最后修改人ID',
  PRIMARY KEY (`id`),
  KEY `dept_id` (`Fdept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='报表';

-- ----------------------------
-- Table structure for t_report_field
-- ----------------------------
DROP TABLE IF EXISTS `t_report_field`;
CREATE TABLE `t_report_field` (
  `Freport_id` int(11) NOT NULL COMMENT '报表ID',
  `Ffield_id` int(11) NOT NULL COMMENT '字段ID',
  PRIMARY KEY (`Freport_id`,`Ffield_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表与字段关联表';

-- ----------------------------
-- Table structure for t_report_value
-- ----------------------------
DROP TABLE IF EXISTS `t_report_value`;
CREATE TABLE `t_report_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据ID',
  `Freport_id` int(11) NOT NULL COMMENT '报表ID',
  `Ffield_id` int(11) NOT NULL COMMENT '字段ID',
  `Fairport_id` int(11) NOT NULL COMMENT '机场ID',
  `Ffield_value` varchar(100) NOT NULL COMMENT '字段值',
  `Fmodify_value` varchar(100) DEFAULT NULL COMMENT '待修改值',
  `Fstate` smallint(6) NOT NULL COMMENT '状态(0:正常，1:超时，2:有修改)',
  `Fadd_time` datetime NOT NULL COMMENT '录入时间',
  `Fmodify_time` datetime NOT NULL COMMENT '最后修改时间',
  `Fmodify_user_id` int(11) NOT NULL COMMENT '最后修改人ID',
  `Fadd_day` varchar(100) NOT NULL COMMENT '录入的当天（用以分组数据）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `day_value` (`Fadd_day`,`Freport_id`,`Ffield_id`,`Fairport_id`),
  KEY `add_time` (`Fadd_time`),
  KEY `report_id` (`Freport_id`),
  KEY `field_id` (`Ffield_id`),
  KEY `airport_id` (`Fairport_id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8 COMMENT='报表数据';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `Fuser_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `Fname` varchar(50) NOT NULL COMMENT '姓名',
  `Fphone` varchar(12) NOT NULL COMMENT '手机号',
  `Fdept_id` int(10) NOT NULL COMMENT '所属部门',
  `Fpassword` varchar(128) NOT NULL COMMENT '密码',
  `Fstate` smallint(6) NOT NULL DEFAULT '2' COMMENT '状态（0:已删除；1:正常；2:锁定）',
  `Frole` smallint(6) NOT NULL DEFAULT '0' COMMENT '角色（0:普通用户；1:管理员）',
  `Fadd_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`Fuser_id`),
  UNIQUE KEY `account` (`Fphone`,`Fstate`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='用户';

SET FOREIGN_KEY_CHECKS = 1;
