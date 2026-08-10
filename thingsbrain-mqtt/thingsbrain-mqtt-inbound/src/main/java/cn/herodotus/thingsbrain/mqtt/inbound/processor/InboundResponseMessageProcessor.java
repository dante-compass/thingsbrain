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

package cn.herodotus.thingsbrain.mqtt.inbound.processor;

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.dante.message.commons.constant.MessageConstants;
import cn.herodotus.dante.message.commons.definition.strategy.MessageSendingEventManager;
import cn.herodotus.dante.message.commons.domain.UserMessage;
import cn.herodotus.thingsbrain.kernel.link.domain.LinkResponse;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttMessageDetails;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: 入站 Reply 消息处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/18 23:19
 */
public class InboundResponseMessageProcessor {

    private final MessageSendingEventManager messageSendingEventManager;

    public InboundResponseMessageProcessor(MessageSendingEventManager messageSendingEventManager) {
        this.messageSendingEventManager = messageSendingEventManager;
    }

    public void process(MqttOperation mqttOperation, MqttMessageDetails details) {
        if (StringUtils.isNotEmpty(mqttOperation.getUserId())) {

            LinkResponse<?> response = JacksonUtils.toObject(details.getPayload(), LinkResponse.class);

            UserMessage userMessage = new UserMessage();
            userMessage.setUserId(mqttOperation.getUserId());
            userMessage.setDestination(MessageConstants.WEBSOCKET_DESTINATION_PERSONAL_NOTIFY);

            if (ObjectUtils.isNotEmpty(response)) {
                if (response.getCode() == 200) {
                    userMessage.setPayload("操作成功");
                } else {
                    userMessage.setPayload("操作失败");
                }
            }

            messageSendingEventManager.websocketToUser(userMessage);
        }
    }
}
