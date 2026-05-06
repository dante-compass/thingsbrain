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

package org.dromara.thingsbrain.mqtt.autoconfigure.integration;

import org.dromara.thingsbrain.kernel.commons.definition.domain.SubscribeTopic;
import org.dromara.thingsbrain.kernel.commons.event.MqttSubscribeTopicAppenderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * <p>Description: 平台订阅主题添加器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/13 23:46
 */
public class MqttSubscribeTopicAppenderListener implements ApplicationListener<MqttSubscribeTopicAppenderEvent> {

    private static final Logger log = LoggerFactory.getLogger(MqttSubscribeTopicAppenderListener.class);

    private final Mqttv5PahoMessageDrivenChannelAdapter mqttv5PahoMessageDrivenChannelAdapter;

    public MqttSubscribeTopicAppenderListener(Mqttv5PahoMessageDrivenChannelAdapter mqttv5PahoMessageDrivenChannelAdapter) {
        this.mqttv5PahoMessageDrivenChannelAdapter = mqttv5PahoMessageDrivenChannelAdapter;
    }

    @Override
    public void onApplicationEvent(MqttSubscribeTopicAppenderEvent event) {

        Set<? extends SubscribeTopic> data = event.getData();

        List<? extends SubscribeTopic> items = new ArrayList<>(data);

        String[] topicItems = new String[data.size()];
        int[] qosItems = new int[data.size()];

        IntStream.range(0, data.size()).forEach(i -> {
            topicItems[i] = items.get(i).getTopic();
            qosItems[i] = items.get(i).getQuality();
        });

        mqttv5PahoMessageDrivenChannelAdapter.addTopics(topicItems, qosItems);

        log.info("[ThingsBrain] |- Dynamic add [{}] subscribe topics.", data.size());
    }


}
