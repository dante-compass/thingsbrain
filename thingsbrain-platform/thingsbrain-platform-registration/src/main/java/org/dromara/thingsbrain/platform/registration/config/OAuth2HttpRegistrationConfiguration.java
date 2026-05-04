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
import org.dromara.dante.spring.condition.ConditionalOnArchitecture;
import org.dromara.dante.spring.enums.Architecture;
import org.dromara.thingsbrain.persistence.commons.manager.IdentifierManager;
import org.dromara.thingsbrain.platform.registration.http.LocalOAuth2ClientRegistrationSuccessListener;
import org.dromara.thingsbrain.platform.registration.http.LocalOAuth2DeviceVerificationSuccessListener;
import org.dromara.thingsbrain.platform.registration.http.RemoteOAuth2ClientRegistrationSuccessListener;
import org.dromara.thingsbrain.platform.registration.http.RemoteOAuth2DeviceVerificationSuccessListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: ThingsBrain 物联网平台 OAuth2 信息同步配置 </p>
 * <p>
 * 基于 OAuth2 的客户端动态注册和设备码验证操作完成之后，将操作结果数据传输回“使用端”。当前的“使用端”为 ThingsBrain 物联网平台。
 * <p>
 * 以 OAuth2 客户端动态注册为例：
 * <p>
 * 一 设计思路：
 * OAuth2 客户端动态注册默认是在 oauth2_registered_client 表中增加 OAuth2 Client 信息。在 Dante Cloud 的设计中，oauth2_registered_client 表结构完全沿用 Spring Authorization Server 标准设计未做任何扩展，以此来保证 OAuth2 认证授权功能的稳定。
 * 在需要扩展的模块中，采用的是单独创建一个业务表来存储和扩展信息。实际应用时先将数据存储至业务表中，然后再将相关信息同步至 oauth2_registered_client 表中。
 * 如果使用了 OAuth2 客户端动态注册，情况正好相反，会先在 oauth2_registered_client 表中生成一条数据，然后再反向同步至业务表中。
 * 例如：OAuth2Application，Product，Device 均是如此设计。
 * <p>
 * 二 存在问题：
 * 按照前面的设计思路来实现，既实现了 OAuth2 Client 扩展信息的存储，又保证了 oauth2_registered_client 表的标准性。当然也存在一些不足：
 * 1. 如果使用 OAuth2 客户端动态注册，那么逻辑正好相反。动态注册成功，数据库中只会创建一条 oauth2_registered_client 数据。那么就需要反向增加一条业务数据，否则业务数据就会不完整。
 * 2. 为了支持微服务架构服务的拆分，oauth2_registered_client 和 Product、Device 会在不同的服务中，因此业务数据和 oauth2_registered_client 是无法采用“同步”方式存储
 * <p>
 * 三 解决思路
 * 1. 创建  OAuth2Application，Product，Device 等功能，目前分析并不是大并发操作，因此除非是在操作过程中出现系统异常，否则异步操作是可以满足需要的。
 * 2. 可以根据一致性需要，在后续增加最终一致性设计。因为会增加实现复杂度，目前暂时不考虑增加
 *
 * @author : gengwei.zheng
 * @date : 2025/10/5 23:27
 */
@Configuration(proxyBeanMethods = false)
class OAuth2HttpRegistrationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OAuth2HttpRegistrationConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[ThingsBrain] |- Module [Platform OAuth2] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnArchitecture(Architecture.MONOLITH)
    static class LocalMessageListenerConfiguration {

        @Bean
        public LocalOAuth2ClientRegistrationSuccessListener localOAuth2ClientRegistrationSuccessListener(ObjectProvider<IdentifierManager> identifierManagerProvider) {
            LocalOAuth2ClientRegistrationSuccessListener listener = new LocalOAuth2ClientRegistrationSuccessListener(identifierManagerProvider);
            log.trace("[ThingsBrain] |- Bean [Local OAuth2 Client Registration Success Listener] Configure.");
            return listener;
        }

        @Bean
        public LocalOAuth2DeviceVerificationSuccessListener localOAuth2DeviceVerificationSuccessListener(ObjectProvider<IdentifierManager> identifierManagerProvider) {
            LocalOAuth2DeviceVerificationSuccessListener listener = new LocalOAuth2DeviceVerificationSuccessListener(identifierManagerProvider);
            log.trace("[ThingsBrain] |- Bean [Local OAuth2 Device Verification Success Listener] Configure.");
            return listener;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnArchitecture(Architecture.DISTRIBUTED)
    static class RemoteMessageListenerConfiguration {

        @Bean
        public RemoteOAuth2ClientRegistrationSuccessListener remoteOAuth2ClientRegistrationSuccessListener(ObjectProvider<IdentifierManager> identifierManagerProvider) {
            RemoteOAuth2ClientRegistrationSuccessListener listener = new RemoteOAuth2ClientRegistrationSuccessListener(identifierManagerProvider);
            log.trace("[ThingsBrain] |- Bean [Remote Oidc Client Registration Listener] Configure.");
            return listener;
        }

        @Bean
        public RemoteOAuth2DeviceVerificationSuccessListener remoteOAuth2DeviceVerificationSuccessListener(ObjectProvider<IdentifierManager> identifierManagerProvider) {
            RemoteOAuth2DeviceVerificationSuccessListener listener = new RemoteOAuth2DeviceVerificationSuccessListener(identifierManagerProvider);
            log.trace("[ThingsBrain] |- Bean [Remote OAuth2 OAuth2 Device Verification Success Listener] Configure.");
            return listener;
        }
    }
}
