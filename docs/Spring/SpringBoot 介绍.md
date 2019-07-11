# SpringBoot 介绍

**Spring Boot 简化 Spring 应用程序开发**。从本质上来说，Spring Boot 就是 Spring，基于 **"习惯优于配置"** 理念使得项目快速运行起来。

所谓 "习惯优先配置" 理念即项目中存在大量的配置，此外还内置了一个习惯性的配置，让程序开发人员无需手动进行配置。

## 自动配置

针对很多 Spring 应用程序常见的应用功能，Spring Boot 能够自动提供相关配置。当开发人员在 pom.xml 文件汇中添加 starter 依赖后， maven 或者 gradle 会自动下载很多 jar 包到 classpath 中。

当 Spring Boot 检测到特定类的存在，就会针对这个应用做一定的配置，自动创建和织入需要 Spring Bean 到程序上下文中。@SpringBootApplication 开启 Spring 组件扫描和 Spring  Boot 的自动配置功能。

## 起步依赖

告诉 SpringBoot 需要什么功能，它就能引入需要的库，利用了传递依赖解析，把常用库聚合在一起，组成了几个特定功能而定制的依赖，不需要担心版本不兼容的问题。

## 命令行界面

这是 SpringBoot 的可选性质，借此你只需要能完成完整的应用程序，无需传统项目构建。



