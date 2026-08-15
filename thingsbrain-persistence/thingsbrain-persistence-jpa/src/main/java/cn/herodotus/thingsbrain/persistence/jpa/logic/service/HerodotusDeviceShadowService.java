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

package cn.herodotus.thingsbrain.persistence.jpa.logic.service;

import cn.herodotus.dante.data.jpa.repository.BaseJpaRepository;
import cn.herodotus.dante.data.jpa.service.AbstractJpaService;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceShadow;
import cn.herodotus.thingsbrain.persistence.jpa.logic.repository.HerodotusDeviceShadowRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>Description: 物联网设备影子 Jpa 存储 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/2 17:38
 */
@Service
public class HerodotusDeviceShadowService extends AbstractJpaService<HerodotusDeviceShadow, String> {

    private final HerodotusDeviceShadowRepository herodotusDeviceShadowRepository;

    public HerodotusDeviceShadowService(HerodotusDeviceShadowRepository herodotusDeviceShadowRepository) {
        this.herodotusDeviceShadowRepository = herodotusDeviceShadowRepository;
    }

    @Override
    public BaseJpaRepository<HerodotusDeviceShadow, String> getRepository() {
        return herodotusDeviceShadowRepository;
    }

    public Optional<HerodotusDeviceShadow> findOneByProductKeyAndDeviceName(String productKey, String deviceName) {
        return herodotusDeviceShadowRepository.findOneByProductKeyAndDeviceName(productKey, deviceName);
    }
}
