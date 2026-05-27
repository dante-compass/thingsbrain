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
import org.dromara.thingsbrain.platform.commons.domain.MqttClientIdFactory;
import org.dromara.thingsbrain.kernel.commons.enums.AuthType;
import org.dromara.thingsbrain.kernel.commons.event.MqttRegistrationSuccessEvent;
import org.dromara.thingsbrain.kernel.commons.utils.DataFormatUtils;
import org.dromara.thingsbrain.persistence.commons.domain.Device;
import org.dromara.thingsbrain.persistence.commons.domain.Product;
import org.dromara.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.dromara.thingsbrain.platform.authentication.definition.MqttDynamicRegistrationProcessor;
import org.dromara.thingsbrain.platform.authentication.definition.domain.OAuth2ClientRegistration;
import org.dromara.thingsbrain.platform.authentication.definition.domain.RegisterDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Optional;

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
public abstract class AbstractEmqxDynamicRegistrationHandler implements MqttDynamicRegistrationProcessor {

    private static final Logger log = LoggerFactory.getLogger(AbstractEmqxDynamicRegistrationHandler.class);

    private final IdentifierManager identifierManager;

    protected AbstractEmqxDynamicRegistrationHandler(ObjectProvider<IdentifierManager> identifierManagerProvider) {
        this.identifierManager = identifierManagerProvider.getIfAvailable();
    }

    @Override
    public void registration(MqttClientIdFactory factory, String mqttUsername) {
        if (factory.getAuthType() == AuthType.REGISTER) {
            success(factory.getClientId(), AuthType.REGISTER);
        } else {
            DataFormatUtils.fromMqttUsername(mqttUsername).ifPresent(identifier -> registration(identifier.getProductKey(), identifier.getDeviceName()));
        }
    }

    /**
     * 一型一密认证方式，不同类型将返回不同的认证参数：
     * · register：一型一密预注册认证方式，返回DeviceSecret。
     * · regnwl：一型一密免预注册认证方式，返回DeviceToken、ClientID。
     * <p>
     * 当前方法为：regnwl，一型一密免预注册认证方式。
     * <p>
     * 采用 OAuth2 DeviceCode 模式注册，然后返回 DeviceToken、ClientID
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     */
    private void registration(String productKey, String deviceName) {
        Optional<Product> optional = identifierManager.findProductByProductKey(productKey);
        optional.ifPresent(product -> {
            registration(product.getProductKey(), product.getProductSecret(), productKey, deviceName);
        });
    }

    /**
     * regnwl，一型一密免预注册认证方式定义。
     * <p>
     * 定义为抽象方法，以便支持 Reactive 和 Servlet 不同环境的实现。
     *
     * @param clientId     OAuth2 ClientId（“父”客户端 ID）
     * @param clientSecret OAuth2 ClientSecret（“父”客户端密钥）
     * @param productKey   物联网产品 ProductKey
     * @param deviceName   物联网设备 DeviceName
     */
    protected abstract void registration(String clientId, String clientSecret, String productKey, String deviceName);

    /**
     * regnwl，一型一密免预注册认证方式注册成功的后续操作
     *
     * @param OAuth2ClientRegistration 注册成功信息 {@link OAuth2ClientRegistration}
     */
    protected void success(OAuth2ClientRegistration OAuth2ClientRegistration) {
        if (ObjectUtils.isNotEmpty(OAuth2ClientRegistration)) {
            success(OAuth2ClientRegistration.getClientId(), AuthType.REGNWL);
        }
    }

    /**
     * 注册成功后返回内容
     *
     * @param clientId 客户端ID
     * @param authType 认证类型 {@link AuthType}
     */
    private void success(String clientId, AuthType authType) {
        Optional<Device> optional = identifierManager.findDeviceByClientId(clientId);
        optional.ifPresent(device -> {
            RegisterDomain registerDomain = new RegisterDomain(device.getProduct().getProductKey(), device.getDeviceName(), device.getDeviceSecret());
            log.debug("[ThingsBrain] |- [MQTT-REGISTRATION] Device [{}] successfully activated.", clientId);
            ServiceContextHolder.publishEvent(new MqttRegistrationSuccessEvent(JacksonUtils.toJson(registerDomain), authType.getValue()));
        });

    }
}
