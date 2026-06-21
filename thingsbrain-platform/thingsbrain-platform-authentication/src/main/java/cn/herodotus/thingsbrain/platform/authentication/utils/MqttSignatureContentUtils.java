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

package cn.herodotus.thingsbrain.platform.authentication.utils;

import cn.herodotus.thingsbrain.kernel.commons.constant.KernelConstants;
import cn.herodotus.thingsbrain.kernel.commons.domain.Identifier;
import cn.herodotus.thingsbrain.platform.commons.domain.MqttClientIdFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: Mqtt 前面个内容生产工具类 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/27 21:56
 */
public class MqttSignatureContentUtils {

    /**
     * 使用 {@link Identifier} 和 {@link MqttClientIdFactory} 内容生成签名内容
     *
     * @param identifier 设备标识符 {@link Identifier}
     * @param factory    Mqtt 客户端 ID 工厂 {@link MqttClientIdFactory}
     * @return 签名内容 Map
     */
    public static Map<String, String> content(Identifier identifier, MqttClientIdFactory factory) {
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
    public static Map<String, String> content(String productKey, String deviceName, MqttClientIdFactory factory) {
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
    public static Map<String, String> content(String productKey, String deviceName, String clientId, String timestamp, String random) {
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
