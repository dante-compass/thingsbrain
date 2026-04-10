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

package org.dromara.thingsbrain.persistence.jpa.specification;

import org.dromara.thingsbrain.persistence.commons.domain.TslUnit;
import org.dromara.thingsbrain.persistence.commons.service.TslUnitService;
import org.dromara.thingsbrain.persistence.jpa.converter.ToTslUnitConverter;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslUnit;
import org.dromara.thingsbrain.persistence.jpa.logic.service.HerodotusTslUnitService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * <p>Description: 物联网物模型单位 Service Jpa 实现 </p>
 * <p>
 * 目前进需要可以查询物模型单位即可，用于前端显示物模型单位列表
 *
 * @author : gengwei.zheng
 * @date : 2025/4/4 16:07
 */
public class JpaTslUnitService implements TslUnitService {

    private final HerodotusTslUnitService delegate;
    private final Converter<HerodotusTslUnit, TslUnit> toTslUnit;

    public JpaTslUnitService(HerodotusTslUnitService herodotusTslUnitService) {
        this.delegate = herodotusTslUnitService;
        this.toTslUnit = new ToTslUnitConverter();
    }

    @Override
    public Page<TslUnit> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        Page<HerodotusTslUnit> pages = delegate.findByPage(pageNumber, pageSize, direction, properties);
        return pages.map(toTslUnit::convert);
    }

    @Override
    public Page<TslUnit> findByPage(int pageNumber, int pageSize) {
        Page<HerodotusTslUnit> pages = delegate.findByPage(pageNumber, pageSize);
        return pages.map(toTslUnit::convert);
    }
}
