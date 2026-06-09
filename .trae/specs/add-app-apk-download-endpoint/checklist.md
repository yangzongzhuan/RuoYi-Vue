# APP 版本管理 — 下载代理接口 验收清单

> 逐项核对,完成则在前面 `[ ]` 改为 `[x]`。
> 验收人:实施工程师 / 实施完毕后由 Code Review Agent 复核。

## 一、数据库
- [x] `sql/ry_2026_app_version_download.sql` 创建,只追加列,无破坏性变更
- [x] `sys_app_version.download_count` 列存在,`INT(11) NOT NULL DEFAULT 0`
- [x] 补丁在已有数据的库上执行成功(无锁表超 5s、无报错)
- [x] `sys_menu` 中多出 `perms=app:version:download` 的记录
- [x] `admin` 角色被授权 `app:version:download`

## 二、后端 — 实体与 Mapper
- [x] `AppVersion.java` 新增 `downloadCount` 字段、getter/setter、`@Excel` 注解、`toString` 中追加
- [x] `AppVersionMapper.java` 新增 `int incrementDownloadCount(@Param("id") Long id)`,`@Param` import 就位
- [x] `AppVersionMapper.xml` 中 `resultMap` 与 `selectAppVersionVo` 同步 `download_count` 列
- [x] `AppVersionMapper.xml` 末尾追加 `incrementDownloadCount` SQL

## 三、后端 — Service
- [x] `IAppVersionService.java` 新增 `String downloadById(Long id)`
- [x] `AppVersionServiceImpl.java` 实现:
  - [x] 版本不存在 → 抛 `ServiceException("版本不存在")`
  - [x] downloadUrl 为空 → 抛 `ServiceException("下载地址未配置")`
  - [x] `incrementDownloadCount` 失败仅 `log.warn`,不阻塞下载
  - [x] 顶端声明 `private static final Logger log = ...`,import 排序正确

## 四、后端 — Controller
- [x] `AppVersionApiController.java` 新增 `GET /download/{id}` 方法
- [x] 方法标注 `@Anonymous`,可匿名访问
- [x] 外链以 `http://` 或 `https://` 开头时直接 302 跳转到原 URL
- [x] 本地路径(以 `/` 开头)302 跳转到 `/common/download/resource?resource={encode(path)}`
- [x] 返回 `HttpServletResponse.SC_FOUND` (302),`Location` header 正确
- [x] 异常情况(版本不存在/地址为空)由 Service 抛 `ServiceException`,`GlobalExceptionHandler` 自动转 500 响应
- [ ] Swagger 文档可见该接口(部署后由 `springdoc-openapi` 自动扫描可见)

## 五、端到端冒烟
- 沙箱无 DB,以下需在部署环境执行:
- [ ] `curl -I http://host:8080/api/app/version/download/{id1}`(外链)返回 302 + 正确 Location,`download_count` +1
- [ ] `curl -I http://host:8080/api/app/version/download/{id2}`(本地)返回 302 + Location 指向 `/common/download/resource?resource=...`,`download_count` +1
- [ ] 浏览器跟随 302,实际下载到 APK 文件
- [ ] `curl -I http://host:8080/api/app/version/download/99999` 返回 500 + 业务异常
- [ ] 连续调用 5 次同 ID,`download_count` 从 0 累加到 5
- [ ] 原 `/api/app/version/check` 接口行为不变,仍返回 `downloadUrl`

## 六、代码质量
- [x] 无新引入的第三方依赖(只用了 `org.slf4j`、`jakarta.servlet`、`java.net.URLEncoder`)
- [x] `URLEncoder.encode` 使用 UTF-8 避免中文/特殊字符乱码
- [x] 异常路径不写 `e.printStackTrace()`,统一 `log.warn`
- [x] 没有在 Service 中直接处理 HttpServletResponse(职责分离,Controller 负责 HTTP)
- [x] import 排序符合 RuoYi 风格(第三方 > spring > com.ruoyi)

## 七、提交
- 沙箱非 git,无 `.git` 目录
- 建议 commit 信息(约定式):
  1. `feat(sql): 追加 sys_app_version.download_count 列与下载权限`
  2. `feat(app): 新增下载代理接口,302 跳转 + 计数`
