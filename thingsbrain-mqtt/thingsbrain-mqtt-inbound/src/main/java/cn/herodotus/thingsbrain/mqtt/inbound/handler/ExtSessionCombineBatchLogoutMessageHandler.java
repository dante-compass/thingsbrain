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

package cn.herodotus.thingsbrain.mqtt.inbound.handler;

import cn.herodotus.dante.core.function.ThrowableBiFunction;
import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.commons.domain.CompleteIdentifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.Identifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.kernel.commons.enums.TopicCategory;
import cn.herodotus.thingsbrain.kernel.commons.exception.InboundMessageProcessingException;
import cn.herodotus.thingsbrain.kernel.link.definition.LinkRequest;
import cn.herodotus.thingsbrain.link.commons.definition.SubsetSessionManager;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.handler.AbstractInboundExtMessageHandler;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;

import java.util.List;

/**
 * <p>Description: 子设备批量下线消息管理器 </p>
 * <p>
 * 因为子设备通过网关通道与物联网平台通信，以上Topic为网关设备的Topic。Topic中变量${productKey}和${deviceName}需替换为网关设备的对应信息
 *
 * @author : gengwei.zheng
 * @date : 2025/6/16 12:34
 */
@Component(MethodConstants.METHOD__COMBINE_BATCH_LOGOUT)
public class ExtSessionCombineBatchLogoutMessageHandler extends AbstractInboundExtMessageHandler<List<Identifier>, List<Identifier>> {

    private final TypeReference<LinkRequest<List<Identifier>>> typeReference = new TypeReference<>() {
    };

    public ExtSessionCombineBatchLogoutMessageHandler(SubsetSessionManager subsetSessionManager) {
        super(new MqttTopic(TopicCategory.EXT, MethodConstants.METHOD__COMBINE_BATCH_LOGOUT), subsetSessionManager);
    }

    @Override
    protected TypeReference<LinkRequest<List<Identifier>>> getTypeReference() {
        return typeReference;
    }

    @Override
    protected ThrowableBiFunction<CompleteIdentifier, List<Identifier>, List<Identifier>, InboundMessageProcessingException> getFunction(SubsetSessionManager subsetSessionManager) {
        return (identity, param) -> subsetSessionManager.batchLogout(identity.getProductKey(), identity.getDeviceName(), param);
    }
}
