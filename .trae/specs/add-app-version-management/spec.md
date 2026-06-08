# APP 版本管理功能 Spec

## Why
当前 RuoYi 后台缺少对移动端 APP 版本发布与升级的统一管理能力,运营人员无法在后台配置新版本、强制升级、灰度策略等,客户端也无法在启动时实时检查更新并提示用户升级。本功能提供完整的版本管理后台与公开的检查更新 API,打通"运营发布 -> 客户端检查 -> 用户升级"的链路。

## What Changes
- 新增 `ruoyi-app` 后端模块,负责 APP 版本的数据维护与公开的版本检查 API
- 新增 `sys_app_version` 数据库表,存储多平台、多应用的版本信息
- 新增管理端接口:列表查询、详情、新增、修改、删除、修改状态、导出
- 新增 APP 端公开接口:`/api/app/version/check`,供客户端调用以检查更新
- 新增前端 `ruoyi-ui` 页面:版本列表(增/改/删/导出)、状态切换、操作日志遵循 RuoYi 既有风格
- 新增菜单及按钮权限:应用管理 -> APP版本管理
- 提供 `dict` 字典:`sys_platform`(ios/android/harmony)与 `app_update_type`(1强制/2可选/3静默)

## Impact
- 受影响能力(新增):
  - `app-version-management`(后台 CRUD)
  - `app-version-check`(公开版本检查)
  - `app-version-dict`(字典项)
- 受影响代码/系统:
  - 新建模块 `ruoyi-app`(controller/service/mapper/domain/vo/sql)
  - 新建前端目录 `ruoyi-ui/src/views/app/version/` 与 `ruoyi-ui/src/api/app/version.js`
  - 父 `pom.xml` 增加 `ruoyi-app` 子模块
  - `ruoyi-admin` 不需要引用 `ruoyi-app`(可由未来业务模块按需引用)
  - 数据库脚本 `sql/ry_2026xxxx_app_version.sql`

## ADDED Requirements

### Requirement: 多应用、多平台版本建模
系统 SHALL 提供 `sys_app_version` 表,字段包含:
`id` 主键、`app_id`(应用标识)、`app_name`(应用名称)、`platform`(字典 `sys_platform`,`ios`/`android`/`harmony`)、`version`(语义化版本号,字符串)、`version_code`(整数,用于客户端比较)、`update_type`(字典 `app_update_type`,1强制/2可选/3静默)、`download_url`(下载或应用市场链接)、`update_log`(更新日志,文本)、`package_size`(包大小,MB,BigDecimal)、`md5`(安装包 MD5,可选)、`min_support_version`(最低支持版本,低于此版本强制升级)、`status`(0正常/1停用)、`publish_time`(发布时间)、`create_by/create_time/update_by/update_time/remark`。

唯一约束:`(app_id, platform, version)` 组合唯一。

#### Scenario: 同一应用同一平台同一版本号重复保存
- **WHEN** 调用新增接口提交与已存在 `(app_id, platform, version)` 完全相同的记录
- **THEN** 返回业务异常 `版本已存在`,提示用户修改

#### Scenario: 缺少必填字段
- **WHEN** `app_id` / `platform` / `version` / `version_code` / `download_url` 中任一为空
- **THEN** 返回参数校验失败,提示具体字段

### Requirement: 管理端版本 CRUD 接口
系统 SHALL 提供以下接口(路径前缀 `/app/version`,需要登录,操作计入日志):

| 方法 | 路径 | 说明 |
| ---- | ---- | ---- |
| GET  | `/app/version/list` | 分页查询,支持 `appId`、`platform`、`updateType`、`status`、`beginTime`/`endTime` |
| GET  | `/app/version/{id}` | 详情 |
| POST | `/app/version` | 新增 |
| PUT  | `/app/version` | 修改 |
| DELETE | `/app/version/{ids}` | 批量删除(逗号分隔) |
| PUT  | `/app/version/changeStatus` | 切换状态(启用/停用) |
| POST | `/app/version/export` | 导出 Excel |

#### Scenario: 运营查询 iOS 最新版本
- **WHEN** 携带参数 `appId=myapp&platform=ios&pageNum=1&pageSize=10` 调用 `/app/version/list`
- **THEN** 返回该应用 iOS 平台已启用版本的最新分页数据,包含全部展示字段

#### Scenario: 删除被引用的版本
- **WHEN** 调用删除接口
- **THEN** 物理删除记录,删除失败时返回业务异常

#### Scenario: 启用停用切换
- **WHEN** 调用 `changeStatus` 传入 `id` 与 `status=1`
- **THEN** 数据库 `status` 字段被更新为 1,列表显示为"停用"标签

