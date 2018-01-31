-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: hdong
-- ------------------------------------------------------
-- Server version	5.7.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict` (
  `dict_app` varchar(10) NOT NULL COMMENT '应用名',
  `dict_type` varchar(30) NOT NULL COMMENT '类型,对应枚举类名',
  `dict_val` varchar(15) NOT NULL COMMENT '值，对应枚举val',
  `dict_name` varchar(30) NOT NULL COMMENT '对应枚举名字',
  `dict_desc` varchar(50) DEFAULT NULL COMMENT '描述，对应枚举的desc',
  PRIMARY KEY (`dict_app`,`dict_type`,`dict_val`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` VALUES ('UPMS','PERMISSION_TYPE','1','CATALOG','目录'),('UPMS','PERMISSION_TYPE','2','MENU','菜单'),('UPMS','PERMISSION_TYPE','3','BUTTON','按钮'),('UPMS','SYSTEM_STATUS','-1','ABNORMAL','异常'),('UPMS','SYSTEM_STATUS','1','NORMAL','正常'),('UPMS','USER_LOCKED','-1','LOCKED','锁定'),('UPMS','USER_LOCKED','1','NORMAL','正常'),('UPMS','USER_PERMISSION_TYPE','-1','SUB','减权限'),('UPMS','USER_PERMISSION_TYPE','1','ADD','加权限'),('UPMS','USER_SEX','1','MALE','男'),('UPMS','USER_SEX','2','FEMALE','女');
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_log`
--

DROP TABLE IF EXISTS `upms_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_log` (
  `log_id` int(11) NOT NULL COMMENT '编号',
  `description` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `username` varchar(20) DEFAULT NULL COMMENT '操作用户',
  `start_time` bigint(20) DEFAULT NULL COMMENT '操作时间',
  `spend_time` int(11) DEFAULT NULL COMMENT '消耗时间',
  `base_path` varchar(500) DEFAULT NULL COMMENT '根路径',
  `uri` varchar(500) DEFAULT NULL COMMENT 'URI',
  `url` varchar(500) DEFAULT NULL COMMENT 'URL',
  `method` varchar(10) DEFAULT NULL COMMENT '请求类型',
  `parameter` mediumtext,
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户标识',
  `ip` varchar(30) DEFAULT NULL COMMENT 'IP地址',
  `result` mediumtext,
  `permissions` varchar(100) DEFAULT NULL COMMENT '权限值',
  PRIMARY KEY (`log_id`),
  KEY `log_id` (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_log`
--

