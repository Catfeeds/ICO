/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : ico

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-08-03 13:04:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for operator_history
-- ----------------------------
DROP TABLE IF EXISTS `operator_history`;
CREATE TABLE `operator_history` (
  `id` int(11) unsigned NOT NULL,
  `user_id` int(11) unsigned DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `ip_address` varchar(32) DEFAULT NULL,
  `operator_time` timestamp NULL DEFAULT NULL,
  `des` text,
  PRIMARY KEY (`id`),
  KEY `fk_operator_2_user_user_id` (`user_id`),
  CONSTRAINT `fk_operator_2_user_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作记录';

-- ----------------------------
-- Records of operator_history
-- ----------------------------

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `name_cn` varchar(100) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `third_endorsement` tinyint(1) DEFAULT NULL,
  `output_token_money_detail_id` int(11) unsigned DEFAULT NULL,
  `input_token_money_datail_id` int(11) unsigned DEFAULT NULL,
  `part_person_number` int(11) DEFAULT NULL,
  `des` text,
  `create_user_id` int(11) unsigned DEFAULT NULL,
  `project_wallet_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_project_2_user_create_user_id` (`create_user_id`),
  KEY `fk_project_2_token_money_detail_output_token_money_detail_id` (`output_token_money_detail_id`),
  KEY `fk_project_2_token_money_detail_intput_token_money_detail_id` (`input_token_money_datail_id`),
  KEY `fk_project_2_project_wallet_project_wallet_id` (`project_wallet_id`),
  CONSTRAINT `fk_project_2_project_wallet_project_wallet_id` FOREIGN KEY (`project_wallet_id`) REFERENCES `project_wallet` (`id`),
  CONSTRAINT `fk_project_2_token_money_detail_intput_token_money_detail_id` FOREIGN KEY (`input_token_money_datail_id`) REFERENCES `token_detail` (`id`),
  CONSTRAINT `fk_project_2_token_money_detail_output_token_money_detail_id` FOREIGN KEY (`output_token_money_detail_id`) REFERENCES `token_detail` (`id`),
  CONSTRAINT `fk_project_2_user_create_user_id` FOREIGN KEY (`create_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ico项目';

-- ----------------------------
-- Records of project
-- ----------------------------

-- ----------------------------
-- Table structure for project_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `project_user_relation`;
CREATE TABLE `project_user_relation` (
  `id` int(11) unsigned NOT NULL,
  `user_id` int(11) unsigned DEFAULT NULL,
  `project_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_project_2_user_user_id` (`project_id`),
  KEY `fk_user_project_2_project_project_id` (`user_id`),
  CONSTRAINT `fk_user_project_2_project_project_id` FOREIGN KEY (`user_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_user_project_2_user_user_id` FOREIGN KEY (`project_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户项目关系';

-- ----------------------------
-- Records of project_user_relation
-- ----------------------------

-- ----------------------------
-- Table structure for project_user_wallet_relation
-- ----------------------------
DROP TABLE IF EXISTS `project_user_wallet_relation`;
CREATE TABLE `project_user_wallet_relation` (
  `id` int(11) unsigned NOT NULL,
  `user_wallet` int(11) unsigned DEFAULT NULL,
  `project_wallet` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_project_wallet_2_user_wallet_user_wallet_id` (`user_wallet`),
  KEY `fk_user_project_wallet_2_project_wallet_project_wallet_id` (`project_wallet`),
  CONSTRAINT `fk_user_project_wallet_2_project_wallet_project_wallet_id` FOREIGN KEY (`project_wallet`) REFERENCES `project_wallet` (`id`),
  CONSTRAINT `fk_user_project_wallet_2_user_wallet_user_wallet_id` FOREIGN KEY (`user_wallet`) REFERENCES `user_wallet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户钱包项目钱包关系';

-- ----------------------------
-- Records of project_user_wallet_relation
-- ----------------------------

-- ----------------------------
-- Table structure for project_wallet
-- ----------------------------
DROP TABLE IF EXISTS `project_wallet`;
CREATE TABLE `project_wallet` (
  `id` int(11) unsigned NOT NULL,
  `wallet_address` varchar(255) DEFAULT NULL,
  `token_money_detail_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_project_wallet_2_token_money_detail_id` (`token_money_detail_id`),
  CONSTRAINT `fk_project_wallet_2_token_money_detail_id` FOREIGN KEY (`token_money_detail_id`) REFERENCES `token_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目钱包';

-- ----------------------------
-- Records of project_wallet
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) DEFAULT NULL,
  `role_name_code` varchar(32) DEFAULT NULL,
  `description` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_USER', null, null);
INSERT INTO `role` VALUES ('2', 'ROLE_ADMIN', null, null);

-- ----------------------------
-- Table structure for token_detail
-- ----------------------------
DROP TABLE IF EXISTS `token_detail`;
CREATE TABLE `token_detail` (
  `id` int(11) unsigned NOT NULL,
  `token_money_id` int(11) unsigned DEFAULT NULL,
  `current_number` int(11) DEFAULT NULL,
  `ico_number` int(11) DEFAULT NULL,
  `min_target_number` int(11) DEFAULT NULL,
  `target_number` int(11) DEFAULT NULL,
  `token_money_whitePaper_cn_url` varchar(255) DEFAULT NULL,
  `token_money_whitePaper_en_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_token_money_detail_2_token_money_token_money_id` (`token_money_id`),
  CONSTRAINT `fk_token_money_detail_2_token_money_token_money_id` FOREIGN KEY (`token_money_id`) REFERENCES `token_money` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代币详情';

-- ----------------------------
-- Records of token_detail
-- ----------------------------

-- ----------------------------
-- Table structure for token_money
-- ----------------------------
DROP TABLE IF EXISTS `token_money`;
CREATE TABLE `token_money` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `name_en_short` varchar(32) DEFAULT NULL,
  `des` text,
  `official_url` varchar(255) DEFAULT NULL,
  `twitter_url` varchar(255) DEFAULT NULL,
  `github_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代币信息';

-- ----------------------------
-- Records of token_money
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email_account` varchar(32) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nick_name` varchar(32) DEFAULT NULL,
  `real_name` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `id_card` varchar(32) DEFAULT NULL,
  `is_validate_email` tinyint(1) DEFAULT NULL,
  `is_enabled` tinyint(1) DEFAULT '1',
  `is_validate_phone` tinyint(1) DEFAULT NULL,
  `des` text,
  `avator_url` varchar(255) DEFAULT NULL,
  `idCard_front_url` varchar(255) DEFAULT NULL,
  `idCard_back_url` varchar(255) DEFAULT NULL,
  `idCard_all_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'user', 'user', 'user', '王大姐', '18829012080', null, null, '1', null, null, null, null, null, null);
INSERT INTO `user` VALUES ('2', 'admin', 'admin', 'admin', '管理员', '18829012090', null, null, '1', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_role_relation`;
CREATE TABLE `user_role_relation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned DEFAULT NULL,
  `role_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role_2_role_role_id` (`role_id`),
  KEY `fk_user_role_2_user_user_id` (`user_id`),
  CONSTRAINT `fk_user_role_2_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_role_2_user_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of user_role_relation
-- ----------------------------
INSERT INTO `user_role_relation` VALUES ('1', '1', '1');
INSERT INTO `user_role_relation` VALUES ('2', '2', '2');

-- ----------------------------
-- Table structure for user_wallet
-- ----------------------------
DROP TABLE IF EXISTS `user_wallet`;
CREATE TABLE `user_wallet` (
  `id` int(11) unsigned NOT NULL,
  `token_money_id` int(11) unsigned DEFAULT NULL,
  `token_money_url` varchar(255) DEFAULT NULL COMMENT '需要加密',
  `user_id` int(11) unsigned DEFAULT NULL,
  `des` text,
  `state` tinyint(4) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '- 存入钱包\r\n            - 转出钱包',
  PRIMARY KEY (`id`),
  KEY `fk_user_wallet_2_user_user_id` (`user_id`),
  KEY `fk_user_wallet_2_token_money_detail_token_money_detail_id` (`token_money_id`),
  CONSTRAINT `fk_user_wallet_2_token_money_detail_token_money_detail_id` FOREIGN KEY (`token_money_id`) REFERENCES `token_detail` (`id`),
  CONSTRAINT `fk_user_wallet_2_user_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户钱包';

-- ----------------------------
-- Records of user_wallet
-- ----------------------------
