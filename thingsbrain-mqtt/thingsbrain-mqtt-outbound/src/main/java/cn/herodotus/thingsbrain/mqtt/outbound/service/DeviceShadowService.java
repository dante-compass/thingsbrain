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

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.ShadowRequest;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.ShadowResponse;
import cn.herodotus.thingsbrain.link.commons.definition.DeviceShadowManager;
import cn.herodotus.thingsbrain.mqtt.commons.constant.MqttConstants;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttOutboundMessagePublisher;
import cn.herodotus.thingsbrain.persistence.commons.domain.DeviceShadow;

import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: 设备影子服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/8 17:50
 */
public class DeviceShadowService {

    private final DeviceShadowManager deviceShadowManager;
    private final MqttOutboundMessagePublisher mqttOutboundMessagePublisher;

    public DeviceShadowService(DeviceShadowManager deviceShadowManager, MqttOutboundMessagePublisher mqttOutboundMessagePublisher) {
        this.deviceShadowManager = deviceShadowManager;
        this.mqttOutboundMessagePublisher = mqttOutboundMessagePublisher;
    }

    /**
     * 业务系统可以通过该 API 改变设备的状态
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param data       变更的属性 {@link Map}
     * @param version    新的版本号
     */
    public void update(String productKey, String deviceName, Map<String, Object> data, Long version) {
        ShadowRequest request = ShadowRequest.update(version).desired(data).build();

        Optional<DeviceShadow> optional = deviceShadowManager.update(productKey, deviceName, request.getState(), request.getVersion());
        ShadowResponse response = optional.map(deviceShadowManager::read)
                .map(ShadowResponse::control)
                .orElse(ShadowResponse.failure());

        mqttOutboundMessagePublisher.publish(MqttConstants.MQTT_TOPIC__SHADOW.getReplyTopic(productKey, deviceName), JacksonUtils.toJson(response));
    }
}
