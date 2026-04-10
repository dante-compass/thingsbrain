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

package org.dromara.thingsbrain.persistence.jpa.converter;

import org.dromara.dante.data.jpa.converter.AbstractToEntityConverter;
import org.dromara.thingsbrain.persistence.commons.domain.TslUnit;
import org.dromara.thingsbrain.persistence.jpa.logic.entity.HerodotusTslUnit;

/**
 * <p>Description: {@link HerodotusTslUnit} 转 {@link TslUnit} 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2025/4/14 22:09
 */
public class ToTslUnitConverter extends AbstractToEntityConverter<HerodotusTslUnit, TslUnit> {

    @Override
    public TslUnit getInstance() {
        return new TslUnit();
    }

    @Override
    public void prepare(HerodotusTslUnit source, TslUnit target) {
        target.setId(source.getUnitId());
        target.setSymbol(source.getUnitSymbol());
        target.setName(source.getUnitName());
    }
}
