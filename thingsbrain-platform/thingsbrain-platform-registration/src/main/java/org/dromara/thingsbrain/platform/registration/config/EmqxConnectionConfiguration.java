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

package org.dromara.thingsbrain.platform.registration.config;

import jakarta.annotation.PostConstruct;
import org.dromara.dante.message.emqx.condition.ConditionalOnEventSource;
import org.dromara.dante.message.emqx.condition.EventSource;
import org.dromara.thingsbrain.persistence.commons.manager.ConnectionManager;
import org.dromara.thingsbrain.platform.registration.connection.EmqxSystemClientConnectedListener;
import org.dromara.thingsbrain.platform.registration.connection.EmqxSystemClientDisconnectedListener;
import org.dromara.thingsbrain.platform.registration.connection.EmqxWebhookClientConnectedListener;
import org.dromara.thingsbrain.platform.registration.connection.EmqxWebhookClientDisconnectedListener;
import org.dromara.thingsbrain.platform.registration.definition.MqttDynamicRegistrationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: Emqx 上下线扩展配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/5 17:39
 */
@Configuration(proxyBeanMethods = false)
class EmqxConnectionConfiguration {

    private static final Logger log = LoggerFactory.getLogger(EmqxConnectionConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Emqx Connection] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnEventSource(EventSource.WEBHOOK)
    static class EmqxWebhookConfiguration {

        @Bean
        public EmqxWebhookClientConnectedListener emqxWebhookClientConnectedListener(ObjectProvider<ConnectionManager> connectionManagerProvider, MqttDynamicRegistrationProcessor emqxDynamicRegistrationHandler) {
            EmqxWebhookClientConnectedListener listener = new EmqxWebhookClientConnectedListener(connectionManagerProvider, emqxDynamicRegistrationHandler);
            log.trace("[ThingsBrain] |- Bean [Emqx Webhook Client Connected Listener] Configure.");
            return listener;
        }

        @Bean
        public EmqxWebhookClientDisconnectedListener emqxWebhookClientDisconnectedListener(ObjectProvider<ConnectionManager> connectionManagerProvider) {
            EmqxWebhookClientDisconnectedListener listener = new EmqxWebhookClientDisconnectedListener(connectionManagerProvider);
            log.trace("[ThingsBrain] |- Bean [Emqx Webhook Client Disconnected Listener] Configure.");
            return listener;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnEventSource(EventSource.SYS_TOPIC)
    static class EmqxTopicConfiguration {

        @Bean
        public EmqxSystemClientConnectedListener emqxSystemClientConnectedListener(ObjectProvider<ConnectionManager> connectionManagerProvider, MqttDynamicRegistrationProcessor emqxDynamicRegistrationHandler) {
            EmqxSystemClientConnectedListener listener = new EmqxSystemClientConnectedListener(connectionManagerProvider, emqxDynamicRegistrationHandler);
            log.trace("[ThingsBrain] |- Bean [Emqx System Client Connected Listener] Configure.");
            return listener;
        }

        @Bean
        public EmqxSystemClientDisconnectedListener emqxSystemClientDisconnectedListener(ObjectProvider<ConnectionManager> connectionManagerProvider) {
            EmqxSystemClientDisconnectedListener listener = new EmqxSystemClientDisconnectedListener(connectionManagerProvider);
            log.trace("[ThingsBrain] |- Bean [Emqx System Client Disconnected Listener] Configure.");
            return listener;
        }
    }
}
