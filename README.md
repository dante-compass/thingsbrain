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