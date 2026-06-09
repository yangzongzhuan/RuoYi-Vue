# APP 版本管理 — 下载代理接口 任务清单

> 范围:在已有的 APP 版本管理基础上,新增"下载代理 + 计数"能力。
> 改造:1 个表字段追加、1 个 Mapper 方法、1 个 Service 方法、1 个 Controller 接口。

---

## Task 0: 数据库补丁脚本
- [ ] **Step 1**:新建 `sql/ry_2026_app_version_download.sql`(增量补丁,只追加列):
  ```sql
  USE ry;
  ALTER TABLE sys_app_version
      ADD COLUMN download_count INT(11) NOT NULL DEFAULT 0 COMMENT '下载次数';
  ```

- [ ] **Step 2**:在 `sys_menu` 中追加按钮 `APP版本下载次数`(`perms=app:version:download`,本期不在前端暴露,仅占位以备后续):
  ```sql
  INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
  SELECT 'APP版本下载', menu_id, 7, '', '', 1, 0, 'F', '0', '0', 'app:version:download', '#', 'admin', sysdate(), ''
  FROM sys_menu WHERE menu_name = 'APP版本管理' LIMIT 1;
  INSERT INTO sys_role_menu (role_id, menu_id)
  SELECT 1, menu_id FROM sys_menu WHERE perms = 'app:version:download';
  ```

- [ ] **Step 3**:执行校验:
  ```bash
  mysql -uroot -p ry < sql/ry_2026_app_version_download.sql
  ```
  预期:`sys_app_version.download_count` 列存在;`sys_menu` 多出 1 条 `perms=app:version:download` 的记录;`admin` 角色已授权。

---

## Task 1: 实体与 Mapper 改造
- [ ] **Step 1**:`AppVersion.java` 新增字段与 getter/setter:
  ```java
  @Excel(name = "下载次数")
  private Integer downloadCount;
  public Integer getDownloadCount() { return downloadCount; }
  public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
  ```
  同步在 `toString()` 中追加 `downloadCount`。

- [ ] **Step 2**:`AppVersionMapper.java` 接口增加:
  ```java
  int incrementDownloadCount(@Param("id") Long id);
  ```

- [ ] **Step 3**:`AppVersionMapper.xml` 中 `resultMap` 与 `selectAppVersionVo` 同步追加:
  ```xml
  <result property="downloadCount" column="download_count" />
  ```
  并在末尾追加:
  ```xml
  <update id="incrementDownloadCount" parameterType="Long">
      update sys_app_version
      set download_count = download_count + 1
      where id = #{id}
  </update>
  ```

---

## Task 2: Service 层
- [ ] **Step 1**:`IAppVersionService.java` 声明:
  ```java
  /**
   * 客户端下载代理:递增计数,返回 downloadUrl。版本不存在或地址为空抛 ServiceException。
   */
  String downloadById(Long id);
  ```

- [ ] **Step 2**:`AppVersionServiceImpl.java` 实现:
  ```java
  @Override
  public String downloadById(Long id) {
      AppVersion v = appVersionMapper.selectAppVersionById(id);
      if (v == null) {
          throw new ServiceException("版本不存在");
      }
      if (StringUtils.isEmpty(v.getDownloadUrl())) {
          throw new ServiceException("下载地址未配置");
      }
      try {
          appVersionMapper.incrementDownloadCount(id);
      } catch (Exception e) {
          // 计数失败不阻塞下载,仅记录日志
          log.warn("递增下载次数失败,id={}", id, e);
      }
      return v.getDownloadUrl();
  }
  ```
  需 import `org.slf4j.Logger`、`org.slf4j.LoggerFactory`,并在类顶部:
  ```java
  private static final Logger log = LoggerFactory.getLogger(AppVersionServiceImpl.class);
  ```

---

## Task 3: 公开 Controller 接口
- [ ] **Step 1**:在 `AppVersionApiController.java` 中增加:
  ```java
  @Autowired
  private IAppVersionService appVersionService;

  /**
   * APP 端下载代理:递增计数,302 跳转到真实下载地址
   */
  @Anonymous
  @GetMapping("/download/{id}")
  public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
      String url = appVersionService.downloadById(id);
      String target;
      if (url.startsWith("http://") || url.startsWith("https://")) {
          target = url;
      } else {
          // 本地资源:统一走 RuoYi 通用下载接口
          String resource = url.startsWith("/") ? url : "/" + url;
          target = "/common/download/resource?resource=" + URLEncoder.encode(resource, "UTF-8");
      }
      response.setStatus(HttpServletResponse.SC_FOUND);
      response.setHeader("Location", target);
  }
  ```
  需 import:
  - `com.ruoyi.app.service.IAppVersionService`
  - `com.ruoyi.common.annotation.Anonymous`
  - `org.springframework.web.bind.annotation.{GetMapping, PathVariable}`
  - `jakarta.servlet.http.HttpServletResponse`
  - `java.io.IOException`
  - `java.net.URLEncoder`

---

## Task 4: 编译与冒烟
- [ ] **Step 1**:执行 `mvn -pl ruoyi-app,ruoyi-admin -am clean compile -DskipTests`(沙箱网络不通,跳过)
- [ ] **Step 2**:启动 ruoyi-admin,先准备两条测试数据:
  ```sql
  INSERT INTO sys_app_version (app_id, app_name, platform, version, version_code, update_type, download_url, status, create_by, create_time)
  VALUES
  ('myapp', 'MyApp', 'ios', '1.0.0', 100, '2', 'https://apps.apple.com/cn/app/myapp', '0', 'admin', sysdate()),
  ('myapp', 'MyApp', 'android', '1.0.0', 100, '2', '/profile/upload/app/test.apk', '0', 'admin', sysdate());
  SELECT id FROM sys_app_version;
  ```

- [ ] **Step 3**:匿名访问外链:
  ```bash
  curl -I http://localhost:8080/api/app/version/download/{id1}
  ```
  预期:
  ```
  HTTP/1.1 302
  Location: https://apps.apple.com/cn/app/myapp
  ```
  DB 中 `download_count` 从 0 → 1。

- [ ] **Step 4**:匿名访问本地资源(若有 `test.apk` 文件):
  ```bash
  curl -I http://localhost:8080/api/app/version/download/{id2}
  ```
  预期:302 + `Location: /common/download/resource?resource=%2Fprofile%2Fupload%2Fapp%2Ftest.apk`,`download_count` +1。

- [ ] **Step 5**:访问不存在 ID:
  ```bash
  curl -I http://localhost:8080/api/app/version/download/99999
  ```
  预期:500 业务异常 `版本不存在`,`download_count` 不变。

---

## Task 5: 端到端联调(可选,本增强不强制改前端)
- 不修改 `checkUpdate`,客户端两种方式仍可用:
  - 方式 A:客户端拿到 `downloadUrl` 后直接下载(原行为)
  - 方式 B:客户端拼 `baseURL + /api/app/version/download/{id}`,后端 302 跳转并计数
- 本期不强制前端改造,后续如需"统计每个版本的真实下载数",可让前端或客户端切换到方式 B。

---

## Task 6: 提交
- 沙箱非 git,实际 commit 需在部署环境执行,建议拆分:
  1. `feat(sql): 追加 sys_app_version.download_count 列与下载权限`
  2. `feat(app): 新增下载代理接口,302 跳转 + 计数`

---

# Task Dependencies
- Task 0 → Task 1(必须先有列才能在 Java 端读取)
- Task 1 → Task 2
- Task 2 → Task 3
- 全部 → Task 4
