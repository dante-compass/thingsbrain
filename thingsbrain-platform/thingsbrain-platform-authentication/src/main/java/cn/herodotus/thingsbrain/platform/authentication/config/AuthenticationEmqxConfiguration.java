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

package cn.herodotus.thingsbrain.platform.authentication.config;

import cn.herodotus.thingsbrain.platform.authentication.emqx.*;
import jakarta.annotation.PostConstruct;
import cn.herodotus.dante.message.emqx.condition.ConditionalOnEventSource;
import cn.herodotus.dante.message.emqx.condition.EventSource;
import cn.herodotus.dante.web.definition.SignatureValidator;
import cn.herodotus.thingsbrain.persistence.commons.manager.IdentifierManager;
import cn.herodotus.thingsbrain.platform.authentication.emqx.*;
import cn.herodotus.thingsbrain.platform.authentication.mqtt.MqttIdentificationHandler;
import cn.herodotus.thingsbrain.platform.commons.definition.EmqxAuthenticationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: Emqx 上下线扩展配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/5 17:39
 */
@Configuration(proxyBeanMethods = false)
class AuthenticationEmqxConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationEmqxConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Platform Authentication Emqx] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnEventSource(EventSource.WEBHOOK)
    static class EmqxWebhookConfiguration {

        @Bean
        public EmqxWebhookClientConnectedListener emqxWebhookClientConnectedListener(IdentifierManager identifierManager, MqttIdentificationHandler emqxDynamicRegistrationHandler) {
            EmqxWebhookClientConnectedListener listener = new EmqxWebhookClientConnectedListener(identifierManager, emqxDynamicRegistrationHandler);
            log.trace("[ThingsBrain] |- Bean [Emqx Webhook Client Connected Listener] Configure.");
            return listener;
        }

        @Bean
        public EmqxWebhookClientDisconnectedListener emqxWebhookClientDisconnectedListener(IdentifierManager identifierManager) {
            EmqxWebhookClientDisconnectedListener listener = new EmqxWebhookClientDisconnectedListener(identifierManager);
            log.trace("[ThingsBrain] |- Bean [Emqx Webhook Client Disconnected Listener] Configure.");
            return listener;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnEventSource(EventSource.SYS_TOPIC)
    static class EmqxTopicConfiguration {

        @Bean
        public EmqxSystemClientConnectedListener emqxSystemClientConnectedListener(IdentifierManager identifierManager, MqttIdentificationHandler emqxDynamicRegistrationHandler) {
            EmqxSystemClientConnectedListener listener = new EmqxSystemClientConnectedListener(identifierManager, emqxDynamicRegistrationHandler);
            log.trace("[ThingsBrain] |- Bean [Emqx System Client Connected Listener] Configure.");
            return listener;
        }

        @Bean
        public EmqxSystemClientDisconnectedListener emqxSystemClientDisconnectedListener(IdentifierManager identifierManager) {
            EmqxSystemClientDisconnectedListener listener = new EmqxSystemClientDisconnectedListener(identifierManager);
            log.trace("[ThingsBrain] |- Bean [Emqx System Client Disconnected Listener] Configure.");
            return listener;
        }
    }

    @Bean
    public EmqxAuthenticationHandler emqxAuthenticationHandler(IdentifierManager identifierManager, SignatureValidator signatureValidator) {
        DefaultEmqxAuthenticationHandler handler = new DefaultEmqxAuthenticationHandler(identifierManager, signatureValidator);
        log.trace("[ThingsBrain] |- Bean [Emqx Authentication Handler] Configure.");
        return handler;
    }
}
