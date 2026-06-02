/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsBrain.
 *
 * Herodotus ThingsBrain is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsBrain is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.thingsbrain.platform.authentication.emqx;

import org.dromara.dante.core.domain.SignatureValidationResult;
import org.dromara.dante.message.emqx.event.WebhookClientConnectedEvent;
import org.dromara.dante.web.definition.SignatureValidator;
import org.dromara.thingsbrain.kernel.commons.domain.Identifier;
import org.dromara.thingsbrain.kernel.commons.enums.AuthType;
import org.dromara.thingsbrain.kernel.commons.utils.DataFormatUtils;
import org.dromara.thingsbrain.persistence.commons.domain.Device;
import org.dromara.thingsbrain.persistence.commons.domain.Product;
import org.dromara.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.dromara.thingsbrain.platform.authentication.utils.MqttSignatureContentUtils;
import org.dromara.thingsbrain.platform.commons.definition.EmqxAuthenticationHandler;
import org.dromara.thingsbrain.platform.commons.domain.EmqxAuthenticationStatus;
import org.dromara.thingsbrain.platform.commons.domain.MqttClientIdFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: Emqx 认证处理器 </p>
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
 * @author : gengwei_zheng
 * @date : 2026/5/27 13:16
 */
public class DefaultEmqxAuthenticationHandler implements EmqxAuthenticationHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultEmqxAuthenticationHandler.class);

    private final IdentifierManager identifierManager;
    private final SignatureValidator signatureValidator;

    public DefaultEmqxAuthenticationHandler(IdentifierManager identifierManager, SignatureValidator signatureValidator) {
        this.identifierManager = identifierManager;
        this.signatureValidator = signatureValidator;
    }

    @Override
    public EmqxAuthenticationStatus process(String mqttClientId, String mqttUsername, String mqttPassword) {
        MqttClientIdFactory id = MqttClientIdFactory.of(mqttClientId).parse();

        // 如果 mqttClientId 不是 client + |xxxx| 格式，即不包含参数，则是使用其它途径进行认证
        if (!id.getSignature()) {
            log.warn("[ThingsBrain] |- Webhook authentication is not applicable to this client [{}].", mqttClientId);
            return EmqxAuthenticationStatus.ignore("Webhook authentication is not applicable to this client!");
        }

        return DataFormatUtils.fromMqttUsername(mqttUsername)
                .map(identifier -> authentication(identifier, id, mqttPassword))
                .orElse(EmqxAuthenticationStatus.deny("Mqtt username format error."));
    }

    /**
     * 签名认证。
     *
     * @param identifier   物联网设备标识符 {@link Identifier}
     * @param factory      MqttClientId 工厂 {@link MqttClientIdFactory}
     * @param mqttPassword Mqtt 链接密码
     * @return 签名认证结果 {@link EmqxAuthenticationStatus}
     */
    private EmqxAuthenticationStatus authentication(Identifier identifier, MqttClientIdFactory factory, String mqttPassword) {
        Optional<Product> optional = identifierManager.findProductByProductKey(identifier.getProductKey());

        // 验证 productKey 是否正确
        return optional.map(product -> {
                    // 如果 mqttClientId 不包含 authType 参数，则认为是正常的链接
                    // 适用于一机一密、一型一密预注册认证方式：使用设备证书（ProductKey、DeviceName和DeviceSecret）连接
                    if (ObjectUtils.isEmpty(factory.getAuthType())) {
                        return authentication(identifier, factory, mqttPassword, false);
                    } else {
                        // 如果 mqttClientId 包含 authType 参数，则认为是动态注册
                        // 适用一型一密免预注册认证方式
                        // 检测动态注册是否开启，手动关闭动态注册开关，则表示拒绝新设备的认证请求
                        if (product.getRegistration()) {
                            if (factory.getAuthType() == AuthType.REGISTER) {
                                return authentication(identifier, factory, mqttPassword, true);
                            } else {
                                return authentication(product.getProductSecret(), identifier, factory, mqttPassword);
                            }
                        } else {
                            return EmqxAuthenticationStatus.deny("The dynamic registration switch is not turned on!");
                        }
                    }
                })
                .orElse(EmqxAuthenticationStatus.deny("ProductKey is not correct!"));
    }

    /**
     * 签名认证。增加设备校验。
     *
     * @param identifier  物联网设备标识符 {@link Identifier}
     * @param factory     MqttClientId 工厂 {@link MqttClientIdFactory}
     * @param signature   待验证签名
     * @param forRegister false 普通认证，true 动态注册认证
     * @return 认证结果 {@link EmqxAuthenticationStatus}
     */
    private EmqxAuthenticationStatus authentication(Identifier identifier, MqttClientIdFactory factory, String signature, boolean forRegister) {
        Optional<Device> optional = identifierManager.findDeviceByDeviceName(identifier.getDeviceName());

        return optional
                .map(device -> {
                    String key = forRegister ? device.getProduct().getProductSecret() : device.getDeviceSecret();
                    return authentication(key, identifier, factory, signature);
                })
                .orElse(EmqxAuthenticationStatus.deny("Device does " + identifier.getDeviceName() + " not exist!"));
    }

    /**
     * 签名认证
     *
     * @param key        签名密钥
     * @param identifier 物联网设备标识符 {@link Identifier}
     * @param factory    MqttClientId 工厂 {@link MqttClientIdFactory}
     * @param signature  待验证签名
     * @return 认证结果 {@link EmqxAuthenticationStatus}
     */
    private EmqxAuthenticationStatus authentication(String key, Identifier identifier, MqttClientIdFactory factory, String signature) {
        Map<String, String> contents = MqttSignatureContentUtils.content(identifier, factory);

        SignatureValidationResult result = signatureValidator.validate(key, factory.getSignMethod(), contents, signature);
        if (result.isValid()) {
            return EmqxAuthenticationStatus.allow();
        } else {
            return EmqxAuthenticationStatus.deny(result.getMessage());
        }
    }
}
