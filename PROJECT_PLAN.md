# Enterprise Base V2 — 项目规划

> 基于 RuoYi-Vue (Spring Boot 3) 加固后端 + React 前端的企业内部基座

## 技术栈

| 层 | 技术 | 说明 |
|----|------|------|
| **后端** | RuoYi-Vue SpringBoot3 分支 (已 P0+P1 加固) | MyBatis + MySQL + Redis |
| **前端** | React 18 + TypeScript + Ant Design Pro | Vite 构建 |
| **部署** | Docker Compose | MySQL + Redis + MinIO + Backend + Frontend |

## 模块规划

### 后端模块（基于 RuoYi 改造）

| 模块 | 职责 | 状态 |
|------|------|------|
| `ruoyi-admin` | 启动入口、配置 | ✅ 已有（已加固） |
| `ruoyi-framework` | 安全、CORS、拦截器 | ✅ 已有（已加固） |
| `ruoyi-system` | 用户/角色/菜单/部门/字典/日志 | ✅ 已有 |
| `ruoyi-common` | 通用工具、常量、注解 | ✅ 已有 |
| `ruoyi-quartz` | 定时任务（已 RCE 加固） | ✅ 已有 |
| `ruoyi-generator` | 代码生成器 | ✅ 已有 |
| **`ruoyi-project`** | **项目管理/任务/标签/评论** | 🆕 新增 |

### 前端页面规划

| 页面 | 对应后端 | 优先级 |
|------|----------|--------|
| 登录 / 注册 | AuthController | P0 |
| 用户管理 | SysUserController | P0 |
| 角色管理 | SysRoleController | P0 |
| 菜单管理 | SysMenuController | P0 |
| 部门管理 | SysDeptController | P0 |
| 字典管理 | SysDictController | P0 |
| 操作日志 | SysOperlogController | P0 |
| **项目管理** | **ProjectController** | P1 |
| **任务管理** | **TaskController** | P1 |
| **标签管理** | **LabelController** | P1 |
| **评论/通知** | **CommentController** | P1 |
| 附件管理 | AttachmentController | P2 |
| 系统监控 | ServerController | P2 |
| 代码生成 | GenController | P2 |

## 阶段计划

### Phase 0：基座初始化 ✅ (Day 1)
- [x] Fork RuoYi 到 `zwd0313/RuoYi-Vue`
- [x] P0+P1 安全加固并编译通过
- [x] 创建项目目录 `enterprise-base-v2`
- [x] 规划文档
- [ ] 初始化 React 前端项目（Ant Design Pro 脚手架）
- [ ] 清理 RuoYi 前端（`ruoyi-ui` 目录标记为 deprecated）
- [ ] Docker Compose 本地开发环境

### Phase 1：前端底盘搭建 (Day 2-4)
- [ ] Ant Design Pro 脚手架初始化
- [ ] 对接 RuoYi 后端 API（登录/JWT/刷新）
- [ ] 全局 Layout（侧边栏菜单动态加载）
- [ ] 用户/角色/菜单/部门/字典管理页面
- [ ] 操作日志页面

### Phase 2：业务模块 — 后端 (Day 3-5)
- [ ] 新建 `ruoyi-project` Maven 模块
- [ ] 数据库表设计（project / task / label / comment / notification）
- [ ] MyBatis Mapper + Service + Controller
- [ ] 集成 RuoYi 数据权限体系（按部门/项目隔离）

### Phase 3：业务模块 — 前端 (Day 5-8)
- [ ] 项目列表/详情/创建/编辑
- [ ] 任务看板（看板视图 + 列表视图）
- [ ] 标签管理
- [ ] 评论/通知

### Phase 4：部署 + 收口 (Day 8-10)
- [ ] Docker 镜像打包（多阶段构建）
- [ ] 部署到阿里云
- [ ] 环境变量配置（JWT_SECRET 等）
- [ ] 冒烟测试

## 从 enterprise-base 迁移的业务实体

| 实体 | 字段 | 迁移要点 |
|------|------|----------|
| **Project** | name, description, status, owner_id | JPA→MyBatis, 新增数据权限 |
| **Task** | title, description, status, priority, assignee_id, project_id | 同上 |
| **Label** | name, color | 简单迁移 |
| **Comment** | content, author_id, task_id | 同上 |
| **Notification** | type, content, read, user_id | 同上 |
| **Attachment** | filename, path, size | 迁移 + 对接 MinIO |
| **ProjectMember** | project_id, user_id, role | 复用 RuoYi 角色体系 |

## 环境变量清单

| 变量 | 必填 | 说明 |
|------|------|------|
| `JWT_SECRET` | ✅ | ≥ 32 位随机密钥 |
| `DRUID_MASTER_USERNAME` | ✅ | 数据库用户 |
| `DRUID_MASTER_PASSWORD` | ✅ | 数据库密码 |
| `REDIS_PASSWORD` | ✅ | Redis 密码 |
| `CORS_ALLOWED_ORIGINS` | ✅ | 前端域名 |
| `SWAGGER_ENABLED` | 可选 | 开发时 `true` |
| `MINIO_ENDPOINT` | 可选 | MinIO 地址 |
| `MINIO_ACCESS_KEY` | 可选 | MinIO AK |
| `MINIO_SECRET_KEY` | 可选 | MinIO SK |

## 仓库

- **后端**：`zwd0313/RuoYi-Vue`（分支 `security-p0-p1-hardening` → 后续合并到 `main`）
- **前端**：`zwd0313/enterprise-base-v2-frontend`（待创建）
