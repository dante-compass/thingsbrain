<p align="center"><img src="./readme/new-logo.png" height="300" width="400" alt="logo"/></p>
<h2 align="center">简洁优雅 · 稳定高效 | 宁静致远 · 精益求精 </h2>
<p align="center">Dante Cloud 生态产品 -- ThingsBrain 物联网平台</p>

---

<p align="center">
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://img.shields.io/badge/Spring%20Boot-4.1.1-blue.svg?logo=springboot" alt="Spring Boot 4.1.1"></a>
    <a href="https://spring.io/projects/spring-cloud" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud-2025.1.3-blue.svg?logo=springboot" alt="Spring Cloud 2025.1.3"></a>
    <a href="https://github.com/alibaba/spring-cloud-alibaba" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2025.1.0.0-blue.svg?logo=alibabadotcom" alt="Spring Cloud Alibaba 2025.1.0.0"></a>
    <a href="https://github.com/Tencent/spring-cloud-tencent" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Tencent-2.1.2.0--2025.0.2-blue.svg?logo=qq" alt="Spring Cloud Tencent 2.1.2.0-2025.0.2"></a>
    <a href="https://nacos.io/docs/latest/overview/" target="_blank"><img src="https://img.shields.io/badge/Nacos-3.2.3-brightgreen.svg?logo=alibabadotcom" alt="Nacos 3.2.3"></a>
</p>
<p align="center">
    <a href="https://my.oschina.net/pointerv" target="_blank"><img src="https://img.shields.io/badge/Author-%E7%A0%81%E5%8C%A0%E5%90%9B-orange" alt="码匠君"></a>
    <a href="./LICENSE"><img src="https://img.shields.io/badge/License-Apache--2.0-blue.svg?logo=apache" alt="License Apache 2.0"></a>
    <a href="https://bell-sw.com/pages/downloads/#downloads" target="_blank"><img src="https://img.shields.io/badge/JDK-25%2B-green.svg?logo=openjdk" alt="Java 25"></a>
    <a href="https://github.com/dante-compass/thingsbrain" target="_blank"><img src="https://img.shields.io/badge/Version-4.1.1.0-red.svg?logo=spring" alt="ThingsBrain 4.1.1.0"></a>
    <a href="https://github.com/dromara/dante-cloud" target="_blank"><img src="https://img.shields.io/badge/Dante%20Cloud-4.1.1.0-red.svg?logo=spring" alt="Version 4.1.1.0"></a>
    <a href="https://github.com/dante-compass/dante-engine" target="_blank"><img src="https://img.shields.io/badge/Dante%20Engine-4.1.1.0-red.svg?logo=spring" alt="Dante Engine 4.1.1.0"></a>
    <a href="https://github.com/dante-compass/dante-cloud-ui" target="_blank"><img src="https://img.shields.io/badge/Dante%20Cloud%20UI-4.1.1.0-blue.svg?logo=quasar&logoColor=%23050A14" alt="Dante Cloud UI 4.1.1.0"></a>
    <a href="https://github.com/dante-compass/herodotus-cloud-ui-vuetify" target="_blank"><img src="https://img.shields.io/badge/Dante%20Cloud%20UI(New)-4.1.1.0-blue.svg?logo=vuetify&logoColor=%231867C0" alt="Dante Cloud UI(New) 4.1.1.0"></a>
    <a href="https://github.com/dante-compass/thingsbrain"><img src="https://img.shields.io/github/stars/dante-compass/thingsbrain.svg?label=Github%20Stars" alt="Github star"></a>
    <a href="https://github.com/dante-compass/thingsbrain"><img src="https://img.shields.io/github/forks/dante-compass/thingsbrain.svg?label=Github%20Forks" alt="Github fork"></a>
    <a href="https://gitee.com/dante-compass/thingsbrain"><img src="https://gitee.com/dante-compass/thingsbrain/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/dante-compass/thingsbrain"><img src="https://gitee.com/dante-compass/thingsbrain/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
    <a href="https://www.herodotus.cn"><img src="https://visitor-badge.laobi.icu/badge?page_id=dante-cloud&title=Total%20Visits" alt="Total Visits"></a>
