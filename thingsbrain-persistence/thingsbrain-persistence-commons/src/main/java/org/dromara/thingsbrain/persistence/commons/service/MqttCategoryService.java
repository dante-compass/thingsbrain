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
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package org.dromara.thingsbrain.persistence.commons.service;

import org.dromara.dante.data.commons.service.BaseWriteAndPageService;
import org.dromara.thingsbrain.persistence.commons.domain.MqttCategory;

import java.util.Optional;

/**
 * <p>Description: 物联网 Mqtt 主题分类管理统一定义 Service</p>
 *
 * @author : gengwei.zheng
 * @date : 2025/10/14 15:51
 */
public interface MqttCategoryService extends BaseWriteAndPageService<MqttCategory, String> {

    /**
     * 查询包含所有平台可以订阅主题的主题分类
     *
     * @return 主题分类 {@link MqttCategory}
     */
    Optional<MqttCategory> findSubscribeCategoryForPlatform();
}
