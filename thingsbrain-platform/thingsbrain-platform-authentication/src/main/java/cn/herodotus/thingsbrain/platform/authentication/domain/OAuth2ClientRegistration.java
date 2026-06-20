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

package cn.herodotus.thingsbrain.platform.authentication.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import cn.herodotus.dante.core.constant.SystemConstants;
import cn.herodotus.dante.spring.jackson.ArrayOrStringToListDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * <p>Description: 客户端注册请求及返回实体 </p>
 * <p>
 * 数据类型转换，参考 <code>org.springframework.security.oauth2.server.authorization.oidc.http.converter.OidcClientRegistrationHttpMessageConverter</code>
 *
 * @author : gengwei.zheng
 * @date : 2023/5/20 14:31
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OAuth2ClientRegistration implements Serializable {

    /**
     * 自定义属性，用于 IOT 设备识别。
     * <p>
     * 非 IOT 用途，在请求时可以不用传该参数
     */
    @JsonProperty(SystemConstants.PARAMETER__PRODUCT_KEY)
    private String productKey;

    /**
     * 指定 OAuth2 客户端 AccessToken 格式。
     * <p>
     * 如果不指定，平台默认使用 Opaque Token
     */
    @JsonProperty(SystemConstants.TOKEN_FORMAT)
    private String tokenFormat;
    /**
     * 客户端ID。
     * <p>
     * 发送客户端动态注册请求时，该参数无需传递，SAS {@code OAuth2ClientRegistrationRegisteredClientConverter} 会自动生成。目前本系统未对该参数进行任何改造
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.CLIENT_ID)
    private String clientId;

    /**
     * 客户端颁发时间。
     * <p>
     * 发送客户端动态注册请求时，该参数无需传递，SAS {@code OAuth2ClientRegistrationRegisteredClientConverter} 会自动生成，为当前时间。
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.CLIENT_ID_ISSUED_AT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8", timezone = "GMT+8", shape = JsonFormat.Shape.NUMBER_INT)
    private Instant clientIdIssuedAt;

    /**
     * 客户端密钥。
     * <p>
     * 发送客户端动态注册请求时，可以设置也可以不设置。
     * · 如果 token_endpoint_auth_method 设置为 ClientAuthenticationMethod.CLIENT_SECRET_POST，则会为客户端生成默认密码
     * · 如果 token_endpoint_auth_method 设置为 ClientAuthenticationMethod.NONE 则不会为客户端生成默认密码
     * · 如果不设置 token_endpoint_auth_method ，则会默认设置为 ClientAuthenticationMethod.CLIENT_SECRET_BASIC 并生成默认密码
     * <p>
     * 参见 SAS {@code OAuth2ClientRegistrationRegisteredClientConverter}
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.CLIENT_SECRET)
    private String clientSecret;

    /**
     * 客户端密钥过期时间
     * <p>
     * 发送客户端动态注册请求时，可以不设置。如果不设置，则认为密钥不过期
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.CLIENT_SECRET_EXPIRES_AT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8", timezone = "GMT+8", shape = JsonFormat.Shape.NUMBER_INT)
    private Instant clientSecretExpiresAt;

    /**
     * 客户端名称。
     * <p>
     * 发送客户端动态注册请求时，可以不设置。如果不设置，则默认设置为系统自动生成的 Client Id。参见：{@code RegisteredClient.Build}
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.CLIENT_NAME)
    private String clientName;

    /**
     * 重定向地址。
     * <p>
     * 如果 grantTypes 中包含 AuthorizationGrantType.AUTHORIZATION_CODE，或者 responseTypes 中包含 code，则必须传递 redirectUris。除此以外可以不传。
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.REDIRECT_URIS)
    @JsonDeserialize(using = ArrayOrStringToListDeserializer.class)
    private List<String> redirectUris;

    /**
     * 客户端认证方法
     * <p>
     * 发送客户端动态注册请求时，可以设置也可以不设置。
     * · 如果设置为 ClientAuthenticationMethod.CLIENT_SECRET_POST，则会为客户端生成默认密码
     * · 如果设置为 ClientAuthenticationMethod.NONE 则不会为客户端生成默认密码
     * · 如果不设置，则会默认设置为 ClientAuthenticationMethod.CLIENT_SECRET_BASIC 并生成默认密码
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.TOKEN_ENDPOINT_AUTH_METHOD)
    private String tokenEndpointAuthenticationMethod;

    /**
     * 认证方法。
     * <p>
     * 发送客户端动态注册请求时，允许不传递该参数。但实际使用中，不指定认证方式，那么 OAuth2 没有任何意义
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.GRANT_TYPES)
    @JsonDeserialize(using = ArrayOrStringToListDeserializer.class)
    private List<String> grantTypes;

    /**
     * 响应类型。
     * <p>
     * 发送客户端动态注册请求时，允许不传递该参数。如果传递了该参数，同时其中包含 code，那么系统会自动将 grantTypes 设置为 AuthorizationGrantType.AUTHORIZATION_CODE。
     * <p>
     * 参见 SAS {@code OAuth2ClientRegistrationRegisteredClientConverter}
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.RESPONSE_TYPES)
    @JsonDeserialize(using = ArrayOrStringToListDeserializer.class)
    private List<String> responseTypes;

    /**
     * 这里 Scope 的格式只能是以空格分隔的字符串。否则存储的时候会出问题。
     * <p>
     * 参见：<code>org.springframework.security.oauth2.server.authorization.oidc.http.converter.OidcClientRegistrationHttpMessageConverter</code>
     * 其中静态类<code>MapOidcClientRegistrationConverter</code>的<code>convertScope</code>方法
     * <p>
     * 注意：在最新版本的 SAS 中，客户端动态注册时默认不需要传 scope 参数。如果传了 scope 参数，那么客户端动态注册时会抛出。参见：SAS {@code OAuth2ClientRegistrationAuthenticationValidator}
     * 当然，如果需要传递 scope，需要自己重新实现 {@code OAuth2ClientRegistrationAuthenticationValidator}
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.SCOPE)
    private String scope;

    /**
     * JWKS 地址。
     * <p>
     * 发送客户端动态注册请求时，允许不传递该参数
     */
    @JsonProperty(OAuth2ClientMetadataClaimNames.JWKS_URI)
    private String jwksUri;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getTokenFormat() {
        return tokenFormat;
    }

    public void setTokenFormat(String tokenFormat) {
        this.tokenFormat = tokenFormat;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Instant getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    public void setClientIdIssuedAt(Instant clientIdIssuedAt) {
        this.clientIdIssuedAt = clientIdIssuedAt;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Instant getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }

    public void setClientSecretExpiresAt(Instant clientSecretExpiresAt) {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<String> getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String getTokenEndpointAuthenticationMethod() {
        return tokenEndpointAuthenticationMethod;
    }

    public void setTokenEndpointAuthenticationMethod(String tokenEndpointAuthenticationMethod) {
        this.tokenEndpointAuthenticationMethod = tokenEndpointAuthenticationMethod;
    }

    public List<String> getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(List<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    public List<String> getResponseTypes() {
        return responseTypes;
    }

    public void setResponseTypes(List<String> responseTypes) {
        this.responseTypes = responseTypes;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJwksUri() {
        return jwksUri;
    }

    public void setJwksUri(String jwksUri) {
        this.jwksUri = jwksUri;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("productKey", productKey)
                .add("tokenFormat", tokenFormat)
                .add("clientId", clientId)
                .add("clientIdIssuedAt", clientIdIssuedAt)
                .add("clientSecret", clientSecret)
                .add("clientSecretExpiresAt", clientSecretExpiresAt)
                .add("clientName", clientName)
                .add("tokenEndpointAuthenticationMethod", tokenEndpointAuthenticationMethod)
                .add("scope", scope)
                .add("jwksUri", jwksUri)
                .toString();
    }
}
