# drop table if exists `act_excute_task`;
create table `act_excute_task` (
  `id` bigint(20) not null comment '主键',
  `batch_id` bigint(20) null default null comment '批次号，验证任务调用时存在',
  `proc_release_id` bigint(20) not null comment '模型版本id',
  `proc_inst_id` varchar(64) character set utf8 collate utf8_bin null default null comment '流程运行实例id',
  `status` varchar(32) character set utf8 collate utf8_bin null default null comment '任务状态，0-待执行，1-启动成功，2-执行完成，3-执行异常',
  `type` varchar(32) character set utf8 collate utf8_bin null default null comment '任务类型，0-验证任务，1-业务系统调用',
  `in_paramter` longtext character set utf8 collate utf8_bin null comment '入参',
  `out_paramter` longtext character set utf8 collate utf8_bin null comment '出参，MQ message内容',
  `spend_time` bigint(20) null default null comment '花费时间',
  `remark` varchar(5000) character set utf8 collate utf8_bin null default null comment '备注',
  `create_time` datetime not null comment '创建时间',
  `create_user` varchar(64) character set utf8 collate utf8_bin null default null comment '创建用户',
  `update_time` datetime null default null comment '结束时间',
  primary key (`id`)
) engine = innodb character set = utf8 collate utf8_bin COMMENT '模型版本记录表';

# drop table if exists `act_proc_release`;
create table `act_proc_release` (
  `id` bigint(20) not null comment '主键',
  `model_id` varchar(64) character set utf8 collate utf8_bin not null comment '模型id，与 act_re_model.id_ 关联',
  `model_code` varchar(64) character set utf8 collate utf8_bin not null comment '模型编码，与 act_re_model.key_ 关联',
  `model_procdef_id` varchar(64) character set utf8 collate utf8_bin not null comment '模型定义id，与 act_re_procdef.id_ 关联,act_re_procdef 表中有模型部署id',
  `model_name` varchar(255) character set utf8 collate utf8_bin null default null comment '模型名称',
  `model_version` varchar(64) character set utf8 collate utf8_bin null default null comment '模型版本',
  `model_category` varchar(64) character set utf8 collate utf8_bin null default null comment '模型分类',
  `version_type` varchar(2) character set utf8 collate utf8_bin null default null comment '版本类型，0-测试版，1-正式版',
  `is_bind` char(1) character set utf8 collate utf8_bin not null default '0' comment '是否绑定： 0-未绑定，1-已绑定;',
  `is_validate` char(1) character set utf8 collate utf8_bin not null default '0' comment '是否验证通过： 0-待验证，1-验证通过，2-验证不通过；默认为0;',
  `is_auto_validate` char(1) character set utf8 collate utf8_bin not null default '0' comment '是否自动验证通过： 0-待验证，1-验证通过，2-验证不通过；默认为0;',
  `is_manual_validate` char(1) character set utf8 collate utf8_bin not null default '0' comment '是否手动验证通过： 0-待验证，1-验证通过，2-验证不通过；默认为0;',
  `is_approve` char(1) character set utf8 collate utf8_bin not null default '0' comment '是否审核通过：0-待审核，1-审核通过，2-审核不通过；默认为0;',
  `approve_task_id` bigint(20)  comment '模型验证关联任务Id',
  `is_effect` varchar(2) character set utf8 collate utf8_bin not null default '0' comment '是否生效：0-有效，1-无效',
  `update_time` datetime null default null comment '更新时间',
  `update_user` varchar(64) character set utf8 collate utf8_bin null default null comment '更新用户',
  `create_time` datetime not null comment '创建时间',
  `create_user` varchar(64) character set utf8 collate utf8_bin null default null comment '创建用户',
  primary key (`id`)
) engine = innodb character set = utf8 collate utf8_bin;


