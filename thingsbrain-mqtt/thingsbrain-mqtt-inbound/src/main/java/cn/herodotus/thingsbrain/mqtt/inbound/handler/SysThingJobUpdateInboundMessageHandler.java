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
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.kernel.commons.exception.InboundMessageProcessingException;
import cn.herodotus.thingsbrain.kernel.link.definition.LinkSysRequest;
import cn.herodotus.thingsbrain.kernel.link.domain.job.Job;
import cn.herodotus.thingsbrain.kernel.link.domain.job.JobUpdate;
import cn.herodotus.thingsbrain.link.commons.definition.DeviceJobManager;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.handler.AbstractInboundSysMessageHandler;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;

/**
 * <p>Description: 更新任务下作业状态消息处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/16 12:22
 */
@Component(MethodConstants.METHOD__THING_JOB_UPDATE)
public class SysThingJobUpdateInboundMessageHandler extends AbstractInboundSysMessageHandler<JobUpdate, Job, DeviceJobManager> {

    private final TypeReference<LinkSysRequest<JobUpdate>> typeReference = new TypeReference<>() {
    };

    public SysThingJobUpdateInboundMessageHandler(DeviceJobManager messageManager) {
        super(new MqttTopic(MethodConstants.METHOD__THING_JOB_UPDATE), messageManager);
    }

    @Override
    protected TypeReference<LinkSysRequest<JobUpdate>> getTypeReference() {
        return typeReference;
    }

    @Override
    protected ThrowableBiFunction<CompleteIdentifier, JobUpdate, Job, InboundMessageProcessingException> getFunction(DeviceJobManager deviceJobManager) {
        return (identity, param) -> deviceJobManager.update(identity.getProductKey(), identity.getDeviceName(), param);
    }
}
