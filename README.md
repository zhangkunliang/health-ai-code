# health-ai-code

## 项目简介

health-ai-code 是一个结合 AI 与健康领域的智能代码生成平台。用户可以通过自然语言描述来生成健康相关的应用程序，平台会根据用户需求自动生成相应的代码，并支持一键部署。

## 技术架构

### 后端技术栈

- Spring Boot 3.5.4
- Java 21
- MySQL 8.0 (主数据库)
- Redis (缓存和会话管理)
- MyBatis Flex (ORM框架)
- LangChain4j (AI集成框架)
- Knife4j (API文档)
- Lombok (简化Java代码)

### 前端技术栈

- Vue 3
- TypeScript
- Vite
- Axios

## 系统设计与核心流程

### 整体架构

系统采用前后端分离架构，后端提供 RESTful API 接口，前端通过 HTTP 请求与后端交互。系统核心功能包括用户管理、应用管理、AI代码生成和应用部署。

### 核心模块设计

#### 1. 用户管理模块

用户管理模块负责用户的注册、登录、权限控制等功能：

- 用户注册：支持账号密码注册，密码加密存储
- 用户登录：基于 Session 的认证机制
- 权限控制：通过 AOP 实现基于角色的访问控制（RBAC）
- 用户信息管理：支持用户信息查看和更新

主要实体类：[User](src/main/java/com/ai/healthaicode/model/entity/User.java)

#### 2. 应用管理模块

应用管理模块负责用户创建、管理和使用 AI 应用：

- 应用创建：用户可以通过自然语言初始化应用
- 应用更新：支持修改应用名称等基本信息
- 应用查询：支持分页查询个人应用和精选应用
- 权限控制：用户只能操作自己的应用，管理员可以操作所有应用

主要实体类：[App](src/main/java/com/ai/healthaicode/model/entity/App.java)

#### 3. AI代码生成模块

AI代码生成是系统的核心功能，用户可以通过自然语言与AI交互生成代码：

- 多种生成模式：
  - HTML 单文件生成
  - 多文件项目生成
- 流式响应：支持 Server-Sent Events (SSE) 实现流式代码生成展示
- 代码解析与保存：将 AI 生成的代码解析并保存到本地文件系统

核心类：
- [AiCodeGeneratorService](src/main/java/com/ai/healthaicode/ai/AiCodeGeneratorService.java)：AI 代码生成服务接口
- [AiCodeGeneratorFacade](src/main/java/com/ai/healthaicode/core/AiCodeGeneratorFacade.java)：AI 代码生成外观类
- [CodeParserExecutor](src/main/java/com/ai/healthaicode/core/parser/CodeParserExecutor.java)：代码解析执行器
- [CodeFileSaverExecutor](src/main/java/com/ai/healthaicode/core/saver/CodeFileSaverExecutor.java)：代码文件保存执行器

#### 4. 应用部署模块

应用部署模块负责将生成的代码部署为可访问的 Web 应用：

- 部署标识生成：为每个应用生成唯一的部署标识
- 文件复制：将生成的代码复制到部署目录
- URL 生成：生成可访问的应用 URL

### 核心业务流程

#### AI代码生成流程

1. 用户在前端输入需求描述
2. 前端通过 SSE 方式调用 [/app/chat/gen/code](src/main/java/com/ai/healthaicode/controller/AppController.java#L35-L54) 接口
3. 后端验证用户权限和参数
4. 调用 [AiCodeGeneratorFacade](src/main/java/com/ai/healthaicode/core/AiCodeGeneratorFacade.java) 生成代码
5. 根据应用类型选择相应的 AI 提示词模板
6. 调用 AI 模型生成代码（支持流式响应）
7. 实时将代码片段返回给前端展示
8. 全部生成完成后，解析并保存代码到本地文件系统

#### 应用部署流程

1. 用户点击部署按钮，调用 [/app/deploy](src/main/java/com/ai/healthaicode/controller/AppController.java#L294-L318) 接口
2. 后端验证用户权限
3. 检查应用代码是否存在
4. 生成或复用部署标识（deployKey）
5. 将代码从生成目录复制到部署目录
6. 更新应用的部署信息
7. 返回可访问的 URL

### 数据库设计

系统使用 MySQL 作为主数据库，包含以下核心表：

- [user](src/main/java/com/ai/healthaicode/model/entity/User.java)：用户表
- [app](src/main/java/com/ai/healthaicode/model/entity/App.java)：应用表
- [chat_history](src/main/java/com/ai/healthaicode/model/entity/ChatHistory.java)：聊天历史表

### 权限设计

系统采用基于角色的访问控制（RBAC）：

- USER_ROLE：普通用户，可以创建和管理自己的应用
- ADMIN_ROLE：管理员，可以管理所有用户和应用

权限控制通过 [@AuthCheck](src/main/java/com/ai/healthaicode/annotation/AuthCheck.java) 注解和 [AuthInterceptor](src/main/java/com/ai/healthaicode/aop/AuthInterceptor.java) AOP 拦截器实现。

### 配置说明

主要配置文件：[application.yml](src/main/resources/application.yml)

关键配置项：
- 数据库连接配置
- Redis 配置
- AI 模型配置（DeepSeek、阿里云等）
- 文件存储路径配置
- API 文档配置

## 部署说明

### 环境要求

- JDK 21
- MySQL 8.0
- Redis 6.0+
- Maven 3.x

### 部署步骤

1. 创建数据库并执行初始化脚本
2. 修改 [application.yml](src/main/resources/application.yml) 中的数据库、Redis等配置
3. 配置 AI 模型的 API Key
4. 构建项目：`mvn clean package`
5. 运行项目：`java -jar target/health-ai-code-backend.jar`

### 目录结构说明

- 生成的代码默认保存在项目目录下的 `tmp/code_output` 目录
- 部署的应用默认保存在项目目录下的 `tmp/code_deploy` 目录