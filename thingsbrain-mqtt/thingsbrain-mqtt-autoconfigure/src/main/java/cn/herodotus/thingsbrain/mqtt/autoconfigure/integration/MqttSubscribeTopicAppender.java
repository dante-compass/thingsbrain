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

import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.mqtt.client.core.Mqtt5ClientManager;
import org.springframework.integration.mqtt.client.inbound.Mqtt5MessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;

/**
 * <p>Description: Mqtt 订阅主题动态添加器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2026/11/24 22:38
 */
public class MqttSubscribeTopicAppender {

    private static final String FLOW_ID = "mqttThingsMeshInbound";
    private final IntegrationFlowContext integrationFlowContext;
    private final Mqtt5ClientManager mqtt5ClientManager;
    private final MessageChannel mqttInboundChannel;

    public MqttSubscribeTopicAppender(IntegrationFlowContext integrationFlowContext, Mqtt5ClientManager mqtt5ClientManager, MessageChannel mqttInboundChannel) {
        this.integrationFlowContext = integrationFlowContext;
        this.mqtt5ClientManager = mqtt5ClientManager;
        this.mqttInboundChannel = mqttInboundChannel;
    }

    public void subscribe(Mqtt5Subscription... subscriptions) {

        Mqtt5MessageDrivenChannelAdapter adapter = new Mqtt5MessageDrivenChannelAdapter(mqtt5ClientManager, subscriptions);
        adapter.setPayloadType(String.class);
        adapter.setManualAck(false);
        adapter.setOutputChannel(mqttInboundChannel);
        adapter.setErrorChannelName(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME);

        IntegrationFlow flow = IntegrationFlow.from(adapter).get();

        integrationFlowContext.registration(flow)
                .id(FLOW_ID)
                .autoStartup(true)
                .register();
    }

    public void unsubscribe() {
        integrationFlowContext.remove(FLOW_ID);
    }
}
