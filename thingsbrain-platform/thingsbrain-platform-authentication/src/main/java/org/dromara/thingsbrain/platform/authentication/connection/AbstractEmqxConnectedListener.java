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

package org.dromara.thingsbrain.platform.authentication.connection;

import org.apache.commons.lang3.ObjectUtils;
import org.dromara.dante.message.emqx.definition.domain.AbstractEmqxDomain;
import org.dromara.thingsbrain.kernel.commons.domain.MqttClientIdFactory;
import org.dromara.thingsbrain.persistence.commons.domain.DeviceConnection;
import org.dromara.thingsbrain.persistence.commons.manager.ConnectionManager;
import org.dromara.thingsbrain.platform.authentication.definition.MqttDynamicRegistrationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: Emqx Client 上线通用代码提取抽象类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/7/2 14:50
 */
abstract class AbstractEmqxConnectedListener<D extends AbstractEmqxDomain, E extends ApplicationEvent> implements ApplicationListener<E> {

    private static final Logger log = LoggerFactory.getLogger(AbstractEmqxConnectedListener.class);

    private final ConnectionManager connectionManager;
    private final Converter<D, DeviceConnection> toDeviceConnection;
    private final MqttDynamicRegistrationProcessor mqttDynamicRegistrationProcessor;

    protected AbstractEmqxConnectedListener(ObjectProvider<ConnectionManager> connectionManagerProvider, Converter<D, DeviceConnection> toDeviceConnection, MqttDynamicRegistrationProcessor mqttDynamicRegistrationProcessor) {
        this.connectionManager = connectionManagerProvider.getIfAvailable();
        this.toDeviceConnection = toDeviceConnection;
        this.mqttDynamicRegistrationProcessor = mqttDynamicRegistrationProcessor;
    }

    protected void connected(D data) {
        String mqttClientId = data.getClientId();
        String mqttUsername = data.getUsername();

        log.debug("[ThingsBrain] |- Mqtt client [{}] connected.", mqttClientId);

        MqttClientIdFactory factory = MqttClientIdFactory.of(mqttClientId).parse();
        // 如果 mqttClientId 中包含 authType，则认为是 mqtt 动态注册
        if (ObjectUtils.isNotEmpty(factory.getAuthType())) {
            log.info("[ThingsBrain] |- [MQTT-REGISTRATION] Is mqtt registration!!!");
            mqttDynamicRegistrationProcessor.registration(factory, mqttUsername);
        } else {
            DeviceConnection deviceConnection = toDeviceConnection.convert(data);
            connectionManager.connected(factory.getClientId(), factory.getSignature(), deviceConnection);
        }
    }
}
