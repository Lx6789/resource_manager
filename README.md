# 企业级媒体资源管理平台

[![JDK](https://img.shields.io/badge/JDK-17-green.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-3.5.5-blue.svg)](https://baomidou.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 📖 项目简介

**企业级媒体资源管理平台** 是一套面向连锁门店的资源管理与分发系统，旨在解决总部与门店之间图片、视频等媒体资源的高效传输与管控问题。系统支持总部集中制作素材、按需创建分发任务、可视化审批流程、门店按需同步，并提供完整的 RBAC 权限控制与操作审计能力。

### 核心业务场景

- 🏢 **总部市场部** 统一制作海报、价签图片、促销视频等素材
- 📋 **市场经理** 创建资源分发任务，指定目标门店，提交审批
- ✅ **总部管理员** 在线审批分发任务，审批通过后自动同步
- 🏪 **门店终端** 接收已审批的资源，实时查看同步状态
- 🔐 **权限隔离** 门店运营仅可见本门店数据，管理员可查看全局

---

## 🛠️ 技术栈

| 层级 | 技术 | 版本 | 说明 |
|------|------|------|------|
| **核心框架** | Spring Boot | 2.7.18 | 主框架，提供 IoC/AOP/自动配置 |
| **JDK** | Java | 17 | LTS 长期支持版本 |
| **持久层** | MyBatis-Plus | 3.5.5 | ORM 增强，自带分页、条件构造器、代码生成器 |
| **数据库** | MySQL | 8.0 | 业务数据存储 |
| **缓存** | Redis | 7.x | JWT 黑名单、热点数据缓存 |
| **对象存储** | MinIO | 最新版 | 文件存储，支持分片上传/断点续传 |
| **安全框架** | Spring Security + JWT | 0.12.5 | 认证与授权，无状态会话管理 |
| **工作流引擎** | Flowable | 6.8.0 | 可视化审批流程 |
| **API 文档** | SpringDoc OpenAPI | 1.7.0 | Swagger 3.0 替代方案 |
| **工具库** | Hutool | 5.8.25 | Java 工具集，简化文件/加密/HTTP 操作 |
| **简化开发** | Lombok | 1.18.x | 消除模板代码 |
| **模板引擎** | Freemarker | 2.3.32 | 代码生成器模板引擎 |
| **容器化** | Docker + Docker Compose | - | 一键编排开发环境 |