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

import org.dromara.dante.core.utils.SignatureUtils;
import cn.herodotus.thingsbrain.kernel.commons.utils.DataFormatUtils;
import org.dromara.thingsbrain.platform.authentication.utils.MqttSignatureContentUtils;
import org.dromara.thingsbrain.platform.commons.definition.MqttSignatureGenerator;
import org.dromara.thingsbrain.platform.commons.domain.MqttClientIdFactory;
import org.dromara.thingsbrain.platform.commons.domain.SignatureGenerationResult;

import java.util.Map;

/**
 * <p>Description: Mqtt 签名内容生成器 </p>
 *
 * @author : gengwei_zheng
 * @date : 2026/5/27 21:54
 */
public class DefaultMqttSignatureGenerator implements MqttSignatureGenerator {

    @Override
    public SignatureGenerationResult process(String productKey, String deviceName, String key, MqttClientIdFactory factory) {
        Map<String, String> contents = MqttSignatureContentUtils.content(productKey, deviceName, factory);

        SignatureGenerationResult result = new SignatureGenerationResult();
        result.setMqttClientId(factory.getClientId());
        result.setMqttUsername(DataFormatUtils.toMqttUsername(productKey, deviceName));
        result.setMqttPassword(SignatureUtils.generate(key, factory.getSignMethod(), contents));

        return result;
    }
}
