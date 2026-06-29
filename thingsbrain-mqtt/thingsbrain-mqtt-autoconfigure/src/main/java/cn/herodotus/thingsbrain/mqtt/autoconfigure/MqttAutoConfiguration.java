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

package cn.herodotus.thingsbrain.mqtt.autoconfigure;

import cn.herodotus.dante.core.utils.ListUtils;
import cn.herodotus.dante.message.autoconfigure.emqx.IntegrationEmqxAutoConfiguration;
import cn.herodotus.dante.message.autoconfigure.mqtt.MqttProperties;
import cn.herodotus.dante.message.commons.constant.Channels;
import cn.herodotus.thingsbrain.mqtt.autoconfigure.integration.MqttInboundMessageHandler;
import cn.herodotus.thingsbrain.mqtt.autoconfigure.integration.MqttSubscribeTopicAppenderListener;
import cn.herodotus.thingsbrain.mqtt.autoconfigure.integration.MqttTopicProperties;
import cn.herodotus.thingsbrain.mqtt.autoconfigure.publisher.DefaultMqttMessagePublisher;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttMessagePublisher;
import cn.herodotus.thingsbrain.mqtt.inbound.config.MqttInboundConfiguration;
import cn.herodotus.thingsbrain.mqtt.inbound.dispatcher.MqttInboundMessageDispatcher;
import cn.herodotus.thingsbrain.mqtt.outbound.config.MqttOutboundConfiguration;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.mqttv5.client.IMqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.ClientManager;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * <p>Description: ThingsBrain 平台 Mqtt 相关功能自动配置 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/1 15:26
 */
@AutoConfiguration(after = {IntegrationEmqxAutoConfiguration.class})
@EnableConfigurationProperties({MqttTopicProperties.class})
@Import({
        MqttInboundConfiguration.class,
        MqttOutboundConfiguration.class
})
public class MqttAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[ThingsBrain] |- Auto [Mqtt] Configure.");
    }

    /**
     * Mqtt 默认消息入站消息转 Event 通道。通过该种方式保证通道的唯一性。
     *
     * @return Mqtt 默认消息入站消息转 Event 通道 {@link MessageChannel}
     */
    @Bean(name = Channels.MQTT__THINGSMESH_INBOUND_CHANNEL)
    public MessageChannel mqttThingsMeshInboundChannel() {
        return MessageChannels.direct().getObject();
    }

    @Bean(name = "mqttThingsMeshInbound")
    public Mqttv5PahoMessageDrivenChannelAdapter mqttThingsMeshInbound(
            ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager,
            MqttTopicProperties mqttTopicProperties,
            @Qualifier(Channels.MQTT__THINGSMESH_INBOUND_CHANNEL) MessageChannel mqttThingsMeshInboundChannel) {
        Mqttv5PahoMessageDrivenChannelAdapter adapter = new Mqttv5PahoMessageDrivenChannelAdapter(clientManager, ListUtils.toStringArray(mqttTopicProperties.getDefaultSubscribes()));
        adapter.setManualAcks(false);
        adapter.setOutputChannel(mqttThingsMeshInboundChannel);
        adapter.setErrorChannelName(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME);
        log.trace("[ThingsMesh] |- Bean [Things Mesh Mqtt Message Driven Channel Adapter] Configure.");
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = Channels.MQTT__THINGSMESH_INBOUND_CHANNEL)
    public MessageHandler mqttThingsMeshInboundHandler(
            MqttProperties mqttProperties,
            MqttInboundMessageDispatcher mqttInboundMessageDispatcher) {
        return new MqttInboundMessageHandler(mqttProperties, mqttInboundMessageDispatcher);
    }

    @Bean
    public MqttSubscribeTopicAppenderListener mqttSubscribeTopicAppenderListener(@Qualifier("mqttThingsMeshInbound") Mqttv5PahoMessageDrivenChannelAdapter mqttThingsMeshInbound) {
        MqttSubscribeTopicAppenderListener listener = new MqttSubscribeTopicAppenderListener(mqttThingsMeshInbound);
        log.trace("[ThingsMesh] |- Bean [Mqtt Subscribe Topic Appender] Configure.");
        return listener;
    }

    @Bean
    public MqttMessagePublisher mqttMessagePublisher() {
        DefaultMqttMessagePublisher mqttMessageManager = new DefaultMqttMessagePublisher();
        log.trace("[ThingsMesh] |- Bean [Default Mqtt Message Manager] Configure.");
        return mqttMessageManager;
    }
}
