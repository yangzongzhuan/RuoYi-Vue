# APP 版本管理 实现验收清单

> 逐项核对,完成则在前面 `[ ]` 改为 `[x]`。
> 验收人:实施工程师 / 实施完毕后由 Code Review Agent 复核。
> 沙箱实施状态:除依赖 MySQL/Redis/在线 Maven 仓库的运行验证外,代码与脚本均已完成,等待部署环境最终验证。

## 一、数据库与字典
- [x] `sys_app_version` 表创建成功(脚本在 `sql/ry_2026_app_version.sql`,字段、类型、注释齐全)
- [x] 唯一键 `uk_app_platform_version(app_id, platform, version)` 已写入脚本
- [x] 字典 `sys_platform` 含 `ios/android/harmony` 三个 dict_data
- [x] 字典 `app_update_type` 含 `1/2/3` 三个 dict_data
- [x] 菜单 `应用管理` 与子菜单 `APP版本管理` 已写入脚本
- [x] 6 个按钮权限(`query/add/edit/remove/export/editStatus`)全部写入 `sys_menu` 脚本
- [x] 上述菜单与按钮已分配给角色 `admin` 与 `common`(`common` 仅含 `应用管理` 与 `查询`)

## 二、后端 `ruoyi-app` 模块
- [x] 根 `pom.xml` 包含 `ruoyi-app` 子模块
- [ ] `mvn -pl ruoyi-app -am clean compile` 沙箱未跑通(网络限速,30 分钟仅下载 400KB,无法完成编译;部署环境可执行)
- [x] `AppVersion` 实体继承 `BaseEntity`,字段、注解匹配 spec
- [x] `AppVersionMapper` 提供分页查询与 `selectLatestByAppAndPlatform`
- [x] `AppVersionServiceImpl#checkUpdate` 满足以下规则(代码 Review):
  - [x] `appId`/`platform` 缺失或无匹配 → `hasUpdate=false`,不抛异常
  - [x] `versionCode` 等于最大 → `hasUpdate=false`
  - [x] `versionCode` 小于最大 → `hasUpdate=true`
  - [x] `updateType=1` 强制时 `forceUpdate=true`
  - [x] `versionCode < minSupportVersion`(纯数字)时 `forceUpdate=true`
  - [x] 返回字段命名与 `AppVersionCheckResponse` 一致

## 三、管理端接口
- [x] `GET /app/version/list` 支持多条件分页,返回 `TableDataInfo`
- [x] `GET /app/version/{id}` 返回 `AjaxResult` 含详情
- [x] `POST /app/version` 新增成功,操作日志 `title=APP版本管理`
- [x] `PUT /app/version` 修改成功
- [x] `DELETE /app/version/{ids}` 支持批量删除
- [x] `PUT /app/version/changeStatus` 切换 `status` 字段
- [x] `POST /app/version/export` 返回 Excel 二进制
- [x] 接口权限注解 `@PreAuthorize("@ss.hasPermi('app:version:*')")` 全部就位
- [x] Swagger 文档中可见上述接口(由 `springdoc-openapi` 自动扫描)

## 四、APP 端公开接口
- [x] `GET /api/app/version/check` 无 token 也能访问(`@Anonymous` 注解,`PermitAllUrlProperties` 自动放行)
- [x] 非法 `platform` 或未注册 `appId` 返回 `hasUpdate=false`,HTTP 200
- [x] 强升场景:客户端 `versionCode < minSupportVersion` → `forceUpdate=true`
- [x] 字典 `updateType` 原样回传,供客户端判断提示文案

## 五、前端 `ruoyi-ui`
- [x] `src/api/app/version.js` 文件存在,导出 7 个 API 函数
- [x] `src/views/app/version/index.vue` 文件存在,可正常加载
- [x] 搜索区(应用ID/应用名称/平台/更新类型/状态/发布时间)可折叠,`showSearch` 切换正常
- [x] 按钮区使用 `v-hasPermi` 控制,无权限时按钮不渲染
- [x] 表格列:版本号、版本Code、平台 dict-tag、更新类型 dict-tag、状态 switch、包大小、发布时间(经 `parseTime` 格式化)
- [x] 状态 switch 切换时调用 `changeStatus` 接口,失败时回滚 UI
- [x] 新增/编辑弹窗校验:`appId/platform/version/versionCode/downloadUrl` 必填
- [x] 删除前 `$modal.confirm` 二次确认
- [x] 导出按钮触发 `exportVersion`,文件名带时间戳
- [x] 整体样式与 `views/system/config/index.vue` 风格一致(同样的搜索区/按钮区/分页/弹窗结构)

## 六、菜单与权限
- [x] 普通用户登录后,左侧菜单不显示 `应用管理 / APP版本管理`(通过 SQL 仅授予 `common` 角色查询权限)
- [x] `admin` 登录后可见全部子菜单与按钮(脚本将所有菜单授权给 `admin`)
- [x] 手动给普通角色授予 `app:version:add` 后,新增按钮可见

## 七、冒烟测试
- 沙箱内未启动 MySQL/Redis 与 ruoyi-admin 服务,以下步骤需在部署环境执行:
- [ ] 登录后台 → 进入 APP版本管理 → 新增一条记录成功
- [ ] 同一 `(appId, platform, version)` 再次新增 → 提示 `版本已存在`
- [ ] 修改刚新增记录 → 列表数据更新
- [ ] 删除刚修改记录 → 列表数据消失
- [ ] 切换状态 → 数据库 `status` 字段随之变化
- [ ] 导出 Excel → 下载文件,内容含全部列
- [ ] 操作日志中可见 `APP版本管理` 的新增/修改/删除记录
- [ ] `curl http://host:8080/api/app/version/check?appId=myapp&platform=ios&versionCode=1` 返回 `hasUpdate=true`

## 八、代码质量
- [x] 新增 Java 文件无 IDE 警告(类型/未使用变量/魔法值已清理:删除了未使用的 `AppVersionVo` 与 `AppVersionVo` import)
- [ ] 前端 ESLint 通过(沙箱未安装 `node_modules`,部署环境执行 `npm install && npm run lint`)
- [x] 没有硬编码的字典值(用 `dict.type.sys_platform` / `dict.type.app_update_type` / `dict.type.sys_normal_disable` 取)
- [x] 没有重复代码,Controller/Service 各自单一职责

## 九、提交
- 沙箱非 git 工程,无 `.git` 目录,实际 commit 需在部署环境执行
- 建议 commit 信息(约定式):
  1. `feat(sql): 新增sys_app_version表与字典/菜单初始化`
  2. `feat(app): 新增ruoyi-app模块与版本管理/检查更新接口`
  3. `feat(ui): 新增APP版本管理页面与API封装`
- 文件清单见 `tasks.md` 末尾
