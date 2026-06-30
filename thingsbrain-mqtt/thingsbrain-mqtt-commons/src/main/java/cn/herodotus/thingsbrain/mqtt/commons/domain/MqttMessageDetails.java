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

import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.core.domain.BaseModel;
import cn.herodotus.dante.core.jackson.JacksonUtils;
import cn.herodotus.dante.core.jackson.JsonNodeUtils;
import cn.herodotus.thingsbrain.kernel.commons.enums.TopicCategory;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import tools.jackson.databind.JsonNode;

/**
 * <p>Description: Mqtt 消息交互详情定义实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/20 10:38
 */
public class MqttMessageDetails implements BaseModel {

    private String topic;
    private JsonNode payload;
    private Integer qos;
    private String responseTopic;
    private byte[] correlationData;
    private boolean duplicate;
    private boolean empty;
    private String messageId;
    private String requestId;
    private String method;
    private TopicCategory topicCategory;
    private boolean supportResponse;

    public String getTopic() {
        return topic;
    }

    private void setTopic(String topic) {
        this.topic = topic;
    }

    public JsonNode getPayload() {
        return payload;
    }

    private void setPayload(JsonNode payload) {
        this.payload = payload;
    }

    public Integer getQos() {
        return qos;
    }

    private void setQos(Integer qos) {
        this.qos = qos;
    }

    public String getResponseTopic() {
        return responseTopic;
    }

    private void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public byte[] getCorrelationData() {
        return correlationData;
    }

    private void setCorrelationData(byte[] correlationData) {
        this.correlationData = correlationData;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    private void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public boolean isEmpty() {
        return empty;
    }

    private void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getMessageId() {
        return messageId;
    }

    private void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getRequestId() {
        return requestId;
    }

    private void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMethod() {
        return method;
    }

    private void setMethod(String method) {
        this.method = method;
    }

    public TopicCategory getTopicCategory() {
        return topicCategory;
    }

    private void setTopicCategory(TopicCategory topicCategory) {
        this.topicCategory = topicCategory;
    }

    public boolean isSupportResponse() {
        return supportResponse;
    }

    private void setSupportResponse(boolean supportResponse) {
        this.supportResponse = supportResponse;
    }

    public static Builder with(String topic, String payload) {
        return new Builder(topic, payload);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("topic", topic)
                .add("payload", payload)
                .add("qos", qos)
                .add("responseTopic", responseTopic)
                .add("duplicate", duplicate)
                .add("messageId", messageId)
                .add("topicCategory", topicCategory)
                .toString();
    }

    public static class Builder {
        private final String topic;
        private final JsonNode payload;
        private Integer qos = 0;
        private String responseTopic;
        private byte[] correlationData;
        private Boolean duplicate;
        private String messageId;

        protected Builder(String topic, String payload) {
            this.topic = topic;
            this.payload = JacksonUtils.toNode(payload);
        }

        public Builder qos(Integer qos) {
            this.qos = qos;
            return this;
        }

        public Builder responseTopic(String responseTopic) {
            this.responseTopic = responseTopic;
            return this;
        }

        public Builder correlationData(byte[] correlationData) {
            this.correlationData = correlationData;
            return this;
        }

        public Builder duplicate(Boolean duplicate) {
            this.duplicate = duplicate;
            return this;
        }

        public Builder messageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        private TopicCategory getTopicCategory(String topic) {
            String prefix = StringUtils.substringBefore(topic, SymbolConstants.FORWARD_SLASH);
            if (StringUtils.isNotBlank(prefix)) {
                return TopicCategory.get(prefix);
            } else {
                return TopicCategory.SYS;
            }
        }

        public MqttMessageDetails build() {
            MqttMessageDetails details = new MqttMessageDetails();
            details.setTopic(this.topic);
            details.setPayload(this.payload);
            details.setQos(this.qos);
            details.setResponseTopic(this.responseTopic);
            details.setCorrelationData(this.correlationData);
            details.setMessageId(this.messageId);
            details.setRequestId(JsonNodeUtils.findStringValue(payload, "id"));
            details.setMethod(JsonNodeUtils.findStringValue(payload, "method"));
            details.setDuplicate(this.duplicate);
            details.setTopicCategory(getTopicCategory(this.topic));
            details.setEmpty(ObjectUtils.isNotEmpty(this.payload));
            details.setSupportResponse(StringUtils.isNotBlank(this.responseTopic));

            return details;
        }
    }
}
