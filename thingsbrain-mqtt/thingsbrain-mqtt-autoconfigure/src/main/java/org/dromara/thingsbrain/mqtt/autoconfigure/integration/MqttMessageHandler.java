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

import cn.hutool.v7.core.text.StrUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.dante.core.domain.Result;
import org.dromara.dante.core.jackson.JacksonUtils;
import org.dromara.dante.message.autoconfigure.mqtt.MqttProperties;
import org.dromara.dante.message.commons.domain.MqttMessage;
import org.dromara.dante.message.commons.event.MqttMessageSendingEvent;
import org.dromara.dante.spring.context.ServiceContextHolder;
import org.dromara.thingsbrain.mqtt.inbound.dispatcher.MqttInboundMessageDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/3 17:55
 */
public class MqttMessageHandler extends AbstractMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(MqttMessageHandler.class);

    private final MqttProperties mqttProperties;
    private final MqttInboundMessageDispatcher mqttInboundMessageDispatcher;

    public MqttMessageHandler(MqttProperties mqttProperties, MqttInboundMessageDispatcher mqttInboundMessageDispatcher) {
        this.mqttProperties = mqttProperties;
        this.mqttInboundMessageDispatcher = mqttInboundMessageDispatcher;
    }

    @Override
    protected void handleMessageInternal(Message<?> message) {
        String topic = message.getHeaders().get(mqttProperties.getTopicHeader(), String.class);
        byte[] correlationData = message.getHeaders().get(MqttHeaders.CORRELATION_DATA, byte[].class);
        String responseTopic = message.getHeaders().get(MqttHeaders.RESPONSE_TOPIC, String.class);

        if (StringUtils.isBlank(topic)) {
            log.warn("[ThingsBrain] |- Cannot find topic in message header, use expression {}", mqttProperties.getTopicExpression());
            topic = mqttProperties.getTopicHeader();
        }

        log.debug("[ThingsBrain] |- LINK - [1] Receive the message from topic [{}]", topic);

        byte[] payload = getPayload(message.getPayload());

        if (ArrayUtils.isNotEmpty(payload)) {
            log.debug("[ThingsBrain] |- LINK - [2] Dispatch the message.");
            mqttInboundMessageDispatcher.process(topic, payload, responseTopic, correlationData);
        } else {
            log.error("[ThingsBrain] |- LINK - [2] Received empty payload from topic [{}]", topic);
            error(responseTopic, correlationData);
        }
    }

    private byte[] getPayload(Object payload) {
        if (ObjectUtils.isNotEmpty(payload) && payload instanceof byte[]) {
            return (byte[]) payload;
        }
        return null;
    }

    private void error(String responseTopic, byte[] correlationData) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setTopic(responseTopic);
        if (ArrayUtils.isNotEmpty(correlationData)) {
            mqttMessage.setCorrelationData(StrUtil.str(correlationData, StandardCharsets.UTF_8));
        }
        mqttMessage.setPayload(JacksonUtils.toJson(Result.failure("The payload cannot be empty", 406, Map.of())));
        mqttMessage.setQos(1);
        ServiceContextHolder.publishEvent(new MqttMessageSendingEvent(mqttMessage));
    }
}
