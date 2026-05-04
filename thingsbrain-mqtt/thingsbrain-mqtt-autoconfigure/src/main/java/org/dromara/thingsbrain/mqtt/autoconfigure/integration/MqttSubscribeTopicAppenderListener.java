/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsBrain.
 *
 * Herodotus ThingsBrain is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsBrain is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
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
