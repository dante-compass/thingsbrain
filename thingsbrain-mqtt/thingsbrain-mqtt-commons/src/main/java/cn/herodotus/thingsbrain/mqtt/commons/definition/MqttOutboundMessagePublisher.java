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

package cn.herodotus.thingsbrain.mqtt.commons.definition;

import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.dante.messaging.event.MqttMessageSendingEvent;
import cn.herodotus.dante.security.domain.UserPrincipal;
import cn.herodotus.dante.spring.context.ServiceContextHolder;
import cn.herodotus.thingsbrain.kernel.commons.enums.Qos;
import cn.herodotus.thingsbrain.kernel.link.domain.LinkRequest;
import cn.herodotus.thingsbrain.kernel.link.domain.LinkResponse;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttMessageDetails;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttOperation;
import cn.herodotus.thingsbrain.mqtt.commons.domain.MqttTopic;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p>Description: Mqtt 消息管理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/17 14:10
 */
public interface MqttOutboundMessagePublisher {

    /**
     * 判断缓存中，对应的请求ID是否已经存在。
     *
     * @param details Mqtt 消息详情 {@link MqttMessageDetails}
     * @return mqtt 请求数据 {@link MqttOperation}
     */
    Optional<MqttOperation> get(MqttMessageDetails details);

    /**
     * 将操作数据进行缓存，方便响应时读取使用
     *
     * @param requestId 请求 ID
     * @param operation 操作数据 {@link MqttOperation}
     */
    void record(String requestId, MqttOperation operation);

    /**
     * 发布 Mqtt 消息
     *
     * @param topic           主题
     * @param payload         内容
     * @param qos             Qos
     * @param responseTopic   响应主题
     * @param correlationData 关联数据
     */
    private void publish(String topic, String payload, Integer qos, String responseTopic, byte[] correlationData) {
        ServiceContextHolder.publishEvent(new MqttMessageSendingEvent(topic, payload, qos, responseTopic, correlationData));
    }

    /**
     * 发布 Mqtt 消息
     *
     * @param topic   主题
     * @param payload 内容
     * @param qos     Qos
     */
    default void publish(String topic, String payload, Integer qos) {
        publish(topic, payload, qos, null, null);
    }

    /**
     * 发布 Mqtt 消息
     *
     * @param topic   主题
     * @param payload 内容
     */
    default void publish(String topic, String payload) {
        publish(topic, payload, Qos.QOS_0.ordinal());
    }

    /**
     * 发布 Mqtt 消息。主要用于向响应主题发送信息。
     * <p>
     * Mqtt 请求响应模式，响应时需要回传 correlationData，此时的 topic 实际为响应主题
     *
     * @param details Mqtt 消息详情 {@link MqttMessageDetails}
     * @param payload 数据 {@link LinkResponse}
     */
    default void response(MqttMessageDetails details, LinkResponse<?> payload) {
        if (StringUtils.isNotBlank(details.getResponseTopic())) {
            publish(details.getResponseTopic(), JacksonUtils.toJson(payload), details.getQos(), null, details.getCorrelationData());
        }
    }

    /**
     * 异步发送 Mqtt 消息。响应结果利用系统机制，通知给用户
     *
     * @param mqttTopic     主题 {@link MqttTopic}
     * @param productKey    物联网产品 ProductKey
     * @param deviceName    物联网设备 DeviceName
     * @param identifier    物模型 Service 相关标识符
     * @param data          发送数据
     * @param qos           QOS 值
     * @param userPrincipal 用户信息 {@link UserPrincipal}
     * @param <T>           发送数据数据类型
     */
    default <T> void request(MqttTopic mqttTopic, String productKey, String deviceName, String identifier, T data, Integer qos, UserPrincipal userPrincipal) {

        LinkRequest<?> request = ObjectUtils.isNotEmpty(data) ? LinkRequest.with(mqttTopic.getMethod(), data) : LinkRequest.with(mqttTopic.getMethod());

        if (ObjectUtils.isNotEmpty(userPrincipal) && StringUtils.isNotBlank(request.getId())) {
            MqttOperation mqttOperation = MqttOperation.with(productKey, deviceName, identifier)
                    .requestId(request.getId())
                    .userId(userPrincipal.getId())
                    .build();

            record(request.getId(), mqttOperation);

            publish(mqttTopic.getTopic(productKey, deviceName, identifier),
                    JacksonUtils.toJson(request),
                    qos,
                    mqttTopic.getReplyTopic(productKey, deviceName, identifier),
                    request.getId().getBytes(StandardCharsets.UTF_8));
        } else {
            publish(mqttTopic.getTopic(productKey, deviceName, identifier), JacksonUtils.toJson(request), qos);
        }
    }

