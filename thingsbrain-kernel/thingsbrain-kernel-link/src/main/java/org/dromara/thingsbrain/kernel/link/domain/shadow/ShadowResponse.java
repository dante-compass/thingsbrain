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

package org.dromara.thingsbrain.kernel.link.domain.shadow;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.dromara.thingsbrain.kernel.commons.definition.domain.AbstractProtocolEntity;
import org.dromara.thingsbrain.kernel.commons.definition.domain.shadow.Metadata;
import org.dromara.thingsbrain.kernel.commons.definition.domain.shadow.State;
import org.dromara.thingsbrain.kernel.commons.domain.Shadow;

/**
 * <p>Description: 设备影子响应实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/31 23:00
 */
public class ShadowResponse extends AbstractProtocolEntity<Long> {

    private Payload payload;

    private Long timestamp;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public static ShadowResponse success(Long version) {
        return new Builder(version).status("success").build();
    }

    public static ShadowResponse success(Shadow shadow) {
        return new Builder(shadow.getVersion())
                .status("success")
                .state(shadow.getState())
                .metadata(shadow.getMetadata())
                .build();
    }

    public static ShadowResponse failure(String code, String message) {
        return new Builder().status("error").error(new Error(code, message)).build();
    }

    public static ShadowResponse failure() {
        return failure("500", "服务端处理异常");
    }

    public static ShadowResponse control(Shadow shadow) {
        return new Builder("control", shadow.getVersion())
                .state(shadow.getState())
                .metadata(shadow.getMetadata())
                .build();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("payload", payload)
                .add("timestamp", timestamp)
                .addValue(super.toString())
                .toString();
    }

    public static class Builder {

        private final String method;
        private final Long version;
        private String status;
        private Error error;
        private State state;
        private Metadata metadata;
        private Long timestamp;

        protected Builder() {
            this(null);
        }

        protected Builder(Long version) {
            this("reply", version);
        }

        protected Builder(String method, Long version) {
            this.method = method;
            this.version = version;
        }

        public Builder status(String status) {
            this.status = "status";
            return this;
        }

        public Builder error(Error error) {
            ;
            this.error = error;
            return this;
        }

        public Builder state(State state) {
            this.state = state;
            return this;
        }

        public Builder metadata(Metadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public ShadowResponse build() {

            ShadowResponse response = new ShadowResponse();
            response.setMethod(method);
            response.setTimestamp(ObjectUtils.isNotEmpty(timestamp) ? timestamp : System.currentTimeMillis());

            Payload payload = new Payload();
            // method 为 control 类型响应中不包含 status 字段
            if (Strings.CS.equals(method, "reply") && StringUtils.isNotEmpty(status)) {
                payload.setStatus(status);

                if (Strings.CS.equals(status, "success")) {
                    payload.setVersion(version);
                }

                if (Strings.CS.equals(status, "error")) {
                    payload.setContent(error);
                }
            }

            if (ObjectUtils.isNotEmpty(state) && ObjectUtils.isNotEmpty(metadata)) {
                payload.setState(state);
                payload.setMetadata(metadata);

                response.setVersion(version);
            }

            response.setPayload(payload);
            return response;
        }
    }
}
