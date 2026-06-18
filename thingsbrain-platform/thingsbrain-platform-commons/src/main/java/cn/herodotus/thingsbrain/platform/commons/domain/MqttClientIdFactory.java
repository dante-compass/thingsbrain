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

package cn.herodotus.thingsbrain.platform.commons.domain;

import cn.hutool.v7.core.util.RandomUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.dante.core.constant.SymbolConstants;
import org.dromara.dante.core.enums.SignatureMethod;
import cn.herodotus.thingsbrain.kernel.commons.constant.KernelConstants;
import cn.herodotus.thingsbrain.kernel.commons.domain.MqttClientIdDetail;
import cn.herodotus.thingsbrain.kernel.commons.enums.AuthType;
import cn.herodotus.thingsbrain.kernel.commons.utils.DataFormatUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Description: Mqtt Client Id </p>
 * <p>
 * 参考阿里云物联网实现 Mqtt ClientId 的构建以及解析
 *
 * @author : gengwei.zheng
 * @date : 2025/4/8 22:28
 */
public class MqttClientIdFactory {

    private static final String KEY__SECURE_MODE = "securemode";
    private static final String KEY__SIGNATURE_METHOD = "signmethod";
    private static final String KEY__TIMESTAMP = "timestamp";
    private static final String KEY__AUTH_TYPE = "authtype";
    private static final String KEY__RANDOM = "random";

    /**
     * 可选值有2（TLS直连模式，需要设置SSL/TLS信息）和3（TCP直连模式，无需设置SSL/TLS信息）
     * 采用一型一密免预注册时，固定取值为-2。
     */
    private Integer secureMode;
    /**
     * 签名方法
     */
    private SignatureMethod signMethod;
    /**
     * 表示当前时间毫秒值，可以不传递timestamp
     * <p>
     * 使用 String 方便传值，避免过多的转换
     */
    private String timestamp;
    /**
     * 一型一密认证方式，不同类型将返回不同的认证参数：
     * · register：一型一密预注册认证方式，返回DeviceSecret。
     * · regnwl：一型一密免预注册认证方式，返回DeviceToken、ClientID。
     */
    private AuthType authType;
    /**
     * 随机数。自定义随机数。
     * <p>
     * 使用 String 方便传值，避免过多的转换
     */
    private String random;
    /**
     * 物联网设备 ClientId
     */
    private String clientId;
    /**
     * MqttClientId 是否包含参数，如果包含参数则认为是签名方式登录
     */
    private Boolean signature;
    /**
     * 生成的 Mqtt Client Id
     */
    private String id;

    public Integer getSecureMode() {
        return secureMode;
    }

    private void setSecureMode(Integer secureMode) {
        this.secureMode = secureMode;
    }

    public SignatureMethod getSignMethod() {
        return signMethod;
    }

    private void setSignMethod(SignatureMethod signMethod) {
        this.signMethod = signMethod;
    }

    public String getTimestamp() {
        return timestamp;
    }

    private void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public AuthType getAuthType() {
        return authType;
    }

    private void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public String getRandom() {
        return random;
    }

    private void setRandom(String random) {
        this.random = random;
    }

    public String getClientId() {
        return clientId;
    }

    private void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public Boolean getSignature() {
        return signature;
    }

    private void setSignature(Boolean signature) {
        this.signature = signature;
    }

    private static MqttClientIdFactory create(Integer secureMode, SignatureMethod signMethod, AuthType authType, String timestamp, String random, String clientId, Boolean hasParameters, String id) {
        MqttClientIdFactory domain = new MqttClientIdFactory();
        domain.setSecureMode(secureMode);
        domain.setSignMethod(signMethod);
        domain.setTimestamp(timestamp);
        domain.setAuthType(authType);
        domain.setRandom(random);
        domain.setClientId(clientId);
        domain.setSignature(hasParameters);
        domain.setId(id);
        return domain;
    }

    public static Builder with(String productKey, String deviceName) {
        return new Builder(productKey, deviceName);
    }

    public static Builder with(String clientId) {
        return new Builder(clientId);
    }

    public static Parser of(String clientId) {
        return new Parser(clientId);
    }

    public static class Builder {

