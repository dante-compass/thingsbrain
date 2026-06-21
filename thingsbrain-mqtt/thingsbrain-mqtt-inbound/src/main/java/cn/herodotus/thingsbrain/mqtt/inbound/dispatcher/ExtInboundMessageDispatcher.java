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

package cn.herodotus.thingsbrain.mqtt.inbound.dispatcher;

import cn.herodotus.thingsbrain.kernel.link.definition.LinkResponse;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttMessagePublisher;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.dispatcher.AbstractReplyMessageDispatcher;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.handler.ExtInboundMessageHandler;
import cn.herodotus.thingsbrain.mqtt.inbound.factory.ExtMessageHandlerFactory;
import cn.herodotus.thingsbrain.mqtt.inbound.response.InboundMessageReplyProcessor;
import tools.jackson.databind.JsonNode;

import java.util.Optional;

/**
 * <p>Description: Mqtt 上报消息监听器 </p>
 * <p>
 * 子设备可以逐个上下线，也可以批量上下线。子设备上线之前，需在物联网平台为子设备注册身份，建立子设备与网关的拓扑关系。子设备上线时，物联网平台会根据拓扑关系进行子设备身份校验，以确定子设备是否具备使用网关通道的能力。
 * <p>
 * 说明
 * · 子设备上下线、批量上下线消息，只支持QoS=0，不支持QoS=1。
 * · 一个网关下，同时在线的子设备数量不能超过2,000。在线子设备数量达到2,000个后，新的子设备上线请求将被拒绝。
 * · 发送子设备批量上下线请求时，单个批次上下线的子设备数量不超过50个。
 * · 设备批量上下线请求结果为全部成功或全部失败，失败时的data响应参数中会包含具体的设备信息。
 *
 * @author : gengwei.zheng
 * @date : 2025/5/14 12:54
 */
public class ExtInboundMessageDispatcher extends AbstractReplyMessageDispatcher {

    private final ExtMessageHandlerFactory extMessageHandlerFactory;

    public ExtInboundMessageDispatcher(ExtMessageHandlerFactory extMessageHandlerFactory, InboundMessageReplyProcessor inboundMessageReplyProcessor, MqttMessagePublisher mqttMessagePublisher) {
        super(inboundMessageReplyProcessor, mqttMessagePublisher);
        this.extMessageHandlerFactory = extMessageHandlerFactory;
    }

    @Override
    protected void requestProcess(String id, String method, String topic, JsonNode payload, String responseTopic, String correlationData) {
        Optional<ExtInboundMessageHandler> optional = extMessageHandlerFactory.getHandler(method);
        optional.ifPresentOrElse(handler -> {
            LinkResponse<?> response = handler.receive(topic, payload, responseTopic, correlationData);
            publish(responseTopic, response, correlationData);
        }, () -> {
            publish(responseTopic, LinkResponse.requestParameterError(id, method), correlationData);
        });
    }
}
