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

package org.dromara.thingsbrain.platform.authentication.mqtt;

import org.apache.commons.lang3.ObjectUtils;
import org.dromara.dante.core.jackson.JacksonUtils;
import org.dromara.dante.message.emqx.event.WebhookClientConnectedEvent;
import org.dromara.dante.spring.context.ServiceContextHolder;
import cn.herodotus.thingsbrain.kernel.commons.domain.Identifier;
import cn.herodotus.thingsbrain.kernel.commons.enums.AuthType;
import cn.herodotus.thingsbrain.kernel.commons.event.MqttRegistrationResponseEvent;
import cn.herodotus.thingsbrain.kernel.commons.utils.DataFormatUtils;
import cn.herodotus.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.dromara.thingsbrain.platform.authentication.domain.MqttRegistrationResponse;
import org.dromara.thingsbrain.platform.authentication.domain.OAuth2ClientRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 基于 Mqtt 协议的设备动态注册抽象定义 </p>
 * <p>
 * 动态注册的逻辑都很相似，首先要用一个“账号”登录，以确保是经过授权的客户端避免随意进行注册，然后利用返回的认证信息再进行客户端的注册。
 * 相同点：
 * · OAuth2：使用的是“父”客户端的 ClientId 和 ClientSecret 获取 AccessToken。之后使用这个 AccessToken 进行动态注册。
 * · Mqtt：使用一个“已知”的账号登录，然后进行注册。注册成功之后会分配一个新的账号，之后使用这个新的账号登录。
 * 不同点：
 * Mqtt 的账号一般是持久化在数据库中，那么第一步中的“已知”的账号就会成为问题：
 * 1. 将其事先存储在数据库确实可以实现登录，但是一方面在逻辑上无法区分是正常登录还是动态注册，另一方面这个账号应该是“一次性”的，只有当前设备注册可用，另外设备注册不可用。
 * 2. 如果要实现阿里云物联网中的签名认证，Emqx 不支持这种方式的认证，除非自己实现插件
 * 所以这个问题如果使用传统数据库认证方式不太好解决。
 * <p>
 * 在 Emqx 中支持认证链，即可以提供多种认证途径，只要其中任意一种认证通过即可认证成功。那么就可以利用这个机制来实现基于 Mqtt 的动态注册。
 * 首先，按照系统提供的签名规则构造 MqttClientId， MqttUsername 和 MqttPassword。
 * 其次，使用 Emqx HTTP API 认证方式来实现首次账号的登录认证。
 * <p>
 * 当前的Mqtt动态注册逻辑：
 * 1. 根据系统提供的 Product 密钥，用户手动生成：MqttClientId， MqttUsername 和 MqttPassword
 * 2. 上一步生成的设备信息，数据库中是没有数据的，所以认证失败，就会进入到 HTTP API 认证 认证环节。
 * 3. HTTP API 认证 用相同的密钥规则，计算 MqttClientId， MqttUsername 和 MqttPassword 并与第一步用户提供的 MqttClientId， MqttUsername 和 MqttPassword 进行比较，如果匹配则认为认证通过。
 * 4. 解析第一步中用户提供的 MqttClientId， MqttUsername 和 MqttPassword 进行动态注册
 * 5. 注册成功后使用专门的 Topic 返回信息
 * 6. 用户断开连接
 * 7. 设备使用新注册的信息登录
 * <p>
 * 注意事项：
 * 1. Emqx HTTP API 认证接口需要返回指定格式内容，来表示认证的状态。在这个阶段是无法返回动态注册结果的。所以当前设计，HTTP API 认证 就只做认证校验。
 * 2. Emqx HTTP API 认证 认证成功之后，还是会触发 {@link WebhookClientConnectedEvent} 事件。那么整个注册逻辑就是在 {@link WebhookClientConnectedEvent} 事件之后进行。
 * 阿里云的客户端动态注册是在第一次链接时就进行了校验和注册，并且可以返回注册结果。具体逻辑不清，猜测是阿里云对自己的 Mqtt 有业务方面的定制。
 * 目前对 Emqx 的了解程度也仅限于此，所以采取了此种方案。
 *
 * @author : gengwei.zheng
 * @date : 2025/7/6 16:05
 */
