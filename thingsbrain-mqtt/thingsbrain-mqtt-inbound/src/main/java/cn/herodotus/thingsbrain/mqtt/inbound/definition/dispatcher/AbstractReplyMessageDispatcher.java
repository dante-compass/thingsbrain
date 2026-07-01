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
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttOutboundMessagePublisher;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttMessageDetails;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttOperation;
import cn.herodotus.thingsbrain.mqtt.inbound.processor.InboundResponseMessageProcessor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: 需要 Reply 的 Mqtt 消息监听器抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/20 14:38
 */
public abstract class AbstractReplyMessageDispatcher implements InboundMessageDispatcher {

    private final InboundResponseMessageProcessor inboundResponseMessageProcessor;
    private final MqttOutboundMessagePublisher mqttOutboundMessagePublisher;

    protected AbstractReplyMessageDispatcher(InboundResponseMessageProcessor inboundResponseMessageProcessor, MqttOutboundMessagePublisher mqttOutboundMessagePublisher) {
        this.inboundResponseMessageProcessor = inboundResponseMessageProcessor;
        this.mqttOutboundMessagePublisher = mqttOutboundMessagePublisher;
    }

    @Override
    public void process(MqttMessageDetails details) {

        // 使用 JsonNode 类型，是为了提升一定的转换效率
        // 如果直接 JacksonUtils 转换成对象，这里不知道该转换成什么类型，只能定义包含 id 和 method 的对象，先转换一次，后面进入到 handler 再转换成具体的类型，这就出现了两次转换
        // 使用 JsonNode，先行将 JSON 进行解析。这里就可以直接获取必要属性，后面进入到 handler 再转换成具体对象。相当于只转换了一次

        // 主要处理 “请求/响应” 类 Mqtt 消息。当前设计中，请求数据需要包含 method，响应数据中不包含 method。可以以此判断是上行数据还是下行数据的响应。
        if (StringUtils.isNotBlank(details.getMethod())) {
            // 上行数据请求
            requestProcess(details);
        } else {
            // 下行数据的反馈响应
            mqttOutboundMessagePublisher.get(details)
                    .ifPresent(operation -> responseProcess(operation, details));
        }
    }

    /**
     * 设备端发送到平台的 “请求” 数据处理。
     *
     * @param details Mqtt 消息详情 {@link MqttMessageDetails}
     */
    abstract protected void requestProcess(MqttMessageDetails details);

    /**
     * 设备端发送到平台的 “响应” 数据处理。
     *
     * @param operation Mqtt 操作信息 {@link MqttOperation}
     * @param details   details Mqtt 消息详情 {@link MqttMessageDetails}
     */
    private void responseProcess(MqttOperation operation, MqttMessageDetails details) {
        inboundResponseMessageProcessor.process(operation, details);
    }

    /**
     * 平台发送 Mqtt 数据方法
     *
     * @param details  details Mqtt 消息详情 {@link MqttMessageDetails}
     * @param response 发送数据
     */
    protected void response(MqttMessageDetails details, LinkResponse<?> response) {
        if (ObjectUtils.isNotEmpty(response)) {
            mqttOutboundMessagePublisher.response(details, response);
        }
    }
}
