# APP 版本管理 — 下载代理接口 Spec

## Why
当前 [add-app-version-management](file:///workspace/.trae/specs/add-app-version-management/spec.md) 中 `checkUpdate` 接口把 `downloadUrl` 直接返回给客户端,客户端再用这个 URL 直接下载。简单可行,但缺少下载次数统计、隐藏真实地址、防盗链等企业级能力。本增强提供 **`GET /api/app/version/download/{id}` 代理接口**,让所有下载都经过服务端,可统计、可观测、可扩展鉴权。

## What Changes
- 新增公开接口 `GET /api/app/version/download/{id}`,`@Anonymous`,**不要求登录**。
- 数据库表 `sys_app_version` 新增字段 `download_count INT(11) DEFAULT 0`,每次下载 +1。
- 命中下载后,**返回 302 重定向**到 `downloadUrl` 字段对应的真实地址;客户端无需感知。
- 若是外链(http/https),重定向到外链即可;若是本地存储(`/profile/upload/...`),重定向到 RuoYi 已有 `/common/download/resource?resource=...`。
- 不修改 `checkUpdate` 接口行为,继续返回 `downloadUrl` 字段(客户端可以两种方式下载:① 直接用 URL ② 走代理接口)。
- 新增菜单权限 `app:version:download`(用于管理端"下载次数"查看,本期不强制使用,保留为后续)。

## Impact
- 受影响能力(新增):
  - `app-apk-download`(APP 安装包下载代理)
- 受影响代码/系统:
  - 数据库表 `sys_app_version` 增加一列 `download_count`(可平滑回填,默认 0)
  - `AppVersion` 实体 / `AppVersionMapper` / `IAppVersionService` / `AppVersionServiceImpl` 各加 1 个方法
  - `AppVersionApiController` 新增 `/download/{id}` 接口
  - `sql/ry_2026_app_version.sql` 增量补丁文件(只追加列与索引,不破坏既有数据)

## ADDED Requirements

### Requirement: 数据库表新增下载次数字段
系统 SHALL 在 `sys_app_version` 表中新增:
```sql
ALTER TABLE sys_app_version
  ADD COLUMN download_count INT(11) NOT NULL DEFAULT 0 COMMENT '下载次数';
```
不重建表,只追加列(默认 0,存量数据自动为 0,无需回填)。

#### Scenario: 旧表升级
- **WHEN** 已在生产运行过原 `ry_2026_app_version.sql` 的环境再次执行补丁脚本
- **THEN** 新列追加成功,`SELECT count(*) FROM sys_app_version` 不变,旧记录 `download_count=0`

### Requirement: 公开下载代理接口
系统 SHALL 在 `AppVersionApiController` 中提供:
```
GET /api/app/version/download/{id}
```
标注 `@Anonymous`,`PermitAllUrlProperties` 自动放行。

接口行为:
1. 根据 `{id}` 查 `sys_app_version`,记录不存在 → 404
2. 命中后,异步/同步**递增** `download_count`(用 `UPDATE ... SET download_count = download_count + 1`)
3. 取该记录的 `download_url` 字段
4. 若 `downloadUrl` 以 `http://` 或 `https://` 开头 → 返回 `302 Found`,`Location` 设为该 URL
5. 若 `downloadUrl` 以 `/` 开头(本地资源,如 `/profile/upload/app/xxx.apk`)→ 返回 `302`,`Location` 设为 `/common/download/resource?resource={path}`(前端可访问,无需鉴权)
6. 若 `downloadUrl` 为空或非法 → 返回 500 业务异常 `下载地址未配置`
7. **不返回文件流本身**,仅做重定向(降低服务端带宽压力)

#### Scenario: 外链地址下载
- **WHEN** 客户端调 `GET /api/app/version/download/1`,数据库 `download_url=https://apps.apple.com/...`
- **THEN** 返回 302,`Location: https://apps.apple.com/...`,数据库 `download_count` 从 0 → 1

#### Scenario: 本地存储下载
- **WHEN** 客户端调 `GET /api/app/version/download/2`,数据库 `download_url=/profile/upload/app/myapp_ios_120_xxx.apk`
- **THEN** 返回 302,`Location: /common/download/resource?resource=/profile/upload/app/myapp_ios_120_120_xxx.apk`,`download_count` +1

#### Scenario: ID 不存在
- **WHEN** 客户端调 `GET /api/app/version/download/9999`(无此记录)
- **THEN** 返回 500 + 业务异常 `版本不存在`,`download_count` 不变

#### Scenario: downloadUrl 为空
- **WHEN** 数据库记录存在但 `download_url IS NULL`
- **THEN** 返回 500 + 业务异常 `下载地址未配置`,`download_count` 不递增(避免脏数据)

### Requirement: Mapper 增量更新
`AppVersionMapper` 增加:
```java
int incrementDownloadCount(@Param("id") Long id);
```
对应 XML:
```xml
<update id="incrementDownloadCount" parameterType="Long">
    update sys_app_version
    set download_count = download_count + 1
    where id = #{id}
</update>
```

### Requirement: Service 编排
`IAppVersionService` 增加:
```java
/**
 * 通过 ID 命中下载:递增计数,返回该记录 downloadUrl,若失败抛 ServiceException
 */
String downloadById(Long id);
```
实现要点:
- `appVersionMapper.selectAppVersionById(id)` 为空 → 抛 `ServiceException("版本不存在")`
- `downloadUrl` 为空 → 抛 `ServiceException("下载地址未配置")`
- `appVersionMapper.incrementDownloadCount(id)`(失败不抛,只记日志,避免下载失败)
- 返回 `downloadUrl`(Controller 再判断外链/本地)

### Requirement: 前端可选使用代理接口
**不强制**。`checkUpdate` 仍返回原始 `downloadUrl`,客户端可继续用直链方式。

仅在管理端"下载次数"展示时(本期不实现,仅预留),后台调用代理接口拿数据。

## MODIFIED Requirements
- 原 `add-app-version-management` 中 `checkUpdate` 返回结构不变,新增列 `download_count` 暂不暴露在响应中(避免冗余)。
- 原 `enhance-app-version-with-apk-upload` 中上传响应不变(不返回 download_count)。

## REMOVED Requirements
无。

## 验收点(Checklist 同名)
详见 `checklist.md`。