### Requirement: APP 端公开检查更新接口
系统 SHALL 提供公开接口 `GET /api/app/version/check`,无需登录(`@Anonymous`)。请求参数:`appId`(必填)、`platform`(必填)、`versionCode`(必填,整数)、`version`(可选,字符串)、`channel`(可选,渠道标识,预留)。

返回结构:
```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "hasUpdate": true,
    "forceUpdate": true,
    "latestVersion": "1.2.0",
    "latestVersionCode": 120,
    "updateType": 1,
    "downloadUrl": "https://...",
    "updateLog": "1. 新增 xxx\n2. 修复 yyy",
    "packageSize": 38.5,
    "md5": "...",
    "publishTime": "2026-06-01 10:00:00"
  }
}
```

匹配规则:
1. 在 `app_id = {appId} AND platform = {platform} AND status = 0` 的记录中,按 `version_code` 倒序取第一条
2. 若取到的 `latestVersionCode > 入参 versionCode`,`hasUpdate = true`,否则 `false`
3. 若 `versionCode < min_support_version` 字段,`forceUpdate = true`(覆盖原 `updateType` 为强制)
4. 无任何匹配记录时,`hasUpdate = false`,`data` 中其他字段为 `null`

#### Scenario: 当前版本低于最低支持版本
- **WHEN** 客户端 `versionCode=100`,服务端 `minSupportVersion=120`
- **THEN** 返回 `hasUpdate=true`、`forceUpdate=true`

#### Scenario: 已是最新版本
- **WHEN** 客户端 `versionCode=120`,服务端最大 `versionCode=120`
- **THEN** 返回 `hasUpdate=false`,无错误

#### Scenario: 平台或 appId 不存在
- **WHEN** 传入平台为 `win` 或未注册的 `appId`
- **THEN** 返回 `hasUpdate=false`,不抛异常,避免泄露后台数据

### Requirement: 前端 RuoYi 风格管理页面
系统 SHALL 在 `ruoyi-ui` 中新增 `views/app/version/index.vue`,使用 Element UI Table、Search 表单、弹窗表单,UI 风格与 `views/system/notice/index.vue` 对齐(参考其查询区、操作按钮、表格列、分页)。

页面要素:
- 左侧面包屑:应用管理 / APP版本管理
- 搜索区:`appId`(输入)、`platform`(下拉,字典)、`updateType`(下拉,字典)、`状态`(下拉)、`时间范围`(日期范围)
- 按钮区:新增 / 修改 / 删除 / 导出 / 刷新,使用 `v-hasPermi` 控制显隐
- 表格列:版本号、版本Code、平台标签(`el-tag`)、应用ID、更新类型(`dict-tag`)、状态(`dict-tag`,状态列 switch 切换)、包大小、发布时间、备注、操作
- 弹窗表单:沿用 RuoYi 的 `el-dialog` 校验规则,字段同上

API 封装在 `ruoyi-ui/src/api/app/version.js`,导出 `listVersion`、`getVersion`、`addVersion`、`updateVersion`、`delVersion`、`changeStatus`、`exportVersion`。

#### Scenario: 新增成功
- **WHEN** 在弹窗中填写完整字段并提交
- **THEN** 后端返回 200,前端提示 `新增成功` 并刷新列表,弹窗关闭

#### Scenario: 版本号重复
- **WHEN** 提交已存在的 `(appId, platform, version)`
- **THEN** 后端返回 500 + `版本已存在`,前端 `msgAlert` 提示原文

### Requirement: 菜单与按钮权限
系统 SHALL 在 `sys_menu` 中初始化(以 SQL 脚本形式):
- 父菜单:应用管理(`/app`)
- 子菜单:APP版本管理(`/app/version`,组件 `app/version/index`)
- 按钮:`app:version:list` / `app:version:query` / `app:version:add` / `app:version:edit` / `app:version:remove` / `app:version:export` / `app:version:editStatus`
- 分配给超管角色 `admin`,普通角色按需分配

`/api/app/version/check` 接口需在 `PermitAllUrlProperties` 中加入白名单 `/api/app/version/check`,且 Controller 方法标注 `@Anonymous`。

#### Scenario: 普通用户无权限
- **WHEN** 登录未授权用户访问 `/app/version/list` 页面
- **THEN** 菜单不可见,直接访问路由跳转 404

## MODIFIED Requirements
无(全新功能,不修改既有需求)。

## REMOVED Requirements
无。

## 数据字典
新增字典:
- `sys_platform`: `ios=IOS`、`android=Android`、`harmony=HarmonyOS`
- `app_update_type`: `1=强制升级`、`2=可选升级`、`3=静默升级`

字典初始化写入 `sql/ry_2026xxxx_app_version.sql`。

## 验收点(Checklist 同名)
详见 `checklist.md`。
