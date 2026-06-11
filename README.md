<p align="center"><img src="./readme/new-logo.png" height="300" width="400" alt="logo"/></p>
<h2 align="center">简洁优雅 · 稳定高效 | 宁静致远 · 精益求精 </h2>
<p align="center">Dante Cloud 生态产品 -- ThingsBrain 物联网平台</p>

---

<p align="center">
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://img.shields.io/badge/Spring%20Boot-4.1.0-blue.svg?logo=springboot" alt="Spring Boot 4.1.0"></a>
    <a href="https://spring.io/projects/spring-cloud" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud-2025.1.2-blue.svg?logo=springboot" alt="Spring Cloud 2025.1.2"></a>
    <a href="https://github.com/alibaba/spring-cloud-alibaba" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2025.1.0.0-blue.svg?logo=alibabadotcom" alt="Spring Cloud Alibaba 2025.1.0.0"></a>
    <a href="https://github.com/Tencent/spring-cloud-tencent" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Tencent-2.1.2.0--2025.0.2-blue.svg?logo=qq" alt="Spring Cloud Tencent 2.1.2.0-2025.0.2"></a>
    <a href="https://nacos.io/docs/latest/overview/" target="_blank"><img src="https://img.shields.io/badge/Nacos-3.2.2-brightgreen.svg?logo=alibabadotcom" alt="Nacos 3.2.2"></a>
</p>
<p align="center">
    <a href="https://my.oschina.net/pointerv" target="_blank"><img src="https://img.shields.io/badge/Author-%E7%A0%81%E5%8C%A0%E5%90%9B-orange" alt="码匠君"></a>
    <a href="./LICENSE"><img src="https://img.shields.io/badge/License-Apache--2.0-blue.svg?logo=apache" alt="License Apache 2.0"></a>
    <a href="https://bell-sw.com/pages/downloads/#downloads" target="_blank"><img src="https://img.shields.io/badge/JDK-25%2B-green.svg?logo=openjdk" alt="Java 25"></a>
    <a href="https://github.com/dante-compass/thingsbrain" target="_blank"><img src="https://img.shields.io/badge/Version-4.0.7.1-red.svg?logo=spring" alt="ThingsBrain 4.0.7.1"></a>
    <a href="https://github.com/dromara/dante-cloud" target="_blank"><img src="https://img.shields.io/badge/Dante%20Cloud-4.0.7.1-red.svg?logo=spring" alt="Version 4.0.7.1"></a>
    <a href="https://github.com/dante-compass/dante-engine" target="_blank"><img src="https://img.shields.io/badge/Dante%20Engine-4.0.7.1-red.svg?logo=spring" alt="Dante Engine 4.0.7.1"></a>
    <a href="https://github.com/dante-compass/dante-cloud-ui" target="_blank"><img src="https://img.shields.io/badge/Dante%20Cloud%20UI-4.0.7.1-blue.svg?logo=quasar&logoColor=%23050A14" alt="Dante Cloud UI 4.0.7.1"></a>
    <a href="https://github.com/dante-compass/herodotus-cloud-ui-vuetify" target="_blank"><img src="https://img.shields.io/badge/Dante%20Cloud%20UI(New)-4.0.7.1-blue.svg?logo=vuetify&logoColor=%231867C0" alt="Dante Cloud UI(New) 4.0.7.1"></a>
    <a href="https://github.com/dante-compass/thingsbrain"><img src="https://img.shields.io/github/stars/dromara/dante-cloud.svg?label=Github%20Stars" alt="Github star"></a>
    <a href="https://github.com/dante-compass/thingsbrain"><img src="https://img.shields.io/github/forks/dromara/dante-cloud.svg?label=Github%20Forks" alt="Github fork"></a>
    <a href="https://gitee.com/dante-compass/thingsbrain"><img src="https://gitee.com/dromara/dante-cloud/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/dante-compass/thingsbrain"><img src="https://gitee.com/dromara/dante-cloud/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
    <a href="https://www.herodotus.cn"><img src="https://visitor-badge.laobi.icu/badge?page_id=dante-cloud&title=Total%20Visits" alt="Total Visits"></a>
</p>
<p align="center">
    <a href="https://github.com/dromara/dante-cloud">Github 仓库</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/dromara/dante-cloud">Gitee 仓库</a> &nbsp; | &nbsp;
    <a href="https://www.herodotus.cn">在线文档</a>
</p>

<h1 align="center"> 支持本项目除了 Fork、Pull 和 Download Zip，还可以点右上角 "Star"！</h1>


# 开发中，敬请期待！



## 五、工程结构

```shell
herodotus-thingsbrain
├── thingsbrain-dependencies -- ThingsBrain Bom 定义, 统一管理工程模块
├── thingsbrain-kernel -- ThingsBrain 核心定义相关模块
├    ├── thingsbrain-kernel-commons -- 核心定义通用代码模块
├    ├── thingsbrain-kernel-link -- 自定义 Link 协议核心定义代码模块
├    └── thingsbrain-kernel-tsl -- 物模型核心定义代码模块
├── thingsbrain-link -- 自定义 Link 协议相关模块
├    ├── thingsbrain-link-autoconfigure -- 自定义 Link 协议自动配置模块
├    ├── thingsbrain-link-commons -- 自定义 Link 协议通用代码模块
├    ├── thingsbrain-link-manager -- 自定义 Link 协议管理器模块
├    └── thingsbrain-link-commons -- 自定义 Link 协议上报数据存储模块(时序数据)
├── thingsbrain-mqtt -- Mqtt 业务逻辑相关模块
├    ├── thingsbrain-mqtt-autoconfigure -- Mqtt 业务逻辑自动配置模块
├    ├── thingsbrain-mqtt-commons -- Mqtt 业务逻辑通用代码模块
├    ├── thingsbrain-mqtt-inbound -- Mqtt 入站数据业务逻辑实现代码模块
├    └── thingsbrain-mqtt-outbound -- Mqtt 出站数据业务逻辑实现代码模块
├── thingsbrain-persistence -- 数据持久化相关模块
├    ├── thingsbrain-persistence-autoconfigure -- 数据持久化自动配置模块
├    ├── thingsbrain-persistence-commons -- 数据持久化通用代码模块
├    ├── thingsbrain-persistence-jpa -- 以 JPA 作为核心业务数据持久化层实现模块
├    └── thingsbrain-persistence-mongodb -- 以 MongoDB 作为核心业务数据持久化层实现模块
├── thingsbrain-platform -- 平台功能相关模块
├    ├── thingsbrain-monolith-application -- ThingsBrain 物联网平台应用(单体版)
├    ├── thingsbrain-platform-authentication -- 设备认证功能逻辑模块
├    ├── thingsbrain-platform-autoconfigure -- 平台功能自动配置模块
├    ├── thingsbrain-platform-commons --  平台功能通用代码模块
└──  └── thingsbrain-platform-rest -- 平台功能 REST 接口模块
```

# [十]、关注我

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