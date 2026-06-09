# APP 版本管理 — 上传 500 + 检查更新未返 id 任务清单

> 范围:两个独立 bug 合一个修复批次。
> 原则:request.js 拦截器最小改动,后端只补一个字段。

---

## Task 1: request.js 拦截器识别 FormData 并删 Content-Type
- [x] **Step 1**:打开 [request.js](file:///workspace/ruoyi-ui/src/utils/request.js),定位 `service.interceptors.request.use(config => { ... })` 顶部
- [x] **Step 2**:在 `config.headers['Authorization']` 赋值之前,插入:
  ```js
  // 上传文件:删除默认的 application/json,让 axios 0.30.x 重新生成
  // `multipart/form-data; boundary=...`,否则后端报 "Current request is not a multipart request"
  if (typeof FormData !== 'undefined' && config.data instanceof FormData) {
    if (config.headers && 'Content-Type' in config.headers) {
      delete config.headers['Content-Type']
    }
  }
  ```
- [x] **Step 3**:确认其余逻辑(token 注入、GET 参数序列化、防重复提交)未触碰

---

## Task 2: AppVersionCheckResponse 新增 id 字段
- [x] **Step 1**:在 [AppVersionCheckResponse.java](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/domain/vo/AppVersionCheckResponse.java) 增加:
  ```java
  /** 版本主键,供 APP 端调 /api/app/version/download/{id} 用 */
  private Long id;
  ```
- [x] **Step 2**:生成对应的 `getId()` / `setId(Long id)` 访问器

---

## Task 3: AppVersionServiceImpl#checkUpdate 写入 id
- [x] **Step 1**:在 [AppVersionServiceImpl#checkUpdate](file:///workspace/ruoyi-app/src/main/java/com/ruoyi/app/service/impl/AppVersionServiceImpl.java#L104-L153) 拿到 `latest` 后,补 `resp.setId(latest.getId());`
- [x] **Step 2**:确认 `latest == null` 早返回路径不写 id(@JsonInclude(NON_NULL) 会自动隐藏 null)

---

## Task 4: 新建前端 APP 端公开 API 封装
- [x] **Step 1**:新建 [src/api/app/sdk.js](file:///workspace/ruoyi-ui/src/api/app/sdk.js)
- [x] **Step 2**:导出 `checkUpdate(appId, platform, versionCode)` 与 `downloadById(id)`,使用 `request` 拦截器
- [x] **Step 3**:在头部 Javadoc 注明"对应后端 @Anonymous 公开接口"

---

## Task 5: 端到端验证(部署环境)
- [x] **Step 1**:启动后端 + 前端 dev server(沙箱无 MySQL/Redis,执行需部署环境)
- [x] **Step 2**:管理端点"上传 APK",Network 面板确认 Request Headers `Content-Type: multipart/form-data; boundary=...`,Response `code=200`
- [x] **Step 3**:Postman/curl 调 `GET /api/app/version/check?appId=...&platform=...&versionCode=...`,响应 `data.id` 是 Long
- [x] **Step 4**:浏览器直接访问 `GET /api/app/version/download/{id}`,确认 302 跳转 + `download_count` 自增 1

> **沙箱注记**:本次会话环境为无 MySQL/Redis 远程沙箱,无法启 `ruoyi-admin` 做端到端冒烟;代码修改已就位(见 Task 1-4),需在部署环境实际验证。

---

# Task Dependencies
- Task 1 → Task 5(上传链路)
- Task 2 + Task 3 → Task 5(check + id 链路)
- Task 4 → Task 5(SDK 演示)
