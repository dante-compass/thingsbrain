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

import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.commons.domain.CompleteIdentifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.Identifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.kernel.commons.exception.InboundMessageProcessingException;
import cn.herodotus.thingsbrain.kernel.link.definition.LinkSysRequest;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.handler.AbstractSysInboundMessageHandler;
import org.dromara.dante.core.function.ThrowableBiFunction;
import org.dromara.thingsbrain.link.commons.definition.SubsetTopoManager;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;

import java.util.List;

/**
 * <p>Description: 删除设备的拓扑关系 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/16 12:35
 */
@Component(MethodConstants.METHOD__THING_TOPO_DELETE)
public class SysThingTopoDeleteInboundMessageHandler extends AbstractSysInboundMessageHandler<List<Identifier>, List<Identifier>, SubsetTopoManager> {

    private final TypeReference<LinkSysRequest<List<Identifier>>> typeReference = new TypeReference<>() {
    };

    public SysThingTopoDeleteInboundMessageHandler(SubsetTopoManager subsetTopoManager) {
        super(new MqttTopic(MethodConstants.METHOD__THING_TOPO_DELETE), subsetTopoManager);
    }

    @Override
    protected TypeReference<LinkSysRequest<List<Identifier>>> getTypeReference() {
        return typeReference;
    }

    @Override
    protected ThrowableBiFunction<CompleteIdentifier, List<Identifier>, List<Identifier>, InboundMessageProcessingException> getFunction(SubsetTopoManager subsetTopoManager) {
        return (identity, param) -> subsetTopoManager.delete(identity.getProductKey(), identity.getDeviceName(), param);
    }
}
