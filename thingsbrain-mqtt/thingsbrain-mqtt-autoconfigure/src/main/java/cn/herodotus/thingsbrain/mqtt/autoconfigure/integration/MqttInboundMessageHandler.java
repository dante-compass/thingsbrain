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

import cn.herodotus.dante.core.domain.Result;
import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.dante.message.autoconfigure.mqtt.MqttProperties;
import cn.herodotus.dante.message.commons.domain.MqttMessage;
import cn.herodotus.dante.message.commons.event.MqttMessageSendingEvent;
import cn.herodotus.dante.spring.context.ServiceContextHolder;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttMessageDuplicateInspector;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttMessageDetails;
import cn.herodotus.thingsbrain.mqtt.inbound.dispatcher.MqttInboundMessageDispatcher;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.Map;

/**
 * <p>Description: ThingsBrain平台功能 Mqtt 入站消息统一处理器 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/3 17:55
 */
public class MqttInboundMessageHandler implements MessageHandler {

    private static final Logger log = LoggerFactory.getLogger(MqttInboundMessageHandler.class);

    private final MqttMessageDuplicateInspector mqttMessageDuplicateInspector;
    private final MqttInboundMessageDispatcher mqttInboundMessageDispatcher;
    private final Converter<Message<?>, MqttMessageDetails> toDetailsConverter;

    public MqttInboundMessageHandler(MqttProperties mqttProperties, MqttMessageDuplicateInspector mqttMessageDuplicateInspector, MqttInboundMessageDispatcher mqttInboundMessageDispatcher) {
        this.mqttMessageDuplicateInspector = mqttMessageDuplicateInspector;
        this.mqttInboundMessageDispatcher = mqttInboundMessageDispatcher;
        this.toDetailsConverter = new MessageToMqttMessageDetailsConverter(mqttProperties);
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {

        MqttMessageDetails details = toDetailsConverter.convert(message);

        if (!details.isEmpty()) {
            log.debug("[ThingsMesh] |- LINK - [1] Receive the message from topic [{}]", details.getTopic());
            if (!mqttMessageDuplicateInspector.isDuplicate(details)) {
                mqttInboundMessageDispatcher.process(details);
                mqttMessageDuplicateInspector.record(details);
            } else {
                log.warn("[ThingsMesh] |- LINK - Ignore message [{}], because messageId in cache or message duplicate!", message);
            }
        } else {
            log.warn("[ThingsMesh] |- LINK - Message [{}] payload is incorrect!", message);
            error(details);
        }
    }

    private record MessageToMqttMessageDetailsConverter(
            MqttProperties mqttProperties) implements Converter<Message<?>, MqttMessageDetails> {

        @Override
        public MqttMessageDetails convert(Message<?> source) {

            String topic = source.getHeaders().get(mqttProperties.getTopicHeader(), String.class);
            String payload = getPayload(source.getPayload());
            Integer qos = source.getHeaders().get(MqttHeaders.QOS, Integer.class);
            byte[] correlationData = source.getHeaders().get(MqttHeaders.CORRELATION_DATA, byte[].class);
            String responseTopic = source.getHeaders().get(MqttHeaders.RESPONSE_TOPIC, String.class);
            String id = source.getHeaders().get(MqttHeaders.ID, String.class);
            Boolean duplicate = source.getHeaders().get(MqttHeaders.DUPLICATE, Boolean.class);

            return MqttMessageDetails.with(topic, payload)
                    .qos(qos)
                    .responseTopic(responseTopic)
                    .correlationData(correlationData)
                    .messageId(id)
                    .duplicate(duplicate)
                    .build();
        }

        private String getPayload(Object payload) {
            if (ObjectUtils.isNotEmpty(payload) && payload instanceof String) {
                return (String) payload;
            }
            return null;
        }
    }


    private void error(MqttMessageDetails details) {
        if (StringUtils.isNotBlank(details.getResponseTopic())) {
            String payload = JacksonUtils.toJson(Result.failure("The payload cannot be empty", 406, Map.of()));

            MqttMessage mqttMessage = MqttMessage.with(details.getResponseTopic(), payload)
                    .qos(details.getQos())
                    .correlationData(details.getCorrelationData())
                    .build();

            ServiceContextHolder.publishEvent(new MqttMessageSendingEvent(mqttMessage));
        }
    }
}
