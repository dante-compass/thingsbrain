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

package cn.herodotus.thingsbrain.mqtt.inbound.dispatcher;

import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.commons.domain.CompleteIdentifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.kernel.commons.domain.Shadow;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.ShadowRequest;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.ShadowResponse;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttMessagePublisher;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.MessageDetails;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.dispatcher.InboundMessageDispatcher;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.dromara.dante.core.jackson.JacksonUtils;
import org.dromara.thingsbrain.link.commons.definition.DeviceShadowManager;
import org.dromara.thingsbrain.persistence.commons.domain.DeviceShadow;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * <p>Description: Mqtt 上报消息监听器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/14 12:54
 */
public class ShadowInboundMessageDispatcher implements InboundMessageDispatcher {

    private static final List<String> SUPPORTED_METHOD = List.of(MethodConstants.METHOD__SHADOW__UPDATE, MethodConstants.METHOD__SHADOW__DELETE, MethodConstants.METHOD__SHADOW__GET);
    private static final String SHADOW_UPDATE_TOPIC_TEMPLATE = "shadow/update/${productKey}/${deviceName}";
    private static final String SHADOW_GET_TOPIC_TEMPLATE = "shadow/get/${productKey}/${deviceName}";

    public static final MqttTopic SHADOW_MQTT_TOPIC = new MqttTopic(SHADOW_UPDATE_TOPIC_TEMPLATE, SHADOW_GET_TOPIC_TEMPLATE);

    private final DeviceShadowManager deviceShadowManager;
    private final MqttMessagePublisher mqttMessagePublisher;

    public ShadowInboundMessageDispatcher(DeviceShadowManager deviceShadowManager, MqttMessagePublisher mqttMessagePublisher) {
        this.deviceShadowManager = deviceShadowManager;
        this.mqttMessagePublisher = mqttMessagePublisher;
    }

    @Override
    public void process(MessageDetails event) {

        String topic = event.getTopic();
        JsonNode payload = event.getPayload();

        ShadowRequest request = JacksonUtils.toObject(payload, ShadowRequest.class);
        ShadowResponse response = verification(request);

        CompleteIdentifier completeIdentifier = CompleteIdentifier.of(SHADOW_MQTT_TOPIC.getTemplate(), topic).build();

        if (ObjectUtils.isEmpty(response)) {
            response = process(request.getMethod(), completeIdentifier.getProductKey(), completeIdentifier.getDeviceName(), request);
        }

        mqttMessagePublisher.publish(SHADOW_MQTT_TOPIC.getReplyTopic(completeIdentifier.getProductKey(), completeIdentifier.getDeviceName()), JacksonUtils.toJson(response));
    }

    private ShadowResponse process(String method, String productKey, String deviceName, ShadowRequest request) {
        return switch (method) {
            case MethodConstants.METHOD__SHADOW__GET -> get(productKey, deviceName);
            case MethodConstants.METHOD__SHADOW__DELETE ->
                    modify(shadowManager -> shadowManager.delete(productKey, deviceName, request.getState(), request.getVersion()));
            default ->
                    modify(shadowManager -> shadowManager.update(productKey, deviceName, request.getState(), request.getVersion()));
        };
    }

    private ShadowResponse get(String productKey, String deviceName) {
        Optional<Shadow> optional = deviceShadowManager.get(productKey, deviceName);
        return optional.map(ShadowResponse::success).orElse(ShadowResponse.failure());
    }

    private ShadowResponse modify(Function<DeviceShadowManager, Optional<DeviceShadow>> function) {
        Optional<DeviceShadow> optional = function.apply(deviceShadowManager);
        return optional.map(deviceShadow -> ShadowResponse.success(deviceShadow.getVersion()))
                .orElse(ShadowResponse.failure());
    }

    private ShadowResponse verification(ShadowRequest request) {

        if (ObjectUtils.isEmpty(request)) {
            return ShadowResponse.failure("400", "不正确的JSON格式");
        }

        if (StringUtils.isBlank(request.getMethod())) {
            return ShadowResponse.failure("401", "影子数据缺少 method 信息");
        }

        if (!SUPPORTED_METHOD.contains(request.getMethod())) {
            return ShadowResponse.failure("406", "影子数据中 method是无效的方法");
        }

        if (!Strings.CS.equals(request.getMethod(), MethodConstants.METHOD__SHADOW__GET)) {
            if (ObjectUtils.isEmpty(request.getState())) {
                return ShadowResponse.failure("402", "影子数据缺少 state 信息");
            }

            if (MapUtils.isEmpty(request.getState().getReported())) {
                return ShadowResponse.failure("405", "影子数据中 reported属性字段为空");
            }
        }

        return null;
    }
}
