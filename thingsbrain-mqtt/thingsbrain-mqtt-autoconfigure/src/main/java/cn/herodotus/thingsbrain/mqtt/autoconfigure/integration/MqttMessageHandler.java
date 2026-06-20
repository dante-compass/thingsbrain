/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * ThingsBrain licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ThingsBrain 是 Dante Cloud 系统生态产品，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 ThingsBrain 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.thingsbrain.mqtt.autoconfigure.integration;

import cn.hutool.v7.core.text.StrUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import cn.herodotus.dante.core.domain.Result;
import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.dante.message.autoconfigure.mqtt.MqttProperties;
import cn.herodotus.dante.message.commons.domain.MqttMessage;
import cn.herodotus.dante.message.commons.event.MqttMessageSendingEvent;
import cn.herodotus.dante.spring.context.ServiceContextHolder;
import cn.herodotus.thingsbrain.mqtt.inbound.dispatcher.MqttInboundMessageDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>Description: ThingsBrain平台功能 Mqtt 入站消息统一处理器 </p>
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
