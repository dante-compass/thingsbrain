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

package cn.herodotus.thingsbrain.mqtt.inbound.definition.dispatcher;

import cn.herodotus.thingsbrain.kernel.link.definition.LinkResponse;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttMessagePublisher;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttOperation;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.MessageDetails;
import cn.herodotus.thingsbrain.mqtt.inbound.response.InboundMessageReplyProcessor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.dante.core.jackson.JsonNodeUtils;
import tools.jackson.databind.JsonNode;

import java.util.Optional;

/**
 * <p>Description: 需要 Reply 的 Mqtt 消息监听器抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/20 14:38
 */
public abstract class AbstractReplyMessageDispatcher implements InboundMessageDispatcher {

    private final InboundMessageReplyProcessor inboundMessageReplyProcessor;
    private final MqttMessagePublisher mqttMessagePublisher;

    protected AbstractReplyMessageDispatcher(InboundMessageReplyProcessor inboundMessageReplyProcessor, MqttMessagePublisher mqttMessagePublisher) {
        this.inboundMessageReplyProcessor = inboundMessageReplyProcessor;
        this.mqttMessagePublisher = mqttMessagePublisher;
    }

    @Override
    public void process(MessageDetails details) {

        // 使用 JsonNode 类型，是为了提升一定的转换效率
        // 如果直接 JacksonUtils 转换成对象，这里不知道该转换成什么类型，只能定义包含 id 和 method 的对象，先转换一次，后面进入到 handler 再转换成具体的类型，这就出现了两次转换
        // 使用 JsonNode，先行将 JSON 进行解析。这里就可以直接获取必要属性，后面进入到 handler 再转换成具体对象。相当于只转换了一次
        JsonNode payload = details.getPayload();

        String id = JsonNodeUtils.findStringValue(payload, "id");
        String method = JsonNodeUtils.findStringValue(payload, "method");

        // event.getCorrelationData() 是将 byte[] 转成 string，提前取出来，减少转换
        String correlationData = details.getCorrelationData();

        if (StringUtils.isNoneBlank(id, method, details.getCorrelationData())) {
            Optional<MqttOperation> optional = mqttMessagePublisher.exists(id);
            optional.ifPresentOrElse(
                    mqttOperation -> responseProcess(mqttOperation, payload),
                    () -> requestProcess(id, method, details.getTopic(), payload, details.getResponseTopic(), correlationData));
        } else {
            publish(details.getResponseTopic(), LinkResponse.requestParameterError(id, method), correlationData);
        }
    }

    private void responseProcess(MqttOperation mqttOperation, JsonNode jsonNode) {
        inboundMessageReplyProcessor.process(mqttOperation, jsonNode);
    }

    abstract protected void requestProcess(String id, String method, String topic, JsonNode payload, String responseTopic, String correlationData);

    protected void publish(String topic, LinkResponse<?> response, String correlationData) {
        if (ObjectUtils.isNotEmpty(response)) {
            mqttMessagePublisher.response(topic, response, correlationData);
        }
    }
}
