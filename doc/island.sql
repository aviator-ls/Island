/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.7.22-log : Database - island
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`island` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `island`;

/*Table structure for table `t_answer` */

DROP TABLE IF EXISTS `t_answer`;

CREATE TABLE `t_answer` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `content` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '回答内容',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '回答者id',
  `ask_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '问题id',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_ask` */

DROP TABLE IF EXISTS `t_ask`;

CREATE TABLE `t_ask` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '问题标题',
  `content` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '问题内容',
  `ask_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '问题状态 1:未解决 2:已解决 3:已过期',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '提问者id',
  `board_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '版块id',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='问题表';

/*Table structure for table `t_ask_tag` */

DROP TABLE IF EXISTS `t_ask_tag`;

CREATE TABLE `t_ask_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ask_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '问题id',
  `tag_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='问题、标签关联表';

/*Table structure for table `t_board` */

DROP TABLE IF EXISTS `t_board`;

CREATE TABLE `t_board` (
  `id` varchar(64) NOT NULL COMMENT '论坛版块id',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父版块id',
  `board_level` int(11) NOT NULL DEFAULT '1' COMMENT '层级',
  `name` varchar(150) NOT NULL COMMENT '论坛版块名',
  `board_desc` varchar(255) DEFAULT NULL COMMENT '论坛版块描述',
  `topic_num` int(11) NOT NULL DEFAULT '0' COMMENT '版块帖子总数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛版块表';

/*Table structure for table `t_board_user` */

DROP TABLE IF EXISTS `t_board_user`;

CREATE TABLE `t_board_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联表id',
  `board_id` varchar(64) NOT NULL COMMENT '论坛版块id',
  `user_id` varchar(64) NOT NULL COMMENT '论坛用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户管理版块关联表';

/*Table structure for table `t_config` */

DROP TABLE IF EXISTS `t_config`;

CREATE TABLE `t_config` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `config_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `config_value` varchar(200) COLLATE utf8_bin NOT NULL,
  `config_desc` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_login_log` */

DROP TABLE IF EXISTS `t_login_log`;

CREATE TABLE `t_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `user_id` varchar(64) NOT NULL COMMENT '发表者id',
  `ip` varchar(32) NOT NULL COMMENT '用户登录ip',
  `login_date` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='登录日志表';

/*Table structure for table `t_page_pic` */

DROP TABLE IF EXISTS `t_page_pic`;

CREATE TABLE `t_page_pic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img_path` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '图片路径',
  `alt` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `img_desc` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `img_type` tinyint(4) NOT NULL COMMENT '图片类型 1:首页轮播图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='主页轮播图片表';

/*Table structure for table `t_post` */

DROP TABLE IF EXISTS `t_post`;

CREATE TABLE `t_post` (
  `id` varchar(64) NOT NULL COMMENT '帖子id',
  `title` varchar(100) NOT NULL COMMENT '帖子标题',
  `content` longtext COMMENT '帖子内容',
  `views` int(11) NOT NULL DEFAULT '1' COMMENT '浏览数',
  `source` varchar(500) DEFAULT NULL COMMENT '来源',
  `source_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '来源,1:原创,2:转载,3:翻译',
  `reference` varchar(500) DEFAULT NULL COMMENT '引用资料',
  `board_id` varchar(64) DEFAULT NULL COMMENT '所属版块id',
  `user_id` varchar(64) NOT NULL COMMENT '发表用户id',
  `boutique` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是精品贴 不是:0,是:1',
  `special_id` varchar(64) DEFAULT NULL COMMENT '专辑id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_open` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1:公开 2:不公开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题帖';

/*Table structure for table `t_post_special` */

DROP TABLE IF EXISTS `t_post_special`;

CREATE TABLE `t_post_special` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '专辑名称',
  `special_desc` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '专辑描述',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='文章专辑表';

/*Table structure for table `t_post_tag` */

DROP TABLE IF EXISTS `t_post_tag`;

CREATE TABLE `t_post_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '帖子id',
  `tag_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_reply_post` */

DROP TABLE IF EXISTS `t_reply_post`;

CREATE TABLE `t_reply_post` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `content` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '回复内容',
  `main_post_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主题帖id',
  `main_reply_post_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '回复贴id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '回复用户id',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_session` */

DROP TABLE IF EXISTS `t_session`;

CREATE TABLE `t_session` (
  `session_id` varchar(100) COLLATE utf8_bin NOT NULL,
  `session_serialize` varchar(500) COLLATE utf8_bin NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_sys_resource` */

DROP TABLE IF EXISTS `t_sys_resource`;

CREATE TABLE `t_sys_resource` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '权限资源id',
  `resource_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '资源名称',
  `resource_type` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源类型',
  `priority` int(11) DEFAULT NULL COMMENT '显示顺序',
  `parent_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父角色id',
  `parent_ids` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '父角色编号',
  `permission` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '权限字符串',
  `available` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可用 0:不可用 1:可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限资源表';

/*Table structure for table `t_sys_role` */

DROP TABLE IF EXISTS `t_sys_role`;

CREATE TABLE `t_sys_role` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `role_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '角色描述',
  `available` tinyint(4) DEFAULT '1' COMMENT '是否可用 0:不可用 1:可用',
  PRIMARY KEY (`id`,`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

/*Table structure for table `t_sys_role_resource` */

DROP TABLE IF EXISTS `t_sys_role_resource`;

CREATE TABLE `t_sys_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '资源权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_sys_user` */

DROP TABLE IF EXISTS `t_sys_user`;

CREATE TABLE `t_sys_user` (
  `id` varchar(64) NOT NULL COMMENT '用户id',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `user_password` varchar(64) NOT NULL COMMENT '用户密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `profile_picture` varchar(200) DEFAULT NULL COMMENT '头像',
  `user_type` int(4) NOT NULL DEFAULT '1' COMMENT '1:普通用户,2:管理员',
  `locked` int(4) NOT NULL DEFAULT '0' COMMENT '0:未锁定,1:锁定',
  `credit` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `salt` varchar(100) DEFAULT NULL COMMENT '盐',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛用户表';

/*Table structure for table `t_sys_user_role` */

DROP TABLE IF EXISTS `t_sys_user_role`;

CREATE TABLE `t_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_tag` */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `tag_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '标签名称',
  `tag_desc` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '标签描述',
  `path` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `tag_img` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '标签图标',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者id',
  `update_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag_name` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='标签表';

/*Table structure for table `t_topic` */

DROP TABLE IF EXISTS `t_topic`;

CREATE TABLE `t_topic` (
  `id` varchar(64) NOT NULL COMMENT '帖子id',
  `board_id` varchar(64) NOT NULL COMMENT '所属版块id',
  `user_id` varchar(64) NOT NULL COMMENT '发表用户id',
  `title` varchar(100) NOT NULL COMMENT '帖子标题',
  `views` int(11) NOT NULL DEFAULT '1' COMMENT '浏览数',
  `replies` int(11) NOT NULL DEFAULT '0' COMMENT '回复数',
  `digest` int(4) NOT NULL DEFAULT '0' COMMENT '0:不是精华话题,1:是精华话题',
  `create_time` datetime NOT NULL COMMENT '发表时间',
  `update_time` datetime NOT NULL COMMENT '最后回复时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `board_id` (`board_id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话题表';

/*Table structure for table `t_user_ask_collect` */

DROP TABLE IF EXISTS `t_user_ask_collect`;

CREATE TABLE `t_user_ask_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `ask_id` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户问答收藏关联表';

/*Table structure for table `t_user_post_collect` */

DROP TABLE IF EXISTS `t_user_post_collect`;

CREATE TABLE `t_user_post_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `post_id` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户收藏文章表';

/*Table structure for table `t_user_user_follow` */

DROP TABLE IF EXISTS `t_user_user_follow`;

CREATE TABLE `t_user_user_follow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `follower_id` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='关注用户表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
