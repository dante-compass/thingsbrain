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

package cn.herodotus.thingsbrain.persistence.jpa.specification;

import cn.herodotus.thingsbrain.persistence.commons.domain.DeviceShadow;
import cn.herodotus.thingsbrain.persistence.commons.service.DeviceShadowService;
import cn.herodotus.thingsbrain.persistence.jpa.converter.FromDeviceShadowConverter;
import cn.herodotus.thingsbrain.persistence.jpa.converter.ToDeviceShadowConverter;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceShadow;
import cn.herodotus.thingsbrain.persistence.jpa.logic.service.HerodotusDeviceShadowService;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

/**
 * <p>Description: 物联网设备影子 Service Jpa 实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/6/2 17:39
 */
public class JpaDeviceShadowService implements DeviceShadowService {

    private final HerodotusDeviceShadowService delegate;
    private final Converter<HerodotusDeviceShadow, DeviceShadow> toDomain;
    private final Converter<DeviceShadow, HerodotusDeviceShadow> fromDomain;

    public JpaDeviceShadowService(HerodotusDeviceShadowService herodotusDeviceShadowService) {
        this.delegate = herodotusDeviceShadowService;
        this.toDomain = new ToDeviceShadowConverter();
        this.fromDomain = new FromDeviceShadowConverter();
    }

    @Override
    public DeviceShadow save(DeviceShadow domain) {
        HerodotusDeviceShadow entity = delegate.save(fromDomain.convert(domain));
        return toDomain.convert(entity);
    }

    @Override
    public void deleteById(String id) {
        delegate.deleteById(id);
    }

    @Override
    public Optional<DeviceShadow> findOneByProductKeyAndDeviceName(String productKey, String deviceName) {
        return delegate.findOneByProductKeyAndDeviceName(productKey, deviceName)
                .map(toDomain::convert);
    }
}
