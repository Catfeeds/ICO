
-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', 'Primas', '乐团', '2017-08-03 16:00:36', '2017-10-20 13:00:52', '1', '1', null, null, '2000', 'Primas是一个开放的内容发布、推荐和交易生态圈。Primas致力于使用区块链和其他技术手段改变现有内容市场格局，解决优质内容难以识别、传播和变现的问题。通过去中心化内容溯源和筛选机制，使用户获取到优质的内容。全新的内容价值评价体系保证优质内容的生产者直接获得收益。通过区块链的不可篡改性为原创者提供版权保护。去中心化数据管理也将更好地保护用户的隐私。', '1', null);
INSERT INTO `project` VALUES ('2', 'Filecoin(IPFS)', '文件币', '2017-08-03 16:00:38', '2017-09-06 13:13:24', '1', '1', null, null, '200', '星际文件系统（InterPlanetary File System，缩写IPFS）是一个旨在创建持久且分布式存储和共享文件的网络传输协议。它是一种内容可寻址的对等超媒体分发协议。在IPFS网络中的节点将构成一个分布式文件系统。它是一个开放源代码项目，自2014年开始由Protocol Labs在开源社区的帮助下发展。其最初由Juan Benet设计。\r\n自第一次出现时，分布式文件系统IPFS（星际文件系统的简称）就备受瞩目和关注。一年半之后，IPFS创造者Juan Benet准备把称作Filecoin的基于区块链的数字货币层融入IPFS，用以激励IPFS网络中的数据存储。IPFS的目标是给网络上的数据添加去中心化性能，利用现有文件恢复系统、HTTP，提高效率，使HTTP可以像点对点系统一样运作。IPFS的运行机制可以用一个比喻来描述。', '1', null);
INSERT INTO `project` VALUES ('3', 'Indorse', '认可币', '2017-08-03 16:00:39', '2017-07-11 13:20:25', '2', '1', null, null, '5000', 'Indorse是采用新型代币化模式和去中心化模式的革命性平台，致力于重塑专业社交网络。项目的目标是让用户重新获得对数据的掌控权，并让他们通过分享自己的技能和参与活动获得利润。', '1', null);
INSERT INTO `project` VALUES ('4', 'Postbase', '基础发行币', '2017-08-03 16:00:41', '2017-08-15 13:24:25', '0', '1', null, null, '1000', 'Postbase是一个雄心勃勃的社会书签和资金项目，专注于开发完全分散的社会书签平台，奖励用户提交优质内容。Postbase处于早期阶段，一些功能还在开发中，包括PB的加密。', '1', null);
INSERT INTO `project` VALUES ('5', 'EOS', '观测星币', '2017-08-03 16:00:44', '2017-08-31 13:37:42', '0', '1', null, null, '10000', '为商用分布式应用设计的一款区块链操作系统，在分布式应用程序开发过程中，如果能有一个底层区块链操作系统就能大大降低开发难度，让开发者专注于更重要的事情－业务逻辑的探索。', '1', null);
INSERT INTO `project` VALUES ('6', 'OmiseGo', 'OmiseGo代币', '2017-08-03 16:04:38', '2017-08-02 13:41:51', '2', '1', null, null, '500', 'OmiseGO是一个基于以太坊公共金融技术的主流数字钱包，可以跨管辖区域和组织单点，同时可以使用法币和去中心化货币，提供实时点对点的价值交换和支付服务，旨在实现金融包容性并颠覆现有机构，从2017年第四季度开始，每个人都可以使用OmiseGo网络和数字钱包框架。', '1', null);

-- ----------------------------
-- Records of user
-- ----------------------------

INSERT INTO `user` VALUES (1, 'user', '$2a$10$WIUYeUw1.TcoKiIxUAf.7Ot5QN3CZNlP0.nts5USRBsvu5F7D02.2', 'user', '用户', '18829012080', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (2, 'admin', '$2a$10$i689y0x9zhtnFtmGmkOPRuLChsxsdBbHLN2/bpMicev/VPcdZAnrC', 'admin', '管理员', '18829012090', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of user_role_relation
-- ----------------------------

INSERT INTO `user_role_relation` VALUES (1, 1, 1);
INSERT INTO `user_role_relation` VALUES (2, 2, 2);

-- ----------------------------
-- Records of role
-- ----------------------------

INSERT INTO `role` VALUES (1, 'ROLE_USER', 'ROLE_USER', NULL);
INSERT INTO `role` VALUES (2, 'ROLE_ADMIN', 'ROLE_ADMIN', NULL);

