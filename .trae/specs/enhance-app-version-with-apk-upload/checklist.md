# APP 版本管理 — APK 上传功能 验收清单

> 逐项核对,完成则在前面 `[ ]` 改为 `[x]`。
> 验收人:实施工程师 / 实施完毕后由 Code Review Agent 复核。

## 一、后端
- [x] `ruoyi-admin/src/main/resources/application.yml` 中 `spring.servlet.multipart.max-file-size=200MB`、`max-request-size=250MB`
- [x] `AppVersionController#uploadApk` 方法已存在,路径 `POST /app/version/upload`
- [x] `@PreAuthorize("@ss.hasPermi('app:version:add') or @ss.hasPermi('app:version:edit')")` 权限就位
- [x] `@Log(title = "APP版本管理", businessType = BusinessType.INSERT)` 注解就位
- [x] 扩展名校验通过,`apk/ipa/hap` 之外抛 `ServiceException`
- [x] 必填参数(`appId/platform/versionCode`)缺失抛 `ServiceException`
- [x] MD5 使用流式计算(`DigestUtils.md5DigestAsHex(InputStream)`),不 OOM
- [x] 包大小换算为 MB,BigDecimal 保留 2 位小数(`RoundingMode.HALF_UP`)
- [x] 文件名规范:`{safeAppId}_{safePlatform}_{versionCode}_{yyyyMMddHHmmss}.{ext}`,`appId/platform` 已做非字母数字下划线字符过滤
- [x] 落盘路径:`{RuoYiConfig.getUploadPath()}/app/`,目录不存在时自动创建
- [x] 返回结构包含 `url/fileName/originalName/size/md5` 五个字段
- [ ] Swagger 文档可见该接口(部署后由 `springdoc-openapi` 自动扫描可见)

## 二、前端
- [x] `src/api/app/version.js` 中 `uploadApk` 函数存在,使用 `axios` 而非 `request`
- [x] `src/views/app/version/index.vue` 弹窗"下载地址"行增加"上传 APK"按钮
- [x] 隐藏的 `<el-upload>` 组件存在,`accept=".apk,.ipa,.hap"`,`:auto-upload="false"`
- [x] `uploadHeaders` 在 `created()` 中通过 `getToken()` 注入
- [x] `triggerUpload` 校验 `appId/platform/versionCode`,缺失时 `msgError` 提示
- [x] `beforeUpload` 校验扩展名与大小(200MB)
- [x] `onUploadSuccess` 成功后将 `url/size/md5` 写入 `form` 对应字段
- [x] `onUploadError` 网络错误时 `msgError` 提示

## 三、端到端
- 沙箱未启动 MySQL/Redis 与 ruoyi-admin,以下需在部署环境执行:
- [ ] 上传 30MB APK 成功,3 个字段被自动回填
- [ ] 匿名访问 `data.url` 可下载该 APK
- [ ] 上传 `test.zip` 被前端拦截,提示"仅支持 apk/ipa/hap"
- [ ] 上传 250MB 大文件被后端拒绝,提示"文件大小超过限制"
- [ ] 删除版本记录后,文件仍保留(本期不级联删除,符合最小化原则)

## 四、代码质量
- [x] `AppVersionUploadResponse` 使用显式 getter/setter(不引入 Lombok,与既有 `AppVersionCheckResponse` 风格一致)
- [x] 前端未使用硬编码 token,统一通过 `getToken()` 读取
- [x] 后端未在循环或大文件处使用 `byte[]`,全程流式 IO
- [x] 没有冗余的 URL 拼接工具,直接使用 `serverConfig.getUrl()`
- [x] 扩展名白名单抽到常量 `ALLOW_EXTS`,便于后续扩展

## 五、提交
- 沙箱非 git 工程,无 `.git` 目录,实际 commit 需在部署环境执行
- 建议 commit 信息(约定式):
  1. `feat(app): 提升 multipart 上传上限至 200MB`(改 `application.yml`)
  2. `feat(app): 新增 APK 上传接口,自动回填元数据`(改 `AppVersionController/ServiceImpl/IAppVersionService/AppVersionUploadResponse`)
  3. `feat(ui): 新增弹窗上传按钮与回填逻辑`(改 `api/app/version.js`、`views/app/version/index.vue`)
