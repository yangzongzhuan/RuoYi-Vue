# Enterprise Base V2 Portal — 业务前台基础框架设计

> 当前设计只定义业务前台的基础框架、工程边界与通用能力，暂不绑定具体业务模块。

## 1. 定位

业务前台（建议仓库名：`enterprise-base-v2-portal`）面向普通业务用户，承载日常业务操作与协作流程。

与管理中台边界：

| 前端 | 定位 | 主要用户 | 职责 |
|------|------|----------|------|
| `enterprise-base-v2-frontend` | 管理中台 / 后台管理端 | 管理员、运营、系统维护人员 | 权限、组织、参数、日志、监控、系统配置 |
| `enterprise-base-v2-portal` | 业务前台 | 普通业务用户、团队成员 | 工作台、业务流转、协作、个人任务、通知 |

原则：**管理配置在中台，业务操作在前台**。

## 2. 技术栈建议

优先保持与管理中台一致，降低维护成本：

- React 19
- TypeScript
- Vite
- Ant Design 5
- React Router
- Axios
- Zustand 或 TanStack Query（二选一，建议 TanStack Query 管服务端状态）
- dayjs

不建议一开始引入复杂框架（如 Next.js SSR），除非后续有 SEO 或多租户门户公开访问诉求。

## 3. 工程目录建议

```text
enterprise-base-v2-portal/
├── public/
├── src/
│   ├── app/                 # 应用入口、路由、Provider
│   │   ├── App.tsx
│   │   ├── router.tsx
│   │   └── providers.tsx
│   ├── api/                 # API 分层
│   │   ├── auth.ts
│   │   ├── user.ts
│   │   └── portal.ts
│   ├── assets/
│   ├── components/          # 通用组件
│   │   ├── common/
│   │   ├── layout/
│   │   └── feedback/
│   ├── features/            # 业务功能模块，后续按业务增长
│   │   ├── dashboard/
│   │   ├── workspace/
│   │   ├── notifications/
│   │   └── profile/
│   ├── hooks/
│   ├── layouts/
│   │   ├── PortalLayout.tsx
│   │   └── AuthLayout.tsx
│   ├── pages/
│   │   ├── LoginPage.tsx
│   │   ├── DashboardPage.tsx
│   │   ├── NotFoundPage.tsx
│   │   └── ForbiddenPage.tsx
│   ├── stores/
│   ├── styles/
│   ├── types/
│   └── utils/
├── index.html
├── package.json
├── tsconfig.json
└── vite.config.ts
```

## 4. 基础页面骨架

第一阶段只做框架，不做具体业务：

1. 登录页
   - 复用 RuoYi `/login`
   - token 存储策略与管理中台一致
   - 登录后进入工作台

2. 门户工作台 `/dashboard`
   - 用户信息卡片
   - 快捷入口区域
   - 待办占位区
   - 通知占位区
   - 最近访问/最近操作占位区

3. 通用布局 `PortalLayout`
   - 顶部导航：Logo、全局搜索入口、通知、用户菜单
   - 左侧或顶部业务导航：根据最终业务复杂度决定
   - 内容区：卡片化、轻量、面向业务操作

4. 个人中心 `/profile`
   - 基本资料
   - 修改密码
   - 头像展示/上传
   - 复用后端 `/system/user/profile`

5. 错误页
   - 403 Forbidden
   - 404 Not Found
   - 500 Error Boundary

## 5. 路由设计

```text
/login
/
/dashboard
/profile
/notifications
/workspace              # 业务工作区占位
/workspace/:id          # 后续业务详情占位
403
404
```

路由守卫：

- 无 token → `/login`
- token 存在但 `/getInfo` 失败 → 清 token → `/login`
- 无权限 → `/403`

## 6. 权限模型

前台不重新造权限体系，直接复用 RuoYi：

- 登录：`/login`
- 当前用户：`/getInfo`
- 用户资料：`/system/user/profile`
- 按钮/页面权限：使用 `permissions` / `roles`

前台只做业务可见性控制：

- 页面级：路由 meta permissions
- 操作级：按钮权限组件 `<Permission />`
- 数据级：交给后端数据权限，不在前端硬编码过滤

## 7. API 层约定

沿用管理中台的 Axios 适配经验：

- `/login` 不携带旧 token
- 请求自动带 `Authorization: Bearer ${token}`
- 兼容 RuoYi 返回：`{ code, msg, data }`
- 兼容分页：`{ rows, total, code }`
- 统一处理 401 / 403 / 500

建议封装：

```ts
request.get<T>()
request.post<T>()
request.put<T>()
request.delete<T>()
```

## 8. UI 风格方向

业务前台应区别于管理中台：

- 管理中台：表格密集、配置导向、ProTable 为主
- 业务前台：任务流/卡片/详情/动态/协作导向

视觉建议：

- 主色延续蓝色系，保持与用户偏好一致
- 页面更轻、更留白，减少后台表格感
- 工作台优先卡片布局
- 详情页采用左右分栏：主内容 + 侧边信息

## 9. 首批基础组件

- `AppShell`：全局框架
- `TopNav`：顶部导航
- `SideNav` / `PortalMenu`：业务导航
- `UserMenu`：用户菜单
- `NotificationBell`：通知入口
- `PageHeader`：页面标题区
- `StatCard`：指标卡
- `EmptyState`：空状态
- `Permission`：权限控制
- `ErrorBoundary`：错误边界
- `LoadingPage`：全屏加载

## 10. 第一阶段里程碑

### M0：脚手架
- [ ] 创建 `enterprise-base-v2-portal` 仓库
- [ ] 初始化 React + TypeScript + Vite
- [ ] 配置 ESLint / tsconfig / path alias
- [ ] 配置 Vite proxy

### M1：认证闭环
- [ ] 登录页
- [ ] token 管理
- [ ] `/getInfo` 拉取用户信息
- [ ] 路由守卫
- [ ] 退出登录

### M2：门户壳
- [ ] `PortalLayout`
- [ ] 顶部导航
- [ ] 用户菜单
- [ ] 通知入口占位
- [ ] 工作台页面占位

### M3：通用能力
- [ ] API request 封装
- [ ] 权限组件
- [ ] 错误页
- [ ] Loading / Empty / Error 状态
- [ ] README 与部署说明

## 11. 暂不做

第一阶段不做以下内容，避免框架阶段失焦：

- 具体业务实体建模
- 复杂图表
- 移动端适配到 App 级别
- 多租户
- SSR / SEO
- 复杂低代码/表单设计器

## 12. 推荐下一步

先执行 M0 + M1：创建业务前台仓库并打通登录、用户信息、基础布局。登录闭环完成后，再进入具体业务模块设计。
