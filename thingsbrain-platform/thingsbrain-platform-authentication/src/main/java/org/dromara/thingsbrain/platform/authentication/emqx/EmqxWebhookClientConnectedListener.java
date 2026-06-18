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

package org.dromara.thingsbrain.platform.authentication.emqx;

import org.dromara.dante.message.emqx.domain.WebhookClientConnected;
import org.dromara.dante.message.emqx.event.WebhookClientConnectedEvent;
import cn.herodotus.thingsbrain.kernel.commons.domain.AddressTuple;
import cn.herodotus.thingsbrain.kernel.commons.domain.Identifier;
import cn.herodotus.thingsbrain.kernel.commons.utils.DataFormatUtils;
import org.dromara.thingsbrain.persistence.commons.domain.DeviceConnection;
import org.dromara.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.dromara.thingsbrain.platform.authentication.mqtt.MqttIdentificationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

/**
 * <p>Description: 物联网设备上线监听 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/10/11 22:14
 */
public class EmqxWebhookClientConnectedListener extends AbstractEmqxConnectedListener<WebhookClientConnected, WebhookClientConnectedEvent> {

    private static final Logger log = LoggerFactory.getLogger(EmqxWebhookClientConnectedListener.class);

    public EmqxWebhookClientConnectedListener(IdentifierManager identifierManager, MqttIdentificationHandler mqttIdentificationHandler) {
        super(identifierManager, mqttIdentificationHandler, new WebhookConnectedToDeviceConnectionConverter());
    }

    @Override
    public void onApplicationEvent(WebhookClientConnectedEvent event) {

        log.debug("[ThingsBrain] |- Emqx WEBHOOK [CONNECTED] listener, response event!");

        WebhookClientConnected data = event.getData();

        connected(data);
    }

    static class WebhookConnectedToDeviceConnectionConverter implements Converter<WebhookClientConnected, DeviceConnection> {

        @Override
        public DeviceConnection convert(WebhookClientConnected source) {
            DeviceConnection target = new DeviceConnection();
            target.setClientId(source.getClientId());
            target.setUsername(source.getUsername());
            target.setProtocolName(source.getProtocolName());
            target.setProtocolVersion(source.getProtocolVersion());
            target.setKeepAlive(source.getKeepAlive());
            target.setCleanStart(source.getCleanStart());
            target.setExpiryInterval(source.getExpiryInterval());
            target.setConnected(true);
            target.setConnectedAt(source.getConnectedAt());

            Optional<Identifier> deviceTuple = DataFormatUtils.fromMqttUsername(source.getUsername());
            deviceTuple.ifPresent(target::setUsername);

            Optional<AddressTuple> mqttSockTuple = DataFormatUtils.fromMqttSockName(source.getUsername());
            mqttSockTuple.ifPresent(target::setSockName);

            return target;
        }
    }
}
