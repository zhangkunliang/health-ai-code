# health-ai-code

## 项目简介

health-ai-code 是一个结合 AI 与健康领域的智能代码生成平台。用户可以通过自然语言描述来生成健康相关的应用程序，平台会根据用户需求自动生成相应的代码，并支持一键部署。

## 项目结构

```
health-ai-code/
├── health-ai-code-fronted/      # 前端项目
├── health-ai-code-microservice/ # 微服务项目
│   ├── ai/                      # AI服务模块
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/ai/healthaicode/ai/
│   │   │       └── resources/
│   │   └── pom.xml
│   ├── app/                     # 应用服务模块
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/ai/healthaicode/app/
│   │   │       └── resources/
│   │   └── pom.xml
│   ├── client/                  # 客户端模块
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/ai/healthaicode/client/
│   │   │       └── resources/
│   │   └── pom.xml
│   ├── common/                  # 公共模块
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/ai/healthaicode/common/
│   │   │       └── resources/
│   │   └── pom.xml
│   ├── gateway/                 # 网关模块
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/ai/healthaicode/gateway/
│   │   │       └── resources/
│   │   └── pom.xml
│   ├── user/                    # 用户服务模块
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/ai/healthaicode/user/
│   │   │       └── resources/
│   │   └── pom.xml
│   └── pom.xml                  # 微服务父工程配置
├── src/                         # 后端项目（单体架构）
│   └── main/
│       ├── java/
│       │   └── com/ai/healthaicode/
│       └── resources/
└── pom.xml                      # 项目父工程配置
```

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

应用管理模块负责用户创建、管理和使用 AI 应用，包含完整的用户端和管理员端功能：

##### 用户端功能：
- 创建应用：用户可以通过自然语言初始化应用
- 修改应用：支持修改自己应用的名称等基本信息
- 删除应用：支持删除自己的应用
- 查看应用详情：支持查看自己应用的详细信息
- 分页查询个人应用列表：支持分页查询自己的应用列表
- 分页查询精选应用列表：支持分页查询精选应用列表
- 应用部署：支持将应用部署为可访问的Web应用

##### 管理员端功能：
- 删除任意应用：管理员可以删除任何用户的应用
- 更新任意应用：管理员可以更新任何用户的应用信息（名称、封面、优先级等）
- 分页查询应用列表：管理员可以分页查询所有应用列表
- 查看应用详情：管理员可以查看任何应用的详细信息

主要实体类：[App](src/main/java/com/ai/healthaicode/model/entity/App.java)
主要视图对象：[AppVO](src/main/java/com/ai/healthaicode/model/vo/AppVO.java)
主要数据传输对象：
- [AppAddRequest](src/main/java/com/ai/healthaicode/model/dto/app/AppAddRequest.java)
- [AppUpdateRequest](src/main/java/com/ai/healthaicode/model/dto/app/AppUpdateRequest.java)
- [AppAdminUpdateRequest](src/main/java/com/ai/healthaicode/model/dto/app/AppAdminUpdateRequest.java)
- [AppQueryRequest](src/main/java/com/ai/healthaicode/model/dto/app/AppQueryRequest.java)
- [AppDeployRequest](src/main/java/com/ai/healthaicode/model/dto/app/AppDeployRequest.java)

主要控制器：[AppController](src/main/java/com/ai/healthaicode/controller/AppController.java)
主要服务接口：[AppService](src/main/java/com/ai/healthaicode/service/AppService.java)
主要服务实现：[AppServiceImpl](src/main/java/com/ai/healthaicode/service/impl/AppServiceImpl.java)
主要数据访问接口：[AppMapper](src/main/java/com/ai/healthaicode/mapper/AppMapper.java)

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

### 部署说明

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

### 后端目录结构说明

```
src/main/java/com/ai/healthaicode/
├── HealthAiCodeApplication.java  # Spring Boot启动类
├── ai/                           # AI相关模块
│   ├── AiCodeGeneratorService.java # AI代码生成服务接口
│   ├── AiCodeGeneratorServiceFactory.java # AI服务工厂
│   ├── guardrail/                # AI安全防护模块
│   ├── model/                    # AI模型相关类
│   └── tools/                    # AI工具类
├── annotation/                   # 自定义注解
├── aop/                          # AOP切面编程
├── common/                       # 通用类
├── config/                       # 配置类
├── constant/                     # 常量定义
├── controller/                   # 控制器层
├── core/                         # 核心业务逻辑
│   ├── AiCodeGeneratorFacade.java # AI代码生成门面类
│   ├── CodeParser.java           # 代码解析器接口
│   ├── CodeFileSaver.java        # 代码文件保存器接口
│   ├── builder/                  # 项目构建器
│   ├── handler/                  # 流处理模块
│   ├── parser/                   # 代码解析模块
│   └── saver/                    # 代码保存模块
├── exception/                    # 异常处理
├── generator/                    # 代码生成器
├── langgraph4j/                  # LangGraph4j工作流实现
├── manager/                      # 管理类
├── mapper/                       # 数据访问层
├── model/                        # 数据模型
│   ├── dto/                      # 数据传输对象
│   ├── entity/                   # 实体类
│   ├── enums/                    # 枚举类
│   └── vo/                       # 视图对象
├── ratelimter/                   # 限流模块
├── service/                      # 业务逻辑层
└── utils/                        # 工具类
```

### 前端目录结构说明

```
health-ai-code-fronted/src/
├── App.vue                       # 根组件
├── access.ts                     # 权限控制
├── api/                          # API接口封装
│   ├── appController.ts
│   ├── chatController.ts
│   ├── fileController.ts
│   ├── index.ts
│   ├── userController.ts
│   └── typings.d.ts
├── assets/                       # 静态资源
├── components/                   # 公共组件
├── config/                       # 配置文件
├── layouts/                      # 布局组件
├── main.ts                       # 入口文件
├── pages/                        # 页面组件
│   ├── Admin/                    # 管理员页面
│   │   ├── AppAdminPage.vue      # 应用管理页面
│   │   └── UserAdminPage.vue     # 用户管理页面
│   ├── App/                      # 应用相关页面
│   │   ├── AppDetailPage.vue     # 应用详情页面
│   │   ├── AppDoPage.vue         # 应用操作页面
│   │   └── AppPage.vue           # 应用列表页面
│   └── User/                     # 用户相关页面
│       ├── ChatPage.vue          # 聊天页面
│       ├── CreateAppPage.vue     # 创建应用页面
│       └── UserInfoPage.vue      # 用户信息页面
├── request.ts                    # 请求封装
├── router/                       # 路由配置
├── stores/                       # 状态管理
└── utils/                        # 工具类
```

### 代码生成和部署目录说明

- 生成的代码默认保存在项目目录下的 `tmp/code_output` 目录
- 部署的应用默认保存在项目目录下的 `tmp/code_deploy` 目录