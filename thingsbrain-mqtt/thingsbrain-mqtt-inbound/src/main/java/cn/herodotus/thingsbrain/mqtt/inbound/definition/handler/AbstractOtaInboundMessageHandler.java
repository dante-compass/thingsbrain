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
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.kernel.link.definition.LinkRequest;
import cn.herodotus.thingsbrain.link.commons.definition.OtaManager;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttMessageDetails;

import java.util.function.BiConsumer;

/**
 * <p>Description: Ota主题北向数据处理器抽象定义 </p>
 *
 * @param <I> 入站请求业务数据类型
 * @author : gengwei.zheng
 * @date : 2025/6/15 21:52
 */
public abstract class AbstractOtaInboundMessageHandler<I> extends AbstractInboundMessageHandler<LinkRequest<I>> implements OtaInboundMessageHandler {

    private final OtaManager otaManager;

    protected AbstractOtaInboundMessageHandler(MqttTopic mqttTopic, OtaManager otaManager) {
        super(mqttTopic);
        this.otaManager = otaManager;
    }

    /**
     * 获取处理器业务逻辑处理定义
     *
     * @param otaManager 逻辑处理管理器
     * @return 业务逻辑处理定义
     */
    protected abstract BiConsumer<CompleteIdentifier, I> getConsumer(OtaManager otaManager);

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void receive(MqttMessageDetails details) {
        CompleteIdentifier identity = getCompleteIdentifier(details.getTopic());
        LinkRequest<I> domain = JacksonUtils.toObject(details.getPayload(), getTypeReference());
        getConsumer(otaManager).accept(identity, domain.getParams());
    }
}
