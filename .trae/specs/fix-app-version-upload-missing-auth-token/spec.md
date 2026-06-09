# APP 版本管理 — 上传接口 401 真因修复 Spec

## Why
用户在管理端用 `admin` 账号点"上传 APK"按钮,后端返回:
```json
{"code":401,"msg":"请求访问：/app/version/upload,认证失败,无法访问系统资源"}
```
上一轮我错误地把 `AppVersionController#uploadApk` 上的 `@PreAuthorize` 注解删除,理由是"用户没有 `app:version:add` 权限"。但 admin 账号是 super_admin(`roleKey=admin`、权限字符 `*:*:*`),在 [PermissionService.hasPermi](file:///workspace/ruoyi-framework/src/main/java/com/ruoyi/framework/web/service/PermissionService.java#L27-L40) 中有 `permissions.contains(Constants.ALL_PERMISSION)` 短路逻辑,**任何 `app:version:*` 都应通过**。401 的真实根因不在 `@PreAuthorize`,而在 `JwtAuthenticationTokenFilter` 环节:前端上传请求**根本没带 token**,所以触发了 `AuthenticationEntryPointImpl`(401),而不是 `@PreAuthorize` 拒绝(403)。

## What Changes
- **恢复** `AppVersionController#uploadApk` 的 `@PreAuthorize("@ss.hasPermi('app:version:add') or @ss.hasPermi('app:version:edit')")` 注解(承认为之前方向错误)。
- **修改** `ruoyi-ui/src/api/app/version.js` 的 `uploadApk` 函数,改用 `request` 拦截器发起请求,使其**自动携带 `Authorization: Bearer <token>` 头**;不再 `import axios from 'axios'`。
- **同步** `ruoyi-ui/src/views/app/version/index.vue` 的 `customUpload`,适配 `request` 返回的响应结构(`res` 已是 RuoYi 业务数据 `{code, msg, data}`)。

## Impact
- 受影响能力:沿用 [enhance-app-version-with-apk-upload](file:///workspace/.trae/specs/enhance-app-version-with-apk-upload/spec.md)
- 受影响代码:
  - [AppVersionController.java](file:///workspace/ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppVersionController.java) — 恢复权限注解
  - [version.js](file:///workspace/ruoyi-ui/src/api/app/version.js) — `uploadApk` 改用 `request`
  - [index.vue](file:///workspace/ruoyi-ui/src/views/app/version/index.vue) — `customUpload` 适配响应结构

## Why Not @PreAuthorize-less
RuoYi 既有 `CommonController.uploadFile` 确实不带权限注解,但它是给"通用资源上传"用的;**业务数据写入**(`add` / `edit`)的权限保护不能通过"放开上传接口"绕过。正确做法是**让上传请求带 token**,而不是降级权限。

## ADDED Requirements

### Requirement: 上传接口权限注解
系统 SHALL 在 [AppVersionController.java](file:///workspace/ruoyi-admin/src/main/java/com/ruoyi/web/controller/app/AppVersionController.java) 的 `uploadApk` 方法上挂 `@PreAuthorize("@ss.hasPermi('app:version:add') or @ss.hasPermi('app:version:edit')")`,与同控制器内 `add` / `edit` 行为一致。

#### Scenario: admin 账号上传
- **WHEN** admin(super_admin)携带有效 token 调用
- **THEN** 权限检查通过,进入文件落盘逻辑

#### Scenario: 无 token
- **WHEN** 请求 header 中无 `Authorization`
- **THEN** `JwtAuthenticationTokenFilter` 不设 SecurityContext,SecurityConfig 的 `.anyRequest().authenticated()` 触发 `AuthenticationEntryPointImpl`,返回 **401**(由前端带 token 修复,而非降级后端权限)

### Requirement: 前端上传 API 走 request 拦截器
[version.js](file:///workspace/ruoyi-ui/src/api/app/version.js) 的 `uploadApk(data)` SHALL 改用 `request` 实例(默认导出),`request.js` 拦截器会自动:
- 从 `Cookies.get('Admin-Token')` 读 token
- 注入 `config.headers['Authorization'] = 'Bearer ' + token`
- 不显式设 `Content-Type`,由 axios 检测 `FormData` 后自动写 `multipart/form-data; boundary=...`

```js
import request from '@/utils/request'

export function uploadApk(data) {
  return request({
    url: '/app/version/upload',
    method: 'post',
    data: data
  })
}
```

#### Scenario: 上传带 token
- **WHEN** 用户已登录(Admin-Token cookie 存在)
- **THEN** axios 实际请求 header 中包含 `Authorization: Bearer eyJhbG...`,后端 `JwtAuthenticationTokenFilter` 解析成功,进入 `AppVersionController#uploadApk`,权限校验通过

#### Scenario: 替换前的对照
- **WHEN** 旧代码用 `axios({...})` 直发
- **THEN** 实际请求 header 中**没有** `Authorization`,后端直接 401,后续权限校验无法触发

### Requirement: customUpload 适配响应结构
[index.vue](file:///workspace/ruoyi-ui/src/views/app/version/index.vue) 的 `customUpload` SHALL 适配 `request` 拦截器**已剥壳**的响应:
- 旧 axios 直发:`res = { data: { code, msg, data } }`,需 `res.data.data`
- 走 `request` 后:`res = { code, msg, data }`,`res.data` 即业务数据

```js
customUpload(option) {
  const formData = new FormData()
  formData.append('file', option.file)
  formData.append('appId', this.form.appId)
  formData.append('platform', this.form.platform)
  formData.append('versionCode', this.form.versionCode)
  uploadApk(formData)
    .then((res) => {
      if (res && res.code === 200) {
        this.form.downloadUrl = res.data.url
        this.form.packageSize = res.data.size
        this.form.md5 = res.data.md5
        this.$modal.msgSuccess('上传成功,字段已自动回填')
        option.onSuccess(res)
      } else {
        const msg = (res && res.msg) || '上传失败'
        this.$modal.msgError(msg)
        option.onError(new Error(msg))
      }
    })
    .catch((err) => {
      this.$modal.msgError('上传失败:' + (err.message || '网络错误'))
      option.onError(err)
    })
    .finally(() => {
      this.uploadLoading = false
    })
}
```

#### Scenario: 上传成功回填
- **WHEN** 后端返回 `code=200, data={url,size,md5}`
- **THEN** 三个字段全部写入 `form`,提示"上传成功,字段已自动回填"

## MODIFIED Requirements
无(只对既有 `enhance-app-version-with-apk-upload` 做 bug 修复,不引入新行为)。

## REMOVED Requirements
无。

## 验收点
详见 `checklist.md`。
