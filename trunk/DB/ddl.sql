/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : test
Target Host     : localhost:3306
Target Database : test
Date: 2012-08-11 16:57:55
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for robot_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `robot_knowledge`;
CREATE TABLE `robot_knowledge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL,
  `creator` varchar(32) DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `modifier` varchar(32) DEFAULT NULL,
  `is_deleted` char(1) DEFAULT NULL,
  `index_id` varchar(128) DEFAULT NULL,
  `question` varchar(4000) DEFAULT NULL,
  `answer` varchar(4000) DEFAULT NULL,
  `content_type` varchar(32) DEFAULT NULL,
  `robot_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of robot_knowledge
-- ----------------------------
INSERT INTO `robot_knowledge` VALUES ('1', '2012-08-10 14:39:35', 'dzl', '2012-08-10 14:39:41', 'dzl', 'n', '1', '你是谁', '邓自立','text','robot1');
INSERT INTO `robot_knowledge` VALUES ('9999999', null, null, null, null, null, '123456', null, null,null,null);
