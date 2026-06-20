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
import cn.herodotus.dante.security.domain.RegisteredClientTransmitter;
import cn.herodotus.thingsbrain.persistence.commons.domain.Device;
import cn.herodotus.thingsbrain.persistence.commons.domain.Product;
import cn.herodotus.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

/**
 * <p>Description: 设备注册同步信息通用监听 </p>
 * <p>
 * 设备客户端动态注册成功之后，返回客户端相关信息，将其同步至 {@link Device} 方便管理
 *
 * @author : gengwei.zheng
 * @date : 2024/10/16 12:35
 */
abstract class AbstractOAuth2ClientRegistrationSuccessListener {

    private static final Logger log = LoggerFactory.getLogger(AbstractOAuth2ClientRegistrationSuccessListener.class);

    private final IdentifierManager identifierManager;

    protected AbstractOAuth2ClientRegistrationSuccessListener(ObjectProvider<IdentifierManager> identifierManagerProvider) {
        this.identifierManager = identifierManagerProvider.getIfAvailable();
    }

    protected void process(RegisteredClientTransmitter registeredClientTransmitter) {

        log.debug("[ThingsBrain] |- [OAUTH2-CLIENT-REGISTRATION] OAuth2 client registration process BEGIN!");

        Optional.ofNullable(registeredClientTransmitter)
                .filter(transmitter -> StringUtils.isNotBlank(transmitter.getParentClientId()))
                .map(RegisteredClientTransmitter::getParentClientId)
                .flatMap(identifierManager::findProductByProductKey)
                .map(product -> toDevice(registeredClientTransmitter, product))
                .ifPresent(identifierManager::performOAuth2Synchronization);
    }

    private Device toDevice(RegisteredClientTransmitter registeredClientTransmitter, Product product) {
        Converter<RegisteredClientTransmitter, Device> toDevice = new RegisteredClientTransmitterToDeviceConverter(product);
        return toDevice.convert(registeredClientTransmitter);
    }

    static class RegisteredClientTransmitterToDeviceConverter implements Converter<RegisteredClientTransmitter, Device> {

        private final Product product;

        public RegisteredClientTransmitterToDeviceConverter(Product product) {
            this.product = product;
        }

        @Override
        public Device convert(RegisteredClientTransmitter source) {

            Device target = new Device();
            target.setId(source.getId());
            target.setProduct(product);
            target.setDeviceName(source.getClientName());
            target.setDeviceSecret(source.getClientSecret());
            target.setClientId(source.getClientId());
            target.setRedirectUris(source.getRedirectUris());

            return target;
        }
    }
}
