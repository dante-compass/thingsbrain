/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus ThingsMesh.
 *
 * Herodotus ThingsMesh is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus ThingsMesh is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package cn.herodotus.thingsbrain.persistence.jpa.logic.repository;

import cn.herodotus.dante.data.jpa.repository.BaseJpaRepository;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceShadow;

import java.util.Optional;

/**
 * <p>Description: 物联网设备影子 Jpa 存储 Repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/2 17:35
 */
public interface HerodotusDeviceShadowRepository extends BaseJpaRepository<HerodotusDeviceShadow, String> {

    /**
     * 根据 ProductKey 和 DeviceName 查找设备影子
     *
     * @param productKey 物联网 ProductKey
     * @param deviceName 物联网 DeviceName
     * @return 设备影子
     */
    Optional<HerodotusDeviceShadow> findOneByProductKeyAndDeviceName(String productKey, String deviceName);
}
