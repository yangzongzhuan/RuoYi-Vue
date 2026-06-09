# APP 版本管理 — 下载代理接口 任务清单

> 范围:在已有的 APP 版本管理基础上,新增"下载代理 + 计数"能力。
> 改造:1 个表字段追加、1 个 Mapper 方法、1 个 Service 方法、1 个 Controller 接口。

---

## Task 0: 数据库补丁脚本
- [x] **Step 1**:`sql/ry_2026_app_version_download.sql` 已创建,只追加 `download_count` 列
- [x] **Step 2**:`sys_menu` 中追加 `perms=app:version:download` 的按钮,授权给 admin
- [ ] **Step 3**:执行校验(沙箱无 MySQL,需部署环境执行 `mysql -uroot -p ry < sql/ry_2026_app_version_download.sql`)

---

## Task 1: 实体与 Mapper 改造
- [x] **Step 1**:`AppVersion.java` 新增 `downloadCount` 字段、`@Excel` 注解、getter/setter、`toString` 同步
- [x] **Step 2**:`AppVersionMapper.java` 新增 `int incrementDownloadCount(@Param("id") Long id)`,`@Param` import 已加
- [x] **Step 3**:`AppVersionMapper.xml` 中 `resultMap` 同步 `download_count` 列,`selectAppVersionVo` SQL 中追加该列,末尾追加 `incrementDownloadCount` 的 `<update>` 节点

---

## Task 2: Service 层
- [x] **Step 1**:`IAppVersionService.java` 声明 `String downloadById(Long id)`
- [x] **Step 2**:`AppVersionServiceImpl.java` 实现:
  - [x] 版本不存在 → 抛 `ServiceException("版本不存在")`
  - [x] downloadUrl 为空 → 抛 `ServiceException("下载地址未配置")`
  - [x] `incrementDownloadCount` 失败仅 `log.warn`,不阻塞下载
  - [x] 顶端声明 `private static final Logger log = LoggerFactory.getLogger(...)`,import 按字母序排好

---

## Task 3: 公开 Controller 接口
- [x] **Step 1**:`AppVersionApiController.java` 新增 `GET /download/{id}` 方法:
  - [x] 标注 `@Anonymous`
  - [x] 外链以 `http://` 或 `https://` 开头时直接 302
  - [x] 本地路径(以 `/` 开头)302 跳转到 `/common/download/resource?resource={encode(path)}`
  - [x] 返回 `HttpServletResponse.SC_FOUND` (302),`Location` header 正确
  - [x] `URLEncoder.encode` 使用 UTF-8
  - [x] import 完整:`HttpServletResponse`、`PathVariable`、`URLEncoder`、`IOException`

---

## Task 4: 编译与冒烟
- [ ] **Step 1**:`mvn -pl ruoyi-app,ruoyi-admin -am clean compile`(沙箱网络不通,跳过)
- [ ] **Step 2**:启动 ruoyi-admin 后,准备两条测试数据(外链 + 本地),`curl -I` 验证 302

---

## Task 5: 端到端联调(可选)
- 不修改 `checkUpdate`,客户端两种方式仍可用:
  - 方式 A:客户端拿到 `downloadUrl` 后直接下载(原行为)
  - 方式 B:客户端拼 `baseURL + /api/app/version/download/{id}`,后端 302 跳转并计数

---

## Task 6: 提交
- 沙箱非 git,实际 commit 需在部署环境执行,建议拆分:
  1. `feat(sql): 追加 sys_app_version.download_count 列与下载权限`
  2. `feat(app): 新增下载代理接口,302 跳转 + 计数`

---

# Task Dependencies
- Task 0 → Task 1
- Task 1 → Task 2
- Task 2 → Task 3
- 全部 → Task 4
