# MySQL-Front 5.1  (Build 1.5)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;


# Host: 127.0.0.1    Database: osworkflow
# ------------------------------------------------------
# Server version 5.0.67-community-nt

DROP DATABASE IF EXISTS `osworkflow`;
CREATE DATABASE `osworkflow` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `osworkflow`;

#
# Source for table os_currentstep
#

CREATE TABLE `os_currentstep` (
  `ID` bigint(20) NOT NULL default '0' COMMENT '当前步骤id',
  `ENTRY_ID` bigint(20) default NULL COMMENT '工作流程实例ID，与os_wfentry的ID相关',
  `STEP_ID` int(11) default NULL COMMENT '当前步骤ID',
  `ACTION_ID` int(11) default NULL COMMENT '当前动作ID',
  `OWNER` varchar(35) default NULL COMMENT '当前状态下流程所有者，与os_user用户ID关联',
  `START_DATE` datetime default NULL COMMENT '当前流程开始时间',
  `FINISH_DATE` datetime default NULL COMMENT '当前流程结束时间',
  `DUE_DATE` datetime default NULL,
  `STATUS` varchar(40) default NULL COMMENT '流程当前状态',
  `CALLER` varchar(35) default NULL COMMENT '当前流程调用者，与os_user用户ID关联',
  PRIMARY KEY  (`ID`),
  KEY `ENTRY_ID` (`ENTRY_ID`),
  KEY `OWNER` (`OWNER`),
  KEY `CALLER` (`CALLER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_currentstep
#


#
# Source for table os_currentstep_prev
#

CREATE TABLE `os_currentstep_prev` (
  `ID` bigint(20) NOT NULL default '0' COMMENT '当前步骤ID，与os_currentstep的ID作关联',
  `PREVIOUS_ID` bigint(20) NOT NULL default '0' COMMENT '当前步骤之前步骤ID，与os_historystep的ID作关联',
  PRIMARY KEY  (`ID`,`PREVIOUS_ID`),
  KEY `ID` (`ID`),
  KEY `PREVIOUS_ID` (`PREVIOUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_currentstep_prev
#


#
# Source for table os_entryids
#

CREATE TABLE `os_entryids` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '由数据库自动生成的workflow_entry_id。供os_wfentry表用',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

#
# Dumping data for table os_entryids
#


#
# Source for table os_historystep
#

CREATE TABLE `os_historystep` (
  `ID` bigint(20) NOT NULL default '0' COMMENT '历史步骤的ID号',
  `ENTRY_ID` bigint(20) default NULL COMMENT '工作流程实例ID，与os_wfentry的ID相关联',
  `STEP_ID` int(11) default NULL COMMENT '历史步骤ID',
  `ACTION_ID` int(11) default NULL COMMENT '历史动作ID',
  `OWNER` varchar(35) default NULL COMMENT '历史状态下流程所有者，与os_user用户ID关联',
  `START_DATE` datetime default NULL COMMENT '历史流程开始时间',
  `FINISH_DATE` datetime default NULL COMMENT '历史流程结束时间',
  `DUE_DATE` datetime default NULL,
  `STATUS` varchar(40) default NULL COMMENT '流程历史状态',
  `CALLER` varchar(35) default NULL COMMENT '历史流程调用者，与os_user用户ID关联',
  PRIMARY KEY  (`ID`),
  KEY `ENTRY_ID` (`ENTRY_ID`),
  KEY `OWNER` (`OWNER`),
  KEY `CALLER` (`CALLER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_historystep
#


#
# Source for table os_historystep_prev
#

CREATE TABLE `os_historystep_prev` (
  `ID` bigint(20) NOT NULL default '0' COMMENT '历史步骤ID，与os_historystep的ID作关联',
  `PREVIOUS_ID` bigint(20) NOT NULL default '0' COMMENT '历史步骤之前步骤ID，与os_historystep的ID作关联',
  PRIMARY KEY  (`ID`,`PREVIOUS_ID`),
  KEY `ID` (`ID`),
  KEY `PREVIOUS_ID` (`PREVIOUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_historystep_prev
#


#
# Source for table os_propertyentry
#

CREATE TABLE `os_propertyentry` (
  `GLOBAL_KEY` varchar(250) NOT NULL default '' COMMENT '全局变量名，如果为JDBCWorkflowStore即为osff_加上流程实例ID；如果为JDBCTemplateWorkflowStore即为osff_temp_加上流程实例ID',
  `ITEM_KEY` varchar(250) NOT NULL default '' COMMENT '局部变量名，通常是指真正要在程序中调用的”key”',
  `ITEM_TYPE` tinyint(3) default NULL COMMENT '以数字标明其property的类型。如5是String型',
  `STRING_VALUE` varchar(255) default NULL,
  `DATE_VALUE` datetime default NULL COMMENT '如果是Date类型的数据，存入这个字段里',
  `DATA_VALUE` blob,
  `FLOAT_VALUE` float default NULL COMMENT '如果是Float类型的数据，存入这个字段里',
  `NUMBER_VALUE` bigint(10) default NULL COMMENT '如果是Number类型的数据，存入这个字段里',
  PRIMARY KEY  (`GLOBAL_KEY`,`ITEM_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_propertyentry
#


#
# Source for table os_stepids
#

CREATE TABLE `os_stepids` (
  `ID` bigint(20) NOT NULL auto_increment COMMENT '由数据库自动生成的step_id。供os__currentstep表或os_historytstep表使用',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;

#
# Dumping data for table os_stepids
#


#
# Source for table os_wfentry
#

CREATE TABLE `os_wfentry` (
  `ID` bigint(20) NOT NULL default '0' COMMENT '流程的ID号',
  `NAME` varchar(60) default NULL COMMENT '流程名称',
  `STATE` int(11) default NULL COMMENT '流程状态。有五种：CREATED = 0;ACTIVATED = 1;SUSPENDED = 2;KILLED = 3;COMPLETED = 4;UNKNOWN = -1',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table os_wfentry
#


#
#  Foreign keys for table os_currentstep
#

ALTER TABLE `os_currentstep`
ADD CONSTRAINT `os_currentstep_ibfk_1` FOREIGN KEY (`ENTRY_ID`) REFERENCES `os_wfentry` (`ID`);

#
#  Foreign keys for table os_currentstep_prev
#

ALTER TABLE `os_currentstep_prev`
ADD CONSTRAINT `os_currentstep_prev_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `os_currentstep` (`ID`),
ADD CONSTRAINT `os_currentstep_prev_ibfk_2` FOREIGN KEY (`PREVIOUS_ID`) REFERENCES `os_historystep` (`ID`);

#
#  Foreign keys for table os_historystep
#

ALTER TABLE `os_historystep`
ADD CONSTRAINT `os_historystep_ibfk_1` FOREIGN KEY (`ENTRY_ID`) REFERENCES `os_wfentry` (`ID`);

#
#  Foreign keys for table os_historystep_prev
#

ALTER TABLE `os_historystep_prev`
ADD CONSTRAINT `os_historystep_prev_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `os_historystep` (`ID`),
ADD CONSTRAINT `os_historystep_prev_ibfk_2` FOREIGN KEY (`PREVIOUS_ID`) REFERENCES `os_historystep` (`ID`);


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
