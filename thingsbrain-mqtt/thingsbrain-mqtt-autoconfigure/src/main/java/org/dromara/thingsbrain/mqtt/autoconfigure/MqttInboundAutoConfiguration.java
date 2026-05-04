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
import org.dromara.dante.message.autoconfigure.mqtt.MqttProperties;
import org.dromara.thingsbrain.mqtt.autoconfigure.integration.MqttMessageHandler;
import org.dromara.thingsbrain.mqtt.inbound.config.MqttInboundConfiguration;
import org.dromara.thingsbrain.mqtt.inbound.dispatcher.MqttInboundMessageDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/3 17:52
 */
@AutoConfiguration(after = {MqttAutoConfiguration.class, MqttIntegrationAutoConfiguration.class})
@Import({
        MqttInboundConfiguration.class,
})
public class MqttInboundAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttInboundAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[ThingsBrain] |- Auto [Mqtt Inbound] Configure.");
    }

    @Bean
    public IntegrationFlow mqttThingsBrainInboundFlow(
            @Qualifier("mqttThingsBrainInbound") Mqttv5PahoMessageDrivenChannelAdapter mqttThingsBrainInbound,
            MqttProperties mqttProperties,
            MqttInboundMessageDispatcher mqttInboundMessageDispatcher) {
        return IntegrationFlow.from(mqttThingsBrainInbound)
                .handle(new MqttMessageHandler(mqttProperties, mqttInboundMessageDispatcher))
                .get();
    }
}