</p>
<p align="center">
    <a href="https://github.com/dromara/dante-cloud">Github 仓库</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/dromara/dante-cloud">Gitee 仓库</a> &nbsp; | &nbsp;
    <a href="https://www.herodotus.cn">在线文档</a>
</p>

# ThingsBrain 企业级物联网平台

ThingsBrain 是一款基于 Dante Cloud 企业级能力构建的高扩展性物联网平台。它兼容阿里云物联网平台标准，支持 MQTT 动态认证与 OAuth2.1 安全体系，具备多协议、多存储可插拔架构，为万物互联与 AI 融合提供高可靠、高安全的一站式物联网应用基座。

ThingsBrain 不仅是功能完备的物联网平台，更是 Dante Cloud 微服务平台全栈能力的最佳价值兑现应用场景，是 Dante Cloud 从理论到实践、从框架到产品的深入实践验证。

## 一、核心特性

### [一]源自 Dante Cloud 的卓越技术底座

ThingsBrain 基于 Dante Cloud 构建——国内首个支持阻塞式和响应式服务并行的微服务平台。Dante Cloud 采用领域驱动模型（DDD）设计思想，以「高质量代码、低安全漏洞」为核心，基于 Spring 生态全域开源技术，高度模块化和组件化设计。ThingsBrain 完整继承了这一技术优势：

**1. 架构灵活，平滑迁移**

Dante Cloud 独创的“一套代码、两种架构”能力——微服务架构和单体架构并非两套分离的代码，而是完全融合的一整套代码。基于此，ThingsBrain 同样支持单体架构和微服务架构两种运行模式，并可平滑迁移。企业在项目早期可以单体架构快速启动，随着业务规模扩大和并发需求提升，可无缝迁移至微服务架构，无需重构代码。

**2. 阻塞式与响应式并行**

Dante Cloud 是国内首个支持阻塞式服务和响应式服务并行的微服务平台。ThingsBrain 同样支持两种运行模式并存使用，可根据不同业务场景灵活选择——响应式模式能够更好地提升系统吞吐量，应对高并发物联网设备接入场景。

**3. OAuth2 认证与 MQTT 认证有机集成**

Dante Cloud 全面拥抱 OAuth2.1 协议，在此基础上，ThingsBrain 将 OAuth2 认证体系与 MQTT 认证深度整合。支持 OAuth2 客户端自动注册功能，支持物联网自定义 Product Key 属性。设备认证与用户认证统一，形成端到端的可信身份体系。

**4. 基于 Spring Integration 的统一消息体系**

Dante Cloud 新开源了基于 Spring Integration 的 MQTT 集成与 EMQX 集成，支持多种类型消息统一聚合发送。ThingsBrain 在此基础上，有机融合 MQTT 消息与 Spring Event 事件机制，构建了统一的消息体系，实现了物联网设备消息与平台内部事件的协同处理。

**5. 动态接口鉴权体系**

Dante Cloud 独创的方法级动态权限配置能力——无须使用传统的 `@PreAuthorize` 注解在代码中写死权限，完全通过后台管理实现方法级权限的灵活控制。ThingsBrain 继承这一能力，所有接口鉴权均可通过后台动态配置、实时生效。

**6. 企业级安全保障**

Dante Cloud 满足国家三级等保要求，支持接口国密数字信封加解密、防刷、高防 XSS 和 SQL 注入等系列安全体系。ThingsBrain 完整继承这一安全体系，为企业级物联网应用提供从传输加密到应用防护的全链路安全保障。

### [二]完备的物联网平台功能

ThingsBrain 支持物联网平台主流功能，全面对标阿里云物联网平台标准：

