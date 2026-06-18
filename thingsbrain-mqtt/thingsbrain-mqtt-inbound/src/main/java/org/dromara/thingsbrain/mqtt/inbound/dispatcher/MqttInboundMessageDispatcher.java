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

package org.dromara.thingsbrain.mqtt.inbound.dispatcher;

import org.apache.commons.lang3.StringUtils;
import org.dromara.dante.core.constant.SymbolConstants;
import cn.herodotus.thingsbrain.kernel.commons.enums.TopicCategory;
import org.dromara.thingsbrain.mqtt.inbound.definition.MessageDetails;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/3 15:21
 */
public class MqttInboundMessageDispatcher {

    private final ExtInboundMessageDispatcher extInboundMessageDispatcher;
    private final OtaInboundMessageDispatcher otaInboundMessageDispatcher;
    private final ShadowInboundMessageDispatcher shadowInboundMessageDispatcher;
    private final SysInboundMessageDispatcher sysInboundMessageDispatcher;

    public MqttInboundMessageDispatcher(ExtInboundMessageDispatcher extInboundMessageDispatcher, OtaInboundMessageDispatcher otaInboundMessageDispatcher, ShadowInboundMessageDispatcher shadowInboundMessageDispatcher, SysInboundMessageDispatcher sysInboundMessageDispatcher) {
        this.extInboundMessageDispatcher = extInboundMessageDispatcher;
        this.otaInboundMessageDispatcher = otaInboundMessageDispatcher;
        this.shadowInboundMessageDispatcher = shadowInboundMessageDispatcher;
        this.sysInboundMessageDispatcher = sysInboundMessageDispatcher;
    }

    public void process(String topic, byte[] payload, String responseTopic, byte[] correlationData) {

        TopicCategory topicCategory = getTopicCategory(topic);

        switch (topicCategory) {
            case OTA -> otaInboundMessageDispatcher.process(new MessageDetails(topic, payload));
            case EXT ->
                    extInboundMessageDispatcher.process(new MessageDetails(topic, payload, responseTopic, correlationData));
            case SHADOW -> shadowInboundMessageDispatcher.process(new MessageDetails(topic, payload));
            default ->
                    sysInboundMessageDispatcher.process(new MessageDetails(topic, payload, responseTopic, correlationData));
        }
        ;
    }

    private TopicCategory getTopicCategory(String topic) {
        String prefix = StringUtils.substringBefore(topic, SymbolConstants.FORWARD_SLASH);
        if (StringUtils.isNotBlank(prefix)) {
            return TopicCategory.get(prefix);
        } else {
            return TopicCategory.SYS;
        }
    }
}