# drop table if exists `act_model_definition`;
create table act_model_definition(
	id bigint(20) NOT NULL COMMENT '主键',
	model_id varchar(50) not null comment '模型id',
	model_code varchar(200) not null comment '模型编码',
	model_name varchar(200) not null comment '模型名称',
	belong_system varchar(200) not null comment '所属系统Code',
	belong_system_name varchar(200) not null comment '所属系统名称',
	business_id varchar(200) not null comment '业务线',
	model_desc varchar(500) comment '模型描述',
	`status`  varchar(10) comment '状态',
	cre_user_id varchar(50) comment '创建人id',
	cre_time datetime not null default now()  comment '创建时间',
	upd_time datetime comment '修改时间'
) engine = innodb character set = utf8 collate utf8_bin;



	# drop table if exists `act_process_audit_his`;
	create table act_process_audit_his(
		id bigint(20) NOT NULL COMMENT '主键',
		task_id varchar(50) not null comment '任务id',
		task_name varchar(50) not null comment '任务名',
		pro_inst_id varchar(50) not null comment '流程实例id',
		pro_define_id varchar(100) not null comment '流程定义id',
		task_define_key varchar(100) not null  comment '任务定义key',
		assignee varchar(100) not null  comment '节点审批人',
		opinion varchar(500) not null  comment '审批意见',
		status varchar(50) comment '状态，completed-完成',
		cre_user_id varchar(50) comment '执行人id',
		cre_time datetime not null default now()  comment '审批时间'
	) engine = innodb character set = utf8 collate utf8_bin comment '流程审批历史';

# drop table if exists `act_process_jump_his`;
CREATE TABLE `act_process_jump_his` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `pro_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '流程名',
  `proc_def_id` varchar(70) COLLATE utf8_bin NOT NULL COMMENT '流程定义id',
  `proc_inst_id` varchar(70) COLLATE utf8_bin NOT NULL COMMENT '实例id',
  `source_task_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '跳转原节点id',
  `source_task_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '跳转原节点Name',
  `target_task_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '跳转目标节点id',
  `target_task_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '跳转目标节点Name',
  `cre_user_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '执行人id',
  `cre_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '跳转时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='流程回退历史';

# drop table if exists `act_model_call_log`;
create table act_model_call_log(
	id bigint(20)  NOT NULL COMMENT '主键',
	business_key varchar(50) not null comment '业务key',
	process_defined_key varchar(200) not null comment '模型编码',
	version varchar(20) null comment '模型版本',
	pro_inst_id varchar(200) not null comment '实例id',
	model_procdef_id varchar(200) not null comment '模型定义id',
	sys_code varchar(50) not null comment '所属系统编码',
	sys_name varchar(50) not null comment '所属系统名称',
	is_end varchar(10) not null comment '流程是否完成,0-Y,1-N',
	`status`  varchar(10) default '0' comment '状态 0-y,1-n',
	cre_time datetime not null default now()  comment '创建时间'
) engine = innodb character set = utf8 collate utf8_bin ;

create table act_model_call_log_param(
	id bigint(20)  NOT NULL COMMENT '主键',
	foreign_id varchar(64) not null comment '外键id',
	datas text comment '模型参数'
) engine = innodb character set = utf8 collate utf8_bin ;

DROP TABLE IF EXISTS `activiti_file_type`;
CREATE TABLE `activiti_file_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_type_code` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '文件类型编码',
  `file_type_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件類型名稱',
  `lfile_type_level` int(11) DEFAULT NULL COMMENT '层级',
  `parent_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '父类型编码',
  `filt_type_path` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '文件类型路径',
  `order_no` int(11) DEFAULT NULL COMMENT '排序字段',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `lock_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '乐观锁',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户名称',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户名称',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `create_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人机构',
  `create_org_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人机构名称',
  `update_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人机构',
  `update_org_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人机构名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '文件类型配置表' ROW_FORMAT = Compact;


drop table if EXISTS `id_sequence` ;
CREATE TABLE `id_sequence` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `module_id` VARCHAR(128) COLLATE utf8_unicode_ci NOT NULL,
  `current_value` BIGINT(20) DEFAULT NULL,
  `modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` VARCHAR(2000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `jpa_version` INT(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `module_id` (`module_id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO activiti_file_type(id, file_type_code, file_type_name, lfile_type_level, parent_code, filt_type_path, order_no, remark, lock_no, create_user, create_user_name, create_time, update_user, update_user_name, update_time, create_org_code, create_org_name, update_org_code, update_org_name) VALUES (1, '001', '业务线配置', 1, '000', '1', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