- **MQTT 动态注册**：支持设备动态认证与自动注册，简化设备接入流程
- **物模型**：支持设备物模型定义与管理，标准化设备数据描述
- **设备影子**：支持设备影子功能，存储设备上报状态与期望状态信息，实现设备状态云端同步
- **多协议可插拔**：具备多协议、多存储可插拔架构，灵活适配不同业务场景

### [三]Dante Cloud 的最佳实践验证

ThingsBrain 不仅是功能完整的物联网平台，更是 Dante Cloud 微服务平台在物联网领域的深度实践验证。它展示了如何基于 Dante Cloud 快速构建生产级物联网应用，为开发者学习和落地 Dante Cloud 提供了完整的参考实现。

## 二、核心价值

| 价值维度         | 说明                                                                               |
|------------------|------------------------------------------------------------------------------------|
| **降低开发成本** | 内置设备接入、物模型、设备影子等能力，减少重复造轮子，让团队专注于业务创新。       |
| **缩短上线周期** | 开箱即用，快速搭建物联网业务原型并平滑演进到生产环境。                             |
| **提升系统吞吐** | 响应式架构与统一消息体系结合，支撑海量设备并发连接与消息处理，提升资源利用率。     |
| **增强安全可控** | OAuth2 + MQTT 统一认证，方法级动态鉴权，权限可运营、可审计，满足企业安全合规要求。 |
| **保护既有投资** | 单体到微服务平滑迁移，兼容阿里云物联网平台，降低架构演进和生态切换风险。           |
| **降低运维难度** | 统一消息与认证体系，简化系统集成与日常运维，减少故障排查成本。                     |

## 三、应用场景

- 智能硬件与消费物联网
- 工业物联网与智能制造
- 智慧城市与楼宇自动化
- 车联网与物流追踪
- 能源监控与环境监测

## 四、开源协议

### 1. 协议声明

ThingsBrain 项目开源协议为 Apache License Version 2.0。可用于个人学习、毕设，允许商业使用，禁止二次开源。严禁搬运至 CSDN 下载等平台进行售卖。

### 2. 补充条款

使用时务必遵守以下补充条款。

- 不得将本软件应用于危害国家安全、荣誉和利益的行为，不能以任何形式用于非法为目的的行为。
- 在延伸的代码中（修改现有源代码衍生的代码中）需要带有原来代码中的协议、版权声明和其他原作者规定需要包含的说明（请尊重原作者的著作权，不要删除或修改文件中的Copyright和@author信息） ，更不要全局替换源代码中的 Dante Cloud、Dante Engine、ThingsBrain 或 码匠君 等字样，否则你将违反本协议条款承担责任。
- 您若套用本软件的一些代码或功能参考，请保留源文件中的版权和作者，需要在您的软件介绍明显位置 说明出处，举例：本软件基于 Dante Cloud 微服务架构 Dante Engine 或 ThingsBrain，并附带链接：<https://www.herodotus.cn>
- 任何基于本软件而产生的一切法律纠纷和责任，均与作者无关。
- 如果你对本软件有改进，希望可以贡献给我们，双向奔赴互相成就才是王道。
- 本项目已申请软件著作权，请尊重开源。

