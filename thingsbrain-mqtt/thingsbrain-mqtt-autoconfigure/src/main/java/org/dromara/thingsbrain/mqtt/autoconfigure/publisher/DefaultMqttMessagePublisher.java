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

package org.dromara.thingsbrain.mqtt.autoconfigure.publisher;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import org.dromara.dante.cache.jetcache.utils.JetCacheUtils;
import org.dromara.thingsbrain.mqtt.commons.constant.MqttConstants;
import org.dromara.thingsbrain.mqtt.commons.definition.MqttMessagePublisher;
import org.dromara.thingsbrain.mqtt.commons.domain.MqttOperation;

import java.time.Duration;
import java.util.Optional;

/**
 * <p>Description: 默认 Mqtt 消息管理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/17 14:11
 */
public class DefaultMqttMessagePublisher implements MqttMessagePublisher {

    private final Cache<String, MqttOperation> cache;

    public DefaultMqttMessagePublisher() {
        this.cache = JetCacheUtils.create(MqttConstants.CACHE_THINGSBRAIN_REQUEST, CacheType.BOTH, Duration.ofSeconds(1), true);
    }

    private MqttOperation get(String requestId) {
        return cache.get(requestId);
    }

    private void put(String requestId, MqttOperation request) {
        cache.put(requestId, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MqttOperation> exists(String requestId) {
        return Optional.ofNullable(get(requestId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cache(String requestId, MqttOperation operation) {
        put(requestId, operation);
    }
}
