/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50132
Source Host           : localhost:3306
Source Database       : conetwork

Target Server Type    : MYSQL
Target Server Version : 50132
File Encoding         : 65001

Date: 2019-06-27 09:36:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `contractId` int(11) NOT NULL AUTO_INCREMENT,
  `partyAName` varchar(255) DEFAULT NULL,
  `partyBName` varchar(255) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`contractId`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8;
