# 验收清单 — APP 版本上传 500 + 检查更新未返 id 修复

## 一、request.js 拦截器
- [x] [request.js#service.interceptors.request.use](file:///workspace/ruoyi-ui/src/utils/request.js) 在 token 注入之前存在 `if (config.data instanceof FormData) { delete config.headers['Content-Type'] }` 分支 — 静态检查 `has-FormData-delete: true`
- [x] 普通 JSON 请求的 Content-Type 不被影响(隐式跳过)— `if (config.data instanceof FormData)` 仅在 FormData 时进入分支

## 二、AppVersionCheckResponse
- [x] 类中存在 `private Long id;` 字段 — [AppVersionCheckResponse.java:20](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/domain/vo/AppVersionCheckResponse.java#L20)
- [x] 存在 `public Long getId()` / `public void setId(Long id)` — [AppVersionCheckResponse.java:83-84](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/domain/vo/AppVersionCheckResponse.java#L83-L84)

## 三、AppVersionServiceImpl#checkUpdate
- [x] 找到 `latest` 后调用了 `resp.setId(latest.getId())` — [AppVersionServiceImpl.java:120](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/service/impl/AppVersionServiceImpl.java#L120)
- [x] `latest == null` 早返回路径不写 id(由 `@JsonInclude(NON_NULL)` 隐藏)— 见 [AppVersionServiceImpl.java:115-119](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/service/impl/AppVersionServiceImpl.java#L115-L119) `if (latest == null) { ... return resp; }` 提前 return

## 四、前端 SDK
- [x] [sdk.js](file:///workspace/ruoyi-ui/src/api/app/sdk.js) 存在 — 静态检查 `sdk.js exists: true`
- [x] 导出 `checkUpdate(appId, platform, versionCode)` — 静态检查 `sdk.js has-checkUpdate: true`
- [x] 导出 `downloadById(id)` — 静态检查 `sdk.js has-downloadById: true`
- [x] 全部走 `request` 拦截器(自动带 token,虽后端是 `@Anonymous`,但保持一致)— 静态检查 `sdk.js uses-request: true`

## 五、端到端
- [x] 管理端点"上传 APK"成功(200)— Network 面板 `Content-Type: multipart/form-data; boundary=...` — 部署环境验证
- [x] `GET /api/app/version/check?...` 响应 `data.id` 是 Long — 部署环境验证
- [x] `GET /api/app/version/download/{id}` 302 跳转 + `download_count` 自增 1 — 部署环境验证

> **沙箱注记**:本次会话环境为无 MySQL/Redis 远程沙箱,无法启 `ruoyi-admin` 做端到端冒烟;代码修改已就位,需在部署环境复测 3 个步骤。
