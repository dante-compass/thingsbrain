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

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.commons.constant.ProtocolConstants;
import cn.herodotus.thingsbrain.kernel.commons.domain.CompleteIdentifier;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.ShadowRequest;
import cn.herodotus.thingsbrain.kernel.link.domain.shadow.ShadowResponse;
import cn.herodotus.thingsbrain.link.commons.definition.DeviceShadowManager;
import cn.herodotus.thingsbrain.mqtt.commons.definition.MqttOutboundMessagePublisher;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttMessageDetails;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttTopic;
import cn.herodotus.thingsbrain.mqtt.inbound.definition.dispatcher.InboundMessageDispatcher;
import cn.herodotus.thingsbrain.persistence.commons.domain.DeviceShadow;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * <p>Description: Mqtt 上报消息监听器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/14 12:54
 */
public class InboundShadowMessageDispatcher implements InboundMessageDispatcher {

    private static final List<String> SUPPORTED_METHOD = List.of(MethodConstants.METHOD__SHADOW_UPDATE, MethodConstants.METHOD__SHADOW_DELETE, MethodConstants.METHOD__SHADOW_GET);
    private static final String SHADOW_UPDATE_TOPIC_TEMPLATE = "shadow/update/${productKey}/${deviceName}";
    private static final String SHADOW_GET_TOPIC_TEMPLATE = "shadow/get/${productKey}/${deviceName}";

    public static final MqttTopic SHADOW_MQTT_TOPIC = new MqttTopic(SHADOW_UPDATE_TOPIC_TEMPLATE, SHADOW_GET_TOPIC_TEMPLATE);

    private final DeviceShadowManager deviceShadowManager;
    private final MqttOutboundMessagePublisher mqttOutboundMessagePublisher;

    public InboundShadowMessageDispatcher(DeviceShadowManager deviceShadowManager, MqttOutboundMessagePublisher mqttOutboundMessagePublisher) {
        this.deviceShadowManager = deviceShadowManager;
        this.mqttOutboundMessagePublisher = mqttOutboundMessagePublisher;
    }

    @Override
    public void process(MqttMessageDetails details) {

        // 从 Topic 中获取必要参数。
        CompleteIdentifier completeIdentifier = CompleteIdentifier.of(SHADOW_MQTT_TOPIC.getTemplate(), details.getTopic()).build();

        // 解析请求数据
        ShadowRequest request = JacksonUtils.toObject(details.getPayload(), ShadowRequest.class);
        // 校验请求数据。
        ShadowResponse response = verification(request);

        // 如果校验通过 response 返回 null，就继续进行逻辑处理，反之则直接发送错误 response 信息
        if (ObjectUtils.isEmpty(response)) {
            // 进行业务处理。对于需要有 response 的业务，则返回 response。
            response = process(request.getMethod(), completeIdentifier.getProductKey(), completeIdentifier.getDeviceName(), request);
        }

        // 如果 response 不为 null，则发送 response 信息给设备。
        if (ObjectUtils.isNotEmpty(response)) {
            mqttOutboundMessagePublisher.publish(SHADOW_MQTT_TOPIC.getReplyTopic(completeIdentifier.getProductKey(), completeIdentifier.getDeviceName()), JacksonUtils.toJson(response));
        }
    }

    private ShadowResponse process(String method, String productKey, String deviceName, ShadowRequest request) {
        return switch (method) {
            case MethodConstants.METHOD__SHADOW_GET -> get(productKey, deviceName);
            case MethodConstants.METHOD__SHADOW_DELETE ->
                    update(manager -> manager.delete(productKey, deviceName, request));
            default -> update(manager -> manager.update(productKey, deviceName, request));
        };
    }

    private ShadowResponse get(String productKey, String deviceName) {
        return deviceShadowManager
                .get(productKey, deviceName)
                .map(ShadowResponse::reply)
                .orElse(ShadowResponse.error());
    }

    private ShadowResponse update(Function<DeviceShadowManager, Optional<DeviceShadow>> function) {
        return function.apply(deviceShadowManager)
                .map(deviceShadow -> ShadowResponse.reply(deviceShadow.getVersion()))
                .orElse(ShadowResponse.error("409", "影子版本冲突"));
    }

    /**
     * 校验请求数据
     *
     * @param request 设备影子请求数据 {@link ShadowRequest}
     * @return null 表示校验通过；{@link ShadowResponse} 请求数据校验出现错误
     */
    private ShadowResponse verification(ShadowRequest request) {

        if (ObjectUtils.isEmpty(request)) {
            return ShadowResponse.error("400", "不正确的 JSON 格式");
        }

        if (StringUtils.isBlank(request.getMethod())) {
            return ShadowResponse.error("401", "影子数据缺少 method 信息");
        }

        if (!SUPPORTED_METHOD.contains(request.getMethod())) {
            return ShadowResponse.error("406", "影子数据中 method 是无效的方法");
        }

        if (MapUtils.isNotEmpty(request.getState())) {
            if (!request.getState().containsKey(ProtocolConstants.PARAMETER__REPORTED)) {
                return ShadowResponse.error("404", "影子数据缺少 reported 字段");
            } else {
                Object reported = request.getState().get(ProtocolConstants.PARAMETER__REPORTED);
                if (ObjectUtils.isEmpty(reported)) {
                    return ShadowResponse.error("405", "影子数据中 reported 属性字段为空");
                }
            }
        } else {
            if (!Strings.CS.equals(request.getMethod(), MethodConstants.METHOD__SHADOW_GET)) {
                if (ObjectUtils.isEmpty(request.getState())) {
                    return ShadowResponse.error("407", "影子内容为空");
                }
            }
        }

        return null;
    }
}