        private final String clientId;
        private Integer secureMode;
        private SignatureMethod signMethod;
        private AuthType authType;
        private String timestamp;
        private String random;
        private final Map<String, Object> parameters;

        protected Builder(String productKey, String deviceName) {
            this(DataFormatUtils.toDeviceClientId(productKey, deviceName));
        }

        protected Builder(String clientId) {
            this.clientId = clientId;
            this.parameters = new LinkedHashMap<>();
        }

        public Builder secureMode(Integer secureMode) {
            this.secureMode = secureMode;
            parameters.put(KernelConstants.KEY__SECURE_MODE, secureMode);
            return this;
        }

        public Builder signMethod(SignatureMethod signMethod) {
            this.signMethod = signMethod;
            parameters.put(KernelConstants.KEY__SIGNATURE_METHOD, signMethod.getValue());
            return this;
        }

        public Builder authType(AuthType authType) {
            this.authType = authType;
            parameters.put(KernelConstants.KEY__AUTH_TYPE, authType);
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            parameters.put(KernelConstants.KEY__TIMESTAMP, timestamp);
            return this;
        }

        public Builder timestamp() {
            return timestamp(String.valueOf(System.currentTimeMillis()));
        }

        public Builder random(String random) {
            this.random = random;
            parameters.put(KernelConstants.KEY__RANDOM, random);
            return this;
        }

        public Builder random() {
            return this.random(RandomUtil.randomNumbers(6));
        }

        private String createId() {
            if (hasParameters()) {
                return clientId;
            } else {
                String params = parameters.entrySet()
                        .stream()
                        .map(e -> e.getKey() + SymbolConstants.EQUAL + e.getValue())
                        .collect(Collectors.joining(SymbolConstants.COMMA));
                return clientId + SymbolConstants.PIPE + params + SymbolConstants.PIPE;
            }
        }

        private Boolean hasParameters() {
            return MapUtils.isNotEmpty(parameters);
        }

        public MqttClientIdFactory build() {
            return create(this.secureMode, this.signMethod, this.authType, this.timestamp, this.random, this.clientId, hasParameters(), createId());
        }
    }

    public static class Parser {
        private Integer secureMode;
        private SignatureMethod signMethod;
        private AuthType authType;
        private String timestamp;
        private String random;
        private String clientId;
        private Boolean hasParameters;
        private final String id;

        protected Parser(String id) {
            this.id = id;
        }

        /**
         * 解析扩展参数
         *
         * @param parameters 扩展参数字符串
         */
        private void parseParameters(String parameters) {
            if (StringUtils.isNotBlank(parameters)) {
                Map<String, String> params = DataFormatUtils.parseMqttParams(parameters);

                params.forEach((key, value) -> {
                    String index = StringUtils.toRootLowerCase(key);
                    switch (index) {
                        case KEY__SECURE_MODE:
                            this.secureMode = Integer.parseInt(value);
                            break;
                        case KEY__SIGNATURE_METHOD:
                            this.signMethod = SignatureMethod.get(value);
                            break;
                        case KEY__TIMESTAMP:
                            this.timestamp = value;
                            break;
                        case KEY__AUTH_TYPE:
                            this.authType = AuthType.get(value);
                            break;
                        case KEY__RANDOM:
                            this.random = value;
                            break;
                        default:
                            break;
                    }
                });
            }
        }

        /**
         * 拆分 ClientId 和扩展参数
         *
         * @param detail 字符串 Tuple {@link MqttClientIdDetail}
         */
        private void parseMqttClientIdDetail(MqttClientIdDetail detail) {
            this.clientId = detail.getClientId();
            this.hasParameters = detail.getHasParameters();
            parseParameters(detail.getParameters());
        }

        public MqttClientIdFactory parse() {

            Optional<MqttClientIdDetail> optional = DataFormatUtils.fromMqttClientId(this.id);
            optional.ifPresentOrElse(this::parseMqttClientIdDetail, () -> this.hasParameters = false);

            if (StringUtils.isBlank(this.clientId)) {
                this.clientId = id;
            }

            return create(this.secureMode, this.signMethod, this.authType, this.timestamp, this.random, this.clientId, hasParameters, this.id);
        }
    }
}
