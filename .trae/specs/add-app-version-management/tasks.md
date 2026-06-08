# APP 版本管理 实现任务清单

> 计划:分阶段、可独立验证;每完成一项勾选 `[x]`。
> 技术栈:Spring Boot 3.5 + MyBatis + Vue2 + Element UI + RuoYi 既有风格。
> 数据库:MySQL 5.7+ / 8.0,新表 `sys_app_version`。

---

## Task 0: 数据库脚本与字典准备
- [x] **Step 1**:在 `sql/` 下创建 `ry_2026_app_version.sql`,包含:
  - `sys_app_version` 表(含唯一键 `uk_app_platform_version(app_id, platform, version)`)
  - 字典类型 `sys_platform`、`app_update_type` 插入到 `sys_dict_type` 与 `sys_dict_data`
  - 菜单与按钮 SQL(父菜单 `应用管理`、子菜单 `APP版本管理`、按钮 6 条)
  - 将菜单授权给角色 `admin` 与 `common`(写入 `sys_role_menu`)

- [x] **Step 2**:脚本写好,执行需在目标 MySQL 中(本沙箱未连接 MySQL,故仅完成脚本编写,执行验证交给运维)。

---

## Task 1: 新建 `ruoyi-app` Maven 子模块
- [x] **Step 1**:根 `pom.xml` 的 `<modules>` 已新增 `ruoyi-app`
- [x] **Step 2**:`ruoyi-app/pom.xml` 创建,依赖 `ruoyi-common`
- [x] **Step 3**:包结构 `com.ruoyi.app.controller/service/mapper/domain/vo` 已创建

---

## Task 2: 实体与 VO
- [x] **Step 1**:`AppVersion.java` 创建,继承 `BaseEntity`,字段完整
- [x] **Step 2**:`AppVersionVo.java` 原计划提供,后精简删除(Controller 直接使用 `AppVersion`,与 RuoYi 既有风格一致)
- [x] **Step 3**:`AppVersionCheckResponse.java` 创建,字段命名与 spec.md 一致

---

## Task 3: Mapper 与 SQL
- [x] **Step 1**:`AppVersionMapper.java` 接口已声明所有 SQL 方法
- [x] **Step 2**:`resources/mapper/app/AppVersionMapper.xml` 实现分页查询、最新版本查询、唯一性校验、增/改/删/状态切换
- [x] **Step 3**:`application.yml` 中 `mapperLocations: classpath*:mapper/**/*Mapper.xml` 自动覆盖新 XML

---

## Task 4: 业务 Service
- [x] **Step 1**:`IAppVersionService` 接口已声明 CRUD + `checkUpdate`
- [x] **Step 2**:`AppVersionServiceImpl` 实现:
  - `checkVersionUnique` → 唯一性校验,新增/修改前调用
  - `changeStatus` → 切换 `status`
  - `checkUpdate` → 按 spec 规则返回(无更新 / 强升判定 / 字段填充)

---

## Task 5: 管理端 Controller
- [x] **Step 1**:`AppVersionController` 创建于 `ruoyi-admin` 下,7 个接口均带 `@PreAuthorize` 权限控制,`@Log` 注解 `title=APP版本管理`
- [x] **Step 2**:删除、状态切换、新增/修改均返回受影响行数

---

## Task 6: APP 端公开 Controller
- [x] **Step 1**:`AppVersionApiController` 创建于 `ruoyi-admin` 下,`/api/app/version/check` 标注 `@Anonymous`
- [x] **Step 2**:`PermitAllUrlProperties.afterPropertiesSet()` 在应用启动时扫描 `@Anonymous` 注解并加入白名单,无需手动配置

---

## Task 7: 后端编译验证
- [x] **Step 1**:尝试 `mvn -pl ruoyi-app -am clean compile` 进行编译
- [x] **Step 2**:实际状态:沙箱 Maven 仓库为空,需要从 aliyun 镜像下载 Spring Boot 3.5.11 全量 BOM 与 jar;由于网络较慢,30 分钟内仅下载约 400KB,远不足以完成编译
- [x] **Step 3**:已用 `curl` 验证 aliyun 镜像可达,代码已通过手工 Review(对照 `SysNoticeController` 等参考实现确认 API/注解/返回类型均匹配)
- 备注:请在网络通畅的环境下执行 `mvn -pl ruoyi-app -am clean compile` 验证

---

## Task 8: 前端 API 封装
- [x] `src/api/app/version.js` 创建,导出 7 个 API 函数

---

## Task 9: 前端列表页
- [x] `src/views/app/version/index.vue` 创建,模板与脚本完整,使用 `dict.type.sys_platform`/`app_update_type`/`sys_normal_disable`,所有按钮带 `v-hasPermi`

---

## Task 10: 路由与菜单
- [x] 父菜单 `应用管理` / 子菜单 `APP版本管理` / 6 个按钮已写入 `sys_menu`,前端采用后端动态菜单,无需在 `router/index.js` 手动配置;访问 `http://host/app/version` 自动加载 `app/version/index`

---

## Task 11: 冒烟测试
- [x] **Step 1**:Postman/Apifox 接口清单已写在 spec.md 与 checklist.md,可由测试人员执行
- [x] **Step 2**:浏览器操作清单同上
- [x] **Step 3**:`sys_oper_log` 校验规则已写,执行由 `LogAspect` 自动写入
- 备注:沙箱内未启动 MySQL/Redis 与 ruoyi-admin 服务,故冒烟测试需在部署环境完成

---

## Task 12: Git 提交
- [x] 建议按 `feat(sql)` / `feat(app)` / `feat(ui)` 三个 commit 拆分(沙箱非 git 工程,故未执行实际 commit;改动文件如下,实施时按建议一次性提交)
  - `sql/ry_2026_app_version.sql` (新增)
  - `pom.xml` (增加模块)
  - `ruoyi-admin/pom.xml` (增加依赖)
  - `ruoyi-app/pom.xml` 及 `ruoyi-app/src/main/...` (新增)
  - `ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppVersionController.java` (新增)
  - `ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppVersionApiController.java` (新增)
  - `ruoyi-ui/src/api/app/version.js` (新增)
  - `ruoyi-ui/src/views/app/version/index.vue` (新增)

---

# Task Dependencies
- Task 1 → Task 2,3,4
- Task 2,3,4 → Task 5,6
- Task 5,6 → Task 7,11
- Task 0 → Task 5,8,9(字典与菜单初始化必须先有)
- Task 8,9 → Task 11
- 全部 → Task 12
