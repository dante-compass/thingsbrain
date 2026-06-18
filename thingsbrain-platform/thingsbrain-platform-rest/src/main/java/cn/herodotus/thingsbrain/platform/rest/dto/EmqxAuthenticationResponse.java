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

package cn.herodotus.thingsbrain.platform.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dromara.dante.core.domain.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: Emqx 认证 Webhook 结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/26 22:04
 */
public class EmqxAuthenticationResponse implements BaseEntity {

    public enum Status {
        allow, deny, ignore;
    }

    /**
     * 认证结果
     */
    @JsonProperty(value = "result")
    private String status;

    /**
     * 是否为超级用户
     */
    @JsonProperty(value = "is_superuser")
    private Boolean superUser = Boolean.FALSE;

    /**
     * 客户端属性
     */
    @JsonProperty(value = "client_attrs")
    private Map<String, String> attributes = new HashMap<>();

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuperUser() {
        return superUser;
    }

    private void setSuperUser(Boolean superUser) {
        this.superUser = superUser;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    private void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String status;
        private Boolean superUser;
        private final Map<String, String> attributes;

        protected Builder() {
            this.superUser = Boolean.FALSE;
            this.attributes = new HashMap<>();
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder superUser(Boolean superUser) {
            this.superUser = superUser;
            return this;
        }

        public Builder attribute(String key, String value) {
            this.attributes.put(key, value);
            return this;
        }

        public EmqxAuthenticationResponse build() {
            EmqxAuthenticationResponse result = new EmqxAuthenticationResponse();
            result.setStatus(this.status);
            result.setSuperUser(superUser);
            result.setAttributes(this.attributes);
            return result;
        }
    }
}
