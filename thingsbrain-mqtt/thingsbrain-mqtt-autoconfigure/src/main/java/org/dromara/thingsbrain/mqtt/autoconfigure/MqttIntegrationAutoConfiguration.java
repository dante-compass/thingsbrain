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
import org.dromara.dante.core.utils.ListUtils;
import org.dromara.dante.message.autoconfigure.emqx.IntegrationEmqxAutoConfiguration;
import org.dromara.dante.message.commons.constant.Channels;
import org.dromara.thingsbrain.mqtt.autoconfigure.integration.MqttSubscribeTopicAppenderListener;
import org.dromara.thingsbrain.mqtt.autoconfigure.integration.MqttTopicProperties;
import org.eclipse.paho.mqttv5.client.IMqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.ClientManager;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/3 17:36
 */
@AutoConfiguration(after = {IntegrationEmqxAutoConfiguration.class})
@EnableConfigurationProperties(MqttTopicProperties.class)
public class MqttIntegrationAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttIntegrationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[ThingsBrain] |- Auto [Mqtt Integration] Configure.");
    }

    @Bean(name = Channels.MQTT__THINGS_BRAIN_INBOUND_CHANNEL)
    public MessageChannel mqttThingsBrainInboundChannel() {
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean(name = "mqttThingsBrainInbound")
    public Mqttv5PahoMessageDrivenChannelAdapter mqttThingsBrainInbound(
            ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager,
            MqttTopicProperties mqttTopicProperties,
            @Qualifier(Channels.MQTT__THINGS_BRAIN_INBOUND_CHANNEL) MessageChannel mqttThingsBrainInboundChannel) {
        Mqttv5PahoMessageDrivenChannelAdapter adapter = new Mqttv5PahoMessageDrivenChannelAdapter(clientManager, ListUtils.toStringArray(mqttTopicProperties.getDefaultSubscribes()));
        adapter.setManualAcks(false);
        adapter.setOutputChannel(mqttThingsBrainInboundChannel);
        adapter.setErrorChannelName(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME);
        log.trace("[ThingsBrain] |- Bean [Things Brain Mqtt Message Driven Channel Adapter] Configure.");
        return adapter;
    }

    @Bean
    public MqttSubscribeTopicAppenderListener mqttSubscribeTopicAppenderListener(@Qualifier("mqttThingsBrainInbound") Mqttv5PahoMessageDrivenChannelAdapter mqttThingsBrainInbound) {
        MqttSubscribeTopicAppenderListener listener = new MqttSubscribeTopicAppenderListener(mqttThingsBrainInbound);
        log.trace("[ThingsBrain] |- Bean [Mqtt Subscribe Topic Appender] Configure.");
        return listener;
    }
}
