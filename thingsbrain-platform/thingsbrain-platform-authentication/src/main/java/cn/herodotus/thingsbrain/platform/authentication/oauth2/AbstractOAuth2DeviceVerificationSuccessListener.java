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

package cn.herodotus.thingsbrain.platform.authentication.oauth2;

import org.apache.commons.lang3.StringUtils;
import org.dromara.dante.security.domain.DeviceVerificationTransmitter;
import cn.herodotus.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Optional;

/**
 * <p>Description: 设备验证成功事件通用监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/2/28 22:25
 */
abstract class AbstractOAuth2DeviceVerificationSuccessListener {

    private static final Logger log = LoggerFactory.getLogger(AbstractOAuth2DeviceVerificationSuccessListener.class);

    private final IdentifierManager identifierManager;

    protected AbstractOAuth2DeviceVerificationSuccessListener(ObjectProvider<IdentifierManager> identifierManagerProvider) {
        this.identifierManager = identifierManagerProvider.getIfAvailable();
    }

    protected void process(DeviceVerificationTransmitter deviceVerificationTransmitter) {

        log.debug("[ThingsBrain] |- [OAUTH2-DEVICE-VERIFICATION] Device verification process BEGIN!");

        Optional.ofNullable(deviceVerificationTransmitter)
                .filter(iotDeviceTransmitter -> StringUtils.isNotBlank(iotDeviceTransmitter.getClientId()))
                .map(DeviceVerificationTransmitter::getClientId)
                .ifPresent(identifierManager::performOAuth2Verification);
    }
}
