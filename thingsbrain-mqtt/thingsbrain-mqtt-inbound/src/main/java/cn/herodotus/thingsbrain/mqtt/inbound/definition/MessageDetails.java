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

package cn.herodotus.thingsbrain.mqtt.inbound.definition;

import cn.hutool.v7.core.text.StrUtil;
import org.apache.commons.lang3.ArrayUtils;
import cn.herodotus.dante.core.domain.BaseModel;
import cn.herodotus.dante.core.jackson.JacksonUtils;
import tools.jackson.databind.JsonNode;

import java.nio.charset.StandardCharsets;

/**
 * <p>Description: Mqtt 消息交互详情定义实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/20 10:38
 */
public class MessageDetails implements BaseModel {

    private final String topic;
    private final byte[] payload;
    private final String responseTopic;
    private final byte[] correlationData;

    public MessageDetails(String topic, byte[] payload) {
        this(topic, payload, null, null);
    }

    public MessageDetails(String topic, byte[] payload, String responseTopic, byte[] correlationData) {
        this.topic = topic;
        this.payload = payload;
        this.responseTopic = responseTopic;
        this.correlationData = correlationData;
    }

    public String getTopic() {
        return topic;
    }

    public JsonNode getPayload() {
        if (ArrayUtils.isNotEmpty(payload)) {
            return JacksonUtils.toNode(payload);
        }

        return null;
    }

    public String getResponseTopic() {
        return responseTopic;
    }

    public String getCorrelationData() {
        if (ArrayUtils.isNotEmpty(correlationData)) {
            return StrUtil.str(correlationData, StandardCharsets.UTF_8);
        }

        return null;
    }
}