LOCK TABLES `upms_log` WRITE;
/*!40000 ALTER TABLE `upms_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `upms_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_organization`
--

DROP TABLE IF EXISTS `upms_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_organization` (
  `organization_id` int(10) unsigned NOT NULL COMMENT '编号',
  `pid` int(10) DEFAULT NULL COMMENT '所属上级',
  `name` varchar(20) DEFAULT NULL COMMENT '组织名称',
  `description` varchar(1000) DEFAULT NULL COMMENT '组织描述',
  `ctime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_organization`
--

LOCK TABLES `upms_organization` WRITE;
/*!40000 ALTER TABLE `upms_organization` DISABLE KEYS */;
INSERT INTO `upms_organization` VALUES (1,0,'浙商银行股份有限公司','浙商银行股份有限公司',1),(2,1,'总部','总部',2);
/*!40000 ALTER TABLE `upms_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_permission`
--

DROP TABLE IF EXISTS `upms_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_permission` (
  `permission_id` int(10) unsigned NOT NULL COMMENT '编号',
  `system_id` int(10) unsigned NOT NULL COMMENT '所属系统',
  `pid` int(10) DEFAULT NULL COMMENT '所属上级',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型(1:目录,2:菜单,3:按钮)',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `uri` varchar(100) DEFAULT NULL COMMENT '路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `ctime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `orders` bigint(20) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_permission`
--

LOCK TABLES `upms_permission` WRITE;
/*!40000 ALTER TABLE `upms_permission` DISABLE KEYS */;
INSERT INTO `upms_permission` VALUES (1,1,0,'系统组织管理',1,'','','zmdi zmdi-accounts-list',1,1),(2,1,1,'系统管理',2,'upms:system:read','manage/system/index','',2,2),(3,1,1,'组织管理',2,'upms:organization:read','manage/organization/index','',3,3),(4,1,0,'角色用户管理',1,'','','zmdi zmdi-accounts',4,4),(5,1,4,'角色管理',2,'upms:role:read','/manage/role/index','',5,5),(6,1,4,'用户管理',2,'upms:user:read','/manage/user/index','',6,6),(7,1,0,'权限资源管理',1,'','','zmdi zmdi-lock-outline',7,7),(12,1,0,'其他数据管理',1,'','','zmdi zmdi-more',12,12),(14,1,12,'会话管理',2,'upms:session:read','/manage/session/index','',14,14),(15,1,12,'序列号管理',2,'upms:sequence:read','/manage/sequence/index','',15,15),(24,1,2,'新增系统',3,'upms:system:create',NULL,'zmdi zmdi-plus',24,24),(25,1,2,'编辑系统',3,'upms:system:update',NULL,'zmdi zmdi-edit',25,25),(26,1,2,'删除系统',3,'upms:system:delete',NULL,'zmdi zmdi-close',26,26),(27,1,3,'新增组织',3,'upms:organization:create',NULL,'zmdi zmdi-plus',27,27),(28,1,3,'编辑组织',3,'upms:organization:update',NULL,'zmdi zmdi-edit',28,28),(29,1,3,'删除组织',3,'upms:organization:delete',NULL,'zmdi zmdi-close',29,29),(30,1,6,'新增用户',3,'upms:user:create',NULL,'zmdi zmdi-plus',30,30),(31,1,6,'编辑用户',3,'upms:user:update',NULL,'zmdi zmdi-edit',31,31),(32,1,6,'删除用户',3,'upms:user:delete',NULL,'zmdi zmdi-close',32,32),(33,1,5,'新增角色',3,'upms:role:create',NULL,'zmdi zmdi-plus',33,33),(34,1,5,'编辑角色',3,'upms:role:update',NULL,'zmdi zmdi-edit',34,34),(35,1,5,'删除角色',3,'upms:role:delete',NULL,'zmdi zmdi-close',35,35),(36,1,39,'新增权限',3,'upms:permission:create',NULL,'zmdi zmdi-plus',36,36),(37,1,39,'编辑权限',3,'upms:permission:update',NULL,'zmdi zmdi-edit',37,37),(38,1,39,'删除权限',3,'upms:permission:delete',NULL,'zmdi zmdi-close',38,38),(39,1,7,'权限管理',2,'upms:permission:read','/manage/permission/index',NULL,39,39),(46,1,5,'角色权限',3,'upms:role:permission',NULL,'zmdi zmdi-key',46,46),(48,1,6,'用户组织',3,'upms:user:organization',NULL,'zmdi zmdi-accounts-list',48,48),(50,1,6,'用户角色',3,'upms:user:role',NULL,'zmdi zmdi-accounts',50,50),(51,1,6,'用户权限',3,'upms:user:permission',NULL,'zmdi zmdi-key',51,51),(53,1,14,'强制退出',3,'upms:session:forceout',NULL,'zmdi zmdi-run',53,53),(57,1,15,'设置序列号',3,'upms:sequence:reset','','zmdi zmdi-close',57,57);
/*!40000 ALTER TABLE `upms_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_role`
--

DROP TABLE IF EXISTS `upms_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_role` (
  `role_id` int(10) unsigned NOT NULL COMMENT '编号',
  `name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(1000) DEFAULT NULL COMMENT '角色描述',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `orders` bigint(20) NOT NULL COMMENT '排序',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_role`
--

LOCK TABLES `upms_role` WRITE;
/*!40000 ALTER TABLE `upms_role` DISABLE KEYS */;
INSERT INTO `upms_role` VALUES (1,'super','拥有所有权限',1,1),(2,'admin','拥有除权限管理系统外的所有权限',2,2),(3,'read','仅有查询权限',3,3);
/*!40000 ALTER TABLE `upms_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_role_permission`
--

DROP TABLE IF EXISTS `upms_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_role_permission` (
  `role_permission_id` int(10) unsigned NOT NULL COMMENT '编号',
  `role_id` int(10) unsigned NOT NULL COMMENT '角色编号',
  `permission_id` int(10) unsigned NOT NULL COMMENT '权限编号',
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_role_permission`
--

LOCK TABLES `upms_role_permission` WRITE;
/*!40000 ALTER TABLE `upms_role_permission` DISABLE KEYS */;
INSERT INTO `upms_role_permission` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,39),(12,1,12),(14,1,14),(15,1,15),(17,1,17),(19,1,19),(20,1,20),(21,1,21),(24,1,24),(27,1,27),(28,1,28),(29,1,29),(30,1,30),(31,1,31),(32,1,32),(33,1,33),(34,1,34),(35,1,35),(36,1,36),(37,1,37),(38,1,38),(39,1,46),(40,1,51),(44,1,48),(45,1,50),(47,1,53),(48,1,18),(49,1,54),(50,1,54),(51,1,55),(52,1,54),(53,1,55),(54,1,56),(55,1,57),(56,1,58),(57,1,58),(58,1,59),(59,1,60),(60,1,61),(61,1,62),(62,1,62),(63,1,63),(64,1,62),(65,1,63),(66,1,64),(67,1,62),(68,1,63),(69,1,64),(70,1,65),(71,1,62),(72,1,63),(73,1,64),(74,1,65),(75,1,66),(76,1,62),(77,1,63),(78,1,64),(79,1,65),(80,1,66),(81,1,67),(82,1,68),(83,1,69),(84,1,69),(85,1,70),(86,1,69),(87,1,70),(88,1,71),(89,1,72),(90,1,72),(91,1,73),(92,1,72),(93,1,73),(94,1,74),(95,1,72),(96,1,73),(97,1,74),(98,1,75),(99,1,76),(100,1,76),(101,1,77),(102,1,76),(103,1,77),(105,1,79),(106,1,80),(107,1,81),(108,1,81),(109,1,82),(110,1,81),(111,1,82),(112,1,83),(113,1,84),(114,1,84),(115,1,85),(121,1,78),(122,1,78),(124,1,25),(125,1,26),(141,2,1),(142,2,2),(143,2,24),(144,2,25),(145,2,26),(146,2,3),(147,2,27),(148,2,28),(149,2,29),(150,2,4),(151,2,6),(152,2,30),(153,2,31),(154,2,32),(155,2,51),(156,2,48),(157,2,50),(158,2,5),(159,2,33),(160,2,34),(161,2,35),(162,2,46),(163,2,7),(164,2,39),(165,2,36),(166,2,37),(167,2,38),(168,2,12),(169,2,14),(170,2,53),(171,2,15),(172,2,57),(173,3,1),(174,3,2),(175,3,3),(176,3,4),(177,3,6),(178,3,5),(179,3,7),(180,3,39);
/*!40000 ALTER TABLE `upms_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_system`
--

DROP TABLE IF EXISTS `upms_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_system` (
  `system_id` int(10) unsigned NOT NULL COMMENT '编号',
  `icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `theme` varchar(20) DEFAULT NULL COMMENT '主题',
  `basepath` varchar(100) DEFAULT NULL COMMENT '根目录',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(-1:黑名单,1:正常)',
  `name` varchar(10) DEFAULT NULL COMMENT '系统名称',
  `title` varchar(20) DEFAULT NULL COMMENT '系统标题',
  `description` varchar(2000) DEFAULT NULL COMMENT '系统描述',
  `ctime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `orders` bigint(20) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_system`
--

LOCK TABLES `upms_system` WRITE;
/*!40000 ALTER TABLE `upms_system` DISABLE KEYS */;
INSERT INTO `upms_system` VALUES (1,'adjust','skin-blue-light','http://upms.hdong1.cn:111112',1,'upms','用户权限管理系统','<div style=\"padding:15px;\">\r\n<p><h1>用户权限管理系统</h1></p>\r\n<p><b>系统简介</b>：本系统基于AdminLTE前台框架、shiro后台权限认证框架、dubbo分布式服务化搭建。通过角色授权和基于用户授权的粗、细粒度权限控制通用平台，并提供单点登录、会话管理和日志管理。接入的系统可自由定义组织、角色、权限、资源等。</p><br/>\r\n<p><h4>系统功能概述：</h4></p>\r\n<p><b>系统组织管理</b>：系统和组织增加、删除、修改、查询功能。</p>\r\n<p><b>用户角色管理</b>：用户和角色增加、删除、修改、查询功能。</p>\r\n<p><b>资源权限管理</b>：菜单和按钮增加、删除、修改、查询功能。</p>\r\n<p><b>权限分配管理</b>：提供给角色和用户的权限增加、删除、修改、查询功能。</p>\r\n<p><b>单点登录(SSO)</b>：提供统一用户单点登录认证、用户鉴权功能。</p>\r\n<p><b>用户会话管理</b>：提供分布式用户会话管理</p>\r\n<p><b>操作日志管理</b>：提供记录用户登录、操作等日志。</p><br/>\r\n<p><h4>对外接口概述：</h4></p>\r\n<p><b>系统组织数据接口</b>、<b>用户角色数据接口</b>、<b>资源权限数据接口</b>、<b>单点登录(SSO)接口</b>、<b>用户鉴权接口</b></p><br/>\r\n<p><h4>关于作者</h4></p>\r\n<p><b>姓　　名</b>：黄栋</p>\r\n<p><b>电子邮箱</b>：hdatsf@163.com</p>\r\n</div>',1,1),(2,'hd-video','skin-red-light','http://upms.hdong1.cn:111112',1,'market','盯市','盯市系统',2,2);
/*!40000 ALTER TABLE `upms_system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_user`
--

DROP TABLE IF EXISTS `upms_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_user` (
  `user_id` int(10) unsigned NOT NULL COMMENT '编号',
  `username` varchar(20) NOT NULL COMMENT '帐号',
  `password` varchar(32) NOT NULL COMMENT '密码MD5(密码+盐)',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `realname` varchar(20) DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(150) DEFAULT NULL COMMENT '头像',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `locked` tinyint(4) DEFAULT NULL COMMENT '状态(0:正常,1:锁定)',
  `ctime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_user`
--

LOCK TABLES `upms_user` WRITE;
/*!40000 ALTER TABLE `upms_user` DISABLE KEYS */;
INSERT INTO `upms_user` VALUES (1,'admin','2338E790936BD0E85C97FA753CEC6FDA','8eaffe6cba0247818afd5c469b3a9fcc','黄栋','userB_6.jpg','12345','403965963@qq.com',1,1,1),(2,'test','9E4AFAE70B98CFAC4DAB4C48748C81ED','ddf8dd6363e94b279b32351bb349eb5e','匿名用户','userB_1.jpg','0571-88888888','test@163.com',1,1,2);
/*!40000 ALTER TABLE `upms_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_user_organization`
--

DROP TABLE IF EXISTS `upms_user_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_user_organization` (
  `user_organization_id` int(10) unsigned NOT NULL COMMENT '编号',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户编号',
  `organization_id` int(10) unsigned NOT NULL COMMENT '组织编号',
  PRIMARY KEY (`user_organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组织关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_user_organization`
--

LOCK TABLES `upms_user_organization` WRITE;
/*!40000 ALTER TABLE `upms_user_organization` DISABLE KEYS */;
INSERT INTO `upms_user_organization` VALUES (19,1,1),(10002,10002,1);
/*!40000 ALTER TABLE `upms_user_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_user_permission`
--

DROP TABLE IF EXISTS `upms_user_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_user_permission` (
  `user_permission_id` int(10) unsigned NOT NULL COMMENT '编号',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户编号',
  `permission_id` int(10) unsigned NOT NULL COMMENT '权限编号',
  `type` tinyint(4) NOT NULL COMMENT '权限类型(-1:减权限,1:增权限)',
  PRIMARY KEY (`user_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_user_permission`
--

LOCK TABLES `upms_user_permission` WRITE;
/*!40000 ALTER TABLE `upms_user_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `upms_user_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upms_user_role`
--

DROP TABLE IF EXISTS `upms_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upms_user_role` (
  `user_role_id` int(10) unsigned NOT NULL COMMENT '编号',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户编号',
  `role_id` int(10) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upms_user_role`
--

LOCK TABLES `upms_user_role` WRITE;
/*!40000 ALTER TABLE `upms_user_role` DISABLE KEYS */;
INSERT INTO `upms_user_role` VALUES (1,1,1),(2,1,2),(3,2,3);
/*!40000 ALTER TABLE `upms_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-23 16:11:51