public abstract class AbstractMqttIdentificationHandler implements MqttIdentificationHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractMqttIdentificationHandler.class);

    private final IdentifierManager identifierManager;

    protected AbstractMqttIdentificationHandler(IdentifierManager identifierManager) {
        this.identifierManager = identifierManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void identifyPerDevice(String clientId) {
        // 一机一密注册认证，只需要给设备配置 Mqtt Account 即可
        identifierManager.performMqttIdentification(clientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void identifyPerProduct(String clientId, String mqttUsername, AuthType authType) {
        // 无需抛出错误或者提示信息，在 Emqx Http 认证阶段已经做了校验以及错误处理
        DataFormatUtils.fromMqttUsername(mqttUsername)
                .ifPresent(identifier -> {
                    if (authType == AuthType.REGISTER) {
                        register(clientId, identifier);
                    } else {
                        regnwl(clientId, identifier);
                    }
                });
    }

    /**
     * regnwl，一型一密免预注册认证方式定义。
     * <p>
     * 定义为抽象方法，以便支持 Reactive 和 Servlet 不同环境的实现。
     *
     * @param clientId      待注册设备 ClientId（对应 OAuth2 ClientID）
     * @param deviceName    待注册设备 DeviceName（对应 OAuth2 ClientName）
     * @param productKey    物联网产品 ProductKey（“父”客户端 Client Name）
     * @param productSecret 物联网产品 ProductSecret（“父”客户端密钥）
     */
    protected abstract void performOAuth2ClientRegistration(String clientId, String deviceName, String productKey, String productSecret);

    /**
     * 一型一密免注册认证方式。即 {@link AuthType} 为 "regnwl" 类型的方式
     *
     * @param clientId   设备 ClientId
     * @param identifier 设备标识符 {@link Identifier}
     */
    private void regnwl(String clientId, Identifier identifier) {
        identifierManager.findDeviceByClientId(clientId)
                .ifPresentOrElse(
                        device -> onRegistrationSuccess(clientId, identifier, device.getDeviceSecret(), AuthType.REGNWL),
                        () -> {
                            // 这里无需再校验 Product 是否存在，在 Emqx Http 认证阶段已经做了校验以及错误处理
                            identifierManager.findProductByProductKey(identifier.getProductKey())
                                    .ifPresent(product -> performOAuth2ClientRegistration(product.getProductKey(), product.getProductSecret(), identifier.getDeviceName(), clientId));
                        });
    }

    /**
     * 一型一密预注册认证方式。即 {@link AuthType} 为 "register" 类型的方式。
     * <p>
     * 该方式需要提前在系统中添加设备。提前添加了设备，所以通过 DeviceName 查询设备信息后，返回 DeviceSecret 等信息。
     *
     * @param clientId   设备 ClientId
     * @param identifier 设备标识符 {@link Identifier}
     */
    private void register(String clientId, Identifier identifier) {
        // 这里无需再校验 Product 是否存在，在 Emqx Http 认证阶段已经做了校验以及错误处理
        identifierManager.findDeviceByClientId(clientId)
                .ifPresent(device -> onRegistrationSuccess(clientId, identifier, device.getDeviceSecret(), AuthType.REGISTER));
    }

    /**
     * 注册成功操作。注册成功后返回具体设备信息。
     *
     * @param clientId     设备 ClientId
     * @param productKey   物联网产品 ProductKey
     * @param deviceName   物联网设备 DeviceName
     * @param deviceSecret 设备密钥
     * @param authType     Mqtt 动态注册类型 {@link AuthType}。不同注册方式结果信息发送到不同主题
     */
    private void onRegistrationSuccess(String clientId, String productKey, String deviceName, String deviceSecret, AuthType authType) {
        identifierManager.performMqttIdentification(clientId);
        MqttRegistrationResponse mqttRegistrationResponse = new MqttRegistrationResponse(productKey, deviceName, deviceSecret);
        log.debug("[ThingsBrain] |- [MQTT-REGISTRATION] Device [{}] identify successfully.", clientId);
        ServiceContextHolder.publishEvent(new MqttRegistrationResponseEvent(JacksonUtils.toJson(mqttRegistrationResponse), authType.getValue()));
    }

    /**
     * 注册成功操作。注册成功后返回具体设备信息。
     *
     * @param clientId     设备 ClientId
     * @param identifier   设备标识符 {@link Identifier}
     * @param deviceSecret 设备密钥
     * @param authType     Mqtt 动态注册类型 {@link AuthType}。不同注册方式结果信息发送到不同主题
     */
    private void onRegistrationSuccess(String clientId, Identifier identifier, String deviceSecret, AuthType authType) {
        onRegistrationSuccess(clientId, identifier.getProductKey(), identifier.getDeviceName(), deviceSecret, authType);
    }

    /**
     * 单独提取一个方法，以便兼容响应式 OAuth2 客户端动态注册
     *
     * @param result OAuth2 客户端动态注册结果 {@link OAuth2ClientRegistration}
     */
    public void onRegistrationSuccess(OAuth2ClientRegistration result) {
        if (ObjectUtils.isNotEmpty(result)) {
            onRegistrationSuccess(result.getClientId(), result.getProductKey(), result.getClientName(), result.getClientSecret(), AuthType.REGNWL);
        }
    }
}
