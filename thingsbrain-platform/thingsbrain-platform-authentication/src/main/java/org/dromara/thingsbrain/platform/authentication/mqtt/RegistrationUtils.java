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

import org.dromara.thingsbrain.platform.authentication.definition.domain.OAuth2ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.List;

/**
 * <p>Description: 客户端注册工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/7/4 23:38
 */
public class RegistrationUtils {

    public static OAuth2ClientRegistration create(String productKey, String deviceName) {
        OAuth2ClientRegistration registration = new OAuth2ClientRegistration();
        registration.setProductKey(productKey);
        registration.setGrantTypes(List.of(AuthorizationGrantType.DEVICE_CODE.getValue(), AuthorizationGrantType.CLIENT_CREDENTIALS.getValue()));
        registration.setScope("openid email profile");
        registration.setClientName(deviceName);
        registration.setTokenEndpointAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue());
        registration.setRedirectUris(List.of("http://192.168.101.10:3000"));
        return registration;
    }
}
