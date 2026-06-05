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

package org.dromara.thingsbrain.mqtt.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.dromara.dante.core.utils.ListUtils;
import org.dromara.dante.message.autoconfigure.emqx.IntegrationEmqxAutoConfiguration;
import org.dromara.dante.message.autoconfigure.mqtt.MqttProperties;
import org.dromara.dante.message.commons.constant.Channels;
import org.dromara.thingsbrain.mqtt.autoconfigure.integration.MqttMessageHandler;
import org.dromara.thingsbrain.mqtt.autoconfigure.integration.MqttSubscribeTopicAppenderListener;
import org.dromara.thingsbrain.mqtt.autoconfigure.integration.MqttTopicProperties;
import org.dromara.thingsbrain.mqtt.autoconfigure.publisher.DefaultMqttMessagePublisher;
import org.dromara.thingsbrain.mqtt.commons.definition.MqttMessagePublisher;
import org.dromara.thingsbrain.mqtt.inbound.config.MqttInboundConfiguration;
import org.dromara.thingsbrain.mqtt.inbound.dispatcher.MqttInboundMessageDispatcher;
import org.dromara.thingsbrain.mqtt.outbound.config.MqttOutboundConfiguration;
import org.eclipse.paho.mqttv5.client.IMqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.ClientManager;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;

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

    @Bean(name = "mqttThingsBrainInbound")
    public Mqttv5PahoMessageDrivenChannelAdapter mqttThingsBrainInbound(
            ClientManager<IMqttAsyncClient, MqttConnectionOptions> clientManager,
            MqttTopicProperties mqttTopicProperties) {
        Mqttv5PahoMessageDrivenChannelAdapter adapter = new Mqttv5PahoMessageDrivenChannelAdapter(clientManager, ListUtils.toStringArray(mqttTopicProperties.getDefaultSubscribes()));
        adapter.setManualAcks(false);
        adapter.setOutputChannel(MessageChannels.publishSubscribe(Channels.MQTT__THINGS_BRAIN_INBOUND_CHANNEL).getObject());
        adapter.setErrorChannelName(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME);
        log.trace("[ThingsBrain] |- Bean [Things Brain Mqtt Message Driven Channel Adapter] Configure.");
        return adapter;
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

    @Bean
    public MqttSubscribeTopicAppenderListener mqttSubscribeTopicAppenderListener(@Qualifier("mqttThingsBrainInbound") Mqttv5PahoMessageDrivenChannelAdapter mqttThingsBrainInbound) {
        MqttSubscribeTopicAppenderListener listener = new MqttSubscribeTopicAppenderListener(mqttThingsBrainInbound);
        log.trace("[ThingsBrain] |- Bean [Mqtt Subscribe Topic Appender] Configure.");
        return listener;
    }

    @Bean
    public MqttMessagePublisher mqttMessagePublisher() {
        DefaultMqttMessagePublisher mqttMessageManager = new DefaultMqttMessagePublisher();
        log.trace("[ThingsBrain] |- Bean [Default Mqtt Message Manager] Configure.");
        return mqttMessageManager;
    }
}
