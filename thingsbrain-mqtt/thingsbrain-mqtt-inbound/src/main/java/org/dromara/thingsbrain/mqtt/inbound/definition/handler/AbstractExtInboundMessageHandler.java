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

package org.dromara.thingsbrain.mqtt.inbound.definition.handler;

import org.dromara.dante.core.jackson.JacksonUtils;
import cn.herodotus.thingsbrain.kernel.commons.domain.CompleteIdentifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.kernel.commons.exception.InboundMessageProcessingException;
import cn.herodotus.thingsbrain.kernel.link.definition.LinkRequest;
import cn.herodotus.thingsbrain.kernel.link.definition.LinkResponse;
import org.dromara.thingsbrain.link.commons.definition.SubsetSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.JsonNode;

/**
 * <p>Description: Ext 主题北向数据处理器抽象定义 </p>
 *
 * @param <I> 入站请求业务数据类型
 * @param <O> 出站响应结果数据类型
 * @author : gengwei.zheng
 * @date : 2025/6/15 21:52
 */
public abstract class AbstractExtInboundMessageHandler<I, O> extends AbstractReplyInboundMessageHandler<I, O, SubsetSessionManager, LinkRequest<I>> implements ExtInboundMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractExtInboundMessageHandler.class);

    private final SubsetSessionManager subsetSessionManager;

    protected AbstractExtInboundMessageHandler(MqttTopic mqttTopic, SubsetSessionManager subsetSessionManager) {
        super(mqttTopic);
        this.subsetSessionManager = subsetSessionManager;
    }

    @Override
    public LinkResponse<?> receive(String topic, JsonNode payload, String responseTopic, String correlationData) {
        CompleteIdentifier completeIdentifier = getCompleteIdentifier(topic);
        LinkRequest<I> domain = JacksonUtils.toObject(payload, getTypeReference());

        try {
            O result = getFunction(subsetSessionManager).apply(completeIdentifier, domain.getParams());
            return success(domain.getId(), domain.getMethod(), result);
        } catch (InboundMessageProcessingException e) {
            log.error("[ThingsBrain] |- Ext session topic data process catch error!", e);
            return internalServerError(domain.getId(), domain.getMethod());
        }
    }
}
