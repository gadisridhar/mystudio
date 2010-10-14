# MySQL-Front 3.2  (Build 13.48)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='SYSTEM' */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;


# Host: localhost    Database: osworkflow
# ------------------------------------------------------
# Server version 5.0.18

#
# Table structure for table os_currentstep
#

DROP TABLE IF EXISTS `os_currentstep`;
CREATE TABLE `os_currentstep` (
  `id` bigint(20) NOT NULL auto_increment,
  `entry_id` bigint(20) default NULL,
  `step_id` int(11) default NULL,
  `action_id` int(11) default NULL,
  `owner` varchar(35) default NULL,
  `start_date` datetime default NULL,
  `finish_date` datetime default NULL,
  `due_date` datetime default NULL,
  `status` varchar(40) default NULL,
  `caller` varchar(35) default NULL,
  PRIMARY KEY  (`id`),
  KEY `entry_id` (`entry_id`),
  KEY `owner` (`owner`),
  KEY `caller` (`caller`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_currentstep
#

INSERT INTO `os_currentstep` VALUES (-1,2,1,1,'good','2010-06-04 12:32:57','2010-06-04 14:40:26',NULL,'Finished','good');
INSERT INTO `os_currentstep` VALUES (1,7,1,0,'chengang','2010-06-04 13:28:19',NULL,NULL,'Underway',NULL);
INSERT INTO `os_currentstep` VALUES (4,2,1,1,'good','2010-06-04 12:32:57','2010-06-04 14:40:26',NULL,'Finished','good');
INSERT INTO `os_currentstep` VALUES (11,8,2,0,NULL,'2010-06-07 08:33:10',NULL,NULL,'Queued',NULL);
INSERT INTO `os_currentstep` VALUES (15,9,2,0,NULL,'2010-06-07 08:53:06',NULL,NULL,'Queued',NULL);
INSERT INTO `os_currentstep` VALUES (17,10,2,0,NULL,'2010-06-07 08:54:17',NULL,NULL,'Queued',NULL);

#
# Table structure for table os_currentstep_prev
#

DROP TABLE IF EXISTS `os_currentstep_prev`;
CREATE TABLE `os_currentstep_prev` (
  `id` bigint(20) NOT NULL auto_increment,
  `previous_id` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`id`,`previous_id`),
  KEY `previous_id` (`previous_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_currentstep_prev
#

INSERT INTO `os_currentstep_prev` VALUES (11,5);
INSERT INTO `os_currentstep_prev` VALUES (15,9);
INSERT INTO `os_currentstep_prev` VALUES (17,16);

#
# Table structure for table os_doc
#

DROP TABLE IF EXISTS `os_doc`;
CREATE TABLE `os_doc` (
  `wf_id` bigint(20) NOT NULL default '0',
  `title` varchar(100) default NULL,
  `content` text,
  PRIMARY KEY  (`wf_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_doc
#

INSERT INTO `os_doc` VALUES (2,'test1','<font face=\"Arial\">test1</font>');
INSERT INTO `os_doc` VALUES (7,'fef','ewfew');
INSERT INTO `os_doc` VALUES (8,'ccc','ccc');
INSERT INTO `os_doc` VALUES (9,'ccc','ccc');
INSERT INTO `os_doc` VALUES (10,'test ok','<font face=\"Arial\">test ok</font>');

#
# Table structure for table os_doc_opinion
#

DROP TABLE IF EXISTS `os_doc_opinion`;
CREATE TABLE `os_doc_opinion` (
  `ID` bigint(20) NOT NULL auto_increment,
  `ENTRY_ID` bigint(20) default NULL,
  `STEP_ID` int(11) default NULL,
  `ACTION_ID` int(11) default NULL,
  `CALLER` varchar(35) default NULL,
  `OPINION` text,
  `OPINION_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_doc_opinion
#

INSERT INTO `os_doc_opinion` VALUES (1,9,NULL,1,'good',NULL,'2010-06-07 08:53:06');
INSERT INTO `os_doc_opinion` VALUES (2,10,NULL,1,'good',NULL,'2010-06-07 08:54:18');

#
# Table structure for table os_group
#

DROP TABLE IF EXISTS `os_group`;
CREATE TABLE `os_group` (
  `GROUPNAME` varchar(20) NOT NULL,
  PRIMARY KEY  (`GROUPNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_group
#

INSERT INTO `os_group` VALUES ('designer');
INSERT INTO `os_group` VALUES ('editor');
INSERT INTO `os_group` VALUES ('employee');
INSERT INTO `os_group` VALUES ('publisher');
INSERT INTO `os_group` VALUES ('writer');

#
# Table structure for table os_historystep
#

DROP TABLE IF EXISTS `os_historystep`;
CREATE TABLE `os_historystep` (
  `id` bigint(20) NOT NULL auto_increment,
  `entry_id` bigint(20) default NULL,
  `step_id` int(11) default NULL,
  `action_id` int(11) default NULL,
  `owner` varchar(35) default NULL,
  `start_date` datetime default NULL,
  `finish_date` datetime default NULL,
  `due_date` datetime default NULL,
  `status` varchar(40) default NULL,
  `caller` varchar(35) default NULL,
  PRIMARY KEY  (`id`),
  KEY `entry_id` (`entry_id`),
  KEY `owner` (`owner`),
  KEY `caller` (`caller`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_historystep
#

INSERT INTO `os_historystep` VALUES (-1,2,1,1,'good','2010-06-04 12:32:57','2010-06-04 14:40:26',NULL,'Finished','good');
INSERT INTO `os_historystep` VALUES (5,8,1,1,'good','2010-06-04 14:47:20','2010-06-07 08:33:10',NULL,'Finished','good');
INSERT INTO `os_historystep` VALUES (9,9,1,1,'good','2010-06-04 14:48:10','2010-06-07 08:53:06',NULL,'Finished','good');
INSERT INTO `os_historystep` VALUES (16,10,1,1,'good','2010-06-07 08:54:13','2010-06-07 08:54:17',NULL,'Finished','good');

#
# Table structure for table os_historystep_prev
#

DROP TABLE IF EXISTS `os_historystep_prev`;
CREATE TABLE `os_historystep_prev` (
  `id` bigint(20) NOT NULL auto_increment,
  `previous_id` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`id`,`previous_id`),
  KEY `previous_id` (`previous_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_historystep_prev
#


#
# Table structure for table os_membership
#

DROP TABLE IF EXISTS `os_membership`;
CREATE TABLE `os_membership` (
  `USERNAME` varchar(20) NOT NULL,
  `GROUPNAME` varchar(20) NOT NULL,
  PRIMARY KEY  (`USERNAME`,`GROUPNAME`),
  KEY `USERNAME` (`USERNAME`),
  KEY `GROUPNAME` (`GROUPNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_membership
#

INSERT INTO `os_membership` VALUES (NULL,'writer');
INSERT INTO `os_membership` VALUES ('aaa','editor');
INSERT INTO `os_membership` VALUES ('chengang','writer');
INSERT INTO `os_membership` VALUES ('designer','designer');
INSERT INTO `os_membership` VALUES ('designer1','designer');
INSERT INTO `os_membership` VALUES ('designer2','designer');
INSERT INTO `os_membership` VALUES ('editor1','editor');
INSERT INTO `os_membership` VALUES ('editor2','editor');
INSERT INTO `os_membership` VALUES ('editor21','editor');
INSERT INTO `os_membership` VALUES ('employee1','employee');
INSERT INTO `os_membership` VALUES ('ffff','writer');
INSERT INTO `os_membership` VALUES ('good','writer');
INSERT INTO `os_membership` VALUES ('newplayer','writer');
INSERT INTO `os_membership` VALUES ('newplayer2','writer');
INSERT INTO `os_membership` VALUES ('publisher1','publisher');
INSERT INTO `os_membership` VALUES ('publisher2','publisher');
INSERT INTO `os_membership` VALUES ('test','writer');
INSERT INTO `os_membership` VALUES ('wangfeng','designer');
INSERT INTO `os_membership` VALUES ('writer','writer');
INSERT INTO `os_membership` VALUES ('xxx','writer');
INSERT INTO `os_membership` VALUES ('xxxx','writer');
INSERT INTO `os_membership` VALUES ('xxxxx','writer');

#
# Table structure for table os_propertyentry
#

DROP TABLE IF EXISTS `os_propertyentry`;
CREATE TABLE `os_propertyentry` (
  `entity_name` varchar(125) NOT NULL,
  `entity_id` bigint(20) NOT NULL,
  `entity_key` varchar(255) NOT NULL,
  `key_type` int(11) default NULL,
  `boolean_val` int(1) default '0',
  `double_val` double default NULL,
  `string_val` varchar(255) default NULL,
  `long_val` bigint(20) default NULL,
  `int_val` int(11) default NULL,
  `date_val` date default NULL,
  PRIMARY KEY  (`entity_name`,`entity_id`,`entity_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_propertyentry
#

INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',1,'caller',5,0,0,'newplayer',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',2,'caller',5,0,0,'good',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',3,'caller',5,0,0,'newplayer',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',4,'caller',5,0,0,'newplayer',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',5,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',6,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',7,'caller',5,0,0,'newplayer',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',8,'caller',5,0,0,'good',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',9,'caller',5,0,0,'good',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',10,'caller',5,0,0,'good',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',12,'caller',5,0,0,'newplayer',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',13,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',14,'caller',5,0,0,'newplayer',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',15,'caller',5,0,0,'newplayer',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',16,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',17,'caller',5,0,0,'newplayer',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',19,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',22,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',23,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',24,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',34,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',35,'caller',5,0,0,'writer',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',37,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',38,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',39,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',40,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',41,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',49,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',51,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',52,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',57,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',58,'caller',5,0,0,'xxx',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',66,'testTrigger',5,0,0,'blahblah',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',67,'testTrigger',5,0,0,'blahblah',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',68,'testTrigger',5,0,0,'blahblah',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',69,'testTrigger',5,0,0,'blahblah',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',92,'testTrigger',5,0,0,'blahblah',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',93,'testTrigger',5,0,0,'blahblah',0,0,NULL);
INSERT INTO `os_propertyentry` VALUES ('OSWorkflowEntry',94,'testTrigger',5,0,0,'blahblah',0,0,NULL);

#
# Table structure for table os_user
#

DROP TABLE IF EXISTS `os_user`;
CREATE TABLE `os_user` (
  `USERNAME` varchar(100) NOT NULL,
  `PASSWORDHASH` mediumtext,
  PRIMARY KEY  (`USERNAME`),
  KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_user
#

INSERT INTO `os_user` VALUES (NULL,'z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('aaa','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('ccc','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('chengang','k8y2xRST6EmowRAICjt/FP/KTS2nKDvaJPjWQB+P2izMPogBGbr2fFZwBYuVrXh2iIypiEfUX2y5yZQJA5P9MQ==');
INSERT INTO `os_user` VALUES ('ddd','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('designer','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('designer1','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('designer2','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('editor1','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('editor2','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('editor21','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('employee1','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('ffff','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('good','ECWb/hpBBY5GcdBaO5Yrd0xLFpRrQEC8BVBZ0YWdXxXsTX8Byj91YWMtAK3WEi0zcCKhURdOW5zr9DAlS46fpw==');
INSERT INTO `os_user` VALUES ('newplayer','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('newplayer2','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('publisher1','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('publisher2','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('qqq','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('test','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('wangfeng','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('writer','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('www','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('xxx','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('xxxx','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('xxxxx','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');
INSERT INTO `os_user` VALUES ('zxc','z4PhNX7vuL3xVChQ1m2AB9Yg5AULVxXcg/SpIdNs6c5H0NE8XYXysP+DGNKHfuwvY7kxvUdBeoGlODJ6+SfaPg==');

#
# Table structure for table os_wfentry
#

DROP TABLE IF EXISTS `os_wfentry`;
CREATE TABLE `os_wfentry` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(60) default NULL,
  `state` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_wfentry
#

INSERT INTO `os_wfentry` VALUES (2,'example',1);
INSERT INTO `os_wfentry` VALUES (7,'example',1);
INSERT INTO `os_wfentry` VALUES (8,'example',1);
INSERT INTO `os_wfentry` VALUES (9,'example',1);
INSERT INTO `os_wfentry` VALUES (10,'example',1);

#
#  Foreign keys for table os_currentstep
#

ALTER TABLE `os_currentstep`
  ADD FOREIGN KEY (`entry_id`) REFERENCES `os_wfentry` (`id`);

#
#  Foreign keys for table os_currentstep_prev
#

ALTER TABLE `os_currentstep_prev`
  ADD FOREIGN KEY (`id`) REFERENCES `os_currentstep` (`id`),
  ADD FOREIGN KEY (`previous_id`) REFERENCES `os_historystep` (`id`);

#
#  Foreign keys for table os_historystep
#

ALTER TABLE `os_historystep`
  ADD FOREIGN KEY (`entry_id`) REFERENCES `os_wfentry` (`id`);

#
#  Foreign keys for table os_historystep_prev
#

ALTER TABLE `os_historystep_prev`
  ADD FOREIGN KEY (`id`) REFERENCES `os_historystep` (`id`),
  ADD FOREIGN KEY (`previous_id`) REFERENCES `os_historystep` (`id`);

#
#  Foreign keys for table os_membership
#

ALTER TABLE `os_membership`
  ADD FOREIGN KEY (`USERNAME`) REFERENCES `os_user` (`USERNAME`),
  ADD FOREIGN KEY (`GROUPNAME`) REFERENCES `os_group` (`GROUPNAME`);


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
