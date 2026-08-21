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

package cn.herodotus.thingsbrain.kernel.link.domain.shadow;

import cn.herodotus.dante.core.constant.SystemConstants;
import cn.herodotus.thingsbrain.kernel.commons.constant.MethodConstants;
import cn.herodotus.thingsbrain.kernel.link.definition.AbstractMethodDomain;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.AbstractShadow;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.Error;
import cn.herodotus.thingsbrain.kernel.link.definition.shadow.Payload;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

/**
 * <p>Description: 设备影子响应实体定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/5/31 23:00
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShadowResponse extends AbstractMethodDomain<Integer> {

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

    public static ShadowResponse reply(Integer version) {
        return new Builder().version(version).build();
    }

    public static ShadowResponse reply(Shadow shadow) {
        return new Builder().shadow(shadow).build();
    }

    public static ShadowResponse error(String code, String message) {
        return new Builder().error(new Error(code, message)).build();
    }

    public static ShadowResponse error() {
        return error("500", "服务端处理异常");
    }

    public static ShadowResponse control(Shadow shadow) {
        return new Builder(MethodConstants.METHOD__SHADOW_CONTROL).shadow(shadow).build();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("timestamp", timestamp)
                .addValue(super.toString())
                .toString();
    }

    public static class Builder {

        private final String method;
        private Integer version;
        private final Long timestamp;
        private String status;
        private Error error;
        private AbstractShadow shadow;

        protected Builder() {
            this(MethodConstants.METHOD__SHADOW_REPLY);
        }

        protected Builder(String method) {
            this.method = method;
            this.timestamp = System.currentTimeMillis();
        }

        public Builder error(Error error) {
            this.error = error;
            this.status = SystemConstants.STATUS__ERROR;
            return this;
        }

        public Builder shadow(AbstractShadow shadow) {
            this.shadow = shadow;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public ShadowResponse build() {

            ShadowResponse response = new ShadowResponse();
            response.setTimestamp(this.timestamp);
            response.setMethod(this.method);

            Payload payload = new Payload();

            if (Strings.CS.equals(this.method, MethodConstants.METHOD__SHADOW_REPLY)) {
                // reply 模式下，都有 status
                if (StringUtils.isBlank(this.status)) {
                    payload.setStatus(SystemConstants.STATUS__SUCCESS);
                } else {
                    payload.setStatus(this.status);
                    if (ObjectUtils.isNotEmpty(this.error)) {
                        payload.setContent(this.error);
                    }
                }

                if (ObjectUtils.isNotEmpty(this.version)) {
                    payload.setVersion(this.version);
                }
            }

            if (ObjectUtils.isNotEmpty(this.shadow) && this.shadow.isNotEmpty()) {
                payload.setShadow(this.shadow);
                // 只有在包含 state 和 metadata 时，才需要给 response 设置 version
                response.setVersion(version);
            }

            response.setPayload(payload);
            return response;
        }
    }
}
