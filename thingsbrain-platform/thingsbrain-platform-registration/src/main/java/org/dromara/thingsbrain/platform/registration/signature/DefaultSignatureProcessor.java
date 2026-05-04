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

package org.dromara.thingsbrain.platform.registration.signature;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.dante.core.domain.SignatureValidationResult;
import org.dromara.dante.core.utils.SignatureUtils;
import org.dromara.dante.web.definition.SignatureValidator;
import org.dromara.thingsbrain.kernel.commons.constant.KernelConstants;
import org.dromara.thingsbrain.kernel.commons.definition.SignatureProcessor;
import org.dromara.thingsbrain.kernel.commons.domain.Identifier;
import org.dromara.thingsbrain.kernel.commons.domain.MqttClientIdFactory;
import org.dromara.thingsbrain.kernel.commons.domain.SignatureAuthenticationResult;
import org.dromara.thingsbrain.kernel.commons.domain.SignatureGenerationResult;
import org.dromara.thingsbrain.kernel.commons.enums.AuthType;
import org.dromara.thingsbrain.kernel.commons.utils.DataFormatUtils;
import org.dromara.thingsbrain.persistence.commons.domain.Device;
import org.dromara.thingsbrain.persistence.commons.domain.Product;
import org.dromara.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Description: 默认 Mqtt 签名认证处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/9/25 22:35
 */
public class DefaultSignatureProcessor implements SignatureProcessor {

    private static final Logger log = LoggerFactory.getLogger(DefaultSignatureProcessor.class);

    private final SignatureValidator signatureValidator;
    private final IdentifierManager identifierManager;

    public DefaultSignatureProcessor(ObjectProvider<IdentifierManager> identifierManagerProvider, SignatureValidator signatureValidator) {
        this.identifierManager = identifierManagerProvider.getIfAvailable();
        this.signatureValidator = signatureValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SignatureAuthenticationResult authentication(String mqttClientId, String mqttUsername, String mqttPassword) {

        MqttClientIdFactory id = MqttClientIdFactory.of(mqttClientId).parse();

        // 如果 mqttClientId 不是 client + |xxxx| 格式，即不包含参数，则是使用其它途径进行认证
        if (!id.getSignature()) {
            log.warn("[ThingsBrain] |- Webhook authentication is not applicable to this client [{}].", mqttClientId);
            return SignatureAuthenticationResult.ignore("Webhook authentication is not applicable to this client!");
        }

        return DataFormatUtils.fromMqttUsername(mqttUsername)
                .map(identifier -> authentication(identifier, id, mqttPassword))
                .orElse(SignatureAuthenticationResult.deny("Mqtt username format error."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SignatureGenerationResult generation(String productKey, String deviceName, String key, MqttClientIdFactory factory) {
        Map<String, String> contents = content(productKey, deviceName, factory);

        SignatureGenerationResult result = new SignatureGenerationResult();
        result.setMqttClientId(factory.getClientId());
        result.setMqttUsername(DataFormatUtils.toMqttUsername(productKey, deviceName));
        result.setMqttPassword(SignatureUtils.generate(key, factory.getSignMethod(), contents));

        return result;
    }

    /**
     * 签名认证。
     *
     * @param identifier   物联网设备标识符 {@link Identifier}
     * @param factory      MqttClientId 工厂 {@link MqttClientIdFactory}
     * @param mqttPassword Mqtt 链接密码
     * @return 签名认证结果 {@link SignatureAuthenticationResult}
     */
    private SignatureAuthenticationResult authentication(Identifier identifier, MqttClientIdFactory factory, String mqttPassword) {
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
                            return SignatureAuthenticationResult.deny("The dynamic registration switch is not turned on!");
                        }
                    }
                })
                .orElse(SignatureAuthenticationResult.deny("ProductKey is not correct!"));
    }

    /**
     * 签名认证。增加设备校验。
     *
     * @param identifier  物联网设备标识符 {@link Identifier}
     * @param factory     MqttClientId 工厂 {@link MqttClientIdFactory}
     * @param signature   待验证签名
     * @param forRegister false 普通认证，true 动态注册认证
     * @return 认证结果 {@link SignatureAuthenticationResult}
     */
    private SignatureAuthenticationResult authentication(Identifier identifier, MqttClientIdFactory factory, String signature, boolean forRegister) {
        Optional<Device> optional = identifierManager.findDeviceByDeviceName(identifier.getDeviceName());

        return optional
                .map(device -> {
                    String key = forRegister ? device.getProduct().getProductSecret() : device.getDeviceSecret();
                    return authentication(key, identifier, factory, signature);
                })
                .orElse(SignatureAuthenticationResult.deny("Device does " + identifier.getDeviceName() + " not exist!"));
    }

    /**
     * 签名认证
     *
     * @param key        签名密钥
     * @param identifier 物联网设备标识符 {@link Identifier}
     * @param factory    MqttClientId 工厂 {@link MqttClientIdFactory}
     * @param signature  待验证签名
     * @return 认证结果 {@link SignatureAuthenticationResult}
     */
    private SignatureAuthenticationResult authentication(String key, Identifier identifier, MqttClientIdFactory factory, String signature) {
        Map<String, String> contents = content(identifier, factory);

        SignatureValidationResult result = signatureValidator.validate(key, factory.getSignMethod(), contents, signature);
        if (result.isValid()) {
            return SignatureAuthenticationResult.allow();
        } else {
            return SignatureAuthenticationResult.deny(result.getMessage());
        }
    }

    /**
     * 使用 {@link Identifier} 和 {@link MqttClientIdFactory} 内容生成签名内容
     *
     * @param identifier 设备标识符 {@link Identifier}
     * @param factory    Mqtt 客户端 ID 工厂 {@link MqttClientIdFactory}
     * @return 签名内容 Map
     */
    private Map<String, String> content(Identifier identifier, MqttClientIdFactory factory) {
        return content(identifier.getProductKey(), identifier.getDeviceName(), factory);
    }

    /**
     * 使用 ProductKey、DeviceName 和 {@link MqttClientIdFactory} 内容生成签名内容
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param factory    Mqtt 客户端 ID 工厂 {@link MqttClientIdFactory}
     * @return 签名内容 Map
     */
    private Map<String, String> content(String productKey, String deviceName, MqttClientIdFactory factory) {
        return content(productKey, deviceName, factory.getClientId(), factory.getTimestamp(), factory.getRandom());
    }

    /**
     * 构建签名内容
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @param clientId   客户端 ID
     * @param timestamp  时间戳
     * @param random     随机数
     * @return 签名内容 Map
     */
    private Map<String, String> content(String productKey, String deviceName, String clientId, String timestamp, String random) {
        Map<String, String> contents = new HashMap<>();
        contents.put(KernelConstants.KEY__PRODUCT_KEY, productKey);
        contents.put(KernelConstants.KEY__DEVICE_NAME, deviceName);
        contents.put(KernelConstants.KEY__CLIENT_ID, clientId);

        if (StringUtils.isNotBlank(timestamp)) {
            contents.put(KernelConstants.KEY__TIMESTAMP, timestamp);
        }

        if (StringUtils.isNotBlank(random)) {
            contents.put(KernelConstants.KEY__RANDOM, random);
        }
        return contents;
    }
}
