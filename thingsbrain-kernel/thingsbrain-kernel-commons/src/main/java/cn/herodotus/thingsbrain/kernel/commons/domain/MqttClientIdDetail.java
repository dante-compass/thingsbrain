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

package cn.herodotus.thingsbrain.kernel.commons.domain;

import cn.herodotus.dante.core.domain.BaseEntity;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: Mqtt 客户端 ID 详情 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/29 13:19
 */
public class MqttClientIdDetail implements BaseEntity {

    /**
     * 物联网设备 ClientId
     */
    private final String clientId;
    /**
     * 物联网客户端ID扩展参数字符串
     */
    private final String parameters;

    /**
     * 是否 MqttClientId 中包含参数
     */
    private final Boolean hasParameters;

    public MqttClientIdDetail(String clientId) {
        this(clientId, null);
    }

    public MqttClientIdDetail(String clientId, String parameters) {
        this.clientId = clientId;
        this.parameters = parameters;
        this.hasParameters = StringUtils.isNotBlank(this.parameters);
    }

    public String getClientId() {
        return clientId;
    }

    public String getParameters() {
        return parameters;
    }

    public Boolean getHasParameters() {
        return hasParameters;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clientId", clientId)
                .add("parameters", parameters)
                .add("hasParameters", hasParameters)
                .toString();
    }
}
