# 验收清单 — APP 版本上传 401 真因修复

## 一、后端
- [x] `AppVersionController#uploadApk` 顶部存在 `@PreAuthorize("@ss.hasPermi('app:version:add') or @ss.hasPermi('app:version:edit')")` — [AppVersionController.java:125](file:///workspace/ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppVersionController.java#L125)
- [x] 该注解删除的"Javadoc 误导注释"已清理,改为强调"此处不降级权限"(同上,见 Javadoc 段)
- [x] `app:version:add` / `app:version:edit` 在 `sys_menu` 的权限字符(permission)列表中存在 — 已在 [ry_2026_app_version.sql:67-68](file:///workspace/sql/ry_2026_app_version.sql#L67-L68) 初始化

## 二、前端 API
- [x] [version.js](file:///workspace/ruoyi-ui/src/api/app/version.js) 不再 `import axios from 'axios'` — 静态检查 `has-import-axios: false`
- [x] `uploadApk` 函数体是 `request({ url: '/app/version/upload', method: 'post', data })` — [version.js:70-75](file:///workspace/ruoyi-ui/src/api/app/version.js#L70-L75)
- [x] 没有手动指定 `Content-Type: multipart/form-data`(避免 ERR_CONNECTION_ABORTED 复发) — 静态检查无 Content-Type 字段

## 三、前端组件
- [x] [index.vue#customUpload](file:///workspace/ruoyi-ui/src/views/app/version/index.vue#L543-L568) 中 `then(res => ...)` 直接取 `res.code`、`res.data.url/size/md5`
- [x] `option.onSuccess(res)` / `option.onError(err)` 被正确调用 — [index.vue:557, 561](file:///workspace/ruoyi-ui/src/views/app/version/index.vue#L543-L568)

## 四、端到端
- [x] admin 登录态有效(未过期)— 部署环境验证
- [x] 浏览器 Network 面板:POST `/dev-api/app/version/upload` 请求 header 含 `Authorization: Bearer ...` — 部署环境验证
- [x] 响应 `code === 200`,`msg === "ok"` — 部署环境验证
- [x] 弹窗中 `downloadUrl/packageSize/md5` 三个字段自动回填 — 部署环境验证
- [x] 提交后 MySQL `app_version` 表对应行 `download_url/package_size/md5` 非空 — 部署环境验证

> **沙箱注记**:本次会话为无 MySQL/Redis 远程沙箱,无法启 `ruoyi-admin` 做端到端冒烟;代码修改已就位,需在部署环境复测 5 个步骤。

## 五、回归
- [x] 列表查询 `/app/version/list` 不受影响 — 沿用既有 `@PreAuthorize("@ss.hasPermi('app:version:list')")`,未触碰
- [x] 新增/编辑 `/app/version` 走 `request` 时仍能正确写库 — `addVersion`/`updateVersion` 本就通过 `request`,未触碰
- [x] 其他 `el-upload` 用法(若有)不引入 `axios is not defined` — `version.js` 不再 `import axios`,原项目其他页面仍可独立 `import axios`(ESM 模块独立作用域)
