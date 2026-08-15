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

package cn.herodotus.thingsbrain.persistence.jpa.converter;

import cn.herodotus.dante.data.jpa.converter.AbstractFromAuditEntityConverter;
import cn.herodotus.thingsbrain.persistence.commons.domain.DeviceShadow;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusDeviceShadow;
import cn.herodotus.thingsbrain.persistence.jpa.logic.entity.HerodotusProductCategory;

/**
 * <p>Description: {@link DeviceShadow} 转 {@link HerodotusProductCategory} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/3/31 16:02
 */
public class FromDeviceShadowConverter extends AbstractFromAuditEntityConverter<DeviceShadow, HerodotusDeviceShadow> {

    @Override
    public HerodotusDeviceShadow getInstance() {
        return new HerodotusDeviceShadow();
    }

    @Override
    public void prepare(DeviceShadow source, HerodotusDeviceShadow target) {
        target.setShadowId(source.getId());
        target.setProductKey(source.getProductKey());
        target.setDeviceName(source.getDeviceName());
        target.setContent(source.getContent());
    }
}
