# APP 版本管理 — 上传 500 + 检查更新未返 id Spec

## Why
两个独立但同源的缺陷,都让 v0.2 的上传 / 检查更新链路不可用:

1. **上传 500**:在 fix-app-version-upload-missing-auth-token 后,前端 `uploadApk` 改用 `request` 拦截器。但 [request.js:14](file:///workspace/ruoyi-ui/src/utils/request.js#L14) 有 `axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'`,axios 0.30.x **不会**为 `FormData` 自动覆盖已显式存在的 `Content-Type`。结果请求以 `application/json` 发出,后端 `DispatcherServlet` 抛 `HttpMediaTypeNotSupportedException` → `{"msg":"Current request is not a multipart request","code":500}`。
2. **下载接口需要 id 但检查更新没返回 id**:[AppVersionApiController#download](file:///workspace/ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppVersionApiController.java#L57-L75) 签名是 `GET /api/app/version/download/{id}`,而 [AppVersionCheckResponse](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/domain/vo/AppVersionCheckResponse.java) 没有 `id` 字段,客户端拿到了 `downloadUrl` 也不知道怎么用后端代理计数。本地资源场景必须暴露一个稳定标识。

## What Changes
- **request.js 拦截器** 在检测到 `config.data instanceof FormData` 时,**删除** `Content-Type` 头,让 axios 0.30.x 重新生成 `multipart/form-data; boundary=...`。最小改动,不影响其他接口。
- **AppVersionCheckResponse** 新增 `id` 字段(Long),`AppVersionServiceImpl#checkUpdate` 把 `AppVersion.id` 写入。
- **下载接口签名保持** `GET /api/app/version/download/{id}`(无需改 URL,只需补 id 即可)。
- **新增前端 APP 端 API 封装** `src/api/app/sdk.js`,导出 `checkUpdate(appId, platform, versionCode)` 与 `download(id)`(命名遵循公开接口 `/api/app/version/*` 路径),并把响应数据落到前端可读的常量。
- **更新 README/docs** 说明检查更新返回 id 后,客户端用 `id` 调下载代理。

## Why Not Change Download URL Signature
不改 `download/{id}` 的两个理由:
- id 已经在内部库(主键),暴露给 APP 端是行业通用做法(国内 Android 厂商小米/华为/OPPO/vivo 的 OTA 协议都返 buildId/versionId,国外 Firebase Remote Config 也是返 releaseId)。
- 改 `{appId}/{platform}/{versionCode}` 复合路径会让接口对"同业务多版本"难以区分(草稿/历史版本),且会暴露后端 schema。

## Impact
- 受影响能力:
  - `app-apk-upload`(APP 安装包上传)— 修复 500
  - `app-version-public-api`(APP 端公开 API)— 增加 id 字段
- 受影响代码:
  - [request.js](file:///workspace/ruoyi-ui/src/utils/request.js) — FormData 检测 + 删 Content-Type
  - [AppVersionCheckResponse.java](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/domain/vo/AppVersionCheckResponse.java) — 新增 id 字段 + getter/setter
  - [AppVersionServiceImpl.java#checkUpdate](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/service/impl/AppVersionServiceImpl.java#L104-L153) — 写入 `resp.setId(latest.getId())`
  - 新建 [sdk.js](file:///workspace/ruoyi-ui/src/api/app/sdk.js) — 公开 API 封装

## ADDED Requirements

### Requirement: FormData 走 request 拦截器时自动重置 Content-Type
[request.js](file:///workspace/ruoyi-ui/src/utils/request.js) 的 `service.interceptors.request.use` SHALL 在 `config.data instanceof FormData` 时 `delete config.headers['Content-Type']`,让 axios 0.30.x 自动补上 `multipart/form-data; boundary=...`。

```js
service.interceptors.request.use(config => {
  // 显式删除默认的 application/json,让 axios 重新生成 multipart/form-data; boundary=...
  if (config.data instanceof FormData) {
    if (config.headers && 'Content-Type' in config.headers) {
      delete config.headers['Content-Type']
    }
  }
  // ... 其余逻辑(防重复提交、token 注入)保持不变
  return config
})
```

#### Scenario: 上传 FormData
- **WHEN** 客户端调用 `request({ method: 'post', data: formData })`
- **THEN** 实际请求 Content-Type 为 `multipart/form-data; boundary=----WebKitFormBoundary...`,后端 `MultipartResolver` 正常解析,不再 500

#### Scenario: 普通 JSON 请求
- **WHEN** 客户端调用 `request({ method: 'post', data: { foo: 'bar' } })`
- **THEN** Content-Type 保持 `application/json;charset=utf-8`,不受影响(已隐式跳过 `delete`)

### Requirement: 检查更新响应携带 id
[AppVersionCheckResponse](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/domain/vo/AppVersionCheckResponse.java) SHALL 新增 `id: Long` 字段及 getter/setter,`@JsonInclude(NON_NULL)` 不影响序列化(主键非空)。

[AppVersionServiceImpl#checkUpdate](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/service/impl/AppVersionServiceImpl.java#L120) 找到 `latest` 后,应同步写:
```java
resp.setId(latest.getId());
```

#### Scenario: 有最新版本
- **WHEN** `appId/platform/versionCode` 在表中匹配到 `latest`
- **THEN** 响应 JSON `data.id` 是 Long,客户端可作 `GET /api/app/version/download/{id}` 的入参

#### Scenario: 找不到版本
- **WHEN** `latest == null`
- **THEN** 响应 `id == null`,`hasUpdate == false`,客户端不触发下载(与既有行为一致)

### Requirement: 前端 APP 端公开 API 封装
新建 [sdk.js](file:///workspace/ruoyi-ui/src/api/app/sdk.js):

```js
import request from '@/utils/request'

// 公开接口无需鉴权(对应后端 @Anonymous)
export function checkUpdate(appId, platform, versionCode) {
  return request({
    url: '/api/app/version/check',
    method: 'get',
    params: { appId, platform, versionCode }
  })
}

// 走 RuoYi 通用下载接口(/common/download/resource),需要 Login 拦截
export function downloadById(id) {
  return request({
    url: '/api/app/version/download/' + id,
    method: 'get'
  })
}
```

> **注**:这是给管理端演示用,真实 APP 端 SDK 由原生团队维护;此处只确保路径与 RuoYi 后端一致,方便联调与文档对齐。

#### Scenario: 检查更新调通
- **WHEN** `checkUpdate('ruoyi-app', 'android', 100)` 调用
- **THEN** 实际请求 `GET /dev-api/api/app/version/check?appId=ruoyi-app&platform=android&versionCode=100`,响应 `{code:200, data:{id, hasUpdate, downloadUrl, ...}}`

#### Scenario: 用 id 调下载代理
- **WHEN** `downloadById(123)` 调用
- **THEN** 实际请求 `GET /dev-api/api/app/version/download/123`,后端 302 跳到真实地址,前端跟随重定向由浏览器/RuoYi `common/download` 代理完成文件落盘

## MODIFIED Requirements
无。

## REMOVED Requirements
无。

## 验收点
详见 `checklist.md`。
