CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  `salt` varchar(50) DEFAULT NULL COMMENT '盐值',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(200) DEFAULT NULL COMMENT '用户头像地址',
  `role` int(2) DEFAULT NULL COMMENT '角色0-普通用户,1-管理员',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE,
  UNIQUE KEY `email_unique` (`email`),
  UNIQUE KEY `nickname_unique` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=100030 DEFAULT CHARSET=utf8 COMMENT='用户表';

