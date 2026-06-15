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

package org.dromara.thingsbrain.platform.authentication.oauth2;

import org.dromara.dante.core.constant.SystemConstants;
import org.dromara.dante.core.utils.AccessTokenUtils;
import org.dromara.dante.spring.context.ServiceContextHolder;
import org.dromara.thingsbrain.platform.authentication.domain.ClientCredentialsAuthenticationToken;
import org.dromara.thingsbrain.platform.authentication.domain.OAuth2ClientRegistration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Optional;

/**
 * <p>Description: 响应式 Oidc 客户端动态注册处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/30 12:17
 */
public class ServletLocalOAuth2ClientRegistrationHandler {

    private final RestClient restClient;

    public ServletLocalOAuth2ClientRegistrationHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    private RestClient getRestClient() {
        return this.restClient.mutate()
                .baseUrl(ServiceContextHolder.getUaaServiceUri())
                .build();
    }

    private ClientCredentialsAuthenticationToken authenticate(String clientId, String clientSecret) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
        params.add(OAuth2ParameterNames.SCOPE, SystemConstants.SCOPE_CLIENT_CREATE);

        return this.getRestClient()
                .post()
                .uri(ServiceContextHolder.getAccessTokenEndpoint())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, AccessTokenUtils.basic(clientId, clientSecret))
                .body(params)
                .retrieve()
                .body(ClientCredentialsAuthenticationToken.class);
    }

    /**
     * OAuth2 客户端注册。
     *
     * @param token                    AccessToken 使用“父”客户端信息获取
     * @param OAuth2ClientRegistration 客户端注册请求信息
     * @return 客户端注册响应信息 {@link OAuth2ClientRegistration}
     */
    private OAuth2ClientRegistration register(String token, OAuth2ClientRegistration OAuth2ClientRegistration) {
        return this.getRestClient()
                .post()
                .uri(ServiceContextHolder.getClientRegistrationEndpoint())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, AccessTokenUtils.bearer(token))
                .body(OAuth2ClientRegistration)
                .retrieve()
                .body(OAuth2ClientRegistration.class);
    }

    /**
     * OAuth2 客户端注册。
     *
     * @param parentClientId           OAuth2 ClientId（“父”客户端 ID）
     * @param parentClientSecret       OAuth2 ClientSecret（“父”客户端密钥）
     * @param OAuth2ClientRegistration 客户端注册请求信息
     * @return 客户端注册响应信息 {@link OAuth2ClientRegistration}
     */
    public OAuth2ClientRegistration register(String parentClientId, String parentClientSecret, OAuth2ClientRegistration OAuth2ClientRegistration) {
        return Optional.ofNullable(authenticate(parentClientId, parentClientSecret))
                .map(ClientCredentialsAuthenticationToken::accessToken)
                .map(token -> register(token, OAuth2ClientRegistration)).orElse(null);
    }

    /**
     * 基于物联网的 OAuth2 客户端动态注册
     *
     * @param clientId      待注册客户端 ClientId
     * @param deviceName    待注册客户端 ClientName
     * @param productKey    物联网产品 ProductKey“父”客户端 ID）
     * @param productSecret 物联网产品 ProductSecret（“父”客户端密钥）
     * @return 客户端注册响应信息 {@link OAuth2ClientRegistration}
     */
    public OAuth2ClientRegistration register(String clientId, String deviceName, String productKey, String productSecret) {
        return register(productKey, productSecret, RegistrationUtils.create(clientId, productKey, deviceName));
    }

    public OAuth2ClientRegistration query(String registrationAccessToken, String registrationClientUri) {
        return this.restClient.mutate().build()
                .get()
                .uri(registrationClientUri)
                .header(HttpHeaders.AUTHORIZATION, AccessTokenUtils.bearer(registrationAccessToken))
                .retrieve()
                .body(OAuth2ClientRegistration.class);
    }
}
