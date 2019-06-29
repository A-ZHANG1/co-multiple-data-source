/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50132
Source Host           : localhost:3306
Source Database       : conetwork

Target Server Type    : MYSQL
Target Server Version : 50132
File Encoding         : 65001

Date: 2019-06-26 12:38:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `companyId` int(11) NOT NULL AUTO_INCREMENT,
  `companyName` varchar(255) DEFAULT NULL,
  `capital` double(11,0) DEFAULT NULL,
  `nodeWeight` double DEFAULT NULL,
  `core` int(11) DEFAULT NULL COMMENT '是否为核心企业，是1，否0',
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`companyId`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('1', '锐迪科微电子（上海）有限公司', '2200', '20', '1', '1');
INSERT INTO `company` VALUES ('2', '中芯国际集成电路制造（上海）有限公司', '219000', '20', '1', null);
INSERT INTO `company` VALUES ('3', '上海宏力半导体制造有限公司', '90000', '20', '0', null);
INSERT INTO `company` VALUES ('4', '威宇科技测试封装有限公司', '3235', '20', '0', null);
INSERT INTO `company` VALUES ('5', '菲尼萨光科技(上海)有限公司', '3530', '20', '0', null);
INSERT INTO `company` VALUES ('6', '芯成半导体（上海）有限公司', '3000', '20', '0', null);
INSERT INTO `company` VALUES ('7', '英飞凌科技中国有限公司', '3000', '20', '1', null);
INSERT INTO `company` VALUES ('8', '展讯通信有限公司', '15000', '20', '0', null);
INSERT INTO `company` VALUES ('9', '鼎芯半导体（上海）有限公司', '123', '20', '0', null);
INSERT INTO `company` VALUES ('10', '捷顶微电子（上海）有限公司', '200', '20', '0', null);
INSERT INTO `company` VALUES ('11', '格科微电子（上海）有限公司', '1180', '20', '0', null);
INSERT INTO `company` VALUES ('12', '芯原微电子有限公司', '36900', '20', '0', null);
INSERT INTO `company` VALUES ('13', 'UT斯达康（中国）有限公司', '10000', '20', '1', null);
INSERT INTO `company` VALUES ('14', '安森美半导体贸易（上海）有限公司', '240', '20', '0', null);
INSERT INTO `company` VALUES ('15', '上海华虹集成电路有限责任公司', '2359', '20', '0', null);
INSERT INTO `company` VALUES ('16', '剑腾液晶显示(上海)有限公司', '115255', '20', '0', null);
INSERT INTO `company` VALUES ('17', '东电电子（上海）有限公司', '4200', '20', '0', null);
INSERT INTO `company` VALUES ('18', '应用材料（中国）有限公司', '8400', '20', '0', null);
INSERT INTO `company` VALUES ('19', '普莱克斯（上海）半导体气体有限公司 ', '18382', '20', '0', null);
INSERT INTO `company` VALUES ('20', '晶晨半导体（上海）有限公司', '37000', '20', '0', null);
INSERT INTO `company` VALUES ('21', '上海明波通信技术有限公司 ', '1090', '20', '0', null);
INSERT INTO `company` VALUES ('22', '上海蓝光科技有限公司', '32568', '20', '0', null);
INSERT INTO `company` VALUES ('23', '上海方泰电子科技有限公司', '3640', '20', '0', null);
INSERT INTO `company` VALUES ('24', '华亚微电子有限公司 ', '5736', '20', '0', null);
INSERT INTO `company` VALUES ('25', '埃派克森微电子（上海）有限公司', '5200', '20', '0', null);
INSERT INTO `company` VALUES ('26', '上海浦东软件园 ', '30000', '20', '2', null);
INSERT INTO `company` VALUES ('27', '上海超级计算中心 ', '3500', '20', '0', null);
INSERT INTO `company` VALUES ('28', '上海银晨智能识别科技有限公司', '4000', '20', '0', null);
INSERT INTO `company` VALUES ('29', '科泰世纪科技有限公司', '3750', '20', '0', null);
INSERT INTO `company` VALUES ('30', '毕博信息技术（上海）有限公司', '500', '20', '0', null);
INSERT INTO `company` VALUES ('31', '普元软件（上海）有限公司', '5320', '20', '0', null);
INSERT INTO `company` VALUES ('32', '盛大网络发展有限公司', '2509', '20', '0', null);
INSERT INTO `company` VALUES ('33', '第九城市计算机咨询（上海）有限公司', '9932', '20', '0', null);
INSERT INTO `company` VALUES ('34', '东软集团有限公司上海分公司', '222', '20', '0', null);
INSERT INTO `company` VALUES ('35', '创新科技（中国）有限公司', '4200', '20', '0', null);
INSERT INTO `company` VALUES ('36', '上海博达数据通信有限公司', '10000', '20', '0', null);
INSERT INTO `company` VALUES ('37', '上海聚益信息技术有限公司', '10220', '20', '0', null);
INSERT INTO `company` VALUES ('38', '上海宝信软件股份有限公司 ', '87730', '20', '0', null);
INSERT INTO `company` VALUES ('39', '上海龙贝信息科技有限公司', '533', '20', '0', null);
INSERT INTO `company` VALUES ('40', '上海复旦金仕达计算机有限公司', '19738', '20', '0', null);
INSERT INTO `company` VALUES ('41', '大道计算机技术（上海）有限公司', '2000', '20', '0', null);
INSERT INTO `company` VALUES ('42', '上海信急送计算机科技有限公司', '100', '20', '0', null);
INSERT INTO `company` VALUES ('43', '普然通讯技术（上海）有限公司', '1050', '20', '0', null);
INSERT INTO `company` VALUES ('44', 'SAP中国研究院', '74088', '20', '0', null);
INSERT INTO `company` VALUES ('45', '上海视金石影视有限公司', '222', '20', '0', null);
INSERT INTO `company` VALUES ('46', '普元软件有限公司', '222', '20', '0', null);
INSERT INTO `company` VALUES ('47', '上海全景数字技术有限公司', '1735', '20', '0', null);
INSERT INTO `company` VALUES ('48', '上海罗氏制药有限公司', '195000', '20', '0', null);
INSERT INTO `company` VALUES ('49', '上海汇仁制药有限公司', '3000', '20', '0', null);
INSERT INTO `company` VALUES ('50', '上海三共制药有限公司', '37700', '20', '0', null);
INSERT INTO `company` VALUES ('51', '微创医疗器械（上海）有限公司', '245000', '20', '0', null);
INSERT INTO `company` VALUES ('52', '上海迪赛诺医药发展有限公司', '57883', '20', '0', null);
INSERT INTO `company` VALUES ('53', '上海复旦张江生物医药股份有限公司 ', '9230', '20', '0', null);
INSERT INTO `company` VALUES ('54', '上海杰隆生物工程股份有限公司（转基因研究中心）', '222', '20', '0', null);
INSERT INTO `company` VALUES ('55', '麒麟鲲鹏（中国）生物药业有限公司 ', '20000', '20', '0', null);
INSERT INTO `company` VALUES ('56', '上海中信亚特斯诊断试剂有限公司', '11600', '20', '0', null);
INSERT INTO `company` VALUES ('57', '美敦力（上海）有限公司', '3500', '20', '0', null);
INSERT INTO `company` VALUES ('58', '上海天士力药业有限公司 ', '108257', '20', '0', null);
INSERT INTO `company` VALUES ('59', '上海曦龙生物医药工程有限公司', '0', '20', '0', null);
INSERT INTO `company` VALUES ('60', '上海泽生科技开发有限公司', '18198', '20', '0', null);
INSERT INTO `company` VALUES ('61', '绿谷（集团）有限公司', '39800', '20', '0', null);
INSERT INTO `company` VALUES ('62', '上海中药制药技术有限公司', '1661', '20', '0', null);
INSERT INTO `company` VALUES ('63', '上海药明康德新药开发有限公司', '600000', '20', '0', null);
INSERT INTO `company` VALUES ('64', '上海中信国健药业有限公司', '51022', '20', '0', null);
INSERT INTO `company` VALUES ('65', '上海赛达生物药业股份有限公司 ', '0', '20', '0', null);
INSERT INTO `company` VALUES ('66', '上海奥普生物医药有限公司', '5299', '20', '0', null);
INSERT INTO `company` VALUES ('67', '上海复旦悦达生物技术有限公司 ', '7000', '20', '0', null);
INSERT INTO `company` VALUES ('68', '通用电气中国技术中心', '70000', '20', '0', null);
INSERT INTO `company` VALUES ('69', '罗氏研发（中国）有限公司 ', '25025', '20', '0', null);
INSERT INTO `company` VALUES ('70', '杜邦（中国）研发中心 ', '0', '20', '0', null);
INSERT INTO `company` VALUES ('71', '霍尼韦尔（中国）有限公司', '2100', '20', '0', null);
INSERT INTO `company` VALUES ('72', '索尼上海技术中心', '100', '20', '0', null);
INSERT INTO `company` VALUES ('73', '联想（上海）有限公司', '800', '20', '1', null);
INSERT INTO `company` VALUES ('74', '中兴通讯上海研发中心 ', '0', '20', '0', null);
INSERT INTO `company` VALUES ('75', '夏新上海研究院 ', '0', '20', '0', null);
INSERT INTO `company` VALUES ('76', '中国科学院上海药物研究所', '0', '20', '0', null);
INSERT INTO `company` VALUES ('77', '上海中药创新研究中心', '1500', '20', '0', null);
INSERT INTO `company` VALUES ('78', '国家新药筛选中心', '4500', '20', '0', null);
INSERT INTO `company` VALUES ('79', '和记黄埔医药（上海）有限公司', '127000', '20', '0', null);
INSERT INTO `company` VALUES ('80', '上海生物芯片有限公司', '29000', '20', '0', null);
INSERT INTO `company` VALUES ('81', '国家人类基因组南方研究中心', '0', '20', '0', null);
INSERT INTO `company` VALUES ('82', '上海睿星基因技术有限公司', '10800', '20', '0', null);
INSERT INTO `company` VALUES ('83', '亚申科技研发中心（上海）有限公司', '0', '20', '0', null);
INSERT INTO `company` VALUES ('84', '上海微电子装备有限公司', '14702', '20', '0', null);
INSERT INTO `company` VALUES ('85', '上海奥威科技开发有限公司', '6000', '20', '0', null);
INSERT INTO `company` VALUES ('86', '中国银联银行卡全国信息处理中心', '0', '20', '0', null);
INSERT INTO `company` VALUES ('87', '国家中药制药工程技术研究中心 ', '1661', '20', '0', null);
INSERT INTO `company` VALUES ('88', '上海德国中心', '21000', '20', '0', null);
INSERT INTO `company` VALUES ('89', '曙光医院', '250', '20', '0', null);
INSERT INTO `company` VALUES ('90', '松下', '56133', '20', '0', null);
INSERT INTO `company` VALUES ('91', '联想', '50', '20', '1', null);
INSERT INTO `company` VALUES ('92', '凹凸科技', '700', '20', '0', null);
INSERT INTO `company` VALUES ('93', '中兴', '1000', '20', '0', null);
INSERT INTO `company` VALUES ('94', '上海鑫海商务咨询有限公司', '30', '20', '0', null);
INSERT INTO `company` VALUES ('95', '上海市红会信息科技有限公司', '6000', '20', '0', null);
INSERT INTO `company` VALUES ('96', '上海致远生物科技有限公司', '0', '20', '0', null);
INSERT INTO `company` VALUES ('97', '上海丽正软件技术有限公司', '538', '20', '0', null);
INSERT INTO `company` VALUES ('98', '上海亿川信息技术有限公司', '50', '20', '0', null);
INSERT INTO `company` VALUES ('99', '维亚生物科技(上海)有限公司', '14000', '20', '0', null);
INSERT INTO `company` VALUES ('100', '上海乔源生物制药有限公司', '400', '20', '0', null);
INSERT INTO `company` VALUES ('101', '上海克硫环保科技股份有限公司', '3500', '20', '0', null);
INSERT INTO `company` VALUES ('102', '上海建缆电气有限公司', '0', '20', '0', null);
INSERT INTO `company` VALUES ('103', '库柏(中国)投资有限公司', '21000', '20', '0', null);
INSERT INTO `company` VALUES ('104', '上海薄荷信息科技有限公司', '500', '20', '0', null);
INSERT INTO `company` VALUES ('105', '上海皓元化学科技有限公司', '5574', '20', '0', null);
INSERT INTO `company` VALUES ('106', '上海芯超生物科技有限公司', '2158', '20', '0', null);
INSERT INTO `company` VALUES ('107', '上海百迈博生物技术有限公司', '52000', '20', '0', null);
INSERT INTO `company` VALUES ('108', '上海韬图动漫科技有限公司', '1339', '20', '0', null);
INSERT INTO `company` VALUES ('109', '胜科金仕达数据系统(中国)有限公司', '19738', '20', '0', null);
INSERT INTO `company` VALUES ('110', ' 格鲁勃福物业管理(上海)有限公司', '0', '20', '0', null);
INSERT INTO `company` VALUES ('111', '智格网信息科技(上海)有限公司', '4200', '20', '0', null);
INSERT INTO `company` VALUES ('112', '上海数讯信息技术有限公司', '5000', '20', '0', null);
INSERT INTO `company` VALUES ('113', '奥解思信息技术(上海)有限公司', '105', '20', '0', null);
INSERT INTO `company` VALUES ('114', ' 浙江科博达工业有限公司', '15000', '20', '0', null);
INSERT INTO `company` VALUES ('115', ' 辉源生物科技(上海)有限公司', '3076', '20', '0', null);
INSERT INTO `company` VALUES ('116', '美国胜科系统国际公司', '0', '20', '0', null);
INSERT INTO `company` VALUES ('117', '捷开通讯(深圳)有限公司', '7000', '20', '0', null);
INSERT INTO `company` VALUES ('118', '上海分公司捷开通讯(深圳)有限公司上海分公司', '0', '20', '0', null);
INSERT INTO `company` VALUES ('119', '上海奇华顿有限公司(Givaudan)', '7400', '20', '0', null);
INSERT INTO `company` VALUES ('120', '百奇生物科技(上海)有限公司', '735', '20', '0', null);
INSERT INTO `company` VALUES ('121', '上海华强仪表有限公司', '1008', '20', '0', null);
INSERT INTO `company` VALUES ('122', '上海丫丫信息科技有限公司', '555', '20', '0', null);
INSERT INTO `company` VALUES ('123', '摩仕网络科技(上海)有限公司', '1610', '20', '0', null);
INSERT INTO `company` VALUES ('124', '奥解思信息技术有限公司', '105', '20', '0', null);
INSERT INTO `company` VALUES ('125', '上海扩利体系统技术有限公司', '108', '20', '0', null);
INSERT INTO `company` VALUES ('126', '上海雄振生物科技有限公司', '300', '20', '0', null);
INSERT INTO `company` VALUES ('127', '宏力半导体制造有限公司', '630000', '20', '0', null);
INSERT INTO `company` VALUES ('128', '上海睿智化学研究有限公司', '13892', '20', '0', null);
INSERT INTO `company` VALUES ('129', '上海妙法文化传播有限公司', '0', '20', '0', null);
INSERT INTO `company` VALUES ('130', '华强仪表有限公司', '1008', '20', '0', null);
INSERT INTO `company` VALUES ('131', '盛美半导体设备(上海)有限公司', '35769', '20', '0', null);
INSERT INTO `company` VALUES ('132', '上海查士睿华生物医药科技有限公司', '0', '20', '0', null);
INSERT INTO `company` VALUES ('133', '奥解思技术有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('134', '盛美半导体设备有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('135', '上海乘方信息科技有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('136', '上海奇华顿有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('137', '上海茂碧信息科技有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('138', '上海朔望餐饮服务有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('139', '上海摩威电子科技有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('140', '灿芯半导体(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('141', '上海药谷药业有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('142', '美敦力医疗用品技术服务(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('143', '上海达安医学检测中心有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('144', '上海春宇供应链管理有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('145', '日月光封装测试(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('146', '菲尼萨光电通讯(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('147', '博彦科技上海分公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('148', '诺华赛分离技术(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('149', '上海火速网络科技有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('150', '美敦力医疗用品技术服务有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('151', '香港杜塞尔多夫展览(中国)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('152', '上海代表处欧瑞斯信息科技(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('153', '上海中医药大学', '20', '20', '0', null);
INSERT INTO `company` VALUES ('154', '上海中医药博物馆 ', '20', '20', '0', null);
INSERT INTO `company` VALUES ('155', '中国美术学院上海设计艺术学院 ', '20', '20', '0', null);
INSERT INTO `company` VALUES ('156', '中国科技大学研发中心', '20', '20', '0', null);
INSERT INTO `company` VALUES ('157', '阿文美驰商用车辆系统(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('158', '埃地沃兹真空泵制造(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('159', '埃尔斯特计量仪表(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('160', '埃姆康净化技术(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('161', '蔼科颂(上海)化工产品有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('162', '艾尔菲玩具生产（上海）有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('163', '艾来得机械(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('164', '艾默生船用过程控制(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('165', '艾默生过程控制有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('166', '艾默生控制系统(上海)有限公司', '20', '20', '1', null);
INSERT INTO `company` VALUES ('167', '爱博斯迪科化学(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('168', '爱迪尔汽车配件(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('169', '爱科来医疗电子(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('170', '爱通汽车零部件(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('171', '安集微电子科技（上海）有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('172', '安捷伦科技(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('173', '安靠封装测试(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('174', '安能利精密模具(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('175', '昂宝电子(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('176', '巴斯夫催化剂(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('177', '巴斯夫应用化工有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('178', '百奇生物科技有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('179', '北摄精密冲压部件(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('180', '倍克精密塑料(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('181', '必达泰克光电设备(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('182', '毕梯优电子(上海)有限公司\r\n毕梯优电子(上海)有限公司\r\n毕梯优电子(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('183', '博鲁可斯科技(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('184', '蔡司工业测量技术(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('185', '超圣电子装配(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('186', '成行精密部件(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('187', '德莱赛稳加油设备(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('188', '德瑞斯海达(上海)机械有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('189', '迪堡金融设备有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('190', '帝人化成复合塑料(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('191', '东方协和塑料工业(上海浦东新区)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('192', '东清电子(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('193', '东拓(上海)电材有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('194', '东芝照明显示系统(上海)有限公司', '20', '20', '1', null);
INSERT INTO `company` VALUES ('195', '岽沣精工精密弹簧(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('196', '杜克普爱华工业制造（上海）有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('197', '多米诺标识科技有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('198', '恩耐激光技术(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('199', '恩尼特克电子科技(上海)有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('200', '菲尼萨光电通讯有限公司', '20', '20', '0', null);
INSERT INTO `company` VALUES ('201', '菲尼萨光电子科技(上海)有限公司', '20', '20', '0', null);
