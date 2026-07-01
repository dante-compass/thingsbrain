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

package cn.herodotus.thingsbrain.mqtt.inbound.config;

import cn.herodotus.dante.message.commons.definition.strategy.MessageSendingEventManager;
import cn.herodotus.thingsbrain.link.commons.definition.DeviceShadowManager;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttOutboundMessagePublisher;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.handler.ExtInboundMessageHandler;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.handler.OtaInboundMessageHandler;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.handler.SysInboundMessageHandler;
import cn.herodotus.thingsbrain.mqtt.inbound.dispatcher.*;
import cn.herodotus.thingsbrain.mqtt.inbound.factory.ExtMessageHandlerFactory;
import cn.herodotus.thingsbrain.mqtt.inbound.factory.OtaMessageHandlerFactory;
import cn.herodotus.thingsbrain.mqtt.inbound.factory.SysMessageHandlerFactory;
import cn.herodotus.thingsbrain.mqtt.inbound.processor.InboundResponseMessageProcessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * <p>Description: Mqtt 入站消息模块配置 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/1 22:15
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {
        "cn.herodotus.thingsbrain.mqtt.inbound.handler",
})
public class MqttInboundConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttInboundConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Mqtt Inbound] Configure.");
    }

    @Bean
    public SysMessageHandlerFactory sysMessageHandlerFactory(Map<String, SysInboundMessageHandler> handlers) {
        SysMessageHandlerFactory factory = new SysMessageHandlerFactory(handlers);
        log.trace("[ThingsBrain] |- Bean [Sys Message Handler Factory] Configure.");
        return factory;
    }

    @Bean
    public ExtMessageHandlerFactory extMessageHandlerFactory(Map<String, ExtInboundMessageHandler> handlers) {
        ExtMessageHandlerFactory factory = new ExtMessageHandlerFactory(handlers);
        log.trace("[ThingsBrain] |- Bean [Ext Message Handler Factory] Configure.");
        return factory;
    }

    @Bean
    public OtaMessageHandlerFactory otaMessageHandlerFactory(Map<String, OtaInboundMessageHandler> handlers) {
        OtaMessageHandlerFactory factory = new OtaMessageHandlerFactory(handlers);
        log.trace("[ThingsBrain] |- Bean [Ota Message Handler Factory] Configure.");
        return factory;
    }

    @Bean
    public InboundResponseMessageProcessor inboundResponseMessageProcessor(MessageSendingEventManager messageSendingEventManager) {
        InboundResponseMessageProcessor handler = new InboundResponseMessageProcessor(messageSendingEventManager);
        log.trace("[ThingsBrain] |- Bean [Inbound Message Reply Processor] Configure.");
        return handler;
    }

    @Bean
    public MqttInboundMessageDispatcher mqttInboundMessageDispatcher(
            SysMessageHandlerFactory sysMessageHandlerFactory,
            ExtMessageHandlerFactory extMessageHandlerFactory,
            OtaMessageHandlerFactory otaMessageHandlerFactory,
            DeviceShadowManager deviceShadowManager,
            InboundResponseMessageProcessor inboundResponseMessageProcessor,
            MqttOutboundMessagePublisher mqttOutboundMessagePublisher) {

        SysInboundMessageDispatcher sysInboundMessageDispatcher = new SysInboundMessageDispatcher(sysMessageHandlerFactory, inboundResponseMessageProcessor, mqttOutboundMessagePublisher);
        ExtInboundMessageDispatcher extInboundMessageDispatcher = new ExtInboundMessageDispatcher(extMessageHandlerFactory, inboundResponseMessageProcessor, mqttOutboundMessagePublisher);
        OtaInboundMessageDispatcher otaInboundMessageDispatcher = new OtaInboundMessageDispatcher(otaMessageHandlerFactory);
        ShadowInboundMessageDispatcher shadowInboundMessageDispatcher = new ShadowInboundMessageDispatcher(deviceShadowManager, mqttOutboundMessagePublisher);
        MqttInboundMessageDispatcher dispatcher = new MqttInboundMessageDispatcher(extInboundMessageDispatcher, otaInboundMessageDispatcher, shadowInboundMessageDispatcher, sysInboundMessageDispatcher);
        log.trace("[ThingsBrain] |- Bean [Shadow Inbound Message Listener] Configure.");
        return dispatcher;
    }
}