    /**
     * 异步发送 Mqtt 消息。响应结果利用系统机制，通知给用户
     *
     * @param mqttTopic     主题 {@link MqttTopic}
     * @param productKey    物联网产品 ProductKey
     * @param deviceName    物联网设备 DeviceName
     * @param identifier    物模型 Service 相关标识符
     * @param data          发送数据
     * @param userPrincipal 用户信息 {@link UserPrincipal}
     * @param <T>           发送数据数据类型
     */
    default <T> void request(MqttTopic mqttTopic, String productKey, String deviceName, String identifier, T data, UserPrincipal userPrincipal) {
        request(mqttTopic, productKey, deviceName, identifier, data, Qos.QOS_1.ordinal(), userPrincipal);
    }

    /**
     * 异步发送 Mqtt 消息。响应结果利用系统机制，通知给用户
     *
     * @param mqttTopic     主题 {@link MqttTopic}
     * @param productKey    物联网产品 ProductKey
     * @param deviceName    物联网设备 DeviceName
     * @param data          发送数据
     * @param qos           QOS 值
     * @param userPrincipal 用户信息 {@link UserPrincipal}
     * @param <T>           发送数据数据类型
     */
    default <T> void request(MqttTopic mqttTopic, String productKey, String deviceName, T data, Integer qos, UserPrincipal userPrincipal) {
        request(mqttTopic, productKey, deviceName, null, data, qos, userPrincipal);
    }

    /**
     * 异步发送 Mqtt 消息。响应结果利用系统机制，通知给用户
     *
     * @param mqttTopic     主题 {@link MqttTopic}
     * @param productKey    物联网产品 ProductKey
     * @param deviceName    物联网设备 DeviceName
     * @param data          发送数据
     * @param userPrincipal 用户信息 {@link UserPrincipal}
     * @param <T>           发送数据数据类型
     */
    default <T> void request(MqttTopic mqttTopic, String productKey, String deviceName, T data, UserPrincipal userPrincipal) {
        request(mqttTopic, productKey, deviceName, data, Qos.QOS_1.ordinal(), userPrincipal);
    }

    /**
     * 异步发送 Mqtt 消息。响应结果利用系统机制，通知给用户
     *
     * @param mqttTopic     主题 {@link MqttTopic}
     * @param productKey    物联网产品 ProductKey
     * @param deviceName    物联网设备 DeviceName
     * @param userPrincipal 用户信息 {@link UserPrincipal}
     */
    default void request(MqttTopic mqttTopic, String productKey, String deviceName, UserPrincipal userPrincipal) {
        request(mqttTopic, productKey, deviceName, null, null, Qos.QOS_1.ordinal(), userPrincipal);
    }

    /**
     * 异步发送 Mqtt 消息。仅是发送消息，不需要通知给用户
     *
     * @param mqttTopic  主题 {@link MqttTopic}
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param identifier 物模型 Service 相关标识符
     * @param data       发送数据
     * @param qos        QOS 值
     * @param <T>        发送数据数据类型
     */
    default <T> void request(MqttTopic mqttTopic, String productKey, String deviceName, String identifier, T data, Integer qos) {
        request(mqttTopic, productKey, deviceName, identifier, data, qos, null);
    }

    /**
     * 异步发送 Mqtt 消息。仅是发送消息，不需要通知给用户
     *
     * @param mqttTopic  主题 {@link MqttTopic}
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param identifier 物模型 Service 相关标识符
     * @param data       发送数据
     * @param <T>        发送数据数据类型
     */
    default <T> void request(MqttTopic mqttTopic, String productKey, String deviceName, String identifier, T data) {
        request(mqttTopic, productKey, deviceName, identifier, data, Qos.QOS_1.ordinal());
    }

    /**
     * 异步发送 Mqtt 消息。仅是发送消息，不需要通知给用户
     *
     * @param mqttTopic  主题 {@link MqttTopic}
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param data       发送数据
     * @param qos        QOS 值
     * @param <T>        发送数据数据类型
     */
    default <T> void request(MqttTopic mqttTopic, String productKey, String deviceName, T data, Integer qos) {
        request(mqttTopic, productKey, deviceName, null, data, qos);
    }

    /**
     * 异步发送 Mqtt 消息。仅是发送消息，不需要通知给用户
     *
     * @param mqttTopic  主题 {@link MqttTopic}
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param data       发送数据
     * @param <T>        发送数据数据类型
     */
    default <T> void request(MqttTopic mqttTopic, String productKey, String deviceName, T data) {
        request(mqttTopic, productKey, deviceName, data, Qos.QOS_1.ordinal());
    }

    /**
     * 异步发送 Mqtt 消息。仅是发送消息，不需要通知给用户
     *
     * @param mqttTopic  主题 {@link MqttTopic}
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     */
    default void request(MqttTopic mqttTopic, String productKey, String deviceName) {
        request(mqttTopic, productKey, deviceName, null, null, Qos.QOS_1.ordinal());
    }
}
