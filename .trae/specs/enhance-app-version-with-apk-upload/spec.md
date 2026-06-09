# APP 版本管理 — APK 上传功能增强 Spec

## Why
当前 [add-app-version-management](file:///workspace/.trae/specs/add-app-version-management/spec.md) 中 APK 安装包只支持填写 `downloadUrl`,运营人员需要先在本地或第三方平台(蒲公英、对象存储)上传后再把链接粘回后台,流程割裂,也无法自动获取包大小/MD5 等关键校验信息。本增强提供"前端拖拽上传 → 后端落盘 → 自动回填元数据"的能力,与"外链模式"并存。

## What Changes
- 新增专属 `POST /app/version/upload` 接口,接收 APK 文件,落盘到 `RuoYiConfig.getUploadPath()`(默认 `{profile}/upload/app/`),返回 `{ url, fileName, originalName, size(MB), md5 }`。
- 上传时按 `(appId, platform, versionCode)` 命名,避免重名覆盖。
- 上传过程**自动计算 MD5** 与**包大小**,减少人工录入错误。
- 校验文件 MIME/扩展名,仅允许 `.apk`、`.ipa`、`.hap`(鸿蒙);超出 `spring.servlet.multipart.max-file-size`(默认 10MB)按 `@ConfigurationProperties` 上限生效;超过时返回 500 + 业务异常。
- 前端 `index.vue` 弹窗"下载地址"输入框旁增加"上传 APK"按钮(`el-upload` 组件,自动上传);
- 上传成功后自动回填: `downloadUrl`、`packageSize`(MB 保留 2 位小数)、`md5`,并提示"上传成功"。
- 不修改 `sys_app_version` 表结构,使用现有字段即可。

## Impact
- 受影响能力(新增):
  - `app-apk-upload`(APP 安装包上传)
- 受影响代码/系统:
  - 复用 `ruoyi-app` 模块:新增 `AppVersionController#upload`
  - 复用 `ruoyi-common` 已有 `FileUploadUtils` / `FileUtils` / `ServerConfig`
  - 前端 `ruoyi-ui/src/views/app/version/index.vue` 改造上传区
  - `ruoyi-admin` 模块的 `spring.servlet.multipart.max-file-size` 默认 10MB,APK 普遍 30~80MB,需在 `application.yml` 提升到 `200MB`(或按需)

## ADDED Requirements

### Requirement: APK 上传接口
系统 SHALL 在 `AppVersionController` 中提供 `POST /app/version/upload`,权限 `app:version:add` 或 `app:version:edit`(复用)。

请求:`multipart/form-data`,字段 `file`(必填)、`appId`(必填)、`platform`(必填)、`versionCode`(必填,Int)。

后端行为:
1. 校验 `file` 非空,扩展名必须在白名单 `[apk, ipa, hap]`;`getContentType()` 需包含 `application/` 前缀。
2. 校验 `appId/platform/versionCode` 非空。
3. 落盘到 `RuoYiConfig.getUploadPath() + "/app/"` 子目录,文件名规则:
   `{appId}_{platform}_{versionCode}_{yyyyMMddHHmmss}.{ext}`
4. 计算 MD5(使用 `DigestUtils.md5DigestAsHex` 流式,避免大文件 OOM)。
5. 文件大小换算为 MB(BigDecimal,保留 2 位)。
6. 落盘前检查 `ServerConfig.getUrl()` 可达,落盘后拼接访问 URL。

返回结构(200):
```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "url": "http://host:port/profile/upload/app/ruoyi_ios_120_20260608102030.apk",
    "fileName": "upload/app/ruoyi_ios_120_20260608102030.apk",
    "originalName": "ruoyi-1.2.0.apk",
    "size": 38.50,
    "md5": "d41d8cd98f00b204e9800998ecf8427e"
  }
}
```

#### Scenario: 上传正常
- **WHEN** 携带合法 `file=*.apk`、`appId=myapp`、`platform=ios`、`versionCode=120` 调用
- **THEN** 落盘成功,返回 `data.url`、`data.size`、`data.md5` 全部非空

#### Scenario: 上传非 APK
- **WHEN** 携带 `file=xx.zip` 调用
- **THEN** 返回 500 + 业务异常 `文件类型不支持,仅允许 apk/ipa/hap`

#### Scenario: 缺参
- **WHEN** `appId` 或 `versionCode` 缺失
- **THEN** 返回 500 + 业务异常 `appId/platform/versionCode 不能为空`

#### Scenario: 文件超限
- **WHEN** 上传 300MB 文件
- **THEN** 抛 `MaxUploadSizeExceededException`,前端提示 `文件超过 200MB 限制`

### Requirement: 前端上传组件
在 `index.vue` 新增/编辑弹窗的"下载地址"行,改造为:
```
[<el-input v-model="form.downloadUrl">] [<el-button @click="openUpload">上传 APK</el-button>] [<el-link v-if="form.md5" type="info">MD5: {{ form.md5 }}</el-link>]
```
点击"上传 APK" 触发隐藏的 `<el-upload :auto-upload="true" :http-request="customUpload">` 组件。

`customUpload` 行为:
1. 调用 `formData.append('appId', form.appId)` 等参数
2. 调用 `uploadApk(formData)`,把返回的 `data` 写入:
   - `form.downloadUrl = data.url`
   - `form.packageSize = data.size`
   - `form.md5 = data.md5`
3. `msgSuccess('上传成功')`

若 `form.appId / platform / versionCode` 为空,弹窗提示"请先填写应用ID/平台/版本Code"。

#### Scenario: 上传前未填元数据
- **WHEN** 用户没填 `appId` 直接点上传
- **THEN** 提示 `请先填写应用ID/平台/版本Code`,不触发上传

#### Scenario: 上传成功回填
- **WHEN** 后端返回 200
- **THEN** `downloadUrl/packageSize/md5` 三个字段全部被覆盖为后端返回值,提交时一并入库

### Requirement: application.yml 配置提升文件大小
在 `ruoyi-admin/src/main/resources/application.yml` 的 `spring.servlet.multipart` 段增加:
```yaml
max-file-size: 200MB
max-request-size: 250MB
```
同时 `serverConfig.getUrl()` 需正确,以便拼接可访问的下载 URL(本地默认 `http://localhost:8080`)。

#### Scenario: 200MB 文件上传
- **WHEN** 上传 150MB APK
- **THEN** 接口正常返回 200,落盘成功

## MODIFIED Requirements
- 原 `add-app-version-management` 中 "前端新增/编辑弹窗" 字段保持不变,**仅在"下载地址"行旁边增加上传按钮**,不删除 URL 输入框(保证外链模式仍可用)。

## REMOVED Requirements
无。

## 验收点(Checklist 同名)
详见 `checklist.md`。
