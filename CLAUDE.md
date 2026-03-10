# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a customized **RuoYi-Vue v3.8.9** project named "JiuYan Image Text Creation System" (玖言图文创作系统). It's a Spring Boot + Vue.js full-stack application for automated PSD template processing, AI-powered content generation, and multi-platform social media publishing.

## Build Commands

### Backend (Maven)
```bash
# Build all modules
mvn clean install

# Build without running tests
mvn clean install -DskipTests

# Run the application (after building)
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

### Frontend (NPM)
```bash
cd ruoyi-ui

# Install dependencies
npm install

# Development server (port 80, proxies API to localhost:8080)
npm run dev

# Production build
npm run build:prod

# Staging build
npm run build:stage

# Lint code
npm run lint
```

## Architecture

### Backend Structure (Multi-Module Maven)
```
ruoyi-admin/      # Entry point, controllers, configuration
ruoyi-common/     # Utilities, base classes, domain annotations
ruoyi-framework/  # Spring Security, Redis, logging, CORS config
ruoyi-system/     # Business logic, services, mappers, domain models
ruoyi-quartz/     # Scheduled task management
ruoyi-generator/  # Code generator
```

Module dependency: `ruoyi-admin` → `ruoyi-framework` → `ruoyi-system` → `ruoyi-common`

### Frontend Structure (Vue 2)
```
ruoyi-ui/src/
├── api/           # API service modules (axios-based)
├── views/         # Page components
├── components/    # Reusable UI components
├── store/         # Vuex state management
├── router/        # Vue Router configuration
├── utils/         # Utility functions
└── layout/        # Layout templates
```

### Key Custom Business Components

- **Coze API Integration** (`ruoyi-system/src/main/java/com/ruoyi/system/coze/`): AI workflow client for content generation
- **PSD Task Management**: `PsdTask` domain, `IPsdTaskService` - handles task lifecycle
- **Task Queues**: `PhotoshopTaskQueue`, `TemPhotoshopJsxQuenu`, `PushGZHTaskQueue` - manage async processing
- **PSD Templates**: Controllers at `/psd/template`, APIs in `ruoyi-ui/src/api/custom/`

## Required Setup

### Jacob DLLs (Windows-only, for Photoshop automation)
```
jacob-1.17-x64.dll → C:\Windows\System32
jacob-1.17-x86.dll → C:\Windows\SysWOW64
```

### External Services Required
| Service | Port | Purpose |
|---------|------|---------|
| MySQL | 3306 | Database (schema: ry-vue) |
| Redis | 6379 | Cache, session storage |
| Python Service | 5409 | Douyin (TikTok) publishing automation |

### Configuration Files
- `ruoyi-admin/src/main/resources/application.yml` - Main Spring Boot config
- `ruoyi-admin/src/main/resources/application-druid.yml` - Database connection
- `ruoyi-ui/.env.development` - Frontend dev environment (API proxy)
- `ruoyi-ui/.env.production` - Frontend production environment

## Tech Stack

- **Backend**: Java 1.8, Spring Boot 2.5.15, Spring Security 5.7.12, MyBatis, JWT 0.9.1
- **Frontend**: Vue 2.6.12, Element UI 2.15.14, Vuex 3.6.0, Axios 0.28.1
- **Database**: MySQL with Druid connection pool
- **Cache**: Redis
- **Build**: Maven (backend), Vue CLI (frontend)

## Business Flow

1. User creates PSD task via frontend
2. Backend calls Coze API for AI content generation
3. Task queued in `PhotoshopTaskQueue`
4. Photoshop JSX script (`jsx/generate.jsx`) processes template
5. Output images saved to configured directory
6. Publishing to WeChat Official Account or Douyin (via Python service)
7. Status callbacks update task in database

## Notes

- No automated tests exist in this project
- PSD processing requires Adobe Photoshop installed on Windows
- The project uses Jacob COM bridge for Java-to-Photoshop communication
