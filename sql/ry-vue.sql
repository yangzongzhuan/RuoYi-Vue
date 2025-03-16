/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : ry-vue

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 15/03/2025 17:07:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (1, 'psd_task', '', NULL, NULL, 'PsdTask', 'crud', '', 'com.ruoyi.system', 'system', 'task', NULL, 'ruoyi', '0', '/', NULL, 'admin', '2025-03-12 17:05:54', '', NULL, NULL);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(0) NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (1, 1, 'id', NULL, 'int', 'Long', 'id', '1', '0', '0', '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2025-03-12 17:05:54', '', NULL);
INSERT INTO `gen_table_column` VALUES (2, 1, 'task_name', '任务名称', 'varchar(255)', 'String', 'taskName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2025-03-12 17:05:54', '', NULL);
INSERT INTO `gen_table_column` VALUES (3, 1, 'username', '作图账号名称', 'varchar(255)', 'String', 'username', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'admin', '2025-03-12 17:05:54', '', NULL);
INSERT INTO `gen_table_column` VALUES (4, 1, 'template_name', '模板名称', 'varchar(255)', 'String', 'templateName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', '', 4, 'admin', '2025-03-12 17:05:55', '', NULL);
INSERT INTO `gen_table_column` VALUES (5, 1, 'image_count', '图片生产数量，[1,2,3] 表示图一1张，图二两张', 'varchar(255)', 'String', 'imageCount', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2025-03-12 17:05:55', '', NULL);

-- ----------------------------
-- Table structure for psd_task
-- ----------------------------
DROP TABLE IF EXISTS `psd_task`;
CREATE TABLE `psd_task`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务名称',
  `account_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作图账号名称',
  `template_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模板名称',
  `image_count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片生产数量，[1,2,3] 表示图一1张，图二两张',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for psd_template
-- ----------------------------
DROP TABLE IF EXISTS `psd_template`;
CREATE TABLE `psd_template`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psd_template
-- ----------------------------
INSERT INTO `psd_template` VALUES (1, '{\"baseConfig\":{\"templateName\":\"正文靠左 三个字 无姓氏\",\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字，textLayer1 是名字， textLayer2是名字描述\"},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"林星然\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字，textLayer1 是名字， textLayer2是名字描述\"}]}');
INSERT INTO `psd_template` VALUES (2, '{\"baseConfig\":{\"templateName\":\"测试\",\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15,\"prompt\":null}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\"}]}');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2025-03-08 23:02:43', '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2025-03-08 23:02:43', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2025-03-08 23:02:43', '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'true', 'Y', 'admin', '2025-03-08 23:02:43', '', NULL, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2025-03-08 23:02:43', '', NULL, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (6, '用户登录-黑名单列表', 'sys.login.blackIPList', '', 'Y', 'admin', '2025-03-08 23:02:43', '', NULL, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '若依科技', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:41', '', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:41', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:41', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:42', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:42', '', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:42', '', NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:42', '', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:42', '', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:42', '', NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2025-03-08 23:02:42', '', NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(0) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '停用状态');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2025-03-08 23:02:43', '', NULL, '登录状态列表');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2025-03-08 23:02:43', '', NULL, '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2025-03-08 23:02:43', '', NULL, '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2025-03-08 23:02:43', '', NULL, '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  INDEX `idx_sys_logininfor_s`(`status`) USING BTREE,
  INDEX `idx_sys_logininfor_lt`(`login_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (100, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-08 23:06:05');
INSERT INTO `sys_logininfor` VALUES (101, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-09 17:12:04');
INSERT INTO `sys_logininfor` VALUES (102, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-10 22:46:01');
INSERT INTO `sys_logininfor` VALUES (103, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-11 23:09:34');
INSERT INTO `sys_logininfor` VALUES (104, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-13 20:06:51');
INSERT INTO `sys_logininfor` VALUES (105, 'admin', '192.168.0.108', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-13 20:09:27');
INSERT INTO `sys_logininfor` VALUES (106, 'admin', '192.168.0.108', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-13 21:09:30');
INSERT INTO `sys_logininfor` VALUES (107, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-13 23:31:02');
INSERT INTO `sys_logininfor` VALUES (108, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-15 01:02:16');
INSERT INTO `sys_logininfor` VALUES (109, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-15 14:12:55');
INSERT INTO `sys_logininfor` VALUES (110, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '登录成功', '2025-03-15 16:36:39');
INSERT INTO `sys_logininfor` VALUES (111, 'admin', '127.0.0.1', '内网IP', 'Chrome 13', 'Windows 10', '0', '退出成功', '2025-03-15 16:36:52');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由名称',
  `is_frame` int(0) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(0) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2008 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2025-03-08 23:02:42', '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, '', '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2025-03-08 23:02:42', '', NULL, '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, '', '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2025-03-08 23:02:42', '', NULL, '系统工具目录');
INSERT INTO `sys_menu` VALUES (4, '若依官网', 0, 4, 'http://ruoyi.vip', NULL, '', '', 0, 0, 'M', '0', '0', '', 'guide', 'admin', '2025-03-08 23:02:42', '', NULL, '若依官网地址');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2025-03-08 23:02:42', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2025-03-08 23:02:42', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2025-03-08 23:02:42', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2025-03-08 23:02:42', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2025-03-08 23:02:42', '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2025-03-08 23:02:42', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2025-03-08 23:02:42', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2025-03-08 23:02:42', '', NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2025-03-08 23:02:42', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2025-03-08 23:02:42', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2025-03-08 23:02:42', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2025-03-08 23:02:42', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2025-03-08 23:02:42', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2025-03-08 23:02:42', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', '2025-03-08 23:02:42', '', NULL, '缓存列表菜单');
INSERT INTO `sys_menu` VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', '', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2025-03-08 23:02:42', '', NULL, '表单构建菜单');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2025-03-08 23:02:42', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2025-03-08 23:02:42', '', NULL, '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2025-03-08 23:02:42', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2025-03-08 23:02:42', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 116, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 116, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 116, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 116, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 116, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 116, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '账号模板创建', 0, 100, 'AccountTemplate', 'custom/psd/AccountTemplate/index', NULL, '', 1, 0, 'C', '0', '0', NULL, 'documentation', 'admin', '2025-03-10 23:21:09', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2001, 'psd模板管理', 0, 200, 'psd', 'custom/psd/index', NULL, '', 1, 0, 'C', '0', '0', '', 'documentation', 'admin', '2025-03-10 23:21:55', 'admin', '2025-03-10 23:23:09', '');
INSERT INTO `sys_menu` VALUES (2002, 'psd任务创建', 0, 300, 'task', 'custom/task/index', NULL, '', 1, 0, 'C', '0', '0', 'psd:task:list', '#', 'admin', '2025-03-12 17:14:21', 'admin', '2025-03-13 10:06:35', '【请填写功能名称】菜单');
INSERT INTO `sys_menu` VALUES (2003, 'psd任务查询', 2002, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'psd:task:query', '#', 'admin', '2025-03-12 17:15:23', 'admin', '2025-03-13 10:06:45', '');
INSERT INTO `sys_menu` VALUES (2004, 'psd任务新增', 2002, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'psd:task:add', '#', 'admin', '2025-03-12 17:15:23', 'admin', '2025-03-13 10:06:50', '');
INSERT INTO `sys_menu` VALUES (2005, 'psd任务修改', 2002, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'psd:task:edit', '#', 'admin', '2025-03-12 17:15:23', 'admin', '2025-03-13 10:06:53', '');
INSERT INTO `sys_menu` VALUES (2006, 'psd任务删除', 2002, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'psd:task:remove', '#', 'admin', '2025-03-12 17:15:23', 'admin', '2025-03-13 10:06:57', '');
INSERT INTO `sys_menu` VALUES (2007, 'psd任务导出', 2002, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'psd:task:export', '#', 'admin', '2025-03-12 17:15:23', 'admin', '2025-03-13 10:07:00', '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2025-03-08 23:02:43', '', NULL, '管理员');
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2025-03-08 23:02:43', '', NULL, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(0) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(0) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(0) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(0) NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `idx_sys_oper_log_bt`(`business_type`) USING BTREE,
  INDEX `idx_sys_oper_log_s`(`status`) USING BTREE,
  INDEX `idx_sys_oper_log_ot`(`oper_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (100, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"custom/psd/AccountTemplate/index\",\"createBy\":\"admin\",\"icon\":\"documentation\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"账号模板创建\",\"menuType\":\"C\",\"orderNum\":100,\"params\":{},\"parentId\":0,\"path\":\"AccountTemplate\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-10 23:21:09', 73);
INSERT INTO `sys_oper_log` VALUES (101, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"cunstom/psd/index\",\"createBy\":\"admin\",\"icon\":\"documentation\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"psd模板管理\",\"menuType\":\"C\",\"orderNum\":200,\"params\":{},\"parentId\":0,\"path\":\"psd\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-10 23:21:55', 30);
INSERT INTO `sys_oper_log` VALUES (102, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '研发部门', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"custom/psd/index\",\"createTime\":\"2025-03-10 23:21:55\",\"icon\":\"documentation\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2001,\"menuName\":\"psd模板管理\",\"menuType\":\"C\",\"orderNum\":200,\"params\":{},\"parentId\":0,\"path\":\"psd\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-10 23:23:09', 17);
INSERT INTO `sys_oper_log` VALUES (103, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', '赛尔克斯', '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"psd_task\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-12 17:05:55', 118);
INSERT INTO `sys_oper_log` VALUES (104, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', '赛尔克斯', '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"psd_task\"}', NULL, 0, NULL, '2025-03-12 17:05:56', 306);
INSERT INTO `sys_oper_log` VALUES (105, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '赛尔克斯', '/psd/task', '127.0.0.1', '内网IP', '{\"params\":{},\"taskName\":\"测试\",\"templateName\":\"正文靠左 三个字 无姓氏\",\"username\":\"liuxianying\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\r\n### The error may exist in file [C:\\JavaProject\\RuoYi-Vue\\ruoyi-system\\target\\classes\\mapper\\system\\PsdTaskMapper.xml]\r\n### The error may involve com.ruoyi.system.mapper.PsdTaskMapper.insertPsdTask-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into psd_task          ( task_name,             username,             template_name )           values ( ?,             ?,             ? )\r\n### Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\n; Field \'id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'id\' doesn\'t have a default value', '2025-03-12 17:38:39', 10635);
INSERT INTO `sys_oper_log` VALUES (106, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '赛尔克斯', '/psd/task', '127.0.0.1', '内网IP', '{\"params\":{},\"taskName\":\"测试\",\"templateName\":\"正文靠左 三个字 无姓氏\",\"username\":\"liuxianying\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-12 17:42:11', 16278);
INSERT INTO `sys_oper_log` VALUES (107, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '赛尔克斯', '/psd/task', '127.0.0.1', '内网IP', '{\"params\":{},\"taskName\":\"测试\",\"templateName\":\"正文靠左 三个字 无姓氏\",\"username\":\"liuxianying\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-12 17:44:35', 14219);
INSERT INTO `sys_oper_log` VALUES (108, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '赛尔克斯', '/psd/task', '127.0.0.1', '内网IP', '{\"params\":{},\"taskName\":\"测试1\",\"templateName\":\"正文靠左 三个字 无姓氏\",\"username\":\"liuxianying\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-12 17:45:24', 7196);
INSERT INTO `sys_oper_log` VALUES (109, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '赛尔克斯', '/psd/task', '127.0.0.1', '内网IP', '{\"params\":{},\"taskName\":\"测试\",\"templateName\":\"正文靠左 三个字 无姓氏\",\"username\":\"liuxianying\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-12 17:47:50', 16108);
INSERT INTO `sys_oper_log` VALUES (110, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '赛尔克斯', '/psd/task', '127.0.0.1', '内网IP', '{\"params\":{},\"taskName\":\"测试3\",\"templateName\":\"正文靠左 三个字 无姓氏\",\"username\":\"liuxianying\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-12 17:48:59', 10084);
INSERT INTO `sys_oper_log` VALUES (111, 'psd任务', 3, 'com.ruoyi.web.controller.custom.PsdTaskController.remove()', 'DELETE', 1, 'admin', '赛尔克斯', '/psd/task/2,3,4,5', '127.0.0.1', '内网IP', '[2,3,4,5]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-12 17:49:04', 38);
INSERT INTO `sys_oper_log` VALUES (112, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '深圳科晶智达科技有限公司', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"custom/task/index\",\"createTime\":\"2025-03-12 17:14:21\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2002,\"menuName\":\"psd任务创建\",\"menuType\":\"C\",\"orderNum\":300,\"params\":{},\"parentId\":0,\"path\":\"task\",\"perms\":\"psd:task:list\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 10:06:35', 35);
INSERT INTO `sys_oper_log` VALUES (113, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '深圳科晶智达科技有限公司', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"\",\"createTime\":\"2025-03-12 17:15:23\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2003,\"menuName\":\"psd任务查询\",\"menuType\":\"F\",\"orderNum\":1,\"params\":{},\"parentId\":2002,\"path\":\"#\",\"perms\":\"psd:task:query\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 10:06:45', 10);
INSERT INTO `sys_oper_log` VALUES (114, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '深圳科晶智达科技有限公司', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"\",\"createTime\":\"2025-03-12 17:15:23\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2004,\"menuName\":\"psd任务新增\",\"menuType\":\"F\",\"orderNum\":2,\"params\":{},\"parentId\":2002,\"path\":\"#\",\"perms\":\"psd:task:add\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 10:06:50', 12);
INSERT INTO `sys_oper_log` VALUES (115, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '深圳科晶智达科技有限公司', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"\",\"createTime\":\"2025-03-12 17:15:23\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2005,\"menuName\":\"psd任务修改\",\"menuType\":\"F\",\"orderNum\":3,\"params\":{},\"parentId\":2002,\"path\":\"#\",\"perms\":\"psd:task:edit\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 10:06:53', 13);
INSERT INTO `sys_oper_log` VALUES (116, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '深圳科晶智达科技有限公司', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"\",\"createTime\":\"2025-03-12 17:15:23\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2006,\"menuName\":\"psd任务删除\",\"menuType\":\"F\",\"orderNum\":4,\"params\":{},\"parentId\":2002,\"path\":\"#\",\"perms\":\"psd:task:remove\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 10:06:57', 11);
INSERT INTO `sys_oper_log` VALUES (117, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', '深圳科晶智达科技有限公司', '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"\",\"createTime\":\"2025-03-12 17:15:23\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2007,\"menuName\":\"psd任务导出\",\"menuType\":\"F\",\"orderNum\":5,\"params\":{},\"parentId\":2002,\"path\":\"#\",\"perms\":\"psd:task:export\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 10:07:00', 10);
INSERT INTO `sys_oper_log` VALUES (118, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column \'accountName\' in \'field list\'\r\n### The error may exist in file [C:\\JavaProject\\RuoYi-Vue\\ruoyi-system\\target\\classes\\mapper\\system\\PsdTaskMapper.xml]\r\n### The error may involve com.ruoyi.system.mapper.PsdTaskMapper.insertPsdTask-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into psd_task          ( task_name,             accountName,             template_name )           values ( ?,             ?,             ? )\r\n### Cause: java.sql.SQLSyntaxErrorException: Unknown column \'accountName\' in \'field list\'\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column \'accountName\' in \'field list\'', '2025-03-13 11:55:48', 10282);
INSERT INTO `sys_oper_log` VALUES (119, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column \'accountName\' in \'field list\'\r\n### The error may exist in file [C:\\JavaProject\\RuoYi-Vue\\ruoyi-system\\target\\classes\\mapper\\system\\PsdTaskMapper.xml]\r\n### The error may involve com.ruoyi.system.mapper.PsdTaskMapper.insertPsdTask-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into psd_task          ( task_name,             accountName,             template_name )           values ( ?,             ?,             ? )\r\n### Cause: java.sql.SQLSyntaxErrorException: Unknown column \'accountName\' in \'field list\'\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column \'accountName\' in \'field list\'', '2025-03-13 11:57:07', 12726);
INSERT INTO `sys_oper_log` VALUES (120, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"params\":{},\"taskName\":\"我\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 14:37:15', 111570);
INSERT INTO `sys_oper_log` VALUES (121, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', NULL, 1, 'DeepSeek返回非标准JSON格式', '2025-03-13 15:02:31', 29158);
INSERT INTO `sys_oper_log` VALUES (122, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 15:08:26', 61740);
INSERT INTO `sys_oper_log` VALUES (123, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 15:18:57', 286548);
INSERT INTO `sys_oper_log` VALUES (124, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"params\":{},\"taskName\":\"达瓦达瓦\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 15:29:58', 51992);
INSERT INTO `sys_oper_log` VALUES (125, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 15:35:00', 50953);
INSERT INTO `sys_oper_log` VALUES (126, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"params\":{}}', NULL, 1, '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\' at line 1\r\n### The error may exist in file [C:\\JavaProject\\RuoYi-Vue\\ruoyi-system\\target\\classes\\mapper\\system\\PsdTaskMapper.xml]\r\n### The error may involve com.ruoyi.system.mapper.PsdTaskMapper.insertPsdTask-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into psd_task\r\n### Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\' at line 1\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\' at line 1', '2025-03-13 15:40:07', 93889);
INSERT INTO `sys_oper_log` VALUES (127, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"params\":{},\"taskName\":\"dw\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 15:43:19', 65242);
INSERT INTO `sys_oper_log` VALUES (128, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"params\":{},\"taskName\":\"dwa\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 15:50:00', 42490);
INSERT INTO `sys_oper_log` VALUES (129, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"测试1\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"测试哇达瓦达瓦梵蒂冈梵蒂冈梵蒂冈反对广泛的\",\"maxCharsPerLine\":15}},\"prompt\":\"测试\",\"generateCount\":2}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 15:51:25', 66646);
INSERT INTO `sys_oper_log` VALUES (130, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"测试1\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字\",\"generateCount\":2},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"测试2\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"杨本身是一种树木发生的力量的撒肯定撒里卡多雷克萨打开了立刻大师傅零点三零\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字\",\"generateCount\":2}]},\"params\":{},\"taskName\":\"dw\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 15:58:14', 114773);
INSERT INTO `sys_oper_log` VALUES (131, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"测试1\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字\",\"generateCount\":1},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"测试2\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"杨本身是一种树木发生的力量的撒肯定撒里卡多雷克萨打开了立刻大师傅零点三零\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"dw\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 16:02:38', 65687);
INSERT INTO `sys_oper_log` VALUES (132, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"测试1\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字\",\"generateCount\":2},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"测试2\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"杨本身是一种树木发生的力量的撒肯定撒里卡多雷克萨打开了立刻大师傅零点三零\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字\",\"generateCount\":2}]},\"params\":{},\"taskName\":\"dw\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 16:05:12', 71425);
INSERT INTO `sys_oper_log` VALUES (133, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"测试1\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字\",\"generateCount\":2},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"测试2\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"杨本身是一种树木发生的力量的撒肯定撒里卡多雷克萨打开了立刻大师傅零点三零\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字\",\"generateCount\":2}]},\"params\":{},\"taskName\":\"dwa\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 16:08:14', 52262);
INSERT INTO `sys_oper_log` VALUES (134, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"测试1\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字\",\"generateCount\":2},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"测试2\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"杨本身是一种树木发生的力量的撒肯定撒里卡多雷克萨打开了立刻大师傅零点三零\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字\",\"generateCount\":2}]},\"params\":{},\"taskName\":\"dwwa\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 16:12:19', 78222);
INSERT INTO `sys_oper_log` VALUES (135, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字\",\"generateCount\":2},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"林星然\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字\",\"generateCount\":2}]},\"params\":{},\"taskName\":\"dwadw\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 16:17:05', 73853);
INSERT INTO `sys_oper_log` VALUES (136, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"测试1\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"测试哇达瓦达瓦梵蒂冈梵蒂冈梵蒂冈反对广泛的\",\"maxCharsPerLine\":15}},\"prompt\":\"测试\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"无敌\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 16:21:37', 77895);
INSERT INTO `sys_oper_log` VALUES (137, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"测试1\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"测试哇达瓦达瓦梵蒂冈梵蒂冈梵蒂冈反对广泛的\",\"maxCharsPerLine\":15}},\"prompt\":\"测试\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"dwdw\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 16:25:10', 49694);
INSERT INTO `sys_oper_log` VALUES (138, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":2},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"林星然\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":2}]},\"params\":{},\"taskName\":\"dw\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 16:28:43', 72765);
INSERT INTO `sys_oper_log` VALUES (139, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":3},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"林星然\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"wdw\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', NULL, 1, 'Invoke of: DoJavaScript\nSource: Adobe Photoshop\nDescription: 发生了常规 Photoshop 错误。该功能可能无法在这个版本的 Photoshop 中使用。\n- 错误 2: UserInteractionLevel 未定义.\r直线: 6\r->  app.userInteractionLevel = UserInteractionLevel.NEVER;\n', '2025-03-13 16:33:47', 46919);
INSERT INTO `sys_oper_log` VALUES (140, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '深圳科晶智达科技有限公司', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":3},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"林星然\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"wdw\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 16:35:13', 45656);
INSERT INTO `sys_oper_log` VALUES (141, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '192.168.0.108', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\liuxianying\\\\Desktop\\\\qm\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":3},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"林星然\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 20:13:38', 55930);
INSERT INTO `sys_oper_log` VALUES (142, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '192.168.0.108', '内网IP', '{\"accountName\":\"liuxianying\",\"config\":{\"baseConfig\":{\"accountName\":\"liuxianying\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"正文靠左 三个字 无姓氏\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成女孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":3},{\"folderName\":\"正文靠左 三个字 小名\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"林星然\",\"sampleText\":\"林星然\",\"maxCharsPerLine\":5},\"textLayer2\":{\"name\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"sampleText\":\"出自宋代诗人孔武仲的《奉祠城西夜坐苑中即事》“乔林翳明星，林缺星粲然” [小名]：然然、小星星\",\"maxCharsPerLine\":15}},\"prompt\":\"给我生成男孩名字，textLayer1 是名字， textLayer2是名字描述\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"决定\",\"templateName\":\"正文靠左 三个字 无姓氏\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 20:17:53', 78862);
INSERT INTO `sys_oper_log` VALUES (143, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '192.168.0.108', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"测试1\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"测试哇达瓦达瓦梵蒂冈梵蒂冈梵蒂冈反对广泛的\",\"maxCharsPerLine\":15}},\"prompt\":\"测试\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 20:20:40', 57503);
INSERT INTO `sys_oper_log` VALUES (144, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '192.168.0.108', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 20:23:27', 62445);
INSERT INTO `sys_oper_log` VALUES (145, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '192.168.0.108', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":2}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 20:29:33', 66059);
INSERT INTO `sys_oper_log` VALUES (146, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '192.168.0.108', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 20:42:05', 68464);
INSERT INTO `sys_oper_log` VALUES (147, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '192.168.0.108', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":20}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 20:44:11', 38292);
INSERT INTO `sys_oper_log` VALUES (148, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '192.168.0.108', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":3}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-13 21:10:56', 63019);
INSERT INTO `sys_oper_log` VALUES (149, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":20}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', NULL, 1, 'Invoke of: DoJavaScript\nSource: Adobe Photoshop\nDescription: 发生了常规 Photoshop 错误。该功能可能无法在这个版本的 Photoshop 中使用。\n- 错误 17: 应为变量名.\r直线: 8\r->  var CONFIG = {\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"逸尘\",\"maxCharsPerLine\":\"3\",\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"逸尘象征着超脱尘世的洒脱，寓意孩子能拥有自由不羁的灵魂，不受世俗束缚。\",\"maxCharsPerLine\":\"15\"}},{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"凌川\",\"maxCharsPerLine\":\"3\",\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"凌川展现出勇往直前、不畏艰险的气势，寓意孩子能在人生道路上奋勇向前，跨越重重困难。\",\"maxCharsPerLine\":\"15\"}}]} ;\n', '2025-03-15 01:03:20', 51234);
INSERT INTO `sys_oper_log` VALUES (150, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":10}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', NULL, 1, 'Invoke of: DoJavaScript\nSource: Adobe Photoshop\nDescription: 发生了常规 Photoshop 错误。该功能可能无法在这个版本的 Photoshop 中使用。\n- 错误 17: 应为变量名.\r直线: 8\r->  var CONFIG = {\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"逸风\",\"maxCharsPerLine\":\"3\",\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"逸风寓意自由洒脱，风度翩翩。\",\"maxCharsPerLine\":\"15\"}},{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"凌云\",\"maxCharsPerLine\":\"3\",\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"凌云象征志向高远，勇往直前。\",\"maxCharsPerLine\":\"15\"}},{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"沐阳\",\"maxCharsPerLine\":\"3\",\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"沐阳寓意充满活力，阳光开朗。\",\"maxCharsPerLine\":\"15\"}},{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"锦轩\",\"maxCharsPerLine\":\"3\",\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"锦轩表示前程似锦，气宇轩昂。\",\"maxCharsPerLine\":\"15\"}},{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"睿渊\",\"maxCharsPerLine\":\"3\",\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"睿渊代表聪明睿智，学识渊博。\",\"maxCharsPerLine\":\"15\"}},{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"皓宇\",\"maxCharsPerLine\":\"3\",\"prompt\":\"\"},\"textLayer2\":{\"nam', '2025-03-15 01:05:23', 39366);
INSERT INTO `sys_oper_log` VALUES (151, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":10}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-15 01:14:27', 139584);
INSERT INTO `sys_oper_log` VALUES (152, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', NULL, 1, 'JSX 读取失败：E:\\JavaProject\\RuoYi-Vue\\ruoyi-admin\\target\\jsx\\generate.jsx', '2025-03-15 14:13:09', 11);
INSERT INTO `sys_oper_log` VALUES (153, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', NULL, 1, 'Can\'t co-create object', '2025-03-15 14:16:09', 133787);
INSERT INTO `sys_oper_log` VALUES (154, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', NULL, 1, 'Can\'t co-create object', '2025-03-15 14:16:39', 47147);
INSERT INTO `sys_oper_log` VALUES (155, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-15 14:18:29', 34155);
INSERT INTO `sys_oper_log` VALUES (156, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-15 14:20:19', 46583);
INSERT INTO `sys_oper_log` VALUES (157, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', NULL, 1, 'Can\'t co-create object', '2025-03-15 14:29:52', 134804);
INSERT INTO `sys_oper_log` VALUES (158, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', NULL, 1, 'java.lang.NullPointerException\r\n	at com.ruoyi.web.controller.custom.PsdTaskController.add(PsdTaskController.java:115)\r\n	at com.ruoyi.web.controller.custom.PsdTaskController$$FastClassBySpringCGLIB$$c5ab983e.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\r\n	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:64)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\r\n	at org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor.invoke(AfterReturningAdviceInterceptor.java:57)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\r\n	at org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:58)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\r\n	at org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor.invoke(AuthorizationManagerBeforeMethodInterceptor.java:162)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proce', '2025-03-15 14:59:01', 17);
INSERT INTO `sys_oper_log` VALUES (159, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"error\\\": \\\"您提供的输入信息不完整，无法为您生成所需的姓名配置。\\\"} \",\"code\":200}', 0, NULL, '2025-03-15 15:00:02', 4073);
INSERT INTO `sys_oper_log` VALUES (160, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘寓意超脱世俗，追求自由与宁静，展现出高雅的气质和不凡的追求。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:01:46', 17656);
INSERT INTO `sys_oper_log` VALUES (161, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘象征着超脱世俗，追求自由，寓意孩子能拥有洒脱的心境，不为尘世所累。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到 json 中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:03:08', 13067);
INSERT INTO `sys_oper_log` VALUES (162, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸风\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸风象征着自由洒脱、风度翩翩，寓意孩子拥有豁达的心境和出众的气质。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到 json 中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:06:02', 13333);
INSERT INTO `sys_oper_log` VALUES (163, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"createDate\":\"2025-03-15 15:09:27.939\",\"jsonInfo\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸风\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸风象征着自由洒脱、风度翩翩，寓意孩子拥有豁达的心境和出众的气质。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到 json 中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-15 15:09:27', 33908);
INSERT INTO `sys_oper_log` VALUES (164, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘象征着超脱尘世的洒脱与自在，寓意孩子能够摆脱世俗的束缚，追求内心的宁静与自由。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:15:56', 13663);
INSERT INTO `sys_oper_log` VALUES (165, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘寓意着超脱世俗，气质非凡，象征孩子能够拥有自由洒脱的人生。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到 json 中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:22:04', 14937);
INSERT INTO `sys_oper_log` VALUES (166, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘寓意超凡脱俗，不受尘世纷扰，追求内心的宁静与自由。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:28:17', 12987);
INSERT INTO `sys_oper_log` VALUES (167, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸风\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸风寓意自由洒脱，风度翩翩，展现出男子的独特魅力。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到 json 中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:29:47', 11833);
INSERT INTO `sys_oper_log` VALUES (168, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘象征着超脱尘世的洒脱，寓意孩子能拥有自由不羁的灵魂，不为世俗所累。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:31:37', 13677);
INSERT INTO `sys_oper_log` VALUES (169, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸风\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸风象征着自由洒脱，风度翩翩，寓意孩子能拥有豁达的胸怀和出众的气质。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:33:13', 12279);
INSERT INTO `sys_oper_log` VALUES (170, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘象征着超脱尘世的洒脱，寓意孩子能够拥有自由不羁的灵魂，不受世俗的束缚。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:34:07', 13305);
INSERT INTO `sys_oper_log` VALUES (171, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘寓意超凡脱俗，不受尘世纷扰，追求自由与宁静。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到 json 中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:35:01', 13343);
INSERT INTO `sys_oper_log` VALUES (172, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"createDate\":\"2025-03-15 15:36:06.763\",\"jsonInfo\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘寓意超凡脱俗，不受尘世纷扰，追求自由与宁静。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到 json 中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-15 15:36:06', 32659);
INSERT INTO `sys_oper_log` VALUES (173, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸风\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸风寓意潇洒自在，风度翩翩。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:36:59', 12903);
INSERT INTO `sys_oper_log` VALUES (174, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸风\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸风寓意自由洒脱，风度翩翩，展现出男子的潇洒气质。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:38:19', 13694);
INSERT INTO `sys_oper_log` VALUES (175, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.getCoze()', 'POST', 1, 'admin', '研发部门', '/psd/task/getCoze', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘象征着超脱世俗，自由不羁，寓意孩子能够拥有洒脱的心境，不为尘世所扰。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到 json 中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"code\":200}', 0, NULL, '2025-03-15 15:40:01', 13147);
INSERT INTO `sys_oper_log` VALUES (176, 'psd任务', 1, 'com.ruoyi.web.controller.custom.PsdTaskController.add()', 'POST', 1, 'admin', '研发部门', '/psd/task', '127.0.0.1', '内网IP', '{\"accountName\":\"测试\",\"config\":{\"baseConfig\":{\"accountName\":\"测试\",\"psdLocalPath\":\"C:\\\\Users\\\\11254\\\\Downloads\\\\A4xin.psd\",\"imageSavePath\":\"C:\\\\output\",\"templateName\":\"测试\"},\"imageConfigs\":[{\"folderName\":\"正文靠左 三个字 无姓氏\",\"hasSubfolder\":true,\"subfolderName\":\"4\",\"textLayerConfigs\":{\"textLayer1\":{\"name\":\"远之\",\"sampleText\":\"远之\",\"maxCharsPerLine\":3,\"prompt\":\"\"},\"textLayer2\":{\"name\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"sampleText\":\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \",\"maxCharsPerLine\":15}},\"prompt\":\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到json中返回给我。\",\"generateCount\":1}]},\"createDate\":\"2025-03-15 15:41:39.795\",\"jsonInfo\":\"{\\\"baseConfig\\\":{\\\"accountName\\\":\\\"测试\\\",\\\"psdLocalPath\\\":\\\"C:\\\\\\\\Users\\\\\\\\11254\\\\\\\\Downloads\\\\\\\\A4xin.psd\\\",\\\"imageSavePath\\\":\\\"C:\\\\\\\\output\\\",\\\"templateName\\\":\\\"测试\\\"},\\\"imageConfigs\\\":[{\\\"folderName\\\":\\\"正文靠左 三个字 无姓氏\\\",\\\"hasSubfolder\\\":true,\\\"subfolderName\\\":\\\"4\\\",\\\"textLayerConfigs\\\":{\\\"textLayer1\\\":{\\\"name\\\":\\\"远之\\\",\\\"sampleText\\\":\\\"逸尘\\\",\\\"maxCharsPerLine\\\":\\\"3\\\",\\\"prompt\\\":\\\"\\\"},\\\"textLayer2\\\":{\\\"name\\\":\\\"杨本身是一种树木，拆解为木易二字，结合姓氏字形，音律都为上佳，也有祝福孩子茁壮成长为参天大树，顺风顺水之意。 \\\",\\\"sampleText\\\":\\\"逸尘象征着超脱世俗，自由不羁，寓意孩子能够拥有洒脱的心境，不为尘世所扰。\\\",\\\"maxCharsPerLine\\\":\\\"15\\\"}},\\\"prompt\\\":\\\"textLayer1是名字，textLayer2是名字描述，给我生成一个男生国风的名字，生成的数据帮我替换到 json 中返回给我。\\\",\\\"generateCount\\\":\\\"1\\\"}]} \",\"params\":{},\"taskName\":\"测试\",\"templateName\":\"测试\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-15 15:41:54', 45132);
INSERT INTO `sys_oper_log` VALUES (177, 'psd任务', 3, 'com.ruoyi.web.controller.custom.PsdTaskController.remove()', 'DELETE', 1, 'admin', '研发部门', '/psd/task/37,36,35', '127.0.0.1', '内网IP', '[37,36,35]', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2025-03-15 15:42:19', 29);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(0) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2025-03-08 23:02:42', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2025-03-08 23:02:42', '', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(0) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2025-03-08 23:02:42', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2025-03-08 23:02:42', '', NULL, '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(0) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 117);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
INSERT INTO `sys_role_menu` VALUES (2, 1058);
INSERT INTO `sys_role_menu` VALUES (2, 1059);
INSERT INTO `sys_role_menu` VALUES (2, 1060);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2025-03-15 16:36:39', 'admin', '2025-03-08 23:02:42', '', '2025-03-15 16:36:39', '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2025-03-08 23:02:42', 'admin', '2025-03-08 23:02:42', '', NULL, '测试员');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `post_id` bigint(0) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
