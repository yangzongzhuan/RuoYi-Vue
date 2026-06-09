-- ===========================================================
-- APP 版本管理 — 下载代理补丁
-- 适用:已在生产环境执行过 ry_2026_app_version.sql 的情况
-- 变更:仅追加 download_count 列(默认 0)与下载权限菜单,无破坏性操作
-- ===========================================================

USE ry;

-- ----------------------------
-- 1、sys_app_version 表追加下载次数字段
-- ----------------------------
ALTER TABLE sys_app_version
    ADD COLUMN download_count INT(11) NOT NULL DEFAULT 0 COMMENT '下载次数';

-- ----------------------------
-- 2、菜单与按钮:APP版本下载(占位,后续管理端可查看下载次数)
-- ----------------------------
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
SELECT 'APP版本下载', menu_id, 7, '', '', 1, 0, 'F', '0', '0', 'app:version:download', '#', 'admin', sysdate(), ''
FROM sys_menu WHERE menu_name = 'APP版本管理' LIMIT 1;

-- ----------------------------
-- 3、将下载权限授权给超管角色(角色ID=1)
-- ----------------------------
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE perms = 'app:version:download';
