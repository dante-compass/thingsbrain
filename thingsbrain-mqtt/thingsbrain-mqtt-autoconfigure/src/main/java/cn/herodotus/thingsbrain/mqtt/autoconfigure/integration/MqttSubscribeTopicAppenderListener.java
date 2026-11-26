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

package cn.herodotus.thingsbrain.mqtt.autoconfigure.integration;

import cn.herodotus.thingsbrain.kernel.commons.definition.SubscribeTopic;
import cn.herodotus.thingsbrain.link.commons.event.MqttSubscribeTopicAppenderEvent;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>Description: 平台订阅主题动态添加器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/13 23:46
 */
public class MqttSubscribeTopicAppenderListener implements ApplicationListener<MqttSubscribeTopicAppenderEvent> {

    private static final Logger log = LoggerFactory.getLogger(MqttSubscribeTopicAppenderListener.class);

    private final MqttSubscribeTopicAppender appender;
    private final MqttTopicProperties properties;

    public MqttSubscribeTopicAppenderListener(MqttSubscribeTopicAppender appender, MqttTopicProperties properties) {
        this.appender = appender;
        this.properties = properties;
    }


    @Override
    public void onApplicationEvent(MqttSubscribeTopicAppenderEvent event) {

        Set<? extends SubscribeTopic> data = event.getData();

        if (CollectionUtils.isNotEmpty(data)) {

            List<SubscribeTopic> items = new ArrayList<>(data);
            if (CollectionUtils.isNotEmpty(properties.getDefaultSubscribes())) {
                properties.getDefaultSubscribes().stream()
                        .map(DefaultSubscribeTopic::with)
                        .forEach(items::add);
            }

            Mqtt5Subscription[] subscriptions = items.stream()
                    .map(item -> Mqtt5Subscription.builder()
                            .topicFilter(item.topic())
                            .qos(getMqttQos(item.quality())) // 可以从外部传入，或使用默认值
                            .noLocal(false) // 按需设置
                            .build())
                    .toArray(Mqtt5Subscription[]::new);

            appender.subscribe(subscriptions);

            log.info("[ThingsMesh] |- Dynamic add [{}] subscribe topics.", data.size());
        }
    }

    private MqttQos getMqttQos(int quality) {
        MqttQos mqttQos = MqttQos.fromCode(quality);
        return ObjectUtils.isNotEmpty(mqttQos) ? mqttQos : MqttQos.AT_LEAST_ONCE;
    }

    record DefaultSubscribeTopic(String topic, int quality) implements SubscribeTopic {
        public static DefaultSubscribeTopic with(String topic) {
            return new DefaultSubscribeTopic(topic, 0);
        }
    }
}
