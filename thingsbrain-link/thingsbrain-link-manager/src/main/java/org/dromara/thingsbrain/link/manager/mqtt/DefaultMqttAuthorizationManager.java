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

package org.dromara.thingsbrain.link.manager.mqtt;

import org.dromara.thingsbrain.link.commons.definition.MqttAuthorizationManager;
import org.dromara.thingsbrain.persistence.commons.domain.MqttCategory;
import org.dromara.thingsbrain.persistence.commons.service.MqttCategoryService;

import java.util.Optional;

/**
 * <p>Description: 系统默认 Mqtt 授权相关操作管理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/14 12:56
 */
public class DefaultMqttAuthorizationManager implements MqttAuthorizationManager {

    private final MqttCategoryService mqttCategoryService;

    public DefaultMqttAuthorizationManager(MqttCategoryService mqttCategoryService) {
        this.mqttCategoryService = mqttCategoryService;
    }

    @Override
    public Optional<MqttCategory> findSubscribeCategoryForPlatform() {
        return mqttCategoryService.findSubscribeCategoryForPlatform();
    }
}
