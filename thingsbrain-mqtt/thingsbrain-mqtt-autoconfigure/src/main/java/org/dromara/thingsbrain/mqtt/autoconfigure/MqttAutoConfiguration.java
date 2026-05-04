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
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.thingsbrain.mqtt.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.dromara.thingsbrain.mqtt.autoconfigure.publisher.DefaultMqttMessagePublisher;
import org.dromara.thingsbrain.mqtt.commons.definition.MqttMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/1 15:26
 */
@AutoConfiguration
public class MqttAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[ThingsBrain] |- Auto [Mqtt] Configure.");
    }

    @Bean
    public MqttMessagePublisher mqttMessagePublisher() {
        DefaultMqttMessagePublisher mqttMessageManager = new DefaultMqttMessagePublisher();
        log.trace("[ThingsBrain] |- Bean [Default Mqtt Message Manager] Configure.");
        return mqttMessageManager;
    }
}
