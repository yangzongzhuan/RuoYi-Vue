-- ===========================================================
-- APP 版本管理
-- ===========================================================
-- 本脚本可在已存在 ry_20260417.sql 的数据库上增量执行,执行前请确保 sys_dict_type、sys_menu、sys_role_menu 表已存在。



-- ----------------------------
-- 1、APP 版本表
-- ----------------------------
DROP TABLE IF EXISTS sys_app_version;
CREATE TABLE sys_app_version (
  id                   BIGINT(20)      NOT NULL AUTO_INCREMENT       COMMENT '主键ID',
  app_id               VARCHAR(64)     NOT NULL                      COMMENT '应用标识',
  app_name             VARCHAR(100)    DEFAULT ''                    COMMENT '应用名称',
  platform             VARCHAR(20)     NOT NULL                      COMMENT '平台(ios/android/harmony)',
  version              VARCHAR(32)     NOT NULL                      COMMENT '版本号(语义化)',
  version_code         INT(11)         NOT NULL                      COMMENT '版本Code(整数比较)',
  update_type          CHAR(1)         DEFAULT '2'                   COMMENT '更新类型(1强制 2可选 3静默)',
  download_url         VARCHAR(500)    NOT NULL                      COMMENT '下载/安装包地址',
  update_log           TEXT                                          COMMENT '更新日志',
  package_size         DECIMAL(10,2)   DEFAULT 0                     COMMENT '包大小(MB)',
  md5                  VARCHAR(64)     DEFAULT ''                    COMMENT '安装包MD5',
  min_support_version  VARCHAR(32)     DEFAULT ''                    COMMENT '最低支持版本(低于此版本强制升级)',
  status               CHAR(1)         DEFAULT '0'                   COMMENT '状态(0正常 1停用)',
  publish_time         DATETIME                                     COMMENT '发布时间',
  create_by            VARCHAR(64)     DEFAULT ''                    COMMENT '创建者',
  create_time          DATETIME                                     COMMENT '创建时间',
  update_by            VARCHAR(64)     DEFAULT ''                    COMMENT '更新者',
  update_time          DATETIME                                     COMMENT '更新时间',
  remark               VARCHAR(500)    DEFAULT NULL                  COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_app_platform_version (app_id, platform, version)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT = 'APP版本管理';

-- ----------------------------
-- 2、字典类型:sys_platform、app_update_type
-- ----------------------------
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('APP平台', 'sys_platform', '0', 'admin', sysdate(), 'APP 平台字典'),
       ('APP更新类型', 'app_update_type', '0', 'admin', sysdate(), 'APP 升级类型字典');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
VALUES (1, 'iOS',     'ios',     'sys_platform', '', 'primary', 'N', '0', 'admin', sysdate(), 'iOS 平台'),
       (2, 'Android', 'android', 'sys_platform', '', 'success', 'N', '0', 'admin', sysdate(), 'Android 平台'),
       (3, '鸿蒙',    'harmony', 'sys_platform', '', 'warning', 'N', '0', 'admin', sysdate(), 'HarmonyOS 平台'),
       (1, '强制升级', '1', 'app_update_type', '', 'danger',  'N', '0', 'admin', sysdate(), '必须升级'),
       (2, '可选升级', '2', 'app_update_type', '', 'primary', 'Y', '0', 'admin', sysdate(), '用户选择'),
       (3, '静默升级', '3', 'app_update_type', '', 'info',    'N', '0', 'admin', sysdate(), '静默更新');

-- ----------------------------
-- 3、菜单与按钮
-- ----------------------------
-- 父菜单:应用管理
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('应用管理', 0, 5, 'app', null, 1, 0, 'M', '0', '0', '', 'phone', 'admin', sysdate(), '应用管理目录');
SET @parentId = LAST_INSERT_ID();

-- 子菜单:APP版本管理
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('APP版本管理', @parentId, 1, 'version', 'app/version/index', 1, 0, 'C', '0', '0', 'app:version:list', 'guide', 'admin', sysdate(), 'APP版本管理菜单');
SET @subId = LAST_INSERT_ID();

-- 按钮
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark) VALUES
('APP版本查询', @subId, 1, '', '', 1, 0, 'F', '0', '0', 'app:version:query',     '#', 'admin', sysdate(), ''),
('APP版本新增', @subId, 2, '', '', 1, 0, 'F', '0', '0', 'app:version:add',        '#', 'admin', sysdate(), ''),
('APP版本修改', @subId, 3, '', '', 1, 0, 'F', '0', '0', 'app:version:edit',       '#', 'admin', sysdate(), ''),
('APP版本删除', @subId, 4, '', '', 1, 0, 'F', '0', '0', 'app:version:remove',     '#', 'admin', sysdate(), ''),
('APP版本导出', @subId, 5, '', '', 1, 0, 'F', '0', '0', 'app:version:export',     '#', 'admin', sysdate(), ''),
('APP状态修改', @subId, 6, '', '', 1, 0, 'F', '0', '0', 'app:version:editStatus', '#', 'admin', sysdate(), '');

-- ----------------------------
-- 4、将菜单授权给超管角色(角色ID=1)和普通角色(角色ID=2)
-- ----------------------------
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE menu_name IN ('应用管理', 'APP版本管理', 'APP版本查询', 'APP版本新增', 'APP版本修改', 'APP版本删除', 'APP版本导出', 'APP状态修改');

-- 普通角色默认只授予"查询"
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 2, menu_id FROM sys_menu WHERE menu_name IN ('应用管理', 'APP版本管理', 'APP版本查询');
