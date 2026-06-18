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

package cn.herodotus.thingsbrain.mqtt.commons.domain;

import cn.herodotus.thingsbrain.kernel.commons.domain.CompleteIdentifier;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * <p>Description: Mqtt 请求相关数据实体 </p>
 * <p>
 * 目前，主要用于缓存请求相关数据
 *
 * @author : gengwei.zheng
 * @date : 2025/10/17 13:54
 */
public class MqttOperation extends CompleteIdentifier {

    private String requestId;
    private String userId;
    /**
     * 找到对应处理逻辑的标识符
     */
    private String method;

    public MqttOperation() {
        super();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder with(String productKey, String deviceName) {
        return new Builder(productKey, deviceName);
    }

    public static Builder with(String productKey, String deviceName, String identifier) {
        return new Builder(productKey, deviceName, identifier);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MqttOperation that = (MqttOperation) o;
        return Objects.equal(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(requestId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("requestId", requestId)
                .add("username", userId)
                .add("method", method)
                .toString();
    }

    public static class Builder {
        private String productKey;
        private String deviceName;
        private String identifier;
        private String requestId;
        private String userId;

        protected Builder() {

        }

        protected Builder(String productKey, String deviceName) {
            this(productKey, deviceName, null);
        }

        protected Builder(String productKey, String deviceName, String identifier) {
            this.productKey = productKey;
            this.deviceName = deviceName;
            this.identifier = identifier;
        }

        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public MqttOperation build() {
            MqttOperation request = new MqttOperation();
            request.setProductKey(productKey);
            request.setDeviceName(deviceName);
            request.setIdentifier(identifier);
            request.setRequestId(requestId);
            request.setUserId(userId);
            return request;
        }
    }
}
