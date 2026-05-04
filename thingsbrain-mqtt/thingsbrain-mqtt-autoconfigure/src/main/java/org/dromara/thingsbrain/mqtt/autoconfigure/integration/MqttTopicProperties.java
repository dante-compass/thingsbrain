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

package org.dromara.thingsbrain.mqtt.autoconfigure.integration;

import org.dromara.dante.message.commons.constant.MessageConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p>Description: Mqtt Topic 配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/9/30 17:13
 */
@ConfigurationProperties(prefix = MessageConstants.PROPERTY_MQTT_TOPIC)
public class MqttTopicProperties {
    /**
     * 默认的 Mqtt 主题
     */
    private List<String> defaultSubscribes = List.of("herodotus.thingsbrain.test");

    public List<String> getDefaultSubscribes() {
        return defaultSubscribes;
    }

    public void setDefaultSubscribes(List<String> defaultSubscribes) {
        this.defaultSubscribes = defaultSubscribes;
    }
}
