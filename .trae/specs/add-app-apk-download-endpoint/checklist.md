# APP 版本管理 — 下载代理接口 验收清单

> 逐项核对,完成则在前面 `[ ]` 改为 `[x]`。
> 验收人:实施工程师 / 实施完毕后由 Code Review Agent 复核。

## 一、数据库
- [ ] `sql/ry_2026_app_version_download.sql` 创建,只追加列,无破坏性变更
- [ ] `sys_app_version.download_count` 列存在,`INT(11) NOT NULL DEFAULT 0`
- [ ] 补丁在已有数据的库上执行成功(无锁表超 5s、无报错)
- [ ] `sys_menu` 中多出 `perms=app:version:download` 的记录
- [ ] `admin` 角色被授权 `app:version:download`

## 二、后端 — 实体与 Mapper
- [ ] `AppVersion.java` 新增 `downloadCount` 字段、getter/setter、`@Excel` 注解、`toString` 中追加
- [ ] `AppVersionMapper.java` 新增 `int incrementDownloadCount(@Param("id") Long id)`
- [ ] `AppVersionMapper.xml` 中 `resultMap` 与 `selectAppVersionVo` 同步 `download_count` 列
- [ ] `AppVersionMapper.xml` 末尾追加 `incrementDownloadCount` SQL

## 三、后端 — Service
- [ ] `IAppVersionService.java` 新增 `String downloadById(Long id)`
- [ ] `AppVersionServiceImpl.java` 实现:
  - [ ] 版本不存在 → 抛 `ServiceException("版本不存在")`
  - [ ] downloadUrl 为空 → 抛 `ServiceException("下载地址未配置")`
  - [ ] `incrementDownloadCount` 失败仅 `log.warn`,不阻塞下载
  - [ ] 顶端声明 `private static final Logger log = ...`

## 四、后端 — Controller
- [ ] `AppVersionApiController.java` 新增 `GET /download/{id}` 方法
- [ ] 方法标注 `@Anonymous`,可匿名访问
- [ ] 外链以 `http://` 或 `https://` 开头时直接 302 跳转到原 URL
- [ ] 本地路径(以 `/` 开头)302 跳转到 `/common/download/resource?resource={encode(path)}`
- [ ] 返回 `HttpServletResponse.SC_FOUND` (302),`Location` header 正确
- [ ] 异常情况(版本不存在/地址为空)返回 500 业务异常,`download_count` 不递增
- [ ] Swagger 文档可见该接口

## 五、端到端冒烟
- [ ] `curl -I http://host:8080/api/app/version/download/{id1}`(外链)返回 302 + 正确 Location,`download_count` +1
- [ ] `curl -I http://host:8080/api/app/version/download/{id2}`(本地)返回 302 + Location 指向 `/common/download/resource?resource=...`,`download_count` +1
- [ ] 浏览器跟随 302,实际下载到 APK 文件
- [ ] `curl -I http://host:8080/api/app/version/download/99999` 返回 500 + 业务异常
- [ ] 连续调用 5 次同 ID,`download_count` 从 0 累加到 5
- [ ] 原 `/api/app/version/check` 接口行为不变,仍返回 `downloadUrl`

## 六、代码质量
- [ ] 无新引入的第三方依赖
- [ ] `URLEncoder.encode` 使用 UTF-8 避免中文/特殊字符乱码
- [ ] 异常路径不写 `e.printStackTrace()`,统一 `log.warn`
- [ ] 没有在 Service 中直接处理 HttpServletResponse(职责分离,Controller 负责 HTTP)

## 七、提交
- 沙箱非 git,无 `.git` 目录
- 建议 commit 信息(约定式):
  1. `feat(sql): 追加 sys_app_version.download_count 列与下载权限`
  2. `feat(app): 新增下载代理接口,302 跳转 + 计数`
