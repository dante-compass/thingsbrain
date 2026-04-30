/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsBrain.
 *
 * Herodotus ThingsBrain is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsBrain is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package org.dromara.thingsbrain.link.autoconfigure.initializer;

import org.apache.commons.collections4.CollectionUtils;
import org.dromara.thingsbrain.link.commons.definition.MqttAuthorizationManager;
import org.dromara.thingsbrain.persistence.commons.domain.MqttCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Optional;

/**
 * <p>Description: 系统 Mqtt 订阅主题发送器 </p>
 *
 * 在启动正常启动之后，获取到平台对应的必要的订阅主题，以 Event 的方式将订阅主题信息发送到 infrastructure-module-mqtt 模块中动态添加以实现主题的订阅
 *
 * 之所以要使用这种方式的原因：
 * 1. 实现系统订阅主题的动态管理，如果采用配置文件的方式配置，无法动态进行修改和管理
 * 2. 从模块依赖关系角度考虑，infrastructure-module-mqtt 模块并没有依赖 assemble 相关模块，相反 infrastructure-core 被 assemble-core 依赖，导致无法调用和数据层相关的代码
 * 3. 从模块解耦的角度考虑，infrastructure-module-mqtt 相对独立，仅有一个获取订阅主题的特殊需求，如果直接依赖数据层相关的模块，徒增耦合性无法提升独立性。
 *
 * 所以采取 Event 的方式，系统启动后，读取订阅数据，然后发送到 infrastructure-module-mqtt 模块中。
 *
 * 目前，还不支持数据库表变化后，同步变化。后续看功能需要，适时添加。
 *
 * @author : gengwei.zheng
 * @date : 2025/10/14 13:03
 */
public class MqttSubscribeTopicSender implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(MqttSubscribeTopicSender.class);

    private final MqttAuthorizationManager mqttAuthorizationManager;

    public MqttSubscribeTopicSender(MqttAuthorizationManager mqttAuthorizationManager) {
        this.mqttAuthorizationManager = mqttAuthorizationManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Optional<MqttCategory> optional = mqttAuthorizationManager.findSubscribeCategoryForPlatform();

        optional.ifPresent(mqttCategory -> {
            if (CollectionUtils.isNotEmpty(mqttCategory.getAuthorities())) {
                log.info("[ThingsBrain] |- Found [{}] subscribe topics.", mqttCategory.getAuthorities().size());
//                ServiceContextHolder.publishEvent(new MqttSubscribeTopicAppenderEvent(mqttCategory.getAuthorities()));
            }
        });
    }
}
