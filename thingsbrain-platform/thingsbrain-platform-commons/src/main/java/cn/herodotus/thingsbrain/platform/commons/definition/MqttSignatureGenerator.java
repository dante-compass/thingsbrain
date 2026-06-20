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

package cn.herodotus.thingsbrain.platform.commons.definition;

import cn.herodotus.dante.core.enums.SignatureMethod;
import cn.herodotus.thingsbrain.platform.commons.domain.MqttClientIdFactory;
import cn.herodotus.thingsbrain.platform.commons.domain.SignatureGenerationResult;

/**
 * <p>Description: Mqtt 签名认证处理器 </p>
 * <p>
 * 目前主要用于 Emqx HTTP API 认证 处理。使用 HTTP API 方式主要为了实现类似于阿里云物联网的签名方式认证，以提升整体登录的安全性
 * 主要处理：
 * 1. 客户端正常登录认证
 * 2. 客户端动态注册
 * <p>
 * 如果使用数据库模式的认证方式，将会涉及密码传输的问题。这个问题待 PKI 功能完善之后再统一考虑。
 *
 * @author : gengwei.zheng
 * @date : 2025/9/25 22:26
 */
public interface MqttSignatureGenerator {

    /**
     * 生成客户端登录签名
     *
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param key        签名密钥（根据不同场景使用 ProductSecret 或者 DeviceSecret）
     * @param factory    Mqtt ClientId 工厂 {@link MqttClientIdFactory}
     * @return 生成签名 {@link SignatureGenerationResult}
     */
    SignatureGenerationResult process(String productKey, String deviceName, String key, MqttClientIdFactory factory);

    /**
     * 指定 clientId，并生成使用默认配置的签名
     *
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param clientId   自定义客户端 ID
     * @param key        签名密钥（根据不同场景使用 ProductSecret 或者 DeviceSecret）
     * @return 生成签名 {@link SignatureGenerationResult}
     */
    default SignatureGenerationResult process(String productKey, String deviceName, String clientId, String key) {
        MqttClientIdFactory factory = MqttClientIdFactory.with(clientId)
                .secureMode(2)
                .signMethod(SignatureMethod.HMAC_SHA256)
                .timestamp()
                .build();
        return process(productKey, deviceName, key, factory);
    }

    /**
     * 生成使用默认配置的签名。clientId 使用 ProductKey 和 DeviceName 生成，格式为
     *
     * @param productKey 物联网产品 ProductKey
     * @param deviceName 物联网设备 DeviceName
     * @param key        签名密钥（根据不同场景使用 ProductSecret 或者 DeviceSecret）
     * @return 生成签名 {@link SignatureGenerationResult}
     */
    default SignatureGenerationResult process(String productKey, String deviceName, String key) {
        MqttClientIdFactory factory = MqttClientIdFactory.with(productKey, deviceName)
                .secureMode(2)
                .signMethod(SignatureMethod.HMAC_SHA256)
                .timestamp()
                .build();
        return process(productKey, deviceName, key, factory);
    }
}