如果您确实需要删除作者或版权信息，需要争得作者同意及授权。或者在 [【使用公司及组织】](https://gitee.com/dromara/dante-cloud/issues/ICAOHG) 下进行登记，经作者整理登记信息形成表格后，可视为正式授权。

## 五、工程结构

```shell
thingsbrain
├── thingsbrain-application -- ThingsMesh 应用相关模块分组
├    ├── herodotus-cloud-iot-ability -- ThingsMesh 服务模块(微服务版)
├    └── thingsbrain-monolith-application -- ThingsMesh 物联网平台应用(单体版)
├── thingsbrain-dependencies -- ThingsBrain Bom 定义, 统一管理工程模块
├── thingsbrain-kernel -- ThingsBrain 核心定义相关模块分组
├    ├── thingsbrain-kernel-commons -- 核心定义通用代码模块
├    ├── thingsbrain-kernel-link -- 自定义 Link 协议核心定义代码模块
├    └── thingsbrain-kernel-tsl -- 物模型核心定义代码模块分组
├── thingsbrain-link -- 自定义 Link 协议相关模块分组
├    ├── thingsbrain-link-autoconfigure -- 自定义 Link 协议自动配置模块
├    ├── thingsbrain-link-commons -- 自定义 Link 协议通用代码模块
├    ├── thingsbrain-link-manager -- 自定义 Link 协议管理器模块
├    └── thingsbrain-link-commons -- 自定义 Link 协议上报数据存储模块(时序数据)
├── thingsbrain-mqtt -- Mqtt 业务逻辑相关模块分组
├    ├── thingsbrain-mqtt-autoconfigure -- Mqtt 业务逻辑自动配置模块
├    ├── thingsbrain-mqtt-commons -- Mqtt 业务逻辑通用代码模块
├    ├── thingsbrain-mqtt-inbound -- Mqtt 入站数据业务逻辑实现代码模块
├    └── thingsbrain-mqtt-outbound -- Mqtt 出站数据业务逻辑实现代码模块
├── thingsbrain-nosql -- NoSQL 非结构化数据存储模块分组
├    ├── thingsbrain-nosql-autoconfigure -- 非结构化数据存储自动配置模块
├    └── thingsbrain-nosql-influxdb3 -- InfluxDB3 封装模块
├── thingsbrain-persistence -- 数据持久化相关模块分组
├    ├── thingsbrain-persistence-autoconfigure -- 数据持久化自动配置模块
├    ├── thingsbrain-persistence-commons -- 数据持久化通用代码模块
├    ├── thingsbrain-persistence-jpa -- 以 JPA 作为核心业务数据持久化层实现模块
├    └── thingsbrain-persistence-mongodb -- 以 MongoDB 作为核心业务数据持久化层实现模块
├── thingsbrain-platform -- 平台功能相关模块分组
├    ├── thingsbrain-platform-authentication -- 设备认证功能逻辑模块
├    ├── thingsbrain-platform-autoconfigure -- 平台功能自动配置模块
└──  └── thingsbrain-platform-rest -- 平台功能 REST 接口模块
```

## 六、版本分支

### 1. 版本号说明

本系统版本号，分为四段。

- 第一段、第二段和第三段，与 Spring Boot 版本对应，根据采用的 Spring Boot 版本变更。例如，当前采用 Spring Boot 2.4.6 版本，那么就以
  2.4.6.X 开头
- 第四段，表示在当前 Spring Boot 版本下，系统功能维护及优化情况。

本系统未采用传统的、从 1.0.0 开始的版本号，主要基于以下两点考虑：一方面，方便了解对应的 Spring Boot 版本；另一方面，与 Dante Cloud 以及 Dante Engine 匹配对应，以减少不必要麻烦。

### 2. 分支说明

| 分支名称 | 对应 Spring 生态版本                     | 对应 JDK 版本 | 用途             | 现状                                                         |
|:--------:|------------------------------------------|---------------|------------------|--------------------------------------------------------------|
|  master  | Spring Boot 4.1 和 Spring Cloud 2025.1.3 | JDK 25        | 主要发布分支     | 推荐使用代码分支                                             |
| develop  | Spring Boot 4.1 和 Spring Cloud 2025.1.3 | JDK 25        | Development 分支 | 新功能、ISSUE 均以此分支作为开发，发布后会 PR 至 master 分支 |


## 七、关注我

<table align="center">
  <tr>
    <th align="center">
      <p>公众号：码匠君</p>
    </th>
  </tr>
  <tr>
    <td align="center">
      <img src="./readme/公众号.jpg" alt="公众号" height="200px">
    </td>
  </tr>
</table>