# APP 版本管理 — APK 上传功能 任务清单

> 范围:在已有的 `add-app-version-management` 基础上,新增"上传 APK"按钮与后端上传接口。
> 工作原则:不破坏外链模式;前端上传成功后自动回填 `downloadUrl/packageSize/md5`。

---

## Task 1: 提升 Spring Boot 文件上传上限
- [x] **Step 1**:修改 `ruoyi-admin/src/main/resources/application.yml`,`max-file-size: 200MB` / `max-request-size: 250MB`
- [x] **Step 2**:`RuoYiConfig.getUploadPath()` 与 `serverConfig.getUrl()` 在 `ruoyi-common` / `ruoyi-framework` 已可用
- [ ] **Step 3**:启动 ruoyy-admin 后冒烟(沙箱无 MySQL/Redis,执行需部署环境)

---

## Task 2: 后端 — `AppVersionController` 新增 upload 接口
- [x] **Step 1**:`AppVersionController#uploadApk` 已添加,`@PreAuthorize("@ss.hasPermi('app:version:add') or @ss.hasPermi('app:version:edit')")` + `@Log(title="APP版本管理", businessType=INSERT)`
- [x] **Step 2**:`IAppVersionService#uploadApk` 已声明
- [x] **Step 3**:`AppVersionServiceImpl#uploadApk` 实现完成,流式 MD5 + 大小换算 + 文件名校验
- [x] **Step 4**:`AppVersionUploadResponse` VO 已创建,字段 `url/fileName/originalName/size/md5`
- [ ] **Step 5**:Swagger 冒烟(沙箱无 DB,执行需部署环境)

---

## Task 3: 前端 API 封装
- [x] `src/api/app/version.js` 末尾增加 `uploadApk`,使用 `axios` 直发 multipart 请求

---

## Task 4: 前端弹窗集成上传组件
- [x] **Step 1**:`data()` 中增加 `uploadHeaders/uploadUrl/uploadExtra/uploadLoading`
- [x] **Step 2**:"下载地址"行替换为"输入框 + 上传按钮 + MD5 链接"组合,隐藏 `<el-upload>` 组件
- [x] **Step 3**:`triggerUpload/beforeUpload/onUploadSuccess/onUploadError` 四个方法已实现
- [x] **Step 4**:`created()` 中刷新 `uploadHeaders`(避免 `getToken()` 在 `data()` 初始化时还未就绪)

---

## Task 5: 端到端冒烟
- 沙箱未启动 ruoyi-admin,需在部署环境完成;详细步骤已写在 `checklist.md` 三、端到端

---

## Task 6: 提交
- 沙箱非 git 工程,实际 commit 需在部署环境执行,见 `checklist.md` 五

---

# Task Dependencies
- Task 1 → Task 2(必须先提高上限才能上传 APK)
- Task 2 → Task 3(后端接口先就绪)
- Task 3 → Task 4(前端 API 先行)
- 全部 → Task 5、6
