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

package cn.herodotus.thingsbrain.mqtt.inbound.definition.handler;

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.thingsbrain.kernel.commons.domain.CompleteIdentifier;
import cn.herodotus.thingsbrain.kernel.commons.exception.InboundMessageProcessingException;
import cn.herodotus.thingsbrain.kernel.link.definition.SysDomain;
import cn.herodotus.thingsbrain.kernel.link.domain.LinkResponse;
import cn.herodotus.thingsbrain.kernel.link.domain.LinkSysRequest;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttMessageDetails;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttTopic;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 系统主题北向数据处理器抽象定义 </p>
 *
 * @param <I> 入站请求业务数据类型
 * @param <O> 出站响应结果数据类型
 * @param <M> 对应业务处理 Manager
 * @author : gengwei.zheng
 * @date : 2025/6/15 21:52
 */
public abstract class AbstractInboundSysMessageHandler<I, O, M> extends AbstractInboundResponseMessageHandler<I, O, M, LinkSysRequest<I>> implements InboundSysMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractInboundSysMessageHandler.class);

    private final M messageManager;

    protected AbstractInboundSysMessageHandler(MqttTopic mqttTopic, M messageManager) {
        super(mqttTopic);
        this.messageManager = messageManager;
    }

    /**
     * 判断是否需要响应
     *
     * @param request 请求数据 {@link LinkSysRequest}
     * @return true 需要回复
     */
    private boolean isNeedReply(MqttMessageDetails details, LinkSysRequest<I> request) {
        // 当前主题定义是否支持 Reply
        if (getMqttTopic().isSupportReply() && details.isSupportResponse()) {
            SysDomain sys = request.getSys();
            // 判断上报数据是否指定需要 Replay
            return ObjectUtils.isNotEmpty(sys) && sys.getAck() == 1;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkResponse<?> receive(MqttMessageDetails details) {
        CompleteIdentifier identity = getCompleteIdentifier(details.getTopic());
        LinkSysRequest<I> domain = JacksonUtils.toObject(details.getPayload(), getTypeReference());

        boolean isNeedReply = isNeedReply(details, domain);

        try {
            O result = getFunction(messageManager).apply(identity, domain.getParams());
            return success(domain.getId(), result, isNeedReply);
        } catch (InboundMessageProcessingException e) {
            log.error("[ThingsBrain] |- Ext session topic data process catch error!", e);
            return internalServerError(domain.getId(), isNeedReply);
        }
    }
}
