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

package cn.herodotus.thingsbrain.platform.authentication.emqx;

import cn.herodotus.dante.core.domain.SignatureValidationResult;
import cn.herodotus.dante.web.definition.SignatureValidator;
import cn.herodotus.thingsbrain.kernel.commons.definition.EmqxAuthenticationHandler;
import cn.herodotus.thingsbrain.kernel.commons.domain.EmqxAuthenticationStatus;
import cn.herodotus.thingsbrain.kernel.commons.domain.Identifier;
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttClientIdFactory;
import cn.herodotus.thingsbrain.kernel.commons.enums.AuthType;
import cn.herodotus.thingsbrain.kernel.commons.utils.DataFormatUtils;
import cn.herodotus.thingsbrain.persistence.commons.domain.Device;
import cn.herodotus.thingsbrain.persistence.commons.domain.Product;
import cn.herodotus.thingsbrain.persistence.commons.manager.IdentifierManager;
import cn.herodotus.thingsbrain.platform.authentication.utils.MqttSignatureContentUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: Emqx 注册认证处理器默认实现 </p>
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

    /**
     * {@inheritDoc}
     */
    @Override
    public EmqxAuthenticationStatus process(String mqttClientId, String mqttUsername, String mqttPassword) {

        log.info("[ThingsBrain] |- PROCESSING mqtt signature authentication for client: [{}].", mqttClientId);

        MqttClientIdFactory id = MqttClientIdFactory.of(mqttClientId).parse();

        // 本认证方式仅用于 Mqtt 签名方式的认证。如果 mqttClientId 不是 client + |xxxx| 格式，即不包含参数，则是认为是非签名方式，那么只能使用其它途径进行认证
        if (!id.getSignature()) {
            log.warn("[ThingsBrain] |- Mqtt signature authentication is not applicable to this client [{}].", mqttClientId);
            return EmqxAuthenticationStatus.ignore();
        }

        return DataFormatUtils.fromMqttUsername(mqttUsername)
                .map(identifier -> authentication(identifier, id, mqttPassword))
                .orElseGet(() -> {
                    log.error("[ThingsBrain] |- Mqtt username [{}] format is incorrect.", mqttUsername);
                    return EmqxAuthenticationStatus.deny();
                });
    }

    /**
     * 签名方式客户端注册认证。
     *
     * @param identifier   物联网设备身份标识符 {@link Identifier}
     * @param factory      MqttClientId 工厂 {@link MqttClientIdFactory}
     * @param mqttPassword Mqtt 链接密码
     * @return 签名认证结果 {@link EmqxAuthenticationStatus}
     */
    private EmqxAuthenticationStatus authentication(Identifier identifier, MqttClientIdFactory factory, String mqttPassword) {
        Optional<Product> optional = identifierManager.findProductByProductKey(identifier.getProductKey());

        // 验证 productKey 是否正确
        return optional.map(product -> {
                    // 如果 mqttClientId 不包含 authType 参数，则认为是正常的链接
                    // 此种方式适用于一机一密注册认证方式：使用设备证书（ProductKey、DeviceName和DeviceSecret）连接
                    // TODO: 一机一密、一型一密预注册和免预注册，securemode 值分别为：2、2 和 -2 目前没有用到，后续根据情况添加。目前根据 AuthType 判断即可满足
                    if (ObjectUtils.isEmpty(factory.getAuthType())) {
                        return validate(identifier, factory, mqttPassword, false);
                    } else {
                        // 如果 mqttClientId 包含 authType 参数，则认为是动态注册
                        // 适用一型一密预注册和一型一密免预注册方式
                        // 此种方式会检测动态注册是否开启，手动关闭动态注册开关，则表示拒绝新设备的认证请求
                        if (product.getRegistration()) {
                            if (factory.getAuthType() == AuthType.REGISTER) {
                                return validate(identifier, factory, mqttPassword, true);
                            } else {
                                return validate(product.getProductSecret(), identifier, factory, mqttPassword);
                            }
                        } else {
                            log.warn("[ThingsBrain] |- Dynamic registration for product [{}] is not turned on!.", product.getProductKey());
                            return EmqxAuthenticationStatus.deny();
                        }
                    }
                })
                .orElseGet(() -> {
                    log.error("[ThingsBrain] |- ProductKey [{}] is incorrect or not exists！", identifier.getProductKey());
                    return EmqxAuthenticationStatus.deny();
                });
    }

    /**
     * 验证签名。增加设备校验。
     *
     * @param identifier  物联网设备标识符 {@link Identifier}
     * @param factory     MqttClientId 工厂 {@link MqttClientIdFactory}
     * @param signature   待验证签名
     * @param forRegister false 普通认证，true 动态注册认证
     * @return 认证结果 {@link EmqxAuthenticationStatus}
     */
    private EmqxAuthenticationStatus validate(Identifier identifier, MqttClientIdFactory factory, String signature, boolean forRegister) {
        // 一机一密、一型一密预注册认证方式，设备信息已经存在。
        Optional<Device> optional = identifierManager.findDeviceByClientId(factory.getClientId());

        return optional
                .map(device -> {
                    // 一机一密使用 deviceSecret 构造和验证签名；一型一密使用 productSecret 构造和验证签名
                    String key = forRegister ? device.getProduct().getProductSecret() : device.getDeviceSecret();
                    return validate(key, identifier, factory, signature);
                })
                .orElseGet(() -> {
                    log.warn("[ThingsBrain] |- Device does [{}] not exist! Must add device first.", factory.getClientId());
                    return EmqxAuthenticationStatus.deny();
                });
    }

    /**
     * 验证签名
     *
     * @param key        签名密钥
     * @param identifier 物联网设备标识符 {@link Identifier}
     * @param factory    MqttClientId 工厂 {@link MqttClientIdFactory}
     * @param signature  待验证签名
     * @return 认证结果 {@link EmqxAuthenticationStatus}
     */
    private EmqxAuthenticationStatus validate(String key, Identifier identifier, MqttClientIdFactory factory, String signature) {

        log.info("[ThingsBrain] |- PROCESSING mqtt signature validation for productKey: [{}] and deviceName: [{}].", identifier.getProductKey(), identifier.getDeviceName());

        Map<String, String> contents = MqttSignatureContentUtils.content(identifier, factory);

        SignatureValidationResult result = signatureValidator.validate(key, factory.getSignMethod(), contents, signature);
        if (result.isValid()) {
            log.info("[ThingsBrain] |- Mqtt signature is ALLOW.");
            return EmqxAuthenticationStatus.allow();
        } else {
            log.warn("[ThingsBrain] |- Mqtt signature is DENY.");
            return EmqxAuthenticationStatus.deny();
        }
    }
}
