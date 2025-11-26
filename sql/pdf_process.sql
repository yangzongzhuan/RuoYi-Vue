-- ----------------------------
-- PDF文件表 sys_pdf_file
-- ----------------------------
create table sys_pdf_file (
  pdf_id            bigint(20)      not null auto_increment    comment 'PDF文件ID',
  pdf_title         varchar(100)     not null                   comment 'PDF标题',
  pdf_description   varchar(500)     default ''                 comment 'PDF描述',
  pdf_path          varchar(255)     not null                   comment 'PDF文件存储路径',
  original_filename varchar(255)     not null                   comment '原始文件名',
  page_count        int(11)          default 0                  comment 'PDF页数',
  status            char(1)          default '0'                comment '处理状态（0待处理 1处理中 2处理完成 3处理失败）',
  create_by         varchar(64)      default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)      default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)     default ''                 comment '备注',
  primary key (pdf_id)
) engine=innodb auto_increment=1000 comment = 'PDF文件表';

-- ----------------------------
-- PDF图片表 sys_pdf_image
-- ----------------------------
create table sys_pdf_image (
  image_id          bigint(20)      not null auto_increment    comment '图片ID',
  pdf_id            bigint(20)      not null                   comment '关联的PDF文件ID',
  page_num          int(11)         not null                   comment '页码',
  image_path        varchar(255)    not null                   comment '图片存储路径',
  image_size        bigint(20)      default 0                  comment '图片大小(字节)',
  create_time       datetime                                   comment '创建时间',
  primary key (image_id),
  index idx_pdf_id (pdf_id)
) engine=innodb auto_increment=1000 comment = 'PDF图片表';

-- ----------------------------
-- 外键约束
-- ----------------------------
alter table sys_pdf_image add constraint fk_pdf_image foreign key (pdf_id) references sys_pdf_file (pdf_id) on delete cascade;

-- ----------------------------
-- 添加PDF菜单
-- ----------------------------
-- 菜单表插入数据 - 先获取系统管理的ID
-- 假设系统管理的ID是100，如果不是，请根据实际情况调整
-- 插入PDF管理主菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('PDF管理', 100, 10, 'pdf', 'system/pdf/index', 1, 0, 'C', '0', '0', 'system:pdf:list', 'el-icon-document', 'admin', sysdate(), 'admin', sysdate(), 'PDF文件管理菜单');

-- 获取刚插入的PDF管理菜单ID
SET @pdf_menu_id = LAST_INSERT_ID();

-- 插入按钮权限，并设置正确的parent_id为PDF管理菜单的ID
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('新增PDF', @pdf_menu_id, 1, '#', '', 0, 0, 'F', '0', '0', 'system:pdf:add', '', 'admin', sysdate(), 'admin', sysdate(), '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('修改PDF', @pdf_menu_id, 2, '#', '', 0, 0, 'F', '0', '0', 'system:pdf:edit', '', 'admin', sysdate(), 'admin', sysdate(), '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('删除PDF', @pdf_menu_id, 3, '#', '', 0, 0, 'F', '0', '0', 'system:pdf:remove', '', 'admin', sysdate(), 'admin', sysdate(), '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('导入PDF', @pdf_menu_id, 4, '#', '', 0, 0, 'F', '0', '0', 'system:pdf:import', '', 'admin', sysdate(), 'admin', sysdate(), '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('导出PDF', @pdf_menu_id, 5, '#', '', 0, 0, 'F', '0', '0', 'system:pdf:export', '', 'admin', sysdate(), 'admin', sysdate(), '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('查看图片', @pdf_menu_id, 6, '#', '', 0, 0, 'F', '0', '0', 'system:pdf:viewImage', '', 'admin', sysdate(), 'admin', sysdate(), '');

-- 插入查询权限（用于查看列表）
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('PDF查询', @pdf_menu_id, 0, '#', '', 0, 0, 'F', '0', '0', 'system:pdf:query', '', 'admin', sysdate(), 'admin', sysdate(), '');

-- ----------------------------
-- 为管理员角色分配PDF菜单权限（使用动态ID）
-- ----------------------------
-- 先删除可能已存在的相同权限配置，避免主键冲突
DELETE FROM sys_role_menu WHERE role_id = '2' AND menu_id IN (
    @pdf_menu_id,
    @pdf_menu_id + 1,
    @pdf_menu_id + 2,
    @pdf_menu_id + 3,
    @pdf_menu_id + 4,
    @pdf_menu_id + 5,
    @pdf_menu_id + 6,
    @pdf_menu_id + 7
);

-- 为PDF管理主菜单分配权限
INSERT INTO sys_role_menu (role_id, menu_id) VALUES ('2', @pdf_menu_id);

-- 查询并为所有PDF相关子菜单分配权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT '2', menu_id FROM sys_menu WHERE parent_id = @pdf_menu_id;
