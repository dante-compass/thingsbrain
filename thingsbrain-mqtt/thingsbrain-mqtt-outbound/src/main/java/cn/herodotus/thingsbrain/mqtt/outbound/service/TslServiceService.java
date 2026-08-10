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

package cn.herodotus.thingsbrain.mqtt.outbound.service;

import cn.herodotus.dante.security.domain.UserPrincipal;
import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.kernel.commons.domain.SchemaValidationResult;
import cn.herodotus.thingsbrain.kernel.commons.exception.JsonSchemaValidateException;
import cn.herodotus.thingsbrain.link.commons.definition.SpecificationManager;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttOutboundMessagePublisher;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>Description: 设置设备属性 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/21 22:53
 */
@Service
public class TslServiceService {

    private static final MqttTopic TOPIC_SET = new MqttTopic(MethodConstants.METHOD__THING_SERVICE_PROPERTY_SET);
    private static final MqttTopic TOPIC_INVOKE = new MqttTopic(MethodConstants.METHOD__THING_SERVICE_IDENTIFIER, MqttTopic.Parameter.SERVICE);

    private final SpecificationManager specificationManager;
    private final MqttOutboundMessagePublisher mqttOutboundMessagePublisher;

    public TslServiceService(SpecificationManager specificationManager, MqttOutboundMessagePublisher mqttOutboundMessagePublisher) {
        this.specificationManager = specificationManager;
        this.mqttOutboundMessagePublisher = mqttOutboundMessagePublisher;
    }

    public void set(String productKey, String deviceName, Map<String, Object> params, UserPrincipal userPrincipal) {
        SchemaValidationResult result = specificationManager.verification(productKey, ProtocolConstants.ACTION__SET, params);
        if (result.getValid()) {
            mqttOutboundMessagePublisher.request(TOPIC_SET, productKey, deviceName, params, userPrincipal);
        } else {
            throw new JsonSchemaValidateException(result.getMessage());
        }
    }

    public void invoke(String productKey, String deviceName, String identifier, Map<String, Object> params, UserPrincipal userPrincipal) {
        SchemaValidationResult result = specificationManager.verification(productKey, identifier, params);
        if (result.getValid()) {
            mqttOutboundMessagePublisher.request(TOPIC_INVOKE, productKey, deviceName, identifier, params, userPrincipal);
        } else {
            throw new JsonSchemaValidateException(result.getMessage());
        }
    }
}
